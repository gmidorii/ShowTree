package crawling;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by midori on 2016/04/19.
 */
public class Crawl {
    private String urlName = "";
    private String host = "";
    private String hostUrl = "";
    private String protocol = "";
    final private String SEARCHTAG = "<a href";

    public Crawl(String urlName, String host, String protocol){
        this.urlName = urlName;
        this.host = host;
        this.protocol = protocol;
        this.hostUrl = protocol + "://" + host;
    }

    public String getUrlName() { return urlName; }
    public String getHost() { return host; }


    public StringBuffer HtmlToString(){
        StringBuffer str = new StringBuffer();
        try {
            URL url = new URL(urlName);
            String host = url.getHost();

            InputStream input = url.openStream();
            BufferedReader buf = new BufferedReader(new InputStreamReader(input));

            StringBuffer line = new StringBuffer();
            while(!line.append(buf.readLine()).toString().equals("null")){
                str.append(line);
                line.setLength(0);
            }

        }catch (IOException e){
//            e.printStackTrace();
        }
        return str;
    }



    public Set<String> generateUrlSet(StringBuffer str){
        int index = 0;
        int getIndex = 0;
        int CATHEERRORNUM = 10;
        Set<String> urlSet = new HashSet<>();
        char doubleQuotation = '"';
        char singleQuotation = '\'';
        StringBuffer url = new StringBuffer();
        int count = 0;
        while( (getIndex = str.indexOf(SEARCHTAG, getIndex)) != -1 && count < 1000){
            index = getIndex;

            // 最初の”まで進める
            while(str.charAt(index) != doubleQuotation && str.charAt(index) != singleQuotation){
                index++;
                if(index - getIndex > CATHEERRORNUM){
                    break;
                }
            }
            index++;
            if(index - getIndex > CATHEERRORNUM){
                getIndex++;
                continue;
            }

            // assign url URL
            while(str.charAt(index) != doubleQuotation && str.charAt(index) != singleQuotation){
                url.append(str.charAt(index));
                index++;
            }

            // add list
            if(url.indexOf(host) != -1 || url.indexOf("/") == 0) {
                urlSet.add(url.toString());
                count++;
            }

            //StringBuffer initialize
            url.setLength(0);
            getIndex++;
        }

        return urlSet;
    }



    public Set<String> getUrlSet(String url, int hierarchy){
        Set<String> urlSet = new HashSet<>();
        List<Set<String>> urlSetList = new ArrayList<>();
        addUrlSet(url, urlSet);
        URLFormatter format = new URLFormatter();
        if(hierarchy > 0){
            hierarchy--;
            getUrlSet(hierarchy, urlSet, urlSetList);
        }

        HashSet<String> clearUrlSet = new HashSet<>();
        for (Set<String> notCleanUrlSet : urlSetList) {
            for (String urlBuf : notCleanUrlSet) {
                clearUrlSet.add(format.removeUnnecessaryPart(new StringBuffer(urlBuf), host));
            }
        }
        return clearUrlSet;
    }

    public void getUrlSet(int hierarchy, Set<String> urlSet, List<Set<String>> urlSetList){
        Set<String> newUrlSet = new HashSet<>();
        for (String url : urlSet) {
            //相対パスの時
            if(url.indexOf(host) == -1){
                url = hostUrl + url;
            }else if(url.indexOf(protocol) == -1){
                url = protocol + ":"+ url;
            }

            addUrlSetList(url, newUrlSet, urlSetList);
        }
        if(hierarchy > 0){
            hierarchy--;
            getUrlSet(hierarchy, newUrlSet, urlSetList);
        }
    }

    public void addUrlSet(String url, Set<String> urlSet){
        String urlStr = "";
        try{
            Document doc = Jsoup.connect(url).get();
            Elements links = doc.getElementsByTag("a");
            for (Element link : links){
                urlStr = link.attr("href");
                // host or 相対パス
                if(urlStr.indexOf(host) != -1 || (urlStr.indexOf("/") == 0 && urlStr.indexOf("/", 1) != 1) ){
                    urlSet.add(urlStr);
                }
            }
        }catch(IOException e){}
    }
    
    public void addUrlSetList(String url, Set<String> urlSet, List<Set<String>> urlSetList){
        addUrlSet(url.toString(), urlSet);
        urlSetList.add(urlSet);
    }
}
