package crawling;

import crawling.file.InputFile;
import crawling.node.Node;
import crawling.node.NodeFormatter;
import crawling.node.NodeList;
import crawling.input.Crawl;
import crawling.input.InputURL;
import crawling.input.MakeUrlSet;
import crawling.input.URLFormatter;
import crawling.output.WriteTree;
import crawling.selector.CrawlUrl;
import crawling.selector.Selector;
import crawling.selector.ShowTree;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static crawling.output.OutputFormat.CONSOLE;
import static crawling.output.OutputFormat.TXT;


/**
 * Created by midori on 2016/04/19.
 */
public class Main {
    public static void main(String[] args){
        long start = System.currentTimeMillis();
        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
            while(true){
                System.out.println("どちらを行いますか？");
                System.out.println("1.URLリストを取得");
                System.out.println("2.パスツリーを取得");
                System.out.println("終了の場合は、「exit」を入力");
                System.out.println("1 or 2 or exit");
                System.out.print(">");

                String in = br.readLine();

                if(in.equals("exit")){
                    System.out.println("システムを終了します");
                    break;
                }

                if(!in.equals("1") && !in.equals("2")){
                    System.out.println("1 or 2 を入力してください");
                    System.out.print(">");
                    continue;
                }


                Selector choice = null;
                if(in.equals("1")){
                    choice = new CrawlUrl();
                }else if(in.equals("2")){
                    choice = new ShowTree();
                }

                choice.select();
                break;
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        printTime(start, end, "AllTime");
    }

    public static void printTime(long s, long e, String tag){
        System.out.println("---------------------------");
        System.out.println(tag + ": " + (e - s) + "ms");
        System.out.println("---------------------------");
    }


}
