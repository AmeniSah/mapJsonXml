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

// This class represents the DataFile values: name, contentType, fileSize, Identifier...

import org.json.simple.JSONObject;

public class DataFileModel {

    long id;
    String filename;
    String contentType;
    long filesize;
    String storageIdentifier;
    String originalFormatLabel;
    long rootDataFileId;
    String md5;
    

    public long getId() {
        return id;
    }

    public String getFilename() {
        return filename;
    }

    public String getContentType() {
        return contentType;
    }

    public long getFilesize() {
        return filesize;
    }

    public String getStorageIdentifier() {
        return storageIdentifier;
    }

    public String getOriginalFormatLabel() {
        return originalFormatLabel;
    }

    public long getRootDataFileId() {
        return rootDataFileId;
    }

    public String getMd5() {
        return md5;
    }

    
    DataFileModel(JSONObject dataFile) {
       
            id = (dataFile.get("id") != null) ? (long) dataFile.get("id") : -1;
            filename = (dataFile.get("filename") != null) ? (String) dataFile.get("filename") : "";
            contentType = (dataFile.get("contentType") != null) ? (String) dataFile.get("contentType") : "";
            filesize = (dataFile.get("filesize") != null) ? (long) dataFile.get("filesize") : -1;
            storageIdentifier = (dataFile.get("storageIdentifier") != null) ? (String) dataFile.get("storageIdentifier") : "";
            originalFormatLabel = (dataFile.get("originalFormatLabel") != null) ? (String) dataFile.get("originalFormatLabel") : "";
            rootDataFileId = (dataFile.get("rootDataFileId") != null) ? (long) dataFile.get("rootDataFileId") : -1;
            md5 = (dataFile.get("md5") != null) ? (String) dataFile.get("md5") : "";
            
        
    }

    public boolean isEmpty() {
        return "".equals(md5) && "".equals(storageIdentifier) && filesize == -1 && "".equals(contentType) && id == -1 && rootDataFileId == -1 && "".equals(originalFormatLabel) && "".equals(filename);
    }
}
