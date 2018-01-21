package com.esa.beuth.testdriveassist.xml;

import lombok.Data;

@Data
public class TestStep {
    private Boolean successful;
    private String type;
    private String value;
    private long time;
    private boolean repeatable;
}
