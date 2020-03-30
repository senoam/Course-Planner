package ca.cmpt213.as5.model;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadCSVFile {
    public void readCSV(List<Course> courses){
        BufferedReader objReader;
        String strCurrentLine;

        final String SMALL_DATA_CSV = "data\\small_data.csv";
        final String COURSE_DATA_2018_CSV = "data\\course_data_2018.csv";
        try {
            objReader = new BufferedReader(new FileReader(COURSE_DATA_2018_CSV));
            int i = 0;
            while ((strCurrentLine = objReader.readLine()) != null) {
//                System.out.println(strCurrentLine);
                if(i != 0){
                    courses.add(new Course(strCurrentLine));
                }
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
