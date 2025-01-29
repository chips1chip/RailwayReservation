
import java.util.Scanner;

public class RailwayRS {

    static Scanner sc = new Scanner(System.in);
    static String[] trains = {"Shatabdi Express", "Rajdhani Express", "Duronto Express", "Tejas Express", "Jan Shatabdi"};
    static int[] availableSeats = {100, 100, 100, 100, 100}; // Initial available seats for each train
    static String[] bookedTrain = new String[100]; // Array to store booked train names
    static int[] bookedSeats = new int[100]; // Array to store booked seat numbers
    static int bookingCount = 0; // Counter for the bookings
    static String storedUsername = "admin"; // Default username
    static String storedPassword = "password"; // Default password
    static String storedEmail = "admin@example.com"; // Default email

    // Method to display the menu
    public static void showMenu() {
        System.out.println("\n*********** Train Booking System ***********");
        System.out.println("1. Book a Ticket");
        System.out.println("2. Cancel a Ticket");
        System.out.println("3. View Booked Tickets");
        System.out.println("4. View Bill");
        System.out.println("5. Log Out");
        System.out.print("Please choose an option: ");
    }

    // Method to sign up (register)
    public static void signUp() {
        System.out.println("\n***** Sign Up *****");
        System.out.print("Enter a username: ");
        storedUsername = sc.nextLine();
        System.out.print("Enter a password: ");
        storedPassword = sc.nextLine();
        System.out.print("Enter your email: ");
        storedEmail = sc.nextLine();
        System.out.println("Sign-up successful! Now, you can log in with your credentials.");
    }

    // Method to login
    public static boolean login() {
        System.out.println("\n***** Login *****");
        System.out.print("Enter your username: ");
        String username = sc.nextLine();
        System.out.print("Enter your password: ");
        String password = sc.nextLine();

        if (username.equals(storedUsername) && password.equals(storedPassword)) {
            System.out.println("Login successful!");
            return true;
        } else {
            System.out.println("Invalid username or password. Please try again.");
            return false;
        }
    }

    // Method for forgot password
    public static void forgotPassword() {
        System.out.println("\n***** Forgot Password *****");
        System.out.print("Enter your email: ");
        String email = sc.nextLine();

        if (email.equals(storedEmail)) {
            System.out.print("Enter a new password: ");
            String newPassword = sc.nextLine();
            storedPassword = newPassword;
            System.out.println("Password reset successful! You can now log in with your new password.");
        } else {
            System.out.println("Invalid email. Please try again.");
        }
    }

    // Method to book a ticket
    public static void bookTicket(String username) {
        System.out.println("Available Trains:");
        for (int i = 0; i < trains.length; i++) {
            System.out.println((i + 1) + ". " + trains[i]);
        }

        System.out.print("Enter the number corresponding to the train you want to book: ");
        int trainChoice = sc.nextInt() - 1; // User selects train by number

        if (trainChoice < 0 || trainChoice >= trains.length) {
            System.out.println("Invalid train number. Please try again.");
            return;
        }

        System.out.println("You selected: " + trains[trainChoice]);
        System.out.println("Available seats: " + availableSeats[trainChoice]);

        int seatsToBook = 0;
        while (seatsToBook <= 0) {
            System.out.print("Enter the number of seats you want to book: ");
            if (sc.hasNextInt()) {
                seatsToBook = sc.nextInt();
                if (seatsToBook <= 0) {
                    System.out.println("You cannot book 0 or negative seats. Please enter a valid number.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                sc.next(); // Consume the invalid input
            }
        }

        if (seatsToBook > availableSeats[trainChoice]) {
            System.out.println("Not enough seats available. Please try again.");
            return;
        }

        // Book the seats
        availableSeats[trainChoice] -= seatsToBook;

        for (int i = 0; i < seatsToBook; i++) {
            bookedTrain[bookingCount] = trains[trainChoice];
            bookedSeats[bookingCount] = i + 1; // Seat numbers for this train (starting from 1)
            bookingCount++;
        }

        System.out.println("You have successfully booked " + seatsToBook + " seats for " + trains[trainChoice]);
    }

    // Method to cancel a ticket
    public static void cancelTicket(String username) {
        System.out.println("Your booked trains:");
        boolean found = false;
        for (int i = 0; i < bookingCount; i++) {
            if (bookedTrain[i] != null) {
                System.out.println((i + 1) + ". " + bookedTrain[i] + " - Seat: " + bookedSeats[i]);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No bookings found to cancel.");
            return;
        }

        System.out.print("Enter the number corresponding to the booking you want to cancel: ");
        int cancelChoice = sc.nextInt() - 1;

        if (cancelChoice < 0 || cancelChoice >= bookingCount || bookedTrain[cancelChoice] == null) {
            System.out.println("Invalid choice. Please try again.");
            return;
        }

        // Cancel the booking
        String cancelledTrain = bookedTrain[cancelChoice];
        bookedTrain[cancelChoice] = null; // Remove the booking record
        bookedSeats[cancelChoice] = 0;  // Remove the seat number

        // Increase the available seat count for the cancelled train
        for (int i = 0; i < trains.length; i++) {
            if (cancelledTrain.equals(trains[i])) {
                availableSeats[i]++;
                break;
            }
        }

        System.out.println("Booking for " + cancelledTrain + " has been cancelled.");
    }

    // Method to view booking history
    public static void viewHistory(String username) {
        System.out.println("Booked Tickets for " + username + ":");
        boolean found = false;
        for (int i = 0; i < bookingCount; i++) {
            if (bookedTrain[i] != null) {
                System.out.println("Train: " + bookedTrain[i] + " | Seat No.: " + bookedSeats[i]);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No bookings found.");
        }
    }

    // Method to check the bill
    public static void checkBill(String username) {
        int totalSeats = 0;
        int[] seatCountPerTrain = new int[trains.length];  // Array to keep track of how many seats are booked for each train

        System.out.println("Bill for user: " + username);
        System.out.println("--------------------------------------------");
        System.out.println("Train Name\tSeats Booked\tPrice");

        // Count how many seats have been booked for each train
        for (int i = 0; i < bookingCount; i++) {
            if (bookedTrain[i] != null) {
                // Find the index of the train in the trains array
                for (int j = 0; j < trains.length; j++) {
                    if (bookedTrain[i].equals(trains[j])) {
                        seatCountPerTrain[j]++;
                        break;  // Exit the loop once we find the correct train
                    }
                }
            }
        }

        // Now display the bill with the number of seats booked per train
        for (int i = 0; i < trains.length; i++) {
            if (seatCountPerTrain[i] > 0) {
                System.out.println(trains[i] + "\t\t" + seatCountPerTrain[i] + "\t\tRs. " + (seatCountPerTrain[i] * 500));
                totalSeats += seatCountPerTrain[i];
            }
        }

        System.out.println("--------------------------------------------");
        System.out.println("Total seats booked: " + totalSeats);
        System.out.println("Your total bill: Rs. " + (totalSeats * 500));  // 500 is the price per seat
        System.out.println("--------------------------------------------");
    }

    // Main method
    public static void main(String[] args) {
        // Welcome Page
        System.out.println("********** Welcome to Train Booking System **********");

        boolean exitProgram = false;

        while (!exitProgram) {
            System.out.println("\n1. Sign Up");
            System.out.println("2. Login");
            System.out.println("3. Forgot Password");
            System.out.println("4. Exit");
            System.out.print("Please choose an option: ");
            int initialChoice = sc.nextInt();
            sc.nextLine();  // Consume newline character

            if (initialChoice == 1) {
                signUp();
            } else if (initialChoice == 2) {
                boolean isLoggedIn = false;
                while (!isLoggedIn) {
                    isLoggedIn = login();
                }

                // Show menu after successful login
                boolean exit = false;
                while (!exit) {
                    showMenu();
                    int choice = sc.nextInt();
                    sc.nextLine();  // Consume newline character

                    switch (choice) {
                        case 1:
                            bookTicket(storedUsername);
                            break;
                        case 2:
                            cancelTicket(storedUsername);
                            break;
                        case 3:
                            viewHistory(storedUsername);
                            break;
                        case 4:
                            checkBill(storedUsername);
                            break;
                        case 5:
                            System.out.println("Logging out...");
                            exit = true;
                            break;
                        default:
                            System.out.println("Invalid option. Please try again.");
                            break;
                    }
                }
            } else if (initialChoice == 3) {
                forgotPassword();
            } else if (initialChoice == 4) {
                System.out.print("Are you sure you want to exit? (y/n): ");
                char exitChoice = sc.next().charAt(0);
                if (exitChoice == 'y' || exitChoice == 'Y') {
                    exitProgram = true;
                    System.out.println("Goodbye! Have a great day!");
                }
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
