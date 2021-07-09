import java.util.HashMap;

public class Node {
        public String data;
        public Node nextNode;
        public Node headPointer; // points to the head of the set, if its a singleton head points to itself
                                // for the head it always points to the last node(provided the head is not a singleton)


    public  Node(String data)
    {
        this.data=data;
        nextNode=null;
        headPointer=null;
    }

    public  Node next()
    {
        return  nextNode;
    }

    public Node getHeadPointer()
    {
        return this.headPointer;
    }

    public  void  setNext(Node nextNode)
    {
        this.nextNode=nextNode;
    }

    public  void  setHeadPointer(Node head)
    {
        headPointer=head;
    }

    public  String value()
    {
        return  data;
    }



}
