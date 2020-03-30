package ca.cmpt213.as5.model;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class ReadCSVFile {
    public void readCSV(){
        BufferedReader objReader;
        String strCurrentLine;
        final String smallDataCSV = "data\\small_data.csv";
        final String courseData2018CSV = "data\\course_data_2018.csv";
        try {
            objReader = new BufferedReader(new FileReader(courseData2018CSV));
            while ((strCurrentLine = objReader.readLine()) != null) {
                /*
                    Testing
                    Replace with copying to actual class
                 */
                System.out.println(strCurrentLine.replace(" ",""));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
