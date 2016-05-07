package crawling.selector;

import crawling.Main;
import crawling.file.WriteFile;
import crawling.input.Crawl;
import crawling.input.InputURL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

/**
 * Created by midori on 2016/05/05.
 */
public class CrawlUrl extends Selector{
    @Override
    public void select() {
        int hierarchy = 0;
        int MAXHIERARCHY = 3;
        String line = "";
        Main.newLine();
        System.out.println("■ URLを探索します");
        System.out.println("調べる階層を選択してください");
        System.out.println("※2以上は時間がかかります");
        Main.newLine();
        System.out.println("1 ~ " + MAXHIERARCHY +" を入力");
        System.out.print(">");

        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
            while(true){
                line = br.readLine();

                if(line == null){
                    throw new IOException();
                }

                for(int i = 1; i <= MAXHIERARCHY; i++){
                    if(line.equals(String.valueOf(i))){
                        hierarchy = i;
                        break;
                    }
                }

                if(hierarchy <= 0 || hierarchy > MAXHIERARCHY){
                    System.out.println("1 ~ " + MAXHIERARCHY + "の数値を再入力してください");
                    System.out.print(">");
                    continue;
                }

                break;
            }

            InputURL input = new InputURL();
            URL url = null;
            try {
                url = new URL(input.inputURL());
            }catch (MalformedURLException e){

            }
            Crawl crawl = new Crawl(url.toString(), url.getHost(), url.getProtocol());
            Set<String> urlSet = crawl.getUrlSet(hierarchy);

            WriteFile write = new WriteFile();
            write.writeUrlSetFile(urlSet, url.getHost(), url.toString());

            return;

        }catch (IOException e){
            e.printStackTrace();
            return;
        }
    }
}
