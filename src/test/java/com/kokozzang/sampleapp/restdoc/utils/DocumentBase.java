package com.kokozzang.sampleapp.restdoc.utils;


import static com.kokozzang.sampleapp.restdoc.utils.ApiDocumentUtils.getDocumentRequest;
import static com.kokozzang.sampleapp.restdoc.utils.ApiDocumentUtils.getDocumentResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public abstract class DocumentBase {

  protected MockMvc mockMvc;

  protected RestDocumentationResultHandler document;

  @BeforeEach
  public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
    this.document = document(
        "{ClassName}/{methodName}/",
        getDocumentRequest(),
        getDocumentResponse()
    );

    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        .apply(documentationConfiguration(restDocumentation)
            .uris()
//            .withScheme("https")
//            .withHost("sadfasfdocs.api.com")
//            .withPort(443)
        )
        .alwaysDo(this.document)
        .build();
  }

}
