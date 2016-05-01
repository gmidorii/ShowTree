package crawling.output;

import crawling.Node.Node;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by midori on 2016/04/24.
 */
public class WriteTreeFormatTxt implements WriteTreeFormat {
    private List<Node> nodeList;
    private String filePath;

    public WriteTreeFormatTxt(List<Node> nodeList, String filePath) {
        this.nodeList = nodeList;
        this.filePath = filePath;
    }

    @Override
    public void writeNode() {
        Path path = Paths.get(filePath);
        Node rootNode = nodeList.get(0);
        String LINEFEEDCODE = "\n";
        try (BufferedWriter bw = Files.newBufferedWriter(path)) {
            bw.write(rootNode.getNodeName() + LINEFEEDCODE);
            for (Node childNode : rootNode.getChildrenNode()) {
                bw.write("|-" + childNode.getNodeName() + LINEFEEDCODE);
                recurChildNodeWriteFile(childNode, 1, bw);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void recurChildNodeWriteFile(Node parentNode, int count, BufferedWriter bw) throws IOException{
        int i = 0;
        int firstCount = count;
        int nodeCount = 0;
        int NODEMAXNUM = 20;
        String LINEFEEDCODE = "\n";
        String bar = "";
        if(parentNode.getChildrenNode().size() > 0){
            for(Node childNode: parentNode.getChildrenNode()) {
                while(i < count){
                    bar += "  ";
                    i++;
                }
                bar += "|-";
                bw.write(bar + childNode.getNodeName() + LINEFEEDCODE);
                count++;
                recurChildNodeWriteFile(childNode, count, bw);
//
//                if(nodeCount > NODEMAXNUM){
//                    bw.write(bar + "." + LINEFEEDCODE);
//                    bw.write(bar + "." + LINEFEEDCODE);
//                    bw.write(bar + "." + LINEFEEDCODE);
//                    break;
//                }
                nodeCount++;

                // initialize
                bar = "";
                i = 0;
                count = firstCount;
            }
        }
    }
}
