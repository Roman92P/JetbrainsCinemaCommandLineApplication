package cinema;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Cinema {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        int rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seats = scanner.nextInt();
        System.out.println();
        String[][] cinemaSeats = createCinemaSeats2DArray(rows, seats);

        while (true) {
            System.out.println("1. Show the seats");
            System.out.println("2. Buy a ticket");
            System.out.println("3. Statistics");
            System.out.println("0. Exit");
            int action = scanner.nextInt();
            if (action == 1) {
                System.out.println("Cinema:");
                print2DArray(cinemaSeats);
                System.out.println();
            } else if (action == 2) {
                buyTicket(scanner, rows, seats, cinemaSeats);
                System.out.println();
            } else if (action == 3) {
                printCinemaStats(cinemaSeats);
                System.out.println();
            } else if (action == 0) {
                break;
            }
        }
    }

    private static void printCinemaStats(String[][] cinemaSeats) {
        int sold = 0;
        double percSold = 0.00;
        int curIncome = 0;
        int totIncome = getTotalIncome(cinemaSeats);

        int allSeats = (cinemaSeats.length - 1) * (cinemaSeats[1].length - 1);

        int halfPoint = (cinemaSeats.length - 1) / 2;

        for (int i = 1; i < cinemaSeats.length; i++) {
            for (int j = 1; j < cinemaSeats[i].length; j++) {
                String seat = cinemaSeats[i][j];
                if (seat.equals("B ")) {
                    sold++;
                    curIncome = curIncome + (i <= halfPoint ? 10 : 8);
                }
            }
        }
        DecimalFormat df = new DecimalFormat("0.00");
        percSold = ((float)sold / allSeats) * 100.00;
        String format = df.format(percSold);

        System.out.printf("Number of purchased tickets: %d", sold);
        System.out.println();
        System.out.printf("Percentage: %s%%", format);
        System.out.println();
        System.out.printf("Current income: $%d", curIncome);
        System.out.println();
        System.out.printf("Total income: $%d", totIncome);
    }

    private static int getTotalIncome(String[][] cinemaSeats) {
        int total = 0;
        int allSeats = (cinemaSeats.length - 1) * (cinemaSeats[1].length - 1);
        int rows = cinemaSeats.length - 1;
        int seats = cinemaSeats[1].length - 1;
        if (allSeats <= 60) {
            total = 10 * allSeats;
        } else {
            int firstHalfSeats = (rows / 2) * seats;
            int secondHalfSeats = (rows - (rows / 2)) * seats;
            total = (firstHalfSeats * 10) + (secondHalfSeats * 8);

        }
        return total;
    }

    private static void buyTicket(Scanner scanner, int rows, int seats, String[][] cinemaSeats) {
        int rowNumber;
        int seatNumInThatRow;
        while (true) {
            System.out.println("Enter a row number:");
            rowNumber = scanner.nextInt();
            System.out.println("Enter a seat number in that row:");
            seatNumInThatRow = scanner.nextInt();
            System.out.println();
            try {
                cinemaSeats[rowNumber][seatNumInThatRow].equals("B ");
            }
            catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Wrong input!");
                continue;
            }
            if (cinemaSeats[rowNumber][seatNumInThatRow].equals("B ")) {
                System.out.println("That ticket has already been purchased");
            } else {
                break;
            }
        }
        cinemaSeats[rowNumber][seatNumInThatRow] = "B ";
        int ticketPrice = getTicketPrice(rows, seats, rowNumber, seatNumInThatRow);
        System.out.print("Ticket price: ");
        System.out.printf("$%d%n", ticketPrice);
    }

    private static int getTicketPrice(int rows, int seats, int rowNumber, int seatNumInThatRow) {
        int ticketPrice = 0;
        int allSeats = rows * seats;
        if (allSeats <= 60) {
            ticketPrice = 10;
        } else {
            int firstHalfSeats = rows / 2 * seats;
            int secondHalfSeats = (rows - (rows / 2)) * seats;
            int seatNumber = ((rowNumber - 1) * seats) + seatNumInThatRow;
            ticketPrice = seatNumber <= firstHalfSeats ? 10 : 8;
        }
        return ticketPrice;
    }

    private static String[][] createCinemaSeats2DArray(int rows, int seats) {
        String[][] cinemaSeats = new String[rows + 1][seats + 1];
        for (int i = 0; i < cinemaSeats.length; i++) {
            for (int j = 0; j < cinemaSeats[i].length; j++) {
                if (i == 0 && j == 0) {
                    cinemaSeats[i][j] = "  ";
                } else if (i == 0) {
                    cinemaSeats[i][j] = j + " ";
                } else if (j == 0) {
                    cinemaSeats[i][j] = i + " ";
                } else {
                    cinemaSeats[i][j] = "S" + " ";
                }
            }
        }
        return cinemaSeats;
    }

    public static void print2DArray(String[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                System.out.print(arr[i][j]);
            }
            System.out.println();
        }
    }
}