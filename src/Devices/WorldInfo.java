package Devices;

import java.lang.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

public class WorldInfo extends JPanel{
	public JTextPane text;
	public Style heading2Style;
	public  StyledDocument doc;
	
	public JTextArea log;

	public boolean adaptivity=false;

	WorldInfo(Dimension d){
		super(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//set up the info area
		
	
		text = new JTextPane() { 
            public boolean getScrollableTracksViewportWidth() { 
                return (getSize().width < getParent().getSize().width); 
            } 	
	    
            public void setSize(Dimension d) { 
                if (d.width < getParent().getSize().width) { 
                    d.width = getParent().getSize().width; 
                } 
                super.setSize(d); 
            } 
        }; 
        
        doc = text.getStyledDocument();
        addStylesToDocument(doc);

	
      	text.setBackground(Color.white);
      	text.setEditable(false);
      	text.setPreferredSize(new Dimension(d.width+300, d.height-190));
		JPanel infoPanel = new JPanel();
      	infoPanel.setPreferredSize(new Dimension(d.width, d.height-125));
		infoPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Action history"),
                        BorderFactory.createEmptyBorder(5,5,5,5)));
      	JScrollPane scroll = new JScrollPane(text);
        scroll.setPreferredSize(new Dimension(d.width-30, d.height-190));
      	
      	
		infoPanel.add(scroll);
      	this.add(infoPanel,c);

		//set up the log area
		log = new JTextArea(6,15);
      	log.setBackground(Color.white);
      	log.setEditable(false);
		JPanel logPanel = new JPanel();
		logPanel.setPreferredSize(new Dimension(d.width, 150));
		logPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Log"),
                        BorderFactory.createEmptyBorder(5,5,5,5)));
        logPanel.add(new JScrollPane(log));
      	c.gridx = 0;
      	c.gridy = 1;      	
      	this.add(logPanel,c);
      	     	
     	
    }
    
    public void appendLog(String s){
    	log.append(s);
    	log.setCaretPosition(log.getText().length());
    }
    
    public void resetLog(){
    	log.setText("");
    }
    
    
    public void appendInfo(String s){
    	String str = text.getText();
    	
    	if(s.contains("finishedTask"))  	{
    		if(adaptivity==false){
    		try {
    			
    			String [] sp = s.split("finishedTask");
    			String [] s1 = sp[1].split("\\(");
    			String [] s2 = s1[1].split("\\)");
    			String [] s3 = s2[0].split(",");
    			
    			doc.insertString(doc.getLength(), "finishedTask("+s3[0]+","+s3[1]+","+s3[2]+",", doc.getStyle("regular"));
    			
    			
    			if(s3[1].equalsIgnoreCase("go")){
    				if(s3[2].equalsIgnoreCase(s3[3]))
    					doc.insertString(doc.getLength(), s3[3], doc.getStyle("regular"));
    				else{
    					doc.insertString(doc.getLength(), s3[3], doc.getStyle("red"));
    					appendLog("badPos("+s3[0]+","+s3[3]+")");
    				}
    				
    			} 
    			else if(s3[1].equalsIgnoreCase("evacuate")){
    				if(s3[3].equalsIgnoreCase("true"))
    					doc.insertString(doc.getLength(), s3[3], doc.getStyle("regular"));
    				else {
    					doc.insertString(doc.getLength(), s3[3], doc.getStyle("red"));
    					appendLog("evac("+s3[2]+")");
    				}	
    			}
    			else if(s3[1].equalsIgnoreCase("takephoto")){
    				if(s3[3].equalsIgnoreCase("true"))
    					doc.insertString(doc.getLength(), s3[3], doc.getStyle("regular"));
    				else{
    					doc.insertString(doc.getLength(), s3[3], doc.getStyle("red"));
    				    appendLog("phLost("+s3[2]+")");
				}
    			}
    			else if(s3[1].equalsIgnoreCase("extinguishfire")){
    				if(s3[3].equalsIgnoreCase("ok"))
    					doc.insertString(doc.getLength(), s3[3], doc.getStyle("regular"));
    				else{
    					doc.insertString(doc.getLength(), s3[3], doc.getStyle("red"));
    				    appendLog("fire("+s3[2]+")");
				}	
    			}
    			else if(s3[1].equalsIgnoreCase("updatestatus")){
    				if(s3[3].equalsIgnoreCase("ok"))
    					doc.insertString(doc.getLength(), s3[3], doc.getStyle("regular"));
    				else{
    					doc.insertString(doc.getLength(), s3[3], doc.getStyle("red"));
    				    appendLog("fire("+s3[2]+")");
				}	
    			}
    			doc.insertString(doc.getLength(), ") \n", doc.getStyle("regular"));
    			
			} catch (BadLocationException e) {

				e.printStackTrace();
			}
    		}
    		else{
    			try {
					doc.insertString(doc.getLength(), s+"\n", doc.getStyle("blue"));
				} catch (BadLocationException e) {
			
					e.printStackTrace();
				}
    		}
    		
    	}
    	else if(s.contains("AdaptStart"))  	{
    		if(adaptivity==false)
    		try {
				doc.insertString(doc.getLength(), "Adaptation started\n", doc.getStyle("boldblue"));
				adaptivity=true;
			} catch (BadLocationException e) {

				e.printStackTrace();
			}
    	}
    	else if(s.contains("AdaptFinish"))  	{
    		if(adaptivity==true)
    		try {
    		doc.insertString(doc.getLength(), "Adaptation found\n", doc.getStyle("boldblue"));
    		adaptivity=false;
    		resetLog();
			} catch (BadLocationException e) {

				e.printStackTrace();
			}
    	}
    	else if(adaptivity==true)  	{
    		try {
    		doc.insertString(doc.getLength(), s+"\n", doc.getStyle("blue"));
		} catch (BadLocationException e) {

			e.printStackTrace();
		}
    		
    	}
    	else  	{
    		try {
				doc.insertString(doc.getLength(), s+"\n", doc.getStyle("regular"));
			} catch (BadLocationException e) {

				e.printStackTrace();
			}
    	} 

    	text.validate();

    	text.setCaretPosition(text.getText().length());
    }

    public void reset(){
    	text.setText("");
    	log.setText("");
    }
    
    protected void addStylesToDocument(StyledDocument doc) {
        //Initialize some styles.
        Style def = StyleContext.getDefaultStyleContext().
                        getStyle(StyleContext.DEFAULT_STYLE);

        Style regular = doc.addStyle("regular", def);
        StyleConstants.setFontFamily(def, "SansSerif");

        Style s = doc.addStyle("italic", regular);
        StyleConstants.setItalic(s, true);

        s = doc.addStyle("bold", regular);
        StyleConstants.setBold(s, true);

        s = doc.addStyle("small", regular);
        StyleConstants.setFontSize(s, 10);

        s = doc.addStyle("large", regular);
        StyleConstants.setFontSize(s, 16);

        s = doc.addStyle("icon", regular);
        StyleConstants.setAlignment(s, StyleConstants.ALIGN_CENTER);
        
        s = doc.addStyle("red", regular);
        StyleConstants.setForeground(s, Color.red);
        
        s = doc.addStyle("green", regular);
        StyleConstants.setForeground(s, Color.green);
        
        s = doc.addStyle("blue", regular);
        StyleConstants.setForeground(s, Color.blue);
        
        s = doc.addStyle("boldblue", regular);
        StyleConstants.setForeground(s, Color.red);
        StyleConstants.setBold(s, true);
    }




    
    
}
