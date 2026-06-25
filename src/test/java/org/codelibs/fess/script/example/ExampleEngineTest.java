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

import org.codelibs.fess.script.ScriptEngine;
import org.codelibs.fess.util.ComponentUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

public class ExampleEngineTest extends UnitScriptTestCase {

    private ExampleEngine exampleEngine;

    @Override
    public void setUp(final TestInfo testInfo) throws Exception {
        super.setUp(testInfo);
        exampleEngine = new ExampleEngine();
    }

    @Test
    public void test_evaluate_substitutesPlaceholders() {
        final Map<String, Object> params = new HashMap<>();
        params.put("name", "Fess");
        assertEquals("Hello Fess", exampleEngine.evaluate("Hello ${name}", params));
    }

    @Test
    public void test_evaluate_substitutesMultipleAndNonStringValues() {
        final Map<String, Object> params = new HashMap<>();
        params.put("user", "alice");
        params.put("count", 3);
        assertEquals("alice has 3 items", exampleEngine.evaluate("${user} has ${count} items", params));
    }

    @Test
    public void test_evaluate_missingKeyIsLeftUntouched() {
        final Map<String, Object> params = new HashMap<>();
        params.put("known", "value");
        // The unknown placeholder remains visible instead of being dropped.
        assertEquals("value and ${unknown}", exampleEngine.evaluate("${known} and ${unknown}", params));
    }

    @Test
    public void test_evaluate_nullValueIsLeftUntouched() {
        final Map<String, Object> params = new HashMap<>();
        params.put("key", null);
        assertEquals("${key}", exampleEngine.evaluate("${key}", params));
    }

    @Test
    public void test_evaluate_noPlaceholdersReturnsTemplate() {
        assertEquals("plain text", exampleEngine.evaluate("plain text", new HashMap<>()));
    }

    @Test
    public void test_evaluate_nullParamMapTreatedAsEmpty() {
        assertEquals("${name}", exampleEngine.evaluate("${name}", null));
    }

    @Test
    public void test_evaluate_blankTemplateReturnsNull() {
        final Map<String, Object> params = new HashMap<>();
        assertNull(exampleEngine.evaluate(null, params));
        assertNull(exampleEngine.evaluate("", params));
        assertNull(exampleEngine.evaluate("   ", params));
    }

    @Test
    public void test_getName() {
        assertEquals("example", exampleEngine.getName());
    }

    /**
     * Demonstrates how an installed engine is consumed: it self-registers under its name and is
     * then retrieved from the factory. {@code register()} adds the engine to the
     * {@code scriptEngineFactory} (provided by {@code test_app.xml}) under {@link
     * ExampleEngine#getName()}.
     */
    @Test
    public void test_registerAndLookupByName() {
        exampleEngine.register();

        final ScriptEngine engine = ComponentUtil.getScriptEngineFactory().getScriptEngine("example");
        assertNotNull(engine);

        final Map<String, Object> params = new HashMap<>();
        params.put("name", "world");
        assertEquals("hi world", engine.evaluate("hi ${name}", params));
    }
}
