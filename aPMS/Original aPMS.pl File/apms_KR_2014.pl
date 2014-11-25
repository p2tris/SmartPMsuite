%%%%%%%%%%%%%%%%%%%% 
% FILE: aPMS.pl
% DESCRIPTION : The most recent version of the Indigolog adaptive Process Management System
% VERSION : 0.2
% FIXED BUGS : Workitems work with multiple inputs/outputs - Exogenous events are correctly supported
% KNOWN PROBLEMS : Nested Adaptation does not work - Priorities are currently not managed - If a running task takes as input or 
%		   as (supposed) expected output an integer fluent (not directly a number), the program could not work properly.
% PROPERTIES : 	   COSTANTS must be represented in the uppercase format.
%		   Non-quantified variables (for example, when used with the some command) must be represented in the lowercase format.
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

service(S) :- domain(S,[act1,act2,act3,act4,rb1,rb2]).

capability(B) :- domain(B,[movement,hatchet,camera,gprs,extinguisher,battery,digger,powerpack]).

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
provides(act4,gprs).
provides(act4,extinguisher). 
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
requires(evacuate,hatchet). 
requires(takephoto,camera). 
requires(updatestatus,gprs). 
requires(extinguishfire,extinguisher). 
requires(move,battery). 
requires(removedebris,digger). 
requires(chargebattery,powerpack).
 
listelem(workitem(go,ID,[FROM,TO],[TO])) :- id(ID),location_type(FROM),location_type(TO).
listelem(workitem(takephoto,ID,[LOC],[true])) :- id(ID),location_type(LOC),boolean_type(true).
listelem(workitem(evacuate,ID,[LOC],[true])) :- id(ID),location_type(LOC),boolean_type(true).
listelem(workitem(move,ID,[FROM,TO],[TO])) :- id(ID),location_type(FROM),location_type(TO).
listelem(workitem(chargebattery,ID,[RB],[])) :- id(ID),service(RB).
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

poss(assign(SRVC,[workitem(go,ID,[FROM,TO],[TO])]),and(isConnected(SRVC),at(SRVC)=FROM)) :- 
service(SRVC),id(ID),location_type(FROM),location_type(TO).

poss(assign(SRVC,[workitem(takephoto,ID,[LOC],[true])]),and(isConnected(SRVC),at(SRVC)=LOC)) :- 
service(SRVC),id(ID),location_type(LOC),boolean_type(true).

poss(assign(SRVC,[workitem(evacuate,ID,[LOC],[true])]),and(isConnected(SRVC),and(at(SRVC)=LOC,and(evacuated(LOC)=false,status(LOC)=ok)))) :- 
service(SRVC),id(ID),location_type(LOC),boolean_type(true).

poss(assign(SRVC,[workitem(move,ID,[FROM,TO],[TO])]),and(atRobot(SRVC)=FROM,batteryLevel(SRVC)>=moveStep)) :-
service(SRVC),id(ID),location_type(FROM),location_type(TO).

poss(assign(SRVC,[workitem(chargebattery,ID,[RB],[])]),and(isConnected(SRVC),(and(at(SRVC)=atRobot(RB),and(provides(RB,battery),generalBattery>=batteryRecharging))))) :-
service(SRVC),id(ID),service(RB).

poss(assign(SRVC,[workitem(removedebris,ID,[LOC],[ST])]), and(atRobot(SRVC)=LOC,and(status(LOC)=debris,batteryLevel(SRVC)>=debrisStep))) :- 
service(SRVC),id(ID),location_type(LOC),status_type(ST).

poss(assign(SRVC,[workitem(extinguishfire,ID,[LOC],[ST])]), and(isConnected(SRVC),and(at(SRVC)=LOC,status(LOC)=fire))) :-
service(SRVC),id(ID),location_type(LOC),status_type(ST).

poss(assign(SRVC,[workitem(updatestatus,ID,[LOC],[ST])]),and(isConnected(SRVC),and(at(SRVC)=LOC,status(LOC)=ok))) :-
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

/*FLUENT AT*/
fun_fluent(at(SRVC)) :- service(SRVC). 

causes_val(finishedTask(SRVC,ID,go,[OUTPUT]),at(SRVC),OUTPUT,assigned(SRVC,[workitem(go,ID,[FROM,TO],[TO])])) :-
	service(SRVC),id(ID),location_type(OUTPUT),location_type(FROM),location_type(TO).

fun_fluent(at_exp(SRVC)) :- service(SRVC).

causes_val(finishedTask(SRVC,ID,go,[OUTPUT]),at_exp(SRVC),TO,and(neg(adapting),assigned(SRVC,[workitem(go,ID,[FROM,TO],[TO])]))) :- 
	service(SRVC),id(ID),location_type(OUTPUT),location_type(FROM),location_type(TO).

/*FLUENT AT_ROBOT*/
fun_fluent(atRobot(SRVC)) :- service(SRVC). 

causes_val(finishedTask(SRVC,ID,move,[OUTPUT]),atRobot(SRVC),OUTPUT,assigned(SRVC,[workitem(move,ID,[FROM,TO],[TO])])) :-
        service(SRVC),id(ID),location_type(OUTPUT),location_type(FROM),location_type(TO).


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
fun_fluent(batteryLevel(SRVC)) :- service(SRVC).

causes_val(finishedTask(SRVC,ID,move,[OUTPUT]),batteryLevel(SRVC),L,and(assigned(SRVC,[workitem(move,ID,[FROM,TO],[TO])]),
L is batteryLevel(SRVC)-moveStep)) :-
        service(SRVC),location_type(OUTPUT),location_type(FROM),location_type(TO).

causes_val(finishedTask(SRVC,ID,removedebris,[OUTPUT]),batteryLevel(SRVC),L,
    and(assigned(SRVC,[workitem(removedebris,ID,[LOC],[ok])]),L is batteryLevel(SRVC)-debrisStep)) :-
        service(SRVC),location_type(LOC),status_type(ok),status_type(OUTPUT).

causes_val(finishedTask(SRVC,ID,chargebattery,[]),batteryLevel(RB),L, 
    and(assigned(SRVC,[workitem(chargebattery,ID,[RB],[])]),L is batteryLevel(RB)+batteryRecharging)) :-
        service(SRVC),service(RB).

/*FLUENT PHOTO TAKEN*/
fun_fluent(photoTaken(P)) :- location_type(P).

causes_val(finishedTask(SRVC,ID,takephoto,[OUTPUT]),photoTaken(LOC),OUTPUT,
  	assigned(SRVC,[workitem(takephoto,ID,[LOC],[true])])) :-
        	service(SRVC),id(ID),location_type(LOC),boolean_type(true),boolean_type(OUTPUT).

fun_fluent(photoTaken_exp(P)) :- location_type(P).

causes_val(finishedTask(SRVC,ID,takephoto,[OUTPUT]),photoTaken_exp(LOC),true,
  	and(neg(adapting),assigned(SRVC,[workitem(takephoto,ID,[LOC],[true])]))) :-
        	service(SRVC),id(ID),location_type(LOC),boolean_type(true),boolean_type(OUTPUT).

/*FLUENT GENERAL BATTERY*/
fun_fluent(generalBattery).

causes_val(finishedTask(SRVC,ID,chargebattery,[]),generalBattery,L,
	and(assigned(SRVC,[workitem(chargebattery,ID,[RB],[])]),L is generalBattery-batteryRecharging)) :-
        	service(SRVC),id(ID),service(RB).

/*FLUENT BATTERY RECHARGING*/
fun_fluent(batteryRecharging).

/*FLUENT MOVE STEP*/
fun_fluent(moveStep).

/*FLUENT DEBRIS STEP*/
fun_fluent(debrisStep).


proc(notConnected,false).

proc(isConnected(SRVC),
	and(provides(SRVC,movement),	
	or(covered(at(SRVC)),
	some(rb,and(service(rb),and(provides(rb,battery),or(neigh(at(SRVC),atRobot(rb)),at(SRVC)=atRobot(rb))))))
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

initially(batteryRecharging,10).
initially(moveStep,2).
initially(debrisStep,3).
initially(generalBattery,30).

initially(at(act1),loc00).
initially(at_exp(act1),loc00).
initially(at(act2),loc00).
initially(at_exp(act2),loc00).
initially(at(act3),loc00).
initially(at_exp(act3),loc00).
initially(at(act4),loc00).
initially(at_exp(act4),loc00).

initially(atRobot(rb1),loc00).
initially(atRobot(rb2),loc00).

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

initially(photoTaken(loc00),false).
initially(photoTaken_exp(loc00),false).
initially(photoTaken(loc01),false).
initially(photoTaken_exp(loc01),false).
initially(photoTaken(loc02),false).
initially(photoTaken_exp(loc02),false).
initially(photoTaken(loc03),false).
initially(photoTaken_exp(loc03),false).
initially(photoTaken(loc10),false).
initially(photoTaken_exp(loc10),false).
initially(photoTaken(loc11),false).
initially(photoTaken_exp(loc11),false).
initially(photoTaken(loc13),false).
initially(photoTaken_exp(loc13),false).
initially(photoTaken(loc20),false).
initially(photoTaken_exp(loc20),false).
initially(photoTaken(loc23),false).
initially(photoTaken_exp(loc23),false).
initially(photoTaken(loc30),false).
initially(photoTaken_exp(loc30),false).
initially(photoTaken(loc31),false).
initially(photoTaken_exp(loc31),false).
initially(photoTaken(loc32),false).
initially(photoTaken_exp(loc32),false).
initially(photoTaken(loc33),false).
initially(photoTaken_exp(loc33),false).

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

initially(batteryLevel(act1),0).
initially(batteryLevel(act2),0).
initially(batteryLevel(act3),0).
initially(batteryLevel(act4),0).
initially(batteryLevel(rb1),15).
initially(batteryLevel(rb2),15).

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

exog_action(recharge(SRVC,BATT)) :- service(SRVC),integer_type(BATT). 
causes_val(recharge(SRVC,BATT),batteryLevel(SRVC),L,and(provides(SRVC,battery),L is batteryLevel(SRVC)+BATT)). 

exog_action(locate(SRVC,LOC)) :- service(SRVC),location_type(LOC). 
causes_val(locate(SRVC,LOC),at(SRVC),LOC,provides(SRVC,movement)). 

exog_action(fire(LOC)) :- location_type(LOC). 
causes_val(fire(LOC),status(LOC),fire,true). 
causes_val(fire(_),exogenous,true,neg(adapting)).

exog_action(rockSlide(LOC)) :- location_type(LOC). 
causes_val(rockSlide(LOC),status(LOC),debris,true). 
causes_val(rockSlide(_),exogenous,true,neg(adapting)).

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

proc(relevant,or(some(srvc,and(service(srvc),and(provides(srvc,movement),or(neg(at(srvc)=at_exp(srvc)),neg(isConnected(srvc)))))),some(poi,and(location_type(poi),or(neg(evacuated_exp(poi)=evacuated(poi)),or(neg(photoTaken_exp(poi)=photoTaken(poi)),neg(status_exp(poi)=status(poi)))))))).

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

proc(manageExecution(X),[atomic([pi(n,[?(and(possible(n,X),and(free(n),capable(n,X)))), assign(n,X)])]),pi(n,[?(assigned(n,X)=true),executionHelp(n,X)]),atomic([pi(n,[?(assigned(n,X)=true),[release(n,X),printALL]])])]).

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
?(writeln(Stream,('hatchet - capability'))),	
?(writeln(Stream,('camera - capability'))),	
?(writeln(Stream,('gprs - capability'))),	
?(writeln(Stream,('extinguisher - capability'))),		
?(writeln(Stream,('battery - capability'))),	
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
?(writeln(Stream,('(provides act2 camera)'))),
?(writeln(Stream,('(provides act2 hatchet)'))),
?(writeln(Stream,('(provides act3 movement)'))),
?(writeln(Stream,('(provides act3 gprs)'))),
?(writeln(Stream,('(provides act3 camera)'))),
?(writeln(Stream,('(provides act3 hatchet)'))),
?(writeln(Stream,('(provides act4 movement)'))),	
?(writeln(Stream,('(provides act4 powerpack)'))),	
?(writeln(Stream,('(provides act4 gprs)'))),	
?(writeln(Stream,('(provides rb1 battery)'))),	
?(writeln(Stream,('(provides rb1 digger)'))),		
?(writeln(Stream,('(provides rb2 battery)'))),	
?(writeln(Stream,('(provides rb2 digger)'))),		
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
?(write(Stream,('('))),?(write(Stream,('atRobot rb1 '))), ?(write(Stream,(atRobot(rb1)))),?(writeln(Stream,(')'))),
?(write(Stream,('('))),?(write(Stream,('atRobot rb2 '))), ?(write(Stream,(atRobot(rb2)))),?(writeln(Stream,(')'))),
?(write(Stream,('(= '))),?(write(Stream,('(batteryLevel rb1) '))), ?(write(Stream,(batteryLevel(rb1)))),?(writeln(Stream,(')'))),
?(write(Stream,('(= '))),?(write(Stream,('(batteryLevel rb2) '))), ?(write(Stream,(batteryLevel(rb2)))),?(writeln(Stream,(')'))),
?(write(Stream,('(= '))),?(write(Stream,('(batteryRecharging) '))), ?(write(Stream,(batteryRecharging))),?(writeln(Stream,(')'))),
?(write(Stream,('(= '))),?(write(Stream,('(generalBattery) '))), ?(write(Stream,(generalBattery))),?(writeln(Stream,(')'))),
?(write(Stream,('(= '))),?(write(Stream,('(debrisStep) '))), ?(write(Stream,(debrisStep))),?(writeln(Stream,(')'))),
?(write(Stream,('(= '))),?(write(Stream,('(moveStep) '))), ?(write(Stream,(moveStep))),?(writeln(Stream,(')'))),
if(photoTaken(loc00)=true,?(writeln(Stream,('(photoTaken loc00)'))),[]),
if(photoTaken(loc01)=true,?(writeln(Stream,('(photoTaken loc01)'))),[]),
if(photoTaken(loc02)=true,?(writeln(Stream,('(photoTaken loc02)'))),[]),
if(photoTaken(loc03)=true,?(writeln(Stream,('(photoTaken loc03)'))),[]),
if(photoTaken(loc10)=true,?(writeln(Stream,('(photoTaken loc10)'))),[]),
if(photoTaken(loc11)=true,?(writeln(Stream,('(photoTaken loc11)'))),[]),
if(photoTaken(loc13)=true,?(writeln(Stream,('(photoTaken loc13)'))),[]),
if(photoTaken(loc20)=true,?(writeln(Stream,('(photoTaken loc20)'))),[]),
if(photoTaken(loc23)=true,?(writeln(Stream,('(photoTaken loc23)'))),[]),
if(photoTaken(loc30)=true,?(writeln(Stream,('(photoTaken loc30)'))),[]),
if(photoTaken(loc31)=true,?(writeln(Stream,('(photoTaken loc31)'))),[]),
if(photoTaken(loc32)=true,?(writeln(Stream,('(photoTaken loc32)'))),[]),
if(photoTaken(loc33)=true,?(writeln(Stream,('(photoTaken loc33)'))),[]),
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
if(photoTaken_exp(loc00)=true,?(writeln(Stream,('(photoTaken loc00)'))),[]),
if(photoTaken_exp(loc01)=true,?(writeln(Stream,('(photoTaken loc01)'))),[]),
if(photoTaken_exp(loc02)=true,?(writeln(Stream,('(photoTaken loc02)'))),[]),
if(photoTaken_exp(loc03)=true,?(writeln(Stream,('(photoTaken loc03)'))),[]),
if(photoTaken_exp(loc10)=true,?(writeln(Stream,('(photoTaken loc10)'))),[]),
if(photoTaken_exp(loc11)=true,?(writeln(Stream,('(photoTaken loc11)'))),[]),
if(photoTaken_exp(loc13)=true,?(writeln(Stream,('(photoTaken loc13)'))),[]),
if(photoTaken_exp(loc20)=true,?(writeln(Stream,('(photoTaken loc20)'))),[]),
if(photoTaken_exp(loc23)=true,?(writeln(Stream,('(photoTaken loc23)'))),[]),
if(photoTaken_exp(loc30)=true,?(writeln(Stream,('(photoTaken loc30)'))),[]),
if(photoTaken_exp(loc31)=true,?(writeln(Stream,('(photoTaken loc31)'))),[]),
if(photoTaken_exp(loc32)=true,?(writeln(Stream,('(photoTaken loc32)'))),[]),
if(photoTaken_exp(loc33)=true,?(writeln(Stream,('(photoTaken loc33)'))),[]),
?(writeln(Stream,('(isConnected act1)'))),
?(writeln(Stream,('(isConnected act2)'))),
?(writeln(Stream,('(isConnected act3)'))),
?(writeln(Stream,('(isConnected act4)'))),
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
	       ?(write('atRobot(rb1) = ')),?(writeln(atRobot(rb1))),
	       ?(write('atRobot(rb2) = ')),?(writeln(atRobot(rb2))),
	       ?(writeln('****BATTERY LEVEL****')),
	       ?(write('batteryLevel(rb1) = ')),?(writeln(batteryLevel(rb1))),
	       ?(write('batteryLevel(rb2) = ')),?(writeln(batteryLevel(rb2))),
	       ?(writeln('****GENERAL BATTERY****')),
	       ?(write('generalBattery = ')),?(writeln(generalBattery)),
	       ?(writeln('****MOVE STEP****')),
 	       ?(write('moveStep = ')),?(writeln(moveStep)),
	       ?(writeln('****DEBRIS STEP****')),
 	       ?(write('debrisStep = ')),?(writeln(debrisStep)),
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
	       ?(write('photoTaken(loc00) = ')),?(writeln(photoTaken(loc00))),
	       ?(write('photoTaken_exp(loc00) = ')),?(writeln(photoTaken_exp(loc00))),
	       ?(write('photoTaken(loc01) = ')),?(writeln(photoTaken(loc01))),
	       ?(write('photoTaken_exp(loc01) = ')),?(writeln(photoTaken_exp(loc01))),
	       ?(write('photoTaken(loc02) = ')),?(writeln(photoTaken(loc02))),
	       ?(write('photoTaken_exp(loc02) = ')),?(writeln(photoTaken_exp(loc02))),
	       ?(write('photoTaken(loc03) = ')),?(writeln(photoTaken(loc03))),
	       ?(write('photoTaken_exp(loc03) = ')),?(writeln(photoTaken_exp(loc03))),
	       ?(write('photoTaken(loc10) = ')),?(writeln(photoTaken(loc10))),
	       ?(write('photoTaken_exp(loc10) = ')),?(writeln(photoTaken_exp(loc10))),
	       ?(write('photoTaken(loc11) = ')),?(writeln(photoTaken(loc11))),
	       ?(write('photoTaken_exp(loc11) = ')),?(writeln(photoTaken_exp(loc11))),
	       ?(write('photoTaken(loc13) = ')),?(writeln(photoTaken(loc13))),
	       ?(write('photoTaken_exp(loc13) = ')),?(writeln(photoTaken_exp(loc13))),
	       ?(write('photoTaken(loc20) = ')),?(writeln(photoTaken(loc20))),
	       ?(write('photoTaken_exp(loc20) = ')),?(writeln(photoTaken_exp(loc20))),
	       ?(write('photoTaken(loc23) = ')),?(writeln(photoTaken(loc23))),
	       ?(write('photoTaken_exp(loc23) = ')),?(writeln(photoTaken_exp(loc23))),
	       ?(write('photoTaken(loc30) = ')),?(writeln(photoTaken(loc30))),
	       ?(write('photoTaken_exp(loc30) = ')),?(writeln(photoTaken_exp(loc30))),
	       ?(write('photoTaken(loc31) = ')),?(writeln(photoTaken(loc31))),
	       ?(write('photoTaken_exp(loc31) = ')),?(writeln(photoTaken_exp(loc31))),
	       ?(write('photoTaken(loc32) = ')),?(writeln(photoTaken(loc32))),
	       ?(write('photoTaken_exp(loc32) = ')),?(writeln(photoTaken_exp(loc32))),
	       ?(write('photoTaken(loc33) = ')),?(writeln(photoTaken(loc33))),
	       ?(write('photoTaken_exp(loc33) = ')),?(writeln(photoTaken_exp(loc33))),
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
	       ?(write('isConnected(act1) = ')),if(isConnected(act1),?(writeln(true)),?(writeln(false))),
 	       ?(write('isConnected(act2) = ')),if(isConnected(act2),?(writeln(true)),?(writeln(false))),
 	       ?(write('isConnected(act3) = ')),if(isConnected(act3),?(writeln(true)),?(writeln(false))),
 	       ?(write('isConnected(act4) = ')),if(isConnected(act4),?(writeln(true)),?(writeln(false)))
]).

