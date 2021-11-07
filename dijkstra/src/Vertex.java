public class Vertex implements Comparable<Vertex> {
    private String label;
    private int cost;
    private boolean isVisited;
    public   Vertex next;

    public Vertex(String label, int cost) {
        this.label = label;
        this.cost = cost;
        isVisited=false;
        next=null;
    }

    public String getLabel() {
        return label;
    }

    public int getCost() {
        return cost;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setCost(int newCost)
    {
        cost=newCost;
    }

    public void setVisited(boolean newVal)
    {
        isVisited=newVal;
    }
    public void setLabel(String newLabel)
    {
        label=newLabel;
    }

    @Override
    public int compareTo(Vertex iTh) {
        if(this.cost>iTh.cost)
            return 1;
        if(this.cost<iTh.cost)
            return -1;
        else
            return 0;
    }
    @Override
    public  String toString()
    {
        return "[cost= "+cost+", "+"Label = "+label+"]";
    }
}
