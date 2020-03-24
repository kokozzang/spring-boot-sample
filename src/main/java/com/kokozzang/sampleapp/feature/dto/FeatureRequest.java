package com.kokozzang.sampleapp.feature.dto;

import com.kokozzang.sampleapp.feature.model.Feature;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FeatureRequest {
  public final static int NAME_MIN_LENGTH = 3;
  public final static int NAME_MAX_LENGTH = 10;


//  @JsonProperty(access = Access.READ_ONLY)
  private Integer id;

  @NotNull
  @Size(min = NAME_MIN_LENGTH, max = NAME_MAX_LENGTH)
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
