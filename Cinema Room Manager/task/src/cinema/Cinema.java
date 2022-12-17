package cinema;

import java.util.Scanner;

public class Cinema {
    static boolean flag = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        int rows = scanner.nextInt() + 1;
        System.out.println("Enter the number of seats in each row:");
        int seats = scanner.nextInt() + 1;
        System.out.println(" ");
        String[][] cinemaMap = new String[rows][seats];
        fillMap(cinemaMap);

        while (true) {
            System.out.println("1. Show the seats\n2. Buy a ticket\n3. Statistics\n0. Exit");
            int menuChoice = scanner.nextInt();
            flag = false;
            if (menuChoice == 1) {
                vizualize(cinemaMap);
            } else if (menuChoice == 2) {
                while (true) {
                    System.out.println("Enter a row number:");
                    int rowNumber = scanner.nextInt();
                    if (rowNumber > rows - 1 || rowNumber <= 0) {
                        System.out.println("Wrong input!\n");
                    } else {
                        System.out.println("Enter a seat number in that row:");
                        int seatNumber = scanner.nextInt();
                        if (seatNumber > seats - 1 || seatNumber <= 0) {
                            System.out.println("Wrong input!\n");
                        } else {
                            int price = ticketPrice(rowNumber, seatNumber, rows, seats, cinemaMap);
                            bookTicket(rowNumber, seatNumber, cinemaMap, price);
                            if (flag) {
                                break;
                            }
                        }
                    }
                }
            } else if (menuChoice == 0) {
                break;
            } else if (menuChoice == 3) {
                statistics(rows, seats, cinemaMap);
            } else {
                System.out.println("Wrong input!\n");
            }
        }
    }

    public static void fillMap(String[][] cinemaMap) {
        for (int i = 0; i < cinemaMap.length; i++) {
            for (int j = 0; j < cinemaMap[i].length; j++) {
                if (i == 0) {
                    cinemaMap[0][j] = Integer.toString(j);
                } else if (i != 0 && j == 0) {
                    cinemaMap[i][j] = Integer.toString(i);
                } else if (i != 0) {
                    cinemaMap[i][j] = "S";
                }
            }
            cinemaMap[0][0] = " ";
        }
    }

    public static void vizualize(String[][] cinemaMap) {
        System.out.println("Cinema:");
        for (String[] rows : cinemaMap) {
            for (String seats : rows) {
                System.out.print(seats + " ");
            }
            System.out.println();
        }
        System.out.println(" ");
    }

    public static int ticketPrice(int rowNumber, int seatNumber, int rows, int seats, String[][] cinemaMap) {
        int totalSeats = seats * rows;
        int ticketPrice;
        int frontRows = rows / 2 - 1;
        if (totalSeats <= 60) {
            ticketPrice = 10;
        } else {
            if (rowNumber <= frontRows) {
                ticketPrice = 10;
            } else {
                ticketPrice = 8;
            }
        }
        return ticketPrice;

    }

    public static void bookTicket(int rowNumber, int seatNumber, String[][] cinemaMap, int ticketPrice) {
        if (cinemaMap[rowNumber][seatNumber].equals("B")) {
            System.out.println("That ticket has already been purchased!");
        } else {
            System.out.println("Ticket price: $" + ticketPrice);
            System.out.println(" ");
            cinemaMap[rowNumber][seatNumber] = "B";
            flag = true;
        }
    }

    public static void statistics(int rows, int seats, String[][] cinemaMap) {
        int numPurchTickets = 0;
        int currentIncome = 0;
        int totalIncome = 0;
        rows -= 1;
        seats -= 1;
        int totalSeats = rows * seats;
        int frontRows = rows / 2;
        int backRows = rows - frontRows;
        if (totalSeats <= 60) {
            totalIncome = rows * seats * 10;
            for (int i = 0; i < cinemaMap.length; i++) {
                for (int j = 0; j < cinemaMap[i].length; j++) {
                    if (cinemaMap[i][j].equals("B")) {
                        currentIncome += 10;
                        numPurchTickets++;
                    }
                }
            }
        } else {
            totalIncome = frontRows * seats * 10 + backRows * seats * 8;
            for (int i = 0; i < cinemaMap.length; i++) {
                for (int j = 0; j < cinemaMap[i].length; j++) {
                    if (cinemaMap[i][j].equals("B")) {
                        if (i <= frontRows) {
                            currentIncome += 10;
                            numPurchTickets++;
                        } else {
                            currentIncome += 8;
                            numPurchTickets++;
                        }
                    }
                }
            }
        }

        float stat = (float) numPurchTickets * 100 / totalSeats;
        System.out.printf("Number of purchased tickets: %d\n", numPurchTickets);
        System.out.printf("Percentage: %.2f%%\n", stat);
        System.out.printf("Current income: " + "$" + "%d\n", currentIncome);
        System.out.printf("Total income: " + "$" + "%d\n", totalIncome);
        System.out.println(" ");
    }
}
