package crawling.node;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by midori on 2016/04/23.
 */
public class NodeFormatter {
    private Node hostNode;

    public NodeFormatter(Node hostNode){
        this.hostNode = hostNode;
    }

    public void addUrlNodeList(List<String> urlList){
        String[] nodeArray;
        List<Node> nodeList;
        for (String url : urlList) {
            if (url.length() != 0){
                nodeArray = url.split("/");
                nodeList = createNodeList(nodeArray);
                registerParentNode(nodeList);
                registerChildrenNode(nodeList);
                addNodeList(nodeList);
            }
        }
    }

    public List<Node> createNodeList(String[] url){
        List<Node> nodeList = new ArrayList<>();
        for(String nodeName: url){
            if (nodeName.length() != 0){
                nodeList.add(new Node(nodeName));
            }
        }
        return nodeList;
    }

    public void registerParentNode(List<Node> nodeList){
        try {
            nodeList.get(0).setParentNode(hostNode.getNodeName());
        }catch (IndexOutOfBoundsException e){
            System.out.println(nodeList);
        }

        for(int i = 1; i < nodeList.size(); i++){
            nodeList.get(i).setParentNode(nodeList.get(i - 1).getNodeName());
        }
    }

    public void registerChildrenNode(List<Node> nodeList){
        hostNode.appendChildrenNode(nodeList.get(0));

        for(int i = 0; i < nodeList.size() - 1; i++){
            nodeList.get(i).appendChildrenNode(nodeList.get(i + 1));
        }
    }

    public void addNodeList(List<Node> nodeList){
        Node stockNode;
        NodeList stockList = NodeList.generateNodeList(hostNode.getNodeName());
        for (Node node : nodeList) {
            if( (stockNode = stockList.isNode(node)) == null){
                stockList.addNodeList(node);
            }else{
                // すでにListに入っている場合
                if(node.getChildrenNode().size() != 0){
                    for (Node childNode : node.getChildrenNode()) {
                        stockNode.copyChildrenNode(childNode);
                    }
                }
            }
        }
    }



}
