package com.example.apartmentsforrent.persistence;

public class Checker {
    public <T extends Comparable<T>> boolean check(T from, T to, T value, T defaultValue) {
        T actualValue = value == null ? defaultValue : value;

        boolean isAboveFrom = from == null || actualValue.compareTo(from) >= 0;
        boolean isBelowTo = to == null || actualValue.compareTo(to) <= 0;

        return isAboveFrom && isBelowTo;
    }
}
