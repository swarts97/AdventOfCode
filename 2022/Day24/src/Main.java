import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static final int WIDTH = 8; //152
    static final int HEIGHT = 6; // 22
    static final int MAXTRY = 20;
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("2022/Day24/testinput.txt"));

            FieldCalculator fieldCalculator = new FieldCalculator(WIDTH, HEIGHT);
            fieldCalculator.initMtx(reader);
            Character[][][] mtx = fieldCalculator.getMtx();

            Point goal = new Point(WIDTH - 2, HEIGHT - 2);
            List<Fieldv2> lastMinuteFields = new ArrayList<>();
            Fieldv2 field = new Fieldv2();
            lastMinuteFields.add(field);
            int result = 0;

            for (int time = 1; time < MAXTRY; time++) {
                List<Fieldv2> nextIterationFields = new ArrayList<>();
                for (int i = 0; i < lastMinuteFields.size(); i++) {
                    Fieldv2 currentField = lastMinuteFields.get(i);
                    nextIterationFields.addAll(currentField.getNextIterationFields(mtx[time]));
                }
                lastMinuteFields = nextIterationFields;
                lastMinuteFields.removeIf(lastMinuteField -> headingTooSlow(goal, lastMinuteField));
                for (Fieldv2 lmf : lastMinuteFields) {
                    System.out.println("DistFromGoal: " + getDistanceFromGoal(goal, lmf.getMyPosition()) + " minutesPassed: " + lmf.getMinutesPassed());
                }
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

    private static boolean headingTooSlow(Point goal, Fieldv2 field) {
        int totalDistance = WIDTH - 3 + HEIGHT - 3 + 1;
        int distanceFromGoal = getDistanceFromGoal(goal, field.getMyPosition());
        int distanceFromStart = totalDistance - distanceFromGoal;
        int drawbackFromIdeal = field.getMinutesPassed() - distanceFromStart;
        //Heuristics from testinput calculations
        int failureGap = 10;
        int maximumAllowedPathLength = totalDistance + failureGap;

        int acceptableDrawbackMinutes = failureGap * field.getMinutesPassed() / maximumAllowedPathLength + 5;
        return drawbackFromIdeal > acceptableDrawbackMinutes;
    }

    private static int getDistanceFromGoal(Point goal, Point myPosition) {
        return goal.x - myPosition.x + goal.y - myPosition.y;
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