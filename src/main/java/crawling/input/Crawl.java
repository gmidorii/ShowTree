package crawling.input;

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
    private URLManager urlManager  = null;
    final private String SEARCHTAG = "<a href";

    public Crawl(URLManager urlManager)throws MalformedURLException{
        this.urlManager = urlManager;
    }

    /*****  Jsoup 利用 ****/
    public Set<String> getUrlSet(int hierarchy){
        System.out.println("-----------------");
        Set<String> urlSet = new HashSet<>();
        List<Set<String>> urlSetList = new ArrayList<>();
        fetchUrlSet(urlManager.getUrl(), urlSet);
        urlSetList.add(urlSet);
        hierarchy--;
        if(hierarchy > 0){
            getUrlSet(hierarchy, urlSet, urlSetList);
        }

        HashSet<String> clearUrlSet = new HashSet<>();
        for (Set<String> notCleanUrlSet : urlSetList) {
            for (String urlBuf : notCleanUrlSet) {
                clearUrlSet.add(urlManager.removeUnnecessaryPart(new StringBuffer(urlBuf)));
            }
        }
        return clearUrlSet;
    }

    public void getUrlSet(int hierarchy, Set<String> urlSet, List<Set<String>> urlSetList){
        System.out.println("-----------------");
        Set<String> newUrlSet = new HashSet<>();
        urlSet.forEach(url -> fetchUrlSet(url, newUrlSet));
        urlSetList.add(newUrlSet);
        hierarchy--;
        if(hierarchy > 0){
            getUrlSet(hierarchy, newUrlSet, urlSetList);
        }
    }

    public void fetchUrlSet(String url, Set<String> urlSet){
        String urlStr = "";
        //相対パスの時
        if(url.indexOf(urlManager.getHost()) == -1){
            url = urlManager.getHostUrl() + url;
        }else if(url.indexOf(urlManager.getProtocol()) == -1){
            url = urlManager.getProtocol() + ":"+ url;
        }

        try{
            Document doc = Jsoup.connect(url.trim()).get();
            Thread.sleep(1300);
            Elements links = doc.getElementsByTag("a");
            for (Element link : links){
                urlStr = link.attr("href");
                // host or 相対パス
                if(urlStr.indexOf(urlManager.getHost()) == (urlManager.getProtocol().length() + 3) || (urlStr.indexOf("/") == 0 && urlStr.indexOf("/", 1) != 1) ){
                    urlSet.add(urlStr);
                }
            }

            System.out.println("GetURLs:" + urlSet.size());
        }catch(IOException e){
        }catch (InterruptedException e){}

    }

    /*****  Jsoup 利用(ここまで) ****/

}
