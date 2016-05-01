package crawling.URL;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by midori on 2016/04/29.
 */
public class InputURL {
    private URLFormatter format;

    public InputURL(){
        format = new URLFormatter();
    }

    public String inputURL(){
        StringBuilder url = null;
        int URLMAXLENGTH  = 200;

        System.out.println("URLを入力してください");
        System.out.print(">");
        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {

            while (true){
                try{
                    url = new StringBuilder(br.readLine().trim());
                    if (url == null || url.length() == 0 || url.length() > URLMAXLENGTH) {
                        throw new IOException();
                    }

                    if(isExistURL(url.toString())){
                        format.removeChildren(url);
                        break;
                    }else{
                        throw new IOException();
                    }
                }catch (IOException e){
                    System.out.println("再入力してください");
                    System.out.print(">");
                }
            }

        }catch(IOException e){
            System.out.println("システムを終了します");
            System.exit(1);
        }

        return url.toString();

    }


    public boolean isExistURL(String urlstr){
        HttpURLConnection conn = null;
        int status = 0;
        try{
            URL url = new URL(urlstr);
            if(urlstr.indexOf("http:") == 0){
                conn = (HttpURLConnection) url.openConnection();
            }else if(urlstr.indexOf("https:") == 0){
                conn = (HttpsURLConnection) url.openConnection();
            }else{
                return false;
            }

            conn.setRequestMethod("HEAD");
            conn.connect();
            status = conn.getResponseCode();
            conn.disconnect();
        }catch (MalformedURLException e){
            return false;
        }catch (IOException e){
            return false;
        }

        if(status == HttpURLConnection.HTTP_OK){
            return true;
        }else {
            return false;
        }
    }
}
