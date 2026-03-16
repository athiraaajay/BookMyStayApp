import java.util.HashMap;
import java.util.Map;

/**
 * =============================================================================
 * ABSTRACT CLASS - Room
 * =============================================================================
 */
abstract class Room {
    protected int numberOfBeds;
    protected int squareFeet;
    protected double pricePerNight;

    public Room(int numberOfBeds, int squareFeet, double pricePerNight) {
        this.numberOfBeds = numberOfBeds;
        this.squareFeet = squareFeet;
        this.pricePerNight = pricePerNight;
    }

    public void displayRoomDetails() {
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Size: " + squareFeet + " sqft");
        System.out.println("Price per night: " + pricePerNight);
    }
}

class SingleRoom extends Room { public SingleRoom() { super(1, 250, 1500.0); } }
class DoubleRoom extends Room { public DoubleRoom() { super(2, 400, 2500.0); } }
class SuiteRoom extends Room { public SuiteRoom() { super(3, 750, 5000.0); } }

/**
 * =============================================================================
 * CLASS - RoomInventory
 * =============================================================================
 * Use Case 3: Centralized Room Inventory Management
 * @version 3.1
 */
class RoomInventory {
    /** Stores available room count for each room type. */
    private Map<String, Integer> roomAvailability;

    /** Constructor initializes the inventory with default values. */
    public RoomInventory() {
        roomAvailability = new HashMap<>();
        initializeInventory();
    }

    /** Initializes room availability data. */
    private void initializeInventory() {
        roomAvailability.put("Single", 5);
        roomAvailability.put("Double", 3);
        roomAvailability.put("Suite", 2);
    }

    /** Returns the current availability map. */
    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }

    /** Updates availability for a specific room type. */
    public void updateAvailability(String roomType, int count) {
        roomAvailability.put(roomType, count);
    }
}

/**
 * =============================================================================
 * MAIN CLASS - UseCase3InventorySetup
 * =============================================================================
 * @version 3.1
 */
public class BookMyStayApp {

    public static void main(String[] args) {
        System.out.println("Hotel Room Inventory Status\n");

        // Initialize Inventory and Rooms
        RoomInventory inventory = new RoomInventory();
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        Map<String, Integer> availability = inventory.getRoomAvailability();

        // Display Single Room
        System.out.println("Single Room:");
        single.displayRoomDetails();
        System.out.println("Available Rooms: " + availability.get("Single") + "\n");

        // Display Double Room
        System.out.println("Double Room:");
        doubleRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + availability.get("Double") + "\n");

        // Display Suite Room
        System.out.println("Suite Room:");
        suite.displayRoomDetails();
        System.out.println("Available Rooms: " + availability.get("Suite"));
    }
}