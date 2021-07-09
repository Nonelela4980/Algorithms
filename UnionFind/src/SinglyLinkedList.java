public class SinglyLinkedList
{
   private Node head;
   private int count;

    public SinglyLinkedList() {

        head=null;
        count=0;
    }

    public void addNode(Node newNode)
    {
        //Node newNode=new Node(element);
        if(count==0)
        {

            newNode.setNext(head);
            head=newNode;
            //head.setHeadPointer(newNode);//points within itself...its the first and the last
            head.setHeadPointer(head);//points within itself...its the first and the last
            count++;
        }
        else {
            Node cur = head;
            while (cur.next()!= null) {
                cur = cur.next();
            }

            cur.setNext(newNode);
            newNode.setHeadPointer(head);
            head.setHeadPointer(newNode); //The node being inserted is the last node, so the head must point its second pointer to it
            count++;
        }
    }
    public int Count()
    {
        return count;
    }
    public Node getFirst()
    {
        return head;
    }
    public void setCount(int newCount)
    {
        count+=newCount;
    }
    public Node getLast()
    {
        Node cur=getFirst();
        while (cur.next()!=null)
            cur=cur.next();

        return cur;
    }


}
