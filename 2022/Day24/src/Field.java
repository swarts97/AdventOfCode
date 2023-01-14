import org.apache.commons.lang3.SerializationUtils;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Field {
    private int HEIGHT = 6; //22
    private int WIDTH = 8; //152
    private Character[][] mtx = new Character[HEIGHT][WIDTH];
    private List<Blizzard> blizzards = new ArrayList<>();
    private Point myPosition = new Point(1, 0);
    private int minutesPassed = 0;

    private boolean isGoalReached = false;

    public Field(Field original) {
        this.HEIGHT = original.HEIGHT;
        this.WIDTH = original.WIDTH;
        this.mtx = SerializationUtils.clone(original.mtx);
        this.myPosition = (Point) original.myPosition.clone();
        this.blizzards = new ArrayList<>();
        for (Blizzard blizzard : original.getBlizzards()) {
            this.blizzards.add(new Blizzard(blizzard));
        }
        this.minutesPassed = original.minutesPassed;
        this.isGoalReached = original.isGoalReached;
    }

    public Field() {
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public List<Blizzard> getBlizzards() {
        return blizzards;
    }

    public int getMinutesPassed() {
        return minutesPassed;
    }

    ///

    public Field goToGoal(Point goal) {
        if (minutesPassed > 25) {
            return null;
        }
        if (isGoalReached || goal.equals(myPosition)) {
            isGoalReached = true;
            printMtx();
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
        printMtx();
        moveBlizzards();

        Point pointToDirectionDown = getNeighbourPoint(Direction.DOWN);
        if (isPointFree(pointToDirectionDown)) {
            System.out.println("Minute " + minutesPassed + ", move down:");
            if (minutesPassed == 17) {
                System.out.println(minutesPassed);
            }
            Field copyForDirectionDown = new Field(this);
            copyForDirectionDown.movePlayer(pointToDirectionDown);
            resultForDown = copyForDirectionDown.goToGoal(goal);
        }

        Point pointToDirectionRight = getNeighbourPoint(Direction.RIGHT);
        if (isPointFree(pointToDirectionRight)) {
            System.out.println("Minute " + minutesPassed + ", move right:");
            Field copyForDirectionRight = new Field(this);
            copyForDirectionRight.movePlayer(pointToDirectionRight);
            resultForRight = copyForDirectionRight.goToGoal(goal);
        }

        Point pointToDirectionUp = getNeighbourPoint(Direction.UP);
        if (isPointFree(pointToDirectionUp)) {
            System.out.println("Minute " + minutesPassed + ", move up:");
            Field copyForDirectionUp = new Field(this);
            copyForDirectionUp.movePlayer(pointToDirectionUp);
            resultForUp = copyForDirectionUp.goToGoal(goal);
        }

        Point pointToDirectionLeft = getNeighbourPoint(Direction.LEFT);
        if (isPointFree(pointToDirectionLeft)) {
            System.out.println("Minute " + minutesPassed + ", move left:");
            Field copyForDirectionLeft = new Field(this);
            copyForDirectionLeft.movePlayer(pointToDirectionLeft);
            resultForLeft = copyForDirectionLeft.goToGoal(goal);
        }

        if (isPointFree(myPosition)) {
            System.out.println("Minute " + minutesPassed + ", wait:");
            Field copyForMyPosition = new Field(this);
            copyForMyPosition.movePlayer(myPosition);
            resultForNotMoving = copyForMyPosition.goToGoal(goal);
        }

        List<Field> results = Arrays.asList(resultForUp, resultForDown, resultForLeft, resultForRight, resultForNotMoving);
        if (results.stream().allMatch(Objects::isNull)) {
            System.out.println();
            System.out.println();
        }
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

    private boolean isPointFree(Point point) {
        return point != null && getMtx(point) == '.';
    }

    private void movePlayer(Point point) {
        myPosition.x = point.x;
        myPosition.y = point.y;
        mtx[point.y][point.x] = 'E';
    }

    private void moveBlizzards() {
        clearMtx();
        for (Blizzard blizzard : blizzards) {
            blizzard.moveOne();
            handleTeleportationIfNeeded(blizzard);
            updateField(blizzard.getPosition(), blizzard.getCharacter());
        }
        minutesPassed++;
    }

    private void clearMtx() {
        for (int j = 1; j < HEIGHT - 1; j++) {
            for (int i = 1; i < WIDTH - 1; i++) {
                setMtx(i, j, '.');
            }
        }
    }

    private void handleTeleportationIfNeeded(Blizzard blizzard) {
        if (getMtx(blizzard.getPosition()) == '#') {
            switch (blizzard.getDirection()) {
                case UP -> blizzard.setPosition(new Point(blizzard.x(), HEIGHT - 2));
                case DOWN -> blizzard.setPosition(new Point(blizzard.x(), 1));
                case RIGHT -> blizzard.setPosition(new Point(1, blizzard.y()));
                case LEFT -> blizzard.setPosition(new Point(WIDTH - 2, blizzard.y()));
            }
        }
    }

    private void updateField(Point position, Character character) {
        Character currentFieldValue = getMtx(position);
        if (currentFieldValue == '.') {
            setMtx(position, character);
        }
        else if (currentFieldValue == '^' || currentFieldValue == 'v' || currentFieldValue == '>' || currentFieldValue == '<') {
            setMtx(position, '2');
        }
        else if (Character.isDigit(currentFieldValue)) {
            int valueOfNumber = Character.getNumericValue(currentFieldValue);
            setMtx(position, Character.forDigit(++valueOfNumber, 10));
        }
    }

//    private Character getMtx(int x, int y) {
//        return mtx[y][x];
//    }

    private void setMtx(int x, int y, Character character) {
        mtx[y][x] = character;
    }

    private void setMtx(Point position, Character character) {
        mtx[position.y][position.x] = character;
    }

    private Character getMtx(Point position) {
        return mtx[position.y][position.x];
    }

    public void printMtx() {
        for (int j = 0; j < HEIGHT; j++) {
            for (int i = 0; i < WIDTH; i++) {
                System.out.print(mtx[j][i]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public void initFromInput(BufferedReader reader) throws IOException {
        String line;
        for (int j = 0; j < HEIGHT; j++) {
            line = reader.readLine();
            if (!line.isBlank()) {
                char[] charactersInLine = line.toCharArray();
                for (int i = 0; i < WIDTH; i++) {
                    Character currectCharacter = charactersInLine[i];
                    mtx[j][i] = currectCharacter;
                    switch (currectCharacter) {
                        case '^' -> blizzards.add(new Blizzard(currectCharacter, Direction.UP, new Point(i, j)));
                        case 'v' -> blizzards.add(new Blizzard(currectCharacter, Direction.DOWN, new Point(i, j)));
                        case '>' -> blizzards.add(new Blizzard(currectCharacter, Direction.RIGHT, new Point(i, j)));
                        case '<' -> blizzards.add(new Blizzard(currectCharacter, Direction.LEFT, new Point(i, j)));
                    }
                }
            }
        }
        reader.close();
    }
}
