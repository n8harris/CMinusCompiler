(FUNCTION  quicksort  [(int x) (int first) (int last)]
  (BB 1
    (OPER 3 Func_Entry []  [])
  )
  (BB 2
    (OPER 4 LT [(r 8)]  [(r 2)(r 3)])
    (OPER 5 BEQ []  [(r 8)(i 0)(bb 5)])
  )
  (BB 3
    (OPER 6 Mov [(r 4)]  [(r 2)])
    (OPER 7 Mov [(r 7)]  [(r 2)])
    (OPER 8 Mov [(r 5)]  [(r 3)])
    (OPER 9 LT [(r 9)]  [(r 7)(r 5)])
    (OPER 10 BEQ []  [(r 9)(i 0)(bb 7)])
  )
  (BB 6
    (OPER 11 LTE [(r 10)]  [(r 1)(r 7)])
    (OPER 12 BEQ []  [(r 10)(i 0)(bb 9)])
  )
  (BB 8
    (OPER 13 Add_I [(r 12)]  [(r 7)(r 11)])
    (OPER 14 Mov [(r 7)]  [(r 12)])
    (OPER 15 LTE [(r 13)]  [(r 1)(r 7)])
    (OPER 16 BNE []  [(r 10)(i 0)(bb 9)])
  )
  (BB 9
    (OPER 17 Add_I [(r 14)]  [(r 5)(r 5)])
    (OPER 18 GT [(r 15)]  [(r 1)(r 14)])
    (OPER 19 BEQ []  [(r 15)(i 0)(bb 11)])
  )
  (BB 10
    (OPER 20 Add_I [(r 17)]  [(r 5)(r 16)])
    (OPER 21 Mov [(r 5)]  [(r 17)])
    (OPER 22 Add_I [(r 18)]  [(r 5)(r 5)])
    (OPER 23 GT [(r 19)]  [(r 1)(r 18)])
    (OPER 24 BNE []  [(r 15)(i 0)(bb 11)])
  )
  (BB 11
    (OPER 25 LT [(r 20)]  [(r 7)(r 5)])
    (OPER 26 BEQ []  [(r 20)(i 0)(bb 13)])
  )
  (BB 12
    (OPER 27 Mov [(r 6)]  [(r 1)])
    (OPER 28 Mov [(r 7)]  [(r 5)])
    (OPER 29 Mov [(r 1)]  [(r 6)])
  )
  (BB 13
    (OPER 30 LT [(r 21)]  [(r 7)(r 5)])
    (OPER 31 BNE []  [(r 9)(i 0)(bb 7)])
  )
  (BB 7
    (OPER 32 Mov [(r 6)]  [(r 1)])
    (OPER 33 Mov [(r 1)]  [(r 5)])
    (OPER 34 Mov [(r 5)]  [(r 6)])
    (OPER 35 Sub_I [(r 23)]  [(r 5)(r 22)])
    (OPER 36 Pass []  [(r 23)])
    (OPER 37 Pass []  [(r 2)])
    (OPER 38 Pass []  [(r 1)])
    (OPER 39 JSR []  [(s quicksort)])
    (OPER 40 Mov [(r 24)]  [(m RetReg)])
    (OPER 41 Pass []  [(r 3)])
    (OPER 42 Add_I [(r 26)]  [(r 5)(r 25)])
    (OPER 43 Pass []  [(r 26)])
    (OPER 44 Pass []  [(r 1)])
    (OPER 45 JSR []  [(s quicksort)])
    (OPER 46 Mov [(r 27)]  [(m RetReg)])
  )
  (BB 4
  )
  (BB 0
    (OPER 1 Func_Exit []  [])
    (OPER 2 Return []  [(m RetReg)])
  )
)