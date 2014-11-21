package com.mxgraph.smartml.view;

import java.awt.Font;  
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.freixas.jwizard.JWizardDialog;
import org.freixas.jwizard.JWizardPanel;

import com.mxgraph.smartml.control.H_AddTask;
import com.mxgraph.smartml.model.XMLParser;

public class AddEffectsTask extends JWizardDialog {
	
	private static final long serialVersionUID = 1L;
	// Stringa di appoggio per catturare il nome dell'atomic term scelto 
	public String atTerm_name;
	// Stringhe per rappresentare gli argomenti di un atomic term
	public String argum1,argum2,argum3,argum4,argum5,arguments;
	// Stringa per rappresentare l'operatore
	public String oper;
	// Stringa per rappresentare un "recap" sull'effetto
	public String summary;
	// Stringa per rappresentare l'espressione 
	public String expr;
	// Stringa per rappresentare la modalit� dell'effetto: supposed/automatic
	public String eff_mode;
	// Stringa per rappresentare l'effetto (in toto)
	public String effect_string;
	
	
	public Vector<Vector<String>> att_arguments = H_AddTask.attual_arg;  
	
	
	public AddEffectsTask() {
	
		// We want the dialog modal -- when the dialog is finished, we
	    // exit the program 
	    setModal(true);
	    
	    // Set the dialog title. This is the title for the wizard as a whole
	    setTitle("Wizard for add effects");
	    
	    setSize(400, 450);  
	    
	    setResizable(true);
	    
	    // Create each step
	    addWizardPanel(new Step0());
	    addWizardPanel(new Step1());
	    addWizardPanel(new Step2());
	    addWizardPanel(new Step3());
	    addWizardPanel(new Step4());

	    // We don't want to have the cancel button enabled when we're done
	    disableCancelAtEnd();
	 
	}
	
	
	/* Definizione di ogni STEP (sono classi interne)*/
	
	public class Step0 extends JWizardPanel {
	
		private static final long serialVersionUID = 1L;
		
		public SpringLayout springLayout;
		public JPanel panel;
		public SpringLayout sl_panel;
		public JComboBox<String> atTermName_comboBox;
		public JLabel lblAtomicTermName;
		
		public Step0() { 
		  
			setStepTitle("Select Atomic Term");
			
			springLayout = new SpringLayout();
			setLayout(springLayout);
			
			panel = new JPanel();
			springLayout.putConstraint(SpringLayout.NORTH, panel, 21, SpringLayout.NORTH, this);
			springLayout.putConstraint(SpringLayout.WEST, panel, 10, SpringLayout.WEST, this);
			springLayout.putConstraint(SpringLayout.SOUTH, panel, 137, SpringLayout.NORTH, this);
			springLayout.putConstraint(SpringLayout.EAST, panel, 440, SpringLayout.WEST, this);
			add(panel);
			sl_panel = new SpringLayout();
			panel.setLayout(sl_panel);
			
			atTermName_comboBox = new JComboBox<String>(XMLParser.getAllDynamicTermsName());
			sl_panel.putConstraint(SpringLayout.EAST, atTermName_comboBox, -93, SpringLayout.EAST, panel);
			panel.add(atTermName_comboBox);
			
			lblAtomicTermName = new JLabel("Atomic Term's name: ");
			lblAtomicTermName.setLabelFor(atTermName_comboBox);
			sl_panel.putConstraint(SpringLayout.NORTH, lblAtomicTermName, 48, SpringLayout.NORTH, panel);
			sl_panel.putConstraint(SpringLayout.EAST, lblAtomicTermName, -275, SpringLayout.EAST, panel);
			sl_panel.putConstraint(SpringLayout.NORTH, atTermName_comboBox, -5, SpringLayout.NORTH, lblAtomicTermName);
			sl_panel.putConstraint(SpringLayout.WEST, atTermName_comboBox, 6, SpringLayout.EAST, lblAtomicTermName);
			panel.add(lblAtomicTermName);
			
			// Set the previous (none) and next steps
			setBackStep(-1);
			setNextStep(1);
			
		}
		
		protected void next(){
			atTerm_name = this.atTermName_comboBox.getSelectedItem().toString();
			
			super.next();
			
		}
		
	} // CHIUDE LA CLASSE STEP0
	
	
	
	public class Step1 extends JWizardPanel {
		
		private static final long serialVersionUID = 1L;
		
		private String lbl;
		
		public SpringLayout springLayout;
		public JPanel panel;
		public JCheckBox ckbx1;
		public JLabel lblArgument_1;
		public JComboBox<String> arg_comboBox_1;
		public JCheckBox ckbx2;
		public JLabel lblArgument_2;
		public JComboBox<String> arg_comboBox_2;
		public JCheckBox ckbx3;
		public JLabel lblArgument_3;
		public JComboBox<String> arg_comboBox_3;
		public JCheckBox ckbx4;
		public JLabel lblArgument_4;
		public JComboBox<String> arg_comboBox_4;
		public JCheckBox ckbx5;
		public JLabel lblArgument_5;
		public JComboBox<String> arg_comboBox_5;
		public JLabel lbl_0Arg;
		
		public Vector<Vector<String>> vec;
		
		protected void makingVisible(){
			// vettori di appoggio per il riempimento delle combobox contenenti gli argomenti degli atomic terms (negli effetti,step1)
			Vector<String> objects_1; 
			Vector<String> objects_2;
			Vector<String> objects_3;
			Vector<String> objects_4;
			Vector<String> objects_5;
			int n_argAT;
			ckbx1.setSelected(false);
			lblArgument_1.setEnabled(true);
			arg_comboBox_1.removeAllItems();
			arg_comboBox_1.setEnabled(true);
			ckbx2.setSelected(false);
			lblArgument_2.setEnabled(true);
			arg_comboBox_2.removeAllItems();
			arg_comboBox_2.setEnabled(true);
			ckbx3.setSelected(false);
			lblArgument_3.setEnabled(true);
			arg_comboBox_3.removeAllItems();
			arg_comboBox_3.setEnabled(true);
			ckbx4.setSelected(false);
			lblArgument_4.setEnabled(true);
			arg_comboBox_4.removeAllItems();
			arg_comboBox_4.setEnabled(true);
			ckbx5.setSelected(false);
			lblArgument_5.setEnabled(true);
			arg_comboBox_5.removeAllItems();
			arg_comboBox_5.setEnabled(true);
			
			// We need to set the label text when the panel is displayed, not when
			// the panel is constructed
			lbl = "Select Arguments for "+ atTerm_name;
			setStepTitle(lbl);
			
			// Mi costruisco il vettore vec con i "dettagli" dell'atomic term scelto
			vec = XMLParser.getAtomicTermParametersTypesAndReturnType(atTerm_name);
			
			lbl_0Arg.setVisible(false);
			
			n_argAT = vec.get(0).size();
			if(n_argAT == 0){
				lbl_0Arg.setVisible(true);
				ckbx1.setEnabled(false);ckbx2.setEnabled(false);ckbx3.setEnabled(false);ckbx4.setEnabled(false);ckbx5.setEnabled(false);
				lblArgument_1.setEnabled(false);lblArgument_2.setEnabled(false);lblArgument_3.setEnabled(false);lblArgument_4.setEnabled(false);lblArgument_5.setEnabled(false);
				arg_comboBox_1.setEnabled(false);arg_comboBox_2.setEnabled(false);arg_comboBox_3.setEnabled(false);arg_comboBox_4.setEnabled(false);arg_comboBox_5.setEnabled(false);
			}
			if(n_argAT == 1){
				ckbx1.setSelected(true);
				objects_1 = XMLParser.getDataObjectsOf(vec.get(0).get(0),true);
				for(int i=0;i<objects_1.size();i++){
					arg_comboBox_1.addItem(objects_1.get(i));
				}
				for(int x=0;x<att_arguments.size();x++){
					if(vec.get(0).get(0).equalsIgnoreCase(att_arguments.get(x).lastElement())){
						arg_comboBox_1.addItem(att_arguments.get(x).firstElement());
					}
				}
				ckbx1.setEnabled(false);ckbx2.setEnabled(false);ckbx3.setEnabled(false);ckbx4.setEnabled(false);ckbx5.setEnabled(false);
				lblArgument_2.setEnabled(false);lblArgument_3.setEnabled(false);lblArgument_4.setEnabled(false);lblArgument_5.setEnabled(false);
				arg_comboBox_2.setEnabled(false);arg_comboBox_3.setEnabled(false);arg_comboBox_4.setEnabled(false);arg_comboBox_5.setEnabled(false);
			}
			else if(n_argAT == 2){
				ckbx1.setSelected(true);
				objects_1 = XMLParser.getDataObjectsOf(vec.get(0).get(0),true);
				for(int i=0;i<objects_1.size();i++){
					arg_comboBox_1.addItem(objects_1.get(i));
				}
				for(int x=0;x<att_arguments.size();x++){
					if(vec.get(0).get(0).equalsIgnoreCase(att_arguments.get(x).lastElement())){
						arg_comboBox_1.addItem(att_arguments.get(x).firstElement());
					}
				}
				ckbx2.setSelected(true);
				objects_2 = XMLParser.getDataObjectsOf(vec.get(0).get(1),true);
				for(int i=0;i<objects_2.size();i++){
					arg_comboBox_2.addItem(objects_2.get(i));
				}
				for(int x=0;x<att_arguments.size();x++){
					if(vec.get(0).get(1).equalsIgnoreCase(att_arguments.get(x).lastElement())){
						arg_comboBox_2.addItem(att_arguments.get(x).firstElement());
					}
				}
				ckbx1.setEnabled(false);ckbx2.setEnabled(false);ckbx3.setEnabled(false);ckbx4.setEnabled(false);ckbx5.setEnabled(false);
				lblArgument_3.setEnabled(false);lblArgument_4.setEnabled(false);lblArgument_5.setEnabled(false);
				arg_comboBox_3.setEnabled(false);arg_comboBox_4.setEnabled(false);arg_comboBox_5.setEnabled(false);
			}
			else if(n_argAT == 3){
				ckbx1.setSelected(true);
				objects_1 = XMLParser.getDataObjectsOf(vec.get(0).get(0),true);
				for(int i=0;i<objects_1.size();i++){
					arg_comboBox_1.addItem(objects_1.get(i));
				}
				for(int x=0;x<att_arguments.size();x++){
					if(vec.get(0).get(0).equalsIgnoreCase(att_arguments.get(x).lastElement())){
						arg_comboBox_1.addItem(att_arguments.get(x).firstElement());
					}
				}
				ckbx2.setSelected(true);
				objects_2 = XMLParser.getDataObjectsOf(vec.get(0).get(1),true);
				for(int i=0;i<objects_2.size();i++){
					arg_comboBox_2.addItem(objects_2.get(i));
				}
				for(int x=0;x<att_arguments.size();x++){
					if(vec.get(0).get(1).equalsIgnoreCase(att_arguments.get(x).lastElement())){
						arg_comboBox_2.addItem(att_arguments.get(x).firstElement());
					}
				}
				ckbx3.setSelected(true);
				objects_3 = XMLParser.getDataObjectsOf(vec.get(0).get(2),true);
				for(int i=0;i<objects_3.size();i++){
					arg_comboBox_3.addItem(objects_3.get(i));
				}
				for(int x=0;x<att_arguments.size();x++){
					if(vec.get(0).get(2).equalsIgnoreCase(att_arguments.get(x).lastElement())){
						arg_comboBox_3.addItem(att_arguments.get(x).firstElement());
					}
				}
				ckbx1.setEnabled(false);ckbx2.setEnabled(false);ckbx3.setEnabled(false);ckbx4.setEnabled(false);ckbx5.setEnabled(false);
				lblArgument_4.setEnabled(false);lblArgument_5.setEnabled(false);
				arg_comboBox_4.setEnabled(false);arg_comboBox_5.setEnabled(false);
			}
			else if(n_argAT == 4){
				ckbx1.setSelected(true);
				objects_1 = XMLParser.getDataObjectsOf(vec.get(0).get(0),true);
				for(int i=0;i<objects_1.size();i++){
					arg_comboBox_1.addItem(objects_1.get(i));
				}
				for(int x=0;x<att_arguments.size();x++){
					if(vec.get(0).get(0).equalsIgnoreCase(att_arguments.get(x).lastElement())){
						arg_comboBox_1.addItem(att_arguments.get(x).firstElement());
					}
				}
				ckbx2.setSelected(true);
				objects_2 = XMLParser.getDataObjectsOf(vec.get(0).get(1),true);
				for(int i=0;i<objects_2.size();i++){
					arg_comboBox_2.addItem(objects_2.get(i));
				}
				for(int x=0;x<att_arguments.size();x++){
					if(vec.get(0).get(1).equalsIgnoreCase(att_arguments.get(x).lastElement())){
						arg_comboBox_2.addItem(att_arguments.get(x).firstElement());
					}
				}
				ckbx3.setSelected(true);
				objects_3 = XMLParser.getDataObjectsOf(vec.get(0).get(2),true);
				for(int i=0;i<objects_3.size();i++){
					arg_comboBox_3.addItem(objects_3.get(i));
				}
				for(int x=0;x<att_arguments.size();x++){
					if(vec.get(0).get(2).equalsIgnoreCase(att_arguments.get(x).lastElement())){
						arg_comboBox_3.addItem(att_arguments.get(x).firstElement());
					}
				}
				ckbx4.setSelected(true);
				objects_4 = XMLParser.getDataObjectsOf(vec.get(0).get(3),true);
				for(int i=0;i<objects_4.size();i++){
					arg_comboBox_4.addItem(objects_4.get(i));
				}
				for(int x=0;x<att_arguments.size();x++){
					if(vec.get(0).get(3).equalsIgnoreCase(att_arguments.get(x).lastElement())){
						arg_comboBox_4.addItem(att_arguments.get(x).firstElement());
					}
				}
				ckbx1.setEnabled(false);ckbx2.setEnabled(false);ckbx3.setEnabled(false);ckbx4.setEnabled(false);ckbx5.setEnabled(false);
				lblArgument_5.setEnabled(false);
				arg_comboBox_5.setEnabled(false);
			}
			else if(n_argAT == 5){
				ckbx1.setSelected(true);
				objects_1 = XMLParser.getDataObjectsOf(vec.get(0).get(0),true);
				for(int i=0;i<objects_1.size();i++){
					arg_comboBox_1.addItem(objects_1.get(i));
				}
				for(int x=0;x<att_arguments.size();x++){
					if(vec.get(0).get(0).equalsIgnoreCase(att_arguments.get(x).lastElement())){
						arg_comboBox_1.addItem(att_arguments.get(x).firstElement());
					}
				}
				ckbx2.setSelected(true);
				objects_2 = XMLParser.getDataObjectsOf(vec.get(0).get(1),true);
				for(int i=0;i<objects_2.size();i++){
					arg_comboBox_2.addItem(objects_2.get(i));
				}
				for(int x=0;x<att_arguments.size();x++){
					if(vec.get(0).get(1).equalsIgnoreCase(att_arguments.get(x).lastElement())){
						arg_comboBox_2.addItem(att_arguments.get(x).firstElement());
					}
				}
				ckbx3.setSelected(true);
				objects_3 = XMLParser.getDataObjectsOf(vec.get(0).get(2),true);
				for(int i=0;i<objects_3.size();i++){
					arg_comboBox_3.addItem(objects_3.get(i));
				}
				for(int x=0;x<att_arguments.size();x++){
					if(vec.get(0).get(2).equalsIgnoreCase(att_arguments.get(x).lastElement())){
						arg_comboBox_3.addItem(att_arguments.get(x).firstElement());
					}
				}
				ckbx4.setSelected(true);
				objects_4 = XMLParser.getDataObjectsOf(vec.get(0).get(3),true);
				for(int i=0;i<objects_4.size();i++){
					arg_comboBox_4.addItem(objects_4.get(i));
				}
				for(int x=0;x<att_arguments.size();x++){
					if(vec.get(0).get(3).equalsIgnoreCase(att_arguments.get(x).lastElement())){
						arg_comboBox_4.addItem(att_arguments.get(x).firstElement());
					}
				}
				ckbx5.setSelected(true);
				objects_5 = XMLParser.getDataObjectsOf(vec.get(0).get(4),true);
				for(int i=0;i<objects_5.size();i++){
					arg_comboBox_5.addItem(objects_5.get(i));
				}
				for(int x=0;x<att_arguments.size();x++){
					if(vec.get(0).get(4).equalsIgnoreCase(att_arguments.get(x).lastElement())){
						arg_comboBox_5.addItem(att_arguments.get(x).firstElement());
					}
				}
				ckbx1.setEnabled(false);ckbx2.setEnabled(false);ckbx3.setEnabled(false);ckbx4.setEnabled(false);ckbx5.setEnabled(false);
			}
			
		}// CHIUDE IL METODO makingVisible()
		
		public Step1() {
			
			springLayout = new SpringLayout();
			setLayout(springLayout);
			
			panel = new JPanel();
			springLayout.putConstraint(SpringLayout.NORTH, panel, 20, SpringLayout.NORTH, this);
			springLayout.putConstraint(SpringLayout.WEST, panel, 10, SpringLayout.WEST, this);
			springLayout.putConstraint(SpringLayout.SOUTH, panel, 278, SpringLayout.NORTH, this);
			springLayout.putConstraint(SpringLayout.EAST, panel, 440, SpringLayout.WEST, this);
			add(panel);
			SpringLayout sl_panel = new SpringLayout();
			panel.setLayout(sl_panel);
			
			ckbx1 = new JCheckBox("");
			panel.add(ckbx1);
			
			lblArgument_1 = new JLabel("Argument # 1");
			sl_panel.putConstraint(SpringLayout.NORTH, ckbx1, -4, SpringLayout.NORTH, lblArgument_1);
			sl_panel.putConstraint(SpringLayout.EAST, ckbx1, -6, SpringLayout.WEST, lblArgument_1);
			panel.add(lblArgument_1);
			
			arg_comboBox_1 = new JComboBox<String>();
			lblArgument_1.setLabelFor(arg_comboBox_1);
			sl_panel.putConstraint(SpringLayout.NORTH, lblArgument_1, 3, SpringLayout.NORTH, arg_comboBox_1);
			sl_panel.putConstraint(SpringLayout.NORTH, arg_comboBox_1, 22, SpringLayout.NORTH, panel);
			sl_panel.putConstraint(SpringLayout.WEST, arg_comboBox_1, 193, SpringLayout.WEST, panel);
			sl_panel.putConstraint(SpringLayout.EAST, arg_comboBox_1, -89, SpringLayout.EAST, panel);
			panel.add(arg_comboBox_1);
			
			ckbx2 = new JCheckBox("");
			sl_panel.putConstraint(SpringLayout.WEST, ckbx2, 0, SpringLayout.WEST, ckbx1);
			panel.add(ckbx2);
			
			lblArgument_2 = new JLabel("Argument # 2");
			sl_panel.putConstraint(SpringLayout.SOUTH, ckbx2, 0, SpringLayout.SOUTH, lblArgument_2);
			sl_panel.putConstraint(SpringLayout.WEST, lblArgument_1, 0, SpringLayout.WEST, lblArgument_2);
			panel.add(lblArgument_2);
			
			arg_comboBox_2 = new JComboBox<String>();
			lblArgument_2.setLabelFor(arg_comboBox_2);
			sl_panel.putConstraint(SpringLayout.WEST, arg_comboBox_2, 193, SpringLayout.WEST, panel);
			sl_panel.putConstraint(SpringLayout.EAST, lblArgument_2, -35, SpringLayout.WEST, arg_comboBox_2);
			sl_panel.putConstraint(SpringLayout.NORTH, arg_comboBox_2, 9, SpringLayout.SOUTH, arg_comboBox_1);
			sl_panel.putConstraint(SpringLayout.EAST, arg_comboBox_2, -89, SpringLayout.EAST, panel);
			sl_panel.putConstraint(SpringLayout.NORTH, lblArgument_2, 3, SpringLayout.NORTH, arg_comboBox_2);
			panel.add(arg_comboBox_2);
			
			ckbx3 = new JCheckBox("");
			sl_panel.putConstraint(SpringLayout.WEST, ckbx3, 0, SpringLayout.WEST, ckbx1);
			panel.add(ckbx3);
			
			lblArgument_3 = new JLabel("Argument # 3");
			sl_panel.putConstraint(SpringLayout.SOUTH, ckbx3, 0, SpringLayout.SOUTH, lblArgument_3);
			panel.add(lblArgument_3);
			
			arg_comboBox_3 = new JComboBox<String>();
			lblArgument_3.setLabelFor(arg_comboBox_3);
			sl_panel.putConstraint(SpringLayout.WEST, arg_comboBox_3, 193, SpringLayout.WEST, panel);
			sl_panel.putConstraint(SpringLayout.EAST, lblArgument_3, -35, SpringLayout.WEST, arg_comboBox_3);
			sl_panel.putConstraint(SpringLayout.NORTH, arg_comboBox_3, 8, SpringLayout.SOUTH, arg_comboBox_2);
			sl_panel.putConstraint(SpringLayout.EAST, arg_comboBox_3, -89, SpringLayout.EAST, panel);
			sl_panel.putConstraint(SpringLayout.NORTH, lblArgument_3, 3, SpringLayout.NORTH, arg_comboBox_3);
			panel.add(arg_comboBox_3);
			
			ckbx4 = new JCheckBox("");
			sl_panel.putConstraint(SpringLayout.WEST, ckbx4, 0, SpringLayout.WEST, ckbx1);
			panel.add(ckbx4);
			
			lblArgument_4 = new JLabel("Argument # 4");
			sl_panel.putConstraint(SpringLayout.SOUTH, ckbx4, 0, SpringLayout.SOUTH, lblArgument_4);
			sl_panel.putConstraint(SpringLayout.NORTH, lblArgument_4, 14, SpringLayout.SOUTH, lblArgument_3);
			sl_panel.putConstraint(SpringLayout.WEST, lblArgument_4, 0, SpringLayout.WEST, lblArgument_1);
			panel.add(lblArgument_4);
			
			arg_comboBox_4 = new JComboBox<String>();
			lblArgument_4.setLabelFor(arg_comboBox_4);
			sl_panel.putConstraint(SpringLayout.NORTH, arg_comboBox_4, 6, SpringLayout.SOUTH, arg_comboBox_3);
			sl_panel.putConstraint(SpringLayout.WEST, arg_comboBox_4, 0, SpringLayout.WEST, arg_comboBox_1);
			sl_panel.putConstraint(SpringLayout.EAST, arg_comboBox_4, 0, SpringLayout.EAST, arg_comboBox_1);
			panel.add(arg_comboBox_4);
			
			ckbx5 = new JCheckBox("");
			sl_panel.putConstraint(SpringLayout.WEST, ckbx5, 0, SpringLayout.WEST, ckbx1);
			panel.add(ckbx5);
			
			lblArgument_5 = new JLabel("Argument # 5");
			sl_panel.putConstraint(SpringLayout.SOUTH, ckbx5, 0, SpringLayout.SOUTH, lblArgument_5);
			sl_panel.putConstraint(SpringLayout.NORTH, lblArgument_5, 14, SpringLayout.SOUTH, lblArgument_4);
			sl_panel.putConstraint(SpringLayout.WEST, lblArgument_5, 0, SpringLayout.WEST, lblArgument_1);
			panel.add(lblArgument_5);
			
			arg_comboBox_5 = new JComboBox<String>();
			lblArgument_5.setLabelFor(arg_comboBox_5);
			sl_panel.putConstraint(SpringLayout.NORTH, arg_comboBox_5, 7, SpringLayout.SOUTH, arg_comboBox_4);
			sl_panel.putConstraint(SpringLayout.WEST, arg_comboBox_5, 0, SpringLayout.WEST, arg_comboBox_1);
			sl_panel.putConstraint(SpringLayout.EAST, arg_comboBox_5, 0, SpringLayout.EAST, arg_comboBox_1);
			panel.add(arg_comboBox_5);
			
			lbl_0Arg = new JLabel("THIS ATOMIC TERM DOESN' T  HAVE ATTRIBUTES!");
			sl_panel.putConstraint(SpringLayout.NORTH, lbl_0Arg, 41, SpringLayout.SOUTH, lblArgument_5);
			sl_panel.putConstraint(SpringLayout.EAST, lbl_0Arg, -89, SpringLayout.EAST, panel);
			panel.add(lbl_0Arg);

			
			// Set the previous and next steps. 	
			setBackStep(0);
			setNextStep(2);
		
		} // CHIUDE IL COSTRUTTORE STEP1()
		
		protected void next(){ 
			if(!ckbx1.isSelected() && !ckbx2.isSelected() && !ckbx3.isSelected() && !ckbx4.isSelected() && !ckbx5.isSelected()){
				arguments = "";
			}
			if(ckbx1.isSelected()){
				argum1 = arg_comboBox_1.getSelectedItem().toString();
				arguments = argum1;
			}
			if(ckbx2.isSelected()){
				argum2 = arg_comboBox_2.getSelectedItem().toString();
				arguments = argum1 + "," + argum2;
			}
			if(ckbx3.isSelected()){ 
				argum3 = arg_comboBox_3.getSelectedItem().toString();
				arguments = argum1 + "," + argum2 + "," + argum3;
			}
			if(ckbx4.isSelected()){
				argum4 = arg_comboBox_4.getSelectedItem().toString();
				arguments = argum1 + "," + argum2 + "," + argum3 + "," + argum4;
			}
			if(ckbx5.isSelected()){ 
				argum5 = arg_comboBox_5.getSelectedItem().toString();
				arguments = argum1 + "," + argum2 + "," + argum3 + "," + argum4 + "," + argum5;
			}
			
			super.next();
		}
		
	
	} // CHIUDE LA CLASSE STEP1
	
	public class Step2 extends JWizardPanel { 

		private static final long serialVersionUID = 1L;
		
		private String lbl;
		
		public Vector<Vector<String>> vec;
		
		public SpringLayout springLayout;
		public SpringLayout sl_panel;
		public JComboBox<String> operator_comboBox;
		
		
		
		protected void makingVisible(){
			// Vettore di appoggio per il riempimento della combobox degli operatori 
			Vector<String> eff_operators;  
			
			lbl = "Select operator for " + atTerm_name;
			setStepTitle(lbl);
			
			vec = XMLParser.getAtomicTermParametersTypesAndReturnType(atTerm_name);
			
			operator_comboBox.removeAllItems();
			eff_operators = XMLParser.getEffectOperators(vec.get(1).get(0)); 
			for(int i=0;i<eff_operators.size();i++){
				operator_comboBox.addItem(eff_operators.get(i));
			}
			
		}// CHIUDE IL METODO makingVisible()
		
		public Step2() {
			springLayout = new SpringLayout();
			setLayout(springLayout);
			
			JPanel panel = new JPanel();
			springLayout.putConstraint(SpringLayout.NORTH, panel, 10, SpringLayout.NORTH, this);
			springLayout.putConstraint(SpringLayout.WEST, panel, 10, SpringLayout.WEST, this);
			springLayout.putConstraint(SpringLayout.SOUTH, panel, 183, SpringLayout.NORTH, this);
			springLayout.putConstraint(SpringLayout.EAST, panel, 428, SpringLayout.WEST, this);
			add(panel);
			
			sl_panel = new SpringLayout();
			panel.setLayout(sl_panel);
			
			operator_comboBox = new JComboBox<String>();
			sl_panel.putConstraint(SpringLayout.NORTH, operator_comboBox, 43, SpringLayout.NORTH, panel);
			sl_panel.putConstraint(SpringLayout.WEST, operator_comboBox, 142, SpringLayout.WEST, panel);
			sl_panel.putConstraint(SpringLayout.EAST, operator_comboBox, -138, SpringLayout.EAST, panel);
			panel.add(operator_comboBox);
			

			// Set the previous and next steps
			setBackStep(1);
			setNextStep(3);
		} // CHIUDE IL COSTRUTTORE STEP2()
		
		protected void next(){
			oper = operator_comboBox.getSelectedItem().toString();
			
			super.next();
		}
		
		
	} // CHIUDE LA CLASSE STEP2
	
	public class Step3 extends JWizardPanel {
		
		private static final long serialVersionUID = 1L;
		
		public SpringLayout springLayout;
		public JPanel panel;
		public SpringLayout sl_panel;
		public JLabel recap_lbl;
		public JRadioButton rdbtn_text;
		public JRadioButton rdbtn_combo;
		public ButtonGroup group;
		public JTextField textField;
		public JComboBox<String> comboBox;
		
		
		
		// Vettore con i dettagli dell'atomic term
		public Vector<Vector<String>> vec;
		
		protected void makingVisible(){
			// Vettore di appoggio per il riempimento della combobox contenente l'espressione
			Vector<String> expr;
			
			summary = atTerm_name + "[" + arguments + "]" + " " + oper + " " ;
			recap_lbl.setText(summary);
			
			vec = XMLParser.getAtomicTermParametersTypesAndReturnType(atTerm_name);
			
			comboBox.removeAllItems();
			expr = XMLParser.getDataObjectsOf(vec.get(1).get(0),true);
			for(int i=0;i<expr.size();i++){
				comboBox.addItem(expr.get(i));
			}
			for(int y=0;y<att_arguments.size();y++){   
				if(vec.get(1).get(0).equalsIgnoreCase(att_arguments.get(y).lastElement())){
					comboBox.addItem(att_arguments.get(y).firstElement());
				}
			}
			
			
		
		} // CHIUDE makingVisible()
		
		public Step3() {
			setStepTitle("Select expression");

			springLayout = new SpringLayout();
			setLayout(springLayout);
			
			panel = new JPanel();
			springLayout.putConstraint(SpringLayout.NORTH, panel, 10, SpringLayout.NORTH, this);
			springLayout.putConstraint(SpringLayout.WEST, panel, 10, SpringLayout.WEST, this);
			springLayout.putConstraint(SpringLayout.SOUTH, panel, 267, SpringLayout.NORTH, this);
			springLayout.putConstraint(SpringLayout.EAST, panel, 440, SpringLayout.WEST, this);
			add(panel);
			
			sl_panel = new SpringLayout();
			panel.setLayout(sl_panel);
			
			recap_lbl = new JLabel("\" \"");
			recap_lbl.setFont(new Font("Times New Roman", Font.BOLD, 18));
			sl_panel.putConstraint(SpringLayout.NORTH, recap_lbl, 42, SpringLayout.NORTH, panel);
			sl_panel.putConstraint(SpringLayout.WEST, recap_lbl, 36, SpringLayout.WEST, panel);
			panel.add(recap_lbl);
			
			rdbtn_text = new JRadioButton("Text mode:");
			sl_panel.putConstraint(SpringLayout.NORTH, rdbtn_text, 35, SpringLayout.SOUTH, recap_lbl);
			sl_panel.putConstraint(SpringLayout.WEST, rdbtn_text, 0, SpringLayout.WEST, recap_lbl);
			panel.add(rdbtn_text);
			
			rdbtn_combo = new JRadioButton("Wizard mode:");
			sl_panel.putConstraint(SpringLayout.NORTH, rdbtn_combo, 19, SpringLayout.SOUTH, rdbtn_text);
			sl_panel.putConstraint(SpringLayout.WEST, rdbtn_combo, 0, SpringLayout.WEST, recap_lbl);
			panel.add(rdbtn_combo);
			
			rdbtn_combo.doClick();
			
			group = new ButtonGroup();
			group.add(rdbtn_text);
			group.add(rdbtn_combo);
			
			textField = new JTextField();
			sl_panel.putConstraint(SpringLayout.NORTH, textField, 94, SpringLayout.NORTH, panel);
			sl_panel.putConstraint(SpringLayout.WEST, textField, 6, SpringLayout.EAST, rdbtn_text);
			sl_panel.putConstraint(SpringLayout.EAST, textField, 140, SpringLayout.EAST, rdbtn_text);
			panel.add(textField);
			textField.setColumns(10);
			
			comboBox = new JComboBox<String>();
			sl_panel.putConstraint(SpringLayout.NORTH, comboBox, 22, SpringLayout.SOUTH, textField);
			sl_panel.putConstraint(SpringLayout.WEST, comboBox, 6, SpringLayout.EAST, rdbtn_combo);
			sl_panel.putConstraint(SpringLayout.EAST, comboBox, 0, SpringLayout.EAST, textField);
			panel.add(comboBox);
			
			
			comboBox.addItemListener(new ItemListener() {
							
				@Override
				public void itemStateChanged(ItemEvent e) {
					if(e.getStateChange() == ItemEvent.SELECTED){
						
						expr = comboBox.getSelectedItem().toString();
						setNextStep(4);
					}
					
				}
			});
					
			rdbtn_text.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(e.getActionCommand().equalsIgnoreCase("Text mode:")){
						textField.getDocument().addDocumentListener(new DocumentListener() {
							
							@Override
							public void removeUpdate(DocumentEvent e) {
								// Auto-generated method stub
								
							}
							
							@Override
							public void insertUpdate(DocumentEvent e) {
								if(textField.getText().isEmpty()){
									setNextStep(3);
								}
								else{
									expr = textField.getText();
									setNextStep(4);
								}
								
							}
							
							@Override
							public void changedUpdate(DocumentEvent e) {
								// Auto-generated method stub
								
							}
						});
						
						
					}
					
					
				}
			});

			// Set the previous and next steps. 
			setBackStep(2);
			setNextStep(3);
			
			
		} // CHIUDE IL COSTRUTTORE STEP3()
	
		
	} // CHIUDE LA CLASSE STEP3
	
	public class Step4 extends JWizardPanel{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		public SpringLayout springLayout; 
		public JPanel panel;
		public SpringLayout sl_panel;
		public JLabel recap_lbl;
		public JRadioButton rdbtn_supposed;
		public JRadioButton rdbtn_automatic;
		public ButtonGroup group;
		
		protected void makingVisible(){
			summary = atTerm_name + "[" + arguments + "]" + " " + oper + " " + expr;
			recap_lbl.setText(summary);
			
		} // CHIUDE makingVisible()
		
		public Step4() {
			
			setStepTitle("Select task effect mode");

			springLayout = new SpringLayout();
			setLayout(springLayout);
			
			panel = new JPanel();
			springLayout.putConstraint(SpringLayout.NORTH, panel, 10, SpringLayout.NORTH, this);
			springLayout.putConstraint(SpringLayout.WEST, panel, 10, SpringLayout.WEST, this);
			springLayout.putConstraint(SpringLayout.SOUTH, panel, 267, SpringLayout.NORTH, this);
			springLayout.putConstraint(SpringLayout.EAST, panel, 440, SpringLayout.WEST, this);
			add(panel);
			
			sl_panel = new SpringLayout();
			panel.setLayout(sl_panel);
			
			recap_lbl = new JLabel("\" \"");
			recap_lbl.setFont(new Font("Times New Roman", Font.BOLD, 18));
			sl_panel.putConstraint(SpringLayout.NORTH, recap_lbl, 42, SpringLayout.NORTH, panel);
			sl_panel.putConstraint(SpringLayout.WEST, recap_lbl, 36, SpringLayout.WEST, panel);
			panel.add(recap_lbl);
			
			rdbtn_supposed = new JRadioButton("supposed");
			sl_panel.putConstraint(SpringLayout.NORTH, rdbtn_supposed, 35, SpringLayout.SOUTH, recap_lbl);
			sl_panel.putConstraint(SpringLayout.WEST, rdbtn_supposed, 0, SpringLayout.WEST, recap_lbl);
			panel.add(rdbtn_supposed);
			
			rdbtn_automatic = new JRadioButton("automatic");
			sl_panel.putConstraint(SpringLayout.NORTH, rdbtn_automatic, 19, SpringLayout.SOUTH, rdbtn_supposed);
			sl_panel.putConstraint(SpringLayout.WEST, rdbtn_automatic, 0, SpringLayout.WEST, recap_lbl);
			panel.add(rdbtn_automatic);
			
			ButtonGroup group = new ButtonGroup();
			group.add(rdbtn_supposed);
			group.add(rdbtn_automatic);
			

			
			
			rdbtn_automatic.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(rdbtn_automatic.isSelected()){
						eff_mode = e.getActionCommand();
						setNextStep(5);
					}
					
					
				}
			});
			
			rdbtn_supposed.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					eff_mode = e.getActionCommand();
					setNextStep(5);
					
				}
			});
			
			// Set the previous and next steps. 
			setBackStep(2);
			setNextStep(4);
			
			
		} // CHIUDE IL COSTRUTTORE STEP4()
	
		
	} // CHIUDE LA CLASSE STEP4
	
	protected void finish(){
		effect_string = summary  ;  // "<" + eff_mode + ">" + " - " + summary  ;
	
		super.finish();
	}
	
	
}// CHIUDE TUTTA LA CLASSE "outer class"
