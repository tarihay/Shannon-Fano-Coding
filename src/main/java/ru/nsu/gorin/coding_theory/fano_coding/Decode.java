package ru.nsu.gorin.coding_theory.fano_coding;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
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

//    private void decoding(String line, StringBuilder decodedText) {
//        if (line.length() == 0) {
//            FileOperations operation = new FileOperations();
//            operation.writeFile(decodedText.toString(), FileOperations.DECODE_RESULT_FILE_NAME);
//            return;
//        }
//
//        String code = "";
//        int index = 0;
//
//        while (index < line.length()) {
//            code += line.charAt(index);
//
//            if (codeToCharacterMap.containsKey(code)) {
//                decodedText.append(codeToCharacterMap.get(code));
//                code = "";
//            }
//
//            index++;
//        }
//    }

//    private void decoding(String line, String letters) {
//        if (line.length() == 0) {
//            FileOperations operation = new FileOperations();
//
//            operation.writeFile(letters, DECODE_RESULT_FILE_NAME);
//
//            return;
//        }
//
//        int start = 0;
//
//        for (int i = 0; i < line.length(); i++) {
//            String code = "";
//            boolean flag = false;
//
//            for (int j = 0; j <= i; j++) {
//                code += line.charAt(j);
//            }
//
//            for (Node<Character, Integer> n : this.codeTree) {
//                if (code.equals(n.getCode())) {
//                    letters += n.getKey();
//                    start = i + 1;
//                    flag = true;
//                    break;
//                }
//            }
//
//            if (flag) {
//                break;
//            }
//        }
//
//        this.decoding(line.substring(start), letters);
//    }

    private void decoding(String line) {
        StringBuilder decodedLetters = new StringBuilder();
        int currentPosition = 0;
        StringBuilder currentCode = new StringBuilder();

        while (currentPosition < line.length()) {
            currentCode.append(line.charAt(currentPosition));

            Character decodedChar = codeToCharacterMap.get(currentCode.toString());
            if (decodedChar != null) {
                decodedLetters.append(decodedChar);
                currentCode.setLength(0); // Reset the current code
            }
            currentPosition++;
        }

        FileOperations operation = new FileOperations();
        operation.writeFile(decodedLetters.toString(), DECODE_RESULT_FILE_NAME);
    }
}