package com.mxgraph.smartml.model;

public class Constants {
	
	private static String XSD_name_file;
	private static String XML_name_file;
	private static String XML_plugin_name_file = "properties/plugins.xml";
	private static boolean isDomTheoryModified;
	private static String modality_of_execution = "simulation";
	
	public static String getXSD_name_file() {
		return XSD_name_file;
	}
	public static void setXSD_name_file(String xSD_name_file) {
		XSD_name_file = xSD_name_file;
	}
	public static String getXML_name_file() {
		return XML_name_file;
	}
	public static void setXML_name_file(String xML_name_file) {
		XML_name_file = xML_name_file;
	}
	public static boolean isDomTheoryModified() {
		return isDomTheoryModified;
	}
	public static void setDomTheoryModified(boolean isModified) {
		Constants.isDomTheoryModified = isModified;
	}
	public static String getXML_plugin_name_file() {
		return XML_plugin_name_file;
	}
	public static void setXML_plugin_name_file(String xML_plugin_name_file) {
		XML_plugin_name_file = xML_plugin_name_file;
	}
	public static String getModality_of_execution() {
		return modality_of_execution;
	}
	public static void setModality_of_execution(String modality_of_execution) {
		Constants.modality_of_execution = modality_of_execution;
	}


}
