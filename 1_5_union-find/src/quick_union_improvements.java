

public class quick_union_improvements {
    private int id[];
    private int size[];

    public quick_union_improvements(int N){
        id = new int[N];
        size = new int[N];
        for(int i=0; i < N ;i++){
            id[i] = i;
            size[i] = 1;
        }
    }

    private int root(int i){
        while (i != id[i]){
            id[i] = id[id[i]];  //用于path compression, 把这一个节点的id改成这个节点的上一个节点的id
            i = id[i];          //把节点都改成指向爷爷的节点，以修建树枝
        }
        return i;
    }

    public boolean connected(int p, int q){
        return root(p) == root(q);
    }

    public void union(int p, int q){        //depth of any node is at most lg(N).
        int i = root(p);
        int j = root(q);
        if (i==j) return;
        if (size[i] < size[j]){
            id[i] = j;
            size[j] += size[i];
        }else {
            id[j] = i;
            size[i] += size[j];
        }
    }
}
