package com.sam;

import java.util.ResourceBundle;

public class PropertyLoader {
	
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("api_testdata");
	
	public static String getFirstcategory() {
		return RESOURCE_BUNDLE.getString("category1");
	}
	
	public static String getSecondcategory() {
		return RESOURCE_BUNDLE.getString("category2");
	}
	
	public static String getThirdcategory() {
		return RESOURCE_BUNDLE.getString("category3");
	}
	
	public static String getTitleOne() {
		return RESOURCE_BUNDLE.getString("title1");
	}
	
	public static String getTitleTwo() {
		return RESOURCE_BUNDLE.getString("title2");
	}
	
	public static String getTitleThree() {
		return RESOURCE_BUNDLE.getString("title3");
	}
	
	public static String getTitleFour() {
		return RESOURCE_BUNDLE.getString("title4");
	}
	
	public static String getTitleFive() {
		return RESOURCE_BUNDLE.getString("title5");
	}
	

}
