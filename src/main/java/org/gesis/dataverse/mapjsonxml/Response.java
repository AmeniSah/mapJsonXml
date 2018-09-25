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

// This class contains the first elements of the json fine: status and data
import org.json.simple.JSONObject;

public class Response {

    String status;
    ResponseModel data;

    public Response(String status, ResponseModel data) {
        this.status = status;
        this.data = data;
    }

    public Response(JSONObject object) {
 
            status = object.get("status") != null ? (String) object.get("status") : "";
            data = new ResponseModel((JSONObject) object.get("data"));
        
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ResponseModel getData() {
        return data;
    }

    public void setData(ResponseModel data) {
        this.data = data;
    }

    public boolean isEmpty() {
        return "".equals(status) && data.isEmpty();
    }
}
