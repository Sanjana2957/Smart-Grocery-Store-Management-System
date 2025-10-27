package store;

import java.util.List;

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