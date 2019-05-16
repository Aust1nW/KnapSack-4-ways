import java.util.Comparator;

public class ItemComparator implements Comparator<Item> {
    public int compare(Item i1, Item i2){
        if(i1.getBound() < i2.getBound()){
            return 1;
        }
        else if(i1.getBound() > i2.getBound()){
            return -1;
        }
        return 0;
    }
}
