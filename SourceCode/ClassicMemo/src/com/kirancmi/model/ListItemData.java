package com.kirancmi.model;

public class ListItemData {
	
	private String szTitle;
	public String getTitle() {
		return szTitle;
	}
	public void setTitle(String szTitle) {
		this.szTitle = szTitle;
	}

	private String szDateModified;
	public String getDateModified() {
		return szDateModified;
	}
	public void setDateModified(String szDateModified) {
		this.szDateModified = szDateModified;
	}
	
	public ListItemData()
	{
		szTitle = "";
		szDateModified = "";
	}
	
	public ListItemData(String szTitle, String szDateModified)
	{
		this.szTitle = szTitle;
		this.szDateModified = szDateModified;
	}
}
