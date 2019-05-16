import java.util.Comparator;
public class ItemComparatorReverse implements Comparator<Item> {
        public int compare(Item i1, Item i2){
            if(i1.getRatio() < i2.getRatio()){
                return 1;
            }
            else if(i1.getRatio() > i2.getRatio()){
                return -1;
            }
            else if(i1.getRatio() == i2.getRatio() && i1.value > i2.value){
                return -1;
            }
            else if(i1.getRatio() == i2.getRatio() && i1.value < i2.value){
                return 1;
            }
            return 0;
        }
}

