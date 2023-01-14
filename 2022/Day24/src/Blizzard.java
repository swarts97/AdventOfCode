import java.awt.Point;

public class Blizzard {

    private final Character character;
    private final Direction direction;
    private Point position;

    public Blizzard(Character character, Direction direction, Point position) {
        this.character = character;
        this.direction = direction;
        this.position = position;
    }

    public Blizzard(Blizzard original) {
        this.character = original.character;
        this.direction = original.direction;
        this.position  = (Point) original.position.clone();
    }

    public Character getCharacter() {
        return character;
    }

    public Direction getDirection() {
        return direction;
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
