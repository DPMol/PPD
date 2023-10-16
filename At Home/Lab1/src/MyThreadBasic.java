public abstract class MyThreadBasic extends Thread {
    protected int[][] initial, c, result;
    protected int k, n, m;

    protected MyThreadBasic(int[][] initial, int[][] c, int[][] result, int n, int m, int k) {
        this.initial = initial;
        this.c = c;
        this.result = result;
        this.n = n;
        this.m = m;
        this.k = k;
    }

    protected int Calculate(int x, int y){
        int pozChange = k/2, current_i, current_j;

        var initial_i = x - pozChange;
        var initial_j = y -  pozChange;
        var sum = 0;

        for(int i = 0; i < k; i++)
        {
            for(int j = 0; j < k; j++){
                current_i = initial_i + i;
                current_j = initial_j + j;
                if(current_j >= 0 && current_i >= 0 && current_j < m && current_i < n)
                    sum += initial[current_i][current_j] * c[i][j];
            }
        }

        return sum;
    }

    public abstract void run();
}

