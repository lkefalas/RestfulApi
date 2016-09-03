package org.labros.rest.Model;

import java.util.ArrayList;
import org.codehaus.jackson.annotate.JsonProperty;

public class ResponseList {
	@JsonProperty("xxx")
    private ArrayList<Object> contacts;

    public ArrayList<Object> getList() {
        return contacts;
    }

    public void setList(ArrayList<Object> contact) {
        this.contacts = contact;
    }
}