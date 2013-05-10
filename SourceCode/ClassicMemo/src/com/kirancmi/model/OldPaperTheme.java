package com.kirancmi.model;

public class OldPaperTheme implements IAppTheme {

	
	private static OldPaperTheme theme = null;
	
	//private String currentTheme = "old_paper";
	
	public static OldPaperTheme getInstance()
	{
		if(theme == null)
		{
			theme = new OldPaperTheme();
		}
		return theme;
	}
	
	@Override
	public int getActivityBkgnd() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getLayoutBkgnd() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getListBkgnd() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getButtonBkgnd() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTextviewFontFamily() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTextviewFontSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTextviewFontColor() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getButtonFontFamily() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getButtonFontSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getButtonFontColor() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMenuItemFontFamily() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMenuItemFontSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMenuItemFontColor() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getListItemFontFamily() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getListItemFontSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getListItemFontColor() {
		// TODO Auto-generated method stub
		return 0;
	}

}
