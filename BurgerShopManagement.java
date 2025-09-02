import java.util.Scanner;

public class BurgerShopManagement {
    final static double BURGERPRICE = 500.0;
    public static final int PREPARING = 0;
    public static final int DELIVERED = 1;
    public static final int CANCEL = 2;

    static String[] orderIDs = new String[100];
    static String[] customerIDs = new String[100];
    static String[] customerNames = new String[100];
    static int[] burgerQty = new int[100];
    static int[] orderStatus = new int[100];
    static int orderCount = 0;

    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n-------------------------------");
            System.out.println("          iHungry Burger        ");
            System.out.println("-------------------------------");
            System.out.println("[1] Place Order        [2] Search Best Customer");
            System.out.println("[3] Search Order       [4] Search Customer");
            System.out.println("[5] View Orders        [6] Update Order Details");
            System.out.println("[7] Exit");
            System.out.print("\nEnter an option to continue > ");

            int choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1:
                    placeOrder();
                    break;
                case 2:
                    System.out.println("Search Best Customer - Feature coming soon...");
                    break;
                case 3:
                    System.out.println("Search Order - Feature coming soon...");
                    break;
                case 4:
                    System.out.println("Search Customer - Feature coming soon...");
                    break;
                case 5:
                    System.out.println("View Orders - Feature coming soon...");
                    break;
                case 6:
                    System.out.println("Update Order Details - Feature coming soon...");
                    break;
                case 7:
                    exit();
                    break;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }

    public static String generateOrderID() {
        return String.format("B%03d", orderCount + 1);
    }

    public static void placeOrder() {
        String orderID = generateOrderID();
        System.out.println("\nOrder ID: " + orderID);

        String customerID;
        while (true) {
            System.out.print("Enter Customer ID (phone number): ");
            customerID = input.nextLine();
            if (customerID.matches("0\\d{9}")) break;
            System.out.println("Invalid phone number! Must start with 0 and have 10 digits.");
        }

        String customerName = null;
        boolean found = false;
        for (int i = 0; i < orderCount; i++) {
            if (customerIDs[i].equals(customerID)) {
                customerName = customerNames[i];
                found = true;
                break;
            }
        }

        if (found) {
            System.out.println("Existing Customer: " + customerName);
        } else {
            System.out.print("Enter Customer Name: ");
            customerName = input.nextLine();
        }

        int qty;
        while (true) {
            System.out.print("Enter Burger Quantity: ");
            qty = input.nextInt();
            input.nextLine();
            if (qty > 0) break;
            System.out.println("Quantity must be greater than 0.");
        }

        double total = qty * BURGERPRICE;
        System.out.println("Total Bill: Rs. " + total);

        System.out.print("Confirm Order? (Y/N): ");
        String confirm = input.nextLine().toUpperCase();

        if (confirm.equals("Y")) {
            orderIDs[orderCount] = orderID;
            customerIDs[orderCount] = customerID;
            customerNames[orderCount] = customerName;
            burgerQty[orderCount] = qty;
            orderStatus[orderCount] = PREPARING;
            orderCount++;
            System.out.println("Order Placed Successfully!");
        } else {
            System.out.println("Order Cancelled!");
        }
    }

    public static void exit() {
        System.out.println("\nYou left the program...");
        System.exit(0);
    }
}
