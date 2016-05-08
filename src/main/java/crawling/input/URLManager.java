package crawling.input;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by midori on 2016/04/22.
 */
public class URLManager {
    private String url;
    private String host;
    private String protocol;
    private String hostUrl;

    public URLManager(String url){
        this.url = url;
        try {
            URL urlObj = new URL(url);
            this.host = urlObj.getHost();
            this.protocol = urlObj.getProtocol();
            this.hostUrl = protocol + "://" + host;
        }catch (MalformedURLException e){

        }
    }

    // Getter
    public String getUrl() {
        return url;
    }
    public String getHost() {
        return host;
    }
    public String getProtocol() {
        return protocol;
    }
    public String getHostUrl() {
        return hostUrl;
    }


    public String removeUnnecessaryPart(StringBuffer url){
        removeParameter(url);
        removeHead(url, host + "/");

        return url.toString();
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

    public void removeChildren(StringBuilder url){
        int index = -1;
        int SLASHCOUNT = 2;
        int count = 0;
        while(count < SLASHCOUNT){
            index = url.indexOf("/", index + 1);
            count++;
        }

        if( (index = url.indexOf("/", index + 1)) != -1){
            url.delete(index, url.length());
        }
    }

}
