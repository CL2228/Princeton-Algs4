import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item>
{
    private int size;
    private Item[] data;

    // construct an empty randomized queue
    public RandomizedQueue() {
        size = 0;
        data = null;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return (size <= 0);
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item){
        if (item != null){
            if (size == 0){
                data = (Item[]) new Object[1];
            } else {
                if (data.length == size)
                    resize(data.length * 2);
            }
            data[size++] = item;
        } else throw new IllegalArgumentException("?");
    }

    // remove and return a random item
    public Item dequeue() {
        if (!isEmpty()){
            int idx = StdRandom.uniform(size);
            Item item = data[idx];
            data[idx] = data[size - 1];
            data[--size] = null;
            if (size < data.length / 4)
                resize(data.length / 2);
            return item;
        } else throw new NoSuchElementException("?");

    }

    // return a random item (but do not remove it)
    public Item sample(){
        if (!isEmpty()){
            int idx = StdRandom.uniform(size);
            return data[idx];
        } else throw new NoSuchElementException("?");
    }

    // resize the array
    private void resize(int capacity){
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++){
            copy[i] = data[i];
        }
        data = copy;
    }

    // public constructor, returning an Iterator
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    // Iterator object, from front to the back
    private class ListIterator implements Iterator<Item> {
        private int N = size;
        private int[] index = StdRandom.permutation(size);

        public boolean hasNext() {
            return (N > 0);
        }

        public void remove() {
            throw new UnsupportedOperationException("?");
        }

        public Item next() {
            if (hasNext()) {
                return data[index[--N]];
            } else throw new NoSuchElementException("?");
        }
    }

    public static void main(String[] args){
        RandomizedQueue<String> randomqueue = new RandomizedQueue<String>();
        randomqueue.enqueue("I");
        randomqueue.enqueue("want");
        randomqueue.enqueue("sex");
        randomqueue.enqueue("with");
        randomqueue.enqueue("you");

        for(String i : randomqueue){
            System.out.println(i);
        }
        System.out.println();


        System.out.println(randomqueue.dequeue());
        System.out.println(randomqueue.sample());
        System.out.println(randomqueue.sample());
        System.out.println(randomqueue.sample());
        System.out.println(randomqueue.sample());
        System.out.println(randomqueue.size());
        System.out.println(randomqueue.isEmpty());
        System.out.println();

    }
}
