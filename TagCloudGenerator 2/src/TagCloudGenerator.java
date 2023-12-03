import java.util.Comparator;

import components.map.Map;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Put a short phrase describing the program here.
 *
 * @author Saahib Mohammed
 *
 */
public final class TagCloudGenerator {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private TagCloudGenerator() {
    }

    private static class StringLT implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            //set to lower case to use correct sorting
            String o3 = o1.toLowerCase();
            String o4 = o2.toLowerCase();
            return o3.compareTo(o4);
        }
    }

    /**
     *
     * @return
     */
    private static Set<Character> generateSeparators() {
        Set<Character> separators = new Set1L<>();

        return separators;
    }

    /**
     *
     * @param inputName
     * @param separators
     * @return
     */
    private static Map<String, Integer> generateWordsWithCount(String inputName,
            Set<Character> separators) {
        Map<String, Integer> wordsWithCount = new Map1L<>();
        SimpleReader inputFile = new SimpleReader1L(inputName);
        while (!inputFile.atEOS()) {
            String oneLine = inputFile.nextLine();
            int position = 0;
            while (position < oneLine.length()) {
                String wordOrSep = nextWordOrSeparator(oneLine, position,
                        separators);
                position += wordOrSep.length();

                //determine if separator
                boolean isSeparator = false;
                if (wordOrSep.length() == 1) {
                    if (separators.contains(wordOrSep.charAt(0))) {
                        isSeparator = true;
                    }
                }
                //if not, add to map (after determining if already present)
                if (!isSeparator) {
                    position++;
                    wordOrSep = wordOrSep.toLowerCase();
                    if (!wordsWithCount.hasKey(wordOrSep)) {
                        wordsWithCount.add(wordOrSep, 1);
                    } else {
                        Map.Pair<String, Integer> wordCountPair = wordsWithCount
                                .remove(wordOrSep);
                        int count = wordCountPair.value();
                        count++;
                        wordsWithCount.add(wordOrSep, count);
                    }
                }
            }
        }
        inputFile.close();
        return wordsWithCount;
    }

    /**
     * Returns the first "word" (maximal length string of characters not in
     * {@code separators}) or "separator string" (maximal length string of
     * characters in {@code separators}) in the given {@code text} starting at
     * the given {@code position}.
     *
     * @param text
     *            the {@code String} from which to get the word or separator
     *            string
     * @param position
     *            the starting index
     * @param separators
     *            the {@code Set} of separator characters
     * @return the first word or separator string found in {@code text} starting
     *         at index {@code position}
     * @requires 0 <= position < |text|
     * @ensures <pre>
     * nextWordOrSeparator =
     *   text[position, position + |nextWordOrSeparator|)  and
     * if entries(text[position, position + 1)) intersection separators = {}
     * then
     *   entries(nextWordOrSeparator) intersection separators = {}  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      intersection separators /= {})
     * else
     *   entries(nextWordOrSeparator) is subset of separators  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      is not subset of separators)
     * </pre>
     */
    private static String nextWordOrSeparator(String text, int position,
            Set<Character> separators) {
        String wordOrSep = "";
        boolean isSeparator = false;
        for (int i = position; i < text.length() && !isSeparator; i++) {
            if (separators.contains(text.charAt(i))) {
                if (i == position) {
                    wordOrSep += text.charAt(i);
                } else {
                    wordOrSep = text.substring(position, i);
                }
                isSeparator = true;
            }
        }
        return wordOrSep;
    }

    private static Queue<String> queueKeys(Map<String, Integer> words, int n) {
        Set<String> usedWords = new Set1L<>();
        Queue<String> numOfWords = new Queue1L<>();
        int wordCount = 0;
        //determine first n numbers of words with highest count
        for (Map.Pair<String, Integer> pair : words) {
            if (pair.value() > wordCount) {
                wordCount = pair.value();
            }
        }
        //proceed until reaching n number of words
        while (usedWords.size() < n) {
            for (Map.Pair<String, Integer> pair : words) {
                if (!usedWords.contains(pair.key()) && pair.value() == wordCount
                        && usedWords.size() < n) {
                    usedWords.add(pair.key());
                    numOfWords.enqueue(pair.key());
                }
            }
            wordCount--;
        }
        return numOfWords;
    }

    /**
     *
     * @param outputName
     * @param sortedWords
     * @param wordsWithCount
     * @param inputName
     */
    private static void buildHtml(String outputName, Queue<String> sortedWords,
            Map<String, Integer> wordsWithCount, String inputName) {
        SimpleWriter out = new SimpleWriter1L(outputName);
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Top " + sortedWords.length() + " words in "
                + inputName + "</title>");
        out.println(
                "<link href=\"http://web.cse.ohio-state.edu/software/2231/web-sw2/assignments/projects/tag-cloud-generator/data/tagcloud.css\" rel=\"stylesheet\" type=\"text/css\">");
        out.println(
                "<link href=\"tagcloud.css\" rel=\"stylesheet\" type=\"text/css\">");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>Top " + sortedWords.length() + " words in " + inputName
                + "</h2>");
        out.println("<hr>");
        out.println("<div class=\"cdiv\">");
        out.println("<p class=\"cbox\">");

        int qLength = sortedWords.length();
        int maxValue = 0;
        //find maximum
        for (Map.Pair<String, Integer> pair : wordsWithCount) {
            if (pair.value() > maxValue && !pair.key().equals("")) {
                maxValue = pair.value();
            }
        }

        //find min
        int minValue = maxValue;
        for (Map.Pair<String, Integer> pair : wordsWithCount) {
            if (pair.value() < minValue && !pair.key().equals("")) {
                minValue = pair.value();
            }
        }

        //sort through queue
        for (int i = 0; i < qLength; i++) {
            String word = sortedWords.dequeue();
            if (!word.equals("")) {
                int wordCount = wordsWithCount.value(word);
                int fontSize = fontSizeCalculator(48, 11, maxValue, minValue,
                        wordCount);
                out.println("<span style=\"cursor:default\" class=\"f"
                        + fontSize + "\" title=\"count: " + wordCount + "\">"
                        + word + "</span>");
            }
        }
        out.println("</p>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }

    /**
     *
     * @param fontMax
     * @param fontMin
     * @param countMax
     * @param countMin
     * @param count
     * @return
     */
    private static int fontSizeCalculator(int fontMax, int fontMin,
            int countMax, int countMin, int count) {
        count -= countMin;

        //calculate fontsize using below formula
        double ratio = 1.0 * count / (1.0 * countMax - countMin);
        int fontSize = (int) (ratio * (fontMax - fontMin));
        fontSize += fontMin;

        return fontSize;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        out.println("Enter the name of an input file: ");
        String inputName = in.nextLine();

        out.println("Enter the name of an output file: ");
        String outputName = in.nextLine();

        out.println(
                "Enter the number of words to be included in the tag cloud: ");
        int numWords = in.nextInteger();

        Set<Character> separators = generateSeparators();

        Map<String, Integer> wordsWithCount = generateWordsWithCount(inputName,
                separators);

        Queue<String> queueOfWords = queueKeys(wordsWithCount, numWords);

        Comparator<String> sortAlpha = new StringLT();

        queueOfWords.sort(sortAlpha);

        buildHtml(outputName, queueOfWords, wordsWithCount, inputName);
        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
