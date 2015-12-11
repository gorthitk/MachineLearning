package com.jet.ml.impl;
import com.mongodb.MongoClient;

import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.jet.ml.model.BusinessInfo;
import com.jet.ml.model.Reviews;
import com.jet.ml.model.UserInfo;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

  /**
   * Title: MongoDBConnectionInterface.java<br>
   * Description: <br>
   * Created: 06-Dec-2015<br>
   * Copyright: Copyright (c) 2015<br>
   * @author Teja Sasank Gorthi (jet.sasank@gmail.com)
   */

  public class MongoDBConnectionInterface {

    /**
    * @return
    * @throws UnknownHostException
    */
    public DB connecttoDB() throws UnknownHostException {
         MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
     DB db = mongoClient.getDB( "yelp" );
     if (db != null) {
     System.out.println("Connection to database successfully");
         }
           return db;
     }

     /**
     * @param collectionName
     * @param db
     * @return
     * @throws Exception 
     */
    public DBCollection getCollection(String collectionName, DB db) throws Exception {
         DBCollection collection = db.getCollection(collectionName);
         if (collection == null) {
           throw new Exception("Unable to find a collection with name " + collectionName);
         }
         return collection;
     }

    /**
   * @param City
   * @return
   */
  public List<BusinessInfo> getBusinessInState(DBCollection collection, String state) {
      System.out.println("Populating Businesses for State : " + state);
       BasicDBObject whereQuery = new BasicDBObject();
       whereQuery.put("state", state);
       DBCursor cursor = collection.find(whereQuery);
       List<BusinessInfo> list = new ArrayList<BusinessInfo>();
       int i =0;
       while (cursor.hasNext()) {
       i++;
       BasicDBObject businessInfo = (BasicDBObject) cursor.next();
       BusinessInfo businessInfoPOJO = new BusinessInfo();
       businessInfoPOJO.setBusinessId(businessInfo.getString("business_id"));
       businessInfoPOJO.setName(businessInfo.getString("name"));
       businessInfoPOJO.setBusinessOpen(businessInfo.getBoolean("open"));
       businessInfoPOJO.setStars(businessInfo.getDouble("stars"));
       businessInfoPOJO.setReview_count(businessInfo.getInt("review_count"));
       businessInfoPOJO.setCity(businessInfo.getString("city"));
       businessInfoPOJO.setState(businessInfo.getString("state"));
       BasicDBList neighbourHoodList = (BasicDBList) businessInfo.get("neighborhoods");
       businessInfoPOJO.setNeighbour_hood_count(neighbourHoodList.size());
       businessInfoPOJO.setLatitude(businessInfo.getDouble("latitude"));
       businessInfoPOJO.setLongitude(businessInfo.getDouble("longitude"));
       BasicDBObject openHoursInfo = (BasicDBObject) businessInfo.get("hours");

       if (openHoursInfo.size() != 0) {
          if ((BasicDBObject) openHoursInfo.get("Saturday") == null && (BasicDBObject) openHoursInfo.get("Sunday") == null) {
              businessInfoPOJO.setWeekendsOpen(false);
          }

          if ((BasicDBObject) openHoursInfo.get("Monday") == null && (BasicDBObject) openHoursInfo.get("Tuesday") == null && 
                  (BasicDBObject) openHoursInfo.get("Wednesday") == null && 
                  (BasicDBObject) openHoursInfo.get("Thursday") == null && (BasicDBObject) openHoursInfo.get("Friday") == null) {
                    businessInfoPOJO.setWeekdaysOpen(false);
              }
           }
               list.add(businessInfoPOJO);
      }

       System.out.println("Number of Businesses found :" + i);
      return list;
    }

  /**
  * @param reviewBusinessMap 
  * @param numberOfReviews
  * @return
  * @throws Exception 
  */
  public List<Reviews> getListOfReviews (DBCollection collection, Map<String, BusinessInfo> businessInfoMap, boolean forBusiness, int number_of_reviews) throws Exception {

    DBCursor cursor = null;
    List<String> businessIds = new ArrayList<String>();
    if (forBusiness) {
        if (businessInfoMap == null || businessInfoMap.isEmpty()) throw new Exception("Ok I Messed Up!");
        for (BusinessInfo business : businessInfoMap.values()) {
            businessIds.add(business.getBusinessId());
       }

        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("business_id", new BasicDBObject("$in",businessIds));
        cursor = collection.find(whereQuery);
    } else {
       cursor = collection.find().limit(number_of_reviews);
    }

    List<Reviews> list = new ArrayList<Reviews>();
    while (cursor.hasNext()) {

     BasicDBObject reviewInfo = (BasicDBObject) cursor.next();
     BasicDBObject votesInfo = (BasicDBObject) reviewInfo.get("votes");

     Reviews review = new Reviews();

     review.setReviewId(reviewInfo.getString("review_id"));
     review.setUserId(reviewInfo.getString("user_id"));
     review.setBusinessId(reviewInfo.getString("business_id"));

     review.setStars(reviewInfo.getInt("stars"));
     review.setCool_votes(votesInfo.getInt("cool"));
     review.setFunny_votes(votesInfo.getInt("funny"));
     review.setUseful_votes(votesInfo.getInt("useful"));

     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
     Date convertedReviewDate = sdf.parse(reviewInfo.getString("date"));
     review.setReview_date(convertedReviewDate);

     Calendar calendar = new GregorianCalendar();
     calendar.setTime(convertedReviewDate);

     review.setReview_year(calendar.get(Calendar.YEAR));
     if (reviewInfo.getString("text") != null && !reviewInfo.getString("text").isEmpty()) {
           review.setReviewLength(reviewInfo.getString("text").length());
     }

     list.add(review);
  }
    System.out.println("Number of Reviews found : "+ list.size());
    return list;
   }

  public void getCheckInInformation(DBCollection collection, Map<String, BusinessInfo> businessInfoMap) {

      List<String> businessIds = new ArrayList<String>();
      for (BusinessInfo business : businessInfoMap.values()) {
           businessIds.add(business.getBusinessId());
      }

      BasicDBObject whereQuery = new BasicDBObject();
      whereQuery.put("business_id", new BasicDBObject("$in",businessIds));
      DBCursor cursor = collection.find(whereQuery);

      int i =0;
      while (cursor.hasNext()) {
      i++;
      BasicDBObject checkInInfo = (BasicDBObject) cursor.next();
      BasicDBObject checkIn_Date_TimeInfo = (BasicDBObject) checkInInfo.get("checkin_info");

      BusinessInfo business = businessInfoMap.get(checkInInfo.get("business_id"));

        for (Entry<String, Object> entry : checkIn_Date_TimeInfo.entrySet()) {
               business.incrementCheckIns(1);
           String[] keyParts = entry.toString().split("-", 2);
           if (keyParts[1].equals("0") || keyParts[1].equals("5") || keyParts[1].equals("6")) {
               business.incrementCheckIns(3);
           } else {
               business.incrementCheckIns(2);
           }
        }
      }
      System.out.println("Number of businesses with checkins :" + i);
  }


   /**
   * @param collection
   * @param reviews
   * @throws ParseException 
   */
  public Map<String, UserInfo> populateUserInfo(DBCollection collection, List<Reviews> reviews) throws ParseException {

     List<String> userIds = new ArrayList<String>();

     for (Reviews review : reviews) {
         userIds.add(review.getUserId());
     }

     BasicDBObject whereQuery = new BasicDBObject();
     whereQuery.put("user_id", new BasicDBObject("$in",userIds));
     DBCursor cursor = collection.find(whereQuery);

     Map<String, UserInfo> users = new HashMap<String, UserInfo>();

        while(cursor.hasNext()) {
          BasicDBObject userInfo = (BasicDBObject) cursor.next();

          UserInfo user = new UserInfo();
          user.setUserId(userInfo.getString("user_id"));

          user.setYelp_user_name(userInfo.getString("name"));
          user.setReview_count_user(userInfo.getInt("review_count"));
          user.setStars_user(userInfo.getDouble("average_stars"));
          user.setFans(userInfo.getInt("fans"));

          BasicDBObject votesInfo = (BasicDBObject) userInfo.get("votes");
          user.setCool_votes_user(votesInfo.getInt("cool"));
          user.setFunny_votes_user(votesInfo.getInt("funny"));
          user.setUseful_votes_user(votesInfo.getInt("useful"));

          BasicDBList friendsList = (BasicDBList) userInfo.get("friends");
          user.setFriends_count(friendsList.size());

          BasicDBList eliteYears = (BasicDBList) userInfo.get("elite");
          if (eliteYears != null && !eliteYears.isEmpty()) {
             //Sets to true is the User was an elite user even for a year
            user.setEliteUser(true);

            user.setYearsOfElite(new ArrayList<String>());
             for (int i =0; i < eliteYears.size(); i ++) {
                   user.getYearsOfElite().add(Integer.toString((int)eliteYears.get(i)));
                 }
              }
          users.put(user.getUserId(), user);
            }
        System.out.println("Number of users :" + users.size());
        return users;
      }

   public Map<String, UserInfo> getUserInfo(DBCollection collection, boolean include_nonElite, List<String> userIds) throws ParseException {
     Map<String, UserInfo> userMap = new HashMap<String, UserInfo>();
     DBCursor cursor = null;

     if (!include_nonElite) {
         BasicDBObject whereQuery = new BasicDBObject();
         //Used when we only need information about users who are elite
         whereQuery.put("elite", new BasicDBObject("$not", new BasicDBObject("$size", 0)));
         cursor = collection.find(whereQuery);
     } else {
         BasicDBObject whereQuery = new BasicDBObject();
         whereQuery.put("user_id", new BasicDBObject("$in",userIds));
         cursor = collection.find(whereQuery);
     }

     while(cursor.hasNext()) {
         BasicDBObject userInfo = (BasicDBObject) cursor.next();
         BasicDBObject votesInfo = (BasicDBObject) userInfo.get("votes");
         BasicDBList eliteYears = (BasicDBList) userInfo.get("elite");
         BasicDBList friendsList = (BasicDBList) userInfo.get("friends");

         UserInfo user = new UserInfo();

         user.setUserId(userInfo.getString("user_id"));
         user.setYelp_user_name(userInfo.getString("name"));
         user.setReview_count_user(userInfo.getInt("review_count"));
         user.setStars_user(userInfo.getInt("average_stars"));



         user.setCool_votes_user(votesInfo.getInt("cool"));
         user.setFunny_votes_user(votesInfo.getInt("funny"));
         user.setUseful_votes_user(votesInfo.getInt("useful"));

         user.setFans(userInfo.getInt("fans"));
         user.setFriends_count(friendsList.size());

         String yelping_since = userInfo.getString("yelping_since") + "-01";
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
         Date convertedYelpingSinceDate = sdf.parse(yelping_since);

         Date currentDate = new Date();
 
         Calendar startCalendar = new GregorianCalendar();
         startCalendar.setTime(convertedYelpingSinceDate);
         Calendar endCalendar = new GregorianCalendar();
         endCalendar.setTime(currentDate);

         int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
         int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);

         user.setNumber_of_months_yelping(diffMonth);
         user.setYelping_since(convertedYelpingSinceDate);

         if (eliteYears != null && !eliteYears.isEmpty()) {
            user.setEliteUser(true);

            user.setYearsOfElite(new ArrayList<String>());
             for (int i =0; i < eliteYears.size(); i ++) {
                   user.getYearsOfElite().add(Integer.toString((int)eliteYears.get(i)));
                 }
              }
         userMap.put(user.getUserId(), user);
         }
     System.out.println("Number of Users fetched from the Database : " + userMap.size());
     return userMap;
     }

   /**
    * @param reviewBusinessMap 
   * @param numberOfReviews
    * @return
    * @throws ParseException 
    */
    public List<Reviews> getListOfReviewsForUserId(DBCollection collection, List<String> userIds) throws ParseException {

      BasicDBObject whereQuery = new BasicDBObject();
      whereQuery.put("user_id", new BasicDBObject("$in",userIds));
      DBCursor cursor = collection.find(whereQuery);

      List<Reviews> list = new ArrayList<Reviews>();
      while (cursor.hasNext()) {

       BasicDBObject reviewInfo = (BasicDBObject) cursor.next();
       BasicDBObject votesInfo = (BasicDBObject) reviewInfo.get("votes");

       Reviews review = new Reviews();

       review.setReviewId(reviewInfo.getString("review_id"));
       review.setUserId(reviewInfo.getString("user_id"));
       review.setBusinessId(reviewInfo.getString("business_id"));

       review.setStars(reviewInfo.getDouble("stars"));
       review.setCool_votes(votesInfo.getInt("cool"));
       review.setFunny_votes(votesInfo.getInt("funny"));
       review.setUseful_votes(votesInfo.getInt("useful"));

       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
       Date convertedReviewDate = sdf.parse(reviewInfo.getString("date"));
       review.setReview_date(convertedReviewDate);

       Calendar calendar = new GregorianCalendar();
       calendar.setTime(convertedReviewDate);

       review.setReview_year(calendar.get(Calendar.YEAR));

       if (reviewInfo.getString("text") != null && !reviewInfo.getString("text").isEmpty()) {
             review.setReviewLength(reviewInfo.getString("text").length());
       }

       list.add(review);

    }
      System.out.println("Number of Reviews found : "+ list.size());
      return list;
     }
//End of Class
}