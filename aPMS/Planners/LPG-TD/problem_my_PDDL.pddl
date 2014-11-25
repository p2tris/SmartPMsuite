;In the GOAL definition the equal operator is not admitted

(define (problem EM1) (:domain Derailment)
(:objects
	act1 - service
	act2 - service
	act3 - service
	act4 - service
	rb1 - service
	rb2 - service
	ok - status_type
	fire - status_type
	debris - status_type
	movement - capability
	hatchet - capability
	camera - capability
	gprs - capability
	extinguisher - capability
	battery - capability
	digger - capability
	powerpack - capability
	loc00 - location_type
	loc01 - location_type
	loc02 - location_type
	loc03 - location_type
	loc10 - location_type
	loc11 - location_type
	loc13 - location_type
	loc20 - location_type
	loc23 - location_type
	loc30 - location_type
	loc31 - location_type
	loc32 - location_type
	loc33 - location_type	
	)
(:init
	(free act1)
	(free act2)
	(free act3)
	(free act4)
	(free rb1)
	(free rb2)

	(provides act1 movement)
	(provides act1 gprs)
	(provides act1 camera)
	(provides act1 extinguisher)
	(provides act2 movement)
	(provides act2 gprs)
	(provides act2 camera)
	(provides act2 hatchet)
	(provides act3 movement)
	(provides act3 gprs)
	(provides act3 camera)
	(provides act3 hatchet)
	(provides act4 movement)
	(provides act4 powerpack)
	(provides act4 gprs)
	(provides rb1 battery)
	(provides rb1 digger)
	(provides rb2 battery)
	(provides rb2 digger)

	(covered loc00)
	(covered loc10)
	(covered loc20)
	(covered loc01)
	(covered loc11)
	(covered loc02)

	(neigh loc00 loc10)
	(neigh loc00 loc11)
	(neigh loc00 loc01)
	(neigh loc11 loc10)
	(neigh loc11 loc01)
	(neigh loc11 loc00)
	(neigh loc11 loc20)
	(neigh loc11 loc02)
	(neigh loc10 loc20)
	(neigh loc10 loc00)
	(neigh loc10 loc11)
	(neigh loc10 loc01)
	(neigh loc01 loc02)
	(neigh loc01 loc11)
	(neigh loc01 loc10)
	(neigh loc01 loc00)
	(neigh loc02 loc03)
	(neigh loc02 loc13)
	(neigh loc02 loc01)
	(neigh loc02 loc11)
	(neigh loc03 loc02)
	(neigh loc03 loc13)
	(neigh loc13 loc03)
	(neigh loc13 loc23)
	(neigh loc13 loc02)
	(neigh loc23 loc13)
	(neigh loc23 loc33)
	(neigh loc23 loc32)
	(neigh loc33 loc23)
	(neigh loc33 loc32)
	(neigh loc32 loc33)
	(neigh loc32 loc23)
	(neigh loc32 loc31)
	(neigh loc31 loc32)
	(neigh loc31 loc20)
	(neigh loc31 loc30)
	(neigh loc30 loc31)
	(neigh loc30 loc20)
	(neigh loc20 loc30)
	(neigh loc20 loc31)
	(neigh loc20 loc10)
	(neigh loc20 loc11)

	(at act1 loc00)
	(at act2 loc00)
	(at act3 loc00)
	(at act4 loc00)
	(atRobot rb1 loc00)
	(atRobot rb2 loc00)
	(= (batteryLevel rb1) 10)
	(= (batteryLevel rb2) 10)
	(= (batteryRecharging) 10)
	(= (generalBattery) 30)
	(= (debrisStep) 2)
	(= (moveStep) 3)	

	(status loc00 ok)
	(status loc01 ok)
	(status loc02 ok)
	(status loc03 ok)
	(status loc10 ok)
	(status loc11 ok)
	(status loc13 ok)	
	(status loc20 ok)
	(status loc23 ok)
	(status loc30 ok)
	(status loc31 ok)
	(status loc32 ok)	
	(status loc33 ok)	
	(evacuated loc00)
	(evacuated loc01)
	(evacuated loc02)
	(evacuated loc03)
	(evacuated loc10)
	(evacuated loc11)
	(evacuated loc13)	
	(evacuated loc20)
	(evacuated loc23)
	(evacuated loc30)
	(evacuated loc31)
	(evacuated loc32)
	(evacuated loc33)
	)
(:goal 
	(and
	(at act1 loc33)
	(at act2 loc00)
	(at act3 loc01)
	(at act4 loc00)
	(isConnected act1)
	(isConnected act2)
	(isConnected act3)
	(isConnected act4)
))
(:metric minimize (total-time))
)
