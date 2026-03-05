import java.util.*;

class PlagiarismDetector {

    HashMap<String, Set<String>> ngramMap = new HashMap<>();

    // Break document into n-grams
    public List<String> generateNgrams(String text, int n) {

        String[] words = text.split(" ");
        List<String> ngrams = new ArrayList<>();

        for (int i = 0; i <= words.length - n; i++) {

            String gram = "";
            for (int j = 0; j < n; j++) {
                gram += words[i + j] + " ";
            }

            ngrams.add(gram.trim());
        }

        return ngrams;
    }

    // Store document n-grams
    public void addDocument(String docId, String text) {

        List<String> grams = generateNgrams(text, 3);

        for (String g : grams) {

            ngramMap.putIfAbsent(g, new HashSet<>());
            ngramMap.get(g).add(docId);
        }
    }

    // Analyze document similarity
    public void analyzeDocument(String docId, String text) {

        List<String> grams = generateNgrams(text, 3);
        HashMap<String, Integer> matchCount = new HashMap<>();

        for (String g : grams) {

            if (ngramMap.containsKey(g)) {

                for (String doc : ngramMap.get(g)) {

                    if (!doc.equals(docId)) {
                        matchCount.put(doc, matchCount.getOrDefault(doc, 0) + 1);
                    }
                }
            }
        }

        for (String doc : matchCount.keySet()) {

            int matches = matchCount.get(doc);
            double similarity = (matches * 100.0) / grams.size();

            System.out.println("Matches with " + doc + " → " + matches +
                    " n-grams, Similarity: " + similarity + "%");
        }
    }
}

public class Main {

    public static void main(String[] args) {

        PlagiarismDetector obj = new PlagiarismDetector();

        obj.addDocument("essay_089.txt",
                "machine learning is used in many applications today");

        obj.addDocument("essay_092.txt",
                "machine learning is used in many applications today and future");

        obj.analyzeDocument("essay_123.txt",
                "machine learning is used in many applications");
    }
}