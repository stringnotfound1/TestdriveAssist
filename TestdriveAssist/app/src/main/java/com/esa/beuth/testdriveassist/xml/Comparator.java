package com.esa.beuth.testdriveassist.xml;

import android.widget.Toast;

import com.esa.beuth.testdriveassist.global.DoubleComparison;

import lombok.NonNull;

/**
 * Created by jschellner on 27.01.2018.
 */

public enum Comparator {
    GREATER((num1, num2) -> num1 > num2),
    GREATER_EQUAL((num1, num2) -> num1 >= num2),
    LESS((num1, num2) -> num1 < num2),
    LESS_EQUAL((num1, num2) -> num1 <= num2),
    EQUAL((num1, num2) -> num1 == num2);

    private DoubleComparison comparison;

    Comparator(final @NonNull DoubleComparison comparison) {
        this.comparison = comparison;
    }

    boolean compare(final @NonNull Object obj1, final @NonNull Object obj2) {
        return obj1.getClass().equals(Double.class) && obj2.getClass().equals(Double.class) ?
                comparison.compare(((Double) obj1).doubleValue(), ((Double) obj2).doubleValue()) : obj1.equals(obj2);
    }
}