package com.nasatrainedmonkeys.duolingowidget.model;

public class DuolingoUser {

  private int id;
  private String username;
  private DuolingoLanguage language;
  private byte[] avatar;

  public byte[] getAvatar() {
    return avatar;
  }

  public void setAvatar(byte[] avatar) {
    this.avatar = avatar;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public DuolingoLanguage getLanguage() {
    return language;
  }

  public void setLanguage(DuolingoLanguage language) {
    this.language = language;
  }

}
