/*
  Java implementation of Binary heap

 */


public class BinaryHeap<Item extends Comparable<Item>>
{
    private Item[] pq;
    private int N;

    public BinaryHeap(int capacity) {
        pq = (Item[]) new Comparable[capacity+1];
    }

    private void exch(int i, int j ) {
        Item temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;
    }

    private boolean less(int i, int j) {
        return (pq[i].compareTo(pq[j]) < 0);
    }

    // when a son has been changed and it is now bigger than parent
    private void swim(int k) {
        while (k > 1 && less(k/2, k)) {
            exch(k, k/2);
        }
    }

    // insert to the end of the array, then swim this element until it satisfies the rule
    public void insert(Item x) {
        pq[++N] = x;
        swim(N);
    }

    // if a parent is smaller than both of the kids, then sink this parent until not
    private void sink(int k) {
        while (2*k <= N) {      //check whether already reached the bottom of the tree
            int j = 2 * k;
            if (j < N && less(j, j+1)) j++;
            if (!less(k, j)) break;
            exch(k, j);
            k = j;
        }
    }

    // delete max, which means delete the element the one on the top
    // first exchange the top element and the last element,
    // delete and return the now last one then sink the top one
    public Item delMax() {
        Item item = pq[1];
        exch(1, N--);
        sink(1);
        pq[N] = null;
        return item;
    }
}
