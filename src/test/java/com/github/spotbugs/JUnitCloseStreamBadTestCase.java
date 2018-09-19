package com.github.spotbugs;

import junit.framework.TestCase;

import java.io.FileInputStream;
import java.io.IOException;

public class JUnitCloseStreamBadTestCase extends TestCase {
    FileInputStream in;

    public void setUp() throws IOException {
        in = new FileInputStream("test");
    }

    public void tearDown() throws IOException {
//        in.close();
    }

    public void testSomething() {

    }
}
