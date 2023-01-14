import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Field {
    private int HEIGHT = 6; //22
    private int WIDTH = 8; //152
    private Character[][] mtx = new Character[HEIGHT][WIDTH];
    private List<Blizzard> blizzards = new ArrayList<>();
    private Point myPosition = new Point(1, 0);

    public Field(int HEIGHT, int WIDTH, Character[][] mtx, List<Blizzard> blizzards, Point myPosition) {
        this.HEIGHT = HEIGHT;
        this.WIDTH = WIDTH;
        this.mtx = mtx;
        this.blizzards = blizzards;
        this.myPosition = myPosition;
    }

    public Field() {
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public void setHEIGHT(int HEIGHT) {
        this.HEIGHT = HEIGHT;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public void setWIDTH(int WIDTH) {
        this.WIDTH = WIDTH;
    }

    public Character[][] getMtx() {
        return mtx;
    }

    public void setMtx(Character[][] mtx) {
        this.mtx = mtx;
    }

    public List<Blizzard> getBlizzards() {
        return blizzards;
    }

    public void setBlizzards(List<Blizzard> blizzards) {
        this.blizzards = blizzards;
    }

    public Point getMyPosition() {
        return myPosition;
    }

    public void setMyPosition(Point myPosition) {
        this.myPosition = myPosition;
    }

    ///

    public void doOneStep() {
        moveBlizzards();
        movePlayer();
        printMtx();
    }

    private void movePlayer() {
        mtx[myPosition.y][myPosition.x] = 'E';
    }

    private void moveBlizzards() {
        clearMtx();
        for (Blizzard blizzard : blizzards) {
            blizzard.moveOne();
            handleTeleportationIfNeeded(blizzard);
            updateField(blizzard.getPosition(), blizzard.getCharacter());
        }
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
        else if (currentFieldValue == '^' || currentFieldValue == 'V' || currentFieldValue == '>' || currentFieldValue == '<') {
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

    private Character setMtx(Point position, Character character) {
        return mtx[position.y][position.x] = character;
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
