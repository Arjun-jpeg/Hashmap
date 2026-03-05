import java.util.*;

class AnalyticsDashboard {

    HashMap<String, Integer> pageViews = new HashMap<>();
    HashMap<String, Set<String>> uniqueVisitors = new HashMap<>();
    HashMap<String, Integer> trafficSources = new HashMap<>();

    // Process incoming event
    public void processEvent(String url, String userId, String source) {

        // count page views
        pageViews.put(url, pageViews.getOrDefault(url, 0) + 1);

        // track unique visitors
        uniqueVisitors.putIfAbsent(url, new HashSet<>());
        uniqueVisitors.get(url).add(userId);

        // count traffic source
        trafficSources.put(source, trafficSources.getOrDefault(source, 0) + 1);
    }

    // Show dashboard
    public void getDashboard() {

        System.out.println("Top Pages:");

        for (String url : pageViews.keySet()) {

            int views = pageViews.get(url);
            int unique = uniqueVisitors.get(url).size();

            System.out.println(url + " - " + views + " views (" + unique + " unique)");
        }

        System.out.println("\nTraffic Sources:");

        for (String source : trafficSources.keySet()) {

            System.out.println(source + ": " + trafficSources.get(source));
        }
    }
}

public class Main {

    public static void main(String[] args) {

        AnalyticsDashboard obj = new AnalyticsDashboard();

        obj.processEvent("/article/breaking-news", "user_123", "google");
        obj.processEvent("/article/breaking-news", "user_456", "facebook");
        obj.processEvent("/sports/championship", "user_789", "direct");
        obj.processEvent("/article/breaking-news", "user_123", "google");

        obj.getDashboard();
    }
}