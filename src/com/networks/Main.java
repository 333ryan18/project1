package com.networks;

import java.util.Scanner;

public class Main {


    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Hostname of server computer must be supplied");
            return;
        }

        int selection = 1;
        while (selection > 0 && selection < 7) {
            selection = displayMenu();
            switch (selection){
                case 1 :
                    dateTime();
                    break;
                case 2 :
                    uptime();
                    break;
                case 3:
                    memoryUse();
                    break;
                case 4:
                    netstat();
                    break;
                case 5:
                    currentUsers();
                    break;
                case 6:
                    processes();
                    break;
                case 7:
                    System.exit(0);
                    break;
                default :
                    displayMenu();
                    break;
            }
        }

    }


    private static int displayMenu() {

        int userInput;
        try {
            System.out.println("Please Choose an Option:\n");
            System.out.println("1.\tHost current Date and Time");
            System.out.println("2.\tHost uptime");
            System.out.println("3.\tHost memory use");
            System.out.println("4.\tHost Netstat");
            System.out.println("5.\tHost current users");
            System.out.println("6.\tHost running processes");
            System.out.println("7.\tQuit");
            System.out.print("\nEnter your selection number:\n ");

            Scanner scan = new Scanner(System.in);
            userInput = scan.nextInt();
            if (userInput < 1 || userInput > 7) {
                System.out.println("Choice not Valid.");
                userInput = displayMenu();
            }
        } catch (Exception ex) {
            System.out.println("Choice not Valid.");
            userInput = displayMenu();
        }
        return userInput;
    }

    // Option 1
    private static void dateTime(){
        System.out.println("Retrieving Host Current Date and Time:\n");
    }
    // Option 2
    private static void uptime(){
        System.out.println("Retrieving Host Uptime: \n");
    }
    // Option 3
    private static void memoryUse(){
        System.out.println("Retrieving Host Memory Use: \n");
    }
    // Option 4
    private static void netstat(){
        System.out.println("Retrieving Host Netstat: \n");
    }
    // Option 5
    private static void currentUsers(){
        System.out.println("Retrieving Host Current Users: \n");
    }
    // Option 6
    private static void processes(){
        System.out.println("Retrieving Host Running Processes: \n");
    }
}
