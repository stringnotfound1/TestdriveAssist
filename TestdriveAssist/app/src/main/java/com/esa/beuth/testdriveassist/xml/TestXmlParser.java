package com.esa.beuth.testdriveassist.xml;

import com.berner.mattner.tools.xml.XmlUtils;
import com.esa.beuth.testdriveassist.gui.CustomTestStep;

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
                    if (attributes.getNamedItem("successful") != null)
                        ((TestSuite) current).setSuccessful(Boolean.parseBoolean(attributes.getNamedItem("successful").getNodeValue()));
                    break;
                case "TestCase":
                    current = new TestCase();
                    ((TestSuite) parent).getTestCases().add((TestCase) current);
                    if (attributes.getNamedItem("successful") != null)
                        ((TestCase) current).setSuccessful(Boolean.parseBoolean(attributes.getNamedItem("successful").getNodeValue()));
                    break;
                case "TestStep":
                    current = new TestStep();
                    ((TestCase) parent).getTestSteps().add((TestStep) current);
                    TestStep testStep = (TestStep) current;
                    testStep.setType(attributes.getNamedItem("type").getNodeValue());
                    testStep.setValue(attributes.getNamedItem("value").getNodeValue());
                    if (attributes.getNamedItem("successful") != null)
                        testStep.setSuccessful(Boolean.parseBoolean(attributes.getNamedItem("successful").getNodeValue()));
                    if (attributes.getNamedItem("time") != null)
                        testStep.setTime(Long.parseLong(attributes.getNamedItem("time").getNodeValue()));
                    if (attributes.getNamedItem("repeatable") != null)
                        testStep.setRepeatable(Boolean.parseBoolean(attributes.getNamedItem("repeatable").getNodeValue()));
                    break;
            }
            return current;
        }, null);
    }

}
