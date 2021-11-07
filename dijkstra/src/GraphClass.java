

import org.w3c.dom.Node;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.StringTokenizer;


public class GraphClass {
    
    private HashMap<String, Integer> NameNumbers = new HashMap<String, Integer>();
    private ArrayList<String> Names = new ArrayList<String>();
    private ArrayList<String> sequence=new ArrayList<>();
    private PriorityQueue<Vertex> vertices=new PriorityQueue<>();
    private HashMap<Integer,Vertex> trackVertices=new HashMap<>();//will help locate vertices
    int[][] matrix;//adjacency matrix;
    //private ArrayList<String> sequence=new ArrayList<>();
    private int counting;
    Vertex furthest=new Vertex("",Integer.MIN_VALUE);
    String source;
    public GraphClass(String source)
    {
        this.source=source;
        counting=0;
        loadNames();
        matrix = new int[NameNumbers.size()][NameNumbers.size()];
        loadFriends();
        dijkstra(source);
    }


    public static void main(String[] args) {
        String source="Monica";
        GraphClass graph=new GraphClass(source);
        System.out.printf("1. The are %d with distance less 50km away from %s",graph.getCounting(),source);
        System.out.println();
        System.out.println("2. furthest :=>"+graph.getFurthest());
        System.out.println("3.---------------------------------------------------------------------------------------");
        System.out.println();
        graph.Sequences();
        System.out.println("---------------------------------------------------------------------------------------");

    }

    public void  Sequences()
    {
        UnionFind unionFind=new UnionFind("resources/Names.txt","resources/Friends2.txt","resources/Test.txt");
        try
        {
            BufferedReader in = new BufferedReader(new FileReader(new File("resources/Test.txt")));
            while(in.ready())
            {

                String name= in.readLine();
                Vertex vert=trackVertices.get(NameNumbers.get(name));
                if(unionFind.find(unionFind.getNamePos(source)).equals(unionFind.find(unionFind.getNamePos(name))))
                {
                    System.out.println(source+" to "+name+"= "+vert.getCost()+" km");
                    System.out.print("Sequence from "+source+ " to "+name+"==>" );
                    printSequence(name);
                }
                else {
                    System.out.println(source+" and "+name+" are not connected");
                }
                System.out.println();
                System.out.println();
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void loadFriends()
    {
        //Create a matrix with edge weights
        try
        {
            BufferedReader in = new BufferedReader(new FileReader(new File("resources/Friends2.txt")));
            while(in.ready())
            {
                String S = in.readLine();
                StringTokenizer line = new StringTokenizer(S,",");
                String Name1 = line.nextToken();
                String Name2 = line.nextToken();
                String weight=line.nextToken();
                int Name1Number = NameNumbers.get(Name1);
                int Name2Number = NameNumbers.get(Name2);

                matrix[Name1Number][Name2Number] = Integer.parseInt(weight);
                matrix[Name2Number][Name1Number] =Integer.parseInt(weight);  //This is not a directed graph so two entries are made in the table per edge
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
            BufferedReader in = new BufferedReader(new FileReader(new File("resources/Names.txt")));
            int counter = 0;
            while(in.ready())
            {
                String S = in.readLine();
                NameNumbers.put(S, counter);
                //NumbersNames.put(counter,S);
                Names.add(S);
                Vertex vert=new Vertex(S,Integer.MAX_VALUE);
                vertices.add(vert); //puts the vertices with infinity(max value in the priority queue)
                trackVertices.put(counter,vert);
                counter++;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }

public void dijkstra(String start)
{
    int keya=NameNumbers.get(start);
    Vertex source=trackVertices.get(keya);
    vertices.remove(source);//its remove from the priority queue
    source.setCost(0); //set the cost of the source vertex to be zero
    vertices.add(source);//the updated source vertex is added to the priority queue;

    while (!vertices.isEmpty())
    {
        Vertex minVertex=vertices.remove();
        if(minVertex.getCost()>=Integer.MAX_VALUE)
        {
            break;
        }
        updateAdjancentVertices(minVertex);
        int key=NameNumbers.get(start);
        trackVertices.remove(key);
        minVertex.setVisited(true);
        trackVertices.put(key,minVertex);
        sequence.add(minVertex.getLabel());

        if(minVertex.getCost()<50)
            counting++;
    }
    System.out.println("dijkstra's algorithm ran fine");

}

private void updateAdjancentVertices(Vertex vert)
{
    int row=NameNumbers.get(vert.getLabel());
    for (int i=0;i<matrix[row].length;i++)
    {
        if(matrix[row][i]!=0)
        {
            Vertex curVertex=trackVertices.get(i);
            if(vert.getCost()+matrix[row][i]<Math.abs(curVertex.getCost()) && !curVertex.isVisited())
            {
                curVertex.setCost(vert.getCost()+matrix[row][i]);
                //trackVertices.remove(i);
                //trackVertices.put(i,curVertex);//updated vertex to the back to the hash map in its original position
                vertices.remove(curVertex);
                vertices.add(curVertex);//the updated  vertex is added to the priority queue;
                //sequence.add(i,vert.getLabel());
                if(curVertex.getCost()>furthest.getCost())
                    furthest=curVertex;
            }
        }
    }
}
public void printSequence(String destination)
{

    for(String vert: sequence)
    {
        System.out.print(vert+"->");
        if(vert.equals(destination))
            return;
    }
}
public Vertex getFurthest()
{
    return furthest;
}
public int getCounting()
{
    return counting;
}
}
