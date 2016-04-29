package crawling;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by midori on 2016/04/22.
 */
public class URLFormatter {

    public void removeUnnecessaryPart(StringBuffer url, String host){
        removeParameter(url);
        removeHead(url, host + "/");
    }

    public void removeParameter(StringBuffer url){
        // Listが0,スタートであるため -1
        int index = -1;
            if( (index = url.indexOf("?")) != -1){
                url.delete(index, url.length());
            }
    }

    public void removeHead(StringBuffer url, String removeString){
        // Listが0,スタートであるため -1
        int index = -1;
        if( (index = url.indexOf(removeString)) != -1){
            url.delete(0, index + removeString.length()); // 文字列の長さを考慮
        }

        if(url.indexOf("/") == 0){
            url.deleteCharAt(0);
        }
    }


    public List<String> removeExtraPart(List<String> urlList, String host){
        List<String> newUrlList = new ArrayList<>();
        for (String url : urlList) {
            String url1 = removeParameter(url);
            String url2 = removeHead(url1, host);
            newUrlList.add(url2);
        }

        return newUrlList;
    }

    public String removeParameter(String url){
        String newUrl = url;
        // Listが0,スタートであるため -1
        int index = -1;
        if( (index = url.indexOf("?")) != -1){
            newUrl = url.substring(0, index);
        }
        return newUrl;
    }

    public String removeHead(String url, String removeString){
        String newUrl = url;
        // Listが0,スタートであるため -1
        int index = -1;
        if( (index = url.indexOf(removeString)) != -1){
            newUrl = url.substring(index + removeString.length() + 1); // 文字列の長さを考慮
        }

        return newUrl;
    }

}
