import java.io.*;
import java.util.*;

public class FileParser {
    public Item[] readFile(String s) throws FileNotFoundException{
        File f = new File(s);
        if(!f.exists()){
            Item[] items = new Item[1];
            items[0] = new Item(-1,-1,-1);
            return items;
        }
        Scanner sc = new Scanner(f);
        int total = sc.nextInt();
        Item[] items = new Item[total];
        int count = 0;
        while(count < total) {
            items[count++] = new Item(sc.nextInt(), sc.nextInt(), sc.nextInt());
        }
        int capacity = sc.nextInt();
        for(int i = 0;i<items.length;i++){
            items[i].setCapacity(capacity);
        }
        return items;
    }
}
