import java.util.*;

class ParkingLot {

    class Spot {
        String licensePlate;
        long entryTime;

        Spot(String plate) {
            this.licensePlate = plate;
            this.entryTime = System.currentTimeMillis();
        }
    }

    Spot[] table;
    int size;

    ParkingLot(int capacity) {
        table = new Spot[capacity];
        size = capacity;
    }

    // Hash function
    public int hash(String plate) {
        return Math.abs(plate.hashCode()) % size;
    }

    // Park vehicle using linear probing
    public void parkVehicle(String plate) {

        int index = hash(plate);
        int probes = 0;

        while (table[index] != null) {
            index = (index + 1) % size;
            probes++;
        }

        table[index] = new Spot(plate);

        System.out.println("Vehicle " + plate + " parked at spot #" + index +
                " (" + probes + " probes)");
    }

    // Exit vehicle
    public void exitVehicle(String plate) {

        for (int i = 0; i < size; i++) {

            if (table[i] != null && table[i].licensePlate.equals(plate)) {

                long duration = (System.currentTimeMillis() - table[i].entryTime) / 1000;

                table[i] = null;

                System.out.println("Vehicle " + plate +
                        " exited from spot #" + i +
                        " Duration: " + duration + " seconds");
                return;
            }
        }

        System.out.println("Vehicle not found");
    }

    // Statistics
    public void getStatistics() {

        int occupied = 0;

        for (Spot s : table) {
            if (s != null)
                occupied++;
        }

        double occupancy = (occupied * 100.0) / size;

        System.out.println("Occupancy: " + occupancy + "%");
    }
}

public class Main {

    public static void main(String[] args) {

        ParkingLot obj = new ParkingLot(10);

        obj.parkVehicle("ABC1234");
        obj.parkVehicle("ABC1235");
        obj.parkVehicle("XYZ9999");

        obj.exitVehicle("ABC1234");

        obj.getStatistics();
    }
}