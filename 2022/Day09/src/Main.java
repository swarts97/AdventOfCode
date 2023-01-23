import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class Main {
    public static List<AbstractMap.SimpleEntry<Direction, Integer>> commands = new ArrayList<>();
    public static Set<Point> visitedPoints = new HashSet<>();
    public static List<Point> knots = new ArrayList<>();
    public static int sizeOfKnots = 2;
    public static Point startingPoint = new Point(0, 4);
    public static char[][] mtx = new char[5][6];

    public static void main(String[] args) {
        try {
            parseInput();
            initKnots(sizeOfKnots);
            executeCommands();
            //visualizeVisitedPoints(6, 5);
            processResult(visitedPoints.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void executeCommands() {
        for (AbstractMap.SimpleEntry<Direction, Integer> command : commands) {
            Direction direction = command.getKey();
            int numberOfSteps = command.getValue();
            for (int step = 0; step < numberOfSteps; step++) {
                moveOne(knots.get(0), direction);
                for (int i = 1; i < knots.size(); i++) {
                    moveKnotIfNeeded(knots.get(i), knots.get(i - 1));
                }
                //visualizeMtx(6, 5);
                visitedPoints.add(new Point(knots.get(sizeOfKnots - 1)));
            }
        }
    }

    private static void moveKnotIfNeeded(Point currentKnot, Point knotInFrontOfCurrentKnot) {
        int horizontalDifference = knotInFrontOfCurrentKnot.x - currentKnot.x;
        int verticalDifference = knotInFrontOfCurrentKnot.y - currentKnot.y;
        if (Math.abs(horizontalDifference) == 2) {
            currentKnot.x += horizontalDifference / 2;
            currentKnot.y = knotInFrontOfCurrentKnot.y;
        }
        else if (Math.abs(verticalDifference) == 2) {
            currentKnot.y += verticalDifference / 2;
            currentKnot.x = knotInFrontOfCurrentKnot.x;
        }
    }

    public static void moveOne(Point point, Direction direction) {
        switch (direction) {
            case UP -> point.y--;
            case DOWN -> point.y++;
            case RIGHT -> point.x++;
            case LEFT -> point.x--;
        }
    }

    private static void initKnots(int size) {
        for (int i = 0; i < size; i++) {
            knots.add(new Point(startingPoint.x, startingPoint.y));
        }
    }

    private static void parseInput() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("2022/Day09/input.txt"));
        String command;
        while ((command = reader.readLine()) != null) {
            if (!command.isBlank()) {
                commands.add(processCommand(command));
            }
        }
        reader.close();
    }

    private static AbstractMap.SimpleEntry<Direction, Integer> processCommand(String command) {
        String[] commandParts = command.split(" ");
        String directionPart = commandParts[0];
        String lengthPart = commandParts[1];
        Direction direction = Direction.NONE;
        switch (directionPart) {
            case "U" -> direction = Direction.UP;
            case "D" -> direction = Direction.DOWN;
            case "R" -> direction = Direction.RIGHT;
            case "L" -> direction = Direction.LEFT;
        }
        return new AbstractMap.SimpleEntry<>(direction, Integer.parseInt(lengthPart));
    }

    private static void visualizeMtx(int width, int height) {
        clearMtx(width, height);
        drawKnots();
        printMtx(width, height);
    }

    private static void visualizeVisitedPoints(int width, int height) {
        clearMtx(width, height);
        drawVisitedPoints();
        printMtx(width, height);
    }

    private static void clearMtx(int width, int height) {
        mtx = new char[height][width];
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                mtx[j][i] = '.';
            }
        }
    }

    private static void drawKnots() {
        for (int i = 0; i < knots.size(); i++) {
            mtx[knots.get(i).y][knots.get(i).x] = Character.forDigit(i, 10);
        }
    }

    private static void drawVisitedPoints() {
        for (Point point : visitedPoints) {
            mtx[point.y][point.x] = '#';
        }
    }

    private static void printMtx(int width, int height) {
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                System.out.print(mtx[j][i]);
            }
            System.out.println();
        }
        System.out.println();
    }



    private static void processResult(int highestCaloriesCount) throws IOException {
        File outputFile = new File("2022/Day09/output.txt");
        outputFile.createNewFile();
        FileWriter myWriter = new FileWriter(outputFile);
        myWriter.write(String.valueOf(highestCaloriesCount));
        System.out.println(highestCaloriesCount);
        myWriter.close();
    }
}