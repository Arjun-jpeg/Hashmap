import java.util.*;

class FlashSaleInventory {

    HashMap<String, Integer> stockMap = new HashMap<>();
    HashMap<String, Queue<Integer>> waitingList = new HashMap<>();

    // Check stock
    public int checkStock(String productId) {
        return stockMap.getOrDefault(productId, 0);
    }

    // Add product stock
    public void addProduct(String productId, int stock) {
        stockMap.put(productId, stock);
    }

    // Purchase item
    public String purchaseItem(String productId, int userId) {

        int stock = stockMap.getOrDefault(productId, 0);

        if (stock > 0) {
            stockMap.put(productId, stock - 1);
            return "Success, " + (stock - 1) + " units remaining";
        }

        waitingList.putIfAbsent(productId, new LinkedList<>());
        Queue<Integer> queue = waitingList.get(productId);

        queue.add(userId);

        return "Added to waiting list, position #" + queue.size();
    }
}

public class Main {
    public static void main(String[] args) {

        FlashSaleInventory obj = new FlashSaleInventory();

        obj.addProduct("IPHONE15_256GB", 3);

        System.out.println(obj.checkStock("IPHONE15_256GB") + " units available");

        System.out.println(obj.purchaseItem("IPHONE15_256GB", 12345));
        System.out.println(obj.purchaseItem("IPHONE15_256GB", 67890));
        System.out.println(obj.purchaseItem("IPHONE15_256GB", 22222));
        System.out.println(obj.purchaseItem("IPHONE15_256GB", 99999));
    }
}