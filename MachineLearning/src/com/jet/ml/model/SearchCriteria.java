package com.jet.ml.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Title: SearchCriteria.java<br>
 * Description: <br>
 * Created: 06-Dec-2015<br>
 * Copyright: Copyright (c) 2015<br>
 * @author Teja Sasank Gorthi (jet.sasank@gmail.com)
 */
public class SearchCriteria implements Serializable {

   private static final long serialVersionUID = 1L;
   private String state;
   private List<Location> geoLocations;

   /**
   * @return the geoLocations
   */
   public List<Location> getGeoLocations() {
      return geoLocations;
   }

   /**
   * @param geoLocations the geoLocations to set
   */
   private void setGeoLocations(List<Location> geoLocations) {
       this.geoLocations = geoLocations;
   }


   /**
   * @return the city
   */
   public String getState() {
     return state;
  }

   /**
   * @param city the city to set
   */
   public void setState(String state) {
      this.state = state;
   }

   /**
   * @param loc
   */
   public void addLocation(Location loc) { 
      if (getGeoLocations() == null) {
          setGeoLocations(new ArrayList<Location>());
      }
      getGeoLocations().add(loc);
   }
}
