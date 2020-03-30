package ca.cmpt213.as5.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadCSVFile {

    private int index = 0;


    public List<Course> readCSV(List<Course> courses){
        List<String> lines;
        {
            try {
                lines = Files.readAllLines(Paths.get("data\\small_data.csv"));
                for(String line:lines){
                    String[] result = line.split(",");
                    for(String s : result){
                        System.out.print(s + " ");
                        courses.add(new Course(Integer.parseInt(result[0]), result[1],
                                Integer.parseInt(result[2]), result[3], Integer.parseInt(result[4]),
                                Integer.parseInt( result[5]), result[6], result[7]));
                        //System.out.println();
                    }
                    System.out.println();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return courses;
    }





}
