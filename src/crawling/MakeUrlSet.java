package crawling;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by midori on 2016/04/24.
 */
public class MakeUrlSet {
    private String host;
    private int LASTHIERARCHY = 1;

    public MakeUrlSet(String host) {
        this.host = host;
    }

    public Set<String> getUrlSet(String url, int hierarchy){
        Set<String> nodeUrlSet = new HashSet<>();
        Crawl crawl = new Crawl(url, host);
        URLFormatter urlformat = new URLFormatter();
        StringBuffer sburl = new StringBuffer("");
        Set<String> urlSet = crawl.generateUrlSet(crawl.HtmlToString());
        for (String sturl : urlSet) {
            sburl.setLength(0);
            sburl.append(sturl);
            urlformat.removeUnnecessaryPart(sburl, host);
            if(sburl.length() != 0){
                nodeUrlSet.add(sburl.toString());
            }
        }
        System.out.println(nodeUrlSet.size());
        if(hierarchy > LASTHIERARCHY){
            hierarchy--;
            getUrlSet(url, hierarchy, urlSet, nodeUrlSet);
        }

        return nodeUrlSet;
    }

    public void getUrlSet(String url, int hierarchy, Set<String> urlSet, Set<String> nodeUrlSet){
        List<StringBuffer> htmlList = new ArrayList<>();
        urlSet.parallelStream().forEach(setUrl -> crawlUrl(setUrl, htmlList));
        Set<String> newUrlSet = new HashSet<>();
        Crawl crawl = new Crawl(url, host);
        URLFormatter urlFormat = new URLFormatter();
        for(StringBuffer html: htmlList){
            newUrlSet = crawl.generateUrlSet(html);
            addUrlSet(newUrlSet, nodeUrlSet, urlFormat);
        }

        System.out.println(nodeUrlSet.size());
        if(hierarchy > LASTHIERARCHY){
            hierarchy--;
            getUrlSet(url, hierarchy, newUrlSet, nodeUrlSet);
        }

        return;
    }

    public void crawlUrl(String url, List<StringBuffer> htmlList){
        Crawl crawl = new Crawl(url, host);
        StringBuffer sb = crawl.HtmlToString();
        htmlList.add(sb);
    }

    public String cleanUrl(String url, URLFormatter urlFormat){
        StringBuffer sbUrl = new StringBuffer(url);
        urlFormat.removeUnnecessaryPart(sbUrl, host);
        return sbUrl.toString();
    }

    public void addUrlSet(Set<String> urlSet, Set<String> nodeUrlSet, URLFormatter urlFormat){
        for (String url : urlSet) {
            String addUrl = cleanUrl(url, urlFormat);
            if(addUrl.length() != 0){
                nodeUrlSet.add(addUrl);
            }
        }
    }

}
