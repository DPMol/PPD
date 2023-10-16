public class MyThreadLine extends MyThreadBasic {
    private final int start, end;

    protected MyThreadLine(int[][] initial, int[][] c, int[][] result, int n, int m, int k,  int start, int end) {
        super(initial, c, result, n, m, k);
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        for(int i = start; i < end; i++){
            for(int j = 0; j < m; j++){
                result[i][j] = Calculate(i, j);
            }
        }
    }
}
