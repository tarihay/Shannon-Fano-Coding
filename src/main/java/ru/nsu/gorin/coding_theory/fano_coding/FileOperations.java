package ru.nsu.gorin.coding_theory.fano_coding;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileOperations {
    public static final String INPUT_FILE_NAME = "input.txt";
    public static final String ENCODE_RESULT_FILE_NAME = "encode.txt";
    public static final String DECODE_RESULT_FILE_NAME = "decode.txt";
    public static final String CODES_RESULT_FILE_NAME = "codes.bin";

    private static FileInputStream byteInput = null;

    public Scanner openFile(String file_name) {
        Scanner file_reference = null;

        try {
            file_reference = new Scanner(new BufferedReader(new FileReader(new File(file_name))));
        } catch (FileNotFoundException ex) {
            System.out.println(file_name + " could not found!");
            ex.printStackTrace();
        }

        return file_reference;
    }

    public String readFile(Scanner file) {
        String line = null;

        if (file.hasNextLine()) {
            line = file.nextLine();
        }

        return line;
    }

    public void writeFile(String line, String file_name) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(file_name), true))) {
            writer.write(line + '\n');
        } catch (IOException ex) {
            System.out.println("Could not write to the " + file_name + "!");
            ex.printStackTrace();
        }
    }

    public void writeCodes(List<Node<Character, Integer>> list, String file_name) {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file_name))) {
            output.writeObject(list);
        } catch (FileNotFoundException ex) {
            System.out.println(file_name + " could not found!\n" + ex.getMessage());
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Could not write to the " + file_name + "!\n" + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public List<Node<Character, Integer>> readCodes(String file_name) {
        List<Node<Character, Integer>> codes = null;

        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(new File(file_name)))) {
            codes = (ArrayList<Node<Character, Integer>>) input.readObject();
        } catch (FileNotFoundException ex) {
            System.out.println(file_name + " could not found!\n" + ex.getMessage());
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            System.out.println("Node class could not found!\n" + ex.getMessage());
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Could not read from " + file_name + "!\n" + ex.getMessage());
        }

        return codes;
    }
}
