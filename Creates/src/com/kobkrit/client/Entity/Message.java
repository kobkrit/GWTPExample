package com.kobkrit.client.Entity;

import java.io.Serializable;

import com.googlecode.objectify.annotation.Entity;

@Entity
public class Message extends DatastoreObject implements Serializable{
	private static final long serialVersionUID = 1L;
	String text;
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Message() {}
	
	public String toString() {
		return this.getClass().getName() +"{"
				+ "id=" + getId()
				+ "text=" + text
				+ "}";
	}
}
