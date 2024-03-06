package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

class FlightNumber{
    User user;
    public FlightNumber(User user){
        this.user = user;
    }

    public String makeFlightNumber(){
        String firstTwoLetters = user.getName().substring(0,2).toLowerCase();
        String lastTwoNumbers = user.getSsn().substring(7,9);
        String randomNumber = Integer.toString(randomNumber());
        return firstTwoLetters + randomNumber + lastTwoNumbers;
    }
    public int randomNumber(){
        Random random = new Random();
        return random.nextInt(9000000) + 1000000;
    }
}

class User{
    private String name;
    private String ssn;
    private int row;
    private int seat;
    private double foodcost = 0;
    public User(String name, String ssn, int row, int seat){
        this.name = name;
        this. ssn = ssn;
        this.row = row;
        this.seat = seat;
    }

    public String getName() {
        return name;
    }
    public String getSsn() {
        return ssn;
    }

    public int getRow() {
        return row;
    }

    public int getSeat() {
        return seat;
    }

    public void setCost(double cost) {
        this.foodcost += cost;
    }

    public double getCost() {
        return foodcost;
    }
}

class Seats{
    private final int ROWS = 33;
    private final int SEATSPERROW = 6;
    private boolean seats[][];
    public Seats(){
        this.seats = new boolean[ROWS][SEATSPERROW];
        initializeSeats();
    }
    public void initializeSeats(){
        for (int i = 0; i < ROWS; i++){
            for (int j = 0; j < SEATSPERROW; j++){
                seats[i][j] = false;
            }
        }
    }
    public boolean pickSeats(int row, int seatInRow){
        if(!seats[row][seatInRow]){
            seats[row][seatInRow] = true;
            return true;
        }
        return false;
    }
}

public class Booking {
    Scanner scanner = new Scanner(System.in);
    private List<User> users = new ArrayList<>();
    Seats seats = new Seats();
    public String nameHandler(){
        System.out.println("Name: ");
        String name = scanner.next();
        scanner.nextLine();
        return name;
    }
    public String ssnHandler(){
        System.out.println("SSN: ");
        String ssn = scanner.next();
        scanner.nextLine();
        return ssn;
    }

    public int rowHandler(){
        System.out.println("Pick Row: ");
        int row = scanner.nextInt()-1;
        scanner.nextLine();
        return row;
    }
    public int seatHandler(){
        System.out.println("Pick seats within the rowm 1-3 or 4-6: ");
        int seat = scanner.nextInt()-1;
        scanner.nextLine();
        return seat;
    }
    public void orderFood(User user){
        System.out.println("Would you like to pre order some food for the plain? (yes/no)");
        String response = scanner.next();
        scanner.nextLine();
        if(response.equalsIgnoreCase("yes")){
            System.out.println("1. Sandwich - $5.00\n2. Salad - $7.00\n3. Soda - $2.00");
            System.out.println("Please enter the number of the item you'd like to order:");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice){
                case 1:
                    user.setCost(5.00);
                    break;
                case 2:
                    user.setCost(7.00);
                    break;
                case 3:
                    user.setCost(2.00);
                    break;
                default:
                    System.out.println("invalid, no food ordered");
                    break;
            }
        }
    }
    public static void main(String[] args) {
        Booking booking = new Booking();
        boolean moreUsers = true;
        boolean seelist = true;
        while(moreUsers){
            boolean seatPickedSuccessfully = false;
            while (!seatPickedSuccessfully) {
                User user = new User(booking.nameHandler(), booking.ssnHandler(), booking.rowHandler(), booking.seatHandler());
                if (booking.seats.pickSeats(user.getRow(), user.getSeat())) {
                    FlightNumber flightNumber = new FlightNumber(user);
                    System.out.println("You are in row : " + (user.getRow() + 1) + " seat " + (user.getSeat() + 1));
                    System.out.println("Your flightnumber is : " + flightNumber.makeFlightNumber());
                    booking.users.add(user);
                    seatPickedSuccessfully = true;
                    booking.orderFood(user);
                } else {
                    System.out.println("This seat is taken, pick another one");
                }
            }
            System.out.println("Do you want to book more flights? (yes/no)");
            moreUsers = booking.scanner.next().equalsIgnoreCase("yes");

        }
        System.out.println("do you want to see the list of every people in the flight? (yes/no)");
        if(seelist == booking.scanner.next().equalsIgnoreCase("yes")) {
            for (User user : booking.users) {
                System.out.println("--------------------------------------------------");
                System.out.println("Name: " + user.getName());
                System.out.println("SSN: " + user.getSsn());
                System.out.println("Row: " + (user.getRow() + 1) + ", Seat: " + (user.getSeat() + 1));
                System.out.printf("Food Cost: $%.2f\n", user.getCost());
                System.out.println("--------------------------------------------------\n");
            }
        }
        System.out.println("Thank you for booking flights with us");
    }
}