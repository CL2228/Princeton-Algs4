/*
    哈希表，搜索的一种更快的方式
    使用BST或者红黑树一般要使用xlgN的时间，
    使用哈希表可以把时间降落到常数左右，但是牺牲了ordered（BST， binary search 都是ordered

    【哈希表的主要思路】
    1. 使用一个hash function，输入一个key，穿出这个key所对应value的index
    2. 难点在于构造一个hash function, 不用的key的hash value可能一样

    【seperate chaining】
    目的：主要在于解决多个key对应一个hush value的问题
    方法：1. 使用一个索引数组，长度为M < N, M通常为hash function使用的质数
         2. hash function计算出这个key所在的索引数组的位置，然后只从这个位置开始找这个key的value的index
    分析：1. 插入/查找的时间proportional to N/M， 一般取 M ～ N/5，这样基本能保证每条链上面有五个左右的元素

 */


import javax.print.attribute.standard.NumberOfDocuments;

public class SeperateChaining <Key, Value>
{
    private int M = 97;     // number of chains
    private Node[] st = new Node[M];


    private class Node{
        private Object key;
        private Object value;
        private Node next;

        public Node(Object key, Object value, Node next) {
            this.value = value;
            this.key = key;
            this.next = next;
        }
    }

    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    public Value get(Key key) {
        int i = hash(key);
        for (Node x = st[i]; x != null; x = x.next) {
            if (key.equals(x.key)) return (Value)x.value;
        }
        return null;
    }

    public void put(Key key, Value value) {
        int i = hash(key);
        for (Node x = st[i]; x != null; x = x.next) {
            if (key.equals(x.key)) x.value = value;
        }
        st[i] = new Node(key, value, st[i]);
    }

}
