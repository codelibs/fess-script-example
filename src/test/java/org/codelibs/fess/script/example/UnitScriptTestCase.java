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

import org.codelibs.fess.util.ComponentUtil;
import org.dbflute.utflute.lastaflute.LastaFluteTestCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.TestInfo;

/**
 * Minimal test base for the example plugin.
 *
 * <p>Fess core script tests (e.g. {@code GroovyEngineTest}) extend
 * {@code org.codelibs.fess.unit.UnitFessTestCase}. That class lives in the Fess
 * <em>test</em> sources, so it is published only in the Fess test-jar, which this plugin does
 * not depend on (the {@code org.codelibs.fess:fess} dependency is the main jar, {@code provided}
 * scope). Because {@code UnitFessTestCase} is therefore not on the plugin classpath, this plugin
 * keeps its own thin base that extends UTFlute's {@code LastaFluteTestCase} directly and provides
 * the same JUnit 4-style assertion bridges used across Fess tests.</p>
 */
public abstract class UnitScriptTestCase extends LastaFluteTestCase {

    @Override
    protected String prepareConfigFile() {
        return "test_app.xml";
    }

    @Override
    protected void tearDown(final TestInfo testInfo) throws Exception {
        ComponentUtil.setFessConfig(null);
        super.tearDown(testInfo);
    }

    // ===== Assert methods for JUnit 4/5 compatibility =====

    protected void fail(final String message) {
        Assertions.fail(message);
    }

    protected void assertTrue(final boolean condition) {
        Assertions.assertTrue(condition);
    }

    protected void assertTrue(final String message, final boolean condition) {
        Assertions.assertTrue(condition, message);
    }

    protected void assertFalse(final boolean condition) {
        Assertions.assertFalse(condition);
    }

    protected void assertFalse(final String message, final boolean condition) {
        Assertions.assertFalse(condition, message);
    }

    protected void assertEquals(final Object expected, final Object actual) {
        Assertions.assertEquals(expected, actual);
    }

    protected void assertEquals(final String message, final Object expected, final Object actual) {
        Assertions.assertEquals(expected, actual, message);
    }

    protected void assertNull(final Object object) {
        Assertions.assertNull(object);
    }

    protected void assertNotNull(final Object object) {
        Assertions.assertNotNull(object);
    }
}
