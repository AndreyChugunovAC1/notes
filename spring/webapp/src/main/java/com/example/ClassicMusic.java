package com.example;

import org.springframework.stereotype.Component;

@Component("inv classic")
public class ClassicMusic implements Music {
  @Override
  public String getSong() {
    return "Hungarian rapsody";
  }
}
