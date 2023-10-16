import static org.junit.Assert.assertEquals;

import java.util.Comparator;

import org.junit.Test;

import components.sortingmachine.SortingMachine;

/**
 * JUnit test fixture for {@code SortingMachine<String>}'s constructor and
 * kernel methods.
 *
 * @author Put your name here
 *
 */
public abstract class SortingMachineTest {

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * implementation under test and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorTest = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorTest(
            Comparator<String> order);

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * reference implementation and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorRef = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorRef(
            Comparator<String> order);

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the
     * implementation under test type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsTest = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsTest(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorTest(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the reference
     * implementation type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsRef = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsRef(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorRef(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     * Comparator<String> implementation to be used in all test cases. Compare
     * {@code String}s in lexicographic order.
     */
    private static class StringLT implements Comparator<String> {

        @Override
        public int compare(String s1, String s2) {
            return s1.compareToIgnoreCase(s2);
        }

    }

    /**
     * Comparator instance to be used in all test cases.
     */
    private static final StringLT ORDER = new StringLT();

    /*
     * Sample test cases.
     */

    @Test
    public final void testConstructor() {
        SortingMachine<String> test = this.constructorTest(ORDER);
        SortingMachine<String> result = this.constructorRef(ORDER);
        assertEquals(result, test);
    }

    /*
     * Add method 
     */

    @Test
    public final void testAddEmpty() {
        SortingMachine<String> test = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> result = this.createFromArgsRef(ORDER, true,
                "0");
        test.add("0");
        assertEquals(result, test);
    }

    @Test
    public final void testAddNonEmpty() {
        SortingMachine<String> test = this.createFromArgsTest(ORDER, true, "0");
        SortingMachine<String> result = this.createFromArgsRef(ORDER, true,
                "0", "1");
        test.add("1");
        assertEquals(result, test);
    }

    @Test
    public final void testAddDuplicates() {
        SortingMachine<String> test = this.createFromArgsTest(ORDER, true, "0");
        SortingMachine<String> result = this.createFromArgsRef(ORDER, true,
                "0", "0");
        test.add("0");
        assertEquals(result, test);
    }

    @Test
    public final void testAddMultipleValues() {
        SortingMachine<String> test = this.createFromArgsTest(ORDER, true, "0",
                "1", "1", "1", "2");
        SortingMachine<String> result = this.createFromArgsRef(ORDER, true,
                "0", "1", "1", "1", "2", "3");
        test.add("3");
        assertEquals(result, test);
    }

    /*
     * changeToExtractionMode
     */

    @Test
    public final void testChangeToExtractionModeEmpty() {
        SortingMachine<String> test = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> result = this.createFromArgsRef(ORDER, false);

        test.changeToExtractionMode();

        assertEquals(result, test);
    }

    @Test
    public final void testChangeToExtractionModeOneValue() {
        SortingMachine<String> test = this.createFromArgsTest(ORDER, true, "0");
        SortingMachine<String> result = this.createFromArgsRef(ORDER, false,
                "0");

        test.changeToExtractionMode();

        assertEquals(result, test);
    }

    /*
     * removeFirst
     */
    @Test
    public final void testRemoveFirstOneValue() {
        SortingMachine<String> test = this.createFromArgsTest(ORDER, false, "0");
        SortingMachine<String> result = this.createFromArgsRef(ORDER, false);

        assertEquals("0", test.removeFirst());
        assertEquals(result, test);
    }

    @Test
    public final void testRemoveFirstDuplicate() {
        SortingMachine<String> test = this.createFromArgsTest(ORDER, false, "0",
                "0");
        SortingMachine<String> result = this.createFromArgsRef(ORDER, false,
                "0");

        assertEquals("0", test.removeFirst());
        assertEquals(result, test);
    }

    /*
     * isInInsertionMode tests.
     */

    @Test
    public final void testIsInsertionModeEmpty() {
        SortingMachine<String> test = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> result = this.createFromArgsRef(ORDER, true);

        assertEquals(true, test.isInInsertionMode());
        assertEquals(result, test);
    }


    @Test
    public final void testIsInsertionModeOneEntry() {
        SortingMachine<String> test = this.createFromArgsTest(ORDER, true, "0");
        SortingMachine<String> result = this.createFromArgsRef(ORDER, true,
                "0");

        assertEquals(true, test.isInInsertionMode());
        assertEquals(result, test);
    }
    @Test
    public final void testIsInsertionModeTwoEntries() {
        SortingMachine<String> test = this.createFromArgsTest(ORDER, true, "0",
                "1");
        SortingMachine<String> result = this.createFromArgsRef(ORDER, true,
                "0", "1");

        assertEquals(true, test.isInInsertionMode());
        assertEquals(result, test);
    }

    @Test
    public final void testIsInsertionModeDuplicateEntries() {
        SortingMachine<String> test = this.createFromArgsTest(ORDER, true, "0",
                "0");
        SortingMachine<String> result = this.createFromArgsRef(ORDER, true,
                "0", "0");

        assertEquals(true, test.isInInsertionMode());
        assertEquals(result, test);
    }
    /*
     * order method tests.
     */

  
    @Test
    public final void testOrderEmpty() {
        SortingMachine<String> test = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> result = this.createFromArgsRef(ORDER, true);

        assertEquals(ORDER, test.order());
        assertEquals(result, test);
    }

    @Test
    public final void testOrderOneEntry() {
        SortingMachine<String> test = this.createFromArgsTest(ORDER, true, "0");
        SortingMachine<String> result = this.createFromArgsRef(ORDER, true,
                "0");

        assertEquals(ORDER, test.order());
        assertEquals(result, test);
    }

    @Test
    public final void testOrderMultipleEntries() {
        SortingMachine<String> test = this.createFromArgsTest(ORDER, true, "0",
                "1");
        SortingMachine<String> result = this.createFromArgsRef(ORDER, true,
                "0", "1");

        assertEquals(ORDER, test.order());
        assertEquals(result, test);
    }

  
    @Test
    public final void testOrderDuplicateEntries() {
        SortingMachine<String> test = this.createFromArgsTest(ORDER, true, "0",
                "0");
        SortingMachine<String> result = this.createFromArgsRef(ORDER, true,
                "0", "0");

        assertEquals(ORDER, test.order());
        assertEquals(result, test);
    }
    /*
     * size method.
     */
    @Test
    public final void testSizeEmpty() {
        SortingMachine<String> test = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> result = this.createFromArgsRef(ORDER, true);

        assertEquals(0, test.size());
        assertEquals(result, test);
    }

    @Test
    public final void testSizeOneEntryEmpty() {
        SortingMachine<String> test = this.createFromArgsTest(ORDER, true, "");
        SortingMachine<String> result = this.createFromArgsRef(ORDER, true,
                "");

        assertEquals(1, test.size());
        assertEquals(result, test);
    }

    @Test
    public final void testSizeOneEntryNonEmpty() {
        SortingMachine<String> test = this.createFromArgsTest(ORDER, true,
                "green");
        SortingMachine<String> result = this.createFromArgsRef(ORDER, true,
                "green");

        assertEquals(1, test.size());
        assertEquals(result, test);
    }

    @Test
    public final void testSizeTwoEntries() {
        SortingMachine<String> test = this.createFromArgsTest(ORDER, true, "green",
                "red");
        SortingMachine<String> result = this.createFromArgsRef(ORDER, true,
                "green", "red");

        assertEquals(2, test.size());
        assertEquals(result, test);
    }

    @Test
    public final void testSizeDuplicateEntries() {
        SortingMachine<String> test = this.createFromArgsTest(ORDER, true, "green",
                "green");
        SortingMachine<String> result = this.createFromArgsRef(ORDER, true,
                "green", "green");

        assertEquals(2, test.size());
        assertEquals(result, test);
    }


}
