package crawling.output;

import crawling.nodes.Node;

import java.util.List;

/**
 * Created by midori on 2016/04/24.
 */
public class WriteTree {

    public static void writeTree(List<Node> nodeList, OutputFormat format, String filePath){
        WriteTreeFormat wFormat = null;
        switch (format){
            case CONSOLE:
                wFormat = new WriteTreeFormatConsole(nodeList);
                break;
            case TXT:
                wFormat = new WriteTreeFormatTxt(nodeList, filePath);
                break;
            case CSV:
                break;
        }

        wFormat.writeNode();
    }
}
