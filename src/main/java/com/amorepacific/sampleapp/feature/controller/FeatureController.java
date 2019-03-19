package com.amorepacific.sampleapp.feature.controller;

import com.amorepacific.sampleapp.common.dto.ErrorDetail;
import com.amorepacific.sampleapp.common.exception.custom.BadRequestException;
import com.amorepacific.sampleapp.common.exception.wrapper.BadRequest;
import com.amorepacific.sampleapp.feature.dto.FeatureRequest;
import com.amorepacific.sampleapp.feature.model.Feature;
import com.google.common.collect.Lists;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/features")
public class FeatureController {

  @GetMapping(value = "")
  public List<Feature> getFeatures(@RequestParam(value = "size") int size, @RequestParam(value = "page") int page) {

    Feature feature = new Feature();
    feature.setId(1);
    feature.setName("feature 1");
    feature.setDescription("description feature 1");
    feature.setDate(LocalDateTime.now());

    List<Feature> features = Lists.newArrayList();

    logger.debug("size: {}, page: {}", size, page);

    return features;
  }

  @PostMapping(value = "")
  @ResponseStatus(HttpStatus.CREATED)
  public Feature saveFeature(@Validated @RequestBody FeatureRequest featureRequest) {

    Feature feature = featureRequest.convert();

    return feature;
  }

  @GetMapping(value = "/{id}")
  public Feature getFeatures(@PathVariable(name = "id") int id) {

    Feature feature = new Feature();
    feature.setId(1);
    feature.setName("feature 1");
    feature.setDescription("description feature 1");
    feature.setDate(LocalDateTime.now());

    return feature;
  }


  @PutMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Feature updateFeature(@PathVariable(name = "id") int id, @Validated @RequestBody FeatureRequest featureRequest) {

    featureRequest.setId(id);
    Feature feature = featureRequest.convert();
//    feature.setId(id);

    return feature;
  }

//  @DeleteMapping(value = "/{id}")
//  @ResponseStatus(HttpStatus.NO_CONTENT)
//  public void deleteFeature(@PathVariable(name = "id") int id) {
//
//    // delete action
//  }


  @GetMapping(value = "/custom_bad_request")
  public Feature throwBadRequest() {
    ErrorDetail errorDetail = ErrorDetail.builder()
        .field("field명")
        .message("field명은 낫널이에여")
        .value(null)
        .build();

    List<ErrorDetail> errorDetails = Lists.newArrayList();
    errorDetails.add(errorDetail);

    throw new BadRequestException(errorDetails);
  }

  @GetMapping(value = "/runtime_error")
  public Feature throwRuntimeError() {

    throw new RuntimeException();
  }
}

