import java.util.*;

/**
 * Represents an individual optional offering.
 */
class AddOnService {
    private String serviceName;
    private double price;

    public AddOnService(String serviceName, double price) {
        this.serviceName = serviceName;
        this.price = price;
    }

    public String getServiceName() { return serviceName; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return serviceName + " (Rs. " + price + ")";
    }
}

/**
 * Manages the association between reservations and selected services.
 * Use Case 7: Add-On Service Selection
 * @version 7.0
 */
class AddOnServiceManager {
    /** Maps reservation ID to a list of selected services. */
    private Map<String, List<AddOnService>> reservationServices;

    public AddOnServiceManager() {
        this.reservationServices = new HashMap<>();
    }

    /** Adds a service to a specific reservation ID. */
    public void addServiceToReservation(String reservationId, AddOnService service) {
        reservationServices
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);
        System.out.println("Added " + service.getServiceName() + " to Reservation: " + reservationId);
    }

    /** Calculates and displays the services and total cost for a reservation. */
    public void displayReservationServices(String reservationId) {
        List<AddOnService> services = reservationServices.get(reservationId);
        System.out.println("\n--- Services for Reservation: " + reservationId + " ---");

        if (services == null || services.isEmpty()) {
            System.out.println("No add-on services selected.");
        } else {
            double totalCost = 0;
            for (AddOnService service : services) {
                System.out.println("- " + service);
                totalCost += service.getPrice();
            }
            System.out.println("Total Add-On Cost: Rs. " + totalCost);
        }
    }
}

/**
 * MAIN CLASS - UseCase7AddOnServiceSelection
 * @version 7.0
 */
public class BookMyStayApp {

    public static void main(String[] args) {
        System.out.println("Hotel Add-On Service Management\n");

        // Initialize Manager
        AddOnServiceManager serviceManager = new AddOnServiceManager();

        // Define available services
        AddOnService wifi = new AddOnService("Premium WiFi", 500.0);
        AddOnService breakfast = new AddOnService("Buffet Breakfast", 1200.0);
        AddOnService spa = new AddOnService("Spa Session", 3000.0);

        // Simulation: Adding services to existing Reservation IDs
        String resId1 = "S-1"; // Assume this came from Use Case 6
        String resId2 = "D-1";

        // Assigning multiple services to Alice (S-1)
        serviceManager.addServiceToReservation(resId1, wifi);
        serviceManager.addServiceToReservation(resId1, breakfast);

        // Assigning one service to Bob (D-1)
        serviceManager.addServiceToReservation(resId2, spa);

        // Display summary for Alice
        serviceManager.displayReservationServices(resId1);

        // Display summary for Bob
        serviceManager.displayReservationServices(resId2);
    }
}