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

// This class is used to extract all the properties of the version: version number, publication date, license, terms of use...
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class LatestVersionModel {

    long id;
    long versionNumber ;
    long versionMinorNumber ;
    String versionState ;
    String productionDate ;
    String lastUpdateTime ;
    String releaseTime ;
    String createTime ;
    String license ;
    String termsOfUse ;
    String termsOfAccess ;
    Citation citation;
    ArrayList<File> files = new ArrayList<>();

    LatestVersionModel(JSONObject responseModel) {
        id = responseModel.get("id") != null ? (long) responseModel.get("id") : -1;
        versionNumber = responseModel.get("versionNumber") != null ? (long) responseModel.get("versionNumber") : -1;
        versionMinorNumber = responseModel.get("versionMinorNumber") != null ? (long) responseModel.get("versionMinorNumber") : -1;
        versionState = responseModel.get("versionState") != null ? (String) responseModel.get("versionState") : "";
        productionDate = responseModel.get("productionDate") != null ? (String) responseModel.get("productionDate") : "";
        lastUpdateTime = responseModel.get("lastUpdateTime") != null ? (String) responseModel.get("lastUpdateTime") : "";
        releaseTime = responseModel.get("releaseTime") != null ? (String) responseModel.get("releaseTime") : "";
        createTime = responseModel.get("createTime") != null ? (String) responseModel.get("createTime") : "";
        license = responseModel.get("license") != null ? (String) responseModel.get("license") : "";
        termsOfUse = responseModel.get("termsOfUse") != null ? (String) responseModel.get("termsOfUse") : "";
        termsOfAccess = responseModel.get("termsOfAccess") != null ? (String) responseModel.get("termsOfAccess") : "";
        citation = new Citation((JSONObject) ((JSONObject) responseModel.get("metadataBlocks")).get("citation"));
        if (responseModel.get("files") != null) {
            initFiles((JSONArray) responseModel.get("files"));
        }
    }

    private void initFiles(JSONArray jsonArray) {
        if (!jsonArray.isEmpty()) {
            for (Object obj : jsonArray) {
                JSONObject currentFile = (JSONObject) obj;
                files.add(new File(currentFile));
            }
        }

    }

    public long getId() {
        return id;
    }

    public long getVersionNumber() {
        return versionNumber;
    }

    public long getVersionMinorNumber() {
        return versionMinorNumber;
    }

    public String getVersionState() {
        return versionState;
    }

    public String getProductionDate() {
        return productionDate;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getLicense() {
        return license;
    }

    public String getTermsOfUse() {
        return termsOfUse;
    }

    public String getTermsOfAccess() {
        return termsOfAccess;
    }

    public Citation getCitation() {
        return citation;
    }

    public ArrayList<File> getFiles() {
        return files;
    }

    public boolean isEmpty() {
        return id == -1 && versionNumber == -1 && versionMinorNumber == -1 && "".equals(versionState) && "".equals(productionDate) && "".equals(lastUpdateTime) && "".equals(releaseTime) && "".equals(createTime) && "".equals(license) && "".equals(termsOfUse) && "".equals(termsOfAccess) && citation.isEmpty() && files.isEmpty();
    }
}
