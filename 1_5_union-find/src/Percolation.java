import edu.princeton.cs.algs4.WeightedQuickUnionUF;
//要使用WeightedQuickUnionUF数据结构

public class Percolation
{
    private final int n;
    //保存构造函数传入的n参数
    private boolean[] isOpenSites;
    //用boolean类型数组保存各个site的打开/关闭情况
    private int numberOfOpenSites;
    //打开的site总数
    private WeightedQuickUnionUF percolationUF;
    //一个UF对应一个Percolation，
    //UF中的节点对应Percolation中的site，
    //利用UF来判断site是否full等情况

    public Percolation(int n)
    {
        //依据要求，参数不当时抛出异常
        if(n <= 0)
        {
            throw new IllegalArgumentException();
        }

        this.n = n;
        isOpenSites = new boolean[n*n+1];
        /*  
            对于原始数据类型boolean数组，
            未初始化值则自动初始化为false。
            
            为与WeightedQuickUnionUF中节点相对应，
            多使用了一个，总数为n*n+1
            即实际使用中isOpenSites[0]无实际意义
        */
        numberOfOpenSites = 0;
        percolationUF = new WeightedQuickUnionUF(n*n+2);
        /*
            多出的两个，第一个用于连接第一排，第二个用于连接最后一排
        */
    }

    /*
        对于传入的二维坐标row和col，
        转换为数组isOpenSites和percolationUF所需的一维坐标
        以及依据题目要求判断传入参数是否合法
    */
    private int index(int row, int col)
    {
        if((row >=1 && row <= n) && (col >=1 && col <= n))
        {
            return (row - 1) * n + col;
        }
        else
        {
            throw new IndexOutOfBoundsException();
        }

    }

    //如果site(row, col)未打开，打开它
    public void open(int row, int col)
    {
        //检查输入参数是否合法
        int index = index(row, col);
        //若site(row, col)未打开，打开它
        if(isOpenSites[index] == false)
        {
            //如果整个系统只有一个site
            if(n == 1)
            {
                isOpenSites[index] = true;
                //将该site标记为打开
                numberOfOpenSites++;
                //打开site总数增加

                //设置对应percolationUF节点的连接状态
                percolationUF.union(0, index);
                percolationUF.union(index, 2);

                return;
            }

            //从这里开始，n至少为2，即系统至少2行
            isOpenSites[index] = true;
            //将该site标记为打开
            numberOfOpenSites++;
            //打开site总数增加

            //设置对应percolationUF节点的连接状态
            //若要开启第一行的site
            if(row == 1)
            {
                percolationUF.union(0, index);
                /*
                    第一行site所对应节点必须和第0节点连接
                    同样最后一行site所对应节点必须和第n*n+1节点连接
                    这样在判断系统是否渗透时，
                    只需比较第0节点和第n*n+1节点，
                    且第一排与最后一排各site无需与其左右site比较相连
                */

                //如果该site下方的site已打开，连接这两个site
                int underIndex = index(row+1, col);
                if(isOpenSites[underIndex] == true)
                {
                    percolationUF.union(index, underIndex);
                }
            }
            //若开启最后一行的site
            else if(row == n)
            {
                percolationUF.union(index, n*n+1);
                /*理由同上*/

                //如果该site上方的site已打开，连接这两个site
                int upIndex = index(row-1, col);
                if(isOpenSites[upIndex] == true)
                {
                    percolationUF.union(index, upIndex);
                }
            }
            //若开启中间行的site，且到此系统定为至少三行
            else
            {
                /*若该site上方和下方的site是打开状态，则连接*/
                int upIndex = index(row-1, col);
                if(isOpenSites[upIndex] == true)
                {
                    percolationUF.union(index, upIndex);
                }
                int underIndex = index(row+1, col);
                if(isOpenSites[underIndex] == true)
                {
                    percolationUF.union(index, underIndex);
                }
                
                /*
                    判断该site的左右site是否需要连接
                    对处于第一列和最后一列的site特别对待
                */
                //若为第一列
                if(col == 1)
                {
                    //只需判断其右侧site是否需要连接
                    int rightIndex = index(row, col+1);
                    if(isOpenSites[rightIndex] == true)
                    {
                        percolationUF.union(index, rightIndex);
                    }
                }
                //若为最后一列
                else if(col == n)
                {
                    //只需判断其左侧site是否需要连接
                    int leftIndex = index(row, col-1);
                    if(isOpenSites[leftIndex] == true)
                    {
                        percolationUF.union(index, leftIndex);
                    }
                }
                //若为一行中处于中间列的site
                else
                {
                    //分别判断左右site是否需要连接
                    int rightIndex = index(row, col+1);
                    if(isOpenSites[rightIndex] == true)
                    {
                        percolationUF.union(index, rightIndex);
                    }
                    int leftIndex = index(row, col-1);
                    if(isOpenSites[leftIndex] == true)
                    {
                        percolationUF.union(index, leftIndex);
                    }
                }

            }
        }
    }

    //返回site(row, col)是否打开
    public boolean isOpen(int row, int col)
    {
        int index = index(row, col);
        return isOpenSites[index];
    }

    //返回site(row, col)是否full，即是否被渗透
    public boolean isFull(int row, int col)
    {
        int index = index(row, col);
        return percolationUF.connected(0, index);
    }

    //返回已打开的site数
    public int numberOfOpenSites()
    {
        return numberOfOpenSites;
    }

    //返回系统是否已渗透
    public boolean percolates()
    {
        return percolationUF.connected(0, n*n+1);
    }

    //main不做要求
    public static void main(String[] args)
    {

    }
}