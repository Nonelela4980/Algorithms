import javax.swing.text.Element;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class UnionFind {

    HashMap<String,Integer> keepNames;//stores names
    HashMap<Integer,SinglyLinkedList> sets;//stores the sets
    HashMap<Integer,Node> keepNodes;//stores nodes
    String testData;
    String Names;
    String friends;

    public UnionFind()
    {
        keepNames=new HashMap<>();
        sets=new HashMap<>();
        keepNodes=new HashMap<>();
        loadNames();
        makeConnections();
    }

    public  UnionFind(String NamesFile,String FriendsFile,String checkConnectionsFile)
    {
        Names=NamesFile;
        friends=FriendsFile;
        testData=checkConnectionsFile;
       //UnionFind a= new UnionFind();
        keepNames=new HashMap<>();
        sets=new HashMap<>();
        keepNodes=new HashMap<>();
        loadNames();
        makeConnections();
    }
    public static void main(String[] args)
    {
       UnionFind updated=new UnionFind("Task01/src/Names.txt","Task01/src/Friends.txt","Task01/src/TestData.txt");

       if(updated.find(updated.getNamePos("A")).equals(updated.find(updated.getNamePos("H"))))
        {
            System.out.println("I am connected");
        }
        else
        System.out.println("Not connected");
    }

    public int getNamePos(String name)
    {
        return keepNames.get(name);
    }

    public void readNames()
    {
        //reads test data
        try
        {
            BufferedReader in = new BufferedReader(new FileReader(testData));
            int i=1;

            while(in.ready())
            {

                String theString = in.readLine();
                StringTokenizer line = new StringTokenizer(theString,",");

                String FirstName = line.nextToken();
                String SecondName = line.nextToken();

                //theNamesBeingRead.add(new String[]{FirstName,SecondName});
                int posA=keepNames.get(FirstName);
                int posB=keepNames.get(SecondName);

                if(find(posA).equals(find(posB)))
                {

                    System.out.println(i+"."+FirstName+" "+SecondName+" are connected");
                }
                else
                    System.out.println(i+"."+FirstName+" "+SecondName+" are not connected");

                System.out.println();
                i++;
            }

        } catch (Exception e)
        {

            e.printStackTrace();
            System.exit(1);
        }
    }
    public void loadNames()
    {
        try
        {
            BufferedReader input = new BufferedReader(new FileReader(Names));
            int track= 0;

            while(input.ready())
            {
                String name=input.readLine();
                keepNames.put(name,track);
                Node element=new Node(name);
                makeSet(element,track);
                //Everytime a name is loaded from the textFile, a singleton is created by the makeSet method
                track++;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void makeConnections()
    {
        ArrayList<Integer[]> list = new ArrayList<>();
        loadNames();
            try
            {
                BufferedReader in = new BufferedReader(new FileReader(friends));

                while(in.ready())
                {

                    String theString = in.readLine();
                    StringTokenizer line = new StringTokenizer(theString,",");

                    String FirstName = line.nextToken();
                    String SecondName = line.nextToken();

                    int posA=keepNames.get(FirstName);
                    int posB=keepNames.get(SecondName);
                    list.add(new Integer[]{posA, posB});
                }

            } catch (Exception e)
            {

                e.printStackTrace();
                System.exit(1);
            }


            for(Integer[] friends : list){

                if(friends.length == 2)
                    if(!find(friends[0]).equals(find(friends[1]))){
                        union(sets.get(friends[0]),sets.get(friends[1]),friends[0],friends[1]);
                    }

            }

    }

    public void union(SinglyLinkedList A, SinglyLinkedList B,int updateA,int updateB)
    {
        //This method us pointer manipulation to make union of set A and Set B

        ArrayList<String> track=new ArrayList<>();
        int count=0;
        //Combine the sets
        if(A.Count()>=B.Count())
        {
            Node cur=B.getFirst();
            while (cur!=null)
            {
                track.add(cur.value());
                cur.setHeadPointer(A.getFirst());

                cur=cur.next();
                count++;

            }
            A.getFirst().setHeadPointer(B.getLast());
            A.getLast().setNext(B.getFirst());//the head of the list must point to the last node in the list( the headpointer(or tail) of the head points to the last element in the list
            A.setCount(count);//count is update for the provision of the added nodes since I was not using addNodes method which would do this

            upDatedAffectedSets(track,A); //updates the sets in the sets hashMap
        }

        else
        {
            Node cur=A.getFirst();
            while (cur!=null)
            {
                track.add(cur.value());
                cur.setHeadPointer(B.getFirst());
                cur=cur.next();
                count++;
            }

            B.getFirst().setHeadPointer(A.getLast());
            B.getLast().setNext(A.getFirst()); //the head of the list must point to the last node in the list( the headpointer(or tail) of the head points to the last element in the list
            B.setCount(count);//count is update for the provision of the added nodes since I was not using addNodes method which would do this
            upDatedAffectedSets(track,B); //updates the sets in the sets hashMap

        }
    }

   public void upDatedAffectedSets(ArrayList<String> list,SinglyLinkedList newSet)
    {
        for (String name:list)
        {
            int key=keepNames.get(name);
            sets.put(key,newSet);
        }
    }
    public String find(int pos)
    {
        //I did it this way because of the way my design works, the headPointer of the first element always points to the last one
        //so it won't give a head pointer similar to others in the same list this if statement is to make provision of that
        if(keepNodes.get(pos).value().equals(sets.get(pos).getFirst().value())) //if its the first element of the list
            return sets.get(pos).getFirst().value();
        else
            return keepNodes.get(pos).getHeadPointer().value();
    }

    public void makeSet(Node element,int pos)
    {

        SinglyLinkedList aSet=new SinglyLinkedList();
        aSet.addNode(element);
        sets.put(pos,aSet);
        keepNodes.put(pos,element);
    }


}
