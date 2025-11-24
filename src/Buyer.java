public class Buyer {

    private String name;
    private String contactNumber;
    private String validId;
    private String address;
    private String license;
    private double monthlySalary;
    public Buyer(String name, String contactNumber, String validId,
                 String address, String license, double monthlySalary) {
        this.name = name;
        this.contactNumber = contactNumber;
        this.validId = validId;
        this.address = address;
        this.license = license;
        this.monthlySalary = monthlySalary;
    }
    public String getName() {
        return name;

    }
    public void displayBuyerInfo() {
        System.out.println("\n--- Buyer Information ---");
        System.out.printf("Name: %s\n", name);
        System.out.printf("Contact: %s\n", contactNumber);
        System.out.printf("Address: %s\n", address);
        System.out.printf("Monthly Salary: $%,.2f\n", monthlySalary);
        System.out.println("ID/License details are secured.");
        System.out.println("---------------------------\n");

    }

    public void setName(String newInput) {
    }
    public void setContactNumber(String newInput) {
    }

    public void setValidId(String newInput) {
    }
    public void setAddress(String newInput) {
    }
    public void setLicense(String newInput) {
    }
    public void setNewInput(String newInput) {
    }
    public void setMonthlySalary(String newInput) {
    }

    public void setMonthlySalary(double newSalary) {
    }
}