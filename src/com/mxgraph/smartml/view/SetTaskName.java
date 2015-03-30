package com.mxgraph.smartml.view;


import java.awt.*;    
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import org.freixas.jwizard.JWizardDialog;
import org.freixas.jwizard.JWizardPanel;

import com.mxgraph.examples.swing.editor.BasicGraphEditor;
import com.mxgraph.model.mxCell;
import com.mxgraph.smartml.control.H_MainFrame;
import com.mxgraph.smartml.model.SpringUtilities;
import com.mxgraph.smartml.model.XMLParser;
import com.mxgraph.smartml.view.SetInitialState.Step0;
import com.mxgraph.smartml.view.SetInitialState.Step1;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxMultiplicity;


public class SetTaskName extends JWizardDialog{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public mxCell selectedCell = null;
	public mxGraph graph = null;
	public String selected_task_name = "";
	public Vector argument_types_vector = new Vector();
	public Vector argument_names_vector = new Vector();
	
	public JLabel lblArgName1 = new JLabel("Arg#1");
	public JComboBox<String> arg_type_cb1;
	
	public JLabel lblArgName2 = new JLabel("Arg#2");
	public JComboBox<String> arg_type_cb2;
	
	public JLabel lblArgName3 = new JLabel("Arg#3");
	public JComboBox<String> arg_type_cb3;
	
	public JLabel lblArgName4  = new JLabel("Arg#4");
	public JComboBox<String> arg_type_cb4;

	public JLabel lblArgName5 = new JLabel("Arg#5");
	public JComboBox<String> arg_type_cb5;
	
	public String final_task;
	
	public SetTaskName(mxGraph gr, mxCell cell){
		super();
		selectedCell = cell;
		graph = gr;
		//this.editor = editor;
		initComponent();
		initHandler();
		
	}
	
	public void initHandler() {
		//_handler = new H_MainFrame(this);
		
	}

	public void initComponent(){
		
		setModal(true);
		setTitle("TASKS REPOSITORY");
		setSize(200, 300);
		setResizable(true);
		addWizardPanel(new Step0());
		addWizardPanel(new Step1());
	}
	
	public class Step0 extends JWizardPanel {
		
		public SpringLayout springLayout;
		public JPanel panel;
		public JComboBox<String> tasks_names = new JComboBox(XMLParser.getTaskNames(false));
		
		
		
		public Step0() {
		setStepTitle("Select Task Name");
		
		JLabel tskName = new JLabel("Name: ");
	
		springLayout = new SpringLayout();
		setLayout(springLayout);
		
		panel = new JPanel();
		panel.setLayout(new FlowLayout());
		
		springLayout.putConstraint(SpringLayout.NORTH, panel, 41, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, panel, 0, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, panel, 380, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.EAST, panel, 150, SpringLayout.WEST, this);
		add(panel);
		
		panel.add(tskName);
		panel.add(tasks_names);
		
		// Set the previous (none) and next steps
	    setBackStep(-1);
		setNextStep(1);
		
		}
		
		protected void next() {
			
			selected_task_name = (String) tasks_names.getSelectedItem();
			
			argument_types_vector = XMLParser.getInputParameterTypesOfTask(selected_task_name);
			argument_names_vector = XMLParser.getInputParameterNamesOfTask(selected_task_name);
			System.out.println(argument_types_vector);
			
			super.next();
		}
		
	}
	
	public class Step1 extends JWizardPanel {
		
		public SpringLayout sl_panel_1;
		public SpringLayout springLayout;
		public JPanel panel_1;
		

		
		
		protected void makingVisible(){
			
			if(argument_types_vector.size()==1){
				arg_type_cb1.setEnabled(true);
				lblArgName1.setEnabled(true);
				lblArgName1.setVisible(true);
				lblArgName1.setText("Arg#1 ("+argument_names_vector.elementAt(0)+")");
				arg_type_cb1.removeAllItems();
				Vector v_1 = XMLParser.getObjectNoPRTof((String)argument_types_vector.elementAt(0));
				for(int i = 0; i<v_1.size(); i++)
					arg_type_cb1.addItem((String) v_1.elementAt(i));
				
				arg_type_cb2.removeAllItems();
				arg_type_cb2.setEnabled(false);
				lblArgName2.setText("Arg#2");
				lblArgName2.setEnabled(false);
				
				arg_type_cb3.removeAllItems();
				arg_type_cb3.setEnabled(false);
				lblArgName3.setText("Arg#3");
				lblArgName3.setEnabled(false);
				
				arg_type_cb4.removeAllItems();
				arg_type_cb4.setEnabled(false);
				lblArgName4.setText("Arg#4");
				lblArgName4.setEnabled(false);
				
				arg_type_cb5.removeAllItems();
				arg_type_cb5.setEnabled(false);
				lblArgName5.setText("Arg#5");
				lblArgName5.setEnabled(false);
			
			}
		
			if(argument_types_vector.size()==2){
				arg_type_cb1.setEnabled(true);
				lblArgName1.setEnabled(true);
				lblArgName1.setVisible(true);
				lblArgName1.setText("Arg#1 ("+argument_names_vector.elementAt(0)+")");
				arg_type_cb1.removeAllItems();
				Vector v_1 = XMLParser.getObjectNoPRTof((String)argument_types_vector.elementAt(0));
				for(int i = 0; i<v_1.size(); i++)
					arg_type_cb1.addItem((String) v_1.elementAt(i));
				
				arg_type_cb2.setEnabled(true);
				lblArgName2.setEnabled(true);
				lblArgName2.setVisible(true);
				lblArgName2.setText("Arg#2 ("+argument_names_vector.elementAt(1)+")");
				arg_type_cb2.removeAllItems();
				Vector v_2 = XMLParser.getObjectNoPRTof((String)argument_types_vector.elementAt(1));
				for(int i = 0; i<v_2.size(); i++)
					arg_type_cb2.addItem((String) v_2.elementAt(i));
				
				arg_type_cb3.removeAllItems();
				arg_type_cb3.setEnabled(false);
				lblArgName3.setText("Arg#3");
				lblArgName3.setEnabled(false);
				
				arg_type_cb4.removeAllItems();
				arg_type_cb4.setEnabled(false);
				lblArgName4.setText("Arg#4");
				lblArgName4.setEnabled(false);
				
				arg_type_cb5.removeAllItems();
				arg_type_cb5.setEnabled(false);
				lblArgName5.setText("Arg#5");
				lblArgName5.setEnabled(false);
				
				
			}
			
			if(argument_types_vector.size()==3){
				arg_type_cb1.setEnabled(true);
				lblArgName1.setEnabled(true);
				lblArgName1.setVisible(true);
				lblArgName1.setText("Arg#1 ("+argument_names_vector.elementAt(0)+")");
				arg_type_cb1.removeAllItems();
				Vector v_1 = XMLParser.getObjectNoPRTof((String)argument_types_vector.elementAt(0));
				for(int i = 0; i<v_1.size(); i++)
					arg_type_cb1.addItem((String) v_1.elementAt(i));
				
				arg_type_cb2.setEnabled(true);
				lblArgName2.setEnabled(true);
				lblArgName2.setVisible(true);
				lblArgName2.setText("Arg#2 ("+argument_names_vector.elementAt(1)+")");
				arg_type_cb2.removeAllItems();
				Vector v_2 = XMLParser.getObjectNoPRTof((String)argument_types_vector.elementAt(1));
				for(int i = 0; i<v_2.size(); i++)
					arg_type_cb2.addItem((String) v_2.elementAt(i));
				
				arg_type_cb3.setEnabled(true);
				lblArgName3.setEnabled(true);
				lblArgName3.setVisible(true);
				lblArgName3.setText("Arg#3 ("+argument_names_vector.elementAt(2)+")");
				arg_type_cb3.removeAllItems();
				Vector v_3 = XMLParser.getObjectNoPRTof((String)argument_types_vector.elementAt(2));
				for(int i = 0; i<v_3.size(); i++)
					arg_type_cb3.addItem((String) v_3.elementAt(i));
		
				arg_type_cb4.removeAllItems();
				arg_type_cb4.setEnabled(false);
				lblArgName4.setText("Arg#4");
				lblArgName4.setEnabled(false);
				
				arg_type_cb5.removeAllItems();
				arg_type_cb5.setEnabled(false);
				lblArgName5.setText("Arg#5");
				lblArgName5.setEnabled(false);
				
				
			}
			
			if(argument_types_vector.size()==4){
				arg_type_cb1.setEnabled(true);
				lblArgName1.setEnabled(true);
				lblArgName1.setVisible(true);
				lblArgName1.setText("Arg#1 ("+argument_names_vector.elementAt(0)+")");
				arg_type_cb1.removeAllItems();
				Vector v_1 = XMLParser.getObjectNoPRTof((String)argument_types_vector.elementAt(0));
				for(int i = 0; i<v_1.size(); i++)
					arg_type_cb1.addItem((String) v_1.elementAt(i));
				
				arg_type_cb2.setEnabled(true);
				lblArgName2.setEnabled(true);
				lblArgName2.setVisible(true);
				lblArgName2.setText("Arg#2 ("+argument_names_vector.elementAt(1)+")");
				arg_type_cb2.removeAllItems();
				Vector v_2 = XMLParser.getObjectNoPRTof((String)argument_types_vector.elementAt(1));
				for(int i = 0; i<v_2.size(); i++)
					arg_type_cb2.addItem((String) v_2.elementAt(i));
				
				arg_type_cb3.setEnabled(true);
				lblArgName3.setEnabled(true);
				lblArgName3.setVisible(true);
				lblArgName3.setText("Arg#3 ("+argument_names_vector.elementAt(2)+")");
				arg_type_cb3.removeAllItems();
				Vector v_3 = XMLParser.getObjectNoPRTof((String)argument_types_vector.elementAt(2));
				for(int i = 0; i<v_3.size(); i++)
					arg_type_cb3.addItem((String) v_3.elementAt(i));
				
				arg_type_cb4.setEnabled(true);
				lblArgName4.setEnabled(true);
				lblArgName4.setVisible(true);
				lblArgName4.setText("Arg#4 ("+argument_names_vector.elementAt(3)+")");
				arg_type_cb4.removeAllItems();
				Vector v_4 = XMLParser.getObjectNoPRTof((String)argument_types_vector.elementAt(3));
				for(int i = 0; i<v_4.size(); i++)
					arg_type_cb4.addItem((String) v_4.elementAt(i));
		
				arg_type_cb5.removeAllItems();
				arg_type_cb5.setEnabled(false);
				lblArgName5.setText("Arg#5");
				lblArgName5.setEnabled(false);
				
			}
			
			if(argument_types_vector.size()==5){
				arg_type_cb1.setEnabled(true);
				lblArgName1.setEnabled(true);
				lblArgName1.setVisible(true);
				lblArgName1.setText("Arg#1 ("+argument_names_vector.elementAt(0)+")");
				arg_type_cb1.removeAllItems();
				Vector v_1 = XMLParser.getObjectNoPRTof((String)argument_types_vector.elementAt(0));
				for(int i = 0; i<v_1.size(); i++)
					arg_type_cb1.addItem((String) v_1.elementAt(i));
				
				arg_type_cb2.setEnabled(true);
				lblArgName2.setEnabled(true);
				lblArgName2.setVisible(true);
				lblArgName2.setText("Arg#2 ("+argument_names_vector.elementAt(1)+")");
				arg_type_cb2.removeAllItems();
				Vector v_2 = XMLParser.getObjectNoPRTof((String)argument_types_vector.elementAt(1));
				for(int i = 0; i<v_2.size(); i++)
					arg_type_cb2.addItem((String) v_2.elementAt(i));
				
				arg_type_cb3.setEnabled(true);
				lblArgName3.setEnabled(true);
				lblArgName3.setVisible(true);
				lblArgName3.setText("Arg#3 ("+argument_names_vector.elementAt(2)+")");
				arg_type_cb3.removeAllItems();
				Vector v_3 = XMLParser.getObjectNoPRTof((String)argument_types_vector.elementAt(2));
				for(int i = 0; i<v_3.size(); i++)
					arg_type_cb3.addItem((String) v_3.elementAt(i));
				
				arg_type_cb4.setEnabled(true);
				lblArgName4.setEnabled(true);
				lblArgName4.setVisible(true);
				lblArgName4.setText("Arg#4 ("+argument_names_vector.elementAt(3)+")");
				arg_type_cb4.removeAllItems();
				Vector v_4 = XMLParser.getObjectNoPRTof((String)argument_types_vector.elementAt(3));
				for(int i = 0; i<v_4.size(); i++)
					arg_type_cb4.addItem((String) v_4.elementAt(i));
				
				arg_type_cb5.setEnabled(true);
				lblArgName5.setEnabled(true);
				lblArgName5.setVisible(true);
				lblArgName5.setText("Arg#5 ("+argument_names_vector.elementAt(4)+")");
				arg_type_cb5.removeAllItems();
				Vector v_5 = XMLParser.getObjectNoPRTof((String)argument_types_vector.elementAt(4));
				for(int i = 0; i<v_5.size(); i++)
					arg_type_cb5.addItem((String) v_5.elementAt(i));

			}
		}
		
		public Step1() {
			setStepTitle("Select Arguments");
					
			
			springLayout = new SpringLayout();
			setLayout(springLayout);

			
			panel_1 = new JPanel();
			sl_panel_1 = new SpringLayout();
			panel_1.setLayout(sl_panel_1);
			
			
			
			springLayout.putConstraint(SpringLayout.NORTH, panel_1, 41, SpringLayout.NORTH, this);
			springLayout.putConstraint(SpringLayout.WEST, panel_1, 0, SpringLayout.WEST, this);
			springLayout.putConstraint(SpringLayout.SOUTH, panel_1, 180, SpringLayout.NORTH, this);
			springLayout.putConstraint(SpringLayout.EAST, panel_1, 200, SpringLayout.WEST, this);
			
			
			add(panel_1);
			
			//Create and populate the panel.

			arg_type_cb1 = new JComboBox<String>(XMLParser.getRelevantTypesForArgumentsOfTasks());
			panel_1.add(lblArgName1);
			panel_1.add(arg_type_cb1);
			
			arg_type_cb2 = new JComboBox<String>(XMLParser.getRelevantTypesForArgumentsOfTasks());
			panel_1.add(lblArgName2);
			panel_1.add(arg_type_cb2);

			arg_type_cb3 = new JComboBox<String>(XMLParser.getRelevantTypesForArgumentsOfTasks());
			panel_1.add(lblArgName3);
			panel_1.add(arg_type_cb3);
			
			arg_type_cb4 = new JComboBox<String>(XMLParser.getRelevantTypesForArgumentsOfTasks());
			panel_1.add(lblArgName4);
			panel_1.add(arg_type_cb4);
			
			arg_type_cb5 = new JComboBox<String>(XMLParser.getRelevantTypesForArgumentsOfTasks());
			panel_1.add(lblArgName5);
			panel_1.add(arg_type_cb5);

			//Lay out the panel.
			SpringUtilities.makeCompactGrid(panel_1,
			                                5, 2, //rows, cols
			                                6, 6,        //initX, initY
			                                6, 6);       //xPad, yPad
			
			
			setBackStep(0);

		}
		
	}

	protected void finish(){
		if(argument_types_vector.size()==1)
		   final_task = selected_task_name+"("+arg_type_cb1.getSelectedItem()+")";
		else if(argument_types_vector.size()==2)
			final_task = selected_task_name+"("+arg_type_cb1.getSelectedItem()+","+arg_type_cb2.getSelectedItem()+")";
		else if(argument_types_vector.size()==3)
			final_task = selected_task_name+"("+arg_type_cb1.getSelectedItem()+","+arg_type_cb2.getSelectedItem()+","+arg_type_cb3.getSelectedItem()+")";
		else if(argument_types_vector.size()==4)
			final_task = selected_task_name+"("+arg_type_cb1.getSelectedItem()+","+arg_type_cb2.getSelectedItem()+","+arg_type_cb3.getSelectedItem()+","+arg_type_cb4.getSelectedItem()+")";
		else if(argument_types_vector.size()==5)
			final_task = selected_task_name+"("+arg_type_cb1.getSelectedItem()+","+arg_type_cb2.getSelectedItem()+","+arg_type_cb3.getSelectedItem()+","+arg_type_cb4.getSelectedItem()+","+arg_type_cb5.getSelectedItem()+")";

		selectedCell.setValue(final_task);
		
		
		//SMARTPM -- UPDATE THE MULTIPLICITY RULES
		mxMultiplicity[] multiplicities = graph.getMultiplicities();
		
		int mult_lenght = multiplicities.length + 2;
				
		mxMultiplicity[] new_multiplicities_array = new mxMultiplicity[mult_lenght];
		
		for(int i=0;i<multiplicities.length;i++) {
			
			new_multiplicities_array[i] = multiplicities[i];
			
		}
				
		// Activities do not want more than one outgoing connection
		new_multiplicities_array[mult_lenght-2] = new mxMultiplicity(false, final_task, null, null, 1,"1", null, "Tasks must have no more than one incoming edge! ", null, true);
		
		// Activities Joins do not want more than one outgoing connection
		new_multiplicities_array[mult_lenght-1] = new mxMultiplicity(true, final_task, null, null, 1,"1", null, "Tasks must have no more than one outgoing edge! ", null, true);
		
		graph.setMultiplicities(new_multiplicities_array);
		
		/////////////////////////////////////////////////
		
		
     	super.finish();
	}
	
}
