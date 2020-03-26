package com.kokozzang.sampleapp.feature.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeaturesRequestParams {

  @NotNull
  @Min(1)
  private Integer page;

  private Integer size = 10;

}
