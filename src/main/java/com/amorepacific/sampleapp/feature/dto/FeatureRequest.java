package com.amorepacific.sampleapp.feature.dto;

import com.amorepacific.common.util.dtotomodel.DtoToModelConverter;
import com.amorepacific.sampleapp.feature.model.Feature;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class FeatureRequest {

  private static final long serialVersionUID = -4184036097314115773L;

  @JsonProperty(access = Access.READ_ONLY)
  private Integer id;

  @NotNull
  @Size(min = 3, max = 10)
  private String name;

  @NotNull
  private String description;

  @Max(10)
  @Min(0)
  @NotNull
  private Integer amount;

  public Feature convert() {
    return DtoToModelConverter.convert(this, Feature.class);
  }

}
