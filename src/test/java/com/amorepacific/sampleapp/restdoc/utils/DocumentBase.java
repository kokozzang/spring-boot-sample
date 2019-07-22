package com.amorepacific.sampleapp.restdoc.utils;


import static com.amorepacific.sampleapp.restdoc.utils.ApiDocumentUtils.getDocumentRequest;
import static com.amorepacific.sampleapp.restdoc.utils.ApiDocumentUtils.getDocumentResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
public abstract class DocumentBase {

  @Rule
  public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

  @Autowired
  public WebApplicationContext context;

  protected MockMvc mockMvc;

  protected RestDocumentationResultHandler document;

  @Before
  public void setUp() {
    this.document = document(
        "{ClassName}/{methodName}/",
        getDocumentRequest(),
        getDocumentResponse()
    );

    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
        .apply(documentationConfiguration(this.restDocumentation)
            .uris()
//            .withScheme("https")
//            .withHost("sadfasfdocs.api.com")
//            .withPort(443)
        )
        .alwaysDo(this.document)
        .build();
  }


  class GG {
    private String urlTemplate;

    private String httpMethod;
  }

}
