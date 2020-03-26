package com.kokozzang.sampleapp.feature;

import static org.hamcrest.Matchers.hasItem;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.kokozzang.sampleapp.feature.controller.FeatureController;
import com.kokozzang.sampleapp.feature.model.Feature;
import com.kokozzang.sampleapp.feature.service.FeatureService;
import java.util.List;
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
@DisplayName("GET /features?page&size")
public class FeatureControllerGetFeaturesTest {

  private final static Logger logger = LoggerFactory.getLogger(FeatureControllerGetFeaturesTest.class);

  private static List<Feature> features;


  @MockBean
  private FeatureService featureService;

  @Autowired
  private MockMvc mockMvc;


  @BeforeAll
  static void setUp() {
    logger.info("@BeforeAll");

    Feature feature1 = Feature.builder()
        .id(1)
        .name("feature 1")
        .description("feature 설명이라능")
        .build();

    Feature feature2 = Feature.builder()
        .id(2)
        .name("feature 2")
        .description("feature 설명이라능")
        .build();

    features = List.of(feature1, feature2);
  }


  @BeforeEach
  void setFeatureRequest() {
    logger.info("@BeforeEach");

  }

  @Test
  @DisplayName("200")
  void saveSuccessTest() throws Exception {
    MockHttpServletRequestBuilder mockRequestBuilder = get("/features")
        .queryParam("page", String.valueOf(1))
        .queryParam("size", String.valueOf(10))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON);

    // given
    given(featureService.getFeatures(anyInt(),anyInt())).willReturn(features);

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
    class QueryParam_page {

      @Test
      void 데이터_타입이_다르다() throws Exception {
        MockHttpServletRequestBuilder mockRequestBuilder = get("/features")
            .queryParam("page", "문자열")
            .queryParam("size", String.valueOf(10))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON);

        // when
        ResultActions actions = mockMvc.perform(
            mockRequestBuilder
        )
            .andDo(print());

        // then
        actions
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.details[*].field", hasItem("page")));
      }

      @Test
      void null_이다() throws Exception {
        MockHttpServletRequestBuilder mockRequestBuilder = get("/features")
            .queryParam("size", String.valueOf(10))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON);

        // when
        ResultActions actions = mockMvc.perform(
            mockRequestBuilder
        )
            .andDo(print());

        // then
        actions
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.details[*].field", hasItem("page")));
      }

      @Test
      void 빈문자열_이다() throws Exception {
        MockHttpServletRequestBuilder mockRequestBuilder = get("/features")
            .queryParam("page", "")
            .queryParam("size", String.valueOf(10))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON);

        // when
        ResultActions actions = mockMvc.perform(
            mockRequestBuilder
        )
            .andDo(print());

        // then
        actions
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.details[*].field", hasItem("page")));
      }

      @Test
      void 최소값보다_작다() throws Exception {
        MockHttpServletRequestBuilder mockRequestBuilder = get("/features")
            .queryParam("page", String.valueOf(0))
            .queryParam("size", String.valueOf(10))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON);

        // when
        ResultActions actions = mockMvc.perform(
            mockRequestBuilder
        )
            .andDo(print());

        // then
        actions
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.details[*].field", hasItem("page")));
      }
    }


  }

}
