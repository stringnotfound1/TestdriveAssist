package com.esa.beuth.testdriveassist.xml;

import com.berner.mattner.tools.xml.XmlUtils;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import lombok.NonNull;

public class TestXmlParser {

    public static TestSuite parse(final @NonNull String filePath) throws Exception {
        return (TestSuite) XmlUtils.traverse(XmlUtils.getDocumentElementFromFile(filePath), (node, parent) -> {
            Object current = null;
            Element element = node.getElement();
            NamedNodeMap attributes = element.getAttributes();
            switch (element.getNodeName()) {
                case "TestSuite":
                    current = new TestSuite();
                    ((TestSuite) current).setSuccesful(Boolean.parseBoolean(attributes.getNamedItem("successful").getNodeValue()));
                    break;
                case "TestCase":
                    current = new TestCase();
                    ((TestSuite) parent).getTestCases().add((TestCase) current);
                    ((TestCase) current).setSuccesful(Boolean.parseBoolean(attributes.getNamedItem("successful").getNodeValue()));
                    break;
                case "TestStep":
                    current = new TestStep();
                    ((TestCase) parent).getTestSteps().add((TestStep) current);
                    ((TestStep) current).setSuccesful(Boolean.parseBoolean(attributes.getNamedItem("successful").getNodeValue()));
                    break;
                case "TestCondition":
                    current = new TestCondition();
                    ((TestStep) parent).getTestConditions().add((TestCondition) current);
                    ((TestCondition) current).setSuccessful(Boolean.parseBoolean(attributes.getNamedItem("successful").getNodeValue()));
                    ((TestCondition) current).setType(attributes.getNamedItem("type").getNodeValue());
                    ((TestCondition) current).setValue(attributes.getNamedItem("value").getNodeValue());
                    if (attributes.getNamedItem("time") != null)
                        ((TestCondition) current).setTime(Long.parseLong(attributes.getNamedItem("time").getNodeValue()));
                    if (attributes.getNamedItem("repeatable") != null)
                        ((TestCondition) current).setRepeatable(Boolean.parseBoolean(attributes.getNamedItem("repeatable").getNodeValue()));
                    break;
            }
            return current;
        }, null);
    }

}
