import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.set.Set;

/**
 * JUnit test fixture for {@code Set<String>}'s constructor and kernel methods.
 *
 * @author Jana Abedeljaber, Sonali Manoharan, Saahib Mohammed
 *
 */
public abstract class SetTest {

    /**
     * Invokes the appropriate {@code Set} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new set
     * @ensures constructorTest = {}
     */
    protected abstract Set<String> constructorTest();

    /**
     * Invokes the appropriate {@code Set} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new set
     * @ensures constructorRef = {}
     */
    protected abstract Set<String> constructorRef();

    /**
     * Creates and returns a {@code Set<String>} of the implementation under
     * test type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsTest = [entries in args]
     */
    private Set<String> createFromArgsTest(String... args) {
        Set<String> set = this.constructorTest();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    /**
     * Creates and returns a {@code Set<String>} of the reference implementation
     * type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsRef = [entries in args]
     */
    private Set<String> createFromArgsRef(String... args) {
        Set<String> set = this.constructorRef();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    /**
     * Test remove to an empty set.
     */
    @Test
    public final void testRemoveToEmpty() {
        Set<String> test = this.createFromArgsTest("Sonu");
        Set<String> expected = this.createFromArgsRef();
        String removed = test.remove("Sonu");
        String expectedRemove = "Sonu";

        assertEquals(test, expected);
        assertEquals(removed, expectedRemove);
    }

    /**
     * Testing remove : routine.
     */
    @Test
    public final void testRemove_lastName() {
        Set<String> test = this.createFromArgsTest("Sonu", "Manoharan");
        Set<String> expected = this.createFromArgsRef();
        String removed = test.remove("Manoharan");
        String expectedRemoved = "Manoharan";

        assertEquals(test, expected);
        assertEquals(removed, expectedRemoved);
    }

    /**
     * Testing remove : routine.
     */
    @Test
    public final void testRemoveRoutine() {
        Set<String> test = this.createFromArgsTest("A");
        Set<String> expected = this.createFromArgsRef();
        Set<String> remove = this.createFromArgsTest("A");
        Set<String> expectedRemoved = this.createFromArgsRef("A");

        Set<String> removed = test.remove(remove);

        assertEquals(test, expected);
        assertEquals(removed, expectedRemoved);
    }

    /**
     * Testing remove from the middle of set.
     */
    @Test
    public final void testRemoveFromMiddle() {
        Set<String> test = this.createFromArgsTest("A", "B", "C");
        Set<String> expected = this.createFromArgsRef("A", "C");
        Set<String> remove = this.createFromArgsTest("B");
        Set<String> expectedRemoved = this.createFromArgsRef("B");

        Set<String> removed = test.remove(remove);

        assertEquals(test, expected);
        assertEquals(removed, expectedRemoved);
    }

    /**
     * Testing removeAny to an empty set.
     */
    @Test
    public final void testRemoveAnyToEmpty() {
        Set<String> test = this.createFromArgsTest("Sonu");
        Set<String> expected = this.createFromArgsRef("Sonu");

        String removed = test.removeAny();
        assertEquals(true, expected.contains(removed));
        expected.remove(removed);
        assertEquals(test, expected);
    }

    /**
     * Testing remove : routine.
     */
    @Test
    public final void testRemoveAnyRoutine() {
        Set<String> test = this.createFromArgsTest("CSE", "2231");
        Set<String> expected = this.createFromArgsRef("CSE", "2231");
        String removed = test.removeAny();

        assertEquals(true, expected.contains(removed));
        expected.remove(removed);
        assertEquals(test, expected);
    }

    /**
     * Testing removeAny from the middle of set.
     */
    @Test
    public final void testRemoveAnyFromMiddle() {
        Set<String> test = this.createFromArgsTest("1", "2", "3");
        Set<String> expected = this.createFromArgsRef("1", "2", "3");
        String removed = test.removeAny();

        assertEquals(true, expected.contains(removed));
        expected.remove(removed);
        assertEquals(test, expected);
    }

    /**
     * Test contains to set.
     */
    @Test
    public final void testContainsTrueSmall() {
        Set<String> test = this.createFromArgsTest("set");
        boolean x = test.contains("set");
        assertEquals(true, x);
    }

    /**
     * Test contains to set.
     */
    @Test
    public final void testContainsTrue() {
        Set<String> test = this.createFromArgsTest("word", "hello", "set");
        boolean x = test.contains("word");
        assertEquals(true, x);
    }

    /**
     * Test contains to set.
     */
    @Test
    public final void testContainsFalse() {
        Set<String> test = this.createFromArgsTest("word", "hello", "set");
        boolean x = test.contains("hi");
        assertEquals(false, x);
    }

    /**
     * Test size to set.
     */
    @Test

    public final void testSize() {
        Set<String> test = this.createFromArgsTest("word", "hello", "set");
        int i = test.size();
        assertEquals(3, i);
    }

    /**
     * Test size to set.
     */
    @Test
    public final void testSizeLarge() {
        Set<String> test = this.createFromArgsTest("hello", "get", "set", "tan",
                "pink", "yellow", "hat");
        int i = test.size();
        assertEquals(7, i);
    }

    /**
     * Test size to set.
     */
    @Test
    public final void testSizeEmpty() {
        Set<String> test = this.createFromArgsTest();
        int i = test.size();
        assertEquals(0, i);
    }

}

