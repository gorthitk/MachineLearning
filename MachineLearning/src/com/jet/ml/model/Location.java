package com.jet.ml.model;

import java.io.Serializable;

/**
 * Title: Location.java<br>
 * Description: <br>
 * Created: 10-Dec-2015<br>
 * Copyright: Copyright (c) 2015<br>
 * @author Teja Sasank Gorthi (jet.sasank@gmail.com)
 */
public class Location implements Serializable {

   private static final long serialVersionUID = 1L;
   private double latitude;
   private double longitude;


   public Location(double latitude, double longitude) {
     super();
     this.latitude = latitude;
     this.longitude = longitude;
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
}
