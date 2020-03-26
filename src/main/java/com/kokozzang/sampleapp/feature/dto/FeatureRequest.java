package com.kokozzang.sampleapp.feature.dto;

import com.kokozzang.sampleapp.feature.model.Feature;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FeatureRequest {
//  @JsonProperty(access = Access.READ_ONLY)
  private Integer id;

  @NotNull
  @Size(min = 3, max = 10)
  private String name;

  @NotNull
  private String description;


  public Feature convert() {
    return Feature.builder()
        .name(name)
        .description(description)
        .build();
  }

}
