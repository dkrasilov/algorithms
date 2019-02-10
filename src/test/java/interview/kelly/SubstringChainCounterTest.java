package interview.kelly;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static interview.kelly.SubstringChainCounter.findLongestChainLength;
import static org.junit.Assert.*;

public class SubstringChainCounterTest {

    @Test
    public void findLongestChainLengthTest() {
        List<String> strings = Arrays.asList("abcd", "abc", "ab", "b");

        assertEquals(findLongestChainLength(strings), 4);
    }
}