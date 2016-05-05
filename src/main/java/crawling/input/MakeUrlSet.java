package crawling.input;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by midori on 2016/04/24.
 */
public class MakeUrlSet {
    private String host;
    private String protocol;
    private StringBuffer hostUrl = new StringBuffer();
    private int LASTHIERARCHY = 0;

    public MakeUrlSet(String host, String protocol) {
        this.host = host;
        this.protocol = protocol;
        this.hostUrl.append(protocol);
        this.hostUrl.append("://");
        this.hostUrl.append(host);
    }

    public Set<String> getUrlSet(String url, int hierarchy){
        Set<String> resultUrlSet = new HashSet<>();
        Crawl crawl = new Crawl(url, host, protocol);
        URLFormatter urlFormat = new URLFormatter();
        StringBuffer sbUrl = new StringBuffer("");

        // Crawling
        Set<String> urlSet = crawl.generateUrlSet(crawl.HtmlToString());

        // url cleaning
        for (String stUrl : urlSet) {
            sbUrl.setLength(0);
            sbUrl.append(stUrl);
            urlFormat.removeUnnecessaryPart(sbUrl, host);
            if(sbUrl.length() != 0){
                resultUrlSet.add(sbUrl.toString());
            }
        }

        System.out.println(resultUrlSet.size());

        // 下の階層へ潜る
        if(hierarchy > LASTHIERARCHY){
            hierarchy--;
            getUrlSet(url, hierarchy, urlSet, resultUrlSet);
        }

        return resultUrlSet;
    }

    public void getUrlSet(String url, int hierarchy, Set<String> urlSet, Set<String> resultUrlSet){
        List<StringBuffer> htmlList = new ArrayList<>();

        // urlのhtmlをパースし、htmllistに入れる
        urlSet.parallelStream().forEach(setUrl -> crawlUrl(setUrl, htmlList));

        // htmlをスクレイピングしurlSetを作りresultUrlSetに入れる
        Set<String> newUrlSet = new HashSet<>();
        Crawl crawl = new Crawl(url, host, protocol);
        for(StringBuffer html: htmlList){
            newUrlSet = crawl.generateUrlSet(html);
            addUrlSet(newUrlSet, resultUrlSet);
        }

        System.out.println(resultUrlSet.size());
        if(hierarchy > LASTHIERARCHY){
            hierarchy--;
            getUrlSet(url, hierarchy, newUrlSet, resultUrlSet);
        }
    }

    public void crawlUrl(String url, List<StringBuffer> htmlList){
        Crawl crawl = null;
        //相対パスの際
        if(url.indexOf(host) != 0){
            StringBuffer newUrl = new StringBuffer(url);
            newUrl.insert(0, hostUrl);
            crawl = new Crawl(newUrl.toString(), host, protocol);
        }else{
            crawl = new Crawl(url, host, protocol);
        }

        StringBuffer sb = crawl.HtmlToString();
        htmlList.add(sb);
    }

    public String cleanUrl(String url, URLFormatter urlFormat){
        StringBuffer sbUrl = new StringBuffer(url);
        urlFormat.removeUnnecessaryPart(sbUrl, host);
        return sbUrl.toString();
    }

    public void addUrlSet(Set<String> urlSet, Set<String> nodeUrlSet){
        URLFormatter urlFormat = new URLFormatter();
        for (String url : urlSet) {
            String addUrl = cleanUrl(url, urlFormat);
            if(addUrl.length() != 0){
                nodeUrlSet.add(addUrl);
            }
        }
    }

}
