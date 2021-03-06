import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;


//This was an attempt to appropriately modularize my code.
public class ReadFile {
    String token1 = "";

    private String path = "/home/joshua/Programs/projects/line-servers/file.txt";

    public ReadFile(String file_path) {
        path = file_path;
    }

    int readLines() throws IOException {

        FileReader file_to_read = new FileReader(path);
        BufferedReader bf = new BufferedReader(file_to_read);
    
        String aLine;
        int numberOfLines = 0;

        while (( aLine= bf.readLine() ) != null) {
            numberOfLines++;
        }
        bf.close();

        return numberOfLines;

    }

    public String[] OpenFile() throws IOException {

        FileReader fr = new FileReader(path);
        BufferedReader textReader = new BufferedReader(fr);

        int numberOfLines = readLines();
        String[] textData = new String[numberOfLines];

        int i;

        for (i=0; i < numberOfLines; i++) {
            textData[i] = textReader.readLine();
            System.out.println(textData[i]);
        }

        textReader.close();
        return textData;
    }
}
