package com.esa.beuth.testdriveassist.xml;

import java.util.LinkedList;
import java.util.List;

import lombok.Data;

@Data
public class TestStep {
	private final List<TestCondition> testConditions = new LinkedList<>();
	private boolean succesful;
}
