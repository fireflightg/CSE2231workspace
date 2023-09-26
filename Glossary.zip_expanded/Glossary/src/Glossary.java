import java.util.Comparator;

import components.map.Map;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Program to receive a text file of words and to output an html page with a
 * table of words and word counts.
 *
 * @author Saahib Mohammed
 *
 */
public final class Glossary {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private Glossary() {
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static Map<String, String> generate(SimpleReader file,
            Queue<String> si) {
        Map<String, String> items = new Map1L<>();

        String place = "blank!", s1 = "blank!", s2 = "blank!";
        int count = 0;
        int seccount = 0;

        while (!file.atEOS()) {

            place = file.nextLine();

            count++;
            if (count == 1) {
                s1 = place;

            }
            if (count == 2) {
                s2 = place;

            }
            if (count > 2) {
                s2 = s2.concat(" " + place);

            }

            if (place.equals("")) {
                si.enqueue(s1);
                items.add(s1, s2);

                count = 0;
                seccount++;

            }

        }

        return items;
    }

    private static class StringLT implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }
    }

    public static void index(SimpleWriter out, String filename,
            String foldername, Queue<String> si) {
        //filename = filename.replace(".txt", "");
        //
        Comparator<String> cs = new StringLT();
        si.sort(cs);
        int count = 0;

        SimpleWriter indexprint = new SimpleWriter1L(
                foldername + "/index.html");

        indexprint.println("<html>");
        indexprint.println("<head>");

        // Prints the title
        indexprint.println("<title>" + filename + "</title>");
        // Prints out more opening tags
        indexprint.println("</head>");
        indexprint.println("<title>Glossary</title>");

        indexprint.println("<h2>Glossary</h2>");
        indexprint.println("<h3>Index</h3>" + " <hr></hr>");
        indexprint.println("<ul>");
        int k = 0;
        for (String s : si) {
            String termname = s;

            indexprint.println("<li><a href=\"" + termname + ".html" + "\">"
                    + termname + "</a></li>");
            k++;
        }

        indexprint.println("</ul>");

        indexprint.println("</body>");
        indexprint.println("</html>");
    }

    private static void Definitions(Map<String, String> m, Queue<String> terms,
            String foldername) {
        int count = 0;
        String filename = "";
        Comparator<String> cs = new StringLT();
        terms.sort(cs);

        for (String e : terms) {
            filename = e;
            String v = "";
            SimpleWriter def = new SimpleWriter1L(
                    foldername + "/" + filename + ".html");
            def.println("<html>");
            def.println("<head>");

            // Prints the title
            def.println("<title>" + filename + "</title>");
            // Prints out more opening tags
            def.println("</head>");

            def.println("<body>");
            def.println("<h2 style= \"color: red; font-style: italic;\" >" + e
                    + "</h2>" + "<hr></hr>");
            def.println("<p>");
            String splice = wordNoSep(m.value(e));
            String checker = m.value(e);

            for (String k : terms) {
                if (splice.contains(k)) {

                    v = k;
                    // System.out.println(checker);
                    checker = checker.replace(k,
                            "<a href = \"" + k + ".html\" >" + k + "</a>");
                    //System.out.println(checker);

                }

            }

            def.println(checker + "</p>");
            def.println("<p>Return to <a href=\"index.html\">index</a></p>");
            def.println("</body>");
            def.println("</html>");
            def.close();

        }

    }

    public static String wordNoSep(String word) {
        String ret = "";
        String Alpahbet = "abcdefghijklmnopqrstuvwxyz";

        for (int i = 0; i < word.length(); i++) {
            if (Alpahbet.indexOf(word.charAt(i)) != -1) {
                String temp = Character.toString(word.charAt(i));

                ret = ret.concat(temp);

            }
        }
        return ret;
    }

    public static void main(String[] args) {
        /*
         * Open input and output streams
         */
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        out.println("file name: ");
        // SimpleReader file = new SimpleReader1L(in.nextLine());
        String name = in.nextLine();
        if (!name.contains(".txt")) {
            if (!name.contains(".")) {
                name = name + ".txt";
            } else {
                name = name + "txt";
            }
        }
        SimpleReader file = new SimpleReader1L(name);
        Queue<String> si = new Queue1L<>();
        out.println("output folder: ");
        String folder = in.nextLine();
        Map<String, String> items = generate(file, si);
        index(out, name, folder, si);
        Definitions(items, si, folder);
        in.close();
        out.close();
        file.close();
    }

}
