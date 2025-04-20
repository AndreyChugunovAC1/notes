package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("inv player")
public class MusicPlayer {
  private Music music;
  private String where;
  private int volume;
  private static int count = 0;

  MusicPlayer() {
    music = null;
  }

  @Autowired
  MusicPlayer(@Qualifier("inv classic") Music music) {
    this.music = music;
  }
 
  void initMethod() {
    ++count;
    System.out.println("I was initialized #" + count);
  }

  public void setMusic(Music music) {
    this.music = music;
  }

  public void setVolume(int volume) {
    this.volume = volume;
  }

  public void setWhere(String where) {
    this.where = where;
  }

  public Music getMusic() {
    return music;
  }

  public int getVolume() {
    return volume;
  }

  public String getWhere() {
    return where;
  }

  public void playMusic() {
    System.out.println("Playing " + music.getSong());
  }
}
