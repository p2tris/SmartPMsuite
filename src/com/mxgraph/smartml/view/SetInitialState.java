package com.mxgraph.smartml.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.SwingWorker;
import javax.swing.Timer;

import org.freixas.jwizard.JWizardDialog;
import org.freixas.jwizard.JWizardPanel;

import com.mxgraph.smartml.model.XMLParser;




public class SetInitialState extends JWizardDialog{
	
	private static final long serialVersionUID = 1L;
	
	public String initialState_content = "";
	public String goalCondition_content = "";
	public Vector<String> inputParams = null;
	
	public SetInitialState(){
		setModal(true);
		
	
		setTitle("INITIAL STATE CONFIGURATION");
		
		//createPDDLPlanningDomain();
		
		//setSize(400, 450);
		setSize(400, 500);
		setResizable(false);
		
		
		// Create each step
		addWizardPanel(new Step0());
		//addWizardPanel(new Step1());
		
		// We don't want to have the cancel button enabled when we're done
	    disableCancelAtEnd();
	
	}
	
	/* Definizione di ogni STEP (sono classi interne)*/
	
	public class Step0 extends JWizardPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		 
		public SpringLayout springLayout;
		public JScrollPane scrollPane;
		public JTextArea textArea;
		
		public String riga = ""; 
		
		
		protected void makingVisible(){
			String nome="";
			
			Vector<Vector<Vector<String>>> finale = new Vector<Vector<Vector<String>>>(); 
			Vector<Vector<String>> appoggio = null;
			Vector<Vector<String>> app = null;
			Vector<Vector<String>> app_inArg = null;
			Vector<Vector<String>> app_out = null;
			
			Vector<Vector<String>> at_names = new Vector<Vector<String>>();
			Vector<String> app_name = new Vector<String>();
			/*
			Vector<String> app_name = new Vector<String>();
			app_name.add("provides");
			at_names.add(app_name);
			
			app_name = new Vector<String>();
			app_name.add("requires");
			at_names.add(app_name);
			*/
			
			Vector <String> atomic_term_names_vector = XMLParser.getAllAtomicTermsName(false);
			
			for(int i=0;i<atomic_term_names_vector.size();i++){
				app_name = new Vector<String>();
				app_name.add(atomic_term_names_vector.get(i));
				at_names.add(app_name);
			}
			// at_names � il vettore di tutti i sottovettori con ognuno un nome di un atomic term
				
			for(int i=0;i<at_names.size();i++){
				
				System.out.println(at_names.get(i).firstElement());
				
				app = new Vector<Vector<String>>();
				app = XMLParser.getAtomicTermParametersTypesAndReturnType(at_names.get(i).firstElement());

				app_inArg = new Vector<Vector<String>>();
				app_inArg.add(app.firstElement());
				app_out = new Vector<Vector<String>>();
				app_out.add(app.lastElement());
				
				appoggio = new Vector<Vector<String>>();
				appoggio.add(at_names.get(i));
				appoggio.add(app_inArg.firstElement());
				appoggio.add(app_out.firstElement());
				
				finale.add(appoggio);
			
			}
			
			// "finale" sar� un vettore di vettore di vettori, di questo tipo.
			// [[[provides], [Participant_type, Capability_type], [Boolean_type]], [[requires], [task_repository, Capability_type], [Boolean_type]], [[at], [Participant_type], [Location_type]], ... , [[covered], [Location_type], [Boolean_type]]]
			// ogni vettore interno contiene 3 vettori:
			// il primo � il nome dell'atomi term;
			// il secondo sono gli argomenti presi in ingresso da tale atomic term --> i tipi
			// il terzo � il valore restituito dall'atomic term  --> il tipo
			for(int j=0;j<finale.size();j++){
				//System.out.println(finale);
				nome =  finale.get(j).firstElement().firstElement() ; // prendo il nome dell'atomic term
				//System.out.println("nome "+nome);
				Vector<String> argom_in = finale.get(j).get(1);	
				//System.out.println("1"+argom_in);
				Vector<String> out_value = finale.get(j).lastElement();
				//System.out.println("2"+out_value);
				String output = XMLParser.getDataObjectsOf(out_value.firstElement(),true).firstElement(); 
				//System.out.println(output);
				
				if(argom_in.size() == 0){
					// ---> l'atomic term non prende alcun argomento in input
					
					riga = riga + nome + "[" + "." + "]" + "=" + output + "\n";  
				}
				if(argom_in.size() == 1){
					Vector<String> arg1 = XMLParser.getDataObjectsOf(argom_in.get(0),true);
					if(arg1.contains("SRVC")){
						arg1.remove("SRVC");
					}
					for(int index1=0;index1<arg1.size();index1++){
						String a1 = arg1.get(index1);
						riga = riga + nome + "[" + a1 + "]" + "=" + output + "\n";	
					}
				}
				if(argom_in.size() == 2){
					Vector<String> arg1 = XMLParser.getDataObjectsOf(argom_in.get(0),true);
					Vector<String> arg2 = XMLParser.getDataObjectsOf(argom_in.get(1),true);
					if(arg1.contains("SRVC")){
						arg1.remove("SRVC");
					}
					if(arg2.contains("SRVC")){
						arg2.remove("SRVC");
					}
					for(int index1=0;index1<arg1.size();index1++){
						String a1 = arg1.get(index1);
						for(int index2=0;index2<arg2.size();index2++){
							String a2 = arg2.get(index2);
							riga = riga + nome + "[" + a1 + "," + a2 + "]" + "=" + output + "\n";	
						}
					}
				}
				if(argom_in.size() == 3){
					Vector<String> arg1 = XMLParser.getDataObjectsOf(argom_in.get(0),true);
					Vector<String> arg2 = XMLParser.getDataObjectsOf(argom_in.get(1),true);
					Vector<String> arg3 = XMLParser.getDataObjectsOf(argom_in.get(2),true);
					if(arg1.contains("SRVC")){
						arg1.remove("SRVC");
					}
					if(arg2.contains("SRVC")){
						arg2.remove("SRVC");
					}
					if(arg3.contains("SRVC")){
						arg3.remove("SRVC");
					}
					for(int index1=0;index1<arg1.size();index1++){
						String a1 = arg1.get(index1);
						for(int index2=0;index2<arg2.size();index2++){
							String a2 = arg2.get(index2);
							for(int index3=0;index3<arg3.size();index3++){
								String a3 = arg3.get(index3);
								riga = riga + nome + "[" + a1 + "," + a2 +","+ a3 + "]" + "=" + output + "\n";
							}
						}	
					}
				}
				if(argom_in.size() == 4){
					Vector<String> arg1 = XMLParser.getDataObjectsOf(argom_in.get(0),true);
					Vector<String> arg2 = XMLParser.getDataObjectsOf(argom_in.get(1),true);
					Vector<String> arg3 = XMLParser.getDataObjectsOf(argom_in.get(2),true);
					Vector<String> arg4 = XMLParser.getDataObjectsOf(argom_in.get(3),true);
					if(arg1.contains("SRVC")){
						arg1.remove("SRVC");
					}
					if(arg2.contains("SRVC")){
						arg2.remove("SRVC");
					}
					if(arg3.contains("SRVC")){
						arg3.remove("SRVC");
					}
					if(arg4.contains("SRVC")){
						arg4.remove("SRVC");
					}
					for(int index1=0;index1<arg1.size();index1++){
						String a1 = arg1.get(index1);
						for(int index2=0;index2<arg2.size();index2++){
							String a2 = arg2.get(index2);
							for(int index3=0;index3<arg3.size();index3++){
								String a3 = arg3.get(index3);
								for(int index4=0;index4<arg4.size();index4++){
									String a4 = arg4.get(index4);
									riga = riga + nome + "[" + a1 + "," + a2 +","+ a3 + ","+ a4 + "]" + "=" + output + "\n";  	
								}
							}
						}	
					}
				}
				if(argom_in.size() == 5){
					Vector<String> arg1 = XMLParser.getDataObjectsOf(argom_in.get(0),true);
					Vector<String> arg2 = XMLParser.getDataObjectsOf(argom_in.get(1),true);
					Vector<String> arg3 = XMLParser.getDataObjectsOf(argom_in.get(2),true);
					Vector<String> arg4 = XMLParser.getDataObjectsOf(argom_in.get(3),true);
					Vector<String> arg5 = XMLParser.getDataObjectsOf(argom_in.get(4),true);
					if(arg1.contains("SRVC")){
						arg1.remove("SRVC");
					}
					if(arg2.contains("SRVC")){
						arg2.remove("SRVC");
					}
					if(arg3.contains("SRVC")){
						arg3.remove("SRVC");
					}
					if(arg4.contains("SRVC")){
						arg4.remove("SRVC");
					}
					if(arg5.contains("SRVC")){
						arg5.remove("SRVC");
					}
					for(int index1=0;index1<arg1.size();index1++){
						String a1 = arg1.get(index1);
						for(int index2=0;index2<arg2.size();index2++){
							String a2 = arg2.get(index2);
							for(int index3=0;index3<arg3.size();index3++){
								String a3 = arg3.get(index3);
								for(int index4=0;index4<arg4.size();index4++){
									String a4 = arg4.get(index4);
									for(int index5=0;index5<arg5.size();index5++){
										String a5 = arg5.get(index5);
										riga = riga + nome + "[" + a1 + "," + a2 +","+ a3 + ","+ a4 + "," + a5 + "]" + "=" + output + "\n";
									}
								}
							}
						}	     
					}
				}
				   
				textArea.setText(riga);
			}
			
		} // CHIUDE IL MAKINGVISIBLE()
		
		
		public Step0(){
			
			setStepTitle("Set Initial State");
			
			springLayout = new SpringLayout();
			setLayout(springLayout);
			
			scrollPane = new JScrollPane();
			scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 41, SpringLayout.NORTH, this);
			springLayout.putConstraint(SpringLayout.WEST, scrollPane, 31, SpringLayout.WEST, this);
			springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, 380, SpringLayout.NORTH, this);
			springLayout.putConstraint(SpringLayout.EAST, scrollPane, 366, SpringLayout.WEST, this);
			add(scrollPane);
			
			textArea = new JTextArea();
			scrollPane.setViewportView(textArea);

			
			// Set the previous (none) and next steps
			setBackStep(-1);
			//setNextStep(0);
			
		} // Chiude il costruttore Step0()
		
		protected void next() {
			String textArea_content = textArea.getText();
			initialState_content = textArea_content;
			
			super.next();
		}
	
		
	} // Chiude la classe Step0
	
	
	public class Step1 extends JWizardPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		public SpringLayout springLayout;
		public JScrollPane scrollPane;
		public JTextArea textArea;
		
		public String riga = ""; 
		
		protected void makingVisible(){
			
			textArea.setText("Deployment in progress");
			
	
			
			
			/*
			String nome="";
			Vector<Vector<Vector<String>>> finale = new Vector<Vector<Vector<String>>>(); 
			Vector<Vector<String>> appoggio = null;
			Vector<Vector<String>> app = null;
			Vector<Vector<String>> app_inArg = null;
			Vector<Vector<String>> app_out = null;
			
			Vector<Vector<String>> at_names = new Vector<Vector<String>>();
			
			Vector<String> app_name = null;
			
			Vector<String> all = XMLParser.getAllDynamicTermsName();
			all.addAll(XMLParser.getFormulae());
			
			for(int i=0;i<all.size();i++){
				app_name = new Vector<String>();
				app_name.add(all.get(i));
				at_names.add(app_name);
			}
			// at_names � il vettore di tutti i sottovettori con ognuno un nome di un atomic term
	
			for(int i=0;i<at_names.size();i++){
				app = new Vector<Vector<String>>();
				app = XMLParser.getAtomicTermParametersNameAndType(at_names.get(i).firstElement());
		
				app_inArg = new Vector<Vector<String>>();
				app_inArg.add(app.firstElement());
				app_out = new Vector<Vector<String>>();
				app_out.add(app.lastElement());
				
				appoggio = new Vector<Vector<String>>();
				appoggio.add(at_names.get(i));
				appoggio.add(app_inArg.firstElement());
				appoggio.add(app_out.firstElement());
				
				finale.add(appoggio);
			
			}
			
			// "finale" sar� un vettore di vettore di vettori, di questo tipo.
			// [[[at], [Participant_type], [Location_type]], [[evacuated], [Location_type], [Boolean_type]], [[photoTaken], [Location_type], [Boolean_type]], [[status], [Location_type], [Status_type]], [[batteryLevel], [Robot_type], [Integer_type]], [[generalBattery], [], [Integer_type]], [[isConnected], [Actor_type], [Boolean_type]]]
			// ogni vettore interno contiene 3 vettori:
			// il primo � il nome dell'atomi term;
			// il secondo sono gli argomenti presi in ingresso da tale atomic term --> i tipi
			// il terzo � il valore restituito dall'atomic term  --> il tipo
			
			for(int j=0;j<finale.size();j++){
				
				nome =  finale.get(j).firstElement().firstElement() ; // prendo il nome dell'atomic term
				
				Vector<String> argom_in = finale.get(j).get(1);	
				Vector<String> out_value = finale.get(j).lastElement();
				//System.out.println(out_value);
				String output = XMLParser.getDataObjectsOf(out_value.firstElement(),true).firstElement(); 
				//System.out.println(output);
				
				if(argom_in.size() == 0){
					// ---> l'atomic term non prende alcun argomento in input
					
					riga = riga + nome + "[" + "." + "]" + "=" + output + "\n";   
				}
				if(argom_in.size() == 1){
					Vector<String> arg1 = XMLParser.getDataObjectsOf(argom_in.get(0),true);
					if(arg1.contains("SRVC")){
						arg1.remove("SRVC");
					}
					for(int index1=0;index1<arg1.size();index1++){
						String a1 = arg1.get(index1);
						riga = riga + nome + "[" + a1 + "]" + "=" + output + "\n";	
					}
				}
				if(argom_in.size() == 2){
					Vector<String> arg1 = XMLParser.getDataObjectsOf(argom_in.get(0),true);
					Vector<String> arg2 = XMLParser.getDataObjectsOf(argom_in.get(1),true);
					if(arg1.contains("SRVC")){
						arg1.remove("SRVC");
					}
					if(arg2.contains("SRVC")){
						arg2.remove("SRVC");
					}
					for(int index1=0;index1<arg1.size();index1++){
						String a1 = arg1.get(index1);
						for(int index2=0;index2<arg2.size();index2++){
							String a2 = arg2.get(index2);
							riga = riga + nome + "[" + a1 + "," + a2 + "]" + "=" + output + "\n";	
						}
					}
				}
				if(argom_in.size() == 3){
					Vector<String> arg1 = XMLParser.getDataObjectsOf(argom_in.get(0),true);
					Vector<String> arg2 = XMLParser.getDataObjectsOf(argom_in.get(1),true);
					Vector<String> arg3 = XMLParser.getDataObjectsOf(argom_in.get(2),true);
					if(arg1.contains("SRVC")){
						arg1.remove("SRVC");
					}
					if(arg2.contains("SRVC")){
						arg2.remove("SRVC");
					}
					if(arg3.contains("SRVC")){
						arg3.remove("SRVC");
					}
					for(int index1=0;index1<arg1.size();index1++){
						String a1 = arg1.get(index1);
						for(int index2=0;index2<arg2.size();index2++){
							String a2 = arg2.get(index2);
							for(int index3=0;index3<arg3.size();index3++){
								String a3 = arg3.get(index3);
								riga = riga + nome + "[" + a1 + "," + a2 +","+ a3 + "]" + "=" + output + "\n";
							}
						}	
					}
				}
				if(argom_in.size() == 4){
					Vector<String> arg1 = XMLParser.getDataObjectsOf(argom_in.get(0),true);
					Vector<String> arg2 = XMLParser.getDataObjectsOf(argom_in.get(1),true);
					Vector<String> arg3 = XMLParser.getDataObjectsOf(argom_in.get(2),true);
					Vector<String> arg4 = XMLParser.getDataObjectsOf(argom_in.get(3),true);
					if(arg1.contains("SRVC")){
						arg1.remove("SRVC");
					}
					if(arg2.contains("SRVC")){
						arg2.remove("SRVC");
					}
					if(arg3.contains("SRVC")){
						arg3.remove("SRVC");
					}
					if(arg4.contains("SRVC")){
						arg4.remove("SRVC");
					}
					for(int index1=0;index1<arg1.size();index1++){
						String a1 = arg1.get(index1);
						for(int index2=0;index2<arg2.size();index2++){
							String a2 = arg2.get(index2);
							for(int index3=0;index3<arg3.size();index3++){
								String a3 = arg3.get(index3);
								for(int index4=0;index4<arg4.size();index4++){
									String a4 = arg4.get(index4);
									riga = riga + nome + "[" + a1 + "," + a2 +","+ a3 + ","+ a4 + "]" + "=" + output + "\n";  	
								}
							}
						}	
					}
				}
				if(argom_in.size() == 5){
					Vector<String> arg1 = XMLParser.getDataObjectsOf(argom_in.get(0),true);
					Vector<String> arg2 = XMLParser.getDataObjectsOf(argom_in.get(1),true);
					Vector<String> arg3 = XMLParser.getDataObjectsOf(argom_in.get(2),true);
					Vector<String> arg4 = XMLParser.getDataObjectsOf(argom_in.get(3),true);
					Vector<String> arg5 = XMLParser.getDataObjectsOf(argom_in.get(4),true);
					if(arg1.contains("SRVC")){
						arg1.remove("SRVC");
					}
					if(arg2.contains("SRVC")){
						arg2.remove("SRVC");
					}
					if(arg3.contains("SRVC")){
						arg3.remove("SRVC");
					}
					if(arg4.contains("SRVC")){
						arg4.remove("SRVC");
					}
					if(arg5.contains("SRVC")){
						arg5.remove("SRVC");
					}
					for(int index1=0;index1<arg1.size();index1++){
						String a1 = arg1.get(index1);
						for(int index2=0;index2<arg2.size();index2++){
							String a2 = arg2.get(index2);
							for(int index3=0;index3<arg3.size();index3++){
								String a3 = arg3.get(index3);
								for(int index4=0;index4<arg4.size();index4++){
									String a4 = arg4.get(index4);
									for(int index5=0;index5<arg5.size();index5++){
										String a5 = arg5.get(index5);
										riga = riga + nome + "[" + a1 + "," + a2 +","+ a3 + ","+ a4 + "," + a5 + "]" + "=" + output + "\n";
									}
								}
							}
						}	     
					}
				}
				   
				textArea.setText(riga);
			}*/
			
		} // CHIUDE IL makingVisible()
		

		public Step1() {
			
			setStepTitle("Deployment");
			
			springLayout = new SpringLayout();
			setLayout(springLayout);
			
			scrollPane = new JScrollPane();
			scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 41, SpringLayout.NORTH, this);
			springLayout.putConstraint(SpringLayout.WEST, scrollPane, 31, SpringLayout.WEST, this);
			springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, 380, SpringLayout.NORTH, this);
			springLayout.putConstraint(SpringLayout.EAST, scrollPane, 366, SpringLayout.WEST, this);
			add(scrollPane);
			
			textArea = new JTextArea();
			scrollPane.setViewportView(textArea);
			
			// Set the previous (none) and next steps 
			setBackStep(0);
			setNextStep(1);
			
		}  // Chiude il costruttore Step1() 
		
		protected void next() {
			if(textArea.getText().isEmpty()) {
				setNextStep(1);
			}
			else {
				setNextStep(2);
				
				String textArea_content = textArea.getText();
				goalCondition_content = textArea_content;
				
				super.next();
			}
			
		}

		
	} // Chiude la classe Step1
	
	/** Metodo ausiliario che legge l'area di testo dell'initial_state e fa il parsing
	 * della stringa in essa contenuta, restituendo il contenuto in un
	 * vettore di vettori di vettori. Esempio: [[[provides], [act1, movement], [false]]] */
	private Vector<Vector<Vector<String>>> LeggiInitialState() {
		Vector<Vector<Vector<String>>> final_vector = new Vector<Vector<Vector<String>>>();
		Vector<Vector<String>> appoggio = null;
		
		String[] tokens = initialState_content.split("\n");
		for(int index=0;index<tokens.length;index++) {
			// Ogni elemento dell'array tokens � una riga del tipo: "provides[act1,movement]=false"
			String[] tokens1 = tokens[index].split("\\[");
			// L'array tokens1 ha due elementi: 
			// - tokens1[0] � del tipo: "provides"  // NOME 
			// - tokens1[1] � del tipo: act1,movement]="false"
			Vector<String> app_name = new Vector<String>();
			app_name.add(tokens1[0]);
			
			appoggio = new Vector<Vector<String>>();
			appoggio.add(app_name);
			
			String[] tokens2 = tokens1[1].split("]=");
			// L'array tokens2 ha due elementi: 
			// - tokens2[0] � del tipo: "act1,movement" ... // ARGOMENTI  
			// - tokens2[1] � del tipo: "" false  ... // OUTPUT-VALORE
			Vector<String> app_ret = new Vector<String>();
			app_ret.add(tokens2[1]);
			
			String[] tokens3 = tokens2[0].split(",");
			Vector<String> argum_app = new Vector<String>(); 
			for(int x=0;x<tokens3.length;x++){
				
				argum_app.add(tokens3[x]);
				
			}
			
			appoggio.add(argum_app);
			appoggio.add(app_ret);
			
			final_vector.add(appoggio);
		}
		
		//System.out.println(final_vector);
		return final_vector;
	} // CHIUDE IL METODO
	
	/** Metodo ausiliario che legge l'area di testo della goal condition e fa il parsing
	 * della stringa in essa contenuta, restituendo il contenuto in un
	 * vettore di vettori di vettori. Esempio: [[[provides], [act1, movement], [false]]] */
	private Vector<Vector<Vector<String>>> LeggiGoalCondition() {
		Vector<Vector<Vector<String>>> final_vector = new Vector<Vector<Vector<String>>>();
		Vector<Vector<String>> appoggio = null;
		
		String[] tokens = goalCondition_content.split("\n");
		for(int index=0;index<tokens.length;index++) {
			// Ogni elemento dell'array tokens � una riga del tipo: "provides[act1,movement]=false"
			String[] tokens1 = tokens[index].split("\\[");
			// L'array tokens1 ha due elementi: 
			// - tokens1[0] � del tipo: "provides"  // NOME 
			// - tokens1[1] � del tipo: act1,movement]="false"
			Vector<String> app_name = new Vector<String>();
			app_name.add(tokens1[0]);
			
			appoggio = new Vector<Vector<String>>();
			appoggio.add(app_name);
			
			String[] tokens2 = tokens1[1].split("]=");
			// L'array tokens2 ha due elementi: 
			// - tokens2[0] � del tipo: "act1,movement" ... // ARGOMENTI  
			// - tokens2[1] � del tipo: "" false  ... // OUTPUT-VALORE
			Vector<String> app_ret = new Vector<String>();
			app_ret.add(tokens2[1]);
			
			String[] tokens3 = tokens2[0].split(",");
			Vector<String> argum_app = new Vector<String>(); 
			for(int x=0;x<tokens3.length;x++){
				argum_app.add(tokens3[x]);	
			}
			appoggio.add(argum_app);
			appoggio.add(app_ret);
			
			final_vector.add(appoggio);
		}
		
		//System.out.println(final_vector);
		return final_vector;
	} // CHIUDE IL METODO
	
	protected void finish() {
		
		
		/* PAOLA VERSION
		Vector<Vector<Vector<String>>> initialState_vector = LeggiInitialState();
		Vector<Vector<Vector<String>>> goal_vector = LeggiGoalCondition();
		Vector<Vector<String>> tipi = new Vector<Vector<String>>();
		for(int i=0;i<initialState_vector.size();i++) {
			Vector<String> inputParams = XMLParser.AtomicTermDetails2(initialState_vector.get(i).firstElement().firstElement()).firstElement();
			// inputParams � un vettore contenente SOLO i parametri di input 
			tipi.add(inputParams);
			// tipi � un vettore di vettori, contenente un "inputParams" per ogni atomic term
		}
		Vector<Vector<String>> tipi_g = new Vector<Vector<String>>();
		for(int k=0;k<goal_vector.size();k++) {
			Vector<String> inputP = XMLParser.AtomicTermOrFormulaDetails2(goal_vector.get(k).firstElement().firstElement()).firstElement();
			// inputP � un vettore contenente SOLO i parametri di input 
			tipi_g.add(inputP);
			// tipi_g � un vettore di vettori, contenente un "inputP" per ogni atomic term o formula
		}
		
		int ret = JOptionPane.showConfirmDialog(this, "Do you want to run the process?");
		if(ret==JOptionPane.YES_OPTION){
			
			XMLParser.resetInitialState();
			XMLParser.setInitialState(initialState_vector,tipi);
			
			XMLParser.resetGoalCondition();
			XMLParser.setGoalCondition(goal_vector, tipi_g);
			
		}
		else {
			// E' STATO SCELTO "NO" OPPURE "ANNULLA"
		}
		*/
		super.finish();
	}
	
	
	private void createPDDLPlanningDomain() {
		
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("(define (domain Derailment)\n");
		buffer.append("(:requirements :derived-predicates :typing :fluents :equality)\n");
		
		//************************************************************************
		//Definition of PDDL Types (services, capabilities, providers, data types)
		//************************************************************************
		
		buffer.append("(:types ");
		
		Vector providers_vector = XMLParser.getProvidersNames(false);
		
		if(providers_vector.size()>0) {
			
			for(int i=0;i<providers_vector.size();i++) {			
				if(i == providers_vector.size()-1)
					buffer.append(providers_vector.elementAt(i) + " - ");
				else
					buffer.append(providers_vector.elementAt(i) + " ");
			}
		}

		buffer.append("service capability");
	
		Vector user_data_types_vector = XMLParser.getUserDataTypeNames();
		
		if(user_data_types_vector.size()>0) {
			
			for(int i=0;i<user_data_types_vector.size();i++) {			
				if(i == (user_data_types_vector.size()-1))
					buffer.append(" " + user_data_types_vector.elementAt(i));
				else
					buffer.append(" " + user_data_types_vector.elementAt(i));
			}
			
		}
		
		buffer.append(")\n\n");
		
		//************************************************************************
		// Definition of PDDL Predicates: 
		// -- Complex Formulae, Functional and Boolean Terms are relational predicates.
		// -- Integer Terms are functions.
		//************************************************************************
		
		StringBuffer predicates_buffer = new StringBuffer();
		StringBuffer functions_buffer = new StringBuffer();
		
		Vector atomic_term_names_vector = XMLParser.getAllAtomicTermsName(false);
				
		for(int i=0;i<atomic_term_names_vector.size();i++) {
		
			StringBuffer atomic_term_buffer = new StringBuffer();
			
			String atomicTermName = (String) atomic_term_names_vector.elementAt(i);
			
			atomic_term_buffer.append("("+atomicTermName);
			
			Vector atomicTermParameters_vector = XMLParser.getAtomicTermParametersNameAndType(atomicTermName);
			
				for(int j=0;j<atomicTermParameters_vector.size();j++) {
					
					Vector atomicTermSingleParameter_vector = (Vector) atomicTermParameters_vector.elementAt(j);
				
							atomic_term_buffer.append(" ?" + atomicTermSingleParameter_vector.firstElement());
							atomic_term_buffer.append(" - " + atomicTermSingleParameter_vector.lastElement());							

				}
			
			String atomicTermReturnType = XMLParser.getAtomicTermReturnType(atomicTermName,false);
			
			if(atomicTermReturnType.equalsIgnoreCase("Boolean_Type")){
				atomic_term_buffer.append(")\n");
				predicates_buffer.append(atomic_term_buffer);
			}
			else if(atomicTermReturnType.equalsIgnoreCase("Integer_Type")){
				atomic_term_buffer.append(")\n");
				functions_buffer.append(atomic_term_buffer);
			}
			else {
				atomic_term_buffer.append(" ?ret - " + atomicTermReturnType+")\n");
				predicates_buffer.append(atomic_term_buffer);
			}
		}
		
		Vector formulae_vector = XMLParser.getFormulae();
		
		for(int i=0;i<formulae_vector.size();i++) {			
			
			StringBuffer formula_buffer = new StringBuffer();
				
			String formula_name = (String) formulae_vector.elementAt(i);
			
			formula_buffer.append("("+formula_name);
					
			Vector formula_parameters_vector = XMLParser.getInputParametersOfFormula(formula_name);
			
							for(int j=0;j<formula_parameters_vector.size();j++) {
					
								Vector formulaSingleParameter_vector = (Vector) formula_parameters_vector.elementAt(j);
				
								formula_buffer.append(" ?" + formulaSingleParameter_vector.firstElement());
								formula_buffer.append(" - " + formulaSingleParameter_vector.lastElement());							

								}
			
							formula_buffer.append(")\n");
							predicates_buffer.append(formula_buffer);
		}
		
		if(predicates_buffer.length()>0) {
			buffer.append("(predicates \n" + predicates_buffer + ")\n\n");
		}
		
		if(functions_buffer.length()>0) {
			buffer.append("(functions \n" + functions_buffer + ")\n\n");
		}
		
		//************************************************************************
		// Definition of PDDL actions: 
		//************************************************************************
		
		Vector task_names_vector = XMLParser.getTaskNames(false);
		
		for(int i=0;i<task_names_vector.size();i++) {
			
			StringBuffer task_buffer = new StringBuffer();
			
			String taskName = (String) task_names_vector.elementAt(i);
			
			task_buffer.append("(:action " + taskName + " \n");
			
			task_buffer.append(":parameters (?SRVC - service");
			
			
			Vector input_parameter_names_vector = XMLParser.getInputParameterNamesOfTask(taskName);
			Vector task_parameters_vector = XMLParser.getInputParametersOfTask(taskName,false);
			
			for(int j=0;j<task_parameters_vector.size();j++) {
				
				Vector single_parameter_vector = (Vector) task_parameters_vector.elementAt(j);
				
				if(input_parameter_names_vector.contains(single_parameter_vector.firstElement()))
					task_buffer.append(" ?" + single_parameter_vector.firstElement() + " - ");
				else
					task_buffer.append(" " + single_parameter_vector.firstElement() + " - ");
				
				task_buffer.append(" " + single_parameter_vector.lastElement());
			}
			task_buffer.append(")\n");
			
			task_buffer.append(createPDDLpreconditions(taskName,input_parameter_names_vector));
			task_buffer.append(createPDDLeffects(taskName,input_parameter_names_vector));
			task_buffer.append(")\n\n");
			
			buffer.append(task_buffer);
		}
		
		scriviFile("/home/and182/indigolog/Examples/aPMS/Planners/LPG-TD/domain.pddl", buffer);
		
	}

	private static void scriviFile(String nomeFile, StringBuffer buffer) {
		 
		  File file = null;
	      FileWriter fw = null;
		   
		   try {
			file = new File(nomeFile);
			fw = new FileWriter(file);
   		fw.write(buffer.toString());
   		fw.close();
   		
		   //fw.flush();
		   //fw.close();
		   }
		   catch(IOException e) {
		   e.printStackTrace();
		   }
		   }
	 
	 /**
	  * Method for creating the PDDL preconditions of a specific task 
	  */
	 private static StringBuffer createPDDLpreconditions(String taskName, Vector input_parameter_names_vector) {
		 
		StringBuffer task_buffer = new StringBuffer();
		    
		Vector task_capabilities_vector = XMLParser.getCapabilitiesRequiredByTask(taskName);		    
		Vector task_preconditions_vector = XMLParser.getTaskPreconditions(taskName);
		 
//******************************* 0.A - If at least one precondition is available (other than the predicate free)
//******************************* 
		if(task_capabilities_vector.size() + task_preconditions_vector.size() > 0) { 
		    	
			task_buffer.append(":precondition (and (free ?SRVC)");

			//// Insert the "provides" predicates to the task precondition //////////////////// 
			for(int j=0;j<task_capabilities_vector.size();j++) {				
				task_buffer.append(" (provides ?SRVC " + task_capabilities_vector.elementAt(j) + ")");
			}
		 
     		 //Add SRVC to the task input parameters --> SRVC is the process participant/service that will execute the task (whose real value is known only at run-time)
			 input_parameter_names_vector.addElement("SRVC");
			 	
			//******************************* 1 - Do this cycle for each explicit task precondition
			//******************************* 
			 for(int j=0;j<task_preconditions_vector.size();j++) {				
					
				 String single_precondition = (String) task_preconditions_vector.elementAt(j);
								 
				 //LOG----
				 //System.out.println(single_precondition);
				 
				//******************************* 1.A - Steps valid if the single task precondition contains a "==" expression
				//******************************* 
				 if(single_precondition.contains("==")) { //The atomic term could be an Integer_Type, a Boolean_Type or a Functional_Type
					 
					 boolean is_Boolean_type = false;
					 boolean is_True = false;
					 boolean is_Integer_type = false;
					 
					 String[] split_exp = single_precondition.split(" == ");
					 
					 String rightPartofTheExpression = split_exp[1];

					 if(rightPartofTheExpression.equalsIgnoreCase("true") || rightPartofTheExpression.equalsIgnoreCase("false")) {
						 is_Boolean_type = true;
						 if(rightPartofTheExpression.equalsIgnoreCase("true"))
							 is_True = true;
					 }
					 					 
					 String[] split_at_name = split_exp[0].split("\\[");
					 
					 String left_atomic_term = split_at_name[0];
					 
					 if(XMLParser.getAtomicTermReturnType(left_atomic_term,false).equalsIgnoreCase("Integer_type"))
						 is_Integer_type = true;
					 
					 //At this point in the code, I know if the atomic term situated in the left part of the expression is a Functional, Boolean or Integer atomic term.
				 
					 if(is_Boolean_type && !is_True)
						task_buffer.append(" (not (" + left_atomic_term);
					 else if (is_Integer_type)
						task_buffer.append(" (= (" + left_atomic_term); 
					 else
						task_buffer.append(" (" + left_atomic_term);
					 
					 String[] split_operators = split_at_name[1].split("\\]");
					 
					//******************************* 1.A.1 - the atomic term or formula used in the left part of the precondition has at least one input parameter
					//******************************* 
					 if(split_operators.length > 0) { 
					
						 String[] split_commas = split_operators[0].split("\\,");

						 if(split_commas.length>1) { //the atomic term or formula used in the left part of the precondition has more than one input parameter (e.g., neigh(loc1,loc2))
							 for(int h=0;h<split_commas.length;h++) { 
								 
								 String param = split_commas[h];
								 
								 if(input_parameter_names_vector.contains(param)) task_buffer.append(" ?"+param);
								 else task_buffer.append(" " + param);
								 
								 if(h==split_commas.length-1) {
									 if(!is_Boolean_type && !is_Integer_type) {
										 
										 if(input_parameter_names_vector.contains(rightPartofTheExpression)) 
											 task_buffer.append(" ?" + rightPartofTheExpression + ") ");
										 else
											 task_buffer.append(" " + rightPartofTheExpression + ") ");
									 }										 
									 else if(is_Boolean_type && !is_True)
										 task_buffer.append(")) ");
									 else if(is_Boolean_type && is_True)
										 task_buffer.append(") ");
									 else if (is_Integer_type) {
										 
										 task_buffer.append(") ");

										 if(rightPartofTheExpression.contains("[")) { //the right part of the expression contains an atomic term
											 
											 String[] split_right_part_at_name = rightPartofTheExpression.split("\\[");
											 task_buffer.append("(" + split_right_part_at_name[0]);
											 
											 String[] split_right_part_params = split_right_part_at_name[1].split("\\]");
											 
											 if(split_right_part_params.length > 0) { //at least one parameter in the atomic term situated in the right part of the expression
												 String[] split_right_part_param_commas = split_right_part_params[0].split("\\,");

												 if(split_right_part_param_commas.length>1) { 
													 for(int x=0;x<split_right_part_param_commas.length;x++) { 
														 
														String param_ret = split_right_part_param_commas[x];
														 
														 if(input_parameter_names_vector.contains(param_ret)) task_buffer.append(" ?"+param_ret);
														 else task_buffer.append(" " + param_ret);
														 
														 if(x==split_right_part_param_commas.length-1) task_buffer.append(")) ");
													 }
												 }
												 else {
													 String param_ret = split_right_part_params[0];
													 
													 if(input_parameter_names_vector.contains(param_ret)) task_buffer.append(" ?"+param_ret+ ")) ");
													 else task_buffer.append(" " + param_ret + ")) ");
												 }
											 }
											 
											 else { //empty parameters list in the atomic term situated in the right part of the expression
												task_buffer.append(")) ");
											 }
										 }
										 else { //the right part of the expression is a number (in the case of an Integer_Type)
											 task_buffer.append(" " + rightPartofTheExpression + ") ");
										 }
									 }
								 }
							 }
						 }
						 else { //the atomic term or formula used in the left part of the precondition has a unique input parameter (e.g., at(loc1))

							 if(input_parameter_names_vector.contains(split_operators[0]))
								 task_buffer.append(" ?" + split_operators[0]);
							 else
								 task_buffer.append(" " + split_operators[0]);
							 
							 if(!is_Boolean_type && !is_Integer_type) {
								 if(input_parameter_names_vector.contains(rightPartofTheExpression)) 
									 task_buffer.append(" ?" + rightPartofTheExpression + ") ");
								 else
									 task_buffer.append(" " + rightPartofTheExpression + ") ");
							 }										 
							 else if(is_Boolean_type && !is_True)
								 task_buffer.append(")) ");
							 else if(is_Boolean_type && is_True)
								 task_buffer.append(") ");			
							 else if (is_Integer_type) {
								 
								 task_buffer.append(") ");

								 if(rightPartofTheExpression.contains("[")) { //the right part of the expression contains an atomic term
									 
									 String[] split_right_part_at_name = rightPartofTheExpression.split("\\[");
									 task_buffer.append("(" + split_right_part_at_name[0]);
									 
									 String[] split_right_part_params = split_right_part_at_name[1].split("\\]");
									 
									 if(split_right_part_params.length > 0) { //at least one parameter in the atomic term situated in the right part of the expression
										 String[] split_right_part_param_commas = split_right_part_params[0].split("\\,");
										 
										 if(split_right_part_param_commas.length>1) { 
											 for(int h=0;h<split_right_part_param_commas.length;h++) { 
												 
												 String param = split_right_part_param_commas[h];
												 
												 if(input_parameter_names_vector.contains(param)) task_buffer.append(" ?"+param);
												 else task_buffer.append(" " + param);
												 
												 if(h==split_right_part_param_commas.length-1) task_buffer.append(")) ");
											 }
										 }
										 else {
											 String param = split_right_part_params[0];
											 
											 if(input_parameter_names_vector.contains(param)) task_buffer.append(" ?"+param+ ")) ");
											 else task_buffer.append(" " + param + ")) ");
										 }
									 }
									 
									 else { //empty parameters list in the atomic term situated in the right part of the expression
										task_buffer.append(")) ");
									 }
								 }
								 else { //the right part of the expression is a number (in the case of an Integer_Type)
									 task_buffer.append(" " + rightPartofTheExpression + ") ");
								 }
							 }
						 }
					 }
					//******************************* 1.A.2 - the atomic term or formula used in the left part of the precondition has no input parameters
					//******************************* 
					 else { 
						 
						 if(!is_Boolean_type && !is_Integer_type) {							 
							 
							 if(input_parameter_names_vector.contains(rightPartofTheExpression)) 
								 task_buffer.append(" ?" + rightPartofTheExpression + ") ");
							 else
								 task_buffer.append(" " + rightPartofTheExpression + ") ");
						 }										 
						 else if(is_Boolean_type && !is_True)
							 task_buffer.append(")) ");
						 else if(is_Boolean_type && is_True)
							 task_buffer.append(") ");
						 else if (is_Integer_type) {
							 
							 task_buffer.append(") ");
							
							 if(rightPartofTheExpression.contains("[")) { //the right part of the expression contains an atomic term
								 
								 String[] split_right_part_at_name = rightPartofTheExpression.split("\\[");
								 task_buffer.append("(" + split_right_part_at_name[0]);

								 String[] split_right_part_params = split_right_part_at_name[1].split("\\]");
								 
								 if(split_right_part_params.length > 0) { 
									 String[] split_right_part_param_commas = split_right_part_params[0].split("\\,");
									 
									 if(split_right_part_param_commas.length>1) { 
										 for(int h=0;h<split_right_part_param_commas.length;h++) { 
											 
											 String param = split_right_part_param_commas[h];
											 
											 if(input_parameter_names_vector.contains(param)) task_buffer.append(" ?"+param);
											 else task_buffer.append(" " + param);
											 
											 if(h==split_right_part_param_commas.length-1) task_buffer.append(")) ");
										 }
									 }
									 else {
										 String param = split_right_part_params[0];
										 
										 if(input_parameter_names_vector.contains(param)) task_buffer.append(" ?"+param+ ")) ");
										 else task_buffer.append(" " + param + ")) ");
									 }
								 }
								 
								 else {
									task_buffer.append(")) ");
								 }
							 }
							 else { //the right part of the expression is a number (in the case of an Integer_Type)
								 task_buffer.append(" " + rightPartofTheExpression + ") ");
							 }
						 }
					 }
				 }
				 
				 
				//******************************* 1.B - Steps valid if the single task precondition contains a "<>" expression
				//******************************* 
				 else if(single_precondition.contains("<>")) { //Valid only for Functional terms
					 
					 // Currently it is not supported
					 
				 }
				 
				//******************************* 1.C - Steps valid if the single task precondition contains a "<" or ">" expression
				//******************************* 
				 else if(single_precondition.contains(">") || single_precondition.contains("<")) { //Valid only for Integer terms
					 
					 String[] split_exp = null;
					 
					 if(single_precondition.contains(">=")) {
						 split_exp = single_precondition.split(" >= ");
						 task_buffer.append(" (>= (");
					 }
					 else if(single_precondition.contains("<=")) {
						 split_exp = single_precondition.split(" <= ");
						 task_buffer.append(" (<= ("); 
					 }
					 else if(single_precondition.contains(">")) {
						 split_exp = single_precondition.split(" > ");
						 task_buffer.append(" (> (");
					 }
					 else if(single_precondition.contains("<")) {
						 split_exp = single_precondition.split(" < ");
						 task_buffer.append(" (< (");
					 }
					 
					 String rightPartofTheExpression = split_exp[1];
					 
					 String[] split_at_name = split_exp[0].split("\\[");
					 
					 String left_atomic_term = split_at_name[0];
					 
                     task_buffer.append(left_atomic_term);
					 
                     String[] split_operators = split_at_name[1].split("\\]");
					 
 					//******************************* 1.C.1 - the Integer atomic term used in the left part of the precondition has at least one input parameter
 					//******************************* 
 					 if(split_operators.length > 0) { 
 					
 						 String[] split_commas = split_operators[0].split("\\,");

 						 if(split_commas.length>1) { //the Integer atomic term used in the left part of the precondition has more than one input parameter (e.g., neigh(loc1,loc2))
 							 for(int h=0;h<split_commas.length;h++) { 
 								 
 								 String param = split_commas[h];
 								 
 								 if(input_parameter_names_vector.contains(param)) task_buffer.append(" ?"+param);
 								 else task_buffer.append(" " + param);
 								 
 								 if(h==split_commas.length-1) {
 									 										 
 										 task_buffer.append(") ");

 										 if(rightPartofTheExpression.contains("[")) { //the right part of the expression contains an atomic term
 											 
 											 String[] split_right_part_at_name = rightPartofTheExpression.split("\\[");
 											 task_buffer.append("(" + split_right_part_at_name[0]);
 											 
 											 String[] split_right_part_params = split_right_part_at_name[1].split("\\]");
 											 
 											 if(split_right_part_params.length > 0) { //at least one parameter in the atomic term situated in the right part of the expression
 												 String[] split_right_part_param_commas = split_right_part_params[0].split("\\,");

 												 if(split_right_part_param_commas.length>1) { 
 													 for(int x=0;x<split_right_part_param_commas.length;x++) { 
 														 
 														String param_ret = split_right_part_param_commas[x];
 														 
 														 if(input_parameter_names_vector.contains(param_ret)) task_buffer.append(" ?"+param_ret);
 														 else task_buffer.append(" " + param_ret);
 														 
 														 if(x==split_right_part_param_commas.length-1) task_buffer.append(")) ");
 													 }
 												 }
 												 else {
 													 String param_ret = split_right_part_params[0];
 													 
 													 if(input_parameter_names_vector.contains(param_ret)) task_buffer.append(" ?"+param_ret+ ")) ");
 													 else task_buffer.append(" " + param_ret + ")) ");
 												 }
 											 }
 											 
 											 else { //empty parameters list in the atomic term situated in the right part of the expression
 												task_buffer.append(")) ");
 											 }
 										 }
 										 else { //the right part of the expression is a number
 											 task_buffer.append(" " + rightPartofTheExpression + ") ");
 										 }
 									 
 								 }
 							 }
 						 }
 						 else { //the Integer atomic term used in the left part of the precondition has a unique input parameter (e.g., batteryLevel(act1))

 							 if(input_parameter_names_vector.contains(split_operators[0]))
 								 task_buffer.append(" ?" + split_operators[0]);
 							 else
 								 task_buffer.append(" " + split_operators[0]);
 							 
 								 task_buffer.append(") ");

 								 if(rightPartofTheExpression.contains("[")) { //the right part of the expression contains an atomic term
 									 
 									 String[] split_right_part_at_name = rightPartofTheExpression.split("\\[");
 									 task_buffer.append("(" + split_right_part_at_name[0]);
 									 
 									 String[] split_right_part_params = split_right_part_at_name[1].split("\\]");
 									 
 									 if(split_right_part_params.length > 0) { //at least one parameter in the atomic term situated in the right part of the expression
 										 String[] split_right_part_param_commas = split_right_part_params[0].split("\\,");
 										 
 										 if(split_right_part_param_commas.length>1) { 
 											 for(int h=0;h<split_right_part_param_commas.length;h++) { 
 												 
 												 String param = split_right_part_param_commas[h];
 												 
 												 if(input_parameter_names_vector.contains(param)) task_buffer.append(" ?"+param);
 												 else task_buffer.append(" " + param);
 												 
 												 if(h==split_right_part_param_commas.length-1) task_buffer.append(")) ");
 											 }
 										 }
 										 else {
 											 String param = split_right_part_params[0];
 											 
 											 if(input_parameter_names_vector.contains(param)) task_buffer.append(" ?"+param+ ")) ");
 											 else task_buffer.append(" " + param + ")) ");
 										 }
 									 }
 									 
 									 else { //empty parameters list in the atomic term situated in the right part of the expression
 										task_buffer.append(")) ");
 									 }
 								 }
 								 else { //the right part of the expression is a number
 									 task_buffer.append(" " + rightPartofTheExpression + ") ");
 								 }
 							 
 						 }
 					 }
 					//******************************* 1.C.2 - the Integer atomic term used in the left part of the precondition has no input parameters
 					//******************************* 
 					 else { 
 						 
 							 task_buffer.append(") ");
 							
 							 if(rightPartofTheExpression.contains("[")) { //the right part of the expression contains an atomic term
 								 
 								 String[] split_right_part_at_name = rightPartofTheExpression.split("\\[");
 								 task_buffer.append("(" + split_right_part_at_name[0]);

 								 String[] split_right_part_params = split_right_part_at_name[1].split("\\]");
 								 
 								 if(split_right_part_params.length > 0) { 
 									 String[] split_right_part_param_commas = split_right_part_params[0].split("\\,");
 									 
 									 if(split_right_part_param_commas.length>1) { 
 										 for(int h=0;h<split_right_part_param_commas.length;h++) { 
 											 
 											 String param = split_right_part_param_commas[h];
 											 
 											 if(input_parameter_names_vector.contains(param)) task_buffer.append(" ?"+param);
 											 else task_buffer.append(" " + param);
 											 
 											 if(h==split_right_part_param_commas.length-1) task_buffer.append(")) ");
 										 }
 									 }
 									 else {
 										 String param = split_right_part_params[0];
 										 
 										 if(input_parameter_names_vector.contains(param)) task_buffer.append(" ?"+param+ ")) ");
 										 else task_buffer.append(" " + param + ")) ");
 									 }
 								 }
 								 
 								 else {
 									task_buffer.append(")) ");
 								 }
 							 }
 							 else { //the right part of the expression is a number (in the case of an Integer_Type)
 								 task_buffer.append(" " + rightPartofTheExpression + ") ");
 							 }
 						 
 					 }

				 }
				 
				}
			 
			 task_buffer.append(")\n"); //END of task preconditions
			 
		    }
		
//******************************* 0.B - There are no explicit preconditions for the task (other than the predicate free)
//******************************* 		
		else {
			
			task_buffer.append(":precondition (free ?SRVC)");
		
		}	 
			 
			 return task_buffer;
		   }
	 
	 /**
	  * Method for creating the PDDL effects of a specific task 
	  */
	 private StringBuffer createPDDLeffects(String taskName, Vector input_parameter_names_vector) {

		StringBuffer task_buffer = new StringBuffer();

		Vector task_preconditions_vector = XMLParser.getTaskPreconditions(taskName);
		Vector task_effects_vector = XMLParser.getTaskEffects(taskName);
		
		//Add SRVC to the task input parameters --> SRVC is the process participant/service that will execute the task (whose real value is known only at run-time)
		input_parameter_names_vector.addElement("SRVC");
		
		//******************************* 1 - Do this cycle for each task effect
		//******************************* 
		 for(int j=0;j<task_effects_vector.size();j++) {				
				
			 String single_effect = (String) task_effects_vector.elementAt(j);
							 
			 //System.out.println(single_effect);
			 
			//******************************* 1.A - Steps valid if the single task effect contains a "=" expression
			//******************************* 
			 if(single_effect.contains("=")  && !single_effect.contains("-=") && !single_effect.contains("+=")) { //The atomic term could be an Integer_Type, a Boolean_Type or a Functional_Type

				 boolean is_Boolean_type = false;
				 boolean is_True = false;
				 boolean is_Integer_type = false;
				 
				 String[] split_exp = single_effect.split(" = ");
				 
				 String rightPartofTheExpression = split_exp[1];

				 if(rightPartofTheExpression.equalsIgnoreCase("true") || rightPartofTheExpression.equalsIgnoreCase("false")) {
					 is_Boolean_type = true;
					 if(rightPartofTheExpression.equalsIgnoreCase("true"))
						 is_True = true;
				 }
				 					 
				 String[] split_at_name = split_exp[0].split("\\[");
				 
				 String left_atomic_term = split_at_name[0];
				 
				 if(XMLParser.getAtomicTermReturnType(left_atomic_term,false).equalsIgnoreCase("Integer_type"))
					 is_Integer_type = true;
				 
				 //At this point in the code, I know if the atomic term situated in the left part of the expression is a Functional, Boolean or Integer atomic term.
			 
				 if(is_Boolean_type && !is_True)
					task_buffer.append(" (not (" + left_atomic_term);
				 else if (is_Integer_type)
					task_buffer.append(" (assign (" + left_atomic_term); 
				 else
					task_buffer.append(" (" + left_atomic_term);
				 
				//********************************************************************************************************
				//********* Corrective actions for functional term effects ***********************************************
				//********************************************************************************************************
				if(!is_Boolean_type && !is_Integer_type) {
					 for(int u=0;u<task_preconditions_vector.size();u++) {
						 if(((String) task_preconditions_vector.elementAt(u)).contains(split_exp[0])) {
							
							 String[] pre_split = ((String)task_preconditions_vector.elementAt(u)).split(" == ");
								
							if(!pre_split[1].equalsIgnoreCase(split_exp[1])) { //if PRE: status(loc) == ok and EFF: status(loc) = ok .... no need of corrective actions

           				    String negative_effect = ((String) task_preconditions_vector.elementAt(u));
							String[] neg_eff = negative_effect.split(" == ");
							negative_effect = neg_eff[0].concat(" !! ").concat(neg_eff[1]);
							
							task_effects_vector.addElement(negative_effect);
						 }
						 }
					 }
				 }
				//*********************************************************
				 
				 String[] split_operators = split_at_name[1].split("\\]");
				 
				//******************************* 1.A.1 - the atomic term or formula used in the left part of the effect has at least one input parameter
				//******************************* 
					 if(split_operators.length > 0) { 
					
						 String[] split_commas = split_operators[0].split("\\,");

						 if(split_commas.length>1) { //the atomic term or formula used in the left part of the effect has more than one input parameter
							 for(int h=0;h<split_commas.length;h++) { 
								 
								 String param = split_commas[h];
								 
								 if(input_parameter_names_vector.contains(param)) task_buffer.append(" ?"+param);
								 else task_buffer.append(" " + param);
								 
								 if(h==split_commas.length-1) {
									 if(!is_Boolean_type && !is_Integer_type) {
										 
										 if(input_parameter_names_vector.contains(rightPartofTheExpression)) 
											 task_buffer.append(" ?" + rightPartofTheExpression + ") ");
										 else
											 task_buffer.append(" " + rightPartofTheExpression + ") ");
									 }										 
									 else if(is_Boolean_type && !is_True)
										 task_buffer.append(")) ");
									 else if(is_Boolean_type && is_True)
										 task_buffer.append(") ");
									 else if (is_Integer_type) {
										 
										 task_buffer.append(") ");

										 if(rightPartofTheExpression.contains("[")) { //the right part of the expression contains an atomic term
											 
											 String[] split_right_part_at_name = rightPartofTheExpression.split("\\[");
											 task_buffer.append("(" + split_right_part_at_name[0]);
											 
											 String[] split_right_part_params = split_right_part_at_name[1].split("\\]");
											 
											 if(split_right_part_params.length > 0) { //at least one parameter in the atomic term situated in the right part of the expression
												 String[] split_right_part_param_commas = split_right_part_params[0].split("\\,");

												 if(split_right_part_param_commas.length>1) { 
													 for(int x=0;x<split_right_part_param_commas.length;x++) { 
														 
														String param_ret = split_right_part_param_commas[x];
														 
														 if(input_parameter_names_vector.contains(param_ret)) task_buffer.append(" ?"+param_ret);
														 else task_buffer.append(" " + param_ret);
														 
														 if(x==split_right_part_param_commas.length-1) task_buffer.append(")) ");
													 }
												 }
												 else {
													 String param_ret = split_right_part_params[0];
													 
													 if(input_parameter_names_vector.contains(param_ret)) task_buffer.append(" ?"+param_ret+ ")) ");
													 else task_buffer.append(" " + param_ret + ")) ");
												 }
											 }
											 
											 else { //empty parameters list in the atomic term situated in the right part of the expression
												task_buffer.append(")) ");
											 }
										 }
										 else { //the right part of the expression is a number (in the case of an Integer_Type)
											 task_buffer.append(" " + rightPartofTheExpression + ") ");
										 }
									 }
								 }
							 }
						 }
						 else { //the atomic term or formula used in the left part of the precondition has a unique input parameter (e.g., at(loc1))

							 if(input_parameter_names_vector.contains(split_operators[0]))
								 task_buffer.append(" ?" + split_operators[0]);
							 else
								 task_buffer.append(" " + split_operators[0]);
							 
							 if(!is_Boolean_type && !is_Integer_type) {
								 if(input_parameter_names_vector.contains(rightPartofTheExpression)) 
									 task_buffer.append(" ?" + rightPartofTheExpression + ") ");
								 else
									 task_buffer.append(" " + rightPartofTheExpression + ") ");
							 }										 
							 else if(is_Boolean_type && !is_True)
								 task_buffer.append(")) ");
							 else if(is_Boolean_type && is_True)
								 task_buffer.append(") ");			
							 else if (is_Integer_type) {
								 
								 task_buffer.append(") ");

								 if(rightPartofTheExpression.contains("[")) { //the right part of the expression contains an atomic term
									 
									 String[] split_right_part_at_name = rightPartofTheExpression.split("\\[");
									 task_buffer.append("(" + split_right_part_at_name[0]);
									 
									 String[] split_right_part_params = split_right_part_at_name[1].split("\\]");
									 
									 if(split_right_part_params.length > 0) { //at least one parameter in the atomic term situated in the right part of the expression
										 String[] split_right_part_param_commas = split_right_part_params[0].split("\\,");
										 
										 if(split_right_part_param_commas.length>1) { 
											 for(int h=0;h<split_right_part_param_commas.length;h++) { 
												 
												 String param = split_right_part_param_commas[h];
												 
												 if(input_parameter_names_vector.contains(param)) task_buffer.append(" ?"+param);
												 else task_buffer.append(" " + param);
												 
												 if(h==split_right_part_param_commas.length-1) task_buffer.append(")) ");
											 }
										 }
										 else {
											 String param = split_right_part_params[0];
											 
											 if(input_parameter_names_vector.contains(param)) task_buffer.append(" ?"+param+ ")) ");
											 else task_buffer.append(" " + param + ")) ");
										 }
									 }
									 
									 else { //empty parameters list in the atomic term situated in the right part of the expression
										task_buffer.append(")) ");
									 }
								 }
								 else { //the right part of the expression is a number (in the case of an Integer_Type)
									 task_buffer.append(" " + rightPartofTheExpression + ") ");
								 }
							 }
						 }
					 }
					//******************************* 1.A.2 - the atomic term or formula used in the left part of the effect has no input parameters
					//******************************* 
						 else { 
							 
							 if(!is_Boolean_type && !is_Integer_type) {							 
								 
								 if(input_parameter_names_vector.contains(rightPartofTheExpression)) 
									 task_buffer.append(" ?" + rightPartofTheExpression + ") ");
								 else
									 task_buffer.append(" " + rightPartofTheExpression + ") ");
							 }										 
							 else if(is_Boolean_type && !is_True)
								 task_buffer.append(")) ");
							 else if(is_Boolean_type && is_True)
								 task_buffer.append(") ");
							 else if (is_Integer_type) {
								 
								 task_buffer.append(") ");
								
								 if(rightPartofTheExpression.contains("[")) { //the right part of the expression contains an atomic term
									 
									 String[] split_right_part_at_name = rightPartofTheExpression.split("\\[");
									 task_buffer.append("(" + split_right_part_at_name[0]);

									 String[] split_right_part_params = split_right_part_at_name[1].split("\\]");
									 
									 if(split_right_part_params.length > 0) { 
										 String[] split_right_part_param_commas = split_right_part_params[0].split("\\,");
										 
										 if(split_right_part_param_commas.length>1) { 
											 for(int h=0;h<split_right_part_param_commas.length;h++) { 
												 
												 String param = split_right_part_param_commas[h];
												 
												 if(input_parameter_names_vector.contains(param)) task_buffer.append(" ?"+param);
												 else task_buffer.append(" " + param);
												 
												 if(h==split_right_part_param_commas.length-1) task_buffer.append(")) ");
											 }
										 }
										 else {
											 String param = split_right_part_params[0];
											 
											 if(input_parameter_names_vector.contains(param)) task_buffer.append(" ?"+param+ ")) ");
											 else task_buffer.append(" " + param + ")) ");
										 }
									 }
									 
									 else {
										task_buffer.append(")) ");
									 }
								 }
								 else { //the right part of the expression is a number (in the case of an Integer_Type)
									 task_buffer.append(" " + rightPartofTheExpression + ") ");
								 }
							 }
						 }
			 }
			 
			//******************************* 1.B - Steps valid if the single task precondition contains a "-=" or "+=" expression
			//******************************* 
				 else if(single_effect.contains("-=") || single_effect.contains("+=")) { //Valid only for Integer terms
					 
					 String[] split_exp = null;
					 
					 if(single_effect.contains("+=")) {
						 split_exp = single_effect.split(" \\+= ");
						 task_buffer.append(" (increase (");
					 }
					 else if(single_effect.contains("-=")) {
						 split_exp = single_effect.split(" -= ");
						 task_buffer.append(" (decrease ("); 
					 }
					 
					 String rightPartofTheExpression = split_exp[1];
					 
					 String[] split_at_name = split_exp[0].split("\\[");
					 
					 String left_atomic_term = split_at_name[0];
					 
                  task_buffer.append(left_atomic_term);
					 
                  String[] split_operators = split_at_name[1].split("\\]");
					 
					//******************************* 1.B.1 - the Integer atomic term used in the left part of the effect has at least one input parameter
					//******************************* 
					 if(split_operators.length > 0) { 
					
						 String[] split_commas = split_operators[0].split("\\,");

						 if(split_commas.length>1) { //the Integer atomic term used in the left part of the precondition has more than one input parameter (e.g., neigh(loc1,loc2))
							 for(int h=0;h<split_commas.length;h++) { 
								 
								 String param = split_commas[h];
								 
								 if(input_parameter_names_vector.contains(param)) task_buffer.append(" ?"+param);
								 else task_buffer.append(" " + param);
								 
								 if(h==split_commas.length-1) {
									 										 
										 task_buffer.append(") ");

										 if(rightPartofTheExpression.contains("[")) { //the right part of the expression contains an atomic term
											 
											 String[] split_right_part_at_name = rightPartofTheExpression.split("\\[");
											 task_buffer.append("(" + split_right_part_at_name[0]);
											 
											 String[] split_right_part_params = split_right_part_at_name[1].split("\\]");
											 
											 if(split_right_part_params.length > 0) { //at least one parameter in the atomic term situated in the right part of the expression
												 String[] split_right_part_param_commas = split_right_part_params[0].split("\\,");

												 if(split_right_part_param_commas.length>1) { 
													 for(int x=0;x<split_right_part_param_commas.length;x++) { 
														 
														String param_ret = split_right_part_param_commas[x];
														 
														 if(input_parameter_names_vector.contains(param_ret)) task_buffer.append(" ?"+param_ret);
														 else task_buffer.append(" " + param_ret);
														 
														 if(x==split_right_part_param_commas.length-1) task_buffer.append(")) ");
													 }
												 }
												 else {
													 String param_ret = split_right_part_params[0];
													 
													 if(input_parameter_names_vector.contains(param_ret)) task_buffer.append(" ?"+param_ret+ ")) ");
													 else task_buffer.append(" " + param_ret + ")) ");
												 }
											 }
											 
											 else { //empty parameters list in the atomic term situated in the right part of the expression
												task_buffer.append(")) ");
											 }
										 }
										 else { //the right part of the expression is a number
											 task_buffer.append(" " + rightPartofTheExpression + ") ");
										 }
									 
								 }
							 }
						 }
						 else { //the Integer atomic term used in the left part of the precondition has a unique input parameter (e.g., batteryLevel(act1))

							 if(input_parameter_names_vector.contains(split_operators[0]))
								 task_buffer.append(" ?" + split_operators[0]);
							 else
								 task_buffer.append(" " + split_operators[0]);
							 
								 task_buffer.append(") ");

								 if(rightPartofTheExpression.contains("[")) { //the right part of the expression contains an atomic term
									 
									 String[] split_right_part_at_name = rightPartofTheExpression.split("\\[");
									 task_buffer.append("(" + split_right_part_at_name[0]);
									 
									 String[] split_right_part_params = split_right_part_at_name[1].split("\\]");
									 
									 if(split_right_part_params.length > 0) { //at least one parameter in the atomic term situated in the right part of the expression
										 String[] split_right_part_param_commas = split_right_part_params[0].split("\\,");
										 
										 if(split_right_part_param_commas.length>1) { 
											 for(int h=0;h<split_right_part_param_commas.length;h++) { 
												 
												 String param = split_right_part_param_commas[h];
												 
												 if(input_parameter_names_vector.contains(param)) task_buffer.append(" ?"+param);
												 else task_buffer.append(" " + param);
												 
												 if(h==split_right_part_param_commas.length-1) task_buffer.append(")) ");
											 }
										 }
										 else {
											 String param = split_right_part_params[0];
											 
											 if(input_parameter_names_vector.contains(param)) task_buffer.append(" ?"+param+ ")) ");
											 else task_buffer.append(" " + param + ")) ");
										 }
									 }
									 
									 else { //empty parameters list in the atomic term situated in the right part of the expression
										task_buffer.append(")) ");
									 }
								 }
								 else { //the right part of the expression is a number
									 task_buffer.append(" " + rightPartofTheExpression + ") ");
								 }
							 
						 }
					 }
					//******************************* 1.B.2 - the Integer atomic term used in the left part of the effect has no input parameters
					//******************************* 
					 else { 
						 
							 task_buffer.append(") ");
							
							 if(rightPartofTheExpression.contains("[")) { //the right part of the expression contains an atomic term
								 
								 String[] split_right_part_at_name = rightPartofTheExpression.split("\\[");
								 task_buffer.append("(" + split_right_part_at_name[0]);

								 String[] split_right_part_params = split_right_part_at_name[1].split("\\]");
								 
								 if(split_right_part_params.length > 0) { 
									 String[] split_right_part_param_commas = split_right_part_params[0].split("\\,");
									 
									 if(split_right_part_param_commas.length>1) { 
										 for(int h=0;h<split_right_part_param_commas.length;h++) { 
											 
											 String param = split_right_part_param_commas[h];
											 
											 if(input_parameter_names_vector.contains(param)) task_buffer.append(" ?"+param);
											 else task_buffer.append(" " + param);
											 
											 if(h==split_right_part_param_commas.length-1) task_buffer.append(")) ");
										 }
									 }
									 else {
										 String param = split_right_part_params[0];
										 
										 if(input_parameter_names_vector.contains(param)) task_buffer.append(" ?"+param+ ")) ");
										 else task_buffer.append(" " + param + ")) ");
									 }
								 }
								 
								 else {
									task_buffer.append(")) ");
								 }
							 }
							 else { //the right part of the expression is a number (in the case of an Integer_Type)
								 task_buffer.append(" " + rightPartofTheExpression + ") ");
							 }
						 
					 }

				 }
				//******************************* 1.C - //Special case - valid only for Functional Terms and corrective actions
				//******************************* 
				 else if(single_effect.contains("!!"))  { 
					 String[] split_exp = single_effect.split(" !! ");
					 
					 String rightPartofTheExpression = split_exp[1];

					 String[] split_at_name = split_exp[0].split("\\[");
					 
					 String left_atomic_term = split_at_name[0];
                     
					 task_buffer.append(" (not (" + left_atomic_term);

					//*********************************************************
					 
					 String[] split_operators = split_at_name[1].split("\\]");
					 
					//******************************* 1.C.1 - the atomic term used in the left part of the effect has at least one input parameter
					//******************************* 
						 if(split_operators.length > 0) { 
						
							 String[] split_commas = split_operators[0].split("\\,");

							 if(split_commas.length>1) { //the atomic term or formula used in the left part of the effect has more than one input parameter
								 for(int h=0;h<split_commas.length;h++) { 
									 
									 String param = split_commas[h];
									 
									 if(input_parameter_names_vector.contains(param)) task_buffer.append(" ?"+param);
									 else task_buffer.append(" " + param);
									 
									 if(h==split_commas.length-1) {
									 
											 if(input_parameter_names_vector.contains(rightPartofTheExpression)) 
												 task_buffer.append(" ?" + rightPartofTheExpression + ")) ");
											 else
												 task_buffer.append(" " + rightPartofTheExpression + ")) ");
									 }
								 }
							 }
							 else { //the atomic term or formula used in the left part of the precondition has a unique input parameter (e.g., at(loc1))

								 if(input_parameter_names_vector.contains(split_operators[0]))
									 task_buffer.append(" ?" + split_operators[0]);
								 else
									 task_buffer.append(" " + split_operators[0]);

									 if(input_parameter_names_vector.contains(rightPartofTheExpression)) 
										 task_buffer.append(" ?" + rightPartofTheExpression + ")) ");
									 else
										 task_buffer.append(" " + rightPartofTheExpression + ")) ");
							 }
						 }
						//******************************* 1.C.2 - the atomic term used in the left part of the effect has no input parameters
						//******************************* 
								 else { 
										 
										 if(input_parameter_names_vector.contains(rightPartofTheExpression)) 
											 task_buffer.append(" ?" + rightPartofTheExpression + ")) ");
										 else
											 task_buffer.append(" " + rightPartofTheExpression + ")) ");
								 }
				 }
		}	 
		
		 
		StringBuffer complete_buffer = new StringBuffer();
		
		if(task_effects_vector.size() > 1) { 
			
			complete_buffer.append(":effect (and");
			complete_buffer.append(task_buffer);
			complete_buffer.append(")\n"); 
			
		}
		else if(task_effects_vector.size() == 1){
			
			complete_buffer.append(":effect");
			complete_buffer.append(task_buffer);
			complete_buffer.append("\n"); 

		}
		 
		return complete_buffer;
		
		}

}


