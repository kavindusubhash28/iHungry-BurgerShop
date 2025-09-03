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
        boolean running = true;
        while (running) {
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
                    searchBestCustomer();
                    break;
                case 3:
                    searchOrder();
                    break;
                case 4:
                    searchCustomer();
                    break;
                case 5:
                    // Show the view orders screen
                    viewOrders();
                    break;
                case 6:
                    // Show the update order details screen
                    updateOrders();
                    break;
                case 7:
                    // allow loop to terminate cleanly
                    running = false;
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
        while (true) {
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

            System.out.print("Do you want to place another order? (Y/N): ");
            String again = input.nextLine().toUpperCase();
            if (!again.equals("Y")) {
                break;
            }
        }
    }

    public static void searchBestCustomer() {
        if (orderCount == 0) {
            System.out.println("No orders available.");
            return;
        }
        String[] uniqueIDs = new String[orderCount];
        double[] totals = new double[orderCount];
        int uniqueCount = 0;
        for (int i = 0; i < orderCount; i++) {
            String id = customerIDs[i];
            double total = burgerQty[i] * BURGERPRICE;
            boolean exists = false;
            for (int j = 0; j < uniqueCount; j++) {
                if (uniqueIDs[j].equals(id)) {
                    totals[j] += total;
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                uniqueIDs[uniqueCount] = id;
                totals[uniqueCount] = total;
                uniqueCount++;
            }
        }

        for (int i = 0; i < uniqueCount - 1; i++) {
            for (int j = i + 1; j < uniqueCount; j++) {
                if (totals[j] > totals[i]) {
                    double tempTotal = totals[i];
                    totals[i] = totals[j];
                    totals[j] = tempTotal;
                    String tempID = uniqueIDs[i];
                    uniqueIDs[i] = uniqueIDs[j];
                    uniqueIDs[j] = tempID;
                }
            }
        }
        System.out.println("\nBest Customers (Descending by Total):");
        for (int i = 0; i < uniqueCount; i++) {
            String name = "";
            for (int j = 0; j < orderCount; j++) {
                if (customerIDs[j].equals(uniqueIDs[i])) {
                    name = customerNames[j];
                    break;
                }
            }
            System.out.println(uniqueIDs[i] + " (" + name + ") - Total: Rs. " + totals[i]);
        }
    }

    public static void searchOrder() {
        if (orderCount == 0) {
            System.out.println("No orders available.");
            return;
        }

        System.out.print("Enter Order ID: ");
        String id = input.nextLine();
        boolean found = false;

        for (int i = 0; i < orderCount; i++) {
            if (orderIDs[i].equals(id)) {
                System.out.println("Order ID: " + orderIDs[i]);
                System.out.println("Customer ID: " + customerIDs[i]);
                System.out.println("Customer Name: " + customerNames[i]);
                System.out.println("Quantity: " + burgerQty[i]);
                System.out.println("Status: " + statusText(orderStatus[i]));
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Invalid Order ID!");
        }
    }

    public static void searchCustomer() {
        if (orderCount == 0) {
            System.out.println("No orders available.");
            return;
        }

        System.out.print("Enter Customer ID: ");
        String cid = input.nextLine();
        boolean found = false;

        for (int i = 0; i < orderCount; i++) {
            if (customerIDs[i].equals(cid)) {
                if (!found) {
                    System.out.println("Orders for Customer ID " + cid + ":");
                }
                System.out.println("Order ID: " + orderIDs[i] + ", Quantity: " + burgerQty[i] + ", Status: " + statusText(orderStatus[i]));
                found = true;
            }
        }

        if (!found) {
            System.out.println("Customer not found!");
        }
    }

    public static void viewOrders() {
        if (orderCount == 0) {
            System.out.println("No orders available.");
            return;
        }

        while (true) {
            System.out.println("\n-------------------------------");
            System.out.println("          VIEW ORDER LIST        ");
            System.out.println("-------------------------------");
            System.out.println("[1] Delivered Order");
            System.out.println("[2] Preparing Order");
            System.out.println("[3] Cancel Order");
            System.out.print("Enter an option to continue > ");
            int opt = input.nextInt();
            input.nextLine();

            int status;
            if (opt == 1) status = DELIVERED;
            else if (opt == 2) status = PREPARING;
            else if (opt == 3) status = CANCEL;
            else {
                System.out.println("Invalid option!");
                return;
            }

            // Table Header
            System.out.println("\n--------------------------------------------------");
            System.out.printf("%-10s %-12s %-12s %-8s %-10s\n", "OrderID", "CustomerID", "Name", "Qty", "OrderValue");
            System.out.println("--------------------------------------------------");

            boolean found = false;
            for (int i = 0; i < orderCount; i++) {
                if (orderStatus[i] == status) {
                    double orderValue = burgerQty[i] * BURGERPRICE;
                    System.out.printf("%-10s %-12s %-12s %-8d %-10.2f\n", orderIDs[i], customerIDs[i], customerNames[i], burgerQty[i], orderValue);
                    found = true;
                }
            }

            if (!found) {
                System.out.println("No orders found for this status.");
            }

            System.out.println("--------------------------------------------------");

            // Ask to stay or go back
            System.out.print("Do you want to stay here? (Y/N): ");
            String choice = input.nextLine().toUpperCase();
            if (!choice.equals("Y")) {
                break;
            }
        }
    }


    public static void updateOrders() {
        if (orderCount == 0) { System.out.println("No orders available."); return; }

        System.out.print("Enter Order ID to update: ");
        String id = input.nextLine();
        int index = -1;
        for (int i = 0; i < orderCount; i++) {
            if (orderIDs[i].equals(id)) { index = i; break; }
        }

        if (index == -1) {
            System.out.println("Invalid Order ID!");
            return;
        }

        if (orderStatus[index] != PREPARING) {
            System.out.println("Cannot update. Order already " + statusText(orderStatus[index]));
            return;
        }

        System.out.println("1. Update Quantity");
        System.out.println("2. Update Status");
        System.out.print("Choose: ");
        int opt = input.nextInt();
        input.nextLine();

        if (opt == 1) {
            System.out.print("Enter new quantity: ");
            int newQty = input.nextInt();
            input.nextLine();
            if (newQty > 0) {
                burgerQty[index] = newQty;
                System.out.println("Quantity updated successfully!");
            } else System.out.println("Invalid quantity!");
        } else if (opt == 2) {
            System.out.println("0. Preparing");
            System.out.println("1. Delivered");
            System.out.println("2. Cancelled");
            System.out.print("Choose status: ");
            int newStatus = input.nextInt();
            input.nextLine();
            if (newStatus >= 0 && newStatus <= 2) {
                orderStatus[index] = newStatus;
                System.out.println("Status updated successfully!");
            } else System.out.println("Invalid status!");
        }
    }

    public static String statusText(int status) {
        if (status == PREPARING) return "PREPARING";
        else if (status == DELIVERED) return "DELIVERED";
        else return "CANCELLED";
    }


    public static void exit() {
        System.out.println("\nYou left the program...");
        System.exit(0);
    }
}
