package store;

import java.util.*;

class Supplier {
    private String name;
    private double priceRating;
    private double qualityRating;

    public Supplier(String name, double priceRating, double qualityRating) {
        this.name = name;
        this.priceRating = priceRating;
        this.qualityRating = qualityRating;
    }

    public String getName() { return name; }

    @Override
    public String toString() {
        return String.format("%s (Price Rating: %.1f | Quality Rating: %.1f)",
                name, priceRating, qualityRating);
    }
}

class Product {
    private String name;
    private double price;
    private int quantity;
    private int reorderLevel;
    private List<Supplier> suppliers;

    public Product(String name, double price, int quantity, int reorderLevel, List<Supplier> suppliers) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.reorderLevel = reorderLevel;
        this.suppliers = suppliers;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public int getReorderLevel() { return reorderLevel; }
    public List<Supplier> getSuppliers() { return suppliers; }

    public void setPrice(double price) { this.price = price; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public boolean isLowStock() {
        return quantity <= reorderLevel;
    }

    public void checkReorderAlert() {
        if (isLowStock()) {
            System.out.println("⚠️  Reorder Alert: " + name + " is below threshold (" + quantity + " left).");
        }
    }

    @Override
    public String toString() {
        return String.format("%-15s Price: ₹%.2f | Quantity: %d | Reorder Level: %d",
                name, price, quantity, reorderLevel);
    }
}

class Customer {
    private String name;
    private String type; // "good" or "normal"
    private List<String> purchaseHistory;
    private double discount;

    public Customer(String name, String type) {
        this.name = name;
        this.type = type;
        this.purchaseHistory = new ArrayList<>();
        this.discount = type.equalsIgnoreCase("good") ? 0.05 : 0.0;
    }

    public String getName() { return name; }
    public String getType() { return type; }

    public void addPurchase(String product) {
        purchaseHistory.add(product);
        if (purchaseHistory.size() >= 3 && discount < 0.1) discount = 0.1;
    }

    public double getDiscount() { return discount; }

    public void displayHistory() {
        System.out.println("Customer: " + name + " (" + type + ")");
        System.out.println("Purchase history: " + purchaseHistory);
        System.out.println("Current discount: " + (discount * 100) + "%");
    }

    @Override
    public String toString() {
        return String.format("%-10s | Type: %-6s | Discount: %.0f%%", name, type, discount * 100);
    }
}

public class Store {
    private static List<Product> inventory = new ArrayList<>();
    private static List<Customer> customers = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        initializeData();
        int choice;
        do {
            System.out.println("\n=== Mr. Rao\'s Store Management ===");
            System.out.println("1. Show Inventory");
            System.out.println("2. Add Product");
            System.out.println("3. Update Product Price");
            System.out.println("4. Check Inventory Alerts & Restock");
            System.out.println("5. Show Customer History");
            System.out.println("6. Customer Shopping / Billing");
            System.out.println("7. Add Customer");
            System.out.println("8. Show All Customers");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 : showInventory();break;
                case 2 : addProduct();break;
                case 3 : updatePrice();break;
                case 4 : checkInventoryAlertsAndRestock();break;
                case 5 : showCustomerHistory();break;
                case 6 : processPurchase();break;
                case 7 : addCustomer();break;
                case 8 : showAllCustomers();break;
                case 0 : System.out.println("Exiting the system. Visit Again!");System.exit(1);
                default : System.out.println("Invalid choice! Try again.");
            }
        } while (choice != 0);
    }

    private static void initializeData() {
        Supplier s1 = new Supplier("Ravi Traders", 9.5, 9.8);
        Supplier s2 = new Supplier("Metro Suppliers", 9.2, 9.6);
        Supplier s3 = new Supplier("FreshMart", 9.7, 9.9);

        inventory.add(new Product("Rice", 45.0, 1000, 250, Arrays.asList(s1, s2)));
        inventory.add(new Product("Oil", 120.0, 100, 20, Arrays.asList(s2, s3)));
        inventory.add(new Product("Soap", 35.0, 80, 10, Arrays.asList(s1, s3)));
        inventory.add(new Product("Shampoo", 60.0, 100, 30, Arrays.asList(s1, s2)));
        inventory.add(new Product("Pulses", 100.0, 2000, 550, Arrays.asList(s1, s2)));
        inventory.add(new Product("Grains", 130.0, 2500, 650, Arrays.asList(s1, s2)));
        inventory.add(new Product("Chips", 25.0, 100, 25, Arrays.asList(s1, s2)));
        inventory.add(new Product("Chocolate", 20.0, 90, 20, Arrays.asList(s1, s2)));

        customers.add(new Customer("Anita", "good"));
        customers.add(new Customer("Kumar", "normal"));
    }

    private static void showInventory() {
        System.out.println("\nInventory List:");
        for (Product p : inventory) {
            System.out.println(p);
            p.checkReorderAlert();
        }
    }

    private static void addProduct() {
        System.out.print("Enter product name: ");
        String name = sc.nextLine();
        System.out.print("Enter price: ");
        double price = sc.nextDouble();
        System.out.print("Enter quantity: ");
        int qty = sc.nextInt();
        System.out.print("Enter reorder level: ");
        int reorder = sc.nextInt();
        sc.nextLine();

        List<Supplier> productSuppliers = new ArrayList<>();
        System.out.print("Enter number of suppliers for this product: ");
        int numSup = sc.nextInt();
        sc.nextLine();
        for (int i = 1; i <= numSup; i++) {
            System.out.print("Enter supplier " + i + " name: ");
            String sName = sc.nextLine();
            productSuppliers.add(new Supplier(sName, 9.0, 9.0));
        }

        inventory.add(new Product(name, price, qty, reorder, productSuppliers));
        System.out.println("Product added successfully!");
    }

    private static void updatePrice() {
        System.out.print("Enter product name to update: ");
        String name = sc.nextLine();
        for (Product p : inventory) {
            if (p.getName().equalsIgnoreCase(name)) {
                System.out.print("Enter new price: ");
                double newPrice = sc.nextDouble();
                p.setPrice(newPrice);
                System.out.println("Price updated successfully!");
                return;
            }
        }
        System.out.println("Product not found!");
    }

    private static void checkInventoryAlertsAndRestock() {
        System.out.println("\nChecking for low-stock alerts...");
        for (Product p : inventory) {
            if (p.isLowStock()) {
                System.out.println("\n" + p.getName() + " is below threshold!");
                System.out.println("Available suppliers:");
                List<Supplier> suppliers = p.getSuppliers();
                for (int i = 0; i < suppliers.size(); i++) {
                    System.out.println((i + 1) + ". " + suppliers.get(i));
                }
                System.out.print("Select a supplier number to reorder (0 to skip): ");
                int choice = sc.nextInt();
                if (choice == 0) continue;
                if (choice < 1 || choice > suppliers.size()) {
                    System.out.println("Invalid choice!");
                    continue;
                }

                Supplier selected = suppliers.get(choice - 1);
                System.out.print("Enter quantity to reorder from " + selected.getName() + ": ");
                int reorderQty = sc.nextInt();
                sc.nextLine();
                p.setQuantity(p.getQuantity() + reorderQty);
                System.out.println("Restocked " + reorderQty + " units of " + p.getName() +
                        " from " + selected.getName() + ".");
            }
        }
    }

    private static void showCustomerHistory() {
        System.out.print("Enter customer name: ");
        String name = sc.nextLine();
        for (Customer c : customers) {
            if (c.getName().equalsIgnoreCase(name)) {
                c.displayHistory();
                return;
            }
        }
        System.out.println("Customer not found!");
    }

    private static void addCustomer() {
        System.out.print("Enter new customer name: ");
        String name = sc.nextLine();

        for (Customer c : customers) {
            if (c.getName().equalsIgnoreCase(name)) {
                System.out.println("Customer already exists!");
                return;
            }
        }

        System.out.print("Is this a good customer? (yes/no): ");
        String typeInput = sc.nextLine();
        String type = typeInput.equalsIgnoreCase("yes") ? "good" : "normal";

        customers.add(new Customer(name, type));
        System.out.println("Customer added successfully!");
    }

    private static void showAllCustomers() {
        System.out.println("\nCustomer List:");
        if (customers.isEmpty()) {
            System.out.println("No customers available.");
            return;
        }
        for (Customer c : customers) {
            System.out.println(c);
        }
    }

    private static void processPurchase() {
        System.out.print("Enter customer name: ");
        String name = sc.nextLine();

        Customer customer = customers.stream()
                .filter(c -> c.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseGet(() -> {
                    System.out.print("Is this a good customer? (yes/no): ");
                    String typeInput = sc.nextLine();
                    String type = typeInput.equalsIgnoreCase("yes") ? "good" : "normal";
                    Customer newC = new Customer(name, type);
                    customers.add(newC);
                    return newC;
                });

        double totalBill = 0.0;
        while (true) {
            System.out.print("Enter product to buy (or 'done' to finish): ");
            String prodName = sc.nextLine();
            if (prodName.equalsIgnoreCase("done")) break;

            Optional<Product> productOpt = inventory.stream()
                    .filter(p -> p.getName().equalsIgnoreCase(prodName))
                    .findFirst();

            if (productOpt.isEmpty()) {
                System.out.println("Product not found!");
                continue;
            }

            Product product = productOpt.get();
            System.out.print("Enter quantity: ");
            int qty = sc.nextInt();
            sc.nextLine();

            if (qty > product.getQuantity()) {
                System.out.println("Not enough stock available!");
                continue;
            }

            double cost = product.getPrice() * qty;
            totalBill += cost;
            product.setQuantity(product.getQuantity() - qty);
            customer.addPurchase(product.getName());
            System.out.println("Added " + qty + " x " + product.getName() + " to bill. Subtotal: ₹" + cost);
        }

        double discount = customer.getDiscount();
        double finalAmount = totalBill * (1 - discount);
        System.out.println("----------------------------------");
        System.out.println("Customer: " + name);
        System.out.println("Total Bill: ₹" + totalBill);
        System.out.println("Discount: " + (discount * 100) + "%");
        System.out.println("Final Amount: ₹" + finalAmount);
        System.out.println("----------------------------------");
        System.out.println("Purchase completed successfully!");
    }
}

