%%%%%%%%%%%%%%%%%%%% 
% FILE: aPMS.pl
% DESCRIPTION : The latest version of SmartPM, the Indigolog adaptive Process Management System
% VERSION : 0.2
% FIXED BUGS : Workitems work with multiple inputs/outputs - Exogenous events are correctly supported
% KNOWN PROBLEMS : Nested Adaptation does not work - Priorities are currently not managed - If a running task takes as input or 
%		   as (supposed) expected output an integer fluent (not directly a number), the program could not work properly.
% PROPERTIES : 	   COSTANTS must be represented in the uppercase format.
%		   Non-quantified variables (for example, when used with the 'some' command) must be represented in the lowercase format.
%%%%%%%%%%%%%%%%%%%% 
/* FIXED */
:- dynamic controller/1.

cache(_):-fail. 

causes_true(_,_,_) :- false. 
causes_false(_,_,_) :- false. 

actionNum(X,X). 

/* MACROS */
square(X,Y) :- Y is X * X. 

member(ELEM,[HEAD|_]) :- ELEM=HEAD. 
member(ELEM,[_|TAIL]) :- member(ELEM,TAIL). 

listEqual(L1,L2) :- subset(L1,L2),subset(L2,L1). 

/* RESOURCES PERSPECTIVE */

/* Defined by the PROCESS DESIGNER */

actor(A) :- domain(A,[act1,act2,act3,act4]).
robot(R) :- domain(R,[rb1,rb2]).

service(S) :- actor(S).
service(S) :- robot(S).

capability(B) :- domain(B,[movement,hatchet,camera,gprs,extinguisher,digger,powerpack]).

provides(act1,movement).
provides(act1,gprs).
provides(act1,extinguisher). 
provides(act1,camera).
provides(act2,movement).
provides(act2,gprs).  
provides(act2,hatchet). 
provides(act3,movement).
provides(act3,gprs). 
provides(act3,hatchet). 
provides(act4,movement).
provides(act4,powerpack). 
provides(rb1,battery). 
provides(rb1,digger).
provides(rb2,battery).
provides(rb2,digger).

/* Pre-defined DATA TYPES */

boolean_type(Q) :- domain(Q,[true,false]).

integer_type(N) :- domain(N,[0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30]).

/* USER-DEFINED DATA TYPES */

status_type(S) :- domain(S,[ok,fire,debris]).

location_type(L) :- domain(L,[loc00,loc01,loc02,loc03,loc10,loc11,loc13,loc20,loc23,loc30,loc31,loc32,loc33]).

/* TASKS PERSPECTIVE */

task(T) :- domain(T,[chargebattery,go,move,evacuate,removedebris,takephoto,updatestatus,extinguishfire]).

task_identifiers([id_1,id_2,id_3,id_4,id_5,id_6,id_7,id_8,id_9,id_10,id_11,id_12,id_13,id_14,id_15,id_16,id_17,id_18,id_19,id_20,id_21,
id_22,id_23,id_24,id_25,id_26,id_27,id_28,id_29,id_30,id_31,id_32,id_33,id_34,id_35,id_36,id_37,id_38,id_39,id_40,id_41,id_42,id_43,
id_44,id_45,id_46,id_47,id_48,id_49,id_50,id_51,id_52,id_53,id_54,id_55,id_56,id_57,id_58,id_59,id_60,id_61,id_62,id_63,id_64,id_65,
id_66,id_67,id_68,id_69,id_70,id_71,id_72,id_73,id_74,id_75,id_76,id_77,id_78,id_79,id_80,id_81,id_82,id_83,id_84,id_85,id_86,id_87,
id_88,id_89,id_90,id_91,id_92,id_93,id_94,id_95,id_96,id_97,id_98,id_99,id_100,id_adapt]).
id(D) :- domain(D,task_identifiers).

requires(go,movement). 
requires(move,battery). 
requires(evacuate,hatchet). 
requires(takephoto,camera). 
requires(updatestatus,gprs). 
requires(extinguishfire,extinguisher). 
requires(removedebris,digger). 
requires(chargebattery,powerpack).
 
listelem(workitem(go,ID,[FROM,TO],[TO])) :- id(ID),location_type(FROM),location_type(TO).
listelem(workitem(takephoto,ID,[LOC],[OUT])) :- id(ID),location_type(LOC),boolean_type(OUT).
listelem(workitem(evacuate,ID,[LOC],[OUT])) :- id(ID),location_type(LOC),boolean_type(OUT).
listelem(workitem(move,ID,[FROM,TO],[TO])) :- id(ID),location_type(FROM),location_type(TO).
listelem(workitem(chargebattery,ID,[RB,LOC],[])) :- id(ID),robot(RB),location_type(LOC).
listelem(workitem(removedebris,ID,[LOC],[ST])) :- id(ID),location_type(LOC),status_type(ST).
listelem(workitem(extinguishfire,ID,[LOC],[ST])) :- id(ID),location_type(LOC),status_type(ST). 
listelem(workitem(updatestatus,ID,[LOC],[ST])) :- id(ID),location_type(LOC),status_type(ST).

worklist([]). 
worklist([ELEM | TAIL]) :- worklist(TAIL),listelem(ELEM). 

rec_worklist([]).
rec_worklist([ELEM | TAIL]) :- rec_worklist(TAIL),rec_listelem(ELEM). 
rec_listelem(recoveryTask(TASK,SRVC,_INPUTS)) :- task(TASK),service(SRVC). 

/* TASKS PRECONDITIONS */

prim_action(assign(SRVC,LWRK)) :- worklist(LWRK),service(SRVC).

poss(assign(SRVC,[workitem(go,ID,[FROM,TO],[TO])]),and(isconnected(SRVC),at(SRVC)=FROM)) :- 
actor(SRVC),id(ID),location_type(FROM),location_type(TO).

poss(assign(SRVC,[workitem(takephoto,ID,[LOC],[true])]),and(isconnected(SRVC),and(phototaken(LOC)=false,at(SRVC)=LOC))) :- 
service(SRVC),id(ID),location_type(LOC),boolean_type(true).

poss(assign(SRVC,[workitem(evacuate,ID,[LOC],[true])]),and(isconnected(SRVC),and(at(SRVC)=LOC,and(evacuated(LOC)=false,status(LOC)=ok)))) :- 
service(SRVC),id(ID),location_type(LOC),boolean_type(true).

poss(assign(SRVC,[workitem(move,ID,[FROM,TO],[TO])]),and(at(SRVC)=FROM,batterylevel(SRVC)>=movestep)) :-
robot(SRVC),id(ID),location_type(FROM),location_type(TO).

poss(assign(SRVC,[workitem(chargebattery,ID,[RB,LOC],[])]),and(isconnected(SRVC),(and(at(SRVC)=LOC,(and(at(RB)=LOC,generalbattery>=batteryrecharging)))))) :-
actor(SRVC),id(ID),robot(RB),location_type(LOC).

poss(assign(SRVC,[workitem(removedebris,ID,[LOC],[ST])]), and(at(SRVC)=LOC,and(status(LOC)=debris,batterylevel(SRVC)>=debrisstep))) :- 
robot(SRVC),id(ID),location_type(LOC),status_type(ST).

poss(assign(SRVC,[workitem(extinguishfire,ID,[LOC],[ST])]), and(isconnected(SRVC),and(at(SRVC)=LOC,status(LOC)=fire))) :-
service(SRVC),id(ID),location_type(LOC),status_type(ST).

poss(assign(SRVC,[workitem(updatestatus,ID,[LOC],[ST])]),and(isconnected(SRVC),and(at(SRVC)=LOC,status(LOC)=ok))) :-
service(SRVC),id(ID),location_type(LOC),status_type(ST).

/* TASK LIFE-CYCLE */

rel_fluent(assigned(SRVC,_LWRK)) :- service(SRVC). 
causes_val(assign(SRVC,LWRK),assigned(SRVC,LWRK),true,true). 
causes_val(release(SRVC,LWRK),assigned(SRVC,LWRK),false,true).
 
rel_fluent(reserved(SRVC,ID,TASK)) :- task(TASK), service(SRVC), id(ID). 
causes_val(readyToStart(SRVC,ID,TASK),reserved(SRVC,ID,TASK),true,true). 
causes_val(finishedTask(SRVC,ID,TASK,_),reserved(SRVC,ID,TASK),false,true).

prim_action(start(SRVC,ID,TASK,INPUT,EXOUTPUT)) :- listelem(workitem(TASK,ID,INPUT,EXOUTPUT)), service(SRVC). 
poss(start(SRVC,ID,TASK,_INPUT,_EXOUTPUT),reserved(SRVC,ID,TASK)) :- service(SRVC), id(ID), task(TASK). 

prim_action(ackCompl(SRVC,ID,TASK)) :- task(TASK), service(SRVC), id(ID). 
poss(ackCompl(SRVC,ID,TASK),neg(reserved(SRVC,ID,TASK))). 

prim_action(release(SRVC,LWRK)) :- worklist(LWRK),service(SRVC). 
poss(release(_SRVC,_LWRK), true). 

rel_fluent(free(SRVC)) :- service(SRVC). 
causes_val(release(SRVC,_LWRK),free(SRVC),true,true). 
causes_val(assign(SRVC,_LWRK),free(SRVC),false,true). 

rel_fluent(built_in_adaptation). 

/*USER-DEFINED STATIC FLUENTS*/
fun_fluent(neigh(LOC1,LOC2)) :- location_type(LOC1),location_type(LOC2).
fun_fluent(covered(LOC)) :- location_type(LOC).
fun_fluent(batteryrecharging).
fun_fluent(movestep).
fun_fluent(debrisstep).

/*FLUENT AT*/
fun_fluent(at(SRVC)) :- service(SRVC). 

causes_val(finishedTask(SRVC,ID,go,[OUTPUT]),at(SRVC),OUTPUT,assigned(SRVC,[workitem(go,ID,[FROM,TO],[TO])])) :-
	actor(SRVC),id(ID),location_type(OUTPUT),location_type(FROM),location_type(TO).

causes_val(finishedTask(SRVC,ID,move,[OUTPUT]),at(SRVC),OUTPUT,assigned(SRVC,[workitem(move,ID,[FROM,TO],[TO])])) :-
        robot(SRVC),id(ID),location_type(OUTPUT),location_type(FROM),location_type(TO).

fun_fluent(at_exp(SRVC)) :- service(SRVC).

causes_val(finishedTask(SRVC,ID,go,[OUTPUT]),at_exp(SRVC),TO,and(neg(adapting),assigned(SRVC,[workitem(go,ID,[FROM,TO],[TO])]))) :- 
	actor(SRVC),id(ID),location_type(OUTPUT),location_type(FROM),location_type(TO).

/*FLUENT EVACUATED*/
fun_fluent(evacuated(P)) :- location_type(P). 

causes_val(finishedTask(SRVC,ID,evacuate,[OUTPUT]),evacuated(LOC),OUTPUT,
   assigned(SRVC,[workitem(evacuate,ID,[LOC],[true])])) :-
        service(SRVC),id(ID),location_type(LOC),boolean_type(true),boolean_type(OUTPUT).

fun_fluent(evacuated_exp(P)) :- location_type(P).

causes_val(finishedTask(SRVC,ID,evacuate,[OUTPUT]),evacuated_exp(LOC),true,
   and(neg(adapting),assigned(SRVC,[workitem(evacuate,ID,[LOC],[true])]))) :-
        service(SRVC),id(ID),location_type(LOC),boolean_type(true),boolean_type(OUTPUT).

/*FLUENT STATUS*/
fun_fluent(status(P)) :- location_type(P).

causes_val(finishedTask(SRVC,ID,extinguishfire,[OUTPUT]),status(LOC),OUTPUT,assigned(SRVC,[workitem(extinguishfire,ID,[LOC],[ok])])) :-
location_type(LOC),status_type(ok),status_type(OUTPUT).

causes_val(finishedTask(SRVC,ID,removedebris,[OUTPUT]),status(LOC),OUTPUT,assigned(SRVC,[workitem(removedebris,ID,[LOC],[ok])])) :- 
location_type(LOC),status_type(ok),status_type(OUTPUT).

causes_val(finishedTask(SRVC,ID,updatestatus,[OUTPUT]),status(LOC),OUTPUT,assigned(SRVC,[workitem(updatestatus,ID,[LOC],[ok])])) :-
location_type(LOC),status_type(ok),status_type(OUTPUT).

fun_fluent(status_exp(P)) :- location_type(P).

causes_val(finishedTask(SRVC,ID,extinguishfire,[OUTPUT]),status_exp(LOC),ok,
    and(assigned(SRVC,[workitem(extinguishfire,ID,[LOC],[ok])]),neg(adapting))) :-
        location_type(LOC),status_type(ok),status_type(OUTPUT).

causes_val(finishedTask(SRVC,ID,removedebris,[OUTPUT]),status_exp(LOC),ok,
    and(assigned(SRVC,[workitem(removedebris,ID,[LOC],[ok])]),neg(adapting))) :-
        location_type(LOC),status_type(ok),status_type(OUTPUT).

causes_val(finishedTask(SRVC,ID,updatestatus,[OUTPUT]),status_exp(LOC),ok,
    and(assigned(SRVC,[workitem(updatestatus,ID,[LOC],[ok])]),neg(adapting))) :-
	location_type(LOC),status_type(ok),status_type(OUTPUT).

/*FLUENT BATTERYLEVEL*/
fun_fluent(batterylevel(SRVC)) :- service(SRVC).

causes_val(finishedTask(SRVC,ID,move,[OUTPUT]),batterylevel(SRVC),L,and(assigned(SRVC,[workitem(move,ID,[FROM,TO],[TO])]),
L is batterylevel(SRVC)-movestep)) :-
        robot(SRVC),location_type(OUTPUT),location_type(FROM),location_type(TO).

causes_val(finishedTask(SRVC,ID,removedebris,[OUTPUT]),batterylevel(SRVC),L,
    and(assigned(SRVC,[workitem(removedebris,ID,[LOC],[ok])]),L is batterylevel(SRVC)-debrisstep)) :-
        robot(SRVC),location_type(LOC),status_type(ok),status_type(OUTPUT).

causes_val(finishedTask(SRVC,ID,chargebattery,[]),batterylevel(RB),L, 
    and(assigned(SRVC,[workitem(chargebattery,ID,[RB,LOC],[])]),L is batterylevel(RB)+batteryrecharging)) :-
        actor(SRVC),robot(RB),location_type(LOC).

/*FLUENT PHOTO TAKEN*/
fun_fluent(phototaken(P)) :- location_type(P).

causes_val(finishedTask(SRVC,ID,takephoto,[OUTPUT]),phototaken(LOC),OUTPUT,
  	assigned(SRVC,[workitem(takephoto,ID,[LOC],[true])])) :-
        	service(SRVC),id(ID),location_type(LOC),boolean_type(true),boolean_type(OUTPUT).

fun_fluent(phototaken_exp(P)) :- location_type(P).

causes_val(finishedTask(SRVC,ID,takephoto,[OUTPUT]),phototaken_exp(LOC),true,
  	and(neg(adapting),assigned(SRVC,[workitem(takephoto,ID,[LOC],[true])]))) :-
        	service(SRVC),id(ID),location_type(LOC),boolean_type(true),boolean_type(OUTPUT).

/*FLUENT GENERAL BATTERY*/
fun_fluent(generalbattery).

causes_val(finishedTask(SRVC,ID,chargebattery,[]),generalbattery,L,
	and(assigned(SRVC,[workitem(chargebattery,ID,[RB,LOC],[])]),L is generalbattery-batteryrecharging)) :-
        	actor(SRVC),id(ID),robot(RB),location_type(LOC).

/*ABBREVIATION ISCONNECTED*/
proc(isconnected(SRVC),
	and(actor(SRVC),	
	or(covered(at(SRVC)),
	some(rb,and(robot(rb),or(neigh(at(SRVC),at(rb)),at(SRVC)=at(rb)))))
	)).

/* INITIAL STATE */

initially(reserved(SRVC,ID,TASK),false) :- task(TASK), service(SRVC), id(ID).
initially(assigned(SRVC,_LWRK),false) :- service(SRVC).
initially(finished,false).
initially(adapting,false).
initially(built_in_adaptation,false).

initially(free(act1),true).
initially(free(act2),true).
initially(free(act3),true).
initially(free(act4),true).
initially(free(rb1),true).
initially(free(rb2),true).

initially(batteryrecharging,10).
initially(movestep,2).
initially(debrisstep,3).
initially(generalbattery,30).

initially(at(act1),loc00).
initially(at_exp(act1),loc00).
initially(at(act2),loc00).
initially(at_exp(act2),loc00).
initially(at(act3),loc00).
initially(at_exp(act3),loc00).
initially(at(act4),loc00).
initially(at_exp(act4),loc00).

initially(at(rb1),loc00).
initially(at(rb2),loc00).

initially(evacuated(loc00),false).
initially(evacuated_exp(loc00),false).
initially(evacuated(loc01),false).
initially(evacuated_exp(loc01),false).
initially(evacuated(loc02),false).
initially(evacuated_exp(loc02),false).
initially(evacuated(loc03),false).
initially(evacuated_exp(loc03),false).
initially(evacuated(loc10),false).
initially(evacuated_exp(loc10),false).
initially(evacuated(loc11),false).
initially(evacuated_exp(loc11),false).
initially(evacuated(loc13),false).
initially(evacuated_exp(loc13),false).
initially(evacuated(loc20),false).
initially(evacuated_exp(loc20),false).
initially(evacuated(loc23),false).
initially(evacuated_exp(loc23),false).
initially(evacuated(loc30),false).
initially(evacuated_exp(loc30),false).
initially(evacuated(loc31),false).
initially(evacuated_exp(loc31),false).
initially(evacuated(loc32),false).
initially(evacuated_exp(loc32),false).
initially(evacuated(loc33),false).
initially(evacuated_exp(loc33),false).

initially(phototaken(loc00),false).
initially(phototaken_exp(loc00),false).
initially(phototaken(loc01),false).
initially(phototaken_exp(loc01),false).
initially(phototaken(loc02),false).
initially(phototaken_exp(loc02),false).
initially(phototaken(loc03),false).
initially(phototaken_exp(loc03),false).
initially(phototaken(loc10),false).
initially(phototaken_exp(loc10),false).
initially(phototaken(loc11),false).
initially(phototaken_exp(loc11),false).
initially(phototaken(loc13),false).
initially(phototaken_exp(loc13),false).
initially(phototaken(loc20),false).
initially(phototaken_exp(loc20),false).
initially(phototaken(loc23),false).
initially(phototaken_exp(loc23),false).
initially(phototaken(loc30),false).
initially(phototaken_exp(loc30),false).
initially(phototaken(loc31),false).
initially(phototaken_exp(loc31),false).
initially(phototaken(loc32),false).
initially(phototaken_exp(loc32),false).
initially(phototaken(loc33),false).
initially(phototaken_exp(loc33),false).

initially(status(loc00),ok).
initially(status_exp(loc00),ok).
initially(status(loc01),ok).
initially(status_exp(loc01),ok).
initially(status(loc02),ok).
initially(status_exp(loc02),ok).
initially(status(loc03),ok).
initially(status_exp(loc03),ok).
initially(status(loc10),ok).
initially(status_exp(loc10),ok).
initially(status(loc11),ok).
initially(status_exp(loc11),ok).
initially(status(loc13),ok).
initially(status_exp(loc13),ok).
initially(status(loc20),ok).
initially(status_exp(loc20),ok).
initially(status(loc23),ok).
initially(status_exp(loc23),ok).
initially(status(loc30),ok).
initially(status_exp(loc30),ok).
initially(status(loc31),ok).
initially(status_exp(loc31),ok).
initially(status(loc32),ok).
initially(status_exp(loc32),ok).
initially(status(loc33),ok).
initially(status_exp(loc33),ok).

initially(batterylevel(act1),0).
initially(batterylevel(act2),0).
initially(batterylevel(act3),0).
initially(batterylevel(act4),0).
initially(batterylevel(rb1),3).
initially(batterylevel(rb2),3).

initially(covered(loc00),true).
initially(covered(loc10),true).
initially(covered(loc20),true).
initially(covered(loc11),true).
initially(covered(loc01),true).
initially(covered(loc02),true).

initially(neigh(loc00,loc10),true).
initially(neigh(loc00,loc11),true).
initially(neigh(loc00,loc01),true).
initially(neigh(loc11,loc10),true).
initially(neigh(loc11,loc01),true).
initially(neigh(loc11,loc00),true).
initially(neigh(loc11,loc20),true).
initially(neigh(loc11,loc02),true).
initially(neigh(loc10,loc20),true).
initially(neigh(loc10,loc00),true).
initially(neigh(loc10,loc11),true).
initially(neigh(loc10,loc01),true).
initially(neigh(loc01,loc02),true).
initially(neigh(loc01,loc11),true).
initially(neigh(loc01,loc10),true).
initially(neigh(loc01,loc00),true).
initially(neigh(loc02,loc03),true).
initially(neigh(loc02,loc13),true).
initially(neigh(loc02,loc01),true).
initially(neigh(loc02,loc11),true).
initially(neigh(loc03,loc02),true).
initially(neigh(loc03,loc13),true).
initially(neigh(loc13,loc03),true).
initially(neigh(loc13,loc23),true).
initially(neigh(loc13,loc02),true).
initially(neigh(loc23,loc13),true).
initially(neigh(loc23,loc33),true).
initially(neigh(loc23,loc32),true).
initially(neigh(loc33,loc23),true).
initially(neigh(loc33,loc32),true).
initially(neigh(loc32,loc33),true).
initially(neigh(loc32,loc23),true).
initially(neigh(loc32,loc31),true).
initially(neigh(loc31,loc32),true).
initially(neigh(loc31,loc20),true).
initially(neigh(loc31,loc30),true).
initially(neigh(loc30,loc31),true).
initially(neigh(loc30,loc20),true).
initially(neigh(loc20,loc30),true).
initially(neigh(loc20,loc31),true).
initially(neigh(loc20,loc10),true).
initially(neigh(loc20,loc11),true).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

/* EXOGENOUS EVENTS */
prim_action(A) :- exog_action(A).
poss(A,true) :- exog_action(A).

/* USED FOR SWITCHING A TASK FROM A STATE TO ANOTHER */
exog_action(readyToStart(SRVC,ID,TASK)) :- task(TASK), service(SRVC), id(ID). 
exog_action(finishedTask(SRVC,ID,TASK,_V)) :- task(TASK), service(SRVC), id(ID). 

/* EXOGENOUS ACTION THAT CHARGES THE RECOVERY PROCEDURE */
exog_action(planReady(_PLAN)). 

/* DOMAIN-DEPENDENT EXOGENOUS ACTIONS */

exog_action(fire(LOC)) :- location_type(LOC). 
causes_val(fire(LOC),status(LOC),fire,true). 
causes_val(fire(_),exogenous,true,neg(adapting)).

exog_action(rockSlide(LOC)) :- location_type(LOC). 
causes_val(rockSlide(LOC),status(LOC),debris,true). 
causes_val(rockSlide(_),exogenous,true,neg(adapting)).

exog_action(photoLost(LOC)) :- location_type(LOC). 
causes_val(photoLost(LOC),phototaken(LOC),false,true). 
causes_val(photoLost(_),exogenous,true,neg(adapting)).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

prim_action(initPMS). 
poss(initPMS, true).

prim_action(endPMS). 
poss(endPMS, true).  

prim_action(finish). 
poss(finish,true). 
rel_fluent(finished). 
causes_val(finish,finished,true,true). 

rel_fluent(exogenous). 
initially(exogenous,false). 
causes_val(release(_,_),exogenous,true,neg(adapting)).

prim_action(resetExog). 
poss(resetExog,true). 
causes_val(resetExog,exogenous,false,true). 

prim_action(adaptFinish). 
poss(adaptFinish,true). 

prim_action(adaptStart). 
poss(adaptStart,true). 

prim_action(adaptFound). 
poss(adaptFound,true).

prim_action(adaptNotFound). 
poss(adaptNotFound,true).

/*COMMANDS FOR INVOKING THE PLANNER*/
prim_action(invokePlanner). 
poss(invokePlanner,true).

rel_fluent(adaptationPlanReady).
initially(adaptationPlanReady,true).
causes_val(invokePlanner,adaptationPlanReady,false,true).
causes_val(planReady(_P),adaptationPlanReady,true,true).

fun_fluent(recoveryPlan).
initially(recoveryPlan,[]).
causes_val(planReady(P),recoveryPlan,P,true).

/* Action needed for managing signal events */
prim_action(stop).
poss(stop,false).

rel_fluent(adapting). 
causes_val(adaptStart,adapting,true,true). 
causes_val(adaptFound,adapting,false,true). 

proc(relevant,or(some(srvc,and(actor(srvc),or(neg(at(srvc)=at_exp(srvc)),neg(isconnected(srvc))))),some(poi,and(location_type(poi),or(neg(evacuated_exp(poi)=evacuated(poi)),or(neg(phototaken_exp(poi)=phototaken(poi)),neg(status_exp(poi)=status(poi)))))))).

proc(monitor,[?(writeln('Monitor')),ndet([?(neg(relevant)),?(writeln('NonRelevant'))],
					 [?(relevant),?(writeln('Relevant')),
					 [adaptStart,createPlanningProblem,?(report_message(user, 'About to adapt...')),
					 if(built_in_adaptation,pconc([adaptingProgram, adaptFinish],while(adapting, [?(writeln('waiting')),wait])),
					    [invokePlanner,manageRecoveryProcess(recoveryPlan),adaptFound,adaptFinish])
					 ]]), 
					 resetExog]). 

proc(adaptingProgram,searchn([?(true),adapt,[adaptFound,?(report_message(user, 'Adaptation program found!'))]	         
					      ],
			     [assumptions([[assign(N,[workitem(T,D,I,E)]),readyToStart(N,D,T)],[start(N,D,T,I,E),finishedTask(N,D,T,E)]])])). 

/*For a recovery plan of lenght n, it is required to invoke "plans(0,n+1)"*/
proc(adapt,plans(0,2)). 

proc(plans(M,N),[if(M=N,[adaptNotFound],[?(M<(N+1)),ndet([actionSequence(M),?(neg(relevant))],[?(SUCCM is M+1),plans(SUCCM,N)])])]). 

proc(actionSequence(N),ndet([?(N=0)],[?(N>0),
	pi([n,t,i,e],[ ?(and(service(n),and(free(n),capable(n,[workitem(t,id_adapt,i,e)])))),
	assign(n,[workitem(t,id_adapt,i,e)]),
	start(n,id_adapt,t,i,e),
	ackCompl(n,id_adapt,t),
	release(n,[workitem(t,id_adapt,i,e)]),
	?(write(' ITERATION = ')),?(write(N)),?(write(' ')),?(write(' TASK = ')),?(write(t)), ?(write(' INPUT = ')), ?(write(i)), 
	?(write(' SERVICE = ')), ?(write(n)), ?(write(' EXOUTPUT = ')), ?(writeln(e))]),
	?(PRECN is N-1), actionSequence(PRECN)])).

proc(capable(N,X),or(X=[],and(X=[A|TAIL],and(listelem(A),and(A=workitem(T,_D,_I,_E),and(findall(B,requires(T,B),D),and(findall(B,provides(N,B),C),and(subset(D,C),capable(N,TAIL))))))))). 

proc(possible(N,X),and(poss(assign(N,X),A),A)). 

proc(manageExecution(X),[
			atomic([pi(n,[?(and(possible(n,X),and(free(n),capable(n,X)))), assign(n,X)])]),
			pi(n,[?(assigned(n,X)=true),executionHelp(n,X)]),	
			[atomic([pi(n,[?(assigned(n,X)=true),[release(n,X),printALL]])])]
]).

proc(executionHelp(_N,[]),[]). 
proc(executionHelp(N,[workitem(T,D,I,E)|TAIL]),[start(N,D,T,I,E), ackCompl(N,D,T), executionHelp(N,TAIL)]). 


proc(manageRecoveryProcess([]),[]).
proc(manageRecoveryProcess(N),[?(N=[recoveryTask(TASKNAME,SERVICE,INPUTS)|TAIL]),
					manageRecoveryTask(TASKNAME,SERVICE,INPUTS),manageRecoveryProcess(TAIL)]).

proc(manageRecoveryTask(TASKNAME,SERVICE,INPUTS), 
[pi(o,[?(and(A=workitem(TASKNAME,_D,INPUTS,o),listelem(A))),manageExecution(SERVICE,[workitem(TASKNAME,id_adapt,INPUTS,o)])])]). 

proc(manageExecution(S,X),[assign(S,X),pi(n,[?(assigned(n,X)=true),executionHelp(n,X)]),atomic([pi(n,[?(assigned(n,X)=true),[release(n,X),printALL]])])]).

proc(main, mainControl(N)) :- controller(N), !. 
proc(main, mainControl(5)). 

proc(mainControl(5), prioritized_interrupts([
interrupt(and(neg(finished),neg(adaptationPlanReady)), [?(writeln('>>>>>>>>>>>> Waiting for a recovery plan...')), wait]),
interrupt(and(neg(finished),exogenous), monitor),
interrupt(true, [process,finish]),
interrupt(neg(finished), wait)])).

/* EXAMPLE OF A RECOVERY PROCESS 
proc(process,[initPMS,
[manageRecoveryTask(act2,evacuate,[loc00])],
endPMS]).
*/

/* MAIN PROCESS */

proc(process,[initPMS,
rrobin([
[manageExecution([workitem(go,id_1,[loc00,loc33],[loc33])]),manageExecution([workitem(takephoto,id_2,[loc33],[true])]),
 manageExecution([workitem(updatestatus,id_3,[loc33],[ok])])],
rrobin([
[manageExecution([workitem(go,id_4,[loc00,loc32],[loc32])]),manageExecution([workitem(evacuate,id_5,[loc32],[true])]),
 manageExecution([workitem(updatestatus,id_6,[loc32],[ok])])],
[manageExecution([workitem(go,id_7,[loc00,loc31],[loc31])]),manageExecution([workitem(evacuate,id_8,[loc31],[true])]),
 manageExecution([workitem(updatestatus,id_9,[loc31],[ok])])]
])]),
endPMS]).


proc(createPlanningProblem,[writeFile('Planners/LPG-TD/problem.pddl')]).

proc(writeFile(File),[?(open(File, write, Stream)),
?(writeln(Stream,('(define (problem EM1) (:domain Derailment)'))),
?(writeln(Stream,('(:objects'))),
?(writeln(Stream,('act1 - actor'))),
?(writeln(Stream,('act2 - actor'))),
?(writeln(Stream,('act3 - actor'))),
?(writeln(Stream,('act4 - actor'))),
?(writeln(Stream,('rb1 - robot'))),
?(writeln(Stream,('rb2 - robot'))),
?(writeln(Stream,('ok - status_type'))),
?(writeln(Stream,('fire - status_type'))),
?(writeln(Stream,('debris - status_type'))),
?(writeln(Stream,('movement - capability'))),
?(writeln(Stream,('battery - capability'))),
?(writeln(Stream,('hatchet - capability'))),	
?(writeln(Stream,('camera - capability'))),	
?(writeln(Stream,('gprs - capability'))),	
?(writeln(Stream,('extinguisher - capability'))),		
?(writeln(Stream,('digger - capability'))),	
?(writeln(Stream,('powerpack - capability'))),
?(writeln(Stream,('loc00 - location_type'))),
?(writeln(Stream,('loc01 - location_type'))),
?(writeln(Stream,('loc02 - location_type'))),
?(writeln(Stream,('loc03 - location_type'))),
?(writeln(Stream,('loc10 - location_type'))),
?(writeln(Stream,('loc11 - location_type'))),
?(writeln(Stream,('loc13 - location_type'))),
?(writeln(Stream,('loc20 - location_type'))),
?(writeln(Stream,('loc23 - location_type'))),
?(writeln(Stream,('loc30 - location_type'))),
?(writeln(Stream,('loc31 - location_type'))),
?(writeln(Stream,('loc32 - location_type'))),
?(writeln(Stream,('loc33 - location_type'))),	
?(writeln(Stream,(')'))),
?(writeln(Stream,('(:init'))),
if(free(act1)=true,?(writeln(Stream,('(free act1)'))),[]),
if(free(act2)=true,?(writeln(Stream,('(free act2)'))),[]),
if(free(act3)=true,?(writeln(Stream,('(free act3)'))),[]),
if(free(act4)=true,?(writeln(Stream,('(free act4)'))),[]),
if(free(rb1)=true,?(writeln(Stream,('(free rb1)'))),[]),
if(free(rb2)=true,?(writeln(Stream,('(free rb2)'))),[]),
?(writeln(Stream,('(provides act1 movement)'))),
?(writeln(Stream,('(provides act1 gprs)'))),
?(writeln(Stream,('(provides act1 camera)'))),
?(writeln(Stream,('(provides act1 extinguisher)'))),
?(writeln(Stream,('(provides act2 movement)'))),
?(writeln(Stream,('(provides act2 gprs)'))),
?(writeln(Stream,('(provides act2 hatchet)'))),
?(writeln(Stream,('(provides act3 movement)'))),
?(writeln(Stream,('(provides act3 gprs)'))),
?(writeln(Stream,('(provides act3 hatchet)'))),
?(writeln(Stream,('(provides act4 movement)'))),
?(writeln(Stream,('(provides act4 powerpack)'))),	
?(writeln(Stream,('(provides rb1 digger)'))),	
?(writeln(Stream,('(provides rb1 battery)'))),	
?(writeln(Stream,('(provides rb2 digger)'))),	
?(writeln(Stream,('(provides rb2 battery)'))),		
?(writeln(Stream,('(covered loc00)'))),		
?(writeln(Stream,('(covered loc10)'))),		
?(writeln(Stream,('(covered loc20)'))),		
?(writeln(Stream,('(covered loc01)'))),			
?(writeln(Stream,('(covered loc11)'))),		
?(writeln(Stream,('(covered loc02)'))),		
?(writeln(Stream,('(neigh loc00 loc10)'))),		
?(writeln(Stream,('(neigh loc00 loc11)'))),	
?(writeln(Stream,('(neigh loc00 loc01)'))),		
?(writeln(Stream,('(neigh loc11 loc10)'))),		
?(writeln(Stream,('(neigh loc11 loc01)'))),		
?(writeln(Stream,('(neigh loc11 loc00)'))),	
?(writeln(Stream,('(neigh loc11 loc20)'))),		
?(writeln(Stream,('(neigh loc11 loc02)'))),		
?(writeln(Stream,('(neigh loc10 loc20)'))),			
?(writeln(Stream,('(neigh loc10 loc00)'))),		
?(writeln(Stream,('(neigh loc10 loc11)'))),	
?(writeln(Stream,('(neigh loc10 loc01)'))),		
?(writeln(Stream,('(neigh loc01 loc02)'))),			
?(writeln(Stream,('(neigh loc01 loc11)'))),
?(writeln(Stream,('(neigh loc01 loc10)'))),	
?(writeln(Stream,('(neigh loc01 loc00)'))),	
?(writeln(Stream,('(neigh loc02 loc03)'))),		
?(writeln(Stream,('(neigh loc02 loc13)'))),		
?(writeln(Stream,('(neigh loc02 loc01)'))),		
?(writeln(Stream,('(neigh loc02 loc11)'))),		
?(writeln(Stream,('(neigh loc03 loc02)'))),			
?(writeln(Stream,('(neigh loc03 loc13)'))),	
?(writeln(Stream,('(neigh loc13 loc03)'))),	
?(writeln(Stream,('(neigh loc13 loc23)'))),	
?(writeln(Stream,('(neigh loc13 loc02)'))),		
?(writeln(Stream,('(neigh loc23 loc13)'))),		
?(writeln(Stream,('(neigh loc23 loc33)'))),	
?(writeln(Stream,('(neigh loc23 loc32)'))),		
?(writeln(Stream,('(neigh loc33 loc23)'))),	
?(writeln(Stream,('(neigh loc33 loc32)'))),	
?(writeln(Stream,('(neigh loc32 loc33)'))),	
?(writeln(Stream,('(neigh loc32 loc23)'))),	
?(writeln(Stream,('(neigh loc32 loc31)'))),	
?(writeln(Stream,('(neigh loc31 loc32)'))),	
?(writeln(Stream,('(neigh loc31 loc20)'))),		
?(writeln(Stream,('(neigh loc31 loc30)'))),
?(writeln(Stream,('(neigh loc30 loc31)'))),	
?(writeln(Stream,('(neigh loc30 loc20)'))),
?(writeln(Stream,('(neigh loc20 loc30)'))),
?(writeln(Stream,('(neigh loc20 loc31)'))),
?(writeln(Stream,('(neigh loc20 loc10)'))),	
?(writeln(Stream,('(neigh loc20 loc11)'))),
?(write(Stream,('('))),?(write(Stream,('at act1 '))), ?(write(Stream,(at(act1)))),?(writeln(Stream,(')'))),
?(write(Stream,('('))),?(write(Stream,('at act2 '))), ?(write(Stream,(at(act2)))),?(writeln(Stream,(')'))),
?(write(Stream,('('))),?(write(Stream,('at act3 '))), ?(write(Stream,(at(act3)))),?(writeln(Stream,(')'))),
?(write(Stream,('('))),?(write(Stream,('at act4 '))), ?(write(Stream,(at(act4)))),?(writeln(Stream,(')'))),
?(write(Stream,('('))),?(write(Stream,('at rb1 '))), ?(write(Stream,(at(rb1)))),?(writeln(Stream,(')'))),
?(write(Stream,('('))),?(write(Stream,('at rb2 '))), ?(write(Stream,(at(rb2)))),?(writeln(Stream,(')'))),
?(write(Stream,('(= '))),?(write(Stream,('(batterylevel rb1) '))), ?(write(Stream,(batterylevel(rb1)))),?(writeln(Stream,(')'))),
?(write(Stream,('(= '))),?(write(Stream,('(batterylevel rb2) '))), ?(write(Stream,(batterylevel(rb2)))),?(writeln(Stream,(')'))),
?(write(Stream,('(= '))),?(write(Stream,('(batteryrecharging) '))), ?(write(Stream,(batteryrecharging))),?(writeln(Stream,(')'))),
?(write(Stream,('(= '))),?(write(Stream,('(generalbattery) '))), ?(write(Stream,(generalbattery))),?(writeln(Stream,(')'))),
?(write(Stream,('(= '))),?(write(Stream,('(debrisstep) '))), ?(write(Stream,(debrisstep))),?(writeln(Stream,(')'))),
?(write(Stream,('(= '))),?(write(Stream,('(movestep) '))), ?(write(Stream,(movestep))),?(writeln(Stream,(')'))),
if(phototaken(loc00)=true,?(writeln(Stream,('(phototaken loc00)'))),[]),
if(phototaken(loc01)=true,?(writeln(Stream,('(phototaken loc01)'))),[]),
if(phototaken(loc02)=true,?(writeln(Stream,('(phototaken loc02)'))),[]),
if(phototaken(loc03)=true,?(writeln(Stream,('(phototaken loc03)'))),[]),
if(phototaken(loc10)=true,?(writeln(Stream,('(phototaken loc10)'))),[]),
if(phototaken(loc11)=true,?(writeln(Stream,('(phototaken loc11)'))),[]),
if(phototaken(loc13)=true,?(writeln(Stream,('(phototaken loc13)'))),[]),
if(phototaken(loc20)=true,?(writeln(Stream,('(phototaken loc20)'))),[]),
if(phototaken(loc23)=true,?(writeln(Stream,('(phototaken loc23)'))),[]),
if(phototaken(loc30)=true,?(writeln(Stream,('(phototaken loc30)'))),[]),
if(phototaken(loc31)=true,?(writeln(Stream,('(phototaken loc31)'))),[]),
if(phototaken(loc32)=true,?(writeln(Stream,('(phototaken loc32)'))),[]),
if(phototaken(loc33)=true,?(writeln(Stream,('(phototaken loc33)'))),[]),
?(write(Stream,('('))),?(write(Stream,('status loc00 '))), ?(write(Stream,(status(loc00)))),?(writeln(Stream,(')'))),
?(write(Stream,('('))),?(write(Stream,('status loc01 '))), ?(write(Stream,(status(loc01)))),?(writeln(Stream,(')'))),
?(write(Stream,('('))),?(write(Stream,('status loc02 '))), ?(write(Stream,(status(loc02)))),?(writeln(Stream,(')'))),
?(write(Stream,('('))),?(write(Stream,('status loc03 '))), ?(write(Stream,(status(loc03)))),?(writeln(Stream,(')'))),
?(write(Stream,('('))),?(write(Stream,('status loc10 '))), ?(write(Stream,(status(loc10)))),?(writeln(Stream,(')'))),
?(write(Stream,('('))),?(write(Stream,('status loc11 '))), ?(write(Stream,(status(loc11)))),?(writeln(Stream,(')'))),
?(write(Stream,('('))),?(write(Stream,('status loc13 '))), ?(write(Stream,(status(loc13)))),?(writeln(Stream,(')'))),
?(write(Stream,('('))),?(write(Stream,('status loc20 '))), ?(write(Stream,(status(loc20)))),?(writeln(Stream,(')'))),
?(write(Stream,('('))),?(write(Stream,('status loc23 '))), ?(write(Stream,(status(loc23)))),?(writeln(Stream,(')'))),
?(write(Stream,('('))),?(write(Stream,('status loc30 '))), ?(write(Stream,(status(loc30)))),?(writeln(Stream,(')'))),
?(write(Stream,('('))),?(write(Stream,('status loc31 '))), ?(write(Stream,(status(loc31)))),?(writeln(Stream,(')'))),
?(write(Stream,('('))),?(write(Stream,('status loc32 '))), ?(write(Stream,(status(loc32)))),?(writeln(Stream,(')'))),
?(write(Stream,('('))),?(write(Stream,('status loc33 '))), ?(write(Stream,(status(loc33)))),?(writeln(Stream,(')'))),
if(evacuated(loc00)=true,?(writeln(Stream,('(evacuated loc00)'))),[]),
if(evacuated(loc01)=true,?(writeln(Stream,('(evacuated loc01)'))),[]),
if(evacuated(loc02)=true,?(writeln(Stream,('(evacuated loc02)'))),[]),
if(evacuated(loc03)=true,?(writeln(Stream,('(evacuated loc03)'))),[]),
if(evacuated(loc10)=true,?(writeln(Stream,('(evacuated loc10)'))),[]),
if(evacuated(loc11)=true,?(writeln(Stream,('(evacuated loc11)'))),[]),
if(evacuated(loc13)=true,?(writeln(Stream,('(evacuated loc13)'))),[]),
if(evacuated(loc20)=true,?(writeln(Stream,('(evacuated loc20)'))),[]),
if(evacuated(loc23)=true,?(writeln(Stream,('(evacuated loc23)'))),[]),
if(evacuated(loc30)=true,?(writeln(Stream,('(evacuated loc30)'))),[]),
if(evacuated(loc31)=true,?(writeln(Stream,('(evacuated loc31)'))),[]),
if(evacuated(loc32)=true,?(writeln(Stream,('(evacuated loc32)'))),[]),
if(evacuated(loc33)=true,?(writeln(Stream,('(evacuated loc33)'))),[]),
?(writeln(Stream,(')'))),
?(writeln(Stream,('(:goal'))),
?(writeln(Stream,('(and'))),
?(write(Stream,('('))),?(write(Stream,('at act1 '))), ?(write(Stream,(at_exp(act1)))),?(writeln(Stream,(')'))),
?(write(Stream,('('))),?(write(Stream,('at act2 '))), ?(write(Stream,(at_exp(act2)))),?(writeln(Stream,(')'))),
?(write(Stream,('('))),?(write(Stream,('at act3 '))), ?(write(Stream,(at_exp(act3)))),?(writeln(Stream,(')'))),
?(write(Stream,('('))),?(write(Stream,('at act4 '))), ?(write(Stream,(at_exp(act4)))),?(writeln(Stream,(')'))),
?(write(Stream,('('))),?(write(Stream,('status loc00 '))), ?(write(Stream,(status_exp(loc00)))),?(writeln(Stream,(')'))),
?(write(Stream,('('))),?(write(Stream,('status loc01 '))), ?(write(Stream,(status_exp(loc01)))),?(writeln(Stream,(')'))),
?(write(Stream,('('))),?(write(Stream,('status loc02 '))), ?(write(Stream,(status_exp(loc02)))),?(writeln(Stream,(')'))),
?(write(Stream,('('))),?(write(Stream,('status loc03 '))), ?(write(Stream,(status_exp(loc03)))),?(writeln(Stream,(')'))),
?(write(Stream,('('))),?(write(Stream,('status loc10 '))), ?(write(Stream,(status_exp(loc10)))),?(writeln(Stream,(')'))),
?(write(Stream,('('))),?(write(Stream,('status loc11 '))), ?(write(Stream,(status_exp(loc11)))),?(writeln(Stream,(')'))),
?(write(Stream,('('))),?(write(Stream,('status loc13 '))), ?(write(Stream,(status_exp(loc13)))),?(writeln(Stream,(')'))),
?(write(Stream,('('))),?(write(Stream,('status loc20 '))), ?(write(Stream,(status_exp(loc20)))),?(writeln(Stream,(')'))),
?(write(Stream,('('))),?(write(Stream,('status loc23 '))), ?(write(Stream,(status_exp(loc23)))),?(writeln(Stream,(')'))),
?(write(Stream,('('))),?(write(Stream,('status loc30 '))), ?(write(Stream,(status_exp(loc30)))),?(writeln(Stream,(')'))),
?(write(Stream,('('))),?(write(Stream,('status loc31 '))), ?(write(Stream,(status_exp(loc31)))),?(writeln(Stream,(')'))),
?(write(Stream,('('))),?(write(Stream,('status loc32 '))), ?(write(Stream,(status_exp(loc32)))),?(writeln(Stream,(')'))),
?(write(Stream,('('))),?(write(Stream,('status loc33 '))), ?(write(Stream,(status_exp(loc33)))),?(writeln(Stream,(')'))),
if(evacuated_exp(loc00)=true,?(writeln(Stream,('(evacuated loc00)'))),[]),
if(evacuated_exp(loc01)=true,?(writeln(Stream,('(evacuated loc01)'))),[]),
if(evacuated_exp(loc02)=true,?(writeln(Stream,('(evacuated loc02)'))),[]),
if(evacuated_exp(loc03)=true,?(writeln(Stream,('(evacuated loc03)'))),[]),
if(evacuated_exp(loc10)=true,?(writeln(Stream,('(evacuated loc10)'))),[]),
if(evacuated_exp(loc11)=true,?(writeln(Stream,('(evacuated loc11)'))),[]),
if(evacuated_exp(loc13)=true,?(writeln(Stream,('(evacuated loc13)'))),[]),
if(evacuated_exp(loc20)=true,?(writeln(Stream,('(evacuated loc20)'))),[]),
if(evacuated_exp(loc23)=true,?(writeln(Stream,('(evacuated loc23)'))),[]),
if(evacuated_exp(loc30)=true,?(writeln(Stream,('(evacuated loc30)'))),[]),
if(evacuated_exp(loc31)=true,?(writeln(Stream,('(evacuated loc31)'))),[]),
if(evacuated_exp(loc32)=true,?(writeln(Stream,('(evacuated loc32)'))),[]),
if(evacuated_exp(loc33)=true,?(writeln(Stream,('(evacuated loc33)'))),[]),
if(phototaken_exp(loc00)=true,?(writeln(Stream,('(phototaken loc00)'))),[]),
if(phototaken_exp(loc01)=true,?(writeln(Stream,('(phototaken loc01)'))),[]),
if(phototaken_exp(loc02)=true,?(writeln(Stream,('(phototaken loc02)'))),[]),
if(phototaken_exp(loc03)=true,?(writeln(Stream,('(phototaken loc03)'))),[]),
if(phototaken_exp(loc10)=true,?(writeln(Stream,('(phototaken loc10)'))),[]),
if(phototaken_exp(loc11)=true,?(writeln(Stream,('(phototaken loc11)'))),[]),
if(phototaken_exp(loc13)=true,?(writeln(Stream,('(phototaken loc13)'))),[]),
if(phototaken_exp(loc20)=true,?(writeln(Stream,('(phototaken loc20)'))),[]),
if(phototaken_exp(loc23)=true,?(writeln(Stream,('(phototaken loc23)'))),[]),
if(phototaken_exp(loc30)=true,?(writeln(Stream,('(phototaken loc30)'))),[]),
if(phototaken_exp(loc31)=true,?(writeln(Stream,('(phototaken loc31)'))),[]),
if(phototaken_exp(loc32)=true,?(writeln(Stream,('(phototaken loc32)'))),[]),
if(phototaken_exp(loc33)=true,?(writeln(Stream,('(phototaken loc33)'))),[]),
?(writeln(Stream,('(isconnected act1)'))),
?(writeln(Stream,('(isconnected act2)'))),
?(writeln(Stream,('(isconnected act3)'))),
?(writeln(Stream,('(isconnected act4)'))),
?(writeln(Stream,('))'))),
?(writeln(Stream,('(:metric minimize (total-time))'))),
?(writeln(Stream,(')'))),
?(nl(Stream)),?(close(Stream))]).

proc(printALL,[?(writeln('****FREE****')),
	       ?(write('free(act1) = ')),?(writeln(free(act1))),
	       ?(write('free(act2) = ')),?(writeln(free(act2))),
	       ?(write('free(act3) = ')),?(writeln(free(act3))),
	       ?(write('free(act4) = ')),?(writeln(free(act4))),
	       ?(write('free(rb1) = ')),?(writeln(free(rb1))),
	       ?(write('free(rb2) = ')),?(writeln(free(rb2))),
	       ?(writeln('****AT****')),
	       ?(write('at(act1) = ')),?(writeln(at(act1))),
	       ?(write('at_exp(act1) = ')),?(writeln(at_exp(act1))),
	       ?(write('at(act2) = ')),?(writeln(at(act2))),
	       ?(write('at_exp(act2) = ')),?(writeln(at_exp(act2))),
	       ?(write('at(act3) = ')),?(writeln(at(act3))),
	       ?(write('at_exp(act3) = ')),?(writeln(at_exp(act3))),
	       ?(write('at(act4) = ')),?(writeln(at(act4))),
	       ?(write('at_exp(act4) = ')),?(writeln(at_exp(act4))),
	       ?(write('at(rb1) = ')),?(writeln(at(rb1))),
	       ?(write('at(rb2) = ')),?(writeln(at(rb2))),
	       ?(writeln('****BATTERY LEVEL****')),
	       ?(write('batterylevel(rb1) = ')),?(writeln(batterylevel(rb1))),
	       ?(write('batterylevel(rb2) = ')),?(writeln(batterylevel(rb2))),
	       ?(writeln('****GENERAL BATTERY****')),
	       ?(write('generalbattery = ')),?(writeln(generalbattery)),
	       ?(writeln('****MOVE STEP****')),
 	       ?(write('movestep = ')),?(writeln(movestep)),
	       ?(writeln('****DEBRIS STEP****')),
 	       ?(write('debrisstep = ')),?(writeln(debrisstep)),
	       ?(writeln('****EVACUATED****')),
	       ?(write('evacuated(loc00) = ')),?(writeln(evacuated(loc00))),
	       ?(write('evacuated_exp(loc00) = ')),?(writeln(evacuated_exp(loc00))),
	       ?(write('evacuated(loc01) = ')),?(writeln(evacuated(loc01))),
	       ?(write('evacuated_exp(loc01) = ')),?(writeln(evacuated_exp(loc01))),
	       ?(write('evacuated(loc02) = ')),?(writeln(evacuated(loc02))),
	       ?(write('evacuated_exp(loc02) = ')),?(writeln(evacuated_exp(loc02))),
	       ?(write('evacuated(loc03) = ')),?(writeln(evacuated(loc03))),
	       ?(write('evacuated_exp(loc03) = ')),?(writeln(evacuated_exp(loc03))),
	       ?(write('evacuated(loc10) = ')),?(writeln(evacuated(loc10))),
	       ?(write('evacuated_exp(loc10) = ')),?(writeln(evacuated_exp(loc10))),
	       ?(write('evacuated(loc11) = ')),?(writeln(evacuated(loc11))),
	       ?(write('evacuated_exp(loc11) = ')),?(writeln(evacuated_exp(loc11))),
	       ?(write('evacuated(loc13) = ')),?(writeln(evacuated(loc13))),
	       ?(write('evacuated_exp(loc13) = ')),?(writeln(evacuated_exp(loc13))),
	       ?(write('evacuated(loc20) = ')),?(writeln(evacuated(loc20))),
	       ?(write('evacuated_exp(loc20) = ')),?(writeln(evacuated_exp(loc20))),
	       ?(write('evacuated(loc23) = ')),?(writeln(evacuated(loc23))),
	       ?(write('evacuated_exp(loc23) = ')),?(writeln(evacuated_exp(loc23))),
	       ?(write('evacuated(loc30) = ')),?(writeln(evacuated(loc30))),
	       ?(write('evacuated_exp(loc30) = ')),?(writeln(evacuated_exp(loc30))),
	       ?(write('evacuated(loc31) = ')),?(writeln(evacuated(loc31))),
	       ?(write('evacuated_exp(loc31) = ')),?(writeln(evacuated_exp(loc31))),
	       ?(write('evacuated(loc32) = ')),?(writeln(evacuated(loc32))),
	       ?(write('evacuated_exp(loc32) = ')),?(writeln(evacuated_exp(loc32))),
	       ?(write('evacuated(loc33) = ')),?(writeln(evacuated(loc33))),
	       ?(write('evacuated_exp(loc33) = ')),?(writeln(evacuated_exp(loc33))),
	       ?(writeln('****PHOTO TAKEN****')),
	       ?(write('phototaken(loc00) = ')),?(writeln(phototaken(loc00))),
	       ?(write('phototaken_exp(loc00) = ')),?(writeln(phototaken_exp(loc00))),
	       ?(write('phototaken(loc01) = ')),?(writeln(phototaken(loc01))),
	       ?(write('phototaken_exp(loc01) = ')),?(writeln(phototaken_exp(loc01))),
	       ?(write('phototaken(loc02) = ')),?(writeln(phototaken(loc02))),
	       ?(write('phototaken_exp(loc02) = ')),?(writeln(phototaken_exp(loc02))),
	       ?(write('phototaken(loc03) = ')),?(writeln(phototaken(loc03))),
	       ?(write('phototaken_exp(loc03) = ')),?(writeln(phototaken_exp(loc03))),
	       ?(write('phototaken(loc10) = ')),?(writeln(phototaken(loc10))),
	       ?(write('phototaken_exp(loc10) = ')),?(writeln(phototaken_exp(loc10))),
	       ?(write('phototaken(loc11) = ')),?(writeln(phototaken(loc11))),
	       ?(write('phototaken_exp(loc11) = ')),?(writeln(phototaken_exp(loc11))),
	       ?(write('phototaken(loc13) = ')),?(writeln(phototaken(loc13))),
	       ?(write('phototaken_exp(loc13) = ')),?(writeln(phototaken_exp(loc13))),
	       ?(write('phototaken(loc20) = ')),?(writeln(phototaken(loc20))),
	       ?(write('phototaken_exp(loc20) = ')),?(writeln(phototaken_exp(loc20))),
	       ?(write('phototaken(loc23) = ')),?(writeln(phototaken(loc23))),
	       ?(write('phototaken_exp(loc23) = ')),?(writeln(phototaken_exp(loc23))),
	       ?(write('phototaken(loc30) = ')),?(writeln(phototaken(loc30))),
	       ?(write('phototaken_exp(loc30) = ')),?(writeln(phototaken_exp(loc30))),
	       ?(write('phototaken(loc31) = ')),?(writeln(phototaken(loc31))),
	       ?(write('phototaken_exp(loc31) = ')),?(writeln(phototaken_exp(loc31))),
	       ?(write('phototaken(loc32) = ')),?(writeln(phototaken(loc32))),
	       ?(write('phototaken_exp(loc32) = ')),?(writeln(phototaken_exp(loc32))),
	       ?(write('phototaken(loc33) = ')),?(writeln(phototaken(loc33))),
	       ?(write('phototaken_exp(loc33) = ')),?(writeln(phototaken_exp(loc33))),
	       ?(writeln('****STATUS****')),
	       ?(write('status(loc00) = ')),?(writeln(status(loc00))),
	       ?(write('status_exp(loc00) = ')),?(writeln(status_exp(loc00))),
	       ?(write('status(loc01) = ')),?(writeln(status(loc01))),
	       ?(write('status_exp(loc01) = ')),?(writeln(status_exp(loc01))),
	       ?(write('status(loc02) = ')),?(writeln(status(loc02))),
	       ?(write('status_exp(loc02) = ')),?(writeln(status_exp(loc02))),
	       ?(write('status(loc03) = ')),?(writeln(status(loc03))),
	       ?(write('status_exp(loc03) = ')),?(writeln(status_exp(loc03))),
	       ?(write('status(loc10) = ')),?(writeln(status(loc10))),
	       ?(write('status_exp(loc10) = ')),?(writeln(status_exp(loc10))),
	       ?(write('status(loc11) = ')),?(writeln(status(loc11))),
	       ?(write('status_exp(loc11) = ')),?(writeln(status_exp(loc11))),
	       ?(write('status(loc13) = ')),?(writeln(status(loc13))),
	       ?(write('status_exp(loc13) = ')),?(writeln(status_exp(loc13))),
	       ?(write('status(loc20) = ')),?(writeln(status(loc20))),
	       ?(write('status_exp(loc20) = ')),?(writeln(status_exp(loc20))),
	       ?(write('status(loc23) = ')),?(writeln(status(loc23))),
	       ?(write('status_exp(loc23) = ')),?(writeln(status_exp(loc23))),
	       ?(write('status(loc30) = ')),?(writeln(status(loc30))),
	       ?(write('status_exp(loc30) = ')),?(writeln(status_exp(loc30))),
	       ?(write('status(loc31) = ')),?(writeln(status(loc31))),
	       ?(write('status_exp(loc31) = ')),?(writeln(status_exp(loc31))),
	       ?(write('status(loc32) = ')),?(writeln(status(loc32))),
	       ?(write('status_exp(loc32) = ')),?(writeln(status_exp(loc32))),
	       ?(write('status(loc33) = ')),?(writeln(status(loc33))),
	       ?(write('status_exp(loc33) = ')),?(writeln(status_exp(loc33))),
	       ?(writeln('****IS CONNECTED****')),
	       ?(write('isconnected(act1) = ')),if(isconnected(act1),?(writeln(true)),?(writeln(false))),
 	       ?(write('isconnected(act2) = ')),if(isconnected(act2),?(writeln(true)),?(writeln(false))),
 	       ?(write('isconnected(act3) = ')),if(isconnected(act3),?(writeln(true)),?(writeln(false))),
 	       ?(write('isconnected(act4) = ')),if(isconnected(act4),?(writeln(true)),?(writeln(false)))
]).

