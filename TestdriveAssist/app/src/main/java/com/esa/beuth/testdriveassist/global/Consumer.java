package com.esa.beuth.testdriveassist.global;

/**
 * Created by jschellner on 21.01.2018.
 */

public interface Consumer<T> {
    void accept(T t);
}
