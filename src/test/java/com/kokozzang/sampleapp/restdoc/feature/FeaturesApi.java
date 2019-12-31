package com.kokozzang.sampleapp.restdoc.feature;

import static com.kokozzang.sampleapp.restdoc.utils.ApiDocumentUtils.apiDescription;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.beneathPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.kokozzang.sampleapp.feature.controller.FeatureController;
import com.kokozzang.sampleapp.feature.dto.FeatureRequest;
import com.kokozzang.sampleapp.feature.dto.FeaturesRequestParams;
import com.kokozzang.sampleapp.feature.model.Feature;
import com.kokozzang.sampleapp.feature.service.FeatureService;
import com.kokozzang.sampleapp.restdoc.utils.ApiDocumentUtils.ConstrainedFields;
import com.kokozzang.sampleapp.restdoc.utils.ApiDocumentUtils.ConstrainedParameters;
import com.kokozzang.sampleapp.restdoc.utils.ApiDocumentUtils.RequestParameterHelper;
import com.kokozzang.sampleapp.restdoc.utils.DocumentBase;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(FeatureController.class)
public class FeaturesApi extends DocumentBase {

  @MockBean
  private FeatureService featureService;


  @Test
  public void saveFeature() throws Exception {

    FeatureRequest featureRequest = new FeatureRequest();
    featureRequest.setName("feature1");
    featureRequest.setDescription("자세한 설명은 생략한다.");

    Feature feature = featureRequest.convert();
    feature.setId(1);
    feature.setDate(LocalDateTime.now());

    ObjectMapper objectMapper = new ObjectMapper();

    given(featureService.saveFeature(any(Feature.class)))
        .willReturn(feature);

    ConstrainedFields fields = new ConstrainedFields(FeatureRequest.class);

    ResultActions resultActions = mockMvc.perform(
        post("/features")
            .content(objectMapper.writeValueAsString(featureRequest))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
    );

    resultActions
        .andExpect(status().isCreated())
        .andDo(print())
        .andDo(document.document(
            apiDescription(resultActions, "자세한 설명은 생략한다."),
            requestFields(
                fields.withPath("id").description("키"),
                fields.withPath("name").description("이름"),
                fields.withPath("description").description("설명")
            ),
            responseFields(

                beneathPath("data").withSubsectionId("data"),
                fieldWithPath("id").type(JsonFieldType.NUMBER).description("feature id"),
                fieldWithPath("name").type(JsonFieldType.STRING).description("feature name"),
                fieldWithPath("description").type(JsonFieldType.STRING).description("feature description"),
                fieldWithPath("date").type(JsonFieldType.STRING).description("created date")

            )
        ));
  }

  @Test
  public void getFeature() throws Exception {
    FeatureRequest featureRequest = new FeatureRequest();
    featureRequest.setName("feature1");
    featureRequest.setDescription("자세한 설명은 생략한다.");

    Feature feature = featureRequest.convert();
    feature.setId(1);
    feature.setDate(LocalDateTime.now());

    given(featureService.getFeature(1))
        .willReturn(feature);

    // when
    ResultActions resultActions = mockMvc.perform(
        get("/features/{id}", 1)
            .accept(MediaType.APPLICATION_JSON)
    );

    // then
    resultActions
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(document.document(
            apiDescription(resultActions, "자세한 설명은 생략한다."),
            pathParameters(
                parameterWithName("id").description("아이디")
            ),
            responseFields(
                beneathPath("data").withSubsectionId("data"),
                fieldWithPath("id").type(JsonFieldType.NUMBER).description("feature id"),
                fieldWithPath("name").type(JsonFieldType.STRING).description("feature name"),
                fieldWithPath("description").type(JsonFieldType.STRING).description("feature description"),
                fieldWithPath("date").type(JsonFieldType.STRING).description("created date")

            )
        ));
  }

  @Test
  public void getFeatures() throws Exception {

    List<Feature> features = Lists.newArrayList();

    Feature feature1 = new Feature();
    feature1.setId(1);
    feature1.setName("feature 1");
    feature1.setDescription("description feature 1");
    feature1.setDate(LocalDateTime.now());

    Feature feature2 = new Feature();
    feature2.setId(2);
    feature2.setName("feature 2");
    feature2.setDescription("description feature 2");
    feature2.setDate(LocalDateTime.now());

    features.add(feature1);
    features.add(feature2);

    given(featureService.getFeatures(2, 10))
        .willReturn(features);

    // when
    ResultActions resultActions = mockMvc.perform(
        get("/features?page={page}&size={size}", 2, 10)
            .accept(MediaType.APPLICATION_JSON)
    );

    ConstrainedParameters parameters = new ConstrainedParameters(FeaturesRequestParams.class);

    // then
    resultActions
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(document.document(
            apiDescription(resultActions, "자세한 설명은 생략한다."),
            requestParameters(
                RequestParameterHelper.init()
                    .descriptor(parameters.withPath("page").description("페이지")).type(JsonFieldType.NUMBER).toDescriptor(),
                RequestParameterHelper.init()
                    .descriptor(parameters.withPath("size").description("사이즈").optional()).type(JsonFieldType.NUMBER).defaultValue("1").toDescriptor()
            ),
            responseFields(
                beneathPath("data").withSubsectionId("data"),
                fieldWithPath("id").type(JsonFieldType.NUMBER).description("feature id"),
                fieldWithPath("name").type(JsonFieldType.STRING).description("feature name"),
                fieldWithPath("description").type(JsonFieldType.STRING).description("feature description"),
                fieldWithPath("date").type(JsonFieldType.STRING).description("created date")

            )
        ));
  }
}
