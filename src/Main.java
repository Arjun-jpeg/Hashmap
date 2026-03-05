import java.util.*;

class MultiLevelCache {

    // L1 Cache (Memory)
    LinkedHashMap<String, String> L1 = new LinkedHashMap<>(16, 0.75f, true);

    // L2 Cache (SSD simulation)
    HashMap<String, String> L2 = new HashMap<>();

    // L3 Database (All videos)
    HashMap<String, String> L3 = new HashMap<>();

    int L1_LIMIT = 3;

    int l1Hits = 0;
    int l2Hits = 0;
    int l3Hits = 0;

    // Add video to database
    public void addVideo(String id, String data) {
        L3.put(id, data);
    }

    // Get video
    public String getVideo(String id) {

        // L1 Cache check
        if (L1.containsKey(id)) {
            l1Hits++;
            return "L1 Cache HIT → " + L1.get(id);
        }

        // L2 Cache check
        if (L2.containsKey(id)) {
            l2Hits++;

            String data = L2.get(id);
            promoteToL1(id, data);

            return "L2 Cache HIT → Promoted to L1";
        }

        // L3 Database check
        if (L3.containsKey(id)) {
            l3Hits++;

            String data = L3.get(id);
            L2.put(id, data);

            return "L3 Database HIT → Added to L2";
        }

        return "Video Not Found";
    }

    // Promote video to L1 cache
    public void promoteToL1(String id, String data) {

        if (L1.size() >= L1_LIMIT) {
            String firstKey = L1.keySet().iterator().next();
            L1.remove(firstKey);
        }

        L1.put(id, data);
    }

    // Show statistics
    public void getStatistics() {

        int total = l1Hits + l2Hits + l3Hits;

        System.out.println("L1 Hits: " + l1Hits);
        System.out.println("L2 Hits: " + l2Hits);
        System.out.println("L3 Hits: " + l3Hits);

        if (total > 0) {
            System.out.println("Overall Hit Rate: " + (100.0 * (l1Hits + l2Hits) / total) + "%");
        }
    }
}

public class Main {

    public static void main(String[] args) {

        MultiLevelCache cache = new MultiLevelCache();

        cache.addVideo("video1", "Movie A");
        cache.addVideo("video2", "Movie B");
        cache.addVideo("video3", "Movie C");

        System.out.println(cache.getVideo("video1"));
        System.out.println(cache.getVideo("video1"));
        System.out.println(cache.getVideo("video2"));

        cache.getStatistics();
    }
}