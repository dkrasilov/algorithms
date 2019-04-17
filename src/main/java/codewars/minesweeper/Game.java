package codewars.minesweeper;

public class Game {
    public static int open(int x, int y) {
        return board.cells.stream().filter(cell -> cell.y == y && cell.x == x)
                .findAny()
                .map(cell -> cell.minesAround)
                .orElseThrow(IllegalStateException::new);
    }
    
    private static Board board = Board.parse("0 0 0 1 x 1 1 x 1 0 0 0 0 0 1 1 1 0 0 1 x 3 x 3 1 2 1\n" +
            "1 1 0 1 1 1 1 1 1 0 0 0 0 0 1 x 1 1 1 2 1 3 x 3 x 2 x\n" +
            "x 2 1 1 0 0 0 0 0 0 1 1 1 0 1 1 1 1 x 1 0 2 2 3 1 3 2\n" +
            "1 2 x 1 0 0 0 0 0 0 1 x 1 0 0 0 0 1 1 1 0 1 x 2 1 2 x\n" +
            "0 1 1 1 0 0 0 0 0 0 1 1 1 0 0 0 0 0 0 0 0 1 2 3 x 2 1\n" +
            "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 x 2 1 0", 16);
}
