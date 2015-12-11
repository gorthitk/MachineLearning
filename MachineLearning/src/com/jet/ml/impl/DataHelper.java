
package com.jet.ml.impl;

import java.util.ArrayList;
import java.util.List;

import com.jet.ml.model.Location;
import com.jet.ml.model.SearchCriteria;

/**
 * Title: YelpTrainingDataCreator.java<br>
 * Description: <br>
 * Created: 10-Dec-2015<br>
 * Copyright: Copyright (c) 2015<br>
 * @author Teja Sasank Gorthi (jet.sasank@gmail.com)
 */
public class DataHelper {

    private List<SearchCriteria> criteria;

    /**
     * @return the criteria
     */
    public List<SearchCriteria> getCriteria() {
        populateSearchCriteria(); 
        return criteria;
    }

    /**
     * @param criteria the criteria to set
     */
    public void setCriteria(List<SearchCriteria> criteria) {
        this.criteria = criteria;
    }

    private void populateSearchCriteria() {
       criteria = new ArrayList<SearchCriteria>();
       SearchCriteria sc = new SearchCriteria();

       //State of Pennsylvania (CMU, University of Pennsylvania) 
       sc.setState("PA");
       sc.addLocation(new Location(40.4433, -79.9436)); //CMU
       sc.addLocation(new Location(39.9522, -75.1954)); // University of Pennsylvania
       criteria.add(sc);

       //State of Wisconsin (University of Wisconsin - Madison)
       sc = new SearchCriteria();
       sc.setState("WI");
       sc.addLocation(new Location(43.0826,-89.4170)); //University of Wisconsin - Madison
       criteria.add(sc);

       //State of Illinois (University of Illinois - Urbana-Champaign)
       sc = new SearchCriteria();
       sc.setState("IL");
       sc.addLocation(new Location(40.1105, -88.2284)); //University of Illinois - Urbana-Champaign
       criteria.add(sc);
    }
}
