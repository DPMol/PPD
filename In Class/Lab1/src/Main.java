import java.util.Random;

public class Main {


    public static void main(String[] args) {
        var testClass = new ThreadTesting();

        testClass.baselineTest();

        testClass.threads1Test();

        testClass.threads2Test();
    }
}

