import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber1L;

/**
 * Customized JUnit test fixture for {@code NaturalNumber3}.
 */
public class NaturalNumber3Test extends NaturalNumberTest {

    @Override
    protected final NaturalNumber constructorTest() {

        return new NaturalNumber3();

    }

    @Override
    protected final NaturalNumber constructorTest(int i) {

        return new NaturalNumber3(i);

    }

    @Override
    protected final NaturalNumber constructorTest(String s) {

        return new NaturalNumber3(s);

        // This line added just to make the component compilable.

    }

    @Override
    protected final NaturalNumber constructorTest(NaturalNumber n) {

        return new NaturalNumber3(n);

        // This line added just to make the component compilable.

    }

    @Override
    protected final NaturalNumber constructorRef() {
        return new NaturalNumber1L();
        // This line added just to make the component compilable.

    }

    @Override
    protected final NaturalNumber constructorRef(int i) {

        return new NaturalNumber1L(i);

        // This line added just to make the component compilable.

    }

    @Override
    protected final NaturalNumber constructorRef(String s) {

        return new NaturalNumber1L(s);

        // This line added just to make the component compilable.

    }

    @Override
    protected final NaturalNumber constructorRef(NaturalNumber n) {

        return new NaturalNumber1L(n);
        // This line added just to make the component compilable.

    }

}
