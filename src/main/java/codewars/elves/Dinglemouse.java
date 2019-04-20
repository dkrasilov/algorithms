package codewars.elves;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

//https://www.codewars.com/kata/i-am-all-alone-poor-me/train/java
public class Dinglemouse {
    public static boolean allAlone(char[][] house) {
        Set<Point> elves = new HashSet<>();
        for (int x = 0; x < house.length; x++) {
            for (int y = 0; y < house[x].length; y++) {
                if (house[x][y] == 'o') {
                    elves.add(new Point(x, y));
                }
            }
        }

        return elves.stream()
                .allMatch(elf -> {
                    final boolean noWalls1 = IntStream.range(0, elf.x)
                            .allMatch(x -> house[x][elf.y] != '#');
                    final boolean noWalls2 = IntStream.range(elf.x, house.length)
                            .allMatch(x -> house[x][elf.y] != '#');
                    final boolean noWalls3 = IntStream.range(0, elf.y)
                            .allMatch(y -> house[elf.x][y] != '#');
                    final boolean noWalls4 = IntStream.range(elf.y, house[0].length)
                            .allMatch(y -> house[elf.x][y] != '#');

                    return noWalls1 || noWalls2 || noWalls3 || noWalls4;
                });

    }
}
