/*
   多个key值一样时候的quick sort
   使用普通的partition很容易遇到使用时间平方的情况，
   在这里使用的策略是使用lt gt i三个指针，

   1. 首先令partition item为lt=lo， i往右边逐个扫描
   2. 若a[i] < v, 直接令i和lt的元素交换，i和lt都加1
   3. 若a[i] > v, 令i和gt的元素交换，gt-1但是i不变（还要检测换回来的这个元素的大小）
   4. i迭代到gt结束
   5. 把分成三段的左右两段继续放到sort递归1～3的操作，直到subset的大小为1
 */





public class MutiKeyWithQS {
    private static void exch(Comparable[] a, int i, int j) {
        Comparable temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    private static boolean less(Comparable v, Comparable w) {
        return (v.compareTo(w) < 0);
    }

    private static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) return;
        int lt = lo, gt = hi;
        int i = lo;
        Comparable partitionItem = a[lo];
        while (i <= gt) {
            int cop = a[i].compareTo(partitionItem);
            if (cop < 0) exch(a, lt++, i++);
            if (cop > 0) exch(a, gt--, i);
            else i++;
        }
        sort(a, lo, lt - 1);
        sort(a, gt +1, hi);
    }
}
