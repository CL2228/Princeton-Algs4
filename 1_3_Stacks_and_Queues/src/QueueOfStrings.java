import org.w3c.dom.Node;

public class QueueOfStrings {
    private int size;
    private Node first;
    private Node last;

    public QueueOfStrings(){
        first = null;
        last = null;
        size = 0;
    }

    public void enqueue(String item){
        size++;

        if (size == 1) {
            first = new Node();
            first.item = item;
            first.next = null;
            last = new Node();
            last = first;
        }else {
            Node oldlast = last;
            last = new Node();
            oldlast.next = last;
            last.next = null;
            last.item = item;
        }
    }

    public String dequeue(){
        if (size > 0){
            String item = first.item;
            first = first.next;
            size--;
            return item;
        } else throw new IllegalArgumentException("shabi");
    }

    public boolean isEmpty(){
        return (size <= 0);
    }

    public int size(){
        return size;
    }

    private class Node{
        Node next;
        String item;
    }
}
