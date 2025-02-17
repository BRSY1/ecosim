package org.openjfx.ui;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;


public class AnimalInfo{
  private String name;
  private String description;
  private Image image;

  public AnimalInfo(String name, String description, String imagePath){
    this.name = name;
    this.description = description;
    this.image = new Image(imagePath);
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public Image getImage() {
    return image;
  }
}