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

import java.util.Map;

import org.codelibs.fess.script.AbstractScriptEngine;

/**
 * Example script engine implementation that demonstrates how to create custom script engines for Fess.
 * This implementation simply returns the template string unchanged without any processing.
 */
public class ExampleEngine extends AbstractScriptEngine {

    /**
     * Creates a new instance of ExampleEngine.
     */
    public ExampleEngine() {
        super();
    }

    /**
     * Evaluates the given template with the provided parameter map.
     * In this example implementation, the template is returned unchanged without any processing.
     *
     * @param template the template string to evaluate
     * @param paramMap the parameter map containing variables for template evaluation
     * @return the template string unchanged
     */
    @Override
    public Object evaluate(final String template, final Map<String, Object> paramMap) {
        return template;
    }

    /**
     * Returns the name of this script engine.
     *
     * @return the name "example" that identifies this script engine
     */
    @Override
    protected String getName() {
        return "example";
    }

}
