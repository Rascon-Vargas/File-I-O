import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    }








}

