package codewars.minesweeper;

public class MineSweeper {
    private final Board board;
    
    public MineSweeper(final String board, final int nMines) {
        this.board = Board.parse(board, nMines);
    }
    
    public String solve() {
        final String s = board.toString();
        if (s.contains("?")) return "?";
        return s;
    }
}
