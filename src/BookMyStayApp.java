import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages the room counts and provides access to inventory data.
 */
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    public Map<String, Integer> getAllInventory() {
        return inventory;
    }

    public void displayInventory() {
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}

/**
 * CLASS - FilePersistenceService
 * Responsible for persisting critical system state to a plain text file.
 */
class FilePersistenceService {

    /**
     * Saves room inventory state to a file.
     * Format: roomType=availableCount
     */
    public void saveInventory(RoomInventory inventory, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, Integer> entry : inventory.getAllInventory().entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue());
                writer.newLine();
            }
            System.out.println("Inventory saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving inventory: " + e.getMessage());
        }
    }

    /**
     * Loads room inventory state from a file.
     */
    public void loadInventory(RoomInventory inventory, String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("No valid inventory data found. Starting fresh.");
            // Initialize with default values if no file exists
            inventory.addRoomType("Single", 5);
            inventory.addRoomType("Double", 3);
            inventory.addRoomType("Suite", 2);
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    inventory.addRoomType(parts[0], Integer.parseInt(parts[1]));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading inventory, starting with defaults.");
        }
    }
}

/**
 * MAIN CLASS - UseCase12DataPersistenceRecovery
 * Demonstrates how system state can be restored after an application restart.
 * @version 12.0
 */
public class BookMyStayApp {

    public static void main(String[] args) {
        System.out.println("System Recovery");

        String persistenceFile = "inventory.txt";
        RoomInventory inventory = new RoomInventory();
        FilePersistenceService persistenceService = new FilePersistenceService();

        // 1. Attempt to load existing data
        persistenceService.loadInventory(inventory, persistenceFile);

        // 2. Display current state (Recovered or Fresh)
        System.out.println("\nCurrent Inventory:");
        inventory.displayInventory();

        // 3. Persist the current state to ensure it's available for next time
        persistenceService.saveInventory(inventory, persistenceFile);
    }
}