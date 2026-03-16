import java.util.HashMap;
import java.util.Map;

/**
 * CUSTOM EXCEPTION - InvalidBookingException
 * Represents errors specific to the hotel booking domain.
 */
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

/**
 * CLASS - RoomInventory
 * Manages room availability and performs state validation.
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
     * Validates and processes a booking request.
     * Throws an exception if the room type is invalid or unavailable.
     */
    public void validateAndProcessBooking(String roomType) throws InvalidBookingException {
        // Step 1: Validate Room Type (Case Sensitivity Check)
        if (!roomAvailability.containsKey(roomType)) {
            throw new InvalidBookingException("FAILURE: Invalid Room Type provided -> " + roomType);
        }

        // Step 2: Validate Availability
        int count = roomAvailability.get(roomType);
        if (count <= 0) {
            throw new InvalidBookingException("FAILURE: No availability for Room Type -> " + roomType);
        }

        // Step 3: Update State only if valid
        roomAvailability.put(roomType, count - 1);
        System.out.println("SUCCESS: Booking confirmed for " + roomType);
    }

    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }
}

/**
 * MAIN CLASS - UseCase9ErrorHandlingValidation
 * Demonstrates structured error handling and validation.
 * @version 9.0
 */
public class BookMyStayApp {

    public static void main(String[] args) {
        System.out.println("Hotel Booking Validation System\n");

        RoomInventory inventory = new RoomInventory();

        // Array of test cases including valid and invalid inputs
        String[] testBookings = {"Single", "Penthouse", "Suite", "Single", "Deluxe"};

        for (String type : testBookings) {
            try {
                System.out.println("Processing request for: " + type);
                inventory.validateAndProcessBooking(type);
            } catch (InvalidBookingException e) {
                // Graceful failure handling
                System.out.println(e.getMessage());
            } finally {
                System.out.println("Current Inventory: " + inventory.getRoomAvailability());
                System.out.println("------------------------------------------------");
            }
        }

        System.out.println("\nSystem continues to run safely after handling errors.");
    }
}