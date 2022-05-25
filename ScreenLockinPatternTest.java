import static org.junit.Assert.assertEquals;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Created by aamado on 05-05-2022.
 * João Costa 24-05-2022
 */
@RunWith(JUnit4.class)
public class ScreenLockinPatternTest {

    /**
     * The corresponding implementations to test.
     *
     * If you want, you can make others :)
     *
     */
    public ScreenLockinPatternTest() {}

    /**
     * Starting node: 3
     * Pattern length: 2
     * Expected value: 5
     */
    @Test
    public void ScreenLockinPatternTestFirst3Length2Test()
    throws InterruptedException, ExecutionException, TimeoutException {
        Future<Integer> count = new ScreenLockinPattern().countPatternsFrom(3, 2);
        assertEquals(count.get().intValue(), 5);
    }

    /**
     * Starting node: 3
     * Pattern length: 3
     * Expected value: 31
     */
    @Test
    public void ScreenLockinPatternTestFirst3Length3Test()
    throws InterruptedException, ExecutionException, TimeoutException {
        Future<Integer> count = new ScreenLockinPattern().countPatternsFrom(3, 3);
        assertEquals(count.get().intValue(), 31);
    }

    /**
     * Starting node: 2
     * Pattern length: 2
     * Expected value: 7
     */
    @Test
    public void ScreenLockinPatternTestFirst2Length2Test()
    throws InterruptedException, ExecutionException, TimeoutException {
        Future<Integer> count = new ScreenLockinPattern().countPatternsFrom(2, 2);
        assertEquals(count.get().intValue(), 7);
    }


    /**
     * Starting node: 2
     * Pattern length: 3
     * Expected value: 37
     */
    @Test
    public void ScreenLockinPatternTestFirst2Length3Test()
    throws InterruptedException, ExecutionException, TimeoutException {
        Future<Integer> count = new ScreenLockinPattern().countPatternsFrom(2, 3);
        assertEquals(count.get().intValue(), 37);
    }

    /**
     * Starting node: 5
     * Pattern length: 2
     * Expected value: 8
     */
    @Test
    public void ScreenLockinPatternTestFirst5Length2Test()
    throws InterruptedException, ExecutionException, TimeoutException {
        Future<Integer> count = new ScreenLockinPattern().countPatternsFrom(5, 2);
        assertEquals(count.get().intValue(), 8);
    }

    /**
     * Verifies that the number of possible combinations of any length is the same
     * for the corner nodes (1, 3, 7, 9)
     */
    @Test
    public void ScreenLockinPatternTestCornerCombinationsMatch()
    throws InterruptedException, ExecutionException, TimeoutException {
        ScreenLockinPattern p = new ScreenLockinPattern();
        int accum1 = 0;
        int accum3 = 0;
        int accum7 = 0;
        int accum9 = 0;
        for(int i = 1; i <= 9; i++) {
            accum1 += p.countPatternsFrom(1, i).get();
            accum3 += p.countPatternsFrom(3, i).get();
            accum7 += p.countPatternsFrom(7, i).get();
            accum9 += p.countPatternsFrom(9, i).get();
        }

        assertEquals(accum1, accum3);
        assertEquals(accum1, accum7);
        assertEquals(accum1, accum9);
    }

    /**
     * Verifies that the number of possible combinations of any length is the same
     * for the intermediate nodes (2, 4, 6, 8)
     */
    @Test
    public void ScreenLockinPatternTestIntermediateCombinationsMatch()
    throws InterruptedException, ExecutionException, TimeoutException {
        ScreenLockinPattern p = new ScreenLockinPattern();
        int accum2 = 0;
        int accum4 = 0;
        int accum6 = 0;
        int accum8 = 0;
        for(int i = 1; i <= 9; i++) {
            accum2 += p.countPatternsFrom(2, i).get();
            accum4 += p.countPatternsFrom(4, i).get();
            accum6 += p.countPatternsFrom(6, i).get();
            accum8 += p.countPatternsFrom(8, i).get();
        }

        assertEquals(accum2, accum4);
        assertEquals(accum2, accum6);
        assertEquals(accum2, accum8);
    }

    /**
     * The total possible combinations of an android puzzle lock
     * is 389112. The minimum length for a lock is 4 numbers.
     * https://www.quora.com/Android-operating-system-How-many-combinations-does-Android-9-point-unlock-have
     */
    @Test
    public void ScreenLockinPatternTestAllCombinationsTest()
    throws InterruptedException, ExecutionException, TimeoutException {
        ScreenLockinPattern p = new ScreenLockinPattern();
        int accum = 0;
        for(int i = 4; i <= 9; i++) {
            accum += p.countPatternsFrom(1, i).get()*4;
            accum += p.countPatternsFrom(2, i).get()*4;
            accum += p.countPatternsFrom(5, i).get();
        }        
        assertEquals(accum, 389112);
    }
}
