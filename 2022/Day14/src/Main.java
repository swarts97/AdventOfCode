import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static List<List<Character>> mtx = new ArrayList<>();
    public static final int LIMIT = 10;
    public static final int OFFSET = 494;
    public static final Character ROCK = '#';
    public static final Character AIR = '.';
    public static final Character SAND = 'o';

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("2022/Day14/testinput.txt"));
            initMtx();
            parseInput(reader);
            visualize();

            reader.close();
            //processResult(result);
        } catch (IOException e) {
            e.printStackTrace();
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
            int x = Integer.parseInt(coordValues[0]) - OFFSET;
            int y = Integer.parseInt(coordValues[1]);
            points.add(new Point(x, y));
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
        for (int i = 0; i < LIMIT; i++) {
            List<Character> row = new ArrayList<>();
            for (int j = 0; j < LIMIT; j++) {
                row.add(AIR);
            }
            mtx.add(row);
        }
    }

    private static Character getMtx(int x, int y) {
        return mtx.get(y).get(x);
    }

    private static void setMtx(int x, int y, Character c) {
        mtx.get(y).set(x, c);
    }

    private static void visualize() {
        printColumns();
        for (int i = 0; i < mtx.size(); i++) {
            List<Character> row = mtx.get(i);
            System.out.print(String.format("%1$4s", i + " "));
            for (int j = 0; j < row.size(); j++) {
                System.out.print(row.get(j));
            }
            System.out.println();
        }
    }

    private static void printColumns() {
        System.out.print("    ");
        for (int i = OFFSET; i < OFFSET + LIMIT; i++) {
            System.out.print(i / 100);
        }
        System.out.println();

        System.out.print("    ");
        for (int i = OFFSET; i < OFFSET + LIMIT; i++) {
            System.out.print(i % 100 / 10);
        }
        System.out.println();

        System.out.print("    ");
        for (int i = OFFSET; i < OFFSET + LIMIT; i++) {
            System.out.print(i % 10);
        }
        System.out.println();
    }

    private static void processResult(int highestCaloriesCount) throws IOException {
        File outputFile = new File("2022/Day14/output.txt");
        outputFile.createNewFile();
        FileWriter myWriter = new FileWriter(outputFile);
        myWriter.write(String.valueOf(highestCaloriesCount));
        System.out.println(highestCaloriesCount);
        myWriter.close();
    }
}