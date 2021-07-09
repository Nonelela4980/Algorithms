package trees;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class RangeTree {
    Node RangeTree;
    int count;
    int countNodes;
    public RangeTree()
    {   count=0;
        RangeTree=null;
        buildTreeRangeTree();
        testData();
    }

    private void InsertX(Node root,Person newPerson)
    {
        if(root==null)
        {
            RangeTree=new Node(newPerson);
            return;
        }

            Person person=(Person)root.getData();
        //if the xValue already exist in the tree
            if(person.equals(newPerson))
            {
                if(root.getInnerTree()==null)
                {
                    root.setInnerTree(new Node(newPerson));
                    countNodes++;
                }
                else
                {
                    insertY(root.getInnerTree(),newPerson);
                }
            }
            else
                if(person.compareX(newPerson))  //if root<newPerson
                {
                    if(root.getLeftChild()==null)
                    {
                        root.setLeftChild(new Node(newPerson));
                        countNodes++;
                    }
                    else
                        InsertX(root.getLeftChild(),newPerson);
                }
            else
            {
                if(root.getRightChild()==null)
                    root.setRightChild(new Node(newPerson));
                else
                    InsertX(root.getRightChild(),newPerson);
            }
    }

    private void insertY(Node start,Person newPerson)
    {
        Person curPerson=(Person)start.getData();
        if(curPerson.compareY(newPerson))
        {

            if(start.getLeftChild()==null)
            {
                start.setLeftChild(new Node(newPerson));
                countNodes++;
            }
            else
                insertY(start.getLeftChild(),newPerson);
        }
        else
        {
            if(start.getRightChild()==null)
            {
                start.setRightChild(new Node(newPerson));
                countNodes++;
            }
            else
                insertY(start.getRightChild(),newPerson);
        }
    }

    public void buildTreeRangeTree()  {

        try{
            BufferedReader in = new BufferedReader(new FileReader(new File("resources/Locations.txt")));
            while(in.ready())
            {
                String S = in.readLine();
                StringTokenizer line = new StringTokenizer(S,",");

                String name = line.nextToken();
                String x = line.nextToken();
                String y=line.nextToken();

                Person person=new Person(name,Integer.parseInt(x),Integer.parseInt(y));
                InsertX(RangeTree,person);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void testData()
    {
        try
        {
            BufferedReader in = new BufferedReader(new FileReader(new File("resources/TestData.txt")));
            int i=1;
            while(in.ready())
            {
                String S = in.readLine();
                StringTokenizer line = new StringTokenizer(S,",");

                count=0;
                int xOne = Integer.parseInt(line.nextToken());
                int xTwo=Integer.parseInt(line.nextToken());

                int yOne=Integer.parseInt(line.nextToken());
                int yTwo=Integer.parseInt(line.nextToken());
                System.out.println("*********************Test"+i+"*********************");
                System.out.println("Range: "+xOne+","+xTwo+","+yOne+","+yTwo);
                rangeSearchFunction(RangeTree,xOne,xTwo,yOne,yTwo);
                System.out.println();
                System.out.println("count: "+count);
                i++;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void rangeSearchFunction(Node start,int xOne,int xTwo,int yOne,int yTwo)
    {
        if(start==null)
            return;

        Person curPeron=(Person) start.getData();

        if(curPeron.getXCoordinate()>=xOne && curPeron.getXCoordinate()<=xTwo)
        {
            if(curPeron.getYCoordinate()>=yOne && curPeron.getYCoordinate()<=yTwo)
            {
                System.out.print(curPeron+" ");
                System.out.println();
                count++;
            }
            rangeSearchOnSubtree(start.getInnerTree(),yOne,yTwo);
        }
        rangeSearchFunction(start.getLeftChild(),xOne,xTwo,yOne,yTwo);
        rangeSearchFunction(start.getRightChild(),xOne,xTwo,yOne,yTwo);
    }

    public void rangeSearchOnSubtree(Node start,int yOne,int yTwo)
    {
        //Range searches on the subtree
        if(start==null)
            return;

        Person curPeron=(Person) start.getData();
        if(curPeron.getYCoordinate()>=yOne && curPeron.getYCoordinate()<=yTwo)
        {
            System.out.print(curPeron+" ");
            count++;
            rangeSearchOnSubtree(start.getLeftChild(),yOne,yTwo);
            rangeSearchOnSubtree(start.getRightChild(),yOne,yTwo);
        }
        else
            if(curPeron.getYCoordinate()>yTwo)
            {
                rangeSearchOnSubtree(start.getLeftChild(),yOne,yTwo);
            }
        else
            {
                rangeSearchOnSubtree(start.getRightChild(),yOne,yTwo);
            }
    }
}
