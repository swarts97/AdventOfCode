import java.awt.Point;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Main {
    public static int HEIGHT = 6; //22
    public static int WIDTH = 8; //152
    public static Character[][] mtx = new Character[HEIGHT][WIDTH];
    public static List<Blizzard> blizzards = new ArrayList<>();
    public static Point myPosition = new Point(0, 1);

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("2022/Day24/testinput.txt"));
            parseInput(reader);
            printMtx();
            for (int i = 0; i < 18; i++) {
                moveBlizzards();
                printMtx();
            }
            //processResult(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void moveBlizzards() {
        clearMtx();
        for (Blizzard blizzard : blizzards) {
            blizzard.moveOne();
            handleTeleportationIfNeeded(blizzard);
            updateField(blizzard.getPosition(), blizzard.getCharacter());
        }
    }

    private static void updateField(Point position, Character character) {
        Character currentFieldValue = getMtx(position);
        if (currentFieldValue == '.') {
            setMtx(position, character);
        }
        else if (currentFieldValue == '^' || currentFieldValue == 'V' || currentFieldValue == '>' || currentFieldValue == '<') {
            setMtx(position, '2');
        }
        else if (Character.isDigit(currentFieldValue)) {
            int valueOfNumber = Character.getNumericValue(currentFieldValue);
            setMtx(position, Character.forDigit(++valueOfNumber, 10));
        }
    }

    private static void handleTeleportationIfNeeded(Blizzard blizzard) {
        if (getMtx(blizzard.getPosition()) == '#') {
            switch (blizzard.getDirection()) {
                case UP -> blizzard.setPosition(new Point(blizzard.x(), HEIGHT - 2));
                case DOWN -> blizzard.setPosition(new Point(blizzard.x(), 1));
                case RIGHT -> blizzard.setPosition(new Point(1, blizzard.y()));
                case LEFT -> blizzard.setPosition(new Point(WIDTH - 2, blizzard.y()));
            }
        }
    }

//    private static Character getMtx(int x, int y) {
//        return mtx[y][x];
//    }

    private static Character setMtx(int x, int y, Character character) {
        return mtx[y][x] = character;
    }

    private static Character setMtx(Point position, Character character) {
        return mtx[position.y][position.x] = character;
    }

    private static Character getMtx(Point position) {
        return mtx[position.y][position.x];
    }

    private static void parseInput(BufferedReader reader) throws IOException {
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
        mtx[0][1] = 'E';
        reader.close();
    }

    private static void printMtx() {
        for (int j = 0; j < HEIGHT; j++) {
            for (int i = 0; i < WIDTH; i++) {
                System.out.print(mtx[j][i]);
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void clearMtx() {
        for (int j = 1; j < HEIGHT - 1; j++) {
            for (int i = 1; i < WIDTH - 1; i++) {
                setMtx(i, j, '.');
            }
        }
    }

    private static void processResult(List<Stack<Character>> stacks) throws IOException {
        File outputFile = new File("2022/Day24/output.txt");
        outputFile.createNewFile();
        FileWriter myWriter = new FileWriter(outputFile);

        StringBuilder result = new StringBuilder();
        for (Stack<Character> currentStack : stacks) {
            if (!currentStack.empty()) {
                result.append(currentStack.pop());
            } else {
                result.append(" ");
            }
        }
        myWriter.write(result.toString());
        System.out.println(result);
        myWriter.close();
    }
}