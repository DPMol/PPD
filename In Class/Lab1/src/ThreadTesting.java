import java.util.Random;

public class ThreadTesting {
    static int[] v1, v2, v3;

    static int numberOfThreads = 8;
    long startTime, endTime;

    static final int size = 101572431;

    private void setup(){
        v1 = new int[size];
        v2 = new int[size];
        v3 = new int[size];
        var rand = new Random();
        for (int i = 0; i < size; i++) {
            v1[i] = rand.nextInt(0, size);
            v2[i] = rand.nextInt(0, size);
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

    public void baselineTest(){
        setup();
        startTime();

        for (int i = 0; i < size; i++) {
            v3[i] = v1[i] + v2[i];
        }

        endTime();
    }

    public void threads1Test() {
        setup();
        startTime();

        int p = 8, c, r, start , end;
        c = size / p;
        r = size % p;

        MyThread1[] thread = new MyThread1[p];
        end = 0;

        for (int i = 0; i < p; i++) {
            start = end ;
            end = start + c;
            if(r > 0){
                end ++;
                r --;
            }
            thread[i] = new MyThread1(i, start, end);
            thread[i].start();
        }


        for (int i = 0; i < p; i++) {
            try {
                thread[i].join();
            }catch (InterruptedException e) {
                System.out.println("Thread with id " + i +" died ☠");
            }
        }

        endTime();
    }

    public void threads2Test() {
        setup();
        startTime();

        MyThread2[] thread = new MyThread2[numberOfThreads];

        for (int i = 0; i < numberOfThreads; i++) {
            thread[i] = new MyThread2(i, i, numberOfThreads);
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
    }

    public static class MyThread1 extends Thread {
        private final int id, start ,end;

        public MyThread1(int id, int start , int end) {
            this.id = id;
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            for(int i = start; i < end; i++){
                v3[i] = v1[i] + v2[i];
            }
        }
    }
    public static class MyThread2 extends Thread {
        private final int id, start ,step;

        public MyThread2(int id, int start , int step) {
            this.id = id;
            this.start = start;
            this.step = step;
        }

        @Override
        public void run() {
            for(int i = start; i < size; i+=step){
                v3[i] = v1[i] + v2[i];
            }
        }
    }
}
