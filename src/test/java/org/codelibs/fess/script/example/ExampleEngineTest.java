package org.codelibs.fess.script.example;

import java.util.HashMap;
import java.util.Map;

import org.codelibs.fess.util.ComponentUtil;
import org.dbflute.utflute.lastaflute.LastaFluteTestCase;

public class ExampleEngineTest extends LastaFluteTestCase {
    public ExampleEngine exampleEngine;

    @Override
    protected String prepareConfigFile() {
        return "test_app.xml";
    }

    @Override
    protected boolean isSuppressTestCaseTransaction() {
        return true;
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        exampleEngine = new ExampleEngine();
    }

    @Override
    public void tearDown() throws Exception {
        ComponentUtil.setFessConfig(null);
        super.tearDown();
    }

    public void test_evaluate() {
        final Map<String, Object> params = new HashMap<>();
        assertEquals("test", exampleEngine.evaluate("test", params));
    }

    public void test_getName() {
        assertEquals("example", exampleEngine.getName());
    }
}
