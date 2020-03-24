package com.kokozzang.sampleapp.feature.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class FeatureRequestValidationTest {

  private final static Logger logger = LoggerFactory.getLogger(FeatureRequestValidationTest.class);

  private Validator validator;

  @BeforeEach
  void setUp() {
    logger.info("beforeEach");
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @DisplayName("밸리데이션 성공")
  @Test
  void validationSuccessTest() {
    FeatureRequest featureRequest = new FeatureRequest();
    featureRequest.setId(1);
    featureRequest.setName("feature 1");
    featureRequest.setDescription("feature 설명이라능");

    Set<ConstraintViolation<FeatureRequest>> violations = validator.validate(featureRequest);
    assertTrue(violations.isEmpty());
  }

  @DisplayName("밸리데이션 실패: name is null")
  @Test
  void featureRequestTest() {
    FeatureRequest featureRequest = new FeatureRequest();
    featureRequest.setId(1);
    featureRequest.setDescription("feature 설명이라능");

    Set<ConstraintViolation<FeatureRequest>> violations = validator.validate(featureRequest);
    assertEquals(1, violations.size());

    ConstraintViolation<FeatureRequest> violation = violations.iterator().next();

    assertEquals("name", violation.getPropertyPath().toString());
    assertEquals(NotNull.class, violation.getConstraintDescriptor().getAnnotation().annotationType());
  }

}