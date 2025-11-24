import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
public class DealershipSimulation {


    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z\\s]+$");
    private static final Pattern DIGITS_ONLY_PATTERN = Pattern.compile("^\\d+$");
    private static final Pattern ID_LICENSE_PATTERN = Pattern.compile("^[a-zA-Z0-9\\s-/]+$");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Car> availableCars = createCarInventory();

        List<Car> purchasedCars = new ArrayList<>();
        Buyer currentBuyer = null;
        boolean running = true;

        System.out.println("==================================================");
        System.out.println("            WELCOME TO THE DEALERSHIP");
        System.out.println("==================================================");

        while (running) {

            int carsBought = purchasedCars.size();
            int choice = showTopLevelMenu(scanner, carsBought);

            switch (choice) {
                case 1:
                    Car selectedCar = showCarSelectionAndDetails(scanner, availableCars);

                    if (selectedCar != null) {

                        if (currentBuyer == null) {
                            System.out.println("\n--- Proceeding to Initial Buyer Registration ---");
                            Buyer newBuyer = getBuyerInformation(scanner);
                            if (newBuyer != null) {
                                currentBuyer = newBuyer;
                            } else {
                                System.out.println("Buyer registration incomplete. Sale aborted.");
                                break;
                            }
                        }

                        selectedCar.markAsSold();
                        purchasedCars.add(selectedCar);

                        System.out.println("\n==================================================");
                        System.out.println("     SALE SUCCESSFUL! Car added to your record.");
                        System.out.printf("     %s %s sold to %s. Total Cars Purchased: %d\n",
                                selectedCar.getColor(), selectedCar.getBrand(),
                                currentBuyer.getName(), purchasedCars.size());
                        System.out.println("==================================================");
                    }
                    break;

                case 2:
                    viewBoughtCarDetails(purchasedCars, currentBuyer);
                    break;

                case 3:
                    viewBuyerRegistration(currentBuyer, purchasedCars);
                    break;

                case 4:
                    running = false;
                    break;

                case 5:
                    processRefundOption(scanner, purchasedCars);
                    if (purchasedCars.isEmpty()) {
                        currentBuyer = null;
                        System.out.println("All purchases refunded. Buyer record reset.");
                    }
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a number from 1 to 4.");
            }
        }
        endProgram(scanner);
    }
    private static int showTopLevelMenu(Scanner scanner, int carsBought) {
        System.out.println("\n==================================================");
        System.out.println("                 DEALERSHIP MAIN MENU");
        System.out.println("==================================================");
        System.out.println("1. View Available Car Inventory (Buy Car)");
        System.out.printf("2. View Your Bought Car Details (Total Purchased: %d)\n", carsBought);

        System.out.println("3. View Your Registration for Buy a Car (Buyer Info)");
        System.out.println("4. Exit Program");

        if (carsBought > 0) {
            System.out.println("5.Process Refund Simulation");
        }

        System.out.print("Enter choice (1-4): ");
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    private static void viewBoughtCarDetails(List<Car> purchasedCars, Buyer buyer) {
        System.out.println("\n==================================================");
        System.out.println("        YOUR PURCHASED VEHICLE DETAILS");
        System.out.println("==================================================");

        if (purchasedCars.isEmpty() || buyer == null) {
            System.out.println("You have not purchased any car yet. Please select option 1 to buy.");
        } else {
            System.out.printf("Registered Buyer: %s\n", buyer.getName());
            System.out.println("--- Listing " + purchasedCars.size() + " Car(s) ---");

            for (int i = 0; i < purchasedCars.size(); i++) {
                Car car = purchasedCars.get(i);
                System.out.printf("\n--- Car %d: %s %s ---\n",
                        i + 1, car.getColor(), car.getBrand());
                car.displayDetails();
            }
        }
        System.out.println("--------------------------------------------------\n");
    }

    private static void viewBuyerRegistration(Buyer currentBuyer, List<Car> purchasedCars) {
        System.out.println("\n==================================================");
        System.out.println("        YOUR REGISTRATION DETAILS");
        System.out.println("==================================================");

        if (currentBuyer != null) {
            currentBuyer.displayBuyerInfo();

            System.out.println("--- VEHICLES REGISTERED UNDER THIS BUYER ---");
            if (purchasedCars.isEmpty()) {
                System.out.println("No cars are currently registered.");
            } else {
                for (int i = 0; i < purchasedCars.size(); i++) {
                    Car car = purchasedCars.get(i);
                    System.out.printf("%d. %s %s (Price: $%,.2f)\n",
                            i + 1, car.getColor(), car.getBrand(), car.getPrice());
                }
            }
        } else {
            System.out.println("No registration details found. Please buy a car first.");
        }
        System.out.println("--------------------------------------------------\n");
    }

    private static Car showCarSelectionAndDetails(Scanner scanner, List<Car> cars) {
        Car finalSelection = null;
        boolean browsing = true;

        while (browsing) {
            System.out.println("\n==================================================");
            System.out.println("            CAR SHOWCASE MENU");
            System.out.println("==================================================");
            System.out.println("Choose a car number to view its full technical specs (clarification).");

            // Show brief list
            for (int i = 0; i < cars.size(); i++) {
                Car c = cars.get(i);
                System.out.printf("%d. %s %s - $%,.2f (%s)\n",
                        i + 1, c.getColor(), c.getBrand(), c.getPrice(),
                        c.isSold() ? "SOLD" : "Available");
            }
            System.out.println("0. Return to Main Menu.");
            System.out.print("Enter choice (0-6): ");

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());

                if (choice == 0) {
                    browsing = false;
                    continue;
                }

                if (choice >= 1 && choice <= cars.size()) {
                    Car selected = cars.get(choice - 1);

                    if (selected.isSold()) {
                        System.out.println("\nERROR: That car is already sold. Please choose another.");
                        continue;
                    }

                    System.out.printf("\n--- FULL SPECIFICATIONS FOR CAR %d: %s %s ---\n",
                            choice, selected.getColor(), selected.getBrand());
                    selected.displayDetails();
                    String confirm = "";
                    while (!confirm.equals("Y") && !confirm.equals("N")) {
                        System.out.println("--- CLARIFICATION COMPLETE ---");
                        System.out.printf("Do you CONFIRM the purchase of the %s %s? (Y/N): ",
                                selected.getBrand(), selected.getColor());
                        confirm = scanner.nextLine().trim().toUpperCase();

                        if (confirm.equals("Y")) {
                            finalSelection = selected;
                            browsing = false;
                        } else if (confirm.equals("N")) {
                            System.out.println("Purchase cancelled for this model. Returning to Car Showcase Menu...");
                            break;
                        } else {
                            System.out.println("Invalid input. Please enter Y for Yes or N for No.");
                        }
                    }
                } else {
                    System.out.println("Invalid choice. Please enter a number between 0 and 6.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
        return finalSelection;
    }

    private static List<Car> createCarInventory() {
        List<Car> cars = new ArrayList<>();
        cars.add(new Car(18500.00, "Toyota Yaris", "Silver",
                "L: 4m, W: 1.7m, H: 1.5m | Weight: 1050kg",
                "1.5L Inline-4, 106hp", "CVT Automatic",
                "Front Disc, Rear Drum Brakes", "Monocoque, Front-Wheel Drive",
                "ABS, 6 Airbags, Backup Camera"));

        cars.add(new Car(45999.00, "BMW 3 Series", "Midnight Blue",
                "L: 4.7m, W: 1.8m, H: 1.4m | Weight: 1550kg",
                "2.0L Turbo Inline-4, 255hp", "8-Speed Automatic",
                "All-Wheel Disc Brakes, Performance Tires", "Unitized Body, Rear-Wheel Drive",
                "Dynamic Stability Control, Parking Assist, Active Guard"));

        cars.add(new Car(38750.00, "Tesla Model Y", "Red Multi-Coat",
                "L: 4.75m, W: 1.9m, H: 1.6m | Weight: 1900kg",
                "Dual Motor All-Wheel Drive, 384hp", "Single Speed Direct Drive",
                "Regenerative Braking, Low Rolling Resistance Tires", "Skateboard Platform, All-Wheel Drive",
                "Autopilot, 360-degree Cameras, Sentry Mode"));

        cars.add(new Car(62000.00, "Ford F-150", "Black",
                "L: 5.9m, W: 2.0m, H: 1.9m | Weight: 2300kg",
                "3.5L Twin-Turbo V6, 400hp", "10-Speed Automatic",
                "Heavy-Duty Ventilated Disc Brakes, All-Terrain Tires", "Body-on-Frame, Four-Wheel Drive",
                "Trailer Backup Assist, Pro-Trailer Hitch Assist, SecuriLock"));

        cars.add(new Car(89999.00, "Mercedes-Benz GLE", "White",
                "L: 4.9m, W: 2.0m, H: 1.8m | Weight: 2150kg",
                "3.0L Turbo Inline-6, 362hp", "9G-TRONIC Automatic",
                "Performance Ventilated Disc Brakes, Air Suspension", "Unitized Body, 4MATIC All-Wheel Drive",
                "Active Distance Assist, PRE-SAFE, Evasive Steering Assist"));

        cars.add(new Car(55000.00, "Dodge Challenger", "Orange",
                "L: 5.0m, W: 1.9m, H: 1.45m | Weight: 1800kg",
                "6.4L HEMI V8, 485hp", "6-Speed Manual",
                "Brembo 6-Piston Brakes, High-Performance Tires", "Monocoque, Rear-Wheel Drive",
                "High-Strength Steel Cage, Electronic Stability Control"));

        return cars;
    }
    private static Buyer getBuyerInformation(Scanner scanner) {
        System.out.println("\n--- BUYER INFORMATION FORM ENTRY (FIRST TIME ONLY) ---");
        String name = getValidatedInput(scanner, "1. Enter Name: ", NAME_PATTERN, "Name must contain only letters and spaces.");
        String contactNumber = getValidatedInput(scanner, "2. Enter Contact Number: ", DIGITS_ONLY_PATTERN, "Contact number must contain digits only (no symbols).");
        String validId = getValidatedInput(scanner, "3. Enter Valid ID: ", ID_LICENSE_PATTERN, "ID/Passport number contains invalid characters.");
        System.out.print("4. Enter Address: ");
        String address = scanner.nextLine();
        String license = getValidatedInput(scanner, "5. Enter Driver's License Number: ", ID_LICENSE_PATTERN, "Driver's license contains invalid characters.");
        String salaryInput = getValidatedInput(scanner, "6. Enter Monthly Salary: $", DIGITS_ONLY_PATTERN, "Salary must contain digits only.");
        double monthlySalary = Double.parseDouble(salaryInput);

        Buyer buyer = new Buyer(name, contactNumber, validId, address, license, monthlySalary);

        boolean confirmed = false;
        while (!confirmed) {
            System.out.println("\n--- REVIEW AND CONFIRMATION ---");
            buyer.displayBuyerInfo();

            System.out.print("Are these details correct? (Y/N): ");
            String confirm = scanner.nextLine().trim().toUpperCase();

            if (confirm.equals("Y")) {
                confirmed = true;
            } else if (confirm.equals("N")) {
                buyer = handleCorrection(scanner, buyer);
            } else {
                System.out.println("Invalid input. Please enter Y for Yes or N for No.");
            }
        }

        return buyer;
    }
    private static Buyer handleCorrection(Scanner scanner, Buyer buyer) {
        System.out.println("\n--- CORRECTION MENU ---");
        System.out.println("Enter the number of the field you wish to correct (1-6):");
        System.out.print("Correction Choice (1-6): ");

        try {
            int correctionChoice = Integer.parseInt(scanner.nextLine().trim());

            String newInput = "";
            switch (correctionChoice) {
                case 1: // Name
                    newInput = getValidatedInput(scanner, "1. Re-enter Name: ", NAME_PATTERN, "Name must contain only letters and spaces.");
                    buyer.setName(newInput);
                    break;
                case 2: // Contact Number
                    newInput = getValidatedInput(scanner, "2. Re-enter Contact Number: ", DIGITS_ONLY_PATTERN, "Contact number must contain digits only (no symbols).");
                    buyer.setContactNumber(newInput);
                    break;
                case 3: // Valid ID
                    newInput = getValidatedInput(scanner, "3. Re-enter Valid ID: ", ID_LICENSE_PATTERN, "ID/Passport number contains invalid characters.");
                    buyer.setValidId(newInput);
                    break;
                case 4: // Address
                    System.out.print("4. Re-enter Address: ");
                    newInput = scanner.nextLine();
                    buyer.setAddress(newInput);
                    break;
                case 5: // Driver's License
                    newInput = getValidatedInput(scanner, "5. Re-enter Driver's License Number: ", ID_LICENSE_PATTERN, "Driver's license contains invalid characters.");
                    buyer.setLicense(newInput);
                    break;
                case 6: // Monthly Salary
                    String salaryInput = getValidatedInput(scanner, "6. Re-enter Monthly Salary: $", DIGITS_ONLY_PATTERN, "Salary must contain digits only.");
                    double newSalary = Double.parseDouble(salaryInput);
                    buyer.setMonthlySalary(newSalary);
                    break;
                default:
                    System.out.println("Invalid choice. Returning to confirmation screen.");
            }
            System.out.println("\nField " + correctionChoice + " successfully updated. Please confirm all details now.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number from the menu.");
        }

        return buyer;
    }
    private static String getValidatedInput(Scanner scanner, String prompt, Pattern pattern, String errorMessage) {
        String input = "";
        boolean isValid = false;
        while (!isValid) {
            System.out.print(prompt);
            input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please re-enter.");
            } else if (pattern.matcher(input).matches()) {
                isValid = true;
            } else {
                System.out.println("ERROR: " + errorMessage);
            }
        }
        return input;
    }

    private static void processRefundOption(Scanner scanner, List<Car> purchasedCars) {
        if (purchasedCars.isEmpty()) {
            System.out.println("\nNOTICE: There are no cars purchased to process a refund.");
            return;
        }

        System.out.println("\n--- REFUND PROCESS SIMULATION ---");
        System.out.println("Select the car number you wish to refund:");

        for (int i = 0; i < purchasedCars.size(); i++) {
            Car car = purchasedCars.get(i);
            System.out.printf("%d. %s %s - $%,.2f\n", i + 1, car.getColor(), car.getBrand(), car.getPrice());
        }
        System.out.print("Enter car number for refund (or 0 to cancel): ");

        try {
            int refundChoice = Integer.parseInt(scanner.nextLine().trim());

            if (refundChoice == 0) {
                System.out.println("Refund cancelled.");
                return;
            }

            if (refundChoice >= 1 && refundChoice <= purchasedCars.size()) {
                Car carToRefund = purchasedCars.get(refundChoice - 1);

                System.out.print("Confirm refund for the " + carToRefund.getBrand() + "? (yes/no): ");
                String confirm = scanner.nextLine().trim().toLowerCase();

                if (confirm.equals("yes")) {
                    carToRefund.processRefund();
                    purchasedCars.remove(carToRefund);

                    System.out.printf("\nREFUND PROCESSED: The sale of the %s %s has been reversed.\n",
                            carToRefund.getColor(), carToRefund.getBrand());
                    System.out.println("The car is now back in the available inventory.");
                } else {
                    System.out.println("Refund confirmation denied. Sale remains valid.");
                }
            } else {
                System.out.println("Invalid car number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }

    private static void endProgram(Scanner scanner) {
        System.out.println("\n==================================================");
        System.out.println("                 END NA");
        System.out.println("==================================================");
        if (scanner != null) {
            scanner.close();
        }
    }
}

/*BARTINA, JADE
KENNNETH ANDRES
JHON CARLO RIVERA
MARCELINO BANDOL
ARCA,NIEL AARON
DIMAL CLERICK JOHN
MALANAO CESAR JAY M.*/

