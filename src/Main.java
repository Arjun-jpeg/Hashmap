import java.util.*;

class AutocompleteSystem {

    HashMap<String, Integer> queryFreq = new HashMap<>();

    // Add or update search query
    public void updateFrequency(String query) {
        queryFreq.put(query, queryFreq.getOrDefault(query, 0) + 1);
    }

    // Get suggestions for a prefix
    public List<String> search(String prefix) {

        List<Map.Entry<String, Integer>> matches = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : queryFreq.entrySet()) {
            if (entry.getKey().startsWith(prefix)) {
                matches.add(entry);
            }
        }

        // sort by frequency (highest first)
        matches.sort((a, b) -> b.getValue() - a.getValue());

        List<String> result = new ArrayList<>();
        int limit = Math.min(10, matches.size());

        for (int i = 0; i < limit; i++) {
            result.add(matches.get(i).getKey() + " (" + matches.get(i).getValue() + ")");
        }

        return result;
    }
}

public class Main {

    public static void main(String[] args) {

        AutocompleteSystem obj = new AutocompleteSystem();

        obj.updateFrequency("java tutorial");
        obj.updateFrequency("javascript");
        obj.updateFrequency("java download");
        obj.updateFrequency("java tutorial");
        obj.updateFrequency("java tutorial");
        obj.updateFrequency("javascript");

        System.out.println(obj.search("jav"));
    }
}