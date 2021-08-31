/*
    范型，使用可以用于多种数据类型的Stack
    范型只能用于链表（JAVA），java中使用数组范型的话需要强制转换（最好避免这一类操作）
*/



public class GenericStack <Item>
{
    private int size;
    private Node last;
    public GenericStack() {
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
}
