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
        InputURL input = new InputURL();
        String url = input.inputURL();
        URL rootUrl = new URL(url);
        String host = rootUrl.getHost();
        String protocol = rootUrl.getProtocol();
        URLFormatter urlformat = new URLFormatter();

        // nodelist 作成
        NodeList nodeList = NodeList.generateNodeList(rootUrl.getHost());
        Node rootNode = nodeList.getHost();
        NodeFormatter format = new NodeFormatter(rootNode);
        MakeUrlSet make = new MakeUrlSet(host, protocol);

        Set<String> nodeUrlSet = make.getUrlSet(url, 3);
        format.addUrlNodeList(nodeUrlSet);

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
