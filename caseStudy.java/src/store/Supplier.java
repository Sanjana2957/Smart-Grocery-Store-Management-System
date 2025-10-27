package store;

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
