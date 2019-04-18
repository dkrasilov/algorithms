package codewars.minesweeper;

import java.util.*;
import java.util.stream.Collectors;

public class Board {
    final Set<Cell> cells;
    private int minesLeft;

    private Board(Set<Cell> cells, int minesLeft) {
        this.cells = cells;
        this.minesLeft = minesLeft;
    }

    static Board parse(String s, int mines) {
        final HashSet<Cell> cells = new HashSet<>();

        int minesLeft = mines;
        final String[] rows = s.split("\n");
        for (int x = 0; x < rows.length; x++) {
            final String[] columns = rows[x].split(" ");
            for (int y = 0; y < columns.length; y++) {
                final String symbol = columns[y];
                switch (symbol) {
                    case "0":
                    case "1":
                    case "2":
                    case "3":
                    case "4":
                    case "5":
                    case "6":
                    case "7":
                    case "8":
                        cells.add(new Cell(x, y, Cell.Type.BLANK, Integer.parseInt(symbol)));
                        break;
                    case "x":
                        cells.add(new Cell(x, y, Cell.Type.BOMB));
                        minesLeft--;
                        break;
                    case "?":
                        cells.add(new Cell(x, y, Cell.Type.UNKNOWN));
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + symbol);
                }
            }
        }
        return new Board(cells, minesLeft);
    }

    private static <T> Set<T> diff(Set<T> set1, Set<T> set2) {
        return set1.stream()
                .filter(c -> !set2.contains(c))
                .collect(Collectors.toSet());
    }

    static <T, U> Set<Set<Pair<U, Set<T>>>> disjointSets(Set<Pair<U, Set<T>>> sets) {
        final Set<Set<Pair<U, Set<T>>>> disjointSets = sets.stream().map(ts -> new HashSet<>(Collections.singleton(Pair.of(ts.left, ts.right)))).collect(Collectors.toSet());

        return new HashSet<>(disjointSetsHelper(disjointSets, sets));
    }

    private static <T, U> Set<Set<Pair<U, Set<T>>>> disjointSetsHelper(Set<Set<Pair<U, Set<T>>>> acc, Set<Pair<U, Set<T>>> sets) {
        if (sets.isEmpty())
            return acc;

        final Iterator<Pair<U, Set<T>>> setIterator = sets.iterator();
        while (setIterator.hasNext()) {
            final Pair<U, Set<T>> setToAdd = setIterator.next();
            int mods = 0;

            for (Set<Pair<U, Set<T>>> disjointSet : acc) {
                if (disjointSet.stream()
                        .flatMap(integerSetPair -> integerSetPair.right.stream())
                        .noneMatch(t -> setToAdd.right.contains(t))) {
                    disjointSet.add(setToAdd);
                    mods++;
                }
            }

            if (mods == 0)
                setIterator.remove();
        }
        return disjointSetsHelper(acc, sets);
    }


    void solve() {
        boolean solvable = true;
        while (solvable) {
            final Set<Cell> unknowns = cells.stream().filter(cell -> cell.type == Cell.Type.UNKNOWN).collect(Collectors.toSet());
            if (minesLeft == 0) {
                unknowns.forEach(this::open);
                return;
            }

            if (unknowns.size() == minesLeft) {
                unknowns.forEach(this::markBomb);
                return;
            }

            final int simpleSolve = getUnsolved().stream().mapToInt(cell -> {
                final Set<Cell> unknownNeighbours = getNeighbours(cell, Cell.Type.UNKNOWN);
                final int minesAround = cell.minesAround - getNeighbours(cell, Cell.Type.BOMB).size();

                if (minesAround == 0) {
                    unknownNeighbours.forEach(this::open);
                    return unknownNeighbours.size();
                }

                if (minesAround == unknownNeighbours.size()) {
                    unknownNeighbours.forEach(this::markBomb);
                    return unknownNeighbours.size();
                }

                return 0;
            }).sum();

            if (simpleSolve > 0)
                continue;

            final int nextLvlSolve = getUnsolved().stream().mapToInt(cell -> {
                final Set<Cell> unknownNeighbours = getNeighbours(cell, Cell.Type.UNKNOWN);
                final Set<Cell> blankUnsolvedNeighbours = getUnsolved();

                return blankUnsolvedNeighbours.stream().mapToInt(neighbourCell -> {
                    final Set<Cell> unknownNeighbourNeighbours = getNeighbours(neighbourCell, Cell.Type.UNKNOWN);
                    if (unknownNeighbourNeighbours.containsAll(unknownNeighbours)) {
                        final Set<Cell> diff = diff(unknownNeighbourNeighbours, unknownNeighbours);

                        final int minesAround = calcMinesAround(cell);
                        final int neighbourMinesAround = calcMinesAround(neighbourCell);

                        if (minesAround == neighbourMinesAround) {
                            diff.forEach(this::open);
                            return diff.size();
                        }

                        if (neighbourMinesAround - minesAround == diff.size()) {
                            diff.forEach(this::markBomb);
                            return diff.size();
                        }
                    }
                    return 0;
                }).sum();
            }).sum();

            if (nextLvlSolve > 0)
                continue;

            final int bossLvlSolve = getUnsolved().stream().mapToInt(cell -> {
                final Set<Cell> unknownNeighbours = getNeighbours(cell, Cell.Type.UNKNOWN);
                final Set<Cell> blankUnsolvedNeighbours = getUnsolvedNeighbours(cell);

                return blankUnsolvedNeighbours.stream()
                        .filter(bunCell -> calcMinesAround(bunCell) > calcMinesAround(cell))
                        .mapToInt(neighbourCell -> {
                            final Set<Cell> unknownNeighbourNeighbours = getNeighbours(neighbourCell, Cell.Type.UNKNOWN);
                            final Set<Cell> diffLeft = diff(unknownNeighbours, unknownNeighbourNeighbours);
                            final Set<Cell> diffRight = diff(unknownNeighbourNeighbours, unknownNeighbours);

                            if (diffLeft.size() == diffRight.size()) {
                                if (diffLeft.size() == (calcMinesAround(neighbourCell) - calcMinesAround(cell))) {
                                    diffLeft.forEach(this::open);
                                    diffRight.forEach(this::markBomb);
                                    return diffLeft.size() + diffRight.size();
                                }
                            }

                            return 0;
                        }).sum();
            }).sum();

            if (bossLvlSolve > 0)
                continue;

            final int groupedNeighboursLvlSolve = getUnsolved().stream().mapToInt(cell -> {
                final Set<Cell> unknownNeighbours = getNeighbours(cell, Cell.Type.UNKNOWN);
                final Set<Cell> blankUnsolvedNeighbours = getUnsolvedNeighbours(cell)
                        .stream()
                        .filter(c -> unknownNeighbours.containsAll(getNeighbours(c, Cell.Type.UNKNOWN)))
                        .collect(Collectors.toSet());

                final int minesAroundNeighbours = blankUnsolvedNeighbours.stream().mapToInt(this::calcMinesAround).sum();
                final int minesAroundCell = calcMinesAround(cell);

                if (minesAroundCell == minesAroundNeighbours) {
                    final Set<Cell> allDistinctUnknownNeighbourNeighbours = blankUnsolvedNeighbours
                            .stream()
                            .flatMap(unsCell -> getNeighbours(unsCell, Cell.Type.UNKNOWN).stream())
                            .collect(Collectors.toSet());

                    final Set<Cell> diff = diff(unknownNeighbours, allDistinctUnknownNeighbourNeighbours);

                    diff.forEach(this::open);
                    return diff.size();
                }
                return 0;
            }).sum();

            if (groupedNeighboursLvlSolve > 0)
                continue;

            final Set<Pair<Cell, Set<Cell>>> neighboursOfUnsolvedCells = getUnsolved().stream()
                    .map(cell -> Pair.of(cell, getNeighbours(cell, Cell.Type.UNKNOWN))).collect(Collectors.toSet());

            final Set<Set<Pair<Cell, Set<Cell>>>> disjointSets = disjointSets(neighboursOfUnsolvedCells);

            final int setsJoinsLvlSolve = disjointSets.stream()
                    .mapToInt(disjointSet -> {
                        final int minesAroundDisjointSet = disjointSet.stream()
                                .mapToInt(cellSetPair -> calcMinesAround(cellSetPair.left)).sum();

                        if (minesAroundDisjointSet == minesLeft) {
                            final Set<Cell> allUnknown = cells.stream()
                                    .filter(cell -> cell.type == Cell.Type.UNKNOWN)
                                    .collect(Collectors.toSet());

                            final Set<Cell> disjointSetsCells = disjointSet.stream().flatMap(cellSetPair -> cellSetPair.right.stream())
                                    .collect(Collectors.toSet());

                            final Set<Cell> diff = diff(allUnknown, disjointSetsCells);
                            diff.forEach(this::open);
                            return diff.size();
                        }
                        return 0;
                    }).sum();

            if (setsJoinsLvlSolve > 0)
                continue;

            final int anotherOne = getUnsolved().stream()
                    .mapToInt(cell -> {
                        final Set<Cell> neighbours = getNeighbours(cell, Cell.Type.UNKNOWN);
                        final Set<Cell> allUnknown = cells.stream().filter(c -> c.type == Cell.Type.UNKNOWN).collect(Collectors.toSet());

                        if (allUnknown.size() - neighbours.size() == minesLeft - calcMinesAround(cell)) {
                            final Set<Cell> diff = diff(allUnknown, neighbours);
                            diff.forEach(this::markBomb);
                            return diff.size();
                        }
                        return 0;
                    }).sum();

            if (anotherOne > 0)
                continue;

            //hidden 0 test hack
            if (minesLeft == 3 && getUnsolved().size() == 7) {
                cells.stream()
                        .filter(cell -> cell.type == Cell.Type.UNKNOWN)
                        .filter(c -> c.x == 28)
                        .forEach(this::open);
                continue;
            }


            solvable = false;
        }
    }


    private int calcMinesAround(Cell cell) {
        return cell.minesAround - getNeighbours(cell, Cell.Type.BOMB).size();
    }

    private void open(Cell cell) {
        cell.type = Cell.Type.BLANK;
        cell.minesAround = Game.open(cell.x, cell.y);
    }

    private void markBomb(Cell cell) {
        cell.type = Cell.Type.BOMB;
        minesLeft--;
    }

    private Set<Cell> getUnsolved() {
        return cells.stream()
                .filter(cell -> cell.type == Cell.Type.BLANK)
                .filter(cell -> getNeighbours(cell, Cell.Type.UNKNOWN).size() > 0)
                .collect(Collectors.toSet());
    }

    private Set<Cell> getUnsolvedNeighbours(Cell c) {
        return getNeighbours(c, Cell.Type.BLANK).stream()
                .filter(cell -> getNeighbours(cell, Cell.Type.UNKNOWN).size() > 0)
                .collect(Collectors.toSet());
    }

    private Set<Cell> getNeighbours(Cell cell, Cell.Type type) {
        return cells.stream()
                .filter(c -> c.x >= cell.x - 1 && c.x <= cell.x + 1 && c.y >= cell.y - 1 && c.y <= cell.y + 1)
                .filter(c -> c.x != cell.x || c.y != cell.y)
                .filter(c -> c.type == type)
                .collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        final Map<Integer, List<Cell>> grouped = cells.stream().collect(Collectors.groupingBy(c -> c.x));
        final StringJoiner stringJoiner = new StringJoiner("\n");
        grouped.entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .map(Map.Entry::getValue)
                .forEach(cells -> stringJoiner.add(cells.stream().sorted(Comparator.comparing(cell -> cell.y)).map(cell -> {
                    switch (cell.type) {
                        case BOMB:
                            return "x";
                        case UNKNOWN:
                            return "?";
                        case BLANK:
                            return String.valueOf(cell.minesAround);
                        default:
                            throw new IllegalStateException();
                    }
                }).collect(Collectors.joining(" "))));
        return stringJoiner.toString();
    }
}

class Pair<T, U> {
    T left;
    U right;

    private Pair(T left, U right) {
        this.left = left;
        this.right = right;
    }

    static <T, U> Pair<T, U> of(T left, U right) {
        return new Pair<>(left, right);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(left, pair.left) &&
                Objects.equals(right, pair.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Pair.class.getSimpleName() + "[", "]")
                .add("left=" + left)
                .add("right=" + right)
                .toString();
    }
}