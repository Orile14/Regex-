//Ori Levy 318501897
import java.util.HashMap;
import java.util.regex.Matcher;

/**
 * The type Front finder.
 * finds the hypernym for the first 4 patterns(from the front)
 */
public class FrontFinder extends Finder {


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
        if (matcher.group(2) != null) {
            //if it is it child
            if (matcher.group(2).contains(lemma)) {
                String hypernym = matcher.group(1);
                if (hypernym.contains(shortLemma)) {
                    return;
                } else {
                    put(counter, hypernym);
                }
            }
        }
    }
}

