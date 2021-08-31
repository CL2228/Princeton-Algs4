public class ShellSort {
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    private static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            if (less(a[i], a[i - 1]))
                return false;
        }
        return true;
    }

    public static void sort(Comparable[] a) {
        int h = 1;
        while (h < a.length / 3) h = h * 3 + 1;
        while (h >= 1) {
            for (int idx = h; idx < a.length; idx++) {
                int i = idx;
                while (i >= h) {
                    if (less(a[i], a[i - h])) exch(a, i, i - h);
                    i -= h;
                }
            }
            h /= 3;
        }
    }
}
