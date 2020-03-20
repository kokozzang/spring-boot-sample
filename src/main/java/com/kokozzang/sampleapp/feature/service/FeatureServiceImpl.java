package com.kokozzang.sampleapp.feature.service;

import com.kokozzang.sampleapp.feature.model.Feature;
import com.google.common.collect.Lists;
import com.kokozzang.sampleapp.feature.repository.FeatureRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeatureServiceImpl implements FeatureService {

  private final FeatureRepository featureRepository;

  public FeatureServiceImpl(FeatureRepository featureRepository) {
    this.featureRepository = featureRepository;
  }


  @Override
  public Feature saveFeature(Feature feature) {
    return featureRepository.saveFeature(feature);
  }

  @Override
  public Feature getFeature(int id) {
    return featureRepository.getFeature(id);
  }

  @Override
  public List<Feature> getFeatures(int page, int size) {
    return featureRepository.getFeatures(page, size);
  }

  @Override
  public Feature updateFeature(int id, Feature feature) {
    return featureRepository.updateFeature(id, feature);
  }

  @Override
  public void removeFeature(int id) {

  }

}
