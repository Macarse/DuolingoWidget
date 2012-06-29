package com.nasatrainedmonkeys.duolingowidget.model;

public class DuolingoLanguage {
  private String name;

  private int level; // 8
  private int levelProgress; // 606
  private int levelLeft; // 94
  private int levelPercent; // 86 %
  private int levelPoints; // 700

  private int points; // 2106
  private int sentencesTranslated; // 26

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public int getLevelProgress() {
    return levelProgress;
  }

  public void setLevelProgress(int levelProgress) {
    this.levelProgress = levelProgress;
  }

  public int getLevelLeft() {
    return levelLeft;
  }

  public void setLevelLeft(int levelLeft) {
    this.levelLeft = levelLeft;
  }

  public int getLevelPercent() {
    return levelPercent;
  }

  public void setLevelPercent(int levelPercent) {
    this.levelPercent = levelPercent;
  }

  public int getLevelPoints() {
    return levelPoints;
  }

  public void setLevelPoints(int levelPoints) {
    this.levelPoints = levelPoints;
  }

  public int getPoints() {
    return points;
  }

  public void setPoints(int points) {
    this.points = points;
  }

  public int getSentencesTranslated() {
    return sentencesTranslated;
  }

  public void setSentencesTranslated(int sentencesTranslated) {
    this.sentencesTranslated = sentencesTranslated;
  }

  @Override
  public String toString() {
    return "DuolingoLanguage [name=" + name + ", level=" + level
        + ", levelProgress=" + levelProgress + ", levelLeft="
        + levelLeft + ", levelPercent=" + levelPercent
        + ", levelPoints=" + levelPoints + ", points=" + points
        + ", sentencesTranslated=" + sentencesTranslated + "]";
  }

}
