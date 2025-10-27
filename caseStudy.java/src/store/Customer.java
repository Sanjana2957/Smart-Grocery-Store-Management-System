package store;

import java.util.ArrayList;
import java.util.List;

class Customer {
    private String name;
    private List<String> purchaseHistory;
    private double discount;

    public Customer(String name) {
        this.name = name;
        this.purchaseHistory = new ArrayList<>();
        this.discount = 0.0;
    }

    public String getName() { return name; }

    public void addPurchase(String product) {
        purchaseHistory.add(product);
        if (purchaseHistory.size() >= 3) discount = 0.1;
    }

    public double getDiscount() { return discount; }

    public void displayHistory() {
        System.out.println("Purchase history for " + name + ": " + purchaseHistory);
    }
}