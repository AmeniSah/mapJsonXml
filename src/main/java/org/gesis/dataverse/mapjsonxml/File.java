/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gesis.dataverse.mapjsonxml;

/**
 *
 * @author ameni
 */

// This class is for the files' details: label, use, version, ID, category

import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class File {

   String label;
    boolean restricted;
    long version;
    double datasetVersionId;
    ArrayList<String> categories = new ArrayList<>();
    DataFileModel dataFileModel;

    File(JSONObject file) {
      
            label = file.get("label") != null ? (String) file.get("label") : "";
            restricted = file.get("restricted") != null ? (boolean) file.get("restricted") : false;
            version = file.get("version") != null ? (long) file.get("version") : -1;
            datasetVersionId = file.get("datasetVersionId") != null ? (long) file.get("datasetVersionId") : -1;
            if (file.get("categories") != null) {
                if (!((JSONArray) file.get("categories")).isEmpty()) {
                    for (Object categorie : (JSONArray) file.get("categories")) {
                        categories.add((String) categorie);
                    }
                }

            }

            dataFileModel = new DataFileModel((JSONObject) file.get("dataFile"));
        

    }

    public String getLabel() {
        return label;
    }

    public boolean isRestricted() {
        return restricted;
    }

    public long getVersion() {
        return version;
    }

    public double getDatasetVersionId() {
        return datasetVersionId;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public DataFileModel getDataFileModel() {
        return dataFileModel;
    }

    public boolean isEmpty() {
        return restricted == false && "".equals(label) && version == -1 && datasetVersionId == -1 && categories.isEmpty() && dataFileModel.isEmpty();
    }

}
