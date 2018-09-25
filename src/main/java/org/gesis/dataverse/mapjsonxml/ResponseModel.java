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

// This class is dedicated to the data properties: identifier, URL, protocol...
import org.json.simple.JSONObject;

public class ResponseModel {

   String identifier ;
    String persistentUrl ;
    String protocol ;
    String authority ;
    String publisher ;
    String publicationDate ; //yyyy-mm-dd
    LatestVersionModel latestVersion;

    ResponseModel(JSONObject data) {
        if (data != null) {
            identifier = data.get("identifier") != null ? (String) data.get("identifier") : "";
            persistentUrl = data.get("persistentUrl") != null ? (String) data.get("persistentUrl") : "";
            protocol = data.get("protocol") != null ? (String) data.get("protocol") : "";
            authority = data.get("authority") != null ? (String) data.get("authority") : "";
            publisher = data.get("publisher") != null ? (String) data.get("publisher") : "";
            publicationDate = data.get("publicationDate") != null ? (String) data.get("publicationDate") : ""; //yyyy-mm-dd
            latestVersion = new LatestVersionModel((JSONObject) data.get("latestVersion"));
        }
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getPersistentUrl() {
        return persistentUrl;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getAuthority() {
        return authority;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public LatestVersionModel getLatestVersion() {
        return latestVersion;
    }

    public boolean isEmpty() {
        return true;
    }

}
