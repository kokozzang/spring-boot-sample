package com.kokozzang.sampleapp.feature.dto;

import com.kokozzang.common.util.dtotomodel.DtoToModelConverter;
import com.kokozzang.sampleapp.feature.model.Feature;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class FeatureRequest {

  private static final long serialVersionUID = -4184036097314115773L;

//  @JsonProperty(access = Access.READ_ONLY)
  private Integer id;

  @NotNull
  @Size(min = 3, max = 10)
  private String name;

  @NotNull
  private String description;

//  @Max(10)
//  @Min(0)
//  @NotNull
//  private Integer amount;

  public Feature convert() {
    return DtoToModelConverter.convert(this, Feature.class);
  }

}
