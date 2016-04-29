package crawling;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by midori on 2016/04/23.
 */
public class Node {
    private String nodeName;
    private String parentNodeName;
    private Set<Node> childrenNode = new HashSet<>();

    public Node(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeName() {
        return nodeName;
    }
    public String getParentNode() {
        return parentNodeName;
    }
    public Set<Node> getChildrenNode() {
        return childrenNode;
    }

    public void setParentNode(String parentNodeName) {
        this.parentNodeName = parentNodeName;
    }

    public void appendChildrenNode(Node node){
        childrenNode.add(node);
    }

    public void copyChildrenNode(Node node){
        for (Node nodeChild : node.getChildrenNode()) {
            childrenNode.add(nodeChild);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        if (!nodeName.equals(node.nodeName)) return false;
        return parentNodeName != null ? parentNodeName.equals(node.parentNodeName) : node.parentNodeName == null;

    }

    @Override
    public int hashCode() {
        int result = nodeName.hashCode();
        result = 31 * result + (parentNodeName != null ? parentNodeName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return getNodeName();
    }
}

