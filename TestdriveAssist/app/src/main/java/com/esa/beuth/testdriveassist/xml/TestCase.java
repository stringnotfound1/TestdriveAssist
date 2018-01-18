package com.esa.beuth.testdriveassist.xml;

import java.util.LinkedList;
import java.util.List;

import lombok.Data;

@Data
public class TestCase {
	private final List<TestStep> testSteps = new LinkedList<>();
	private Boolean succesful;
}
