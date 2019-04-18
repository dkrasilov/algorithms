package codewars.minesweeper;

public class Game {

    private static final String boardHardSomeSolve = "0 0 0 1 x 1 1 x 1 0 0 0 0 0 1 1 1 0 0 1 x 3 x 3 1 2 1\n" +
            "1 1 0 1 1 1 1 1 1 0 0 0 0 0 1 x 1 1 1 2 1 3 x 3 x 2 x\n" +
            "x 2 1 1 0 0 0 0 0 0 1 1 1 0 1 1 1 1 x 1 0 2 2 3 1 3 2\n" +
            "1 2 x 1 0 0 0 0 0 0 1 x 1 0 0 0 0 1 1 1 0 1 x 2 1 2 x\n" +
            "0 1 1 1 0 0 0 0 0 0 1 1 1 0 0 0 0 0 0 0 0 1 2 3 x 2 1\n" +
            "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 x 2 1 0";
    private static String boardSampleUnsolvable = "1 1 0 1 1 1 0 0 1 1 1 0 0 0 0 1 1 1 0\n" +
            "x 1 0 1 x 1 0 0 2 x 2 0 0 0 0 1 x 2 1\n" +
            "1 1 0 2 3 3 1 1 3 x 2 0 0 0 0 1 2 x 1\n" +
            "0 1 1 2 x x 1 2 x 3 1 0 0 0 0 0 1 1 1\n" +
            "0 1 x 2 2 2 1 3 x 3 0 0 0 0 0 0 0 0 0\n" +
            "0 1 1 1 0 0 0 2 x 2 0 0 0 0 0 0 0 0 0\n" +
            "0 0 0 0 0 0 0 1 1 1 1 2 2 1 0 0 0 0 0\n" +
            "0 0 0 0 0 0 0 0 0 0 1 x x 1 0 0 0 0 0\n" +
            "0 0 1 1 1 0 1 1 1 0 1 2 2 1 0 0 0 0 0\n" +
            "0 0 1 x 2 1 3 x 2 0 0 0 0 0 0 1 1 1 0\n" +
            "0 0 1 1 2 x 3 x 3 1 1 0 0 0 0 1 x 1 0\n" +
            "0 0 0 0 1 2 3 2 2 x 1 0 0 0 0 1 1 1 0\n" +
            "0 0 0 0 0 1 x 1 1 1 1 0 0 0 0 0 1 1 1\n" +
            "0 0 1 1 2 2 2 1 0 0 0 0 0 0 0 0 1 x 1\n" +
            "0 0 1 x 2 x 2 1 1 0 0 0 0 0 0 0 1 1 1\n" +
            "0 0 1 1 2 1 3 x 3 1 0 0 0 0 0 0 0 1 1\n" +
            "0 0 0 0 0 0 2 x x 1 0 0 0 1 1 1 0 1 x\n" +
            "0 0 0 1 1 1 1 2 2 1 0 0 0 1 x 1 1 2 2\n" +
            "0 0 0 1 x 3 2 1 0 0 0 1 1 2 1 1 1 x 2\n" +
            "0 0 0 1 2 x x 1 0 0 0 1 x 1 0 1 2 3 x\n" +
            "0 0 0 0 1 2 2 1 1 1 1 1 1 1 0 1 x 3 2\n" +
            "0 0 0 0 1 1 1 1 2 x 1 1 1 1 0 2 3 x 2\n" +
            "0 0 0 0 1 x 1 1 x 2 1 1 x 1 0 1 x 3 x";

    private static String game3_23 = "0 0 0 0 0 0 0 1 1 1\n" +
            "1 1 1 1 1 1 0 2 x 2\n" +
            "1 x 2 2 x 1 0 2 x 2\n" +
            "1 1 2 x 2 1 0 1 1 1\n" +
            "0 0 2 2 2 1 1 1 0 0\n" +
            "0 0 1 x 1 1 x 2 1 1\n" +
            "0 0 1 1 2 2 2 3 x 2\n" +
            "0 0 0 0 1 x 1 2 x 2\n" +
            "0 0 0 0 1 1 1 1 1 1\n" +
            "0 0 0 1 2 2 1 0 0 0\n" +
            "0 0 0 1 x x 1 0 0 0\n" +
            "0 0 0 1 2 2 1 0 0 0\n" +
            "0 0 0 0 0 0 0 0 0 0\n" +
            "0 0 0 0 0 0 0 0 0 0\n" +
            "1 1 0 1 1 1 0 0 0 0\n" +
            "x 1 0 1 x 1 0 0 0 0\n" +
            "2 3 1 3 2 2 1 1 1 0\n" +
            "x 2 x 2 x 1 1 x 2 1\n" +
            "1 2 1 2 1 1 1 2 x 1\n" +
            "0 0 1 1 1 0 0 1 1 1\n" +
            "0 0 1 x 1 1 1 2 2 2\n" +
            "0 0 1 1 1 1 x 2 x x\n" +
            "0 0 0 0 0 1 1 2 2 2";
    private static Board board = Board.parse(game3_23, 23);

    public static int open(int x, int y) {
        return board.cells.stream().filter(cell -> cell.y == y && cell.x == x)
                .findAny()
                .map(cell -> cell.minesAround)
                .orElseThrow(IllegalStateException::new);
    }
}
