public class MyThreadColumn extends MyThreadBasic {

    private final int start, end;

    protected MyThreadColumn(int[][] initial, int[][] c, int[][] result, int n, int m, int k,  int start, int end) {
        super(initial, c, result, n, m, k);
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        for(int i = 0; i < n; i++){
            for(int j = start; j < end; j++){
                result[i][j] = Calculate(i, j);
            }
        }
    }
}
