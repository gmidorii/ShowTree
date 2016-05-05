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
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();

        // URL 取得
//        InputURL input = new InputURL();
//        String url = input.inputURL();
//
//
//        URL rootUrl = new URL(url);
//        String host = rootUrl.getHost();
//        String protocol = rootUrl.getProtocol();
//        URLFormatter urlformat = new URLFormatter();
//        Crawl crawl = new Crawl(url, host, protocol);

        // File input
        InputFile inFile = new InputFile();
        List<String> urlList = new ArrayList<>();
        inFile.inputFile("data/urlset/test.txt", urlList);

        URL url = new URL(urlList.get(0));
        String host = url.getHost();
        String protocol = url.getProtocol();
        urlList.remove(0);

        //Jsoup利用
//        Set<String> urlSet = crawl.getUrlSet(url, 0);

        // nodelist 作成
        NodeList nodeList = NodeList.generateNodeList(host);
        Node rootNode = nodeList.getHost();
        NodeFormatter nodeFormat = new NodeFormatter(rootNode);
        MakeUrlSet make = new MakeUrlSet(host, protocol);

//        Set<String> nodeUrlSet = make.getUrlSet(url, 3);
//        format.addUrlNodeList(nodeUrlSet);



        nodeFormat.addUrlNodeList(urlList);

        WriteTree.writeTree(nodeList.getNodeList(), CONSOLE, "");
        WriteTree.writeTree(nodeList.getNodeList(), TXT, "data/tree2.txt");

        long end = System.currentTimeMillis();
        printTime(start, end, "AllTime");
    }

    public static void printTime(long s, long e, String tag){
        System.out.println("---------------------------");
        System.out.println(tag + ": " + (e - s) + "ms");
        System.out.println("---------------------------");
    }


}
