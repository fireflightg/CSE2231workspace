import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;

/**
 * JUnit test fixture for {@code NaturalNumber}'s constructors and kernel
 * methods.
 *
 * @author Put your name here
 *
 */
public abstract class NaturalNumberTest {

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @return the new number
     * @ensures constructorTest = 0
     */
    protected abstract NaturalNumber constructorTest();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorTest = i
     */
    protected abstract NaturalNumber constructorTest(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorTest)
     */
    protected abstract NaturalNumber constructorTest(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorTest = n
     */
    protected abstract NaturalNumber constructorTest(NaturalNumber n);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @return the new number
     * @ensures constructorRef = 0
     */
    protected abstract NaturalNumber constructorRef();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorRef = i
     */
    protected abstract NaturalNumber constructorRef(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorRef)
     */
    protected abstract NaturalNumber constructorRef(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorRef = n
     */
    protected abstract NaturalNumber constructorRef(NaturalNumber n);

    // TODO - add test cases for four constructors, multiplyBy10, divideBy10, isZero

    @Test
    public final void testDefaultConstructor() {

        NaturalNumber test = this.constructorTest();
        NaturalNumber result = this.constructorRef();

        assertEquals(result, test);
    }

    @Test
    public final void testIntisZero() {

        NaturalNumber test = this.constructorTest(0);
        NaturalNumber result = this.constructorRef(0);

        assertEquals(result, test);
    }

    @Test
    public final void testIntisNonZero() {

        NaturalNumber test = this.constructorTest(111);
        NaturalNumber result = this.constructorRef(111);

        assertEquals(result, test);
    }

    @Test
    public final void testIntConstructor() {

        NaturalNumber test = this.constructorTest(4020);
        NaturalNumber result = this.constructorRef(4020);

        assertEquals(result, test);
    }

    @Test
    public final void testStringisZero() {
        NaturalNumber test = this.constructorTest("0");
        NaturalNumber result = this.constructorRef("0");
        assertEquals(result, test);
    }

    @Test
    public final void testStringNotZero() {
        NaturalNumber test = this.constructorTest("911");
        NaturalNumber result = this.constructorRef("911");
        assertEquals(result, test);
    }

    @Test
    public final void testSingleDigit() {
        NaturalNumber test = this.constructorTest("3");
        NaturalNumber result = this.constructorRef("3");
        assertEquals(result, test);
    }

    @Test
    public final void testContainsZeroDigit() {

        NaturalNumber test = this.constructorTest("3040");
        NaturalNumber result = this.constructorRef("3040");
        assertEquals(result, test);
    }

    @Test
    public final void testConstructorZero() {

        NaturalNumber test = this.constructorRef(0);
        NaturalNumber result = this.constructorRef(0);

        assertEquals(result, test);
        ;
    }

    @Test
    public final void testNaturalNumberConstructorNonZero() {

        NaturalNumber five = this.constructorRef(5);
        NaturalNumber q = this.constructorTest(five);
        NaturalNumber qExpected = this.constructorRef(5);

        assertEquals(qExpected, q);
    }

    @Test
    public final void testMultiplybyZero() {

        NaturalNumber test = this.constructorTest(0);
        NaturalNumber result = this.constructorRef(0);
        test.multiplyBy10(5);
        result.multiplyBy10(5);
        assertEquals(result, test);
    }

    @Test
    public final void testMultiplyBy10addnumber() {

        NaturalNumber test = this.constructorTest(2);
        NaturalNumber result = this.constructorRef(2);
        test.multiplyBy10(8);
        result.multiplyBy10(8);

        assertEquals(result, test);
    }

    @Test
    public final void testMultiplyBy10() {

        NaturalNumber test = this.constructorTest(589076);
        NaturalNumber result = this.constructorRef(589076);
        test.multiplyBy10(3);
        result.multiplyBy10(3);

        assertEquals(result, test);
    }

    @Test
    public final void testDivideBy10Zero() {

        NaturalNumber test = this.constructorTest(0);
        NaturalNumber result = this.constructorRef(0);

        int rtest = test.divideBy10();
        int remainder = result.divideBy10();
        assertEquals(result, test);
        assertEquals(rtest, remainder);
    }

    @Test
    public final void testDivideEndingZero() {
        NaturalNumber test = this.constructorTest(1005056710);
        NaturalNumber result = this.constructorRef(1005056710);
        int rtest = test.divideBy10();
        int remainder = result.divideBy10();
        assertEquals(result, test);
        assertEquals(rtest, remainder);
    }

    @Test
    public final void testIsZeroWithZero() {

        NaturalNumber test = this.constructorTest(0);
        NaturalNumber result = this.constructorRef(0);
        assertEquals(result, test);
        boolean IsZero = test.isZero();
        assertEquals(test, result);
        assertEquals(IsZero, true);
    }

    @Test
    public final void testIsZero() {
        NaturalNumber test = this.constructorTest(1080);
        NaturalNumber result = this.constructorRef(1080);
        assertEquals(result, test);

        assertEquals(test, result);
        assertEquals(test.isZero(), false);
    }

}
