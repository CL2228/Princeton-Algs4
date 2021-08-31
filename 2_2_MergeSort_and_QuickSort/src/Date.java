public class Date implements Comparable<Date>
{
    private int year;
    private int month;
    private int date;

    public Date(int y, int m, int d){
        year = y;
        month = m;
        date = d;
    }

    public int compareTo(Date that){
        if (this.year > that.year) return 1;
        if (this.year < that.year) return -1;
        if (this.month > that.month) return 1;
        if (this.month < that.month) return -1;
        if (this.date > that.date) return 1;
        if (this.date < that.date) return -1;
        return 0;    }
}
