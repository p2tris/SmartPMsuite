;We cannot use integer or boolean parameters as inputs

(define (domain Derailment)
(:requirements :derived-predicates :typing :fluents :equality)
(:types robot actor - service 
	capability location_type status_type)

(:predicates 
(free ?x - service)
(provides ?x - service ?c - capability)
(neigh ?y1 - location_type ?y2 - location_type)
(covered ?y - location_type)
(at ?x - actor ?y - location_type)
(atRobot ?x - robot ?y - location_type)
(status ?y - location_type ?s - status_type)
(evacuated ?y - location_type)
(photoTaken ?l - location_type)
(isConnected ?x - service)
)

(:functions
(batteryLevel ?x - robot)
(moveStep)
(debrisStep)
(generalBattery)
(batteryRecharging)
)

(:action go
:parameters (?x - actor ?from - location_type ?to - location_type) 
:precondition (and (provides ?x movement) (free ?x) (at ?x ?from) (isConnected ?x))
:effect (and (not (at ?x ?from)) (at ?x ?to))
)

(:action move
:parameters (?x - robot ?from - location_type ?to - location_type) 
:precondition (and (provides ?x battery) (free ?x) (atRobot ?x ?from) (>= (batteryLevel ?x) (moveStep)))
:effect (and (not (atRobot ?x ?from)) (atRobot ?x ?to) (decrease (batteryLevel ?x) (moveStep)))
)

(:action extinguishfire
:parameters (?x - actor ?y - location_type)
:precondition (and (provides ?x extinguisher) (free ?x) (at ?x ?y) (status ?y fire) (isConnected ?x))
:effect (status ?y ok)
)

(:action evacuate
:parameters (?x - actor ?y - location_type)
:precondition (and (provides ?x hatchet) (free ?x) (at ?x ?y) (status ?y ok) (not (evacuated ?y)) (isConnected ?x))
:effect (evacuated ?y)
)

(:action removedebris
:parameters (?x - robot ?y - location_type)
:precondition (and (provides ?x digger) (free ?x) (atRobot ?x ?y) (status ?y debris) (>= (batteryLevel ?x) (debrisStep)))
:effect (and (status ?y ok) (decrease (batteryLevel ?x) (debrisStep)))
)

(:action chargebattery
:parameters (?x1 - actor ?x2 - robot ?y - location_type)
:precondition (and (provides ?x1 powerpack) (free ?x1) (at ?x1 ?y) (provides ?x2 battery) (atRobot ?x2 ?y)  (>= (generalBattery) (batteryRecharging)) (isConnected ?x1))
:effect (and (decrease (generalBattery) (batteryRecharging)) (increase (batteryLevel ?x2) (batteryRecharging)))
)

(:action updatestatus
:parameters (?x - actor ?y - location_type)
:precondition (and (provides ?x gprs) (free ?x) (at ?x ?y) (status ?y ok) (isConnected ?x))
:effect (status ?y ok)
)

(:action takephoto
:parameters (?x - actor ?y - location_type)
:precondition (and (provides ?x camera) (free ?x) (at ?x ?y) (isConnected ?x))
:effect (photoTaken ?y)
)

(:derived (isConnected ?x - actor) 
	(and (provides ?x movement)
	(or (exists (?y - location_type) (and (at ?x ?y) (covered ?y)))	
            (exists (?r - robot ?y - location_type ?z - location_type) (and (provides ?r battery) (at ?x ?y) (atRobot ?r ?z) 
									      (or (neigh ?y ?z) (= ?y ?z))))
									      )
									  )
)

)
