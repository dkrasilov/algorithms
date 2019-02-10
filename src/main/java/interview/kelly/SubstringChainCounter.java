package interview.kelly;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Задача найти самую длинную возможную цепочку
 * цепочка формируется по правилу что из строки можно вырезать 1 любой символ
 * и то что получилось должно быть в исходном наборе
 * <p>
 * Например (abc, ab, a) - цепочка abc -> ab -> a, убирается правый символ. Ответ: 3
 */
public class SubstringChainCounter {
    static int findLongestChainLength(List<String> strings) {
        return strings.stream()
                .mapToInt(s -> substringsCounterHelper(new HashSet<>(strings), s))
                .max().orElse(0);
    }

    private static int substringsCounterHelper(Set<String> strings, String string) {
        if (strings.contains(string))
            return 1 + substrings(string)
                    .mapToInt(substring -> substringsCounterHelper(strings, substring))
                    .max().orElse(0);
        return 0;
    }

    private static Stream<String> substrings(String s) {
        return IntStream.range(0, s.length())
                .mapToObj(i -> new StringBuilder(s).deleteCharAt(i).toString());
    }
}