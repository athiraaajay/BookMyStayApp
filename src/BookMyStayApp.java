import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Manages the available room counts.
 */
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        // Initializing with sample data (5 Single rooms available)
        inventory.put("Single", 5);
        inventory.put("Double", 3);
    }

    public void restoreRoom(String roomType) {
        inventory.put(roomType, inventory.get(roomType) + 1);
    }

    public int getAvailableRooms(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}

/**
 * CLASS - CancellationService
 * Responsible for handling booking cancellations and inventory rollback.
 */
class CancellationService {
    // Stack used to track released room IDs for rollback (LIFO)
    private Stack<String> releasedRoomIds;
    // Map used to associate a Reservation ID with its Room Type
    private Map<String, String> reservationRoomTypeMap;

    public CancellationService() {
        this.releasedRoomIds = new Stack<>();
        this.reservationRoomTypeMap = new HashMap<>();
    }

    /**
     * Registers a confirmed booking for potential later cancellation.
     */
    public void registerBooking(String reservationId, String roomType) {
        reservationRoomTypeMap.put(reservationId, roomType);
    }

    /**
     * Cancels a confirmed booking and restores inventory safely.
     */
    public void cancelBooking(String reservationId, RoomInventory inventory) {
        if (!reservationRoomTypeMap.containsKey(reservationId)) {
            System.out.println("Error: Reservation ID not found.");
            return;
        }

        String roomType = reservationRoomTypeMap.get(reservationId);

        // Push to stack to track the rollback history
        releasedRoomIds.push(reservationId);

        // Restore the inventory count
        inventory.restoreRoom(roomType);

        // Remove from active reservations
        reservationRoomTypeMap.remove(reservationId);

        System.out.println("Booking cancelled successfully. Inventory restored for room type: " + roomType);
    }

    /**
     * Displays recently cancelled reservations (Most Recent First).
     */
    public void showRollbackHistory() {
        System.out.println("\nRollback History (Most Recent First):");
        if (releasedRoomIds.isEmpty()) {
            System.out.println("No history found.");
            return;
        }

        // Display stack contents in reverse order (LIFO)
        for (int i = releasedRoomIds.size() - 1; i >= 0; i--) {
            System.out.println("Released Reservation ID: " + releasedRoomIds.get(i));
        }
    }
}

/**
 * MAIN CLASS - UseCase10BookingCancellation
 * This class demonstrates how confirmed bookings can be cancelled safely.
 * @version 10.0
 */
public class BookMyStayApp {

    public static void main(String[] args) {
        System.out.println("Booking Cancellation");

        // 1. Initialize Inventory and Service
        RoomInventory inventory = new RoomInventory();
        CancellationService cancellationService = new CancellationService();

        // 2. Simulate a confirmed booking (Single-1)
        cancellationService.registerBooking("Single-1", "Single");

        // 3. Perform the cancellation
        cancellationService.cancelBooking("Single-1", inventory);

        // 4. Show the Rollback History (Stack demonstration)
        cancellationService.showRollbackHistory();

        // 5. Display updated availability
        System.out.println("\nUpdated Single Room Availability: " + inventory.getAvailableRooms("Single"));
    }
}