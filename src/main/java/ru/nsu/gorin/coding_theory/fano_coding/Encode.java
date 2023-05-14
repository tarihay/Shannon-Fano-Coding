package ru.nsu.gorin.coding_theory.fano_coding;

import java.util.*;

public class Encode {
    private List<Node<Character, Integer>> frequencies = new ArrayList<>();

    public static void main(String[] args) {
        Encode encode = new Encode();
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
            operation.writeFile(encode.encoding(line), FileOperations.ENCODE_RESULT_FILE_NAME);
        }

        operation.writeCodes(encode.frequencies, FileOperations.CODES_RESULT_FILE_NAME);

        System.out.println("File was encoded!");
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

            int stop = 1;

            while (stop < list.size()) {
                double total_possibility = 0;

                for (int i = 0; i < stop; i++) {
                    total_possibility += list.get(i).getPossibility();
                }

                if (Math.abs(total_possibility - NodeHelper.sum(list, stop)) <= 0.2) {
                    for (int i = 0; i < stop; i++) {
                        list.get(i).setCode(list.get(i).getCode() + '0');
                        left.add(list.get(i));
                    }

                    for (int i = stop; i < list.size(); i++) {
                        list.get(i).setCode(list.get(i).getCode() + '1');
                        right.add(list.get(i));
                    }

                    break;
                }

                stop++;
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