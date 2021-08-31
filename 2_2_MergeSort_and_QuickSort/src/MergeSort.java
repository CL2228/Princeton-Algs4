/*
    归并排序法，排序N各个单位使用时间 NlgN

 【归并排序法法的时间分析】
    1. 数组比较次数: compare C(N)
        C(N) <= C(N/2) + C(N/2) + N
            其中N用于merge操作，其实只有在merge操作内才有数组比较，C(1)=0,

    2. 数组读取操作，array accesses A(N)
        A(N) <= A(N/2) + A(N/2) + 6N
            merge里面的读取操作次数是6N

    3. 可由数学证明上述表达式的求和结果为 NlgN, 6NlgN respectively

  【归并排序的内存分析】
    1.Compareable[] a, aux的内存，指针int lo, hi, mid, i, k, j

  【归并排序的性能提升方法】
    1.在merge之前测试一下两边的数组是否已经排好序
    2.在merge时候不进行copy操作，而是在sort时候把排序好的两段放在aux，在merge时候直接把aux里面的merge到a


 */


public class MergeSort{
    private static boolean less(Comparable v, Comparable w) {
        return (v.compareTo(w) < 0);
    }

    private static boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo; i <= hi; i++) {
            if (less(a[i] , a[i - 1]))
                return false;
        }
        return true;
    }

    public static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        assert isSorted(a, lo, mid);
        assert isSorted(a, mid + 1, hi);

        for(int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }
        /*
            比较和两个segment以后把数据重新放回a，使用三个下表i，j，k
         */
        int i = lo, j = mid + 1;
        for(int k = lo; k <= hi; k++) {
            if (i > mid){
                a[k] = aux[j++];
            } else if (j > hi) {
                a[k] = aux[i++];
            } else if (less(aux[j], aux[i])) {
                a[k] = aux[j++];
            } else {
                a[k] = aux[i++];
            }
        }
        assert isSorted(a, lo, hi);
    }

    // 排序函数，排序时候aux应该直接由主程序传入，而避免在sort递归里面多次使用花费时间
    public static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
        if (hi <= lo) return;
        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid + 1, hi);
        if (!less(a[mid + 1], a[mid])) return;  // 如果mid+1不是小于mid则不进行merge，提升方法1
        merge(a, aux, lo, mid, hi);
    }

    public static void sort(Comparable[] a) {
        Comparable[] aux = new Comparable[a.length];
        sort(a, aux, 0, a.length - 1);
    }
}