package interview.kelly;

import io.vavr.collection.List;
import io.vavr.collection.Set;
import io.vavr.collection.Stream;

import java.util.Comparator;

public class SubstringChainFinder {
    static List<String> findLongestChain(List<String> strings) {
        return strings.map(s -> substringsCounterHelper(strings.toSet(), s))
                .maxBy(Comparator.comparing(List::size))
                .getOrElse(List::empty);
    }

    private static List<String> substringsCounterHelper(Set<String> strings, String currentString) {
        if (strings.contains(currentString))
            return substrings(currentString)
                    .map(substring -> substringsCounterHelper(strings, substring))
                    .maxBy(Comparator.comparing(List::size))
                    .getOrElse(List::empty)
                    .append(currentString);
        return List.empty();
    }

    private static Stream<String> substrings(String s) {
        return Stream.range(0, s.length())
                .map(i -> new StringBuilder(s).deleteCharAt(i).toString());
    }
}
