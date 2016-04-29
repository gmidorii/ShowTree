package crawling;

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
    final private String SEARCHTAG = "<a href";

    public Crawl(String urlName, String host){
        this.urlName = urlName;
        this.host = host;
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

}
