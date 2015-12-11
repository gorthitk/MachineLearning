package com.jet.ml.model;
import java.io.Serializable;
import java.util.List;

/**
 * Title: UserInfo.java<br>
 * Description: <br>
 * Created: 06-Dec-2015<br>
 * Copyright: Copyright (c) 2015<br>
 * @author Teja Sasank Gorthi (jet.sasank@gmail.com)
 */
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    private int funny_votes_user;
    private int useful_votes_user;
    private int cool_votes_user;
    private int stars_user;
    private int friends_count;
    private int review_count_user;
    private int fans;
    private String yelp_user_name;
    private String userId;
    private boolean eliteUser;
 
    //temp variables
    private List<String> yearsOfElite;

    private int number_of_months_yelping;
    //Compliments
    
    /**
     * @return the funny_votes_user
     */
    public int getFunny_votes_user() {
        return funny_votes_user;
    }
    /**
     * @param funny_votes_user the funny_votes_user to set
     */
    public void setFunny_votes_user(int funny_votes_user) {
        this.funny_votes_user = funny_votes_user;
    }
    /**
     * @return the useful_votes_user
     */
    public int getUseful_votes_user() {
        return useful_votes_user;
    }
    /**
     * @param useful_votes_user the useful_votes_user to set
     */
    public void setUseful_votes_user(int useful_votes_user) {
        this.useful_votes_user = useful_votes_user;
    }
    /**
     * @return the cool_votes_user
     */
    public int getCool_votes_user() {
        return cool_votes_user;
    }
    /**
     * @param cool_votes_user the cool_votes_user to set
     */
    public void setCool_votes_user(int cool_votes_user) {
        this.cool_votes_user = cool_votes_user;
    }
    /**
     * @return the stars_user
     */
    public int getStars_user() {
        return stars_user;
    }
    /**
     * @param stars_user the stars_user to set
     */
    public void setStars_user(int stars_user) {
        this.stars_user = stars_user;
    }
    /**
     * @return the friends_count
     */
    public int getFriends_count() {
        return friends_count;
    }
    /**
     * @param friends_count the friends_count to set
     */
    public void setFriends_count(int friends_count) {
        this.friends_count = friends_count;
    }
    /**
     * @return the review_count_user
     */
    public int getReview_count_user() {
        return review_count_user;
    }
    /**
     * @param review_count_user the review_count_user to set
     */
    public void setReview_count_user(int review_count_user) {
        this.review_count_user = review_count_user;
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
     * @return the yelp_user_name
     */
    public String getYelp_user_name() {
        return yelp_user_name;
    }
    /**
     * @param yelp_user_name the yelp_user_name to set
     */
    public void setYelp_user_name(String yelp_user_name) {
        this.yelp_user_name = yelp_user_name;
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
     * @return the yearsOfElite
     */
    public List<String> getYearsOfElite() {
        return yearsOfElite;
    }
    /**
     * @param yearsOfElite the yearsOfElite to set
     */
    public void setYearsOfElite(List<String> yearsOfElite) {
        this.yearsOfElite = yearsOfElite;
    }

    /**
     * @return the number_of_months_yelping
     */
    public int getNumber_of_months_yelping() {
        return number_of_months_yelping;
    }
    /**
     * @param number_of_months_yelping the number_of_months_yelping to set
     */
    public void setNumber_of_months_yelping(int number_of_months_yelping) {
        this.number_of_months_yelping = number_of_months_yelping;
    }

}
