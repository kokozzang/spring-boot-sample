package com.kokozzang.sampleapp.restdoc.utils.snippet;

import java.util.Map;
import org.springframework.restdocs.operation.Operation;
import org.springframework.restdocs.snippet.TemplatedSnippet;

public class ApiDescriptionSnippet extends TemplatedSnippet {

    private static final String snippetName = "api-description";


    public ApiDescriptionSnippet(Map<String, Object> attributes) {
        super(snippetName, attributes);
    }

    @Override
    protected Map<String, Object> createModel(Operation operation) {
        return getAttributes();
    }
}
