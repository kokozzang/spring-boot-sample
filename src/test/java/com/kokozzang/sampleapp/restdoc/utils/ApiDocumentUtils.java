package com.kokozzang.sampleapp.restdoc.utils;


import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.removeHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.restdocs.snippet.Attributes.key;

import com.kokozzang.sampleapp.restdoc.utils.snippet.ApiDescriptionSnippet;
import java.util.Map;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.StringUtils;

public class ApiDocumentUtils {
  public static OperationRequestPreprocessor getDocumentRequest() {
    return preprocessRequest(
        removeHeaders("Host"),
        prettyPrint());
  }

  public static OperationResponsePreprocessor getDocumentResponse() {
    return preprocessResponse(
        prettyPrint()
    );
  }

  public static class ConstrainedFields {

    private final ConstraintDescriptions constraintDescriptions;

    public ConstrainedFields(Class<?> input) {
      this.constraintDescriptions = new ConstraintDescriptions(input);
    }

    public FieldDescriptor withPath(String path) {
      return fieldWithPath(path)
          .attributes(key("constraints").value(
              StringUtils.collectionToDelimitedString(this.constraintDescriptions.descriptionsForProperty(path), ". "))
          );
    }
  }

  public static class ConstrainedParameters {

    private final ConstraintDescriptions constraintDescriptions;

    public ConstrainedParameters(Class<?> input) {
      this.constraintDescriptions = new ConstraintDescriptions(input);
    }

    public ParameterDescriptor withPath(String path) {
      return parameterWithName(path)
          .attributes(key("constraints").value(
              StringUtils.collectionToDelimitedString(this.constraintDescriptions.descriptionsForProperty(path), ". "))
          );
    }
  }


  public static class RequestParameterHelper {

    private ParameterDescriptor descriptor;

    private JsonFieldType type;

    private String defaultValue = "";


    private RequestParameterHelper() {

    }

    public static RequestParameterHelper init() {
      return new RequestParameterHelper();
    }

    public RequestParameterHelper descriptor(ParameterDescriptor descriptor) {
      this.descriptor = descriptor;
      return this;
    }

    public RequestParameterHelper type(JsonFieldType type) {
      this.type = type;
      return this;
    }

    public RequestParameterHelper defaultValue(String defaultValue) {
      this.defaultValue = defaultValue;
      return this;
    }

    public ParameterDescriptor toDescriptor() {
      descriptor.attributes(key("type").value(type));
      descriptor.attributes(key("defaultValue").value(defaultValue));
      return descriptor;
    }
  }


  public static ApiDescriptionSnippet apiDescription(ResultActions resultActions, String description) {
    String urlTemplate = (String) resultActions.andReturn().getRequest()
        .getAttribute("org.springframework.web.servlet.HandlerMapping.bestMatchingPattern");

    String httpMethod = resultActions.andReturn().getRequest().getMethod();

    Map<String, Object> attributes = attributes(
        key("httpMethod").value(httpMethod),
        key("urlTemplate").value(urlTemplate),
        key("description").value(description)
    );

    return new ApiDescriptionSnippet(attributes);
  }


}
