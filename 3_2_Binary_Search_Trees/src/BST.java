/*
    binary search tree,
    以key的大小建立搜寻树，
    对于每个节点 比这个节点小的在左边，大的在右边

    寻找的次数取决于key插入的顺序，如果插入的顺序是ordered的那走远了，要用N次搜寻
 */

import java.util.Iterator;

public class BST <Key extends Comparable<Key>, Value> implements Iterable<Key>
{
    private Node root;

    private class Node {
        private Key key;
        private Value value;
        private Node left, right;
        private int count;
        public Node(Key key, Value value, int count) {
            this.key = key;
            this.value = value;
            this.count = count;
        }
    }


    // 使用递归的方式插入一个key
    public void put(Key key, Value val) {
        root = put(root, key, val);
    }
    private Node put(Node x, Key key, Value val) {
        // 如果插入方向是空的(即就是要放在这个位置)，直接创建一个Node，并返回给parent.left/right
        if (x == null) return new Node(key, val, 1);
        // 如果不是空的则继续进行递归或者修改已存在key的value值
        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            x.left = put(x.left, key, val);
        else if (cmp > 0)
            x.right = put(x.right, key, val);
        else
            x.value = val;
        x.count = 1 + size(x.left) + size(x.right);      // 再创建树的时候
        return x;
    }

    // 返回树的大小
    public int size() {
        return size(root);
    }
    private int size(Node x) {
        if (x == null) return 0;
        else return x.count;
    }


    // 给定一个key，找到小于等于key的最大key并返回
    public Key floor(Key key) {
        Node x = floor(root, key);
        if (x == null) return null;     // 如果并没有小于等于这个key的node则直接返回null
        return x.key;
    }
    private Node floor(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);

        if (cmp == 0) return x;     // 如果当前的key和floor一样，则直接返回这个Node
        if (cmp < 0) return floor(x.left, key); // 如果当前floor小于当前的key，则应该往左边找

        // 如果floor大于当前key，则应该尽可能往右边找
        // 如果的在右边找不到了，则直接返回这个节点，否则返回在右边找到的节点
        Node t = floor(x.right, key);
        if (t != null) return t;
        else return x;
    }


    // 搜寻函数，从root开始往下找，按照小左大右的原则，
    // 如果找到了就返回，直到找到没有subtree时候返回null
    public Value get(Key key) {
        Node x= root;
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else return x.value;
        }
        return null;
    }

    // how many keys < k?
    public int rank(Key key) {
        return rank(key, root);
    }
    private int rank(Key key, Node x) {
        if (x == null) return 0;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return rank(key, x.left);  //如果这个要找的key在当前的左边，那rank(当前)和rank(左边)一样
        // 如果这个key在当前位置的右边，则key左边的数量 = 1+ 当前左边的数量 + 右边符合的数量
        else if (cmp > 0) return 1 + size(x.left) + rank(key, x.right);
        else return size(x.left);   //如果key和当前key相等，则只要返回左边的数量就好了
    }

    // 删除某个元素，删除的策略：
    // 1. 找到找到这个元素在BST中的位置
    // 2. 以这个元素右边的son为root，找到这个'root'中的最小元素
    // 3. 使用这个'最小元素'替换掉需要删除的Node
    // 4. 更新count
    public void delete(Key key) {
        root = delete(root, key);
    }
    private Node delete(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = delete(x.left, key);    // 如果这个key小于x，则往左边走
        else if (cmp > 0) x.right = delete(x.right, key);  //如果key大于x则往右边走
        else {  // 找到了需要delete的key
            if (x.right == null) return x.left;     // 这个Node只有一个son的情况
            if (x.left == null) return x.right;

            Node t = x;                         // t现在是要删掉的node
            x = min(t.right);                   // x现在是要替换的大于要删掉node的最小元素
            x.right = deleteMin(t.right);       // 这个x的右边现在是t删掉右边最小项后的sub tree
            x.left = t.left;                    // x的左边和原来x的左边一样
        }
        x.count = size(x.left) + size(x.right) + 1; // 更新count
        return x;
    }


    // 删除并返回root中的最小元素
    private Node min(Node x) {
        if (x.left == null) return x;
        return min(x.left);
    }
    public void deleteMin(Key key) {
        root = deleteMin(root);
    }
    private Node deleteMin(Node x) {
        if (x.left == null) return x.right; // 如果x没有左边son，则直接返回s右边的son作为predecessor的left
        x.left = deleteMin(x.left);
        x.count = 1 + size(x.left) + size(x.right);
        return x;
    }

    // 返回一个iterator对象，使用一个queue，从树的左边到右边以此加入进去
    public Iterator<Key> iterator(){
        return null;
    }
    public Iterable<Key> keys() {
        Queue<Key> q = new Queue<Key>();
        inorder(root, q);
        return q;
    }
    private void inorder(Node x, Queue<Key> q) {
        // 从左到右以此插入到queue里面
        if (x == null) return;
        inorder(x.left, q);
        q.enqueue(x.key);
        inorder(x.right, q);
    }
    private class Queue<Key extends Comparable<Key>> implements Iterable<Key>{
        public void enqueue(Key key) {

        }
        public Iterator<Key> iterator(){
            return null;
        }
    }
}
