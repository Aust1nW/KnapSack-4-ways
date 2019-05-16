import java.util.NoSuchElementException;

public class MyHeap {
    private int size;
    private int capacity;
    private Item [] heap;

    public double findUpperBound(double ratio, int currentValue,int remainingWeight){
        return currentValue+(remainingWeight*ratio);
    }
    public MyHeap(int capacity){
        size = 0;
        this.capacity = capacity;
        heap = new Item[capacity+1];

    }
    public boolean insert(Item item){
        if (isFull()) {
            return false;
        }
        heap[size] = item;
        size++;
        driftUp(size-1);
        return true;
    }
    public Item findMax(){
        if(isEmpty()){
            throw new NoSuchElementException("Underflow Exception");
        }
        return heap[0];
    }
    //Helper function for deleteMin()
    private Item delete(int index){
        if(isEmpty()){
            throw new NoSuchElementException("Underflow Exception");
        }
        Item result = heap[index];
        heap[index] = heap[size-1];
        size--;
        driftDown(index);
        return result;
    }

    public Item deleteMax(){
        Item result = heap[0];
        delete(0);
        return result;
    }
    private int findParent(int index){
        return (index-1)/2;
    }
    private int findKthChild(int i, int k){
        return 2*i+k;
    }
    private int findLastChild(int i){
        int resultIndex = findKthChild(i,1);
        int x =2;
        int position = findKthChild(i,x);
        while((x<=2) && (position<size)){
            if(heap[position].getRatio() < heap[resultIndex].getRatio()){
                resultIndex = position;
            }
            position = findKthChild(i,x++);
        }
        return resultIndex;
    }

    public boolean isEmpty(){
        return size == 0;
    }
    public boolean isFull(){
        return size == heap.length;
    }
    public void driftDown(int index){
        int child;
        Item temp = heap[index];
        while(findKthChild(index,1) < size){
            child = findLastChild(index);
            if(heap[child].getRatio() < temp.getRatio()){
                heap[index] = heap[child];
            }
            else{
                break;
            }
            index = child;
        }
        heap[index] = temp;
    }
    public void driftUp(int index){
        Item temp = heap[index];
        while(index > 0 && temp.getRatio() >= heap[findParent(index)].getRatio()){
            heap[index] = heap[findParent(index)];
            index = findParent(index);
        }
        heap[index] = temp;
    }
    public int getHeapCap(){
        return capacity;
    }
    public int getHeapSize(){
        return size;
    }

    private static void maxHeapify(Item arr[],int size, int index){
        int small = index;
        int left = 2* index+1;
        int right = 2* index + 2;
        //Base case for left and right, triggers the recursive call to stop
        if (left < size && arr[left].getRatio() < arr[small].getRatio()){
            small = left;
        }
        if(right < size && arr[right].getRatio() < arr[small].getRatio()){
            small = right;
        }
        if(small != index){
            Item temp = arr[index];
            arr[index] = arr[small];
            arr[small] = temp;
            //Recursive call
            maxHeapify(arr,size,small);
        }
    }
}
