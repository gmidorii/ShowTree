package crawling.input;

/**
 * Created by midori on 2016/04/22.
 */
public class URLFormatter {

    public String removeUnnecessaryPart(StringBuffer url, String host){
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
        int SLASHCOUNT = 3;
        int count = 0;
        while(count < SLASHCOUNT){
            index = url.indexOf("/", index + 1);
            count++;
        }
        url.delete(index + 1, url.length());
    }

}
