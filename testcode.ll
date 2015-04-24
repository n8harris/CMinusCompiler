(DATA  a)
(FUNCTION  addThem  [(int d) (int e)]
  (BB 2
    (OPER 3 Func_Entry []  [])
  )
  (BB 3
    (OPER 4 Add_I [(r 4)]  [(r 1)(r 2)])
    (OPER 5 Mov [(r 3)]  [(r 4)])
    (OPER 6 Mov [(m RetReg)]  [(r 3)])
    (OPER 7 Jmp []  [(bb 1)])
  )
  (BB 1
    (OPER 1 Func_Exit []  [])
    (OPER 2 Return []  [(m RetReg)])
  )
)
(FUNCTION  putDigit  [(int s)]
  (BB 2
    (OPER 3 Func_Entry []  [])
  )
  (BB 3
    (OPER 4 Mov [(r 2)]  [(i 48)])
    (OPER 5 Add_I [(r 3)]  [(r 2)(r 1)])
    (OPER 6 Pass []  [(r 3)])
    (OPER 7 JSR []  [(s putchar)])
    (OPER 8 Mov [(r 4)]  [(m RetReg)])
  )
  (BB 1
    (OPER 1 Func_Exit []  [])
    (OPER 2 Return []  [(m RetReg)])
  )
)
(FUNCTION  printInt  [(int r)]
  (BB 2
    (OPER 3 Func_Entry []  [])
  )
  (BB 3
    (OPER 4 Mov [(r 4)]  [(i 0)])
    (OPER 5 Mov [(r 3)]  [(r 4)])
    (OPER 6 Mov [(r 5)]  [(i 10000)])
    (OPER 7 GTE [(r 6)]  [(r 1)(r 5)])
    (OPER 8 BEQ []  [(r 6)(i 0)(bb 6)])
  )
  (BB 4
    (OPER 9 Mov [(r 7)]  [(i 45)])
    (OPER 10 Pass []  [(r 7)])
    (OPER 11 JSR []  [(s putchar)])
    (OPER 12 Mov [(r 8)]  [(m RetReg)])
    (OPER 13 Mov [(r 9)]  [(i 1)])
    (OPER 14 Pass []  [(r 9)])
    (OPER 15 JSR []  [(s putDigit)])
    (OPER 16 Mov [(r 10)]  [(m RetReg)])
    (OPER 17 Jmp []  [(bb 1)])
  )
  (BB 5
  )
  (BB 1
    (OPER 1 Func_Exit []  [])
    (OPER 2 Return []  [(m RetReg)])
  )
  (BB 11
    (OPER 48 Mov [(r 29)]  [(i 1)])
    (OPER 49 EQ [(r 30)]  [(r 3)(r 29)])
    (OPER 50 BEQ []  [(r 30)(i 0)(bb 13)])
  )
  (BB 12
    (OPER 51 Mov [(r 31)]  [(i 0)])
    (OPER 52 Pass []  [(r 31)])
    (OPER 53 JSR []  [(s putDigit)])
    (OPER 54 Mov [(r 32)]  [(m RetReg)])
  )
  (BB 13
    (OPER 55 Jmp []  [(bb 10)])
  )
  (BB 16
    (OPER 69 Mov [(r 41)]  [(i 1)])
    (OPER 70 EQ [(r 42)]  [(r 3)(r 41)])
    (OPER 71 BEQ []  [(r 42)(i 0)(bb 18)])
  )
  (BB 17
    (OPER 72 Mov [(r 43)]  [(i 0)])
    (OPER 73 Pass []  [(r 43)])
    (OPER 74 JSR []  [(s putDigit)])
    (OPER 75 Mov [(r 44)]  [(m RetReg)])
  )
  (BB 18
    (OPER 76 Jmp []  [(bb 15)])
  )
  (BB 6
    (OPER 18 Mov [(r 11)]  [(i 1000)])
    (OPER 19 GTE [(r 12)]  [(r 1)(r 11)])
    (OPER 20 BEQ []  [(r 12)(i 0)(bb 8)])
  )
  (BB 7
    (OPER 21 Mov [(r 13)]  [(i 1000)])
    (OPER 22 Div_I [(r 14)]  [(r 1)(r 13)])
    (OPER 23 Mov [(r 2)]  [(r 14)])
    (OPER 24 Pass []  [(r 2)])
    (OPER 25 JSR []  [(s putDigit)])
    (OPER 26 Mov [(r 15)]  [(m RetReg)])
    (OPER 27 Mov [(r 16)]  [(i 1000)])
    (OPER 28 Mul_I [(r 17)]  [(r 2)(r 16)])
    (OPER 29 Sub_I [(r 18)]  [(r 1)(r 17)])
    (OPER 30 Mov [(r 1)]  [(r 18)])
    (OPER 31 Mov [(r 19)]  [(i 1)])
    (OPER 32 Mov [(r 3)]  [(r 19)])
  )
  (BB 8
    (OPER 33 Mov [(r 20)]  [(i 100)])
    (OPER 34 GTE [(r 21)]  [(r 1)(r 20)])
    (OPER 35 BEQ []  [(r 21)(i 0)(bb 11)])
  )
  (BB 9
    (OPER 36 Mov [(r 22)]  [(i 100)])
    (OPER 37 Div_I [(r 23)]  [(r 1)(r 22)])
    (OPER 38 Mov [(r 2)]  [(r 23)])
    (OPER 39 Pass []  [(r 2)])
    (OPER 40 JSR []  [(s putDigit)])
    (OPER 41 Mov [(r 24)]  [(m RetReg)])
    (OPER 42 Mov [(r 25)]  [(i 100)])
    (OPER 43 Mul_I [(r 26)]  [(r 2)(r 25)])
    (OPER 44 Sub_I [(r 27)]  [(r 1)(r 26)])
    (OPER 45 Mov [(r 1)]  [(r 27)])
    (OPER 46 Mov [(r 28)]  [(i 1)])
    (OPER 47 Mov [(r 3)]  [(r 28)])
  )
  (BB 10
    (OPER 56 Mov [(r 33)]  [(i 10)])
    (OPER 57 GTE [(r 34)]  [(r 1)(r 33)])
    (OPER 58 BEQ []  [(r 34)(i 0)(bb 16)])
  )
  (BB 14
    (OPER 59 Mov [(r 35)]  [(i 10)])
    (OPER 60 Div_I [(r 36)]  [(r 1)(r 35)])
    (OPER 61 Mov [(r 2)]  [(r 36)])
    (OPER 62 Pass []  [(r 2)])
    (OPER 63 JSR []  [(s putDigit)])
    (OPER 64 Mov [(r 37)]  [(m RetReg)])
    (OPER 65 Mov [(r 38)]  [(i 10)])
    (OPER 66 Mul_I [(r 39)]  [(r 2)(r 38)])
    (OPER 67 Sub_I [(r 40)]  [(r 1)(r 39)])
    (OPER 68 Mov [(r 1)]  [(r 40)])
  )
  (BB 15
    (OPER 77 Pass []  [(r 1)])
    (OPER 78 JSR []  [(s putDigit)])
    (OPER 79 Mov [(r 45)]  [(m RetReg)])
    (OPER 80 Jmp []  [(bb 5)])
  )
)
(FUNCTION  main  []
  (BB 2
    (OPER 3 Func_Entry []  [])
  )
  (BB 3
    (OPER 4 Mov [(r 6)]  [(i 5)])
    (OPER 5 Mov [(r 1)]  [(r 6)])
    (OPER 6 Mov [(r 7)]  [(i 5)])
    (OPER 7 Mov [(r 2)]  [(r 7)])
    (OPER 8 Mov [(r 8)]  [(i 5)])
    (OPER 9 EQ [(r 9)]  [(r 1)(r 8)])
    (OPER 10 BEQ []  [(r 9)(i 0)(bb 6)])
  )
  (BB 4
    (OPER 11 Mov [(r 10)]  [(i 3)])
    (OPER 12 Store []  [(r 10)(s a)])
  )
  (BB 5
    (OPER 16 Mov [(r 12)]  [(i 0)])
    (OPER 17 Mov [(r 3)]  [(r 12)])
    (OPER 18 Mov [(r 13)]  [(i 1)])
    (OPER 19 Mov [(r 5)]  [(r 13)])
    (OPER 20 Mov [(r 14)]  [(i 8)])
    (OPER 21 LTE [(r 15)]  [(r 5)(r 14)])
    (OPER 22 BEQ []  [(r 15)(i 0)(bb 8)])
  )
  (BB 7
    (OPER 23 Add_I [(r 16)]  [(r 3)(r 5)])
    (OPER 24 Mov [(r 3)]  [(r 16)])
    (OPER 25 Mov [(r 17)]  [(i 1)])
    (OPER 26 Add_I [(r 18)]  [(r 5)(r 17)])
    (OPER 27 Mov [(r 5)]  [(r 18)])
    (OPER 28 Mov [(r 19)]  [(i 8)])
    (OPER 29 LTE [(r 20)]  [(r 5)(r 19)])
    (OPER 30 BNE []  [(r 20)(i 0)(bb 7)])
  )
  (BB 8
    (OPER 31 Mov [(r 21)]  [(i 3)])
    (OPER 32 Div_I [(r 22)]  [(r 3)(r 21)])
    (OPER 33 Mov [(r 4)]  [(r 22)])
    (OPER 34 Mov [(r 23)]  [(i 4)])
    (OPER 35 Mul_I [(r 24)]  [(r 4)(r 23)])
    (OPER 36 Mov [(r 3)]  [(r 24)])
    (OPER 37 Load [(r 25)]  [(s a)])
    (OPER 38 Pass []  [(r 25)])
    (OPER 39 Pass []  [(r 1)])
    (OPER 40 JSR []  [(s addThem)])
    (OPER 41 Mov [(r 26)]  [(m RetReg)])
    (OPER 42 Mov [(r 2)]  [(r 26)])
    (OPER 43 Mov [(r 27)]  [(i 56)])
    (OPER 44 Pass []  [(r 27)])
    (OPER 45 JSR []  [(s putchar)])
    (OPER 46 Mov [(r 28)]  [(m RetReg)])
    (OPER 47 Mov [(r 29)]  [(i 61)])
    (OPER 48 Pass []  [(r 29)])
    (OPER 49 JSR []  [(s putchar)])
    (OPER 50 Mov [(r 30)]  [(m RetReg)])
    (OPER 51 Add_I [(r 31)]  [(r 2)(r 3)])
    (OPER 52 Pass []  [(r 31)])
    (OPER 53 JSR []  [(s putchar)])
    (OPER 54 Mov [(r 32)]  [(m RetReg)])
    (OPER 55 Mov [(r 33)]  [(i 10)])
    (OPER 56 Pass []  [(r 33)])
    (OPER 57 JSR []  [(s putchar)])
    (OPER 58 Mov [(r 34)]  [(m RetReg)])
    (OPER 59 Mov [(r 35)]  [(i 0)])
    (OPER 60 Mov [(r 5)]  [(r 35)])
    (OPER 61 Mov [(r 36)]  [(i 10)])
    (OPER 62 LT [(r 37)]  [(r 5)(r 36)])
    (OPER 63 BEQ []  [(r 37)(i 0)(bb 10)])
  )
  (BB 9
    (OPER 64 Mov [(r 38)]  [(i 48)])
    (OPER 65 Add_I [(r 39)]  [(r 38)(r 5)])
    (OPER 66 Pass []  [(r 39)])
    (OPER 67 JSR []  [(s putchar)])
    (OPER 68 Mov [(r 40)]  [(m RetReg)])
    (OPER 69 Mov [(r 41)]  [(i 1)])
    (OPER 70 Add_I [(r 42)]  [(r 5)(r 41)])
    (OPER 71 Mov [(r 5)]  [(r 42)])
    (OPER 72 Mov [(r 43)]  [(i 10)])
    (OPER 73 LT [(r 44)]  [(r 5)(r 43)])
    (OPER 74 BNE []  [(r 44)(i 0)(bb 9)])
  )
  (BB 10
    (OPER 75 Mov [(r 45)]  [(i 10)])
    (OPER 76 Pass []  [(r 45)])
    (OPER 77 JSR []  [(s putchar)])
    (OPER 78 Mov [(r 46)]  [(m RetReg)])
    (OPER 79 Mov [(r 47)]  [(i 67)])
    (OPER 80 Pass []  [(r 47)])
    (OPER 81 JSR []  [(s putchar)])
    (OPER 82 Mov [(r 48)]  [(m RetReg)])
    (OPER 83 Mov [(r 49)]  [(i 83)])
    (OPER 84 Pass []  [(r 49)])
    (OPER 85 JSR []  [(s putchar)])
    (OPER 86 Mov [(r 50)]  [(m RetReg)])
    (OPER 87 Mov [(r 51)]  [(i 3510)])
    (OPER 88 Pass []  [(r 51)])
    (OPER 89 JSR []  [(s printInt)])
    (OPER 90 Mov [(r 52)]  [(m RetReg)])
    (OPER 91 Mov [(r 53)]  [(i 10)])
    (OPER 92 Pass []  [(r 53)])
    (OPER 93 JSR []  [(s putchar)])
    (OPER 94 Mov [(r 54)]  [(m RetReg)])
    (OPER 95 Mov [(r 55)]  [(i 0)])
    (OPER 96 Mov [(r 1)]  [(r 55)])
    (OPER 97 Mov [(r 56)]  [(i 1)])
    (OPER 98 Mov [(r 2)]  [(r 56)])
    (OPER 99 Mov [(r 57)]  [(i 1)])
    (OPER 100 Mov [(r 3)]  [(r 57)])
    (OPER 101 Mov [(r 58)]  [(i 0)])
    (OPER 102 Mov [(r 4)]  [(r 58)])
    (OPER 103 Mov [(r 59)]  [(i 0)])
    (OPER 104 Mov [(r 5)]  [(r 59)])
    (OPER 105 Mov [(r 60)]  [(i 0)])
    (OPER 106 EQ [(r 61)]  [(r 1)(r 60)])
    (OPER 107 BEQ []  [(r 61)(i 0)(bb 13)])
  )
  (BB 11
    (OPER 108 Mov [(r 62)]  [(i 0)])
    (OPER 109 EQ [(r 63)]  [(r 2)(r 62)])
    (OPER 110 BEQ []  [(r 63)(i 0)(bb 16)])
  )
  (BB 14
    (OPER 111 Mov [(r 64)]  [(i 1)])
    (OPER 112 Mov [(r 5)]  [(r 64)])
  )
  (BB 15
  )
  (BB 12
    (OPER 131 Mov [(r 73)]  [(i 10)])
    (OPER 132 EQ [(r 74)]  [(r 5)(r 73)])
    (OPER 133 BEQ []  [(r 74)(i 0)(bb 25)])
  )
  (BB 23
    (OPER 134 Mov [(r 75)]  [(i 99)])
    (OPER 135 Pass []  [(r 75)])
    (OPER 136 JSR []  [(s putchar)])
    (OPER 137 Mov [(r 76)]  [(m RetReg)])
    (OPER 138 Mov [(r 77)]  [(i 0)])
    (OPER 139 Pass []  [(r 77)])
    (OPER 140 JSR []  [(s putDigit)])
    (OPER 141 Mov [(r 78)]  [(m RetReg)])
    (OPER 142 Mov [(r 79)]  [(i 0)])
    (OPER 143 Pass []  [(r 79)])
    (OPER 144 JSR []  [(s putDigit)])
    (OPER 145 Mov [(r 80)]  [(m RetReg)])
    (OPER 146 Mov [(r 81)]  [(i 108)])
    (OPER 147 Pass []  [(r 81)])
    (OPER 148 JSR []  [(s putchar)])
    (OPER 149 Mov [(r 82)]  [(m RetReg)])
  )
  (BB 24
    (OPER 170 Mov [(r 92)]  [(i 10)])
    (OPER 171 Pass []  [(r 92)])
    (OPER 172 JSR []  [(s putchar)])
    (OPER 173 Mov [(r 93)]  [(m RetReg)])
    (OPER 174 Mov [(r 94)]  [(i 0)])
    (OPER 175 Mov [(m RetReg)]  [(r 94)])
    (OPER 176 Jmp []  [(bb 1)])
  )
  (BB 1
    (OPER 1 Func_Exit []  [])
    (OPER 2 Return []  [(m RetReg)])
  )
  (BB 6
    (OPER 13 Mov [(r 11)]  [(i 4)])
    (OPER 14 Store []  [(r 11)(s a)])
    (OPER 15 Jmp []  [(bb 5)])
  )
  (BB 22
    (OPER 123 Mov [(r 71)]  [(i 3)])
    (OPER 124 Mov [(r 5)]  [(r 71)])
    (OPER 125 Jmp []  [(bb 21)])
  )
  (BB 19
    (OPER 118 Mov [(r 68)]  [(i 0)])
    (OPER 119 EQ [(r 69)]  [(r 4)(r 68)])
    (OPER 120 BEQ []  [(r 69)(i 0)(bb 22)])
  )
  (BB 20
    (OPER 121 Mov [(r 70)]  [(i 10)])
    (OPER 122 Mov [(r 5)]  [(r 70)])
  )
  (BB 21
    (OPER 126 Jmp []  [(bb 18)])
  )
  (BB 16
    (OPER 113 Mov [(r 65)]  [(i 0)])
    (OPER 114 EQ [(r 66)]  [(r 3)(r 65)])
    (OPER 115 BEQ []  [(r 66)(i 0)(bb 19)])
  )
  (BB 17
    (OPER 116 Mov [(r 67)]  [(i 2)])
    (OPER 117 Mov [(r 5)]  [(r 67)])
  )
  (BB 18
    (OPER 127 Jmp []  [(bb 15)])
  )
  (BB 13
    (OPER 128 Mov [(r 72)]  [(i 0)])
    (OPER 129 Mov [(r 5)]  [(r 72)])
    (OPER 130 Jmp []  [(bb 12)])
  )
  (BB 25
    (OPER 150 Mov [(r 83)]  [(i 98)])
    (OPER 151 Pass []  [(r 83)])
    (OPER 152 JSR []  [(s putchar)])
    (OPER 153 Mov [(r 84)]  [(m RetReg)])
    (OPER 154 Mov [(r 85)]  [(i 97)])
    (OPER 155 Pass []  [(r 85)])
    (OPER 156 JSR []  [(s putchar)])
    (OPER 157 Mov [(r 86)]  [(m RetReg)])
    (OPER 158 Mov [(r 87)]  [(i 100)])
    (OPER 159 Pass []  [(r 87)])
    (OPER 160 JSR []  [(s putchar)])
    (OPER 161 Mov [(r 88)]  [(m RetReg)])
    (OPER 162 Mov [(r 89)]  [(i 61)])
    (OPER 163 Pass []  [(r 89)])
    (OPER 164 JSR []  [(s putchar)])
    (OPER 165 Mov [(r 90)]  [(m RetReg)])
    (OPER 166 Pass []  [(r 5)])
    (OPER 167 JSR []  [(s printInt)])
    (OPER 168 Mov [(r 91)]  [(m RetReg)])
    (OPER 169 Jmp []  [(bb 24)])
  )
)
