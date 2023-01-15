import java.awt.*;
import java.util.*;
import java.util.List;

public class Fieldv2 {

    private Point myPosition;
    private int minutesPassed = 0;

    public Fieldv2(Fieldv2 original) {
        this.myPosition = (Point) original.myPosition.clone();
        this.minutesPassed = original.minutesPassed + 1;
    }

    public Fieldv2(Point start) {
        myPosition = start;
    }

    public int getMinutesPassed() {
        return minutesPassed;
    }

    public Point getMyPosition() {
        return myPosition;
    }

    ///

    public Set<Fieldv2> getNextIterationFields(Character[][] mtx) {
        Fieldv2 copyForDirectionRight = null;
        Fieldv2 copyForDirectionDown = null;
        Fieldv2 copyForMyPosition = null;
        Fieldv2 copyForDirectionUp = null;
        Fieldv2 copyForDirectionLeft = null;

        Point pointToDirectionRight = getNeighbourPoint(Direction.RIGHT);
        if (isPointFree(pointToDirectionRight, mtx)) {
            //System.out.println("Minute " + time + ", move right:");
            copyForDirectionRight = new Fieldv2(this);
            copyForDirectionRight.movePlayer(pointToDirectionRight, mtx);
        }

        Point pointToDirectionDown = getNeighbourPoint(Direction.DOWN);
        if (isPointFree(pointToDirectionDown, mtx)) {
            //System.out.println("Minute " + time + ", move down:");
            copyForDirectionDown = new Fieldv2(this);
            copyForDirectionDown.movePlayer(pointToDirectionDown, mtx);
        }

        if (isPointFree(myPosition, mtx)) {
            //System.out.println("Minute " + time + ", wait:");
            copyForMyPosition = new Fieldv2(this);
            copyForMyPosition.movePlayer(myPosition, mtx);
        }

        Point pointToDirectionUp = getNeighbourPoint(Direction.UP);
        if (isPointFree(pointToDirectionUp, mtx)) {
            //System.out.println("Minute " + time + ", move up:");
            copyForDirectionUp = new Fieldv2(this);
            copyForDirectionUp.movePlayer(pointToDirectionUp, mtx);
        }

        Point pointToDirectionLeft = getNeighbourPoint(Direction.LEFT);
        if (isPointFree(pointToDirectionLeft, mtx)) {
            //System.out.println("Minute " + time + ", move left:");
            copyForDirectionLeft = new Fieldv2(this);
            copyForDirectionLeft.movePlayer(pointToDirectionLeft, mtx);
        }
        List<Fieldv2> results = Arrays.asList(copyForDirectionRight, copyForDirectionDown, copyForMyPosition, copyForDirectionUp, copyForDirectionLeft);
        List<Fieldv2> resultsListWithoutNulls =
                results.stream()
                .filter(Objects::nonNull)
                .toList();
        return new HashSet<>(resultsListWithoutNulls);
    }

    private Point getNeighbourPoint(Direction direction) {
        switch (direction) {
            case DOWN -> {
                if (myPosition.y == 6 - 1) {
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
                if (myPosition.x == 8 - 1) {
                    return null;
                }
                return new Point(myPosition.x + 1, myPosition.y);
            }
        }
        return null;
    }

    private boolean isPointFree(Point point, Character[][] mtx) {
        return point != null && getMtx(point, mtx) == '.';
    }

    private void movePlayer(Point point, Character[][] mtx) {
        myPosition.x = point.x;
        myPosition.y = point.y;
        //mtx[point.y][point.x] = 'E';
    }

    private Character getMtx(Point position, Character[][] mtx) {
        return mtx[position.y][position.x];
    }

    public void printMtx(Character[][] mtx) {
        for (int j = 0; j < 6; j++) {
            for (int i = 0; i < 8; i++) {
                System.out.print(mtx[j][i]);
            }
            System.out.println();
        }
        System.out.println();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fieldv2 fieldv2 = (Fieldv2) o;
        return minutesPassed == fieldv2.minutesPassed && myPosition.equals(fieldv2.myPosition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myPosition, minutesPassed);
    }
}
