//Ori Levy 318501897
import java.util.HashMap;
/**
 * The type Finder.
 * finds the hypernym
 */
public class Finder {

    /**
     * Put.
     * put the hypernym in the map that count occurrences
     *
     * @param counter  the counter
     * @param hypernym the hypernym
     */
    public void put(HashMap<String, Integer> counter, String hypernym) {
        if (counter.containsKey(hypernym)) {
            //old one, just update
            counter.put(hypernym, counter.get(hypernym) + 1);
            return;
        } else {
            //its new word
            counter.put(hypernym, 1);
            return;
        }
    }

}
