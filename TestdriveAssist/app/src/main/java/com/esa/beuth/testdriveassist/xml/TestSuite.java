package com.esa.beuth.testdriveassist.xml;

import java.util.LinkedList;
import java.util.List;

import lombok.Data;

@Data
public class TestSuite {
	private final List<TestCase> testCases = new LinkedList<>();
	private boolean succesful;
}
