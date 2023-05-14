package ru.nsu.gorin.coding_theory.fano_coding;

import java.util.List;
import java.util.Scanner;

public class Decode {

    private List<Node<Character, Integer>> codeTree = null;

    public static void main(String[] args) {
        FileOperations operation = new FileOperations();
        Decode decode = new Decode();
        Scanner reference = operation.openFile(FileOperations.ENCODE_RESULT_FILE_NAME);
        String line = null;

        decode.codeTree = operation.readCodes(FileOperations.CODES_RESULT_FILE_NAME);

        while ((line = operation.readFile(reference)) != null) {
            decode.decoding(line, "");
        }

        System.out.println("Decoded successful");
    }

    private void decoding(String line, String letters) {
        if (line.length() == 0) {
            FileOperations operation = new FileOperations();

            operation.writeFile(letters, FileOperations.DECODE_RESULT_FILE_NAME);

            return;
        }

        int start = 0;

        for (int i = 0; i < line.length(); i++) {
            String code = "";
            boolean flag = false;

            for (int j = 0; j <= i; j++) {
                code += line.charAt(j);
            }

            for (Node<Character, Integer> n : this.codeTree) {
                if (code.equals(n.getCode())) {
                    letters += n.getKey();
                    start = i + 1;
                    flag = true;
                    break;
                }
            }

            if (flag) {
                break;
            }
        }

        this.decoding(line.substring(start), letters);
    }
}