/*
    红黑树——一种高效的2-3树实现方式

    【2-3树】
    1. 保证绝对平衡，2，3分别指的是有2，3个son
    2. 3树中
    3. 可以保证无论是search还是insert都在NlgN内

    【红黑树】
    1. 使用和binary search tree一样的物理结构，但使用红黑枝来表示这是2还是3tree
    2. red links lean left, no node has two read links connected to it

 */

import java.util.Iterator;

public class RedBlackTree <Key extends Comparable<Key>, Value> implements Iterable<Key>
{
    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private Node root;

    private class Node {
        private Key key;
        private Value val;
        private Node left, right;
        private boolean color;  // color of parent link
        public Node(Key key, Value value, boolean color) {
            this.key = key;
            this.val = value;
            this.color = color;
        }
    }

    private boolean isRed(Node x) {
        if (x == null) return false;
        return (x.color == RED);
    }

    // 当一个Node的右边son是一个red Node（不符合要求）
    // 此时应该用右边son代替自己，自己变成一个red Node， 右边son的左边的son应该变成自己右边的son
    private Node rotateLeft(Node h) {
        assert isRed(h.right);  // 自己右边son是个red
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }

    // 在某些操作的中间步骤中需要把原来顺序正确的的红黑树（parent左边的son是个红NOde）变成右边是个红Node
    private Node rotateRight(Node h) {
        assert isRed(h.left);
        Node x = h.left;
        h.left = x.right;
        x.color = h.color;
        h.color = RED;
        return x;
    }

    // 用于4-Node的操作，当出现一个4-node，即一个Node里面有3个key
    // 最大和最小的key和中间key都是用red link连接的，因此中间的两边son都是red Node，而中间的是black Node
    // 此时要把两边的son都变成black Node，而中间的变成Red Node（中间这个元素往上走了一层，插入到了上一层的Node
    // 而此时上一层的Node起码是一个3-Node，即使此时red link可能不是lean left但可以再通过rotateLeft校正
    private void flipColors(Node h) {
        assert !isRed(h);
        assert isRed(h.left);
        assert isRed(h.right);
        h.color = RED;
        h.left.color = BLACK;
        h.right.color = BLACK;
    }


    // 红黑树的插入操作，一共分为三种情况
    // 1. 这个元素左边son以及左边son的左边son都是red，则应该right rotation，
    //    用左边son代替这个元素，使得左边son的两边都是Red，然后返回给上一层，在进行flipcolor
    // 2. 这个元素的左边son以及左边son的右边son是Red，则应该对左边son进行一次left rotation，变成情况1
    // 3. 两边son都是red，（通常是直接插入，或者是1或2的子结果），此时进行flip color，把结果返回给上一层进行操作
    private Node put(Node h, Key key, Value value) {
        if (h == null) return new Node(key, value, RED);

        int cmp = key.compareTo(h.key);
        if (cmp < 0) h.left = put(h.left, key, value);
        else if (cmp > 0) h.right = put(h.right, key, value);
        else h.val = value;

        if (isRed(h.right) && !isRed(h.left)) h = rotateLeft(h);        // 普通的right leaning情况
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);    // 经过这一步以后下一个if就出触发了（情况2
        if (isRed(h.right) && isRed(h.left)) flipColors(h);

        return h;
    }



    // get method, identical to binary search tree
    public Value get(Key key) {
        Node x = root;
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else if (cmp == 0) return x.val;
        }
        return null;
    }




    public Iterator<Key> iterator(){
        return null;
    }
}
