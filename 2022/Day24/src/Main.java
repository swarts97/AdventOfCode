import java.awt.*;
import java.io.*;

public class Main {
    static final int WIDTH = 152; //152
    static final int HEIGHT = 22; // 22
    static final int MAXTRY = 400;
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("2022/Day24/input.txt"));
            FieldCalculator fieldCalculator = new FieldCalculator(WIDTH, HEIGHT);
            fieldCalculator.initMtx(reader);
            Character[][][] mtx = fieldCalculator.getMtx();

            Point firstGoal = new Point(WIDTH - 2, HEIGHT - 2);
            PathFinder pathFinder = new PathFinder(HEIGHT, WIDTH, MAXTRY, 400, 30, firstGoal);
            int result = pathFinder.getResult(mtx, 0);
            processResult(result);
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