import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("2022/Day24/testinput.txt"));
            FieldCalculator fieldCalculator = new FieldCalculator();
            fieldCalculator.initMtx(reader);
            Character[][][] mtx = fieldCalculator.getMtx();

            Point goal = new Point(8 - 2, 6 - 2);
            List<Fieldv2> lastMinuteFields = new ArrayList<>();
            Fieldv2 field = new Fieldv2();
            lastMinuteFields.add(field);
            int result = 0;

            for (int time = 1; time < 20; time++) {
                List<Fieldv2> nextIterationFields = new ArrayList<>();
                for (int i = 0; i < lastMinuteFields.size(); i++) {
                    Fieldv2 currentField = lastMinuteFields.get(i);
                    nextIterationFields.addAll(currentField.getNextIterationFields(mtx[time]));
                }
                lastMinuteFields = nextIterationFields;
                if (lastMinuteFields.stream().anyMatch(fieldv2 -> goal.equals(fieldv2.getMyPosition()))) {
                    Fieldv2 finalField = lastMinuteFields.stream()
                            .filter(fieldv2 -> goal.equals(fieldv2.getMyPosition()))
                            .findFirst().get();
                    result = finalField.getMinutesPassed() + 1;
                    System.out.println("================================");
                    System.out.println("Solution found: " + result);
                    System.out.println("================================");
                    break;
                }
            }
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