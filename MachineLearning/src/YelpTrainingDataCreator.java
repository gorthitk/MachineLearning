import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jet.ml.impl.DataHelper;
import com.jet.ml.impl.HelperClass;
import com.jet.ml.impl.MongoDBConnectionInterface;
import com.jet.ml.model.BusinessInfo;
import com.jet.ml.model.Location;
import com.jet.ml.model.PredictionUserInfo;
import com.jet.ml.model.Reviews;
import com.jet.ml.model.SearchCriteria;
import com.jet.ml.model.UserInfo;
import com.mongodb.DB;
import com.mongodb.DBCollection;


/**
 * Title: YelpTrainingDataCreator.java<br>
 * Description: <br>
 * Created: 06-Dec-2015<br>
 * Copyright: Copyright (c) 2015<br>
 * @author Teja Sasank Gorthi (jet.sasank@gmail.com)
 */
public class YelpTrainingDataCreator {
    private static final String REVIEW_COLLECTION_NAME = "reviews";
    private static final String BUSINESS_COLLECTION_NAME = "businessInfo";
    private static final String USER_COLLECTION_NAME = "users";
    private static final String CHECKIN_COLLECTION_NAME = "checkinInfo";
    private static final HelperClass HELPER = new HelperClass();
    private static final DataHelper DATA_HELPER = new DataHelper();
    private static final MongoDBConnectionInterface MONGODB_CONNECTION_INTERFACE = new MongoDBConnectionInterface();

     /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {

        if (args == null) throw new Exception("I tried mending it with a Hammer !");
        DB db = MONGODB_CONNECTION_INTERFACE.connecttoDB();
        Map<String, DBCollection> collections = new HashMap<String, DBCollection>();

        collections.put(BUSINESS_COLLECTION_NAME, MONGODB_CONNECTION_INTERFACE.getCollection(BUSINESS_COLLECTION_NAME, db));
        collections.put(REVIEW_COLLECTION_NAME, MONGODB_CONNECTION_INTERFACE.getCollection(REVIEW_COLLECTION_NAME, db));
        collections.put(USER_COLLECTION_NAME, MONGODB_CONNECTION_INTERFACE.getCollection(USER_COLLECTION_NAME, db));
        collections.put(CHECKIN_COLLECTION_NAME, MONGODB_CONNECTION_INTERFACE.getCollection(CHECKIN_COLLECTION_NAME, db));

        if (args[0].equals("1")) {
            populate_data_for_business(collections);
        } else {
            populate_data_for_users(collections);
        }
    }

    public static void populate_data_for_users(Map<String, DBCollection> collections) throws ParseException {
      System.out.println("Populating User Info");
      Map<String, UserInfo> userMap = MONGODB_CONNECTION_INTERFACE.getUserInfo(collections.get(USER_COLLECTION_NAME), true);
      List<String> userIds = new ArrayList<String>();
      userIds.addAll(userMap.keySet());
      List<Reviews> reviews = MONGODB_CONNECTION_INTERFACE.getListOfReviewsForUserId(collections.get(REVIEW_COLLECTION_NAME), userIds);

      Map<String, Map<String, PredictionUserInfo>> mostComplicatedUserMap = new HashMap<String, Map<String, PredictionUserInfo>>();

      //Populating User Info against each Elite Year
      System.out.println("Populating User Info against each elite year");
      for (String userId : userIds) {
          UserInfo user = userMap.get(userId);
          if (user.getYearsOfElite() != null && !user.getYearsOfElite().isEmpty()) {
              for (String year : user.getYearsOfElite()) {
                  PredictionUserInfo predUserInfo = new PredictionUserInfo(user.getUserId(), user.getStars_user(), user.getCool_votes_user(), user.getFunny_votes_user(), 
                      user.getUseful_votes_user(), user.getFriends_count(), user.getFans());
                  predUserInfo.setEliteUser(true);

                  Calendar startCalendar = new GregorianCalendar();
                  startCalendar.setTime(user.getYelping_since());

                  Calendar endCalendar = new GregorianCalendar();
                  endCalendar.set(Integer.parseInt(year), 01, 01);

                  int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
                  int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);

                  predUserInfo.setMonths_yelping(diffMonth);
                  predUserInfo.setEarlier_years_elite_membership(user.getYearsOfElite().size());

                  if (mostComplicatedUserMap.get(userId) == null) {
                      Map<String, PredictionUserInfo> map = new HashMap<String, PredictionUserInfo>();
                      mostComplicatedUserMap.put(userId, map);
                  }

                      mostComplicatedUserMap.get(userId).put(year, predUserInfo);
                } 
          } else {
              PredictionUserInfo predUserInfo = new PredictionUserInfo(user.getUserId(), user.getStars_user(), user.getCool_votes_user(), user.getFunny_votes_user(), 
                      user.getUseful_votes_user(), user.getFriends_count(), user.getFans());
                  predUserInfo.setEliteUser(false); 

                  Calendar cal = new GregorianCalendar();
                  cal.setTime(new Date());
                  predUserInfo.setMonths_yelping(user.getNumber_of_months_yelping() - cal.get(Calendar.MONTH));
                  Map<String, PredictionUserInfo> map = new HashMap<String, PredictionUserInfo>();
                  map.put(Integer.toString(cal.get(Calendar.YEAR)), predUserInfo);
                  mostComplicatedUserMap.put(userId, map);
          }
      } //End of Part 1

      for (Reviews review : reviews) {
          Calendar reviewDate = new GregorianCalendar();
          reviewDate.setTime(review.getReview_date());
          Map<String, PredictionUserInfo> map = mostComplicatedUserMap.get(review.getUserId());
          for(String year : map.keySet()) {
             if (Integer.parseInt(year) > reviewDate.get(Calendar.YEAR)) {
                   PredictionUserInfo predUserInfo = map.get(year);
                   predUserInfo.setReview_cool(predUserInfo.getReview_cool() + review.getCool_votes());
                   predUserInfo.setReview_funny(predUserInfo.getReview_funny() + review.getFunny_votes());
                   predUserInfo.setReview_useful(predUserInfo.getReview_useful() + review.getUseful_votes());
                   predUserInfo.setStars(predUserInfo.getReview_star_count() + review.getStars());
                   predUserInfo.setActual_review_count(predUserInfo.getActual_review_count() + 1);
                   map.put(year, predUserInfo);
                   mostComplicatedUserMap.put(review.getUserId(), map);
             }
          }
      }

      List<PredictionUserInfo> finalUserInfoList = new ArrayList<PredictionUserInfo>();
      for (String userId : mostComplicatedUserMap.keySet()) {
         for (String year : mostComplicatedUserMap.get(userId).keySet()) {
               finalUserInfoList.add(mostComplicatedUserMap.get(userId).get(year));
         }
      }
      System.out.println("Total Number of Distinct elite user profiles : " + finalUserInfoList.size());
      HELPER.generateCSVdataForUsers(finalUserInfoList);
    }

   public static void populate_data_for_business(Map<String, DBCollection> collections) throws Exception {

        List<SearchCriteria> searchCriterias = DATA_HELPER.getCriteria();


        if (searchCriterias == null || searchCriterias.isEmpty()) {
            throw new Exception ("Houston, we've had a problem here!");
        }

        for (SearchCriteria criteria : searchCriterias) {
            System.out.println("Processing Data for State : " + criteria.getState());
            List<BusinessInfo> businessList = MONGODB_CONNECTION_INTERFACE.getBusinessInState(collections.get(BUSINESS_COLLECTION_NAME), criteria.getState());

            Map<String, BusinessInfo> businessInfoMap = new HashMap<String, BusinessInfo>();

            for (BusinessInfo business : businessList) {
                double distance = 0;
                for (Location loc : criteria.getGeoLocations()) {
                   double tempdistance = HELPER.distance(loc.getLatitude(), loc.getLongitude(), business.getLatitude(), business.getLongitude(), 'K');
                   if (distance == 0) distance = tempdistance;
                   if (tempdistance < distance) distance = tempdistance;
                }

                if (distance == 0) {
                   throw new Exception ("Houston, we've had a problem here!");
                }

                business.setDistance(Math.round(distance)); //Rounding off the distance to reduce the over-head for processing.
                businessInfoMap.put(business.getBusinessId(), business);
            }
            MONGODB_CONNECTION_INTERFACE.getCheckInInformation(collections.get(CHECKIN_COLLECTION_NAME), businessInfoMap);

            List<Reviews> reviews = MONGODB_CONNECTION_INTERFACE.getListOfReviewsForBusinessId(collections.get(REVIEW_COLLECTION_NAME), businessInfoMap);

            Map<String, UserInfo> users = new HashMap<String, UserInfo>();
            if (reviews != null && !reviews.isEmpty()) {
                users = MONGODB_CONNECTION_INTERFACE.populateUserInfo(collections.get(USER_COLLECTION_NAME), reviews);
             }


            for (Reviews review : reviews) {
                BusinessInfo bi = businessInfoMap.get(review.getBusinessId());

                bi.setNumber_of_reviews(bi.getNumber_of_reviews() + 1);
                bi.setNumber_of_users(bi.getNumber_of_users() + 1);


                UserInfo user = users.get(review.getUserId());

                SimpleDateFormat df = new SimpleDateFormat("yyyy");
                String review_year = df.format(review.getReview_date());

                if (user.getYearsOfElite() != null && !user.getYearsOfElite().isEmpty()) {
                   if(user.getYearsOfElite().contains(review_year)) {
                   review.setEliteYearReview(true);
                   bi.setNumber_elite_user_review_count(bi.getNumber_elite_user_review_count() + 1);
                 }
                }

                Date currentDate = new Date();
                Calendar startCalendar = new GregorianCalendar();
                startCalendar.setTime(review.getReview_date());
                Calendar endCalendar = new GregorianCalendar();
                endCalendar.setTime(currentDate);

                int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
                int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);

                if (diffMonth <= 12) {
                    bi.incrementReviewCount(1);
                } else if (diffMonth <= 24 && diffMonth >= 13) {
                   bi.incrementReviewCount(2);
                } else {
                   bi.incrementReviewCount(3);
                }

                bi.setTotal_cool_review(bi.getTotal_cool_review() + review.getCool_votes());
                bi.setTotal_funny_review(bi.getTotal_funny_review() + review.getFunny_votes());
                bi.setTotal_useful_review(bi.getTotal_useful_review() + review.getUseful_votes());

                bi.setTotal_cool_user(bi.getTotal_cool_user() + user.getCool_votes_user());
                bi.setTotal_funny_user(bi.getTotal_funny_user() + user.getFunny_votes_user());
                bi.setTotal_useful_user(bi.getTotal_useful_user() + user.getUseful_votes_user());

                bi.setTotal_star_user(bi.getTotal_star_user() + user.getStars_user());
                bi.setTotal_stars_review(bi.getTotal_stars_review() + review.getStars());
                bi.setTotal_review_length(bi.getTotal_review_length() + review.getReviewLength());

                review.setUserInfo(user);
                bi.addReview(review);
                businessInfoMap.put(review.getReviewId(), bi);
            }

            HELPER.generateCSVdataForBusiness(businessList, criteria);
        }

    }

}
