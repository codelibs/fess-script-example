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

import java.util.Collections;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.codelibs.core.lang.StringUtil;
import org.codelibs.fess.script.AbstractScriptEngine;

/**
 * Example script engine that demonstrates how to implement a custom script engine for Fess.
 *
 * <p>A script engine takes a {@code template} string plus a {@code paramMap} of named values
 * and produces a result. Fess invokes engines through {@link AbstractScriptEngine}: this example
 * registers itself under the name {@code "example"} (see {@link #getName()}) and is wired into the
 * DI container by {@code fess_se++.xml}.</p>
 *
 * <p>To keep the example easy to follow, this engine performs simple {@code ${key}} placeholder
 * substitution: every {@code ${key}} occurrence in the template is replaced with the matching
 * value from {@code paramMap}. For example, the template {@code "Hello ${name}"} with
 * {@code {name=Fess}} evaluates to {@code "Hello Fess"}.</p>
 *
 * <p>When you build your own engine, the two things you customize are the evaluation logic in
 * {@link #evaluate(String, Map)} and the engine identifier in {@link #getName()}. The real
 * {@code GroovyEngine} in Fess follows the same shape but evaluates full Groovy scripts.</p>
 */
public class ExampleEngine extends AbstractScriptEngine {

    /** Matches {@code ${key}} placeholders where {@code key} is one or more non-"}" characters. */
    private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\$\\{([^}]+)\\}");

    /**
     * Creates a new instance of ExampleEngine.
     */
    public ExampleEngine() {
        super();
    }

    /**
     * Evaluates the given template by substituting {@code ${key}} placeholders with values
     * from the parameter map.
     *
     * <p>Null-safety mirrors {@code GroovyEngine}: a blank template returns {@code null}, and a
     * {@code null} parameter map is treated as an empty map. A {@code ${key}} whose key is missing
     * from the map (or maps to {@code null}) is left untouched in the output, so unresolved
     * placeholders remain visible rather than being silently dropped.</p>
     *
     * <p>CUSTOMIZE HERE: replace this substitution logic with whatever evaluation your engine
     * needs (a real scripting language, an expression evaluator, an external template engine,
     * etc.).</p>
     *
     * @param template the template string to evaluate (null-safe, returns null if blank)
     * @param paramMap the parameters available to the template (null-safe, treated as empty if null)
     * @return the evaluated string, or null if the template is blank
     */
    @Override
    public Object evaluate(final String template, final Map<String, Object> paramMap) {
        if (StringUtil.isBlank(template)) {
            return null;
        }

        final Map<String, Object> safeParamMap = paramMap != null ? paramMap : Collections.emptyMap();

        final Matcher matcher = PLACEHOLDER_PATTERN.matcher(template);
        final StringBuilder buffer = new StringBuilder();
        while (matcher.find()) {
            final String key = matcher.group(1);
            final Object value = safeParamMap.get(key);
            // Leave the original "${key}" in place when the key is missing or null.
            final String replacement = value != null ? value.toString() : matcher.group();
            matcher.appendReplacement(buffer, Matcher.quoteReplacement(replacement));
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }

    /**
     * Returns the name of this script engine.
     *
     * <p>CUSTOMIZE HERE: this is the identifier the engine is registered and looked up under
     * (e.g. via {@code ScriptEngineFactory.getScriptEngine("example")}). Change it to your own
     * engine's unique name.</p>
     *
     * @return the name "example" that identifies this script engine
     */
    @Override
    protected String getName() {
        return "example";
    }

}
