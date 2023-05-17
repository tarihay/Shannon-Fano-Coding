package ru.nsu.gorin.coding_theory.fano_coding;

import java.io.Serializable;

public class Node<K, V> implements Serializable {
    private K key = null;
    private V value = null;
    private String code = "";
    private double possibility = 0;

    public Node(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return this.key;
    }

    public V getValue() {
        return this.value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getPossibility() {
        return this.possibility;
    }

    public void setPossibility(double possibility) {
        this.possibility = possibility;
    }

    @Override
    public String toString() {
        return "Key: " + this.key + "; Value: " + this.value + "; Code: " + code +"\n";
    }
}