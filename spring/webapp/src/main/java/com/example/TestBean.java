package com.example;

public class TestBean {
  private String name;

  TestBean(String name) {
    updateName(name);
  }

  @Override
  public String toString() {
    return name;
  }

  void updateName(String name) {
    this.name = name;
  }
}
