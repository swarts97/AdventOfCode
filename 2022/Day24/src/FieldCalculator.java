import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.*;

public class FieldCalculator {
    private final int HEIGHT; //22
    private final int WIDTH; //152

    private final Character[][][] mtx;
    private final List<Blizzard> blizzards = new ArrayList<>();

    public FieldCalculator(int width, int height) {
        WIDTH = width;
        HEIGHT = height;
        mtx = new Character[400][HEIGHT][WIDTH];
    }

    public Character[][][] getMtx() {
        return mtx;
    }

    public void initMtx(BufferedReader reader) throws IOException {
        String line;
        for (int j = 0; j < HEIGHT; j++) {
            line = reader.readLine();
            if (!line.isBlank()) {
                char[] charactersInLine = line.toCharArray();
                for (int i = 0; i < WIDTH; i++) {
                    Character currectCharacter = charactersInLine[i];
                    mtx[0][j][i] = currectCharacter;
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
        for(int i = 1; i < 400; i++) {
            moveBlizzardsAndUpdateMtxFields(i);
        }
    }

    private void moveBlizzardsAndUpdateMtxFields(int time) {
        clearMtx(time);
        for (Blizzard blizzard : blizzards) {
            blizzard.moveOne();
            handleTeleportationIfNeeded(time, blizzard);
            updateField(time, blizzard.getPosition(), blizzard.getCharacter());
        }
    }

    private void clearMtx(int time) {
        //Top line
        for (int i = 0; i < WIDTH; i++) {
            Character characterToSet = i == 1 ? '.' : '#';
            setMtx(time, i, 0, characterToSet);
        }
        //Bottom line
        for (int i = 0; i < WIDTH; i++) {
            //Character characterToSet = i == WIDTH - 2 ? '.' : '#';
            Character characterToSet = '#';
            setMtx(time, i, HEIGHT - 1, characterToSet);
        }
        //Left line
        for (int j = 0; j < HEIGHT; j++) {
            Character characterToSet = '#';
            setMtx(time, 0, j, characterToSet);
        }
        //Right line
        for (int j = 0; j < HEIGHT; j++) {
            Character characterToSet = '#';
            setMtx(time, WIDTH - 1, j, characterToSet);
        }
        for (int j = 1; j < HEIGHT - 1; j++) {
            for (int i = 1; i < WIDTH - 1; i++) {
                setMtx(time, i, j, '.');
            }
        }
    }

    private void handleTeleportationIfNeeded(int time, Blizzard blizzard) {
        if (getMtx(time, blizzard.getPosition()) == '#') {
            switch (blizzard.getDirection()) {
                case UP -> blizzard.setPosition(new Point(blizzard.x(), HEIGHT - 2));
                case DOWN -> blizzard.setPosition(new Point(blizzard.x(), 1));
                case RIGHT -> blizzard.setPosition(new Point(1, blizzard.y()));
                case LEFT -> blizzard.setPosition(new Point(WIDTH - 2, blizzard.y()));
            }
        }
    }

    private void updateField(int time, Point position, Character character) {
        Character currentFieldValue = getMtx(time, position);
        if (currentFieldValue == '.') {
            setMtx(time, position, character);
        }
        else if (currentFieldValue == '^' || currentFieldValue == 'v' || currentFieldValue == '>' || currentFieldValue == '<') {
            setMtx(time, position, '2');
        }
        else if (Character.isDigit(currentFieldValue)) {
            int valueOfNumber = Character.getNumericValue(currentFieldValue);
            setMtx(time, position, Character.forDigit(++valueOfNumber, 10));
        }
    }

    private void setMtx(int time, int x, int y, Character character) {
        mtx[time][y][x] = character;
    }

    private void setMtx(int time, Point position, Character character) {
        mtx[time][position.y][position.x] = character;
    }

    private Character getMtx(int time, Point position) {
        return mtx[time][position.y][position.x];
    }

    public void printAllMtx() {
        for (int i = 0; i <= 18; i++) {
            printMtx(i);
        }
    }

    public void printMtx(int time) {
        System.out.println("Minute " + time);
        for (int j = 0; j < HEIGHT; j++) {
            for (int i = 0; i < WIDTH; i++) {
                System.out.print(mtx[time][j][i]);
            }
            System.out.println();
        }
        System.out.println();
    }
}
