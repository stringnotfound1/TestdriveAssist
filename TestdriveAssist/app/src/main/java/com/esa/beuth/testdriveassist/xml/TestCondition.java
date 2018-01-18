package com.esa.beuth.testdriveassist.xml;

import lombok.Data;

@Data
public class TestCondition {
    private Boolean successful;
    private String type;
    private String value;
    private long time;
    private boolean repeatable;
}
