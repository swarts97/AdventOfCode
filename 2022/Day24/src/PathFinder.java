import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class PathFinder {
    private final int HEIGHT; //22
    private final int WIDTH; //152
    private final int MAXTRY; //400
    private final int FAILURE_GAP; //400
    private final int FAILURE_OFFSET; //20
    private final Point start;
    private final Point goal;

    public PathFinder(int HEIGHT, int WIDTH, int MAXTRY, int FAILURE_GAP, int FAILURE_OFFSET, Point start, Point goal) {
        this.HEIGHT = HEIGHT;
        this.WIDTH = WIDTH;
        this.MAXTRY = MAXTRY;
        this.FAILURE_GAP = FAILURE_GAP;
        this.FAILURE_OFFSET = FAILURE_OFFSET;
        this.start = start;
        this.goal = goal;
    }

    public int getResult(Character[][][] mtx, int timePassed) {
        Set<Fieldv2> lastMinuteFields = new HashSet<>();
        Fieldv2 initialField = new Fieldv2(start);
        lastMinuteFields.add(initialField);

        for (int time = timePassed + 1; time < timePassed + MAXTRY; time++) {
            Set<Fieldv2> nextIterationFields = new HashSet<>();
            for (Fieldv2 lastMinuteField : lastMinuteFields) {
                nextIterationFields.addAll(lastMinuteField.getNextIterationFields(mtx[time]));
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
                int result = finalField.getMinutesPassed() + 1;
                System.out.println("================================");
                System.out.println("Solution found: " + result);
                System.out.println("================================");
                /*
                System.out.println(result - 2);
                printMtx(mtx, result - 2);

                System.out.println(result - 1);
                printMtx(mtx, result - 1);

                System.out.println(result);
                printMtx(mtx, result);
                */
                return result;
            }
        }
        return -1;
    }

    private boolean headingTooSlow(Point goal, Fieldv2 field) {
        int totalDistance = WIDTH - 3 + HEIGHT - 3 + 1;
        int distanceFromGoal = getDistanceFromGoal(goal, field.getMyPosition());
        int distanceFromStart = totalDistance - distanceFromGoal;
        int drawbackFromIdeal = field.getMinutesPassed() - distanceFromStart;
        //Heuristics from testinput calculations
        int failureGap = FAILURE_GAP;
        int maximumAllowedPathLength = totalDistance + failureGap;

        int acceptableDrawbackMinutes = failureGap * field.getMinutesPassed() / maximumAllowedPathLength + FAILURE_OFFSET;
        return drawbackFromIdeal > acceptableDrawbackMinutes;
    }

    private int getDistanceFromGoal(Point goal, Point myPosition) {
        return goal.x - myPosition.x + goal.y - myPosition.y;
    }

    private void printMtx(Character[][][] mtx, int time) {
        for (int j = 0; j < HEIGHT; j++) {
            for (int i = 0; i < WIDTH; i++) {
                System.out.print(mtx[time][j][i]);
            }
            System.out.println();
        }
        System.out.println();
    }
}
