(defrule grid-values

   ?f <- (phase grid-values)

   =>
   
   (retract ?f)
   
   (assert (phase expand-any))

   (assert (size 3))
   
   (assert (possible (row 1) (column 1) (value any) (diagonal 1) (group 1) (id 1)))
   (assert (possible (row 1) (column 2) (value 2) (diagonal 0) (group 1) (id 2)))
   (assert (possible (row 1) (column 3) (value any) (diagonal 0) (group 1) (id 3)))
   (assert (possible (row 2) (column 1) (value any) (diagonal 0) (group 1) (id 4)))
   (assert (possible (row 2) (column 2) (value any) (diagonal 1) (group 1) (id 5)))
   (assert (possible (row 2) (column 3) (value any) (diagonal 0) (group 1) (id 6)))     

   (assert (possible (row 1) (column 4) (value any) (diagonal 0) (group 2) (id 7)))
   (assert (possible (row 1) (column 5) (value 5) (diagonal 0) (group 2) (id 8)))
   (assert (possible (row 1) (column 6) (value any) (diagonal 2) (group 2) (id 9)))
   (assert (possible (row 2) (column 4) (value 2) (diagonal 0) (group 2) (id 10)))
   (assert (possible (row 2) (column 5) (value any) (diagonal 2) (group 2) (id 11)))
   (assert (possible (row 2) (column 6) (value any) (diagonal 0) (group 2) (id 12)))

   (assert (possible (row 3) (column 1) (value any) (diagonal 0) (group 3) (id 13)))
   (assert (possible (row 3) (column 2) (value any) (diagonal 0) (group 3) (id 14)))
   (assert (possible (row 3) (column 3) (value 1) (diagonal 1) (group 3) (id 15)))
   (assert (possible (row 4) (column 1) (value any) (diagonal 0) (group 3) (id 16)))
   (assert (possible (row 4) (column 2) (value any) (diagonal 0) (group 3) (id 17)))
   (assert (possible (row 4) (column 3) (value any) (diagonal 2) (group 3) (id 18)))   

   (assert (possible (row 3) (column 4) (value 5) (diagonal 2) (group 4) (id 19)))
   (assert (possible (row 3) (column 5) (value any) (diagonal 0) (group 4) (id 20)))
   (assert (possible (row 3) (column 6) (value any) (diagonal 0) (group 4) (id 21)))
   (assert (possible (row 4) (column 4) (value any) (diagonal 1) (group 4) (id 22)))
   (assert (possible (row 4) (column 5) (value any) (diagonal 0) (group 4) (id 23)))
   (assert (possible (row 4) (column 6) (value 3) (diagonal 0) (group 4) (id 24)))  
  
   (assert (possible (row 5) (column 1) (value any) (diagonal 0) (group 5) (id 25)))
   (assert (possible (row 5) (column 2) (value any) (diagonal 2) (group 5) (id 26)))
   (assert (possible (row 5) (column 3) (value 3) (diagonal 0) (group 5) (id 27)))
   (assert (possible (row 6) (column 1) (value 6) (diagonal 2) (group 5) (id 28)))
   (assert (possible (row 6) (column 2) (value any) (diagonal 0) (group 5) (id 29)))
   (assert (possible (row 6) (column 3) (value any) (diagonal 0) (group 5) (id 30)))
   
   (assert (possible (row 5) (column 4) (value any) (diagonal 0) (group 6) (id 31)))
   (assert (possible (row 5) (column 5) (value any) (diagonal 1) (group 6) (id 32)))
   (assert (possible (row 5) (column 6) (value any) (diagonal 0) (group 6) (id 33)))
   (assert (possible (row 6) (column 4) (value any) (diagonal 0) (group 6) (id 34)))
   (assert (possible (row 6) (column 5) (value 4) (diagonal 0) (group 6) (id 35)))
   (assert (possible (row 6) (column 6) (value any) (diagonal 1) (group 6) (id 36)))
 )   
