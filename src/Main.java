import java.util.*;

class Transaction {
    int id;
    int amount;
    String merchant;

    Transaction(int id, int amount, String merchant) {
        this.id = id;
        this.amount = amount;
        this.merchant = merchant;
    }
}

class FraudDetector {

    List<Transaction> transactions = new ArrayList<>();

    // Add transaction
    public void addTransaction(int id, int amount, String merchant) {
        transactions.add(new Transaction(id, amount, merchant));
    }

    // Classic Two-Sum
    public void findTwoSum(int target) {

        HashMap<Integer, Transaction> map = new HashMap<>();

        for (Transaction t : transactions) {

            int complement = target - t.amount;

            if (map.containsKey(complement)) {

                Transaction other = map.get(complement);

                System.out.println("Pair Found → (" + other.id + ", " + t.id + ")");
                return;
            }

            map.put(t.amount, t);
        }

        System.out.println("No pair found");
    }

    // Detect duplicate payments (same amount + merchant)
    public void detectDuplicates() {

        HashMap<String, List<Integer>> map = new HashMap<>();

        for (Transaction t : transactions) {

            String key = t.amount + "-" + t.merchant;

            map.putIfAbsent(key, new ArrayList<>());
            map.get(key).add(t.id);
        }

        for (String key : map.keySet()) {

            if (map.get(key).size() > 1) {
                System.out.println("Duplicate transaction → " + key +
                        " IDs: " + map.get(key));
            }
        }
    }
}

public class Main {

    public static void main(String[] args) {

        FraudDetector obj = new FraudDetector();

        obj.addTransaction(1, 500, "StoreA");
        obj.addTransaction(2, 300, "StoreB");
        obj.addTransaction(3, 200, "StoreC");
        obj.addTransaction(4, 500, "StoreA");

        obj.findTwoSum(500);

        obj.detectDuplicates();
    }
}