import java.util.*;

class DNSEntry {
    String ip;
    long expiryTime;

    DNSEntry(String ip, int ttlSeconds) {
        this.ip = ip;
        this.expiryTime = System.currentTimeMillis() + (ttlSeconds * 1000);
    }

    boolean isExpired() {
        return System.currentTimeMillis() > expiryTime;
    }
}

class DNSCache {

    HashMap<String, DNSEntry> cache = new HashMap<>();
    int hits = 0;
    int misses = 0;

    // Resolve domain
    public String resolve(String domain) {

        if (cache.containsKey(domain)) {
            DNSEntry entry = cache.get(domain);

            if (!entry.isExpired()) {
                hits++;
                return "Cache HIT → " + entry.ip;
            } else {
                cache.remove(domain);
                System.out.println("Cache EXPIRED");
            }
        }

        misses++;
        String ip = queryUpstream(domain);

        cache.put(domain, new DNSEntry(ip, 10)); // TTL = 10 seconds
        return "Cache MISS → " + ip;
    }

    // Simulate upstream DNS query
    public String queryUpstream(String domain) {
        return "192.168.1." + new Random().nextInt(255);
    }

    // Cache statistics
    public void getCacheStats() {
        int total = hits + misses;
        double hitRate = total == 0 ? 0 : (hits * 100.0) / total;

        System.out.println("Hits: " + hits);
        System.out.println("Misses: " + misses);
        System.out.println("Hit Rate: " + hitRate + "%");
    }
}

public class Main {
    public static void main(String[] args) {

        DNSCache dns = new DNSCache();

        System.out.println(dns.resolve("google.com"));
        System.out.println(dns.resolve("google.com"));

        dns.getCacheStats();
    }
}