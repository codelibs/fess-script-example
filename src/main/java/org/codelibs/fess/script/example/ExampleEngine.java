package org.codelibs.fess.script.example;

import java.util.Map;

import org.codelibs.fess.script.AbstractScriptEngine;

public class ExampleEngine extends AbstractScriptEngine {

    @Override
    public Object evaluate(String template, Map<String, Object> paramMap) {
         return template;
    }

    @Override
    protected String getName() {
        return "example";
    }

}
