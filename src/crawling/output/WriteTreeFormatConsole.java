package crawling.output;

import crawling.Node.Node;

import java.util.List;

/**
 * Created by midori on 2016/04/24.
 */
public class WriteTreeFormatConsole implements WriteTreeFormat {
    private List<Node> nodeList;

    public WriteTreeFormatConsole(List<Node> nodeList){
        this.nodeList = nodeList;
    }

    @Override
    public void writeNode() {
        Node rootNode = nodeList.get(0);
        System.out.println(rootNode.getNodeName());
        for(Node childNode: rootNode.getChildrenNode()){
            System.out.println("|-" + childNode);
            recurChildNode(childNode, 1);
        }

    }

    public void recurChildNode(Node parentNode, int count){
        int i = 0;
        int firstCount = count;
        int nodeCount = 0;
        int NODEMAXNUM = 10;
        String bar = "";
        if(parentNode.getChildrenNode().size() > 0){
            for(Node childNode: parentNode.getChildrenNode()) {
                while(i < count){
                    bar += "  ";
                    i++;
                }
                bar += "|-";
                System.out.println(bar + childNode);
                count++;
                recurChildNode(childNode, count);

                if(nodeCount > NODEMAXNUM){
                    System.out.println(bar + ".");
                    System.out.println(bar + ".");
                    System.out.println(bar + ".");
                    break;
                }
                nodeCount++;

                // initialize
                bar = "";
                i = 0;
                count = firstCount;
            }
        }
    }

}
