import java.text.SimpleDateFormat;
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

        DB db = MONGODB_CONNECTION_INTERFACE.connecttoDB();
        Map<String, DBCollection> collections = new HashMap<String, DBCollection>();

        collections.put(BUSINESS_COLLECTION_NAME, MONGODB_CONNECTION_INTERFACE.getCollection(BUSINESS_COLLECTION_NAME, db));
        collections.put(REVIEW_COLLECTION_NAME, MONGODB_CONNECTION_INTERFACE.getCollection(REVIEW_COLLECTION_NAME, db));
        collections.put(USER_COLLECTION_NAME, MONGODB_CONNECTION_INTERFACE.getCollection(USER_COLLECTION_NAME, db));
        collections.put(CHECKIN_COLLECTION_NAME, MONGODB_CONNECTION_INTERFACE.getCollection(CHECKIN_COLLECTION_NAME, db));

        populate_data_for_business(collections);
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
