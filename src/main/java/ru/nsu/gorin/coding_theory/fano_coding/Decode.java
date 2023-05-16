package ru.nsu.gorin.coding_theory.fano_coding;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import static ru.nsu.gorin.coding_theory.fano_coding.FileOperations.DECODE_RESULT_FILE_NAME;
import static ru.nsu.gorin.coding_theory.fano_coding.FileOperations.ENCODE_RESULT_FILE_NAME;

public class Decode {

    private List<Node<Character, Integer>> codeTree = null;
    private Map<String, Character> codeToCharacterMap;

    public static void main(String[] args) {
        long sec = System.currentTimeMillis();

        FileOperations operation = new FileOperations();

        File decodeFile = new File(DECODE_RESULT_FILE_NAME);
        if (decodeFile.exists()){
            decodeFile.delete();
        }

        Decode decode = new Decode();
        Scanner reference = operation.openFile(FileOperations.ENCODE_RESULT_FILE_NAME);
        String line = null;

        decode.codeTree = operation.readCodes(FileOperations.CODES_RESULT_FILE_NAME);
        decode.codeToCharacterMap = decode.codeTree.stream()
                .collect(Collectors.toMap(Node::getCode, Node::getKey));

        while ((line = operation.readFile(reference)) != null) {
            decode.decoding(line);
        }

        System.out.println("Decoded successful! The time is: " + (System.currentTimeMillis() -sec));
    }

    private void decoding(String line) {
        StringBuilder decodedLetters = new StringBuilder();
        int currentPosition = 0;
        String currentCode = "";

        while (currentPosition < line.length()) {
            currentCode += line.charAt(currentPosition);

            Character decodedChar = codeToCharacterMap.get(currentCode);
            if (decodedChar != null) {
                decodedLetters.append(decodedChar);
                currentCode = ""; // Reset the current code
            }
            currentPosition++;
        }

        FileOperations operation = new FileOperations();
        operation.writeFile(decodedLetters.toString(), DECODE_RESULT_FILE_NAME);
    }
}