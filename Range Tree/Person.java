package trees;
public class Person {
    private String name;
    private int xCoordinate;
    private int yCoordinate;


    public Person(String name, int xCoordinate, int yCoordinate) {
        this.name = name;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }
    public int getXCoordinate() {
        return xCoordinate;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }
    public boolean compareX(Person personOne)
    {
        return this.getXCoordinate()>personOne.getXCoordinate();
    }

    public boolean compareY(Person person)
    {
        return this.getYCoordinate()>person.getYCoordinate();
    }
    public boolean equals(Person person)
    {
        return this.getXCoordinate()== person.getXCoordinate();
    }
    @Override
    public String toString()
    {
        return name+"("+xCoordinate+","+yCoordinate+")";
    }
}
