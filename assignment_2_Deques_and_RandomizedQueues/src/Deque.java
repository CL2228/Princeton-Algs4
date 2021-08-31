/*
    Princeton Algorithms Assignment #2
 */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int size;


    // construct an empty deque
    public Deque() {
        size = 0;
        first = null;
        last = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return (first == null || last == null);
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item != null) {
            if (size == 0) {             // initiation for the first adding
                first = new Node();
                last = new Node();
                first.item = item;
                first.front = null;
                first.back = null;
                last = first;
            } else {                     // normal front adding
                Node oldfirst = first;
                first = new Node();
                first.front = null;
                first.item = item;
                first.back = oldfirst;
                oldfirst.front = first;
            }
            size++;
        } else throw new IllegalArgumentException("?");
    }

    // add the item to the back
    public void addLast(Item item) {        // initiation
        if (item != null) {
            if (size == 0) {
                first = new Node();
                last = new Node();
                first.item = item;
                first.front = null;
                first.back = null;
                last = first;
            } else {
                Node oldlast = last;
                last = new Node();
                last.back = null;
                last.item = item;
                last.front = oldlast;
                oldlast.back = last;
            }
            size++;
        } else throw new IllegalArgumentException("?");
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (!isEmpty()) {
            Item item = first.item;
            first = first.back;
            size--;
            if (size != 0)
                first.front = null;
            return item;
        } else throw new NoSuchElementException("?");
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (!isEmpty()) {
            Item item = last.item;
            last = last.front;
            size--;
            if (size != 0)
                last.back = null;
            return item;
        } else throw new NoSuchElementException("?");

    }

    // linked list Node Object
    private class Node {
        Node front;
        Node back;
        Item item;
    }

    // public constructor, returning an Iterator
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    // Iterator object, from front to the back
    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException("?");
        }

        public Item next() {
            if (hasNext()) {
                Item item = current.item;
                current = current.back;
                return item;
            } else throw new NoSuchElementException("?");

        }

    }

    public static void main(String[] args) {
        Deque<String> stack = new Deque<String>();
        stack.addFirst("want");
        stack.addLast("sex");
        stack.addFirst("I");
        for (String s : stack) {
            StdOut.println(s);
        }
        System.out.println(stack.size());
        System.out.println();

        stack.addLast("with");
        stack.addLast("you");
        for (String s : stack) {
            StdOut.println(s);
        }
        System.out.println(stack.size());
        System.out.println();


        stack.removeFirst();
        for (String s : stack) {
            StdOut.println(s);
        }
        System.out.println(stack.size());
        System.out.println();


        stack.removeLast();
        for (String s : stack) {
            StdOut.println(s);
        }
        System.out.println(stack.size());
        System.out.println();


        stack.removeLast();
        stack.removeFirst();
        for (String s : stack) {
            StdOut.println(s);
        }
        System.out.println(stack.size());
        System.out.println();


        stack.removeFirst();
        for (String s : stack) {
            StdOut.println(s);
        }
        System.out.println(stack.size());
        System.out.println();

        System.out.println(stack.isEmpty());

    }
}
