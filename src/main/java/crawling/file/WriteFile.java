package crawling.file;

import crawling.Main;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

/**
 * Created by midori on 2016/05/05.
 */
public class WriteFile {
    public void writeUrlSetFile(Set<String> urlSet, String fileName, String url){
        Path filepath = Paths.get("data/urlset/" + fileName + ".txt");
        int excCount = 0;
        int EXCLIMIT = 3;
        while(true){
            try(BufferedWriter bw = Files.newBufferedWriter(filepath)){
                bw.write(url);
                bw.write(Main.ctrl);
                for (String inUrl : urlSet) {
                    bw.write(URLDecoder.decode(inUrl, StandardCharsets.UTF_8.name()));
                    bw.write(Main.ctrl);
                }
                bw.flush();

                Main.newLine();
                System.out.println("------ 書き込み完了 ------");
                System.out.println("ファイル名 : " + fileName + ".txt");
                System.out.println("TotalURLs:" + urlSet.size());
                System.out.println("--------------------------");
                break;
            }catch (IOException e){
                filepath = Paths.get("temporary.txt");
                excCount++;
                if(excCount > EXCLIMIT){
                    System.out.println("書き込みに失敗しました");
                    break;
                }
            }
        }
    }
}
