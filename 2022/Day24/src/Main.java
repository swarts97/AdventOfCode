import java.awt.*;
import java.io.*;

public class Main {
    static final int WIDTH = 152; //152
    static final int HEIGHT = 22; // 22
    static final int MAXTRY = 450;

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("2022/Day24/input.txt"));
            FieldCalculator fieldCalculator = new FieldCalculator(WIDTH, HEIGHT, 1200);
            fieldCalculator.initMtx(reader);
            Character[][][] mtx = fieldCalculator.getMtx();

            Point firstStart = new Point(1, 0);
            Point firstGoal = new Point(WIDTH - 2, HEIGHT - 1);
            PathFinder firstPathFinder = new PathFinder(HEIGHT, WIDTH, MAXTRY, 200, 30, firstStart, firstGoal);
            int firstResult = firstPathFinder.getResult(mtx, 0);
            //processResult(firstResult);

            Point secondStart = new Point(WIDTH - 2, HEIGHT - 1);
            Point secondGoal = new Point(1, 0);
            PathFinder secondPathFinder = new PathFinder(HEIGHT, WIDTH, MAXTRY, 250, 40, secondStart, secondGoal);
            int secondResult = secondPathFinder.getResult(mtx, firstResult);
            //processResult(secondResult);

            PathFinder thirdPathFinder = new PathFinder(HEIGHT, WIDTH, MAXTRY, 200, 30, firstStart, firstGoal);
            int thirdResult = thirdPathFinder.getResult(mtx, firstResult + secondResult);
            //processResult(thirdResult);
            processResult(firstResult + secondResult + thirdResult);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processResult(int result) throws IOException {
        File outputFile = new File("2022/Day24/output.txt");
        outputFile.createNewFile();
        FileWriter myWriter = new FileWriter(outputFile);
        myWriter.write(String.valueOf(result));
        System.out.println(result);
        myWriter.close();
    }
}