import java.util.Iterator;

/*
在原来范型的stack基础上加入了一个Iterator，
用户可以直接调用iterator函数，返回一个Iterator，直接遍历stack中的所有元素
 */


public class GenericStack_withIterator <Item> implements Iterable<Item>
{


    private int size;
    private Node last;
    public GenericStack_withIterator() {
        last = null;
        size = 0;
    }

    public void push(Item item){
        size++;
        Node oldlast = last;
        last = new Node();
        last.next = oldlast;
        last.item = item;

    }

    public Item pop() {
        if(size > 0) {
            Item item = last.item;
            last = last.next;
            size--;
            return item;
        }else throw new IllegalArgumentException("?");
    }

    public boolean isEmpty(){
        return (size <= 0);
    }

    private class Node{
        Item item;
        Node next;
    }


    public Iterator<Item> iterator(){
        return new ListInterator();
    }

    private class ListInterator implements Iterator<Item>
    {
        private Node current = last;

        public boolean hasNext() { return current != null; }
        public Item next() {
            Item temp = current.item;
            current = current.next;
            return temp;
        }
    }
}
