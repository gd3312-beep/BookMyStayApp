import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

class RoomInventory {
  private Map<String, Integer> inventory;

  public RoomInventory() {
    inventory = new HashMap<>();
    inventory.put("Single", 5);
    inventory.put("Double", 3);
    inventory.put("Suite", 2);
  }

  public Map<String, Integer> getInventory() {
    return inventory;
  }

  public void setInventory(Map<String, Integer> inventory) {
    this.inventory = inventory;
  }

  public void displayInventory() {
    System.out.println("Current Inventory:");
    for (String type : inventory.keySet()) {
      System.out.println(type + ": " + inventory.get(type));
    }
  }
}

class FilePersistenceService {
  public void saveInventory(RoomInventory inventory, String filePath) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
      for (Map.Entry<String, Integer> entry : inventory.getInventory().entrySet()) {
        writer.write(entry.getKey() + "=" + entry.getValue());
        writer.newLine();
      }

      System.out.println("Inventory saved successfully.");
    } catch (IOException exception) {
      System.out.println("Error saving inventory.");
    }
  }

  public void loadInventory(RoomInventory inventory, String filePath) {
    File file = new File(filePath);

    if (!file.exists()) {
      System.out.println("No valid inventory data found. Starting fresh.");
      return;
    }

    Map<String, Integer> loadedData = new HashMap<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
      String line;

      while ((line = reader.readLine()) != null) {
        String[] parts = line.split("=");

        if (parts.length == 2) {
          String roomType = parts[0];
          int count = Integer.parseInt(parts[1]);
          loadedData.put(roomType, count);
        }
      }

      inventory.setInventory(loadedData);
    } catch (Exception exception) {
      System.out.println("Error loading inventory. Starting fresh.");
    }
  }
}

public class BookMyStay {
  public static void main(String[] args) {
    System.out.println("System Recovery");

    RoomInventory inventory = new RoomInventory();
    FilePersistenceService persistenceService = new FilePersistenceService();
    String filePath = "inventory.txt";

    persistenceService.loadInventory(inventory, filePath);
    inventory.displayInventory();
    persistenceService.saveInventory(inventory, filePath);
  }
}
