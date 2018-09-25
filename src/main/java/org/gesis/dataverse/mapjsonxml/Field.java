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

// The field could be a title, author, description... It is treated on the Main class qs this key has different values
import org.json.simple.JSONObject;

public class Field {
String typeName = "";
    boolean multiple;
    String typeClass = "";
    Object value;
    String title = "";

    Field(JSONObject field) {
    
            typeName = field.get("typeName") != null ? (String) field.get("typeName") : "";
            multiple = field.get("multiple") != null ? (boolean) field.get("multiple") : false;
            typeClass = field.get("typeClass") != null ? (String) field.get("typeClass") : "";
            value =field.get("value");
        
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public boolean isMultiple() {
        return multiple;
    }

    public void setMultiple(boolean multiple) {
        this.multiple = multiple;
    }

    public String getTypeClass() {
        return typeClass;
    }

    public void setTypeClass(String typeClass) {
        this.typeClass = typeClass;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public boolean isEmpty() {
        return "".equals(typeName) && multiple == false && "".equals(typeClass) && value == null && "".equals(title);
    }
}
