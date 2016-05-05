package crawling.nodes;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by midori on 2016/04/23.
 */
public class Node {
    private String nodeName;
    private String parentNodeName;
    private Set<Node> childrenNode = new LinkedHashSet<>();

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
        childrenNode.add(node);
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

