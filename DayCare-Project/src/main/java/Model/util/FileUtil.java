/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

/**
 *
 * @author zhengbochao
 */
public class FileUtil {
    public static String[] readLinesFromFile(String inputFile) {
        String line;
        LinkedList<String> lines = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find inputFile.");
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("Input IO error");
            throw new RuntimeException(e);
        }
        String[] result = new String[lines.size()];
        return lines.toArray(result);
    }

    public static void writeFile(String[] lines, String outputFile) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {
//            bw.write(header);
//            bw.newLine();
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Output IO error");
            throw new RuntimeException(e);
        }
    }
}
