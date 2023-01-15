import java.awt.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("2022/Day24/input.txt"));
            FieldCalculator fieldCalculator = new FieldCalculator();
            fieldCalculator.initMtx(reader);

            Field field = new Field();
            field.setMtx(fieldCalculator.getMtx());
            Field fieldWhenReachingGoal = field.goToGoal(new Point(field.getWIDTH() - 2, field.getHEIGHT() - 2));
            processResult(fieldWhenReachingGoal.getMinutesPassed() + 1);
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