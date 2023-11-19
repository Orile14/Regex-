//Ori Levy 318501897
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

/**
 * The type Discover hypernym.
 */
public class DiscoverHypernym {
    private static final String NP = "<np>([^<]+)</np>";

    /**
     * The entry point of application.
     *
     * @param args the input arguments to look in and for
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("not enough arguments");
            return;
        }
        String directoryPath = args[0];
        String shortLemma = args[1];
        //if lemma is bigger than one word
        if (args.length > 1) {
            for (int i = 2; i < args.length; i++) {
                shortLemma = shortLemma + " " + args[i];
            }
        }
        //create it as it in the txt
        String lemma = "<np>" + shortLemma + "</np>";
        File directory = new File(directoryPath);
        HashMap<String, Integer> counter = new HashMap<String, Integer>();
        if (!directory.isDirectory()) {
            System.out.println("Invalid corpus directory path.");
            return;
        }
        //creating patterns as needed
        NpPattern a = new NpPattern(
                NP + " ,* *(such as (" + NP + ")( , " + NP + "){0,}( ,){0,}(( or| and) " + NP + "){0,1})");
        NpPattern b = new NpPattern(
                "such (" + NP + ") as ((" + NP + ")(( )*(,)*( )*(" + NP + "))*( ,){0,}( (and|or) " + NP + ")*)");
        NpPattern c = new NpPattern(
                NP + " ,* *including ((" + NP + ")(( , )+(" + NP + ")){0,}( ,){0,}(( or| and) (" + NP + ")){0,1})");
        NpPattern d = new NpPattern(
                NP + " ,* *especially *(((" + NP + ")()*( , " + NP + "){0,}( ,){0,}(( or| and) " + NP + "){0,}))");
        NpPattern e1 = new NpPattern(
                "(" + NP + ") (, )?which is (((an example|a kind|a class) of )*(a ){0,1}(" + NP + "))");
        List<NpPattern> patterns = new ArrayList<>();
        patterns.add(a);
        patterns.add(b);
        patterns.add(c);
        patterns.add(d);
        patterns.add(e1);
        File[] files = directory.listFiles();
        //going over all files
        if (files != null) {
            for (File file : files) {
                //read from file
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    //read line
                    while ((line = reader.readLine()) != null) {
                        if (line.contains(lemma)) {
                            //go over all the patterns
                            for (NpPattern pattern : patterns) {
                                Matcher matcher = pattern.cmp(line);
                                while (matcher.find()) {
                                    //if its the last special pattern
                                    if (pattern == patterns.get(4)) {
                                        BackFinder backFinder = new BackFinder();
                                        backFinder.findHypernym(matcher, lemma, shortLemma, counter
                                        );
                                    } else {
                                        FrontFinder frontFinder = new FrontFinder();
                                        frontFinder.findHypernym(matcher, lemma, shortLemma, counter);
                                    }
                                }
                            }
                        }
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(counter.entrySet());

        // Sort the list in descending order by values
        entryList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        // Iterate over the sorted list and print the entries
        for (Map.Entry<String, Integer> entry : entryList) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            System.out.println(key + ": (" + value + ")");
        }
        if (entryList.size() == 0) {
            System.out.println("The lemma doesn't appear in the corpus.");
        }
    }
}