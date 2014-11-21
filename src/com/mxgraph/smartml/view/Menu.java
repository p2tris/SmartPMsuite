package com.mxgraph.smartml.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JMenu;   
import javax.swing.JMenuItem;




import com.mxgraph.smartml.control.H_Menu;


public class Menu extends JMenu{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public H_Menu _handler = null;
	
	//Menu and menu items for editing resource perspective
	private JMenuItem exit_item;
	private JMenuItem set_in_state_item;
	private JMenuItem plugins_item;
	
	private JMenu resource_perspective_menu;
	private JMenuItem services_menu_item;
	private JMenuItem capabilities_menu_item;
	
	public JMenu at_term;
	public JMenuItem add_atTerm;
	public JMenu edit_atTerm; // DA "COSTRUIRE"
	public JMenu delete_atTerm; // DA "COSTRUIRE"
	public JMenuItem data_type;
	public JMenu task;
	public JMenuItem add_task;
	public JMenu edit_task;  // DA "COSTRUIRE"
	public JMenu delete_task; // DA "COSTRUIRE" 
	public JMenu ex_event;
	public JMenuItem add_exEvent;
	public JMenu edit_exEvent; // DA "COSTRUIRE"
	public JMenu delete_exEvent; // DA "COSTRUIRE"
	public JMenu formula; 
	public JMenuItem add_formula; 
	public JMenu edit_formula; // DA "COSTRUIRE"
	public JMenu delete_formula; // DA "COSTRUIRE"
	public JMenuItem run_process;
	


	
	
	public Menu(JMenu mf,JMenu m,Vector<String> t,Vector<String> ee, Vector<String> f,Vector<String> at) { 
		super();
		initComponent(mf,m,t,ee,f,at);
		initHandler();
		
	}

	private void initHandler() {
		_handler = new H_Menu(this);
		
	}

	private void initComponent(JMenu file_menu,JMenu menu,Vector<String> t,Vector<String> ee,Vector<String> fr,Vector<String> at_t) { 
		
		
		plugins_item = new JMenuItem("Configure SmartPM Plugins");
		plugins_item.setActionCommand("plugins");
		
		set_in_state_item = new JMenuItem("Set Initial state");
		set_in_state_item.setActionCommand("init");
		
		set_in_state_item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//System.out.println("INSERIRE IL WIZARD");
				SetInitialState rp = new SetInitialState();
				rp.setVisible(true);
				
			}
		});
		
		exit_item = new JMenuItem("Exit");
		
		file_menu.add(plugins_item);
		file_menu.addSeparator();
		file_menu.add(set_in_state_item);
		file_menu.addSeparator();
		file_menu.add(exit_item);
		
		//Menu and menu items for editing resource perspective
		resource_perspective_menu = new JMenu("Resource Perspective");
		services_menu_item = new JMenuItem("Manage Services");		
		capabilities_menu_item = new JMenuItem("Manage Capabilities");
		
		
		at_term = new JMenu("Atomic Term");
		data_type = new JMenuItem("Data Perspective");
		task = new JMenu("Task");
		add_task = new JMenuItem("Add new Task");
		edit_task = new JMenu("Edit Task");
		delete_task = new JMenu("Delete Task");
		ex_event = new JMenu("Exogenous Event");
		add_exEvent = new JMenuItem("Add new Exogenous Event");
		edit_exEvent = new JMenu("Edit Exogenous Event");
		delete_exEvent = new JMenu("Delete Exogenous Event");
		formula = new JMenu("Formula");
		add_formula = new JMenuItem("Add new Formula");
		edit_formula = new JMenu("Edit Formula");
		delete_formula = new JMenu("Delete Formula");
		add_atTerm = new JMenuItem("Add new Atomic Term");
		edit_atTerm = new JMenu("Edit Atomic Term");
		delete_atTerm = new JMenu("Delete Atomic Term");
		
		menu.add(resource_perspective_menu);
		resource_perspective_menu.add(services_menu_item);
		resource_perspective_menu.add(capabilities_menu_item);
		
		menu.add(data_type);
		menu.addSeparator();
		
		menu.add(at_term);
		at_term.add(add_atTerm);
		at_term.addSeparator();
		at_term.add(edit_atTerm);
		at_term.addSeparator();
		at_term.add(delete_atTerm);
		
		menu.addSeparator();
		
		menu.add(task);
		task.add(add_task);
		task.addSeparator();
		task.add(edit_task);
		task.addSeparator();
		task.add(delete_task);
		
		menu.add(ex_event);
		ex_event.add(add_exEvent);
		ex_event.addSeparator();
		ex_event.add(edit_exEvent);
		ex_event.addSeparator();
		ex_event.add(delete_exEvent);
		
		menu.addSeparator();
		
		menu.add(formula);
		formula.add(add_formula);
		formula.addSeparator();
		formula.add(edit_formula);
		formula.addSeparator();
		formula.add(delete_formula);
		
	}

	public JMenu getResource_perspective_menu() {
		return resource_perspective_menu;
	}

	public JMenuItem getServices_menu_item() {
		return services_menu_item;
	}

	public JMenuItem getCapabilities_menu_item() {
		return capabilities_menu_item;
	}

	public JMenuItem getExit_menu_item() {
		return exit_item;
	}
	
	public JMenuItem getPlugins_menu_item() {
		return plugins_item;
	}
	

}
