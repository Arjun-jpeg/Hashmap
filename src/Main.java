import java.util.*;

class RateLimiter {

    class TokenBucket {
        int tokens;
        long lastReset;

        TokenBucket(int maxTokens) {
            this.tokens = maxTokens;
            this.lastReset = System.currentTimeMillis();
        }
    }

    HashMap<String, TokenBucket> clients = new HashMap<>();

    int MAX_REQUESTS = 5; // example limit (instead of 1000 for testing)
    long WINDOW = 3600000; // 1 hour in milliseconds

    // Check rate limit
    public String checkRateLimit(String clientId) {

        clients.putIfAbsent(clientId, new TokenBucket(MAX_REQUESTS));
        TokenBucket bucket = clients.get(clientId);

        long now = System.currentTimeMillis();

        // Reset after 1 hour
        if (now - bucket.lastReset > WINDOW) {
            bucket.tokens = MAX_REQUESTS;
            bucket.lastReset = now;
        }

        if (bucket.tokens > 0) {
            bucket.tokens--;
            return "Allowed (" + bucket.tokens + " requests remaining)";
        }

        long retry = (WINDOW - (now - bucket.lastReset)) / 1000;
        return "Denied (retry after " + retry + " seconds)";
    }

    // Show client status
    public void getRateLimitStatus(String clientId) {

        TokenBucket bucket = clients.get(clientId);

        int used = MAX_REQUESTS - bucket.tokens;

        System.out.println("Used: " + used);
        System.out.println("Limit: " + MAX_REQUESTS);
    }
}

public class Main {

    public static void main(String[] args) {

        RateLimiter obj = new RateLimiter();

        System.out.println(obj.checkRateLimit("abc123"));
        System.out.println(obj.checkRateLimit("abc123"));
        System.out.println(obj.checkRateLimit("abc123"));
        System.out.println(obj.checkRateLimit("abc123"));
        System.out.println(obj.checkRateLimit("abc123"));
        System.out.println(obj.checkRateLimit("abc123"));

        obj.getRateLimitStatus("abc123");
    }
}