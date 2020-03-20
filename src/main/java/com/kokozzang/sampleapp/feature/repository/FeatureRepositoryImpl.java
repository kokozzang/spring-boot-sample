package com.kokozzang.sampleapp.feature.repository;

import com.google.common.collect.Lists;
import com.kokozzang.sampleapp.feature.model.Feature;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class FeatureRepositoryImpl implements FeatureRepository {

  @Override
  public Feature saveFeature(Feature feature) {
    return feature;
  }

  @Override
  public Feature getFeature(int id) {
    return Feature.builder()
        .id(id)
        .name("feature" + id)
        .description("description feature " + id)
        .build();
  }

  @Override
  public List<Feature> getFeatures(int page, int size) {
    List<Feature> features = Lists.newArrayList();

    Feature feature1 = Feature.builder()
        .id(1)
        .name("feature" + 1)
        .description("description feature " + 1)
        .build();

    Feature feature2 = Feature.builder()
        .id(2)
        .name("feature" + 2)
        .description("description feature " + 2)
        .build();

    features.add(feature1);
    features.add(feature2);

    return features;
  }

  @Override
  public Feature updateFeature(int id, Feature feature) {
    return feature;
  }

  @Override
  public void removeFeature(Feature feature) {
    // feature 삭제
  }
}
