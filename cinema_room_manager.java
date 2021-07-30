package cinema;

import java.util.Scanner;

public class Cinema {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        int row = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seat = scanner.nextInt();
        int totalSeats = row * seat;
        //int total;
        String[][] cinema = new String[row + 1][seat + 1];
        bildCinema(cinema);
        menu(cinema, totalSeats, row, seat);
    }
        
    public static void menu(String[][] cinema, int totalSeats, int row, int seat) {
        boolean showMenu = true;
        while (showMenu) {
            Scanner sc = new Scanner(System.in);
            System.out.println("1. Show the seats");
            System.out.println("2. Buy a ticket");
            System.out.println("3. Statistics");
            System.out.println("0. Exit");
            int entry = sc.nextInt();
            switch (entry) {
                case 0:
                    showMenu = false;
                    break;
                case 1:
                    showTheSeats(cinema);
                    break;
                case 2:
                    buyATicket(cinema, totalSeats, row);
                    break;
                case 3:
                    showStats(totalSeats, row, cinema, seat);
                    break;
            }
        }
    }
        
    public static String[][] bildCinema(String[][] cinema) {
        //Array cinema befüllen
        for (int i = 0; i < cinema.length; i++) {
            String iString = String.valueOf(i);
            for (int j = 0; j < cinema[i].length; j++) {
                String jString = String.valueOf(j);
                if (i == 0 && j == 0) {
                    cinema[i][j] = " ";
                } else if (i == 0) {
                    cinema[i][j] = jString;
                } else if (j == 0) {
                    cinema[i][j] = iString;
                } else if (i != 0 && j != 0) {
                    cinema[i][j] = "S";
                }
            }
        }
        
        return cinema;
    } 
    
    public static String[][] showTheSeats(String[][] cinema) {        
        //Array cinema ausgeben
        System.out.println("Cinema:");
        for (int i = 0; i < cinema.length; i++) {
            for (int j = 0; j < cinema[i].length; j++) {
                System.out.print(cinema[i][j] + " ");
            }
            System.out.println();
        }
        
        return cinema;   
    }
        
        
        
        
        /*if (totalSeats < 60) {
            total = totalSeats * 10;
            System.out.println("$" + total);
        } else if (totalSeats >= 60) {
            int halfRows = row / 2;
            if (row % 2 == 0) {
                int totalFirst = ((halfRows * seat) * 10);
                int totalSecond = ((halfRows * seat) * 8);
                total = totalFirst + totalSecond;
                System.out.println("$" + total); 
            } else {
                int firstHalf = halfRows % row;
                int secondHalf = row - firstHalf;
                int totalFirst = ((firstHalf * seat) * 10);
                int totalSecond = ((secondHalf * seat) * 8);
                total = totalFirst + totalSecond;
                System.out.println("$" + total);    
            }
            
        }*/
        
    public static String[][] buyATicket(String[][] cinema, int totalSeats, int row) {
        Scanner sc2 = new Scanner(System.in);
        //Eingabe reservierter Sitz in Reihe
        boolean correctSeat = true;
        
        while (correctSeat) {
            System.out.println("Enter a row number:");
            int rowR = sc2.nextInt();
            System.out.println("Enter a seat number in that row:");
            int seatR = sc2.nextInt();
            if (rowR >= 1 && rowR < cinema.length) {
                if (seatR >= 1 && seatR < cinema[rowR].length) {
                     if (cinema[rowR][seatR].equals("B")) {
                        System.out.println("That ticket has already been purchased!");
                    } else {
                        cinema[rowR][seatR] = "B";
                        correctSeat = false;    
                        getPrice(totalSeats, row, rowR);
                    }    
                } else {
                    System.out.println("Wrong input!");
                }
            } else {
                System.out.println("Wrong input!");
            }
               
        }
                       
        return cinema;
    }
    
    public static void getPrice(int totalSeats, int row, int rowR) {
        //Preis des Sitzes, je nach größe des Saals
        if (totalSeats < 60) {
            System.out.println("Ticket price: $10");
        } else if (totalSeats >= 60) {
            int halfRows = row / 2;
            if (row % 2 == 0 && halfRows < rowR) {
                System.out.println("Ticket price: $8");
            } else if (row % 2 == 0 && halfRows >= rowR) {
                System.out.println("Ticket price: $10");
            } else if (halfRows < rowR) {
                System.out.println("Ticket price: $8");
            } else {
                System.out.println("Ticket price: $10");
            }
        }
            
        return;
    }
    
    public static void showStats(int totalSeats, int row, String[][] cinema, int seat) {
        System.out.println("Number of purchased Tickets: " + purchasedTickets(cinema));
        String percent = String.format("Percentage: %.2f", percentageReserved(totalSeats, cinema));
        System.out.println(percent + "%");
        System.out.println("Current income: $" + currentIncome(cinema, row, totalSeats));
        System.out.println("Total income: $" + totalIncome(totalSeats, row, seat));
        return;
        
    }
    
    public static int purchasedTickets(String[][] cinema) {
        int purchasedTickets = 0;
        
        for (int i = 0; i < cinema.length; i++) {
            for (int j = 0; j < cinema[i].length; j++){
                if (cinema[i][j].equals("B")) {
                    purchasedTickets ++;
                }
            }
        }
        
        return purchasedTickets;
    }
    
    public static double percentageReserved(int totalSeats, String[][] cinema) {
        int tickets = purchasedTickets(cinema);        
        double ticketsReserved = Double.valueOf(tickets);
        double percentage = 100 * ticketsReserved / totalSeats;
        return percentage;
    }
    
    public static int currentIncome(String[][] cinema, int row, int totalSeats) {
        int currentIncome = 0;
        int currentReservedFirst = 0;
        int currentReservedSecond = 0;
        int halfRows = row / 2;
        
        for (int i = 0; i < cinema.length; i++) {
            for (int j = 0; j < cinema[i].length; j++) {
                if (cinema[i][j].equals("B")) {
                    if (row % 2 == 0) {
                        if (i <= halfRows) {
                            currentReservedFirst += 1;    
                        } else if (i > halfRows && i <= row) {
                            currentReservedSecond += 1;
                        }
                    } else {
                        int firstHalf = halfRows % row;
                        int secondHalf = row - firstHalf;
                        if (i <= firstHalf) {
                            currentReservedFirst += 1;
                        } else if (i >= secondHalf && i <= row) {
                            currentReservedSecond += 1;
                        }
                    }
                }
            }
        }
        
        if (totalSeats < 60) {
            currentIncome = (currentReservedFirst + currentReservedSecond) * 10;
        } else if (totalSeats >= 60) {
            currentIncome = currentReservedFirst * 10 + currentReservedSecond * 8;
        }
        return currentIncome;
    }
    
    public static int totalIncome(int totalSeats, int row, int seat) {
        int total = 0;
        if (totalSeats < 60) {
            total = totalSeats * 10;
            //System.out.println("$" + total);
        } else if (totalSeats >= 60) {
            int halfRows = row / 2;
            if (row % 2 == 0) {
                int totalFirst = ((halfRows * seat) * 10);
                int totalSecond = ((halfRows * seat) * 8);
                total = totalFirst + totalSecond;
                //System.out.println("$" + total); 
            } else {
                int firstHalf = halfRows % row;
                int secondHalf = row - firstHalf;
                int totalFirst = ((firstHalf * seat) * 10);
                int totalSecond = ((secondHalf * seat) * 8);
                total = totalFirst + totalSecond;
                //System.out.println("$" + total);    
            }
        }
        
        return total;
    }
        
        
        
        /*//Ausagebe Saal mit reserviertem Sitz
        System.out.println("Cinema:");
        for (int i = 0; i < cinema.length; i++) {
            for (int j = 0; j < cinema[i].length; j++) {
                System.out.print(cinema[i][j] + " ");
            }
            System.out.println();
        }*/
}
