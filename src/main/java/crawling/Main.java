package crawling;

import crawling.selector.CrawlUrl;
import crawling.selector.Selector;
import crawling.selector.ShowTree;

import java.io.*;


/**
 * Created by midori on 2016/04/19.
 */
public class Main {
    public static String ctrl =  System.lineSeparator();
    public static void main(String[] args){
        long start = System.currentTimeMillis();
        System.out.println("============================");
        System.out.println("||        ShowTree        ||");
        System.out.println("============================");
        newLine();
        System.out.println("【システム内容】");
        System.out.println("URLのリストから、パス構造を取得するシステムです");
        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
            while(true){
                newLine();
                newLine();
                System.out.println("■ どちらを行いますか？(スタート)");
                newLine();
                System.out.println("1.URLリストを取得");
                System.out.println("2.パスツリーを取得");
                System.out.println("(終了の場合は、「exit」を入力)");
                newLine();
                System.out.println("1 or 2 or exit を入力");
                System.out.print(">");

                String in = br.readLine();

                if(in.equals("exit")){
                    System.out.println("システムを終了します");
                    break;
                }

                if(!in.equals("1") && !in.equals("2")){
                    System.out.println("ERROR : 1 or 2 を入力してください");
                    newLine();
                    continue;
                }


                Selector choice = null;
                if(in.equals("1")){
                    choice = new CrawlUrl();
                }else if(in.equals("2")){
                    choice = new ShowTree();
                }

                choice.select();

            }
        }catch (IOException e){
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        printTime(start, end, "AllTime");
    }

    public static void printTime(long s, long e, String tag){
        newLine();
        System.out.println("---------------------------");
        System.out.println(tag + ": " + (e - s) + "ms");
        System.out.println("---------------------------");
        newLine();
    }

    public static void newLine(){
        System.out.print(ctrl);
    }
}
