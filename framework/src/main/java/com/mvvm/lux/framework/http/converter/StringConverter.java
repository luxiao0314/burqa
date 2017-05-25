package com.mvvm.lux.framework.http.converter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class StringConverter implements Converter<ResponseBody, String> {

  public static final StringConverter INSTANCE = new StringConverter();

  @Override
  public String convert(ResponseBody value) throws IOException {
    return value.string();
  }
}