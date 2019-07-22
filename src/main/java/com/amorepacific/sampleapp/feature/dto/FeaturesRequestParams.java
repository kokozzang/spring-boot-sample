package com.amorepacific.sampleapp.feature.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FeaturesRequestParams {

  @NotNull
  @Min(1)
  private Integer page;

  private Integer size;

}
