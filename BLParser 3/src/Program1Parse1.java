import components.program.Program;
import components.program.Program1;
import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.statement.Statement;
import components.utilities.Tokenizer;

/**
 * Layered implementation of secondary method {@code parse} for {@code Program}.
 *
 * @author Put your name here
 *
 */
public final class Program1Parse1 extends Program1 {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Parses a single BL instruction from {@code tokens} returning the
     * instruction name as the value of the function and the body of the
     * instruction in {@code body}.
     *
     * @param tokens
     *            the input tokens
     * @param body
     *            the instruction body
     * @return the instruction name
     * @replaces body
     * @updates tokens
     * @requires <pre>
     * [<"INSTRUCTION"> is a prefix of tokens]  and
     *  [<Tokenizer.END_OF_INPUT> is a suffix of tokens]
     * </pre>
     * @ensures <pre>
     * if [an instruction string is a proper prefix of #tokens]  and
     *    [the beginning name of this instruction equals its ending name]  and
     *    [the name of this instruction does not equal the name of a primitive
     *     instruction in the BL language] then
     *  parseInstruction = [name of instruction at start of #tokens]  and
     *  body = [Statement corresponding to the block string that is the body of
     *          the instruction string at start of #tokens]  and
     *  #tokens = [instruction string at start of #tokens] * tokens
     * else
     *  [report an appropriate error message to the console and terminate client]
     * </pre>
     */
    private static String parseInstruction(Queue<String> tokens,
            Statement body) {
        assert tokens != null : "Violation of: tokens is not null";
        assert body != null : "Violation of: body is not null";
        assert tokens.length() > 0 && tokens.front().equals("INSTRUCTION") : ""
                + "Violation of: <\"INSTRUCTION\"> is proper prefix of tokens";

        
        tokens.dequeue();
        String name = tokens.dequeue();
        Reporter.assertElseFatalError(Tokenizer.isIdentifier(name), "INVALID NAME");
        String i = tokens.dequeue();
        Reporter.assertElseFatalError(i.equals("IS"), "INVALID NAME");
        body.parseBlock(tokens);
        String e = tokens.dequeue();
        Reporter.assertElseFatalError(e.equals("END"), "INVALID NAME");
        String n = tokens.dequeue();
        Reporter.assertElseFatalError(n.equals(name), "INVALID NAME");

        body.parseBlock(tokens);
                String ending = tokens.dequeue();
            Reporter.assertElseFatalError(tokens.length() > 0 && ending.equals("END"), "NO END TAGS");
        String endingname = tokens.dequeue();
        Reporter.assertElseFatalError(endingname.equals(name),"No Name for ending tag");
Reporter.assertElseFatalError(tokens.length() > 0,"Instruction should have no more values");

        // This line added just to make the program compilable.
        return name;
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Program1Parse1() {
        super();
    }

    /*
     * Public methods ---------------------------------------------------------
     */

    @Override
    public void parse(SimpleReader in) {
        assert in != null : "Violation of: in is not null";
        assert in.isOpen() : "Violation of: in.is_open";
        Queue<String> tokens = Tokenizer.tokens(in);
        this.parse(tokens);
    }

    @Override
    public void parse(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        assert tokens.length() > 0 : ""
                + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";


        String start  = tokens.dequeue(); 
        Reporter.assertElseFatalError(start.equals("PROGRAM"),"PROGRAM STATMENT NEEDED");
        String startname = tokens.dequeue();
        boolean t = Tokenizer.isIdentifier(startname);
        Reporter.assertElseFatalError(t, "INVALID NAME");
        String ischeck = tokens.dequeue();
        Reporter.assertElseFatalError(ischeck.equals("IS"), "INVALID NAME");
        Map<String, Statement> bl = this.newContext();
        while (tokens.front().equals("INSTRUCTION")) {

 Statement inbody = this.newBody();
 String inname = parseInstruction(tokens, inbody);
 bl.add(inname, inbody);
 

    }
        String begin = tokens.dequeue();
    Reporter.assertElseFatalError(tokens.length() > 0 && begin.equals("BEGIN"),"BEGIN STATMENT NEEDED");
    Statement body = this.newBody();
     body.parseBlock(tokens);
     String ending = tokens.dequeue();
     Reporter.assertElseFatalError(ending.equals("END"), "NO END TAGS");
     String endingname = tokens.dequeue();
 Reporter.assertElseFatalError(endingname.equals(startname),"No name for ending tag");
 Reporter.assertElseFatalError(tokens.length() > 0,"Program should have no more values");
         this.setName(startname);
         this.swapContext(bl);
         this.swapBody(body);
 String endOfInput = tokens.dequeue();
 Reporter.assertElseFatalError(endOfInput.equals(Tokenizer.END_OF_INPUT),"Unpexpected line");
    }

    /*
     * Main test method -------------------------------------------------------
     */

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        /*
         * Get input file name
         */
        out.print("Enter valid BL program file name: ");
        String fileName = in.nextLine();
        /*
         * Parse input file
         */
        out.println("*** Parsing input file ***");
        Program p = new Program1Parse1();
        SimpleReader file = new SimpleReader1L(fileName);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        p.parse(tokens);
        /*
         * Pretty print the program
         */
        out.println("*** Pretty print of parsed program ***");
        p.prettyPrint(out);

        in.close();
        out.close();
    }

}
