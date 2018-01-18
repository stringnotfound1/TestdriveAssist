package com.esa.beuth.testdriveassist.xml;

import com.berner.mattner.tools.xml.XmlUtils;

import javax.xml.transform.TransformerException;

import lombok.NonNull;

/**
 * Created by jschellner on 17.01.2018.
 */

public class TestXmlWriter {

    public void write(final @NonNull String filePath, final @NonNull TestSuite testSuite) {
        try {
            XmlUtils.write(filePath, XmlUtils.objectToXmlElement(testSuite, object -> object.getClass().getSimpleName()));
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
