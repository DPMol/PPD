import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class ThreadTesting {

    static int[][] initial, result, c, resultLine, resultColumn;

    static final String filePathInitial = "initial.txt";

    static final String filePathC = "c.txt";

    static int numberOfThreads = 8;
    static long startTime, endTime;

    static int n, m, k;

    public ThreadTesting() {
        readC();
        readInitial();
        setup();

        baseTest();
    }

    private void setup(){
        resultLine = new int[n][m];
        resultColumn = new int[n][m];
        result = new int[n][m];
    }

    private void readInitial(){
        try {
            // Open the file using a Scanner
            File file = new File(filePathInitial);
            Scanner scanner = new Scanner(file);

            // Read the dimensions of the matrix (n and m)
            n = scanner.nextInt();
            m = scanner.nextInt();

            // Create a 2D array to store the matrix
            initial = new int[n][m];

            // Read the matrix values from the file
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (scanner.hasNextInt()) {
                        initial[i][j] = scanner.nextInt();
                    } else {
                        System.err.println("Matrix file is not properly formatted.");
                        return;
                    }
                }
            }

            // Close the file
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filePathInitial);
        }
    }

    private void readC(){
        try {
            // Open the file using a Scanner
            File file = new File(filePathC);
            Scanner scanner = new Scanner(file);

            // Read the dimensions of the matrix (n and m)
            k = scanner.nextInt();

            // Create a 2D array to store the matrix
            c = new int[k][k];

            // Read the matrix values from the file
            for (int i = 0; i < k; i++) {
                for (int j = 0; j < k; j++) {
                    if (scanner.hasNextInt()) {
                        c[i][j] = scanner.nextInt();
                    } else {
                        System.err.println("Matrix file is not properly formatted.");
                        return;
                    }
                }
            }

            // Close the file
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filePathC);
        }
    }


    private void startTime(){
        startTime = System.currentTimeMillis();

        System.out.println("Test started at: " + startTime);
    }

    private void endTime(){
        endTime = System.currentTimeMillis();
        System.out.println("Test ended at: " +  endTime);
        System.out.println("Test time was: " +  (double) (endTime - startTime) + "\n");
    }

    public void baseTest(){
        startTime();

        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                result[i][j] = Calculate(i, j);
            }
        }

        endTime();
    }

    private int Calculate(int x, int y){
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

    public void lineTest(){
        startTime();

        int s, r, start , end;
        s = n / numberOfThreads;
        r = n % numberOfThreads;

        MyThreadLine[] thread = new MyThreadLine[numberOfThreads];
        end = 0;

        for (int i = 0; i < numberOfThreads; i++) {
            start = end ;
            end = start + s;
            if(r > 0){
                end ++;
                r --;
            }
            thread[i] = new MyThreadLine(initial, c, resultLine, n, m, k, start ,end);
            thread[i].start();
        }


        for (int i = 0; i < numberOfThreads; i++) {
            try {
                thread[i].join();
            }catch (InterruptedException e) {
                System.out.println("Thread with id " + i +" died ☠");
            }
        }

        endTime();
        var isCorrect = Arrays.deepEquals(result, resultLine);
        System.out.println("Result is " + isCorrect+ "\n\n");
    }

    public void columnTest(){
        startTime();

        int s, r, start , end;
        s = m / numberOfThreads;
        r = m % numberOfThreads;

        MyThreadColumn[] thread = new MyThreadColumn[numberOfThreads];
        end = 0;

        for (int i = 0; i < numberOfThreads; i++) {
            start = end ;
            end = start + s;
            if(r > 0){
                end ++;
                r --;
            }
            thread[i] = new MyThreadColumn(initial, c, resultColumn, n, m, k, start ,end);
            thread[i].start();
        }


        for (int i = 0; i < numberOfThreads; i++) {
            try {
                thread[i].join();
            }catch (InterruptedException e) {
                System.out.println("Thread with id " + i +" died ☠");
            }
        }

        endTime();
        var isCorrect = Arrays.deepEquals(result, resultColumn);
        System.out.println("Result is " + isCorrect + "\n\n");
    }
}
