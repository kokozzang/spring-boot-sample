package com.kokozzang.sampleapp.feature;

import static org.hamcrest.Matchers.hasItem;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kokozzang.sampleapp.feature.controller.FeatureController;
import com.kokozzang.sampleapp.feature.dto.FeatureRequest;
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
@DisplayName("features 생성 test")
public class FeatureControllerCreateTest {

  private final static Logger logger = LoggerFactory.getLogger(FeatureControllerCreateTest.class);
  private static ObjectMapper objectMapper;

  @MockBean
  private FeatureService featureService;

  @Autowired
  private MockMvc mockMvc;

  FeatureRequest featureRequest;

  MockHttpServletRequestBuilder mockRequestBuilder;


  @BeforeAll
  static void setUp() {
    logger.info("@BeforeAll");
    objectMapper = new ObjectMapper();
  }


  @BeforeEach
  void setFeatureRequest() {
    logger.info("@BeforeEach");
    featureRequest = new FeatureRequest();
    featureRequest.setName("feature 1");
    featureRequest.setDescription("feature 설명이라능");

    mockRequestBuilder = post("/features")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON);
  }

  @Test
  @DisplayName("생성 성공")
  void saveSuccessTest() throws Exception {
    Feature feature = featureRequest.convert();

    Feature savedFeature = Feature.builder()
        .id(1)
        .name(feature.getName())
        .description(feature.getDescription())
        .build();

    // given
    given(featureService.saveFeature(any(Feature.class))).willReturn(savedFeature);

    // when
    ResultActions actions = mockMvc.perform(
        mockRequestBuilder.content(objectMapper.writeValueAsString(featureRequest))
    )
        .andDo(print());

    // then
    actions
        .andExpect(status().is2xxSuccessful());
  }


  @Nested
  @DisplayName("400 밸리데이션: name")
  class Name {
    @Test
    @DisplayName("null 일때")
    void null_일때() throws Exception {

      featureRequest.setName(null);

      // when
      ResultActions actions = mockMvc.perform(
          mockRequestBuilder.content(objectMapper.writeValueAsString(featureRequest))
      )
          .andDo(print());

      // then
      actions
          .andExpect(status().isBadRequest())
          .andExpect(jsonPath("$.details[*].field", hasItem("name")));

    }

    @Test
    @DisplayName("짧다")
    void 짧음() throws Exception {
      featureRequest.setName("짧아");

      // when
      ResultActions actions = mockMvc.perform(
          mockRequestBuilder.content(objectMapper.writeValueAsString(featureRequest))
      )
          .andDo(print());

      // then
      actions
          .andExpect(status().isBadRequest())
          .andExpect(jsonPath("$.details[*].field", hasItem("name")));

      Assertions.assertTrue(featureRequest.getName().length() < 3);
    }

    @Test
    @DisplayName("길다")
    void 길다() throws Exception {
      featureRequest.setName("길다길어길다길어길다길어길다길어길다길어");

      // when
      ResultActions actions = mockMvc.perform(
          mockRequestBuilder.content(objectMapper.writeValueAsString(featureRequest))
      )
          .andDo(print());

      // then
      actions
          .andExpect(status().isBadRequest())
          .andExpect(jsonPath("$.details[*].field", hasItem("name")));

      Assertions.assertTrue(featureRequest.getName().length() > 10);
    }
  }

  @Nested
  @DisplayName("400 밸리데이션: description")
  class Description {

    @Test
    @DisplayName("null 일때")
    void null_일때() throws Exception {

      featureRequest.setDescription(null);

      // when
      ResultActions actions = mockMvc.perform(
          mockRequestBuilder.content(objectMapper.writeValueAsString(featureRequest))
      )
          .andDo(print());

      // then
      actions
          .andExpect(status().isBadRequest())
          .andExpect(jsonPath("$.details[*].field", hasItem("description")));

    }
  }

}
