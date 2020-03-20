package com.kokozzang.sampleapp.feature;

import com.kokozzang.sampleapp.feature.controller.FeatureController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest(FeatureController.class)
public class FeatureControllerTest {

  @DisplayName("Feature 생성")
  @Test
  void saveFeatureTest() {

  }





  @DisplayName("Feature 조회")
  @Test
  void getFeatureTest() {

  }

  @DisplayName("Feature 리스트 조회")
  @Test
  void getFeaturesTest() {

  }
}
