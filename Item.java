public class Item {
    public int weight;
    public int value;
    public double ratio;
    public int index;
    public int capacity;
    public int height;
    public double upperBound;
    public char[] itemsPicked;

    public Item(int i,double v, double w){
        this.weight = (int)w;
        this.value =(int) v;
        if(v == 0 && w == 0){ this.ratio = 0; }
        else{ this.ratio = v/w;}
        this.index = i;
    }
    public Item(int v, int w, int h, double ub,char[] c, int n){
        this.weight = w;
        this.value = v;
        this.height = h;
        this.itemsPicked = new char[n];
        this.upperBound = ub;
        System.arraycopy(c,0,this.itemsPicked,0,n);
    }
    public void setHeight(int i){
        this.height = i;
    }
    public int getHeight(){
        return this.height;
    }

    public int getIndex(){
        return this.index;
    }
    public int getWeight(){
        return this.weight;
    }
    public int getValue(){
        return this.value;
    }
    public double getRatio(){
        return this.ratio;
    }
    public void setCapacity(int capacity){
        this.capacity = capacity;
    }
    public int getCapacity(){
        return this.capacity;
    }
    public void setValue(int v){
        this.value = v;
    }
    public void setWeight(int w){
        this.weight = w;
    }
    public void setBound(double b){
        this.upperBound = b;
    }

    public double getBound(){
        return this.upperBound;
    }
}
