//Ori Levy 318501897
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type Np pattern.
 * the pattern to use
 */
public class NpPattern {
    private String p;

    /**
     * Instantiates a new Np pattern.
     *
     * @param p the p
     */
    public NpPattern(String p) {
        this.p = p;
    }

    /**
     * Cmp matcher.
     * compiling the pattern and creating the matcher
     * @param line the line to find in
     * @return the matcher that find matches
     */
    public Matcher cmp(String line) {
        Pattern regex = Pattern.compile(this.p);
        Matcher matcher = regex.matcher(line);
        return matcher;
    }
}
