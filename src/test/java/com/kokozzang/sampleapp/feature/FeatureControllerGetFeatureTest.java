package com.kokozzang.sampleapp.feature;

import static org.hamcrest.Matchers.hasItem;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jayway.jsonpath.JsonPath;
import com.kokozzang.sampleapp.feature.controller.FeatureController;
import com.kokozzang.sampleapp.feature.model.Feature;
import com.kokozzang.sampleapp.feature.service.FeatureService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@WebMvcTest(FeatureController.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@DisplayName("GET /feature/{id}")
public class FeatureControllerGetFeatureTest {

  private final static Logger logger = LoggerFactory.getLogger(FeatureControllerGetFeatureTest.class);


  @MockBean
  private FeatureService featureService;

  @Autowired
  private MockMvc mockMvc;


  @BeforeAll
  static void setUp() {
    logger.info("@BeforeAll");
  }


  @BeforeEach
  void setFeatureRequest() {
    logger.info("@BeforeEach");
  }

  @Test
  @DisplayName("200")
  void saveSuccessTest() throws Exception {
    MockHttpServletRequestBuilder mockRequestBuilder = get("/features/{id}", 1)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON);

    Feature feature = Feature.builder()
        .id(1)
        .name("feature 1")
        .description("feature 설명이라능")
        .build();

    // given
    given(featureService.getFeature(anyInt())).willReturn(feature);

    // when
    ResultActions actions = mockMvc.perform(
        mockRequestBuilder
    )
        .andDo(print());

    // then
    actions
        .andExpect(status().is2xxSuccessful());
  }


  @Nested
  @DisplayName("400")
  class BadRequest {

    @Nested
    class PathVariable_id {
      @Test
      void 데이터_타입이_다를때() throws Exception {
        MockHttpServletRequestBuilder mockRequestBuilder = get("/features/{id}", "문자열")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON);

        // when
        ResultActions actions = mockMvc.perform(
            mockRequestBuilder
        )
            .andDo(print());

        // then
        actions
            .andExpect(status().isBadRequest());
      }
    }


  }

}
