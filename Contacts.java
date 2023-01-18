import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;



public class Contacts {
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        String directory = "data";
        String filename = "contacts.txt";

        Path dataDirectory = Paths.get(directory);
        Path dataFile = Paths.get(directory, filename);

        if(Files.notExists(dataDirectory)){
            Files.createDirectories(dataDirectory);
        }

        if(! Files.exists(dataFile)){
            Files.createFile(dataFile);
        }

        Path contactTXTPath = dataFile;

        boolean proceed = false;

        do{
            boolean badInput = true;
            System.out.println("\nHello! Please select an option from below: ");
            System.out.println(" 1. View contacts");
            System.out.println(" 2. Add a new contact");
            System.out.println(" 3. Search contact by name");
            System.out.println(" 4. Delete an existing contact");
            System.out.println(" 5. Exit");
            System.out.println("\nPlease enter a number option: ");

            while(badInput){
                int userInput = scanner.nextInt();

                if(userInput < 1 || userInput >5){
                    System.out.println("Invalid! Please enter a number between 1 and 5.");
                }else if(userInput == 1){
                    String name = "Name";
                    String phoneNumber = "Phone Number";

                    System.out.println("---------------CONTACT LIST---------------");
                    System.out.printf("%-20s| %-20s|\n", name, phoneNumber);
                    System.out.println("------------------------------------------");

                    printContacts(contactTXTPath);
                    proceed = mainMenu(true);
                    badInput = false;

                } else if(userInput == 2){
                    boolean answer = true;
                    while (answer){
                        System.out.println("---------------ADD NEW CONTACT----------------");
                        System.out.println("Please enter the first and last name of contact.");

                        String firstName = scanner.next();
                        String lastName = scanner.next();

                        System.out.println("Please enter the contacts phone number: ");
                        String contactNumber = scanner.next();

                        String contactName = firstName + " " + lastName;
                        String contactInfo = contactName + " | " + contactNumber;

                        Files.write(contactTXTPath, Arrays.asList(contactInfo), StandardOpenOption.APPEND);

                        System.out.println("\nThe contact " + contactName + " has been successfully added.");
                        System.out.println("\nWould you like to add another contact? [y/N]");
                        String response = scanner.next();

                        answer = response.equalsIgnoreCase("y") || response.equalsIgnoreCase("yes");
                    }
                    proceed = mainMenu(true);
                    badInput = false;

                }else if(userInput == 3){
                    searchContacts(contactTXTPath);
                    proceed = mainMenu(true);
                    badInput = false;
                }else if(userInput == 4){
                    removeContact(contactTXTPath);
                    proceed = mainMenu(true);
                    badInput = false;
                }else if(userInput == 5){
                    System.out.println("----------Thank you for participating, please come again soon!----------");
                    badInput = false;
                    proceed = false;
                }
            }
        } while (proceed);
    }


    public static void printContacts(Path filePath) throws IOException {
        List<String> personList = Files.readAllLines(filePath);

        for(int i = 0; i < personList.size(); i++){
            String name = personList.get(i);
            String [] peopleNames = name.split("\\|");
            String mainHeading = peopleNames[0];
            String subHeading = peopleNames[1];
            System.out.printf("%-20s| %-20s|\n", mainHeading, subHeading);

        }
    }

    public static void searchContacts(Path filePath) throws IOException{
        List<String> personList = Files.readAllLines(filePath);
        boolean tryAgain = true;

        do{
            System.out.println("---------------SEARCH CONTACT---------------\n");
            System.out.println("Please enter a name you would like to look up: ");
            String contactName = scanner.next();
            System.out.println("\nHere is the contact info: \n");
            int findContact = 0;

            for(int i = 0; i < personList.size(); i++){
                if(personList.get(i).toLowerCase().contains(contactName.toLowerCase())){
                    findContact += 1;
                    System.out.printf("%d: %s\n", i + 1, personList.get(i));
                }else if(i == personList.size() - 1 && findContact == 0){
                    System.out.println("Contact not found!");
                }
            }
            System.out.println("\nWould you like to search again [y/N]");
            String userInput = scanner.next();
            if(userInput.equalsIgnoreCase("yes") || userInput.equalsIgnoreCase("y")){
                tryAgain = true;
            }else {
                break;
            }
        }while(tryAgain);
    }

    public static void removeContact(Path filePath) throws IOException{
        List<String> lines = Files.readAllLines(filePath);
        ArrayList<String> newList = new ArrayList<>();

        System.out.println("---------------DELETE CONTACT BY NAME---------------");
        printContacts(filePath);

        System.out.println("Please enter the name of the contact you wish to delete: ");
        String deleteContact = scanner.next();

        for(String line : lines){
            if(line.toLowerCase().contains(deleteContact.toLowerCase())){
                continue;
            }
            newList.add(line);
        }
        Files.write(filePath, newList);
        System.out.println("The contact " + deleteContact + " has been removed!");

    }

    public static boolean mainMenu(boolean yesNo){
        System.out.println("Would you like to return to the main menu? [y/N]");
        String userInput = scanner.next();

        if(userInput.equalsIgnoreCase("yes") || userInput.equalsIgnoreCase("y")){
            yesNo = true;
        }else{
            yesNo = false;
        }
        return yesNo;
    }





}

