import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Blizzard {

    private Character character;
    private Direction direction;
    private Point position;

    public Blizzard(Character character, Direction direction, Point position) {
        this.character = character;
        this.direction = direction;
        this.position = position;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public void moveOne() {
        switch (direction) {
            case UP -> position.y--;
            case DOWN -> position.y++;
            case RIGHT -> position.x++;
            case LEFT -> position.x--;
        }
    }

    public int x() {
        return position.x;
    }

    public int y() {
        return position.y;
    }
}
