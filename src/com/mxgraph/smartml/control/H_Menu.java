package com.mxgraph.smartml.control;

import java.awt.event.ActionEvent;      
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import com.mxgraph.smartml.model.XMLParser;
import com.mxgraph.smartml.view.AddAtomicTerm;
import com.mxgraph.smartml.view.CapabilitiesPerspective;
import com.mxgraph.smartml.view.DataType;
import com.mxgraph.smartml.view.EditAtomicTerm;
import com.mxgraph.smartml.view.EditExogenousEvent;
import com.mxgraph.smartml.view.EditFormula;
import com.mxgraph.smartml.view.EditTask;
import com.mxgraph.smartml.view.AddExogenousEvent;
import com.mxgraph.smartml.view.AddFormula;
import com.mxgraph.smartml.view.Menu;
import com.mxgraph.smartml.view.PluginsManager;
import com.mxgraph.smartml.view.ResourcePerspective;
import com.mxgraph.smartml.view.ServicePerspective;
import com.mxgraph.smartml.view.AddTask;


public class H_Menu {
	public Menu _view = null;
	
	
	public H_Menu (Menu i_view){
		_view = i_view;
		installListeners();
	}

	private void installListeners() {
		
		_view.getExit_menu_item().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equalsIgnoreCase("Exit")){
					XMLParser.mf.dispose();
				}
			}
		});
		
		_view.getPlugins_menu_item().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equalsIgnoreCase("plugins")){

					PluginsManager pm = new PluginsManager();
					
				}
			}
		});
		
		_view.add_atTerm.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equalsIgnoreCase("Add new Atomic Term")){
					AddAtomicTerm at = new AddAtomicTerm();
					at.setVisible(true);	
				}
			}
		});
		
		
		 _view.edit_atTerm.addMenuListener(new MenuListener() {
			
			@Override
			public void menuSelected(MenuEvent e) {
				// Auto-generated method stub
				if(e.getSource().equals(_view.edit_atTerm)){
					_view.edit_atTerm.removeAll();
					Vector<String> nat = XMLParser.getAllAtomicTermsName(false);
					
				if(nat.size()>0) {				
						for(int index=0;index<nat.size();index++){
							JMenuItem at_mi = new JMenuItem(nat.get(index));
							_view.edit_atTerm.add(at_mi);
					}
				}
				else {
					JMenuItem at_mi = new JMenuItem("<empty>");
					_view.edit_atTerm.add(at_mi);
					at_mi.setEnabled(false);
				}
				}	
				
				for(int i=0;i<_view.edit_atTerm.getItemCount();i++){
					_view.edit_atTerm.getItem(i).addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							for(int i=0;i<_view.edit_atTerm.getItemCount();i++){
								if(e.getActionCommand().equalsIgnoreCase(_view.edit_atTerm.getItem(i).getText())){
									//System.out.println("AGGIORNA L'ATOMIC TERM: " + _view.edit_atTerm.getItem(i).getText());
									EditAtomicTerm edat = new EditAtomicTerm(_view.edit_atTerm.getItem(i).getText());
									edat.setVisible(true);
								}
							}
							
								
						}
						
					});
				}
				
				
				
			}
			
			@Override
			public void menuDeselected(MenuEvent e) {
				// Auto-generated method stub
				
			}
			
			@Override
			public void menuCanceled(MenuEvent e) {
				// Auto-generated method stub
				
			}
		});
		
		_view.delete_atTerm.addMenuListener(new MenuListener() {
			
			@Override
			public void menuSelected(MenuEvent e) {
				// Auto-generated method stub
				if(e.getSource().equals(_view.delete_atTerm)){
					_view.delete_atTerm.removeAll();
					Vector<String> natr = XMLParser.getAllAtomicTermsName(false);
					
					if(natr.size()>0) {
						for(int index1=0;index1<natr.size();index1++){
							JMenuItem dat_mi = new JMenuItem(natr.get(index1));
							_view.delete_atTerm.add(dat_mi);
					}
					}
					else {
						JMenuItem dat_mi = new JMenuItem("<empty>");
						_view.delete_atTerm.add(dat_mi);
						dat_mi.setEnabled(false);
					}
				}
				for(int i=0;i<_view.delete_atTerm.getItemCount();i++){
					_view.delete_atTerm.getItem(i).addActionListener(new ActionListener() {
							
						@Override
						public void actionPerformed(ActionEvent e) {
							for(int i=0;i<_view.delete_atTerm.getItemCount();i++){
								if(e.getActionCommand().equalsIgnoreCase(_view.delete_atTerm.getItem(i).getText())){
									int ret = JOptionPane.showConfirmDialog(_view, "Are you sure you want to delete this atomic term?");
									if(ret==JOptionPane.YES_OPTION){
										//System.out.println("CANCELLARE L'ATOMIC TERM: " + _view.delete_atTerm.getItem(i).getText());
										
										XMLParser.deleteTermFromTheoryElement(_view.delete_atTerm.getItem(i).getText());
										XMLParser.deleteAtomicTerm(_view.delete_atTerm.getItem(i).getText());
										
										XMLParser.mf.updateInformationView();
									
									}
									else{
										// � stato scelto "NO" oppure "CANCEL"
									}
								}
							}
								
						}
						
					});
				}
				
				
			}
			
			@Override
			public void menuDeselected(MenuEvent e) {
				// Auto-generated method stub
				
			}
			
			@Override
			public void menuCanceled(MenuEvent e) {
				// Auto-generated method stub
				
			}
		});
		
		
		_view.data_type.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				DataType dt = new DataType();
				dt.setVisible(true);
				// PRECARICO I VARI DATI
				dt.mostraBooleanValues();
				dt.mostraBoundsInt(XMLParser.getIntegerType());
				dt.mostraDataTypes(XMLParser.getUserDataTypes(false));
			
			}
		});
		
		_view.getServices_menu_item().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				ServicePerspective svp = new ServicePerspective();
				svp.setVisible(true);
				
			}
		});
		
		_view.getCapabilities_menu_item().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				CapabilitiesPerspective cpv = new CapabilitiesPerspective();
				cpv.setVisible(true);
				
			}
		});
		
		_view.add_task.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equals("Add new Task")){
					AddTask t = new AddTask();
					t.setVisible(true);
				}
				
			}
		});
		
		_view.edit_task.addMenuListener(new MenuListener() {
			
			@Override
			public void menuSelected(MenuEvent e) {
				// Auto-generated method stub
				if(e.getSource().equals(_view.edit_task)){
					_view.edit_task.removeAll();
					Vector<String> ntsk = XMLParser.getTaskNames(false);
					
					if(ntsk.size()>0) {
						for(int index=0;index<ntsk.size();index++){
							JMenuItem ts_mi = new JMenuItem(ntsk.get(index));
							_view.edit_task.add(ts_mi);
						}
					}
					else {
						JMenuItem ts_mi = new JMenuItem("<empty>");
						_view.edit_task.add(ts_mi);
						ts_mi.setEnabled(false);
					}
				}	
				
				for(int i=0;i<_view.edit_task.getItemCount();i++){
					_view.edit_task.getItem(i).addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							for(int i=0;i<_view.edit_task.getItemCount();i++){
								if(e.getActionCommand().equalsIgnoreCase(_view.edit_task.getItem(i).getText())){
									
									EditTask edt = new EditTask(_view.edit_task.getItem(i).getText());
									edt.setVisible(true);
								}
							}
							
								
						}
						
					});
				}
				
				
				
			}
			
			@Override
			public void menuDeselected(MenuEvent e) {
				// Auto-generated method stub
				
			}
			
			@Override
			public void menuCanceled(MenuEvent e) {
				// Auto-generated method stub
				
			}
		});
		
		_view.delete_task.addMenuListener(new MenuListener() {
			
			@Override
			public void menuSelected(MenuEvent e) {
				// Auto-generated method stub
				if(e.getSource().equals(_view.delete_task)){
					_view.delete_task.removeAll();
					Vector<String> ntsk = XMLParser.getTaskNames(false);
					
					if(ntsk.size()>0) {
					for(int index1=0;index1<ntsk.size();index1++){
						JMenuItem dts_mi = new JMenuItem(ntsk.get(index1));
						_view.delete_task.add(dts_mi);
					}
					}					
					else {
						JMenuItem dts_mi = new JMenuItem("<empty>");
						_view.delete_task.add(dts_mi);
						dts_mi.setEnabled(false);
					}
					
				}
				for(int i=0;i<_view.delete_task.getItemCount();i++){
					_view.delete_task.getItem(i).addActionListener(new ActionListener() {
							
						@Override
						public void actionPerformed(ActionEvent e) {
							for(int i=0;i<_view.delete_task.getItemCount();i++){
								if(e.getActionCommand().equalsIgnoreCase(_view.delete_task.getItem(i).getText())){
									int ret = JOptionPane.showConfirmDialog(_view, "Are you sure you want to delete this task?");
									if(ret==JOptionPane.YES_OPTION){
										
										XMLParser.deleteTaskFromXSD(_view.delete_task.getItem(i).getText());
										XMLParser.deleteTask(_view.delete_task.getItem(i).getText());
										
										XMLParser.mf.updateInformationView();
										
									}
								}
							}	
						}

					});
				}
			}
			
			public void menuDeselected(MenuEvent e) {}
			
			public void menuCanceled(MenuEvent e) {}
		
		});
		
		_view.add_exEvent.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equals("Add new Exogenous Event")){
					AddExogenousEvent exEv = new AddExogenousEvent();  
					exEv.setVisible(true);
				}
				
			}
		});
		
		_view.edit_exEvent.addMenuListener(new MenuListener() {
			
			@Override
			public void menuSelected(MenuEvent e) {
				// Auto-generated method stub
				if(e.getSource().equals(_view.edit_exEvent)){
					_view.edit_exEvent.removeAll();
					Vector<String> nexEv = XMLParser.getExogenousEventsName();
					if(nexEv.size()>0) {
						for(int index=0;index<nexEv.size();index++){
							JMenuItem exE = new JMenuItem(nexEv.get(index));
							_view.edit_exEvent.add(exE);
						}
					}
					else {
						JMenuItem exE = new JMenuItem("<empty>");
						_view.edit_exEvent.add(exE);
						exE.setEnabled(false);
					}
				}
				for(int i=0;i<_view.edit_exEvent.getItemCount();i++){
					_view.edit_exEvent.getItem(i).addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							for(int i=0;i<_view.edit_exEvent.getItemCount();i++){
								if(e.getActionCommand().equalsIgnoreCase(_view.edit_exEvent.getItem(i).getText())){
									
									EditExogenousEvent eExEv = new EditExogenousEvent(_view.edit_exEvent.getItem(i).getText());
									eExEv.setVisible(true);
								}
							}
							
								
						}
						
					});
				}
				
				
			}
			
			@Override
			public void menuDeselected(MenuEvent e) {
				// Auto-generated method stub
				
			}
			
			@Override
			public void menuCanceled(MenuEvent e) {
				// Auto-generated method stub
				
			}
		});
		
		_view.delete_exEvent.addMenuListener(new MenuListener() {
			
			@Override
			public void menuSelected(MenuEvent e) {
				// Auto-generated method stub
				if(e.getSource().equals(_view.delete_exEvent)){
					_view.delete_exEvent.removeAll();
					Vector<String> nexEv = XMLParser.getExogenousEventsName();
					if(nexEv.size()>0) {
						for(int index1=0;index1<nexEv.size();index1++){
							JMenuItem d_exE = new JMenuItem(nexEv.get(index1));
							_view.delete_exEvent.add(d_exE);
						}
					}
					else {
						JMenuItem d_exE = new JMenuItem("<empty>");
						_view.delete_exEvent.add(d_exE);
						d_exE.setEnabled(false);
					}
				}
				for(int i=0;i<_view.delete_exEvent.getItemCount();i++){
					_view.delete_exEvent.getItem(i).addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							for(int i=0;i<_view.delete_exEvent.getItemCount();i++){
								if(e.getActionCommand().equalsIgnoreCase(_view.delete_exEvent.getItem(i).getText())){
									int ret = JOptionPane.showConfirmDialog(_view, "Are you sure you want to delete this exogenous event?");
									if(ret==JOptionPane.YES_OPTION){
										
										XMLParser.deleteExogenousEventfromXSD(_view.delete_exEvent.getItem(i).getText());
										XMLParser.deleteExogenousEvent(_view.delete_exEvent.getItem(i).getText());
										
										XMLParser.mf.updateInformationView();
										
									}
								}
							}
								
						}
						
					});
				}
				
			}
			
			@Override
			public void menuDeselected(MenuEvent e) {
				// Auto-generated method stub
				
			}
			
			@Override
			public void menuCanceled(MenuEvent e) {
				// Auto-generated method stub
				
			}
		});
		
		_view.add_formula.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equalsIgnoreCase("Add new Formula")){
					AddFormula f = new AddFormula();
					f.setVisible(true);
				}
				
			}
		});
		
		_view.edit_formula.addMenuListener(new MenuListener() {
			
			public void menuSelected(MenuEvent e) {
				
				if(e.getSource().equals(_view.edit_formula)){
					_view.edit_formula.removeAll();
					Vector<String> nfrm = XMLParser.getFormulae();
					
					if(nfrm.size()>0) {
						for(int i=0;i<nfrm.size();i++){
						JMenuItem fr_mi = new JMenuItem(nfrm.get(i));
						_view.edit_formula.add(fr_mi);
						}
					}
					else  {
						JMenuItem fr_mi = new JMenuItem("<empty>");
						_view.edit_formula.add(fr_mi);
						fr_mi.setEnabled(false);
					}
				}
				for(int j=0;j<_view.edit_formula.getItemCount();j++){
					_view.edit_formula.getItem(j).addActionListener(new ActionListener() {						
						public void actionPerformed(ActionEvent e) {
							for(int i=0;i<_view.edit_formula.getItemCount();i++){
								if(e.getActionCommand().equalsIgnoreCase(_view.edit_formula.getItem(i).getText())){									
									EditFormula ef = new EditFormula(_view.edit_formula.getItem(i).getText());
									ef.setVisible(true);
								}
							}
						}
					});
				}
			}
			
			public void menuDeselected(MenuEvent e) {
			}
			
			public void menuCanceled(MenuEvent e) {
			}
		});
		
		_view.delete_formula.addMenuListener(new MenuListener() {
			
			public void menuSelected(MenuEvent e) {
				if(e.getSource().equals(_view.delete_formula)){
					_view.delete_formula.removeAll();
					Vector<String> nfrm = XMLParser.getFormulae();
					if(nfrm.size()>0) {
						for(int i=0;i<nfrm.size();i++){
							JMenuItem fr_mi = new JMenuItem(nfrm.get(i));
							_view.delete_formula.add(fr_mi);
						}
					}
					else  {
						JMenuItem fr_mi = new JMenuItem("<empty>");
						_view.delete_formula.add(fr_mi);
						fr_mi.setEnabled(false);
					}
				}
				for(int k=0;k<_view.delete_formula.getItemCount();k++){
					_view.delete_formula.getItem(k).addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							for(int i=0;i<_view.delete_formula.getItemCount();i++){
								if(e.getActionCommand().equalsIgnoreCase(_view.delete_formula.getItem(i).getText())){
									int ret = JOptionPane.showConfirmDialog(_view, "Do you want to delete this formula?");
									if(ret==JOptionPane.YES_OPTION){										
										XMLParser.deleteFormulaFromXSD(_view.delete_formula.getItem(i).getText());
										XMLParser.deleteFormula(_view.delete_formula.getItem(i).getText());		
										
										XMLParser.mf.updateInformationView();
									}
								}
							}
							
						}
					});
				}
			}
			
			public void menuDeselected(MenuEvent e) {
			}
			
			public void menuCanceled(MenuEvent e) {
			}
		});
		
		
		
		
	} // Chiude installListeners()
	

}
