package com.kokozzang.sampleapp.feature.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
public class Feature implements Serializable {

  private static final long serialVersionUID = 696164566368452699L;

  private Integer id;

  @Override
  public String toString() {
    return "Feature{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", date=" + date +
        '}';
  }

  private String name;

  private String description;

  private LocalDateTime date;

  @Builder
  public Feature(Integer id, String name, String description) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.date = LocalDateTime.now();
  }
}
