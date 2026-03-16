import java.util.LinkedList;
import java.util.Queue;

/**
 * =============================================================================
 * CLASS - Reservation
 * =============================================================================
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

    @Override
    public String toString() {
        return "Guest: " + guestName + " | Room Type: " + roomType;
    }
}

/**
 * =============================================================================
 * CLASS - BookingRequestQueue
 * =============================================================================
 * Manages incoming booking requests in a FIFO order.
 * @version 5.0
 */
class BookingRequestQueue {
    /** Queue to store reservations in order of arrival. */
    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        this.requestQueue = new LinkedList<>();
    }

    /** Adds a new booking request to the queue. */
    public void addRequest(Reservation reservation) {
        requestQueue.add(reservation);
        System.out.println("Request added for: " + reservation.getGuestName());
    }

    /** Returns the queue of reservations. */
    public Queue<Reservation> getRequestQueue() {
        return requestQueue;
    }

    /** Displays all pending requests in the queue. */
    public void displayQueue() {
        System.out.println("\n--- Current Booking Request Queue ---");
        if (requestQueue.isEmpty()) {
            System.out.println("No pending requests.");
        } else {
            for (Reservation res : requestQueue) {
                System.out.println(res);
            }
        }
    }
}

/**
 * =============================================================================
 * MAIN CLASS - UseCase5BookingRequestQueue
 * =============================================================================
 * @version 5.0
 */
public class BookMyStayApp {

    public static void main(String[] args) {
        System.out.println("Booking Request Intake System\n");

        // Initialize the Booking Queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Simulate incoming booking requests (Intake stage)
        bookingQueue.addRequest(new Reservation("Alice", "Suite"));
        bookingQueue.addRequest(new Reservation("Bob", "Single"));
        bookingQueue.addRequest(new Reservation("Charlie", "Double"));

        // Display the queue to verify FIFO order
        bookingQueue.displayQueue();

        System.out.println("\nRequests are waiting for allocation processing.");
    }
}