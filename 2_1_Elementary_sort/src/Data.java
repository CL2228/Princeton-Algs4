public class Data implements Comparable<Data> {
    private int year;
    private int month;
    private int date;

    public Data(int y, int m, int d) {
        year = y;
        month = m;
        date = d;
    }

    public int compareTo(Data that) {
        if (this.year > that.year) return 1;
        if (this.year < that.year) return -1;
        if (this.month > that.month) return 1;
        if (this.month < that.month) return -1;
        if (this.date > that.date) return 1;
        if (this.date < that.date) return -1;
        return 0;
    }
    
}
