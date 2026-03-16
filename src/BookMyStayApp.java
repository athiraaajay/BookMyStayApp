import java.util.*;

/**
 * Represents a guest's intent to book a room.
 */
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
}

/**
 * CLASS - RoomInventory
 * Manages room availability with thread-safe updates.
 */
class RoomInventory {
    private Map<String, Integer> roomAvailability;

    public RoomInventory() {
        roomAvailability = new HashMap<>();
        roomAvailability.put("Single", 5);
        roomAvailability.put("Double", 3);
        roomAvailability.put("Suite", 2);
    }

    /**
     * Synchronized method to prevent race conditions during booking.
     * This is the "Critical Section".
     */
    public synchronized boolean processBooking(Reservation reservation) {
        String type = reservation.getRoomType();
        int count = roomAvailability.getOrDefault(type, 0);

        if (count > 0) {
            // Simulate processing delay to test concurrency
            try { Thread.sleep(10); } catch (InterruptedException e) {}

            roomAvailability.put(type, count - 1);
            System.out.println("SUCCESS: " + reservation.getGuestName() + " booked a " + type);
            return true;
        } else {
            System.out.println("FAILED: No availability for " + reservation.getGuestName() + " (" + type + ")");
            return false;
        }
    }

    public synchronized Map<String, Integer> getFinalStatus() {
        return new HashMap<>(roomAvailability);
    }
}

/**
 * MAIN CLASS - UseCase11ConcurrentBookingSimulation
 * Demonstrates how synchronization protects shared resources.
 * @version 11.0
 */
public class BookMyStayApp {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Concurrent Booking Simulation Started...\n");

        RoomInventory inventory = new RoomInventory();

        // Creating a list of concurrent booking tasks
        List<Thread> threads = new ArrayList<>();

        // Guests trying to book the limited 2 Suites simultaneously
        String[] guests = {"Alice", "Bob", "Charlie", "David", "Eve"};

        for (String name : guests) {
            Thread t = new Thread(() -> {
                Reservation res = new Reservation(name, "Suite");
                inventory.processBooking(res);
            });
            threads.add(t);
        }

        // Start all threads at once to simulate peak load
        for (Thread t : threads) {
            t.start();
        }

        // Wait for all threads to complete
        for (Thread t : threads) {
            t.join();
        }

        System.out.println("\n--- Final Inventory Status ---");
        System.out.println(inventory.getFinalStatus());
        System.out.println("\nSimulation complete. No double-booking occurred.");
    }
}