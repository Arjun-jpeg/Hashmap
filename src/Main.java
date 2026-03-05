import java.util.*;//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

class UsernameChecker {

    HashMap<String, Integer> usernameMap = new HashMap<>();
    HashMap<String, Integer> attemptCount = new HashMap<>();

    // Check availability
    public boolean checkAvailability(String username) {

        attemptCount.put(username, attemptCount.getOrDefault(username, 0) + 1);

        if (usernameMap.containsKey(username)) {
            return false;
        }

        return true;
    }

    // Register username
    public void register(String username, int userId) {
        usernameMap.put(username, userId);
    }

    // Suggest alternatives
    public List<String> suggestAlternatives(String username) {

        List<String> suggestions = new ArrayList<>();

        suggestions.add(username + "1");
        suggestions.add(username + "2");
        suggestions.add(username.replace("_", "."));

        return suggestions;
    }

    // Find most attempted username
    public String getMostAttempted() {

        String most = "";
        int max = 0;

        for (String key : attemptCount.keySet()) {

            if (attemptCount.get(key) > max) {
                max = attemptCount.get(key);
                most = key;
            }
        }

        return most + " (" + max + " attempts)";
    }
}


public class Main {
    public static void main(String[] args) {
        UsernameChecker obj = new UsernameChecker();

        obj.register("john_doe", 101);

        System.out.println(obj.checkAvailability("john_doe"));
        System.out.println(obj.checkAvailability("jane_smith"));

        System.out.println(obj.suggestAlternatives("john_doe"));

        System.out.println(obj.getMostAttempted());

    }
}