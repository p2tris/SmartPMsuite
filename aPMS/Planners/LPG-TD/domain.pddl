;We cannot use integer or boolean parameters as inputs

(define (domain Derailment)
(:requirements :derived-predicates :typing :fluents :equality)
(:types robot actor - service 
	capability location_type status_type mq2_type mq3_type mq5_type hum_type temp_type noize_type)

(:predicates 
(free ?x - service)
(provides ?x - service ?c - capability)
(neigh ?y1 - location_type ?y2 - location_type)
(covered ?y - location_type)
(at ?x - service ?y - location_type)
(status ?y - location_type ?s - status_type)
(evacuated ?y - location_type)
(phototaken ?l - location_type)
(isconnected ?x - service)
)

(:functions
(batterylevel ?x - robot)
(movestep)
(debrisstep)
(generalbattery)
(batteryrecharging)
)

(:action go
:parameters (?x - actor ?from - location_type ?to - location_type) 
:precondition (and (free ?x) (provides ?x movement) (at ?x ?from) (isconnected ?x))
:effect (and (not (at ?x ?from)) (at ?x ?to))
)

(:action move
:parameters (?x - robot ?from - location_type ?to - location_type) 
:precondition (and (free ?x) (provides ?x battery) (at ?x ?from) (>= (batterylevel ?x) (movestep)))
:effect (and (not (at ?x ?from)) (at ?x ?to) (decrease (batterylevel ?x) (movestep)))
)

(:action extinguishfire
:parameters (?x - actor ?y - location_type)
:precondition (and (provides ?x extinguisher) (free ?x) (at ?x ?y) (status ?y fire) (isconnected ?x))
:effect (and (status ?y ok) (not (status ?y fire)))
)

(:action evacuate
:parameters (?x - actor ?y - location_type)
:precondition (and (provides ?x hatchet) (free ?x) (at ?x ?y) (status ?y ok) (not (evacuated ?y)) (isconnected ?x))
:effect (evacuated ?y)
)

(:action removedebris
:parameters (?x - robot ?y - location_type)
:precondition (and (provides ?x digger) (free ?x) (at ?x ?y) (status ?y debris) (>= (batterylevel ?x) (debrisstep)))
:effect (and (status ?y ok) (not (status ?y debris)) (decrease (batterylevel ?x) (debrisstep)))
)

(:action chargebattery
:parameters (?x1 - actor ?x2 - robot ?y - location_type)
:precondition (and (provides ?x1 powerpack) (free ?x1) (at ?x1 ?y) (at ?x2 ?y)  (>= (generalbattery) (batteryrecharging)) (isconnected ?x1))
:effect (and (decrease (generalbattery) (batteryrecharging)) (increase (batterylevel ?x2) (batteryrecharging)))
)

(:action updatestatus
:parameters (?x - actor ?y - location_type)
:precondition (and (provides ?x gprs) (free ?x) (at ?x ?y) (status ?y ok) (isconnected ?x))
:effect (status ?y ok)
)

(:action takephoto
:parameters (?x - actor ?y - location_type)
:precondition (and (provides ?x camera) (free ?x) (at ?x ?y) (not (phototaken ?y)) (isconnected ?x))
:effect (phototaken ?y)
)

(:derived (isconnected ?x - actor) 
	(or (exists (?y - location_type) (and (at ?x ?y) (covered ?y)))	
            (exists (?r - robot ?y - location_type ?z - location_type) (and (at ?x ?y) (at ?r ?z) 
									      (or (neigh ?y ?z) (= ?y ?z))))
									      )
									  
)

)
