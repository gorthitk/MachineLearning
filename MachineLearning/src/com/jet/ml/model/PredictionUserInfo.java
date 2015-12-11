package com.jet.ml.model;

import java.io.Serializable;

/**
 * Title: PredictionUserInfo.java<br>
 * Description: <br>
 * Created: 06-Dec-2015<br>
 * Copyright: Copyright (c) 2015<br>
 * @author Teja Sasank Gorthi (jet.sasank@gmail.com)
 */
public class PredictionUserInfo implements Serializable{

   private static final long serialVersionUID = 1L;
   private String userId;
   private double stars;
   private int cool;
   private int funny;
   private int useful;
   private int friends;
   private int fans;

   private int months_yelping;
   private boolean eliteUser;
   private int earlier_years_elite_membership = 0;


   private int actual_review_count = 0;
   private double review_star_count= 0;
   private int review_cool= 0;
   private int review_funny= 0;
   private int review_useful= 0;

   
public PredictionUserInfo(String userId, double stars, int cool, int funny, int useful, int friends, int fans) {
    super();
    this.userId = userId;
    this.stars = stars;
    this.cool = cool;
    this.funny = funny;
    this.useful = useful;
    this.friends = friends;
    this.fans = fans;
}
/**
 * @return the userId
 */
public String getUserId() {
    return userId;
}
/**
 * @param userId the userId to set
 */
public void setUserId(String userId) {
    this.userId = userId;
}
/**
 * @return the stars
 */
public double getStars() {
    return stars;
}
/**
 * @param stars the stars to set
 */
public void setStars(double stars) {
    this.stars = stars;
}
/**
 * @return the cool
 */
public int getCool() {
    return cool;
}
/**
 * @param cool the cool to set
 */
public void setCool(int cool) {
    this.cool = cool;
}
/**
 * @return the funny
 */
public int getFunny() {
    return funny;
}
/**
 * @param funny the funny to set
 */
public void setFunny(int funny) {
    this.funny = funny;
}
/**
 * @return the useful
 */
public int getUseful() {
    return useful;
}
/**
 * @param useful the useful to set
 */
public void setUseful(int useful) {
    this.useful = useful;
}
/**
 * @return the friends
 */
public int getFriends() {
    return friends;
}
/**
 * @param friends the friends to set
 */
public void setFriends(int friends) {
    this.friends = friends;
}
/**
 * @return the fans
 */
public int getFans() {
    return fans;
}
/**
 * @param fans the fans to set
 */
public void setFans(int fans) {
    this.fans = fans;
}
/**
 * @return the months_yelping
 */
public int getMonths_yelping() {
    return months_yelping;
}
/**
 * @param months_yelping the months_yelping to set
 */
public void setMonths_yelping(int months_yelping) {
    this.months_yelping = months_yelping;
}
/**
 * @return the actual_review_count
 */
public int getActual_review_count() {
    return actual_review_count;
}
/**
 * @param actual_review_count the actual_review_count to set
 */
public void setActual_review_count(int actual_review_count) {
    this.actual_review_count = actual_review_count;
}
/**
 * @return the review_star_count
 */
public double getReview_star_count() {
    return review_star_count;
}
/**
 * @param review_star_count the review_star_count to set
 */
public void setReview_star_count(double review_star_count) {
    this.review_star_count = review_star_count;
}
/**
 * @return the review_cool
 */
public int getReview_cool() {
    return review_cool;
}
/**
 * @param review_cool the review_cool to set
 */
public void setReview_cool(int review_cool) {
    this.review_cool = review_cool;
}
/**
 * @return the review_funny
 */
public int getReview_funny() {
    return review_funny;
}
/**
 * @param review_funny the review_funny to set
 */
public void setReview_funny(int review_funny) {
    this.review_funny = review_funny;
}
/**
 * @return the review_useful
 */
public int getReview_useful() {
    return review_useful;
}
/**
 * @param review_useful the review_useful to set
 */
public void setReview_useful(int review_useful) {
    this.review_useful = review_useful;
}
/**
 * @return the eliteUser
 */
public boolean isEliteUser() {
    return eliteUser;
}
/**
 * @param eliteUser the eliteUser to set
 */
public void setEliteUser(boolean eliteUser) {
    this.eliteUser = eliteUser;
}
/**
 * @return the earlier_years_elite_membership
 */
public int getEarlier_years_elite_membership() {
    return earlier_years_elite_membership;
}
/**
 * @param earlier_years_elite_membership the earlier_years_elite_membership to set
 */
public void setEarlier_years_elite_membership(int earlier_years_elite_membership) {
    this.earlier_years_elite_membership = earlier_years_elite_membership;
}

}
