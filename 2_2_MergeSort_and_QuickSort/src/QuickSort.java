/*
    快速排序法

    【思想】
     1. 首先shuffle数组（十分重要，可以避免数组一开始就有序而导致的n方情况
     2. 取shuffle好的数组的第一个元素为partition item, PI
     3. 在两端分别去两个指针，通过把所有小于PI的放在左边，大于的放在PI右边
     4. 对两边的两部分再进行partition操作直到数组长度为1

    【和merge sort的区别】
     1. merge sort需要需用N倍的线性额外内存空间，适用于auxiliary array
     2. merge sort的理论比较次数是NlgN，quick sort是1.39NlgN, 但是QS不需要数组移动，节省时间
     3. merge sort为稳定排序，quick sort为不稳定排序（有长距离数组元素交换）
     4. merge sort是先递归再排序， quick sort是先排序再递归

    【merge sort性能提升】
     1. Cutoff: 在元素数量小于10时候直接用insertion sort
     2. Median of sample， 理论上来说如果第一次的partition item是中位数的话使用时间最大，
        但是直接求中位数很蠢，可以在partition前使用lo（PI），中间元素，hi求中位数

    【selection problem】
      这个情境下其实不需要使用一次完整的quick sort
      (思路):
      在partition后得到的j和所需要的Kth比较，如果j>K则仅使用左边的元素进行partition，j<K则仅使用右边的元素进行partition
 */


import edu.princeton.cs.algs4.StdRandom;

public class QuickSort {

    private static boolean less(Comparable v, Comparable w) {
        return (v.compareTo(w) < 0);
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }



    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo;
        int j = hi + 1;

        while (true) {
            while (less(a[++i], a[lo])) {           //i一直往右移，移到大于等于PI时候停止
                if (i == hi) break;
            }

            while (less(a[lo], a[--j])) {           //j一直往左移，移到小于等于PI停止
                if (j == lo) break;
            }

            if (i >= j) break;      //当i不等于j时候交换i和j
            exch(a, i, j);
        }
        exch(a, lo, j);
        return j;                   //交换PI和j，返回j，是下一次递归以j分割成两部分
    }

    public static void sort(Comparable[] a) {
        StdRandom.shuffle(a);
        sort(a, 0, a.length - 1);
    }

    private static int medianOf3(Comparable[] a ,int lo, int mi, int hi) {
        Comparable less = a[lo];
        //TODO: needed implementation
        return 0;
    }

    private static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) return; //当需要排序的数组只有一个元素时候直接返回

        int m = medianOf3(a, lo, lo + (hi - lo)/2, hi);

        int j = partition(a, lo, hi);
        sort(a, lo, j-1);
        sort(a, j+1, hi);   //使用递归的方式继续partition
    }

    public static Comparable select(Comparable[] a, int k) {
        //从a里面取出第k大的元素
        StdRandom.shuffle(a);
        int lo = 0, hi = a.length - 1;
        while (hi > lo) {
            int j = partition(a, lo, hi);
            if (j < k) lo = j + 1;
            else if (j > k) hi = j - 1;
            else return a[k];
        }
        return a[k];
    }























}
