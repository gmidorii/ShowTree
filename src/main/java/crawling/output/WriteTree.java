package crawling.output;

import crawling.Main;
import crawling.nodes.Node;

import java.io.IOException;
import java.util.List;

/**
 * Created by midori on 2016/04/24.
 */
public class WriteTree {

    public static void writeTree(List<Node> nodeList, OutputFormat format, String filename) throws IOException{
        WriteTreeFormat wFormat = null;
        switch (format){
            case CONSOLE:
                wFormat = new WriteTreeFormatConsole(nodeList);
                break;
            case TXT:
                wFormat = new WriteTreeFormatTxt(nodeList, "data/txt/" + filename + ".txt");
                break;
            case CSV:
                Main.newLine();
                System.out.println("ごめんなさい");
                System.out.println("まだ、CSV出力は実装されていません");
                return;
        }

        wFormat.writeNode();
    }
}
