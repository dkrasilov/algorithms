package interview.kelly;

import io.vavr.collection.List;
import org.junit.Test;

import static interview.kelly.SubstringChainFinder.findLongestChain;
import static org.junit.Assert.*;

public class SubstringChainFinderTest {

    @Test
    public void findLongestChainTest() {
        final List<String> longestChain = findLongestChain(List.of("abcd", "abc", "ab", "b"));
        assertEquals(longestChain.size(), 4);
    }
}