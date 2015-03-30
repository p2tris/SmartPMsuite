package com.mxgraph.examples.swing.editor;

import java.awt.EventQueue;
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JMenu; 
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.TransferHandler;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.mxgraph.examples.swing.editor.EditorActions.HistoryAction;
import com.mxgraph.model.mxCell;
import com.mxgraph.smartml.model.Constants;
import com.mxgraph.smartml.view.DeployProcess;
import com.mxgraph.smartml.view.MainFrame;
import com.mxgraph.smartml.view.RunProcess;
import com.mxgraph.smartml.view.SetInitialState;
import com.mxgraph.smartml.view.SetTaskName;
import com.mxgraph.swing.util.mxGraphActions;
import com.mxgraph.util.mxResources;
import com.mxgraph.view.mxGraph;



public class EditorPopupMenu extends JPopupMenu
{

	mxCell selectedCell = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = -3132749140550242191L;

	public EditorPopupMenu(BasicGraphEditor editor)
	{
		
		boolean selected = !editor.getGraphComponent().getGraph()
				.isSelectionEmpty();
		
		
		add(editor.bind(mxResources.get("undo"), new HistoryAction(true),
				"/com/mxgraph/examples/swing/images/undo.gif"));

		addSeparator();

		add(
				editor.bind(mxResources.get("cut"), TransferHandler
						.getCutAction(),
						"/com/mxgraph/examples/swing/images/cut.gif"))
				.setEnabled(selected);
		add(
				editor.bind(mxResources.get("copy"), TransferHandler
						.getCopyAction(),
						"/com/mxgraph/examples/swing/images/copy.gif"))
				.setEnabled(selected);
		add(editor.bind(mxResources.get("paste"), TransferHandler
				.getPasteAction(),
				"/com/mxgraph/examples/swing/images/paste.gif"));

		addSeparator();

		add(
				editor.bind(mxResources.get("delete"), mxGraphActions
						.getDeleteAction(),
						"/com/mxgraph/examples/swing/images/delete.gif"))
				.setEnabled(selected);

		addSeparator();

		// Creates the format menu
		JMenu menu = (JMenu) add(new JMenu(mxResources.get("format")));

		EditorMenuBar.populateFormatMenu(menu, editor);

		// Creates the shape menu
		//menu = (JMenu) add(new JMenu(mxResources.get("shape")));

		//EditorMenuBar.populateShapeMenu(menu, editor);

		addSeparator();

		/*
		add(
				editor.bind(mxResources.get("edit"), mxGraphActions
						.getEditAction())).setEnabled(selected);

		addSeparator();
		 */
		add(editor.bind(mxResources.get("selectVertices"), mxGraphActions
				.getSelectVerticesAction()));
		add(editor.bind(mxResources.get("selectEdges"), mxGraphActions
				.getSelectEdgesAction()));

		addSeparator();

		add(editor.bind(mxResources.get("selectAll"), mxGraphActions
				.getSelectAllAction()));
		
		/* ********************************************************
		 * ******************************************************** */
		/* ----------- SMARTPM - ------------- */
		
		selectedCell = (mxCell) editor.getGraphComponent().getGraph().getSelectionCell();
        final mxGraph graph = (mxGraph) editor.getGraphComponent().getGraph();
		
		final BasicGraphEditor ed = editor;
		
		if(selected && selectedCell.isVertex()){
			addSeparator();
			// NEW ITEM - SET THE NAME OF THE TASK 
			JMenuItem task_names = new JMenuItem("Set Task Name"); 
			add(task_names);    //.setEnabled(selected);
			
			task_names.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					SetTaskName tn = new SetTaskName(graph,selectedCell);
					tn.setVisible(true);
					ed.getGraphComponent().getGraph().refresh();
					
				}
			}); 
			
		}
		else if(selected && selectedCell.isEdge()){
			addSeparator();
			// NEW ITEM - SET THE CONDITION ON THE EDGE 
			JMenuItem edge_condition = new JMenuItem("Set Edge Condition"); 
			add(edge_condition);    //.setEnabled(selected);
			
			edge_condition.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					SetTaskName tn = new SetTaskName(graph,selectedCell);
					tn.setVisible(true);
					ed.getGraphComponent().getGraph().refresh();
					
				}
			}); 
			
		}
		else{
		
			addSeparator();
			// NEW ITEM - ADD THE DOMAIN THEORY 
			JMenuItem domain = new JMenuItem("Domain Theory"); 
			add(domain);    //.setEnabled(selected);
						
			addSeparator();
			
			JMenuItem deploy_process = new JMenuItem("Deploy");
			JMenuItem run_process = new JMenuItem("Run Process");
			add(deploy_process);
			add(run_process);
			
			domain.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					MainFrame sm = new MainFrame();
					sm.setVisible(true);
					
				}
			});  // ASCOLTATORE per il bottone domain. Lo metto direttamente qui, trattandosi solo di una voce del menu
			

			
			deploy_process.addActionListener(new ActionListener()
	        {
	            public void actionPerformed(ActionEvent ae)
	            {
	                Runnable runnable = new Runnable() {
	                    @Override
	                    public void run() {
	                        new DeployProcess().displayGUI();
	                    }
	                };
	                EventQueue.invokeLater(runnable);
	            }
	            
	            
	        });
			
run_process.addActionListener(new ActionListener() {
                
                @Override
                
                
                public void actionPerformed(ActionEvent e) {
                    
                	int reply = JOptionPane.showConfirmDialog(null, "Do you want to simulate the execution of the process?", "ATTENTION!", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon("images/question_icon.png"));
                    
                	if(reply == 0 || reply ==1) {
                	
                	if(reply==0) Constants.setModality_of_execution("simulation");
                	else if(reply==1) Constants.setModality_of_execution("execution");
                	
                    try {
                        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                    } catch (ClassNotFoundException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (InstantiationException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (IllegalAccessException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (UnsupportedLookAndFeelException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    Thread thread = new Thread(new Runnable() {
                        
                        public void run() {
                            RunProcess rp = new RunProcess();
                        }
                    });
                    thread.start();
                   
                    ed.exit();
                    
                }
                }
            });
			
		}
	}
	
}
