import java.util.ArrayList;
import java.util.List;

/**
 * Data model for a Reservation.
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
 * CLASS - BookingHistory
 * This class maintains a record of confirmed reservations using a List.
 */
class BookingHistory {
    private List<Reservation> confirmedReservations;

    public BookingHistory() {
        this.confirmedReservations = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        confirmedReservations.add(reservation);
    }

    public List<Reservation> getConfirmedReservations() {
        return confirmedReservations;
    }
}

/**
 * CLASS - BookingReportService
 * This class generates reports from booking history data.
 */
class BookingReportService {
    /**
     * Displays a summary report of all confirmed bookings.
     * @param history booking history
     */
    public void generateReport(BookingHistory history) {
        System.out.println("Booking History Report");
        for (Reservation reservation : history.getConfirmedReservations()) {
            System.out.println("Guest: " + reservation.getGuestName() +
                    ", Room Type: " + reservation.getRoomType());
        }
    }
}

/**
 * MAIN CLASS - UseCase8BookingHistoryReport
 * This class demonstrates how confirmed bookings are stored and reported.
 * @version 8.0
 */
public class BookMyStayApp {

    public static void main(String[] args) {
        System.out.println("Booking History and Reporting\n");

        // 1. Initialize data storage and the reporting service
        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // 2. Add confirmed reservations to history (simulating Use Case Flow)
        history.addReservation(new Reservation("Abhi", "Single"));
        history.addReservation(new Reservation("Subha", "Double"));
        history.addReservation(new Reservation("Vanmathi", "Suite"));

        // 3. Generate and display the report
        reportService.generateReport(history);
    }
}