//Ori Levy 318501897
import java.util.HashMap;
import java.util.regex.Matcher;

/**
 * The type Back finder.
 * finds the hypernym for the last pattern(from the back).
 */
public class BackFinder extends Finder {
    /**
     * Find lemma.
     * finds the hypernym in the line
     *
     * @param matcher     the matcher that finds
     * @param lemma       the lemma to look for
     * @param shortLemma the short lemma without np
     * @param counter     the map that counts
     */
    public void findHypernym(Matcher matcher, String lemma, String shortLemma, HashMap<String, Integer> counter) {
        //going from the back as been told
        if (matcher.group(1).contains(lemma)) {
            String hypernym = matcher.group(matcher.groupCount());
            put(counter, hypernym);
        }
    }
}


