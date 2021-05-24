package main.java.fileIO;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by marco on 29/03/2017.
 */
public class MyFileReader {

    private String line = "";
    private String inputFilePath;
    private BufferedReader bufferedReader;

    public MyFileReader(String inputFilePath) {

        this.inputFilePath = inputFilePath;
        try {
            bufferedReader = new BufferedReader(new FileReader(inputFilePath));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getNextLine() {

        try {
            line = bufferedReader.readLine();
            if (line != null) {
                return line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void close() {

        try {
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
