package ru.nsu.gorin.coding_theory.fano_coding;

import java.io.File;
import java.util.*;

import static ru.nsu.gorin.coding_theory.fano_coding.FileOperations.ENCODE_RESULT_FILE_NAME;

public class Encode {
    private List<Node<Character, Integer>> frequencies = new ArrayList<>();

    public static void main(String[] args) {
        long sec = System.currentTimeMillis();

        Encode encode = new Encode();

        File encodeFile = new File(ENCODE_RESULT_FILE_NAME);
        if (encodeFile.exists()){
            encodeFile.delete();
        }

        FileOperations operation = new FileOperations();
        Scanner reference = operation.openFile(FileOperations.INPUT_FILE_NAME);
        String line = null;

        while ((line = operation.readFile(reference)) != null) {
            encode.calculateFrequencies(line);
        }

        encode.sort();
        encode.calculatePossibilities();
        encode.generateCodes(encode.frequencies);

        reference = operation.openFile(FileOperations.INPUT_FILE_NAME);

        while ((line = operation.readFile(reference)) != null) {
            operation.writeFile(encode.encoding(line), ENCODE_RESULT_FILE_NAME);
        }

        operation.writeCodes(encode.frequencies, FileOperations.CODES_RESULT_FILE_NAME);

        System.out.println("File was encoded! The time is: " + (System.currentTimeMillis() -sec));
    }

    private void calculateFrequencies(String line) {
        for (int i = 0; i < line.length(); i++) {
            boolean flag = true;

            for (Node<Character, Integer> n : this.frequencies) {
                if (n.getKey().equals(line.charAt(i))) {
                    n.setValue(n.getValue() + 1);

                    flag = false;
                    break;
                }
            }

            if (flag) {
                this.frequencies.add(new Node<>(line.charAt(i), 1));
            }
        }
    }

    private void sort() {
        this.frequencies.sort((first, second) -> second.getValue().compareTo(first.getValue()));
    }

    private void calculatePossibilities() {
        int amount_of_letter = 0;
        boolean counter = true;

        for (int i = 0; i < 2; i++) {
            for (Node<Character, Integer> n : this.frequencies) {
                if (counter) {
                    amount_of_letter += n.getValue();
                } else {
                    n.setPossibility((double) n.getValue() / amount_of_letter);
                }
            }

            counter = false;
        }
    }

    private void generateCodes(List<Node<Character, Integer>> list) {
        if (list.size() > 1) {
            List<Node<Character, Integer>> left = new ArrayList<>();
            List<Node<Character, Integer>> right = new ArrayList<>();

            int index = 0;
            double leftWeight = 0.0;
            double rightWeight = 0.0;

            while (index < list.size()) {
                if (leftWeight <= rightWeight) {
                    left.add(list.get(index));
                    leftWeight += list.get(index).getPossibility();
                } else {
                    right.add(list.get(index));
                    rightWeight += list.get(index).getPossibility();
                }
                index++;
            }

            for (Node<Character, Integer> node : left) {
                node.setCode(node.getCode() + '0');
            }

            for (Node<Character, Integer> node : right) {
                node.setCode(node.getCode() + '1');
            }

            this.generateCodes(left);
            this.generateCodes(right);
        }
    }

    private String encoding(String line) {
        StringBuilder encoded = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            for (Node<Character, Integer> n : this.frequencies) {
                if (line.charAt(i) == n.getKey()) {
                    encoded.append(n.getCode());
                }
            }
        }

        return encoded.toString();
    }
}