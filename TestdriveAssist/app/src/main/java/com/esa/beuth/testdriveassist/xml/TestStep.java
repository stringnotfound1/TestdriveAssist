package com.esa.beuth.testdriveassist.xml;

import java.util.UUID;

import lombok.Data;

@Data
public class TestStep {
    private final UUID customId = UUID.randomUUID();
    private Boolean successful;
    private String type;
    private String value;
    private long time;
    private boolean repeatable;
}
