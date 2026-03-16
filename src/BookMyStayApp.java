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
 * Manages room availability using a HashMap.
 */
class RoomInventory {
    private Map<String, Integer> roomAvailability;

    public RoomInventory() {
        roomAvailability = new HashMap<>();
        roomAvailability.put("Single", 5);
        roomAvailability.put("Double", 3);
        roomAvailability.put("Suite", 2);
    }

    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }

    public void updateAvailability(String roomType, int count) {
        roomAvailability.put(roomType, count);
    }
}

/**
 * Processes queued requests and handles room allocation.
 * Use Case 6: Reservation Confirmation & Room Allocation
 * @version 6.0
 */
class RoomAllocationService {
    // Maps room type to a Set of uniquely assigned room IDs
    private Map<String, Set<String>> allocatedRooms;

    public RoomAllocationService() {
        allocatedRooms = new HashMap<>();
        allocatedRooms.put("Single", new HashSet<>());
        allocatedRooms.put("Double", new HashSet<>());
        allocatedRooms.put("Suite", new HashSet<>());
    }

    /**
     * Dequeues requests and allocates rooms if available.
     */
    public void processAllocations(Queue<Reservation> queue, RoomInventory inventory) {
        System.out.println("Processing Room Allocations...\n");

        while (!queue.isEmpty()) {
            Reservation request = queue.poll(); // Dequeue in FIFO order
            String type = request.getRoomType();
            int availableCount = inventory.getRoomAvailability().getOrDefault(type, 0);

            if (availableCount > 0) {
                // Generate a unique Room ID (e.g., S-1, D-1, etc.)
                String roomId = type.charAt(0) + "-" + (allocatedRooms.get(type).size() + 1);

                // Add to Set to ensure uniqueness (Prevention of Double-Booking)
                allocatedRooms.get(type).add(roomId);

                // Decrement Inventory
                inventory.updateAvailability(type, availableCount - 1);

                System.out.println("CONFIRMED: " + request.getGuestName() +
                        " | Room: " + roomId + " (" + type + ")");
            } else {
                System.out.println("FAILED: No availability for " + request.getGuestName() +
                        " (" + type + ")");
            }
        }
    }
}

/**
 * MAIN CLASS - UseCase6RoomAllocationService
 */
public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("Hotel Reservation & Allocation System\n");

        // 1. Setup Inventory
        RoomInventory inventory = new RoomInventory();

        // 2. Setup Request Queue (FIFO)
        Queue<Reservation> bookingQueue = new LinkedList<>();
        bookingQueue.add(new Reservation("Alice", "Suite"));
        bookingQueue.add(new Reservation("Bob", "Single"));
        bookingQueue.add(new Reservation("Charlie", "Suite"));
        bookingQueue.add(new Reservation("David", "Suite")); // Should fail (only 2 Suites)

        // 3. Setup Allocation Service and Process
        RoomAllocationService allocationService = new RoomAllocationService();
        allocationService.processAllocations(bookingQueue, inventory);

        // 4. Show final inventory state
        System.out.println("\nFinal Inventory Status: " + inventory.getRoomAvailability());
    }
}