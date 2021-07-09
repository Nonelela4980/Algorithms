package trees;

public class Node {
    private Object data;
    private Node leftChild;
    private Node rightChild;
    private Node parent;
    private Node innerTree;

    public Node(Object  data)
    {
        this.data=data;
        parent=null;
        leftChild=null;
        rightChild=null;
        innerTree=null;
    }

    public Node getLeftChild(){
        return leftChild;
    }
    public Node getRightChild(){
        return rightChild;
    }
    public Node getInnerTree(){return innerTree;}

    public void setLeftChild(Node node)
    {
        leftChild=node;
        if (node!=null)
            node.setParentNode(this);
    }

    public void setRightChild(Node node)
    {
        rightChild=node;
        if(node!=null)
            node.setParentNode(this);
    }

    public void setParentNode(Node parent)
    {
        this.parent=parent;
    }

    public void setInnerTree(Node rootOfInnerTree)
    {
        innerTree=rootOfInnerTree;
        if(rootOfInnerTree!=null)
            rootOfInnerTree.setParentNode(this);
    }

    public Object getData()
    {
        return data;
    }

}
