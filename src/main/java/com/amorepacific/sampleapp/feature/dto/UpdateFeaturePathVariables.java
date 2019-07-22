package com.amorepacific.sampleapp.feature.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateFeaturePathVariables {

  @NotNull
  @Min(1)
  private Integer id;

}
