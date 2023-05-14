package ru.nsu.gorin.coding_theory.fano_coding;

import java.util.List;

public class NodeHelper {
        public static double sum(List<Node<Character, Integer>> rest_of_list, int start) {
            double result = 0d;

            for (int i = start; i < rest_of_list.size(); i++) {
                result += rest_of_list.get(i).getPossibility();
            }

            return result;
        }
}
