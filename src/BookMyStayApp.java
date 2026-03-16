/**
 * =============================================================================
 * ABSTRACT CLASS - Room
 * =============================================================================
 * Use Case 2: Basic Room Types & Static Availability
 * @version 2.1
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

/**
 * CLASS - SingleRoom
 */
class SingleRoom extends Room {
    public SingleRoom() {
        super(1, 250, 1500.0);
    }
}

/**
 * CLASS - DoubleRoom
 */
class DoubleRoom extends Room {
    public DoubleRoom() {
        super(2, 400, 2500.0);
    }
}

/**
 * CLASS - SuiteRoom
 */
class SuiteRoom extends Room {
    public SuiteRoom() {
        super(3, 750, 5000.0);
    }
}

/**
 * =============================================================================
 * MAIN CLASS - UseCase2RoomInitialization
 * =============================================================================
 * @version 2.1
 */
public class BookMyStayApp{

    public static void main(String[] args) {
        System.out.println("Hotel Room Initialization\n");

        // Initialize Room Objects
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Static availability variables
        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        // Display Single Room
        System.out.println("Single Room:");
        single.displayRoomDetails();
        System.out.println("Available: " + singleAvailable + "\n");

        // Display Double Room
        System.out.println("Double Room:");
        doubleRoom.displayRoomDetails();
        System.out.println("Available: " + doubleAvailable + "\n");

        // Display Suite Room
        System.out.println("Suite Room:");
        suite.displayRoomDetails();
        System.out.println("Available: " + suiteAvailable);
    }
}