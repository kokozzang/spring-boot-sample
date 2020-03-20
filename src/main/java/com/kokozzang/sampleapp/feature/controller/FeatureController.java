package com.kokozzang.sampleapp.feature.controller;

import com.kokozzang.common.dto.ErrorDetail;
import com.kokozzang.common.exception.custom.BadRequestException;
import com.kokozzang.sampleapp.feature.dto.FeatureRequest;
import com.kokozzang.sampleapp.feature.dto.FeaturesRequestParams;
import com.kokozzang.sampleapp.feature.dto.UpdateFeaturePathVariables;
import com.kokozzang.sampleapp.feature.model.Feature;
import com.kokozzang.sampleapp.feature.service.FeatureService;
import com.google.common.collect.Lists;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/features")
public class FeatureController {

  private final FeatureService featureService;

  public FeatureController(FeatureService featureService) {
    this.featureService = featureService;
  }

//  @GetMapping(value = "")
//  public List<Feature> getFeatures(@RequestParam(value = "size") int size, @RequestParam(value = "page") int page) {
//
//    logger.debug("size: {}, page: {}", size, page);
//
//    return featureService.getFeatures(page, size);
//  }

  @PostMapping(value = "")
  @ResponseStatus(HttpStatus.CREATED)
  public Feature saveFeature(@Validated @RequestBody FeatureRequest featureRequest) {

    Feature feature = featureRequest.convert();

    return featureService.saveFeature(feature);
  }


  @GetMapping(value = "/{id}")
  public Feature getFeature(@PathVariable(name = "id") @Min(1) int id) {
    return featureService.getFeature(id);
  }

  @GetMapping(value = "")
  public List<Feature> getFeatures(@Validated FeaturesRequestParams featuresRequestParams) {

    logger.debug("size: {}, page: {}", featuresRequestParams.getSize(), featuresRequestParams.getPage());

    return featureService.getFeatures(featuresRequestParams.getPage(), featuresRequestParams.getSize());
  }


//  @PutMapping(value = "/{id}")
//  @ResponseStatus(HttpStatus.OK)
//  public Feature updateFeature(@PathVariable(name = "id") int id, @Validated @RequestBody FeatureRequest featureRequest) {
//
//    Feature feature = featureRequest.convert();
//    feature.setId(id);
//
//    return featureService.getFeature(id);
//  }

  @PutMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Feature updateFeature(UpdateFeaturePathVariables updateFeaturePathVariables, @Validated @RequestBody FeatureRequest featureRequest) {

    Feature feature = featureRequest.convert();

    return featureService.updateFeature(updateFeaturePathVariables.getId(), feature);
  }

  @DeleteMapping(value = "/{id}")
  public void deleteFeature(@PathVariable(name = "id") int id) {
    featureService.removeFeature(id);
  }


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

