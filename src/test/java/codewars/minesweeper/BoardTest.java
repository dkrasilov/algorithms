package codewars.minesweeper;

import codewars.minesweeper.Board;
import org.junit.Test;


import static org.junit.Assert.*;

public class BoardTest {
    
    @Test
    public void parse() {
        final Board board = Board.parse("1 x 1 1 x 1\n2 2 2 1 2 2\n2 x 2 0 1 x\n2 x 2 1 2 2\n1 1 1 1 x 1\n0 0 0 1 1 1", 12);
        assertEquals(board, null);
    }
    
    @Test
    public void solve() {
        final Board board = Board.parse("0 0 0 ? ? ? ? ? ? 0 0 0 0 0 ? ? ? 0 0 ? ? ? ? ? ? ? ?\n" +
                "? ? 0 ? ? ? ? ? ? 0 0 0 0 0 ? ? ? ? ? ? ? ? ? ? ? ? ?\n" +
                "? ? ? ? 0 0 0 0 0 0 ? ? ? 0 ? ? ? ? ? ? 0 ? ? ? ? ? ?\n" +
                "? ? ? ? 0 0 0 0 0 0 ? ? ? 0 0 0 0 ? ? ? 0 ? ? ? ? ? ?\n" +
                "0 ? ? ? 0 0 0 0 0 0 ? ? ? 0 0 0 0 0 0 0 0 ? ? ? ? ? ?\n" +
                "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 ? ? ? ? 0", 16);
        board.solve();
        System.out.println(board);
        assertEquals("0 0 0 1 x 1 1 x 1 0 0 0 0 0 1 1 1 0 0 1 x 3 x 3 1 2 1\n" +
                "1 1 0 1 1 1 1 1 1 0 0 0 0 0 1 x 1 1 1 2 1 3 x 3 x 2 x\n" +
                "x 2 1 1 0 0 0 0 0 0 1 1 1 0 1 1 1 1 x 1 0 2 2 3 1 3 2\n" +
                "1 2 x 1 0 0 0 0 0 0 1 x 1 0 0 0 0 1 1 1 0 1 x 2 1 2 x\n" +
                "0 1 1 1 0 0 0 0 0 0 1 1 1 0 0 0 0 0 0 0 0 1 2 3 x 2 1\n" +
                "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 x 2 1 0", board.toString());
        /*

1)

? ? ? ? ? ?
? ? 2 1 2 ?
? ? 2 0 1 ?
? ? 2 1 2 ?
1 1 1 1 ? ?
0 0 0 1 ? ?

? ? ? ? ? ?
2 2 2 1 2 2
2 x 2 0 1 x
2 x 2 1 2 2
1 1 1 1 x 1
0 0 0 1 1 1


EXPECTED
1 x 1 1 x 1
2 2 2 1 2 2
2 x 2 0 1 x
2 x 2 1 2 2
1 1 1 1 x 1
0 0 0 1 1 1

REALITY
1 0 x 1 0 1
2 2 2 1 2 2
2 x 2 0 1 x
2 x 2 1 2 2
1 1 1 1 x 1
0 0 0 1 1 1
         */
    }
}