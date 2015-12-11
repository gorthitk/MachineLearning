package com.jet.ml.model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Title: BusinessInfo.java<br>
 * Description: <br>
 * Created: 08-Dec-2015<br>
 * Copyright: Copyright (c) 2015<br>
 * @author Teja Sasank Gorthi (jet.sasank@gmail.com)
 */
public class BusinessInfo implements Serializable{

   private static final long serialVersionUID = 1L;
   private String businessId;
   private String city;
   private String state;
   private double distance;
   private int review_count;
   private int number_elite_user_review_count = 0;
   private double stars;
   private int neighbour_hood_count;
   private boolean businessOpen;
   private boolean weekdaysOpen = true;
   private boolean weekendsOpen = true;
   private String name;
   private double latitude;
   private double longitude;
   private List<Reviews> reviews;
   private int numberOfCheckIns;
   private int numberOfCheckInsOnWeekend;
   private int numberOfCheckInsOnWeekday;

   private double total_stars_review = 0;
   private double total_star_user = 0;
   private double total_cool_review = 0;
   private double total_cool_user = 0;
   private double total_useful_review = 0;
   private double total_useful_user = 0;
   private double total_funny_review = 0;
   private double total_funny_user = 0;
   private double total_review_length = 0;
   private long number_of_reviews;
   private long number_of_users;

   private int number_of_reviews_last_twelve_months = 0;
   private int number_of_reviews_twelve_months_before_the_end_of_last_twelve_months = 0;
   private int number_of_reviews__until_two_year_before = 0;

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
 * @return the city
 */
public String getCity() {
   return city;
}
/**
 * @param city the city to set
 */
public void setCity(String city) {
   this.city = city;
}
/**
 * @return the state
 */
public String getState() {
   return state;
}
/**
 * @param state the state to set
 */
public void setState(String state) {
   this.state = state;
}
/**
 * @return the distance
 */
public double getDistance() {
   return distance;
}
/**
 * @param distance the distance to set
 */
public void setDistance(double distance) {
   this.distance = distance;
}
/**
 * @return the review_count
 */
public int getReview_count() {
   return review_count;
}
/**
 * @param review_count the review_count to set
 */
public void setReview_count(int review_count) {
   this.review_count = review_count;
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
 * @return the neighbour_hood_count
 */
public int getNeighbour_hood_count() {
   return neighbour_hood_count;
}
/**
 * @param neighbour_hood_count the neighbour_hood_count to set
 */
public void setNeighbour_hood_count(int neighbour_hood_count) {
   this.neighbour_hood_count = neighbour_hood_count;
}
/**
 * @return the businessOpen
 */
public boolean isBusinessOpen() {
   return businessOpen;
}
/**
 * @param businessOpen the businessOpen to set
 */
public void setBusinessOpen(boolean businessOpen) {
   this.businessOpen = businessOpen;
}
/**
 * @return the weekdaysOpen
 */
public boolean isWeekdaysOpen() {
   return weekdaysOpen;
}
/**
 * @param weekdaysOpen the weekdaysOpen to set
 */
public void setWeekdaysOpen(boolean weekdaysOpen) {
   this.weekdaysOpen = weekdaysOpen;
}
/**
 * @return the weekendsOpen
 */
public boolean isWeekendsOpen() {
   return weekendsOpen;
}
/**
 * @param weekendsOpen the weekendsOpen to set
 */
public void setWeekendsOpen(boolean weekendsOpen) {
   this.weekendsOpen = weekendsOpen;
}
/**
 * @return the name
 */
public String getName() {
   return name;
}
/**
 * @param name the name to set
 */
public void setName(String name) {
   this.name = name;
}
/**
 * @return the latitude
 */
public double getLatitude() {
   return latitude;
}
/**
 * @param latitude the latitude to set
 */
public void setLatitude(double latitude) {
   this.latitude = latitude;
}
/**
 * @return the longitude
 */
public double getLongitude() {
   return longitude;
}
/**
 * @param longitude the longitude to set
 */
public void setLongitude(double longitude) {
   this.longitude = longitude;
}
/**
 * @return the reviews
 */
public List<Reviews> getReviews() {
	return reviews;
}
/**
 * @param reviews the reviews to set
 */
private void setReviews(List<Reviews> reviews) {
	this.reviews = reviews;
}
/**
 * @return the numberOfCheckIns
 */
public int getNumberOfCheckIns() {
	return numberOfCheckIns;
}
/**
 * @param numberOfCheckIns the numberOfCheckIns to set
 */
private void setNumberOfCheckIns(int numberOfCheckIns) {
	this.numberOfCheckIns = numberOfCheckIns;
}
/**
 * @return the numberOfCheckInsOnWeekend
 */
public int getNumberOfCheckInsOnWeekend() {
	return numberOfCheckInsOnWeekend;
}
/**
 * @param numberOfCheckInsOnWeekend the numberOfCheckInsOnWeekend to set
 */
private void setNumberOfCheckInsOnWeekend(int numberOfCheckInsOnWeekend) {
	this.numberOfCheckInsOnWeekend = numberOfCheckInsOnWeekend;
}
/**
 * @return the numberOfCheckInsOnWeekday
 */
public int getNumberOfCheckInsOnWeekday() {
	return numberOfCheckInsOnWeekday;
}
/**
 * @param numberOfCheckInsOnWeekday the numberOfCheckInsOnWeekday to set
 */
private void setNumberOfCheckInsOnWeekday(int numberOfCheckInsOnWeekday) {
	this.numberOfCheckInsOnWeekday = numberOfCheckInsOnWeekday;
}

public void addReview(Reviews review) {
    if (getReviews() == null) {
        setReviews(new ArrayList<Reviews>());
    }
    getReviews().add(review);
}



/**
 * @return the number_of_reviews_last_twelve_months
 */
public int getNumber_of_reviews_last_twelve_months() {
	return number_of_reviews_last_twelve_months;
}

/**
 * @param number_of_reviews_last_twelve_months the number_of_reviews_last_twelve_months to set
 */
public void setNumber_of_reviews_last_twelve_months(int number_of_reviews_last_twelve_months) {
	this.number_of_reviews_last_twelve_months = number_of_reviews_last_twelve_months;
}

/**
 * @return the number_of_reviews_twelve_months_before_the_end_of_last_twelve_months
 */
public int getNumber_of_reviews_twelve_months_before_the_end_of_last_twelve_months() {
	return number_of_reviews_twelve_months_before_the_end_of_last_twelve_months;
}

/**
 * @param number_of_reviews_twelve_months_before_the_end_of_last_twelve_months the number_of_reviews_twelve_months_before_the_end_of_last_twelve_months to set
 */
public void setNumber_of_reviews_twelve_months_before_the_end_of_last_twelve_months(
		int number_of_reviews_twelve_months_before_the_end_of_last_twelve_months) {
	this.number_of_reviews_twelve_months_before_the_end_of_last_twelve_months = number_of_reviews_twelve_months_before_the_end_of_last_twelve_months;
}

/**
 * @return the number_of_reviews__until_two_year_before
 */
public int getNumber_of_reviews__until_two_year_before() {
	return number_of_reviews__until_two_year_before;
}

/**
 * @param number_of_reviews__until_two_year_before the number_of_reviews__until_two_year_before to set
 */
public void setNumber_of_reviews__until_two_year_before(int number_of_reviews__until_two_year_before) {
	this.number_of_reviews__until_two_year_before = number_of_reviews__until_two_year_before;
}

/**
 * @return the total_stars_review
 */
public double getTotal_stars_review() {
	return total_stars_review;
}

/**
 * @param total_stars_review the total_stars_review to set
 */
public void setTotal_stars_review(double total_stars_review) {
	this.total_stars_review = total_stars_review;
}

/**
 * @return the total_star_user
 */
public double getTotal_star_user() {
	return total_star_user;
}

/**
 * @param total_star_user the total_star_user to set
 */
public void setTotal_star_user(double total_star_user) {
	this.total_star_user = total_star_user;
}

/**
 * @return the total_cool_review
 */
public double getTotal_cool_review() {
	return total_cool_review;
}

/**
 * @param total_cool_review the total_cool_review to set
 */
public void setTotal_cool_review(double total_cool_review) {
	this.total_cool_review = total_cool_review;
}

/**
 * @return the total_cool_user
 */
public double getTotal_cool_user() {
	return total_cool_user;
}

/**
 * @param total_cool_user the total_cool_user to set
 */
public void setTotal_cool_user(double total_cool_user) {
	this.total_cool_user = total_cool_user;
}

/**
 * @return the total_useful_review
 */
public double getTotal_useful_review() {
	return total_useful_review;
}

/**
 * @param total_useful_review the total_useful_review to set
 */
public void setTotal_useful_review(double total_useful_review) {
	this.total_useful_review = total_useful_review;
}

/**
 * @return the total_useful_user
 */
public double getTotal_useful_user() {
	return total_useful_user;
}

/**
 * @param total_useful_user the total_useful_user to set
 */
public void setTotal_useful_user(double total_useful_user) {
	this.total_useful_user = total_useful_user;
}

/**
 * @return the total_funny_review
 */
public double getTotal_funny_review() {
	return total_funny_review;
}

/**
 * @param total_funny_review the total_funny_review to set
 */
public void setTotal_funny_review(double total_funny_review) {
	this.total_funny_review = total_funny_review;
}

/**
 * @return the total_funny_user
 */
public double getTotal_funny_user() {
	return total_funny_user;
}

/**
 * @param total_funny_user the total_funny_user to set
 */
public void setTotal_funny_user(double total_funny_user) {
	this.total_funny_user = total_funny_user;
}

/**
 * @return the total_review_length
 */
public double getTotal_review_length() {
	return total_review_length;
}

/**
 * @param total_review_length the total_review_length to set
 */
public void setTotal_review_length(double total_review_length) {
	this.total_review_length = total_review_length;
}

/**
 * @return the number_elite_user_review_count
 */
public int getNumber_elite_user_review_count() {
	return number_elite_user_review_count;
}

/**
 * @param number_elite_user_review_count the number_elite_user_review_count to set
 */
public void setNumber_elite_user_review_count(int number_elite_user_review_count) {
	this.number_elite_user_review_count = number_elite_user_review_count;
}


/**
 * @return the number_of_reviews
 */
public long getNumber_of_reviews() {
	return number_of_reviews;
}

/**
 * @param number_of_reviews the number_of_reviews to set
 */
public void setNumber_of_reviews(long number_of_reviews) {
	this.number_of_reviews = number_of_reviews;
}

/**
 * @return the number_of_users
 */
public long getNumber_of_users() {
	return number_of_users;
}

/**
 * @param number_of_users the number_of_users to set
 */
public void setNumber_of_users(long number_of_users) {
	this.number_of_users = number_of_users;
}


public void incrementReviewCount(int type) {
    switch(type) {

    case 1: setNumber_of_reviews_last_twelve_months(getNumber_of_reviews_last_twelve_months() + 1);
    case 2: setNumber_of_reviews_twelve_months_before_the_end_of_last_twelve_months(getNumber_of_reviews_twelve_months_before_the_end_of_last_twelve_months() + 1);
    case 3: setNumber_of_reviews__until_two_year_before(getNumber_of_reviews__until_two_year_before() + 1);
    default: break;
    }
 }

 public void incrementCheckIns(int type) {
     switch(type) {
     case 1: setNumberOfCheckIns(getNumberOfCheckIns() + 1);
     case 2: setNumberOfCheckInsOnWeekday(getNumberOfCheckInsOnWeekday() + 1);
     case 3: setNumberOfCheckInsOnWeekend(getNumberOfCheckInsOnWeekend() + 1);
     default: break;
    }
 }

}
