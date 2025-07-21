/*
 * Copyright 2012-2025 CodeLibs Project and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
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

    // Ultrathink comprehensive test cases

    public void test_evaluate_withNullTemplate() {
        final Map<String, Object> params = new HashMap<>();
        assertNull(exampleEngine.evaluate(null, params));
    }

    public void test_evaluate_withEmptyTemplate() {
        final Map<String, Object> params = new HashMap<>();
        assertEquals("", exampleEngine.evaluate("", params));
    }

    public void test_evaluate_withNullParameterMap() {
        assertEquals("test", exampleEngine.evaluate("test", null));
    }

    public void test_evaluate_withEmptyParameterMap() {
        final Map<String, Object> params = new HashMap<>();
        assertEquals("test", exampleEngine.evaluate("test", params));
    }

    public void test_evaluate_withPopulatedParameterMap() {
        final Map<String, Object> params = new HashMap<>();
        params.put("key1", "value1");
        params.put("key2", 123);
        params.put("key3", true);
        assertEquals("template content", exampleEngine.evaluate("template content", params));
    }

    public void test_evaluate_withSpecialCharacters() {
        final Map<String, Object> params = new HashMap<>();
        final String template = "Special chars: !@#$%^&*(){}[]|\\:;\"'<>,.?/~`";
        assertEquals(template, exampleEngine.evaluate(template, params));
    }

    public void test_evaluate_withMultilineTemplate() {
        final Map<String, Object> params = new HashMap<>();
        final String template = "Line 1\nLine 2\nLine 3";
        assertEquals(template, exampleEngine.evaluate(template, params));
    }

    public void test_evaluate_withJapaneseCharacters() {
        final Map<String, Object> params = new HashMap<>();
        final String template = "こんにちは世界";
        assertEquals(template, exampleEngine.evaluate(template, params));
    }

    public void test_evaluate_withLongTemplate() {
        final Map<String, Object> params = new HashMap<>();
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            sb.append("This is a long template content. ");
        }
        final String template = sb.toString();
        assertEquals(template, exampleEngine.evaluate(template, params));
    }

    public void test_evaluate_withComplexParameterMap() {
        final Map<String, Object> params = new HashMap<>();
        params.put("string", "value");
        params.put("number", 42);
        params.put("boolean", true);
        params.put("null", null);
        params.put("map", new HashMap<String, String>());
        assertEquals("complex template", exampleEngine.evaluate("complex template", params));
    }

    public void test_getName_consistency() {
        assertEquals("example", exampleEngine.getName());
        assertEquals("example", exampleEngine.getName());
        assertEquals("example", exampleEngine.getName());
    }

    public void test_getName_returnsConstant() {
        final String name1 = exampleEngine.getName();
        final String name2 = exampleEngine.getName();
        assertSame(name1, name2);
    }

    public void test_constructor_initialization() {
        final ExampleEngine engine = new ExampleEngine();
        assertNotNull(engine);
        assertEquals("example", engine.getName());
    }

    public void test_multipleInstances_independence() {
        final ExampleEngine engine1 = new ExampleEngine();
        final ExampleEngine engine2 = new ExampleEngine();

        assertNotSame(engine1, engine2);
        assertEquals(engine1.getName(), engine2.getName());

        final Map<String, Object> params = new HashMap<>();
        assertEquals("test", engine1.evaluate("test", params));
        assertEquals("test", engine2.evaluate("test", params));
    }

    public void test_evaluate_preservesTemplateIntegrity() {
        final Map<String, Object> params = new HashMap<>();
        params.put("modify", "attempt");

        final String originalTemplate = "original template";
        final Object result = exampleEngine.evaluate(originalTemplate, params);

        assertEquals(originalTemplate, result);
        assertTrue(result instanceof String);
        assertEquals(String.class, result.getClass());
    }

    public void test_evaluate_withWhitespaceOnly() {
        final Map<String, Object> params = new HashMap<>();
        assertEquals(" ", exampleEngine.evaluate(" ", params));
        assertEquals("  ", exampleEngine.evaluate("  ", params));
        assertEquals("\t", exampleEngine.evaluate("\t", params));
        assertEquals("\n", exampleEngine.evaluate("\n", params));
    }

    public void test_evaluate_returnTypeConsistency() {
        final Map<String, Object> params = new HashMap<>();
        final Object result = exampleEngine.evaluate("test", params);
        assertTrue(result instanceof String);
        assertEquals("test", result);
    }
}
