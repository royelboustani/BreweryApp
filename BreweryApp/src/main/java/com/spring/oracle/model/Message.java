package com.spring.oracle.model;
 
import java.io.Serializable;
 
/*
* This is our model class and it corresponds to Brewery table in database
*/

public class Message implements Serializable{
 
	private static final long serialVersionUID = 1L;
	
    private String message = "";
 
 
 
    public Message() {
        super();
    }
    
    public Message(String message) {
        super();;
        this.message = message;

    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

 
}