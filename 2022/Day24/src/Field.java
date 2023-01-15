import org.apache.commons.lang3.SerializationUtils;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Field {

    private int HEIGHT = 22; //22
    private int WIDTH = 152; //152

    private Character[][][] mtx = new Character[400][HEIGHT][WIDTH];
    private Point myPosition = new Point(1, 0);
    private int minutesPassed = 0;

    private boolean isGoalReached = false;

    public Field(Field original) {
        this.HEIGHT = original.HEIGHT;
        this.WIDTH = original.WIDTH;
        this.mtx = SerializationUtils.clone(original.mtx);
        this.myPosition = (Point) original.myPosition.clone();
        this.minutesPassed = original.minutesPassed;
        this.isGoalReached = original.isGoalReached;
    }

    public Field() {
    }

    public void setMtx(Character[][][] mtx) {
        this.mtx = mtx;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public int getMinutesPassed() {
        return minutesPassed;
    }

    ///

    private boolean headingTooSlow(Point goal) {
        int totalDistance = WIDTH - 3 + HEIGHT - 3 + 1;
        int distanceFromGoal = getDistanceFromGoal(goal);
        int distanceFromStart = totalDistance - distanceFromGoal;
        int drawbackFromIdeal = minutesPassed - distanceFromStart;
        //Heuristics from testinput calculations
        int failureGap = 200;
        int maximumAllowedPathLength = totalDistance + failureGap;

        int acceptableDrawbackMinutes = failureGap * minutesPassed / maximumAllowedPathLength + 20;
        return drawbackFromIdeal > acceptableDrawbackMinutes;
    }

    private int getDistanceFromGoal(Point goal) {
        return goal.x - myPosition.x + goal.y - myPosition.y;
    }

    public Field goToGoal(Point goal) {
        if (headingTooSlow(goal)) {

            System.out.print(getDistanceFromGoal(goal));
            System.out.print(" - ");
            System.out.println(minutesPassed);
            return null;
        }
        if (isGoalReached || goal.equals(myPosition)) {
            isGoalReached = true;
            printMtx(minutesPassed);
            System.out.println("================================");
            System.out.println("Solution found: " + minutesPassed);
            System.out.println("================================");
            return this;
        }
        Field resultForDown = null;
        Field resultForUp = null;
        Field resultForRight = null;
        Field resultForLeft = null;
        Field resultForNotMoving = null;
        //printMtx(minutesPassed);
        minutesPassed++;

        Point pointToDirectionDown = getNeighbourPoint(Direction.DOWN);
        if (isPointFree(minutesPassed, pointToDirectionDown)) {
            //System.out.println("Minute " + minutesPassed + ", move down:");
            Field copyForDirectionDown = new Field(this);
            copyForDirectionDown.movePlayer(minutesPassed, pointToDirectionDown);
            resultForDown = copyForDirectionDown.goToGoal(goal);
        }

        Point pointToDirectionRight = getNeighbourPoint(Direction.RIGHT);
        if (isPointFree(minutesPassed, pointToDirectionRight)) {
            //System.out.println("Minute " + minutesPassed + ", move right:");
            Field copyForDirectionRight = new Field(this);
            copyForDirectionRight.movePlayer(minutesPassed, pointToDirectionRight);
            resultForRight = copyForDirectionRight.goToGoal(goal);
        }

        Point pointToDirectionUp = getNeighbourPoint(Direction.UP);
        if (isPointFree(minutesPassed, pointToDirectionUp)) {
            //System.out.println("Minute " + minutesPassed + ", move up:");
            Field copyForDirectionUp = new Field(this);
            copyForDirectionUp.movePlayer(minutesPassed, pointToDirectionUp);
            resultForUp = copyForDirectionUp.goToGoal(goal);
        }

        Point pointToDirectionLeft = getNeighbourPoint(Direction.LEFT);
        if (isPointFree(minutesPassed, pointToDirectionLeft)) {
            //System.out.println("Minute " + minutesPassed + ", move left:");
            Field copyForDirectionLeft = new Field(this);
            copyForDirectionLeft.movePlayer(minutesPassed, pointToDirectionLeft);
            resultForLeft = copyForDirectionLeft.goToGoal(goal);
        }

        if (isPointFree(minutesPassed, myPosition)) {
            //System.out.println("Minute " + minutesPassed + ", wait:");
            Field copyForMyPosition = new Field(this);
            copyForMyPosition.movePlayer(minutesPassed, myPosition);
            resultForNotMoving = copyForMyPosition.goToGoal(goal);
        }

        List<Field> results = Arrays.asList(resultForUp, resultForDown, resultForLeft, resultForRight, resultForNotMoving);
        return results.stream()
                .filter(Objects::nonNull)
                .min(Comparator.comparingInt(Field::getMinutesPassed))
                .orElse(null);
    }

    private Point getNeighbourPoint(Direction direction) {
        switch (direction) {
            case DOWN -> {
                if (myPosition.y == HEIGHT - 1) {
                    return null;
                }
                return new Point(myPosition.x, myPosition.y + 1);
            }
            case UP -> {
                if (myPosition.y == 0) {
                    return null;
                }
                return new Point(myPosition.x, myPosition.y - 1);
            }
            case LEFT -> {
                if (myPosition.x == 0) {
                    return null;
                }
                return new Point(myPosition.x - 1, myPosition.y);
            }
            case RIGHT -> {
                if (myPosition.x == WIDTH - 1) {
                    return null;
                }
                return new Point(myPosition.x + 1, myPosition.y);
            }
        }
        return null;
    }

    private boolean isPointFree(int time, Point point) {
        return point != null && getMtx(time, point) == '.';
    }

    private void movePlayer(int time, Point point) {
        myPosition.x = point.x;
        myPosition.y = point.y;
        mtx[time][point.y][point.x] = 'E';
    }

    private Character getMtx(int time, Point position) {
        return mtx[time][position.y][position.x];
    }

    public void printMtx(int time) {
        for (int j = 0; j < HEIGHT; j++) {
            for (int i = 0; i < WIDTH; i++) {
                System.out.print(mtx[time][j][i]);
            }
            System.out.println();
        }
        System.out.println();
    }
}
