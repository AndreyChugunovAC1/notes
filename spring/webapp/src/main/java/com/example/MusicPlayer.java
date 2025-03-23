package com.example;

public class MusicPlayer {
  private Music music;
  private String where;
  private int volume;

  MusicPlayer() {
    music = null;
  }

  MusicPlayer(Music music) {
    this.music = music;
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
