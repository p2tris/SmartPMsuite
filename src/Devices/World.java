package Devices;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class World extends JPanel implements ActionListener{
	
	private JFrame frame = null;
	private WorldInfo worldInfo = null;
	private Map<String, String> positions;
	private Map<String, String> services;
	
	//wumpus world stuff
	public int robotX=-1;
	public int robotY=-1;
	int maxX, maxY;
	
	public int refresh_counter = 0;
	
	//gui stuff
	JLabel labels[][];
	
	public World(int maxX, int maxY, Dimension dim){
		
	    frame = new JFrame( "Simulator Environment");
  		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
  		frame.setSize(550, 550 + 35 /* allow room for Frame bar */);
  		//frame.setSize(750, 550 + 35 /* allow room for Frame bar */);
		frame.addWindowListener(
  		  new WindowAdapter(){     		
     		public void windowClosing( WindowEvent e ){
        		System.exit(0);
        	}
     	  } 
  		); 

  		frame.validate();  
		
		
		positions = new HashMap<String, String>();
		services = new HashMap<String, String>();
		buildLocations();
		buildServicePosition();

		
		this.maxX = maxX;
		this.maxY = maxY;
		int width = dim.width;
		int height = dim.height;
		
		//set up the border and size
		this.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Map of the world"),
                        BorderFactory.createEmptyBorder(5,5,5,5)));
		this.setPreferredSize(dim);
		
		//set up cell properties
		int cellWidth = (width-10)/maxY;
		int cellHeight = (width-10)/maxX;
		int cell = Math.min(cellWidth, cellHeight);
		Dimension d = new Dimension(cell, cell);
		Font f = new Font(null,Font.BOLD,cell/8);
		
		//set up the grid
	  	labels = new JLabel[maxX][maxY];
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		for(int i=0; i<maxX; i++)
			for(int j=0; j<maxY; j++){
				c.gridx = i;
				c.gridy = maxY-j;
				//String id = "(" + i + "," + j + ")";
				String id = "";
				labels[i][j] = new JLabel(id,SwingConstants.CENTER); 
				labels[i][j].setFont(f);
				labels[i][j].setOpaque(true);
				labels[i][j].setBackground(Color.white);
				labels[i][j].setPreferredSize(d);
				labels[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
				add(labels[i][j], c);
			}
		
		createInitialSituation();
		
		//worldInfo = new WorldInfo(new Dimension(200,500));
		refreshConnectivityToDefault(0, 0);
		//frame.add(worldInfo, BorderLayout.WEST);
	    frame.add(this, BorderLayout.CENTER);
	    frame.setLocation(610, 0);
	    frame.setVisible(true);


	}

	
	private void createInitialSituation() {
		
		Iterator it = positions.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        String pos = (String) pairs.getValue();
	        String loc = pos.substring(4,7);
	        String [] sfinal = loc.split(",");
	        setCellHeader(Integer.parseInt(sfinal[0]),Integer.parseInt(sfinal[1]),(String)pairs.getKey());
	    }
	    	refreshMap();
	    	
	    }
	
	public void changeServicePosition(String sr,String pos){

		String p = positions.get(pos);
		services.remove(sr);
		services.put(sr,p);
		refreshMap();
				
	}
	
	private void refreshMap(){
		resetCells();
        updateServicesPositions();
        finalizeCellText();
        frame.validate();
	frame.repaint();
	}
	
	private void finalizeCellText(){
		for(int i=0; i<maxX; i++)
			for(int j=0; j<maxY; j++){
				if(labels[i][j].getText().contains("html")) {
					String text = labels[i][j].getText() + "</html>";
					labels[i][j].setText(text);
				}
			}
	}
	
	private void resetCells(){
		for(int i=0; i<maxX; i++)
			for(int j=0; j<maxY; j++){
				if(labels[i][j].getText().contains("html")) {
					String text = labels[i][j].getText();
					int ind = text.lastIndexOf("</font>");
					text = text.substring(0,ind+7);
					labels[i][j].setText(text);
				}
			}
	}
	
	private void buildServicePosition() {
		services.put("act1", "loc(0,0)");
		services.put("act2", "loc(0,0)");
		services.put("act3", "loc(0,0)");
		services.put("act4", "loc(0,0)");
		services.put("rb1", "loc(0,0)");
		services.put("rb2", "loc(0,0)");
	}
	
	private void buildLocations() {
	
	/*
	   Vector<String> v = new Vector<String>();
	   FileReader fr;
	   try {
		fr = new FileReader("locations.txt");
	    BufferedReader br = new BufferedReader(fr);
	    String line;
	    int i=0;
	    while ((line = br.readLine()) != null) {
		   v.add(line);
		   i++;
	    }
	    br.close();
	    fr.close();
	    }
	    catch (Exception e) { e.printStackTrace();}
	    
	    Vector<String> occ = new Vector<String>();
	    
	    occ.add("loc(1,2)");
	    
	    for(int i=0;i<v.size();i++){
			if(((String)v.get(i)).equalsIgnoreCase("loc(1,2)"))
				occ.removeElementAt(occ.indexOf("loc(1,2)"));
				}
	    
		for(int i=0;i<v.size();i++){
			positions.put("coach"+(i+1), v.get(i));
			}
		
		for(int i=v.size(),j=0;j<occ.size();i++,j++){
			positions.put("poi"+(j+1), "loc(1,2)");
     		}
		
		positions.put("startingPoint","loc(0,0)");
		//System.out.println(positions);
		*/
	
	positions.put("loc00","loc(0,0)");
	positions.put("loc01","loc(0,1)");
	positions.put("loc02","loc(0,2)");
	positions.put("loc03","loc(0,3)");
	
	positions.put("loc10","loc(1,0)");
	positions.put("mountain-A","loc(1,1)");
	positions.put("mountain-B","loc(1,2)");
	positions.put("loc13","loc(1,3)");
	
	positions.put("loc20","loc(2,0)");
	positions.put("mountain-C","loc(2,1)");
	positions.put("mountain-D","loc(2,2)");
	positions.put("loc23","loc(2,3)");
	
	positions.put("loc30","loc(3,0)");
	positions.put("loc31","loc(3,1)");
	positions.put("loc32","loc(3,2)");
	positions.put("loc33","loc(3,3)");
	    
	}
	
	private void updateServicesPositions() {
		
		Iterator it = services.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        String pos = (String) pairs.getValue();
	        String loc = pos.substring(4,7);
	        String [] sfinal = loc.split(",");
	        setCell(Integer.parseInt(sfinal[0]),Integer.parseInt(sfinal[1]),(String)pairs.getKey());
	}
	}
	
	
	
	private boolean setCell(int x, int y, String data){
		if (x<0 || x>labels.length || y<0 || y>labels[0].length)
			return false;
		else{
			String text = labels[x][y].getText();
			text = text + "<br>"+data;
			labels[x][y].setText(text);
			
			if(data.contains("rb")){
				labels[x][y].setBackground(new Color(173,227,223));
				updateConnectivity(x,y);
			}
         	return true;
		}
	}
	
	private void updateConnectivity(int x, int y){
		
		if(refresh_counter==0){
			refreshConnectivityToDefault(0, 0);
			refresh_counter=1;
		}
		else if (refresh_counter==1) refresh_counter=0;
			
		for(int i=x-1; i<=x+1; i++)
			for(int j=y-1; j<=y+1; j++){
				  if(j<maxY && i<maxX && i>=0 && j>=0){
				  labels[i][j].setBackground(new Color(173,227,223));
				}
			}
		
		/*
		for(int i=0; i<maxX; i++)
			for(int j=0; j<maxY; j++){
				  labels[i][j].setBackground(Color.white);
				}
		
		for(int i=x-2; i<=x+2; i++)
			for(int j=y-2; j<=y+2; j++){
				  if(j<maxY && i<maxX && i>=0 && j>=0){
				  System.out.println(i + " CAZZO " + j);
				  labels[i][j].setBackground(new Color(173,227,223));
				}
			}*/
	}
	
	private void refreshConnectivityToDefault(int x, int y){
		
		for(int i=0; i<maxX; i++)
			for(int j=0; j<maxY; j++){
				  labels[i][j].setBackground(Color.white);
				}
		
		for(int i=x-2; i<=x+2; i++)
			for(int j=y-2; j<=y+2; j++){
				  if(j<maxY && i<maxX && i>=0 && j>=0){
				  labels[i][j].setBackground(new Color(173,227,223));
				}
			}
	}
	
	private boolean setCellHeader(int x, int y, String data){
		if (x<0 || x>labels.length || y<0 || y>labels[0].length)
			return false;
		else{
			labels[x][y].setText("<html><font color=red><i><u>"+data+"</u></i></font></html>");
			
		    return true;
		}
	}
	

	

	public void reset(){
		for(int i=0; i<maxX; i++)
			for(int j=0; j<maxY; j++){
				labels[i][j].setText("");
				labels[i][j].setBackground(Color.white);
			}
	}
	
	public void appendInfo(String str){
		worldInfo.appendInfo(str);
	}
	
	public void actionPerformed(ActionEvent e){
	}
	
}

