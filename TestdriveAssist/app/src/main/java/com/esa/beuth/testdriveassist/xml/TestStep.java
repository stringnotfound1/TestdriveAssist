package com.esa.beuth.testdriveassist.xml;

import java.util.UUID;

import lombok.Data;
import lombok.NonNull;

@Data
public class TestStep {
    private final UUID customId = UUID.randomUUID();
    private Boolean successful;
    private String type;
    private String value;
    private Long time;
    private Comparator comparator = Comparator.EQUAL;
    private boolean repeatable;

    public boolean isConditionMet(final @NonNull String value) {
        return comparator.compare(parseValue(value), parseValue(this.value));
    }

    private Object parseValue(final @NonNull String stringValue) {
        Object parsedValue = null;
        try {
            parsedValue = Double.parseDouble(stringValue);
        } catch (Exception e) {
        }
        if (stringValue.equals("true") || stringValue.equals("false"))
            parsedValue = Boolean.parseBoolean(stringValue);
        if (parsedValue == null)
            parsedValue = stringValue;
        return parsedValue;
    }
}
