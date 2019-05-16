import java.util.*;

public class KnapSack {

    //Brute Force
    public static void bruteForceKnapSack(Item[] items){
        int twoToTheN = (int)Math.pow(2,items.length);
        int [] tempAnswer = new int [items.length];
        int bestVal = 0,bestWeight = 0;
        int [] result = new int[items.length];
        for(int i = 0;i<twoToTheN;i++){
            int j = items.length-1;
            int tempW = 0,tempV = 0;
            while(tempAnswer[j] != 0 && j>0)
                tempAnswer[j--] = 0;
            tempAnswer[j] = 1;
            for(int k = 0;k<items.length;k++){
                if(tempAnswer[k] == 1){
                    tempW = tempW+items[k].getWeight();
                    tempV = tempV+items[k].getValue();
                }
            }
            if((tempV > bestVal) && (tempW <= items[0].getCapacity())){
                bestVal = tempV;
                bestWeight = tempW;
                for(int l = 0;l<tempAnswer.length;l++){
                    result[l] = 0;
                    if(tempAnswer[l] == 1)
                        result[l] = 1;
                }
            }
        }
        System.out.println();
        System.out.print("Using Brute force the best feasible solution found: ");
        System.out.print("Value: "+ bestVal + " Weight: "+ bestWeight+"\nItems: ");
        for(int i = 0;i<result.length;i++){
            if(result[i] == 1)
                System.out.print(items[i].getIndex()+ " ");
        }
        System.out.println("\n");

    }
    //Greedy
    public static void greedyKnapSack(Item [] items){
        Arrays.sort(items,new ItemComparatorReverse());
        int totalWeight=0,totalValue=0;
        int[] itemsPicked = new int[items.length];
        int k = 0;
        for(int i = 0;i<items.length;i++){
            if(totalWeight+items[i].getWeight() <= items[0].getCapacity()){
                itemsPicked[k++] = items[i].getIndex();
                totalWeight += items[i].getWeight();
                totalValue += items[i].getValue();
            }
        }
        Arrays.sort(itemsPicked);
        System.out.print("\nGreedy solution (not necessarily optimal): ");
        System.out.print("Value: "+ totalValue+ " Weight: "+ totalWeight+"\nItems: ");
        for(int m=0;m<itemsPicked.length;m++)
            if(itemsPicked[m] != 0)
                System.out.print(itemsPicked[m]+ " ");
        System.out.println("\n");
    }

    //DYNAMIC PROGRAMMING
    public static Item[] duplicateItems(Item [] items){
        //Helper function for dynamic, sets array of items to start at index 1
        Item newItem = new Item(-1,0, 0);
        Item[] newItems = new Item[items.length+1];
        newItems[0] = newItem;
        for(int i = 1;i<newItems.length;i++)
            newItems[i] = items[i-1];
        return newItems;
    }
    public static void dynamicKnapSack(Item [] items){
        int[][] opt = new int[items.length+1][items[0].getCapacity()+1];
        //Initialize the 2-D array such that the first row and column are set to 0
        for(int i = 0;i<items.length+1;i++)
            opt[i][0] = 0;
        for(int i = 0;i<items[0].getCapacity()+1;i++)
            opt[0][i] = 0;
        Item [] newItems = duplicateItems(items);
        //Now that the table is initialized, fill the table using the recurrence relation opt[i][j] = max(opt[i-1][j-Weight of i],opt[i-1][j]
        for(int i = 1;i<newItems.length;i++){
            for(int j=0;j<newItems[1].getCapacity()+1;j++){
                if(newItems[i].getWeight() > j)
                    opt[i][j] = opt[i-1][j];
                else
                    opt[i][j] = Math.max((opt[i-1][j-newItems[i].getWeight()]+newItems[i].getValue()),opt[i-1][j]);
            }
        }//Table is built
        int currentAnswer = opt[newItems.length-1][items[0].getCapacity()];
        ArrayList<Item> itemsPicked = new ArrayList<>();
        int currentCapacity = items[0].getCapacity();
        int totalWeight = 0;
        for(int i = newItems.length-1;i>0 && currentAnswer>0;i--){
            if(currentAnswer == opt[i-1][currentCapacity])
                continue;
            else{
                itemsPicked.add(0,newItems[i]);
                currentCapacity -= newItems[i].getWeight();
                totalWeight+= newItems[i].getWeight();
                currentAnswer -= newItems[i].getValue();
            }
        }
        System.out.print("\nDynamic Programming solution: ");
        System.out.print("Value: "+ opt[items.length][items[0].getCapacity()] + " Weight: "+ totalWeight+"\nItems: ");
        for(int m=0;m<itemsPicked.size();m++)
            System.out.print(itemsPicked.get(m).getIndex() + " ");
        System.out.println("\n");
    }
    //Branch and bound
    public static void branchAndBoundKnapSack(Item [] items){
        double startTime = System.nanoTime();
        Arrays.sort(items,new ItemComparatorReverse());
        PriorityQueue<Item> p = new PriorityQueue<Item>(1000000,new ItemComparator());
        Item finalResult = new Item(-2,0,0);
        int capacity = items[0].capacity;
        char[] itemsPickedLeft = new char [items.length+1];
        char[] itemsPickedRight = new char[items.length+1];
        char[] result = new char[items.length];
        int[] chosen = new int[items.length];
        for(int i = 0;i<itemsPickedLeft.length;i++){
            itemsPickedLeft[i] = '*';
            itemsPickedRight[i] = '*';
        }
        Item emptyNode = new Item(0,0,-1,findUpperBound(itemsPickedLeft,items),itemsPickedLeft,items.length);
        p.add(emptyNode);
        //Head node is now in the heap
        while(!p.isEmpty()){
            double currentTime = System.nanoTime() - startTime;
            if(currentTime/Math.pow(10,9) >= 60){
                break;
            }
            //Dequeue a node, pull off the queue first
            Item currentNode = p.poll();
            if(currentNode.upperBound < finalResult.value && currentNode.height == items.length-1)
                continue;
            int height = currentNode.height+1;

            //Left child setup:
            Item leftNode = new Item(currentNode.value+items[height].value,currentNode.weight+items[height].weight,height,currentNode.upperBound,currentNode.itemsPicked,items.length);
            leftNode.itemsPicked[height] = '1';
            //Pseudo-pruning for left node, checks if node is a leaf, nodeWeight vs KnSack capacity, and upperBound vs current maxValue
            if(leftNode.value > finalResult.value && leftNode.weight <= capacity){
                finalResult.value = leftNode.value;
                finalResult.weight = leftNode.weight;
                System.arraycopy(leftNode.itemsPicked,0,result,0,items.length);
            }
            if(leftNode.height != items.length-1&& leftNode.weight <= capacity && leftNode.upperBound > finalResult.value)
                p.add(leftNode);
            //Right child set up:
            Item rightNode = new Item(currentNode.value,currentNode.weight,height,findUpperBound(currentNode.itemsPicked,items),currentNode.itemsPicked,items.length);
            rightNode.itemsPicked[height] = '0';
            //Pseudo-pruning for right node, checks if node is a leaf, nodeWeight vs KnSack capacity, and upperBound vs current maxValue
            if(rightNode.height != items.length-1 && rightNode.weight <= capacity && rightNode.upperBound > finalResult.value)
                p.add(rightNode);
           // System.out.println(finalResult.value + " " + finalResult.weight);
        }
        System.out.print("\nUsing Branch and Bound the best feasible solution found: "+ "Value: "+ finalResult.value+" Weight: "+ finalResult.weight+"\nItems: ");
        //Print items picked
        for(int i =0;i<result.length;i++){
            if(result[i] == '1')
                chosen[i] = items[i].getIndex();
        }
        Arrays.sort(chosen);
        for(int i = 0;i<chosen.length;i++){
            if(chosen[i] != 0)
                System.out.print(chosen[i]+" ");
        }
        System.out.println("\n");
    }

    public static double findUpperBound(char[] c,Item[] items){
        double runningBound = 0;
        double runningTotalWeight = 0;
        for(int x = 0;x<items.length;x++){
            //If we know to not take the item, then exclude it from the calculation
            if(c[x] == '0')
                continue;
            //If the knapsack is partially filled, then do a fractional addition and return
            if(runningTotalWeight+items[x].getWeight() > items[0].capacity) {
                runningBound= runningBound + ((items[0].capacity-runningTotalWeight)*items[x].ratio);
                break;
            }
            else{
                runningBound += items[x].getValue();
                runningTotalWeight+= items[x].getWeight();
            }
        }
        return runningBound;
    }
}


