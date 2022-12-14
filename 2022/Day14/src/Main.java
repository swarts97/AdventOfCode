import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static List<List<Character>> mtx = new ArrayList<>();
    public static final int HEIGHT = 200;
    public static final int WIDTH = 1000;
    public static final int FALLINGTIMEOUT = 250;
    public static final Character ROCK = '#';
    public static final Character AIR = '.';
    public static final Character SAND = 'o';
    public static Point sandCoord = new Point();
    public static int highestYCoord = -1;

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("2022/Day14/input.txt"));
            initMtx();
            parseInput(reader);
            drawFloor(highestYCoord + 2);
            int reachedDepth = -1;
            int sandCounter = 0;
            while (reachedDepth != 0) {
                reachedDepth = startOneSand();
                sandCounter++;
            }
            visualize();

            reader.close();
            //Part 1
            //processResult(sandCounter - 1);
            processResult(sandCounter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int startOneSand() {
        setMtx(500, 0, SAND);
        sandCoord.x = 500;
        sandCoord.y = 0;
        boolean sandCanMove = canSandMove();
        int depth = 0;
        while (sandCanMove) {
            moveSand();
            depth++;
            sandCanMove = canSandMove();
            //Part 1
            //if (depth == FALLINGTIMEOUT) {
            //    return depth;
            //}
        }
        return depth;
    }

    /**
     * Return true if sand was moved, otherwise false
     */
    private static void moveSand() {
        if (isAir(getBottom())) {
            moveBottom();
        } else if (isAir(getBottomLeft())) {
            moveBottomLeft();
        } else if (isAir(getBottomRight())) {
            moveBottomRight();
        }
    }

    private static void parseInput(BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.isBlank()) {
                processOneLine(line);
            }
        }
    }

    private static void processOneLine(String line) {
        List<Point> points = convertOneLineIntoPoints(line);
        for (int i = 0; i < points.size() - 1; i++) {
            drawOneLine(points.get(i), points.get(i + 1));
        }
    }

    private static List<Point> convertOneLineIntoPoints(String line) {
        List<Point> points = new ArrayList<>();
        String[] coords = line.split(" -> ");
        for (String coord : coords) {
            String[] coordValues = coord.split(",");
            int x = Integer.parseInt(coordValues[0]);
            int y = Integer.parseInt(coordValues[1]);
            points.add(new Point(x, y));

            if (y > highestYCoord) {
                highestYCoord = y;
            }
        }
        return points;
    }

    private static void drawOneLine(Point start, Point end) {
        if (start.x == end.x) {
            drawVerticalLine(start, end);
        }
        else if (start.y == end.y) {
            drawHorizontalLine(start, end);
        }
        else {
            throw new IllegalArgumentException("drawOneLine bad argument");
        }
    }

    private static void drawVerticalLine(Point start, Point end) {
        int minimumY = Math.min(start.y, end.y);
        int maximumY = Math.max(start.y, end.y);
        int lineLength = maximumY - minimumY;
        for (int i = 0; i <= lineLength; i++)
            setMtx(start.x, minimumY + i, ROCK);
    }

    private static void drawHorizontalLine(Point start, Point end) {
        int minimumX = Math.min(start.x, end.x);
        int maximumX = Math.max(start.x, end.x);
        int lineLength = maximumX - minimumX;
        for (int i = 0; i <= lineLength; i++)
            setMtx(minimumX + i, start.y, ROCK);
    }

    private static void initMtx() {
        for (int i = 0; i < HEIGHT; i++) {
            List<Character> row = new ArrayList<>();
            for (int j = 0; j < WIDTH; j++) {
                row.add(AIR);
            }
            mtx.add(row);
        }
    }

    private static void drawFloor(int y) {
        for (int i = 0; i < WIDTH; i++) {
            setMtx(i, y, ROCK);
        }
    }

    private static Character getMtx(int x, int y) {
        return mtx.get(y).get(x);
    }
    private static Character getBottom() {
        return getMtx(sandCoord.x, sandCoord.y + 1);
    }
    private static Character getBottomLeft() {
        return getMtx(sandCoord.x - 1, sandCoord.y + 1);
    }
    private static Character getBottomRight() {
        return getMtx(sandCoord.x + 1, sandCoord.y + 1);
    }

    private static void moveBottom() {
        setMtx(sandCoord.x, sandCoord.y, AIR);
        sandCoord.y++;
        setMtx(sandCoord.x, sandCoord.y, SAND);
    }

    private static void moveBottomLeft() {
        setMtx(sandCoord.x, sandCoord.y, AIR);
        sandCoord.y++;
        sandCoord.x--;
        setMtx(sandCoord.x, sandCoord.y, SAND);
    }

    private static void moveBottomRight() {
        setMtx(sandCoord.x, sandCoord.y, AIR);
        sandCoord.y++;
        sandCoord.x++;
        setMtx(sandCoord.x, sandCoord.y, SAND);
    }

    private static boolean isAir(Character character) {
        return character == AIR;
    }

    private static boolean canSandMove() {
        Character bottom = getBottom();
        Character bottomLeft = getBottomLeft();
        Character bottomRight = getBottomRight();
        return isAir(bottom) || isAir(bottomLeft) || isAir(bottomRight);
    }

    private static void setMtx(int x, int y, Character c) {
        mtx.get(y).set(x, c);
    }

    private static void visualize() {
        printColumns();
        for (int i = 0; i < mtx.size(); i++) {
            List<Character> row = mtx.get(i);
            System.out.printf("%1$4s", i + " ");
            for (Character character : row) {
                System.out.print(character);
            }
            System.out.println();
        }
    }

    private static void printColumns() {
        System.out.print("    ");
        for (int i = 0; i < WIDTH; i++) {
            System.out.print(i / 100);
        }
        System.out.println();

        System.out.print("    ");
        for (int i = 0; i < WIDTH; i++) {
            System.out.print(i % 100 / 10);
        }
        System.out.println();

        System.out.print("    ");
        for (int i = 0; i < WIDTH; i++) {
            System.out.print(i % 10);
        }
        System.out.println();
    }

    private static void processResult(int result) throws IOException {
        File outputFile = new File("2022/Day14/output.txt");
        outputFile.createNewFile();
        FileWriter myWriter = new FileWriter(outputFile);
        myWriter.write(String.valueOf(result));
        System.out.println(result);
        myWriter.close();
    }
}