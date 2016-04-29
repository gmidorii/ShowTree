package crawling;

import crawling.output.WriteTree;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.Pack200;

import static crawling.output.OutputFormat.CONSOLE;
import static crawling.output.OutputFormat.TXT;


/**
 * Created by midori on 2016/04/19.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();

        String url = "http://www.navitime.co.jp/";
//        String url = "http://texchg.com/archives/category/textbook";
        URL rootUrl = new URL(url);
        final String host = rootUrl.getHost();
        URLFormatter urlformat = new URLFormatter();
        // nodelist 作成
        NodeList nodeList = NodeList.generateNodeList(rootUrl.getHost());
        Node rootNode = nodeList.getHost();
        NodeFormatter format = new NodeFormatter(rootNode);
        MakeUrlSet make = new MakeUrlSet(host);

        Set<String> nodeUrlSet = make.getUrlSet(url, 4);
        format.addUrlNodeList(nodeUrlSet);

        WriteTree.writeTree(nodeList.getNodeList(), CONSOLE, "");
        WriteTree.writeTree(nodeList.getNodeList(), TXT, "data/tree.txt");


        long end = System.currentTimeMillis();
        System.out.println("---------------------------");
        System.out.println("Time: " + (end - start) + "ms");
        System.out.println("---------------------------");

    }

    public static void crawlUrl(String url, String host, List<StringBuffer> htmlList){
        Crawl crawl = new Crawl(url, host);
        StringBuffer sb = crawl.HtmlToString();
        htmlList.add(sb);
    }


    public static void printlist(Set<String> list){
        for (String s : list) {
            System.out.println(":" + s);
        }
    }



}
