/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gesis.dataverse.mapjsonxml;

import org.apache.tika.Tika;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.json.simple.JSONObject;

/**
 *
 * @author ameni
 */

// This class is just used to do tests of the resource type using Tika Library

public class TestResourceType {

    public String resourceType(String firstFileName) {
        /* Resource Type */
        String resourceTypeValue;

        // mime type checking from files
        Tika tika = new Tika();

        String mimetype;
        mimetype = tika.detect(firstFileName);
        if (mimetype.contains("text")) {
            resourceTypeValue = "Text";

        } else if (mimetype.contains("video")) {
            resourceTypeValue = "Audiovisual";

        } else if (mimetype.contains("image")) {
            resourceTypeValue = "Image";

        } else if (mimetype.contains("audio")) {
            resourceTypeValue = "Sound";

        } else {
            resourceTypeValue = "Other";

        }
        return resourceTypeValue;
    }
    

      
      
}
