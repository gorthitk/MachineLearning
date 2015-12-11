package com.jet.ml.model;
import java.io.Serializable;
import java.util.Date;


/**
 * Title: Reviews.java<br>
 * Description: <br>
 * Created: 06-Dec-2015<br>
 * Copyright: Copyright (c) 2015<br>
 * @author Teja Sasank Gorthi (jet.sasank@gmail.com)
 */
public class Reviews implements Serializable{

    private static final long serialVersionUID = 1L;

    private String reviewId;
    private String businessId;
    private String userId;

    private int reviewLength;
    private Date review_date;

    private double Stars;
    private int funny_votes;
    private int useful_votes;
    private int cool_votes;

    private UserInfo userInfo;
    private boolean eliteYearReview;
    private long review_year;

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
     * @return the reviewLength
     */
    public int getReviewLength() {
        return reviewLength;
    }
    /**
     * @param reviewLength the reviewLength to set
     */
    public void setReviewLength(int reviewLength) {
        this.reviewLength = reviewLength;
    }
    /**
     * @return the funny_votes
     */
    public int getFunny_votes() {
        return funny_votes;
    }
    /**
     * @param funny_votes the funny_votes to set
     */
    public void setFunny_votes(int funny_votes) {
        this.funny_votes = funny_votes;
    }
    /**
     * @return the useful_votes
     */
    public int getUseful_votes() {
        return useful_votes;
    }
    /**
     * @param useful_votes the useful_votes to set
     */
    public void setUseful_votes(int useful_votes) {
        this.useful_votes = useful_votes;
    }
    /**
     * @return the cool_votes
     */
    public int getCool_votes() {
        return cool_votes;
    }
    /**
     * @param cool_votes the cool_votes to set
     */
    public void setCool_votes(int cool_votes) {
        this.cool_votes = cool_votes;
    }
    /**
     * @return the review_date
     */
    public Date getReview_date() {
        return review_date;
    }
    /**
     * @param review_date the review_date to set
     */
    public void setReview_date(Date review_date) {
        this.review_date = review_date;
    }

    /**
    * @return the reviewId
    */
    public String getReviewId() {
        return reviewId;
     }

    /**
    * @param reviewId the reviewId to set
    */
    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    /**
     * @return the businessId
     */
    public String getBusinessId() {
        return businessId;
    }
    /**
     * @param businessId the businessId to set
     */
    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }
    /**
     * @return the userInfo
     */
    public UserInfo getUserInfo() {
        return userInfo;
    }
    /**
     * @param userInfo the userInfo to set
     */
    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
    /**
     * @return the stars
     */
    public double getStars() {
        return Stars;
    }
    /**
     * @param stars the stars to set
     */
    public void setStars(double stars) {
        Stars = stars;
    }

    /**
     * @return the eliteYearReview
     */
    public boolean isEliteYearReview() {
        return eliteYearReview;
    }
    /**
     * @param eliteYearReview the eliteYearReview to set
     */
    public void setEliteYearReview(boolean eliteYearReview) {
        this.eliteYearReview = eliteYearReview;
    }

    /**
     * @return the review_year
     */
    public long getReview_year() {
        return review_year;
    }
    /**
     * @param review_year the review_year to set
     */
    public void setReview_year(long review_year) {
        this.review_year = review_year;
    }
}
