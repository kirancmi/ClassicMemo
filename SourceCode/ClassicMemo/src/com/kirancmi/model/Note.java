package com.kirancmi.model;

import java.io.Serializable;

public class Note implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String szTitle;
	public String getTitle() {
		return szTitle;
	}
	public void setTitle(String szTitle) {
		this.szTitle = szTitle;
		bIsModified = true;
	}

	private String szDateCreated;
	public String getDateCreated() {
		return szDateCreated;
	}
	public void setDateCreated(String szDateCreated) {
		this.szDateCreated = szDateCreated;
	}

	private String szDateModified;
	public String getDateModified() {
		return szDateModified;
	}
	public void setDateModified(String szDateModified) {
		this.szDateModified = szDateModified;
		bIsModified = true;
	}
	
	private String szNoteContent;	
	public String getNoteContent() {
		return szNoteContent;
	}
	public void setNoteContent(String szNoteContent) {
		this.szNoteContent = szNoteContent;
		bIsModified = true;
	}

	private boolean bIsModified = false;
	public boolean IsModified() {
		return bIsModified;
	}

	public Note() 
	{
		super();
		this.szTitle = "";
		this.szDateCreated = "";
		this.szDateModified = "";
		this.szNoteContent = "";
		this.bIsModified = false;
	}
	
	public Note(String szTitle, String szNoteContent, String szDateCreated, String szDateModified) 
	{
		super();
		this.szTitle = szTitle;
		this.szDateCreated = szDateCreated;
		this.szDateModified = szDateModified;
		this.szNoteContent = szNoteContent;
		this.bIsModified = true;
	}
	
}
