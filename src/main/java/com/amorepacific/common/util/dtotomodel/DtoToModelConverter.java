package com.amorepacific.common.util.dtotomodel;

import org.modelmapper.ModelMapper;

public class DtoToModelConverter {


  private final static ModelMapper modelMapper = new ModelMapper();


  private DtoToModelConverter() {

  }

  public static <T> T convert(Object dto, Class<T> modelClass) {
    return modelMapper.map(dto, modelClass);

  }

}
