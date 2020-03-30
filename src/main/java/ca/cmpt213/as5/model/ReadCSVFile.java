package ca.cmpt213.as5.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class ReadCSVFile {
    public void readCSV(){
        List<String> lines;
        {
            try {
                lines = Files.readAllLines(Paths.get("data\\small_data.csv"));
                for(String line:lines){
                    String[] result = line.split(",");
                    for(String s : result){
                        System.out.print(s + " ");
                        //System.out.println();
                    }
                    System.out.println();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
