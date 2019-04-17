package codewars.minesweeper;

import java.util.StringJoiner;

public class Cell {
    int x, y;
    Type type;
    int minesAround;
    
    Cell(int x, int y, Type type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.minesAround = 0;
    }
    
    Cell(int x, int y, Type type, int minesAround) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.minesAround = minesAround;
    }
    
    enum Type {
        BOMB, UNKNOWN, BLANK
    }
    
    @Override
    public String toString() {
        return new StringJoiner(", ", Cell.class.getSimpleName() + "[", "]")
                .add("x=" + x)
                .add("y=" + y)
                .add("type=" + type)
                .add("minesAround=" + minesAround)
                .toString();
    }
}
