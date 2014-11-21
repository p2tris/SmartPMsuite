package Devices;

import java.util.Iterator;
import java.util.Vector;

public class Workitem {
	
	String task = null;
	String id = null;
	Vector inputsList = null;
	Vector outputsList = null;
	Vector allDataTypes = null;
	Vector outputTypes = null;
	private String integer_type[] = {"integer_type"};
	
	
	public Workitem(String task,String id,Vector inputsList,Vector outputsList,Vector allDataTypes) {
		this.task = task;
		this.id = id;
		this.inputsList = inputsList;
		this.outputsList =outputsList;
		this.allDataTypes = allDataTypes;
		this.createOutputTypes();
	}
	
	public String getTask() {
		return this.task;
	}
	
	public String getId() {
		return this.id;
	}
	
	public Vector getInputsList() {
		return this.inputsList;
	}
	
	private void createOutputTypes() {
		outputTypes = new Vector();

		if(isWrkOutputListEmpty()){
		String[] type = new String[1];
		type[0] = "no_outputs";
		outputTypes.addElement(type);
		}
		else{
		Iterator it = outputsList.iterator();
		while(it.hasNext()) {
			boolean found = false;
			String out = (String)it.next();
			Iterator it2 = allDataTypes.iterator();
			while(it2.hasNext()) {
				String[] singletype = (String[]) it2.next();
				for(int i=0;i<singletype.length;i++){
					if(singletype[i].equalsIgnoreCase(out)){
						outputTypes.addElement(singletype);
						found=true;
						break;
					}
				}
				if(found==true)
				break;
			}
			if(found==false){
				String[] type = new String[1];
				type[0] = "integer_type";
				outputTypes.addElement(type);
			}
		}
		}
	}
	
	public Vector getOutputTypes() {
		return this.outputTypes;
	}
	
	public Vector getOutputsList() {
		return this.outputsList;
	}

	public boolean isWrkInputListEmpty() {
		if(this.inputsList.elementAt(0).toString().equalsIgnoreCase("no inputs"))
			return true;
		else
			return false;
	}
	
	public boolean isWrkOutputListEmpty() {
		if(this.outputsList.elementAt(0).toString().equalsIgnoreCase("no outputs"))
			return true;
		else
			return false;
	}
}
