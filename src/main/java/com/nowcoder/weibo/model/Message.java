package com.nowcoder.weibo.model;

import java.util.Date;

/**
 * Created by rainday on 16/6/30.
 */
public class Message {

  private int id;

private String content;
  private String image;




  private Date createdDate;

  private int userId;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }



  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }




  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }
}
