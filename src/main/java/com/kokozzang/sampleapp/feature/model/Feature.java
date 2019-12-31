package com.kokozzang.sampleapp.feature.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Feature implements Serializable {

  private static final long serialVersionUID = 696164566368452699L;

  private Integer id;

  private String name;

  private String description;

  private LocalDateTime date;

}
