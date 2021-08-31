import edu.princeton.cs.algs4.StdRandom;
//需要生成随机数
import edu.princeton.cs.algs4.StdStats;
//需要其中的一些统计方法
import edu.princeton.cs.algs4.StdOut;
//main中需要一些输出
import java.lang.Math;
//需要使用根号
import java.lang.Integer;
//向main()传参时，需要将String转为int

public class PercolationStats
{
    private double[] thresholds;
    //保存每个Percolation的threshold
    private double mean;
    //所有threshold的平均数
    private double stddev;
    //标准差
    private double confidenceLo;
    private double confidenceHi;

    public PercolationStats(int n, int trials)
    {
        thresholds = new double[trials];
        //初始化后默认值为0.0

        //用computeThreshold()生成一个Percolation的threshold
        //循环生成所需的trials个
        for(int i = 0; i < trials; i++)
        {
            thresholds[i] = computeThreshold(n);
        }

        //计算平均数和标准差并保存
        mean = StdStats.mean(thresholds);
        stddev = StdStats.stddev(thresholds);

        //依据提供的公式计算confidenceLo和confidenceHi
        double tmp = (1.96 * stddev) / Math.sqrt(trials);
        confidenceLo = mean - tmp;
        confidenceHi = mean + tmp;
    }

    //计算Percolation的threshold
    private double computeThreshold(int n)
    {
        //为每次计算生成全新percolation
        Percolation percolation = new Percolation(n);

        //当percolation还未渗透，随机挑选其中的一个site打开
        while(!percolation.percolates())
        {
            //随机生成一个[0, n*n)的int型数
            int randomInt = StdRandom.uniform(n*n);
            percolation.open( (randomInt / n) + 1 , (randomInt % n) + 1 );
        }

        //到此percolation已渗透，计算threshold并返回
        double numberOfOpenSites = percolation.numberOfOpenSites();
        return numberOfOpenSites / (n*n);
    }

    public double mean()
    {
        return mean;
    }

    public double stddev()
    {
        return stddev;
    }

    public double confidenceLo()
    {
        return confidenceLo;
    }

    public double confidenceHi()
    {
        return confidenceHi;
    }

    //进行一些测试
    public static void main(String[] args)
    {
        PercolationStats pclStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        StdOut.println("mean                    = " + pclStats.mean);
        StdOut.println("stddev                  = " + pclStats.stddev);
        StdOut.println("95% confidence interval = [" + pclStats.confidenceLo + ", " + pclStats.confidenceHi + "]");
    }
}