import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.map.Map;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

public class GlossaryTest {

    /**
     * Routine test of generating Map and Queue.
     */
    @Test
    public void testGenerate1() {
        SimpleWriter def = new SimpleWriter1L("test.txt");
        ;
        def.println("is");
        def.println("Bob");
        def.println("");
        def.println("Cool bob");
        SimpleReader file = new SimpleReader1L("test.txt");

        Queue<String> si = new Queue1L<>();
        Map<String, String> items = Glossary.generate(file, si);

        Map<String, String> items2 = new Map1L<>();
        items2.add("is", "Bob ");

        assertEquals(items2, items);

    }

    @Test
    public void testGenerate2() {
        SimpleWriter def = new SimpleWriter1L("test.txt");
        ;
        def.println("is");
        def.println("Bob");
        def.println("");
        def.println("");

        SimpleReader file = new SimpleReader1L("test.txt");

        Queue<String> si = new Queue1L<>();
        Map<String, String> items = Glossary.generate(file, si);

        Map<String, String> items2 = new Map1L<>();
        items2.add("is", "Bob ");
        items2.add("", "Bob ");

        assertEquals(items2, items);

    }

    @Test
    public void testWordSeperator() {
        SimpleWriter def = new SimpleWriter1L("test.txt");
        String sep = "this-is-easy";
        String expected = "thisiseasy";
        String actual = Glossary.wordNoSep(sep);

        assertEquals(expected, actual);

    }

    @Test
    public void testgenerateIndex() {
        SimpleWriter def = new SimpleWriter1L("index.html");
        Queue<String> si = new Queue1L<>();
        si.enqueue("friends");
        si.enqueue("ever");

        Glossary.index(def, "test.html", "data", si);
        SimpleReader1L read = new SimpleReader1L("data/index.html");
        String freinds = "friends.html";
        String ever = "ever.html";
        boolean evers = false;
        boolean freind = false;
        while (!read.atEOS()) {
            String line = read.nextLine();
            if (line.contains(freinds)) {
                freind = true;
            }
            if (line.contains(ever)) {
                evers = true;
            }

        }
        read.close();

        assertEquals(true, freind);
        assertEquals(true, evers);

    }

}
