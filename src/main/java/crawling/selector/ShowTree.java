package crawling.selector;

import crawling.Main;
import crawling.file.InputFile;
import crawling.input.MakeUrlSet;
import crawling.input.URLManager;
import crawling.nodes.Node;
import crawling.nodes.NodeFormatter;
import crawling.nodes.NodeList;
import crawling.output.OutputFormat;
import crawling.output.WriteTree;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static crawling.output.OutputFormat.CONSOLE;
import static crawling.output.OutputFormat.TXT;

/**
 * Created by midori on 2016/05/05.
 */
public class ShowTree extends Selector {
    @Override
    public void select() throws IOException{
        Main.newLine();
        File dir = new File("data/urlset");
        File files[] = dir.listFiles();
        String in = "";
        int fileNum = 0;

        // File input
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String regexFile = "[0-" + (files.length - 1) + "]";
        while (true){
            System.out.println("■ 取得したいファイルを選択してください");
            for (int i = 0; i < files.length; i++) {
                System.out.println(i + "." + files[i].getName());
            }
            Main.newLine();
            System.out.println("0 ~ " + (files.length - 1) + " を入力");
            System.out.print(">");

            in = br.readLine();

            if(Pattern.matches(regexFile, in)) {
                break;
            }

            Main.newLine();
            System.out.println("※指定する数を入力してください");
            Main.newLine();
        }

        fileNum = Integer.parseInt(in);


        String filepath = files[fileNum].toString();
        InputFile inFile = new InputFile();
        List<String> urlList = new ArrayList<>();
        inFile.inputFile(filepath, urlList);

        URLManager urlManager = new URLManager(urlList.get(0));
        urlList.remove(0);

        // nodelist 作成
        NodeList nodeList = NodeList.generateNodeList(urlManager.getHost());
        Node hostNode = nodeList.getHost();
        NodeFormatter nodeFormat = new NodeFormatter(hostNode);
        nodeFormat.addUrlNodeList(urlList);


        OutputFormat outputFormat;
        while(true){
            String regexOut = "[0-" + OutputFormat.values().length + "]";
            Main.newLine();
            System.out.println("■ 出力形式を選択してください");
            int number = 0;
            for(OutputFormat value: OutputFormat.values()){
                System.out.println(number + ". " + value.getName());
                number++;
            }

            Main.newLine();
            System.out.println("0 ~ " + OutputFormat.values().length + " を入力");
            System.out.print(">");
            in = br.readLine();

            if(in == null){
                continue;
            }

            if(Pattern.matches(regexOut, in)){
                outputFormat = OutputFormat.values()[Integer.parseInt(in)];
                break;
            }
        }

        WriteTree.writeTree(nodeList.getNodeList(), outputFormat, urlManager.getHost());

    }
}
