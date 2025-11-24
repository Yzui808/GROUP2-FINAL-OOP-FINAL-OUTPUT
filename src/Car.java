public class Car {


    private double price;
    private String brand;
    private String color;
    private String dimensionsWeight;
    private String engine;
    private String transmission;
    private String friction;
    private String chassis;
    private String safetySecurity;
    private boolean isSold;

    /**
     * Constructor to initialize a Car object.
     */
    public Car(double price, String brand, String color, String dimensionsWeight,
               String engine, String transmission, String friction,
               String chassis, String safetySecurity) {
        this.price = price;
        this.brand = brand;
        this.color = color;
        this.dimensionsWeight = dimensionsWeight;
        this.engine = engine;
        this.transmission = transmission;
        this.friction = friction;
        this.chassis = chassis;
        this.safetySecurity = safetySecurity;
        this.isSold = false;
    }

    // Getters
    public double getPrice() { return price; }
    public String getBrand() { return brand; }
    public String getColor() { return color; }
    public boolean isSold() { return isSold; }

    public void markAsSold() {
        this.isSold = true;
    }

    public void processRefund() {
        this.isSold = true;
    }

    public void displayDetails() {
        System.out.println("------------------------------------------");
        System.out.printf("  Brand: %s | Color: %s | Price: $%,.2f\n", brand, color, price);
        System.out.println("------------------------------------------");
        System.out.printf("  1. Dimensions & Weight: %s\n", dimensionsWeight);
        System.out.printf("  2. Engine: %s\n", engine);
        System.out.printf("  3. Transmission: %s\n", transmission);
        System.out.printf("  4. Friction (Brakes/Tires): %s\n", friction);
        System.out.printf("  5. Chassis: %s\n", chassis);
        System.out.printf("  6. Safety and Security: %s\n", safetySecurity);
        System.out.printf("  Status: %s\n", isSold ? "SOLD" : "Available");
        System.out.println("------------------------------------------");
    }
}