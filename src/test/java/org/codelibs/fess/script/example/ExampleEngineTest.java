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

    // Comprehensive test cases for evaluating various input scenarios

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

    // Additional comprehensive test cases for better coverage

    public void test_evaluate_withUnicodeCharacters() {
        final Map<String, Object> params = new HashMap<>();
        // Test various Unicode characters including emojis
        final String template = "Hello 🌍 World 🚀 Test 😀";
        assertEquals(template, exampleEngine.evaluate(template, params));
    }

    public void test_evaluate_withVariousEmojiSequences() {
        final Map<String, Object> params = new HashMap<>();
        final String template = "👨‍👩‍👧‍👦 👍🏻 🏴󠁧󠁢󠁥󠁮󠁧󠁿";
        assertEquals(template, exampleEngine.evaluate(template, params));
    }

    public void test_evaluate_withMixedLanguages() {
        final Map<String, Object> params = new HashMap<>();
        final String template = "English 日本語 中文 한글 العربية Русский";
        assertEquals(template, exampleEngine.evaluate(template, params));
    }

    public void test_evaluate_withSQLInjectionLikeStrings() {
        final Map<String, Object> params = new HashMap<>();
        final String template = "'; DROP TABLE users; --";
        assertEquals(template, exampleEngine.evaluate(template, params));
    }

    public void test_evaluate_withXSSLikeStrings() {
        final Map<String, Object> params = new HashMap<>();
        final String template = "<script>alert('XSS')</script>";
        assertEquals(template, exampleEngine.evaluate(template, params));
    }

    public void test_evaluate_withHTMLEntities() {
        final Map<String, Object> params = new HashMap<>();
        final String template = "&lt;div&gt;&amp;&quot;&#39;&lt;/div&gt;";
        assertEquals(template, exampleEngine.evaluate(template, params));
    }

    public void test_evaluate_withPathTraversalLikeStrings() {
        final Map<String, Object> params = new HashMap<>();
        final String template = "../../etc/passwd";
        assertEquals(template, exampleEngine.evaluate(template, params));
    }

    public void test_evaluate_withControlCharacters() {
        final Map<String, Object> params = new HashMap<>();
        final String template = "Line1\r\nLine2\u0000Null\u0007Bell";
        assertEquals(template, exampleEngine.evaluate(template, params));
    }

    public void test_evaluate_withVeryLongString() {
        final Map<String, Object> params = new HashMap<>();
        final StringBuilder sb = new StringBuilder();
        // Create a string of 10MB
        for (int i = 0; i < 10000; i++) {
            sb.append("This is a very long string for testing memory handling. ");
        }
        final String template = sb.toString();
        assertEquals(template, exampleEngine.evaluate(template, params));
    }

    public void test_evaluate_consecutiveCalls() {
        final Map<String, Object> params = new HashMap<>();
        // Test that consecutive calls don't affect each other
        assertEquals("first", exampleEngine.evaluate("first", params));
        assertEquals("second", exampleEngine.evaluate("second", params));
        assertEquals("third", exampleEngine.evaluate("third", params));
    }

    public void test_evaluate_parameterMapImmutability() {
        final Map<String, Object> params = new HashMap<>();
        params.put("key1", "value1");
        params.put("key2", "value2");

        final String template = "test";
        final int originalSize = params.size();
        exampleEngine.evaluate(template, params);

        // Verify that the parameter map was not modified
        assertEquals(originalSize, params.size());
        assertEquals("value1", params.get("key1"));
        assertEquals("value2", params.get("key2"));
    }

    public void test_evaluate_withNullValuesInParameterMap() {
        final Map<String, Object> params = new HashMap<>();
        params.put("key1", null);
        params.put("key2", "value");
        params.put("key3", null);

        final String template = "test with nulls";
        assertEquals(template, exampleEngine.evaluate(template, params));
    }

    public void test_evaluate_withNestedMapsInParameters() {
        final Map<String, Object> params = new HashMap<>();
        final Map<String, Object> nestedMap = new HashMap<>();
        nestedMap.put("nested1", "value1");
        nestedMap.put("nested2", 123);
        params.put("outer", nestedMap);

        final String template = "nested test";
        assertEquals(template, exampleEngine.evaluate(template, params));
    }

    public void test_evaluate_withListsInParameters() {
        final Map<String, Object> params = new HashMap<>();
        final java.util.List<String> list = new java.util.ArrayList<>();
        list.add("item1");
        list.add("item2");
        list.add("item3");
        params.put("list", list);

        final String template = "list test";
        assertEquals(template, exampleEngine.evaluate(template, params));
    }

    public void test_evaluate_templateImmutability() {
        final Map<String, Object> params = new HashMap<>();
        final String originalTemplate = "original";
        final Object result = exampleEngine.evaluate(originalTemplate, params);

        // Verify that the returned value equals the original
        assertEquals(originalTemplate, result);
        // Verify that it's still a String
        assertTrue(result instanceof String);
    }

    public void test_evaluate_withRegexSpecialCharacters() {
        final Map<String, Object> params = new HashMap<>();
        final String template = ".*+?^${}()|[]\\";
        assertEquals(template, exampleEngine.evaluate(template, params));
    }

    public void test_evaluate_withJSONLikeString() {
        final Map<String, Object> params = new HashMap<>();
        final String template = "{\"key\": \"value\", \"number\": 123, \"nested\": {\"inner\": true}}";
        assertEquals(template, exampleEngine.evaluate(template, params));
    }

    public void test_evaluate_withXMLLikeString() {
        final Map<String, Object> params = new HashMap<>();
        final String template = "<?xml version=\"1.0\"?><root><element attr=\"value\">text</element></root>";
        assertEquals(template, exampleEngine.evaluate(template, params));
    }

    public void test_getName_notNull() {
        assertNotNull(exampleEngine.getName());
    }

    public void test_getName_notEmpty() {
        assertFalse(exampleEngine.getName().isEmpty());
    }

    public void test_getName_exactValue() {
        final String name = exampleEngine.getName();
        assertEquals("example", name);
        assertEquals(7, name.length());
    }

    public void test_multipleInstancesWithDifferentTemplates() {
        final ExampleEngine engine1 = new ExampleEngine();
        final ExampleEngine engine2 = new ExampleEngine();

        final Map<String, Object> params1 = new HashMap<>();
        final Map<String, Object> params2 = new HashMap<>();

        assertEquals("template1", engine1.evaluate("template1", params1));
        assertEquals("template2", engine2.evaluate("template2", params2));
    }

    public void test_evaluate_withBoundaryLengthStrings() {
        final Map<String, Object> params = new HashMap<>();

        // Test single character
        assertEquals("a", exampleEngine.evaluate("a", params));

        // Test two characters
        assertEquals("ab", exampleEngine.evaluate("ab", params));

        // Test exactly 255 characters
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 255; i++) {
            sb.append("x");
        }
        final String str255 = sb.toString();
        assertEquals(str255, exampleEngine.evaluate(str255, params));
    }

    public void test_evaluate_withBackslashCharacters() {
        final Map<String, Object> params = new HashMap<>();
        final String template = "C:\\Users\\Test\\file.txt";
        assertEquals(template, exampleEngine.evaluate(template, params));
    }

    public void test_evaluate_withQuotationMarks() {
        final Map<String, Object> params = new HashMap<>();
        final String template = "He said \"Hello\" and she replied 'Hi'";
        assertEquals(template, exampleEngine.evaluate(template, params));
    }

    public void test_evaluate_stateIndependence() {
        final Map<String, Object> params = new HashMap<>();
        // First call
        exampleEngine.evaluate("first call", params);
        // Second call should not be affected by the first
        final Object result = exampleEngine.evaluate("second call", params);
        assertEquals("second call", result);
    }

    public void test_evaluate_withNumbersOnlyString() {
        final Map<String, Object> params = new HashMap<>();
        final String template = "1234567890";
        assertEquals(template, exampleEngine.evaluate(template, params));
    }

    public void test_evaluate_withMixedContent() {
        final Map<String, Object> params = new HashMap<>();
        final String template = "Text123!@#$%^&*()_+-=[]{}|;':\",./<>?`~\n\t\r";
        assertEquals(template, exampleEngine.evaluate(template, params));
    }
}
