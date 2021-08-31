import org.w3c.dom.Node;

public class StackOfStrings {
    private int nodeNum;
    private Node first;

    public StackOfStrings(){
        nodeNum = 0;
        first = null;

    }

    public void push(String item){
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
    }

    public String pop(){
        if(isEmpty()){
            String item = first.item;
            first = first.next;
            nodeNum -= 1;
            return item;
        }else throw new IllegalArgumentException("already empty now!");
    }

    public boolean isEmpty(){
        return (first == null);
    }

    public int size(){
        return nodeNum;
    }


    private class Node{
        String item;
        Node next;
    }



    public static void main(String[] args){
        //StackOfStrings stack = new StackOfStrings();
        /*
        while (!StdIn.isEmpty()){
            String s = StdIn.readString();
            if(s.equals("-")) StdOut.print(stack.pop());
            else stack.push(s);
        }*/
        StdOut.print("fuck");
        String s = StdIn.readString();
        StdOut.print(s);
    }
}
