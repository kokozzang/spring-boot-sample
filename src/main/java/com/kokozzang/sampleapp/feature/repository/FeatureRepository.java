package com.kokozzang.sampleapp.feature.repository;


import com.kokozzang.sampleapp.feature.model.Feature;
import java.util.List;

public interface FeatureRepository {

  Feature saveFeature(Feature feature);

  Feature getFeature(int id);

  List<Feature> getFeatures(int page, int size);

  Feature updateFeature(int id, Feature feature);

  void removeFeature(Feature feature);

}
