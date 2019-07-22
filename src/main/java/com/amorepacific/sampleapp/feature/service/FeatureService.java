package com.amorepacific.sampleapp.feature.service;

import com.amorepacific.sampleapp.feature.model.Feature;
import com.google.common.collect.Lists;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class FeatureService {

  public Feature saveFeature(Feature feature) {
    return feature;
  }

  public Feature getFeature(int id) {
    return new Feature();
  }

  public List<Feature> getFeatures(int page, int size) {
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

    return features;
  }

}
