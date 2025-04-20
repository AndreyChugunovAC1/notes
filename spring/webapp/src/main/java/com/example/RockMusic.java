package com.example;

import org.springframework.stereotype.Component;

@Component("inv rock")
public class RockMusic implements Music {
  @Override
  public String getSong() {
    return "Blue Öyster Cult - Perfect Water";
  }
}
