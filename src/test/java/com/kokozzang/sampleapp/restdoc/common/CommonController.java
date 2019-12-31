package com.kokozzang.sampleapp.restdoc.common;

import java.util.List;
import org.assertj.core.util.Lists;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonController {

  @GetMapping("/docs")
  public List<String> findAll() {

    return Lists.newArrayList("sdf");
  }
}
