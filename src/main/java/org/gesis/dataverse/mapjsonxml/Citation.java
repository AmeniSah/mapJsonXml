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

// This class is dedicated to Citation that contains all the fields of metadataBlocks (author, publication, title...)
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Citation {

    String displayName;
    ArrayList<Field> fields = new ArrayList<>();

    Citation(JSONObject citation) {
 
            displayName = (String) citation.get("displayName");
            if (citation.get("fields") != null) {
                initFields((JSONArray) citation.get("fields"));
            }
        
    }

    private void initFields(JSONArray jsonArray) {
        if (!jsonArray.isEmpty()) {
            for (Object obj : jsonArray) {
                Field currentField = new Field((JSONObject) obj);
                fields.add(currentField);
            }
        }
    }

    public String getDisplayName() {
        return displayName;
    }

    public ArrayList<Field> getFields() {
        return fields;
    }

    public boolean isEmpty() {
        return "".equals(displayName) && fields.isEmpty();
    }
}
