import java.io.FileNotFoundException;
public class Main {
    public static void errorMessage(){
        System.out.println("Error, function usage incorrect "+
                           "\nUsage: java Main ___filename___ __toggle-option__"+
                           "\nToggle option: \n1 = Brute Force\n2 = Greedy\n3 = Dynamic\n4 = Branch and Bound");
    }
    public static void main(String[] args) throws FileNotFoundException {
        if(args.length != 2) {
            errorMessage();
            return;
        }
        FileParser f = new FileParser();
        KnapSack k = new KnapSack();
        Item[] items = f.readFile(args[0]);
        if(items[0].value == -1 && items[0].weight == -1){
            errorMessage();
            return;
        }
        if(args[1].compareTo("1") == 0)
            k.bruteForceKnapSack(items);
        else if(args[1].compareTo("2") == 0)
            k.greedyKnapSack(items);
        else if(args[1].compareTo("3") == 0)
            k.dynamicKnapSack(items);
        else if(args[1].compareTo("4") == 0)
            k.branchAndBoundKnapSack(items);
        else
            errorMessage();
    }
}
