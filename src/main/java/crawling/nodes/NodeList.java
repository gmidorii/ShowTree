package crawling.nodes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by midori on 2016/04/22.
 */
public class NodeList {
    private static NodeList nodeListInstance = null;
    private List<Node> nodeList = new ArrayList<>();

    private NodeList(String host){
        Node hostNode = new Node(host);
        hostNode.setParentNode(null);
        addNodeList(hostNode);
    }

    public static synchronized NodeList generateNodeList(String host){
        if(nodeListInstance == null){
            nodeListInstance = new NodeList(host);
        }

        return nodeListInstance;
    }

    public void addNodeList(Node node){
        nodeList.add(node);
    }

    public Node isNode(Node node){
        for (Node nodeInList : nodeList) {
            if(nodeInList.equals(node)){
                return nodeInList;
            }
        }
        return null;
    }


    public Node getHost(){
        return this.nodeList.get(0); // hostは必ず最初に入る
    }

    public List<Node> getNodeList(){
        return this.nodeList;
    }

    // test code
    public void printNodeList(){
        for (Node node : nodeList) {
            System.out.print(node.getParentNode() + "-");
            System.out.print(node);
            System.out.print("-");
            System.out.println(node.getChildrenNode());
        }
        System.out.println(nodeList.size());
    }
}
