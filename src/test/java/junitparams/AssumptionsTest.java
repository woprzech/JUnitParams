package junitparams;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assume.assumeThat;

@RunWith(JUnitParamsRunner.class)
public class AssumptionsTest {

    private static int counter = 2;

    @Before
    public void before() {
        System.out.println("Before method " + Thread.currentThread().getId());
        lookBusy((counter--) * 5000);
    }

    public static void lookBusy(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            System.out.println("interrupted");
        }
    }

    @Test
    @Parameters(value = {"false", "true"}, parallel = true)
    public void assumeOnceWorksAndOnceIgnores(boolean value) {
        System.out.println("Value: " + value);
        System.out.println("test " + Thread.currentThread().getId());
        assumeThat(value, is(true));
        assertThat(value).isTrue();
    }
}
