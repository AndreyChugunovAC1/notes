package com.example;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpring {
  public static void main(String[] args) {
    var context = new ClassPathXmlApplicationContext("applicationContext.xml");
    // Music music = context.getBean("musicBean", Music.class);
    // MusicPlayer mp = new MusicPlayer(music);

    MusicPlayer mp = context.getBean("inv player", MusicPlayer.class);
    mp.playMusic();

    context.close();

    // TestBean testBean = context.getBean("testBean", TestBean.class);
    // System.out.println(testBean);
  }
}
