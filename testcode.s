.data
.comm	a,4,4

.text
	.align 4
.globl  addThem
addThem:
addThem_bb1:
	movl	%EDI, %EAX
	movl	%ESI, %EDI
addThem_bb2:
	addl	%EDI, %EAX
addThem_bb3:
	ret
.globl  putDigit
putDigit:
putDigit_bb1:
putDigit_bb2:
	movl	$48, %EAX
	addl	%EDI, %EAX
	movl	%EAX, %ECX
	call	putchar
	ret
.globl  printInt
printInt:
printInt_bb1:
	pushq	%R14
	pushq	%R15
	movl	%EDI, %R15D
printInt_bb2:
	movl	$0, %EAX
	movl	%EAX, %R14D
	movl	$10000, %EAX
	cmpl	%EAX, %R15D
	jl	printInt_bb5
printInt_bb3:
	movl	$45, %EAX
	pushq	%RAX
	call	putchar
	movl	$1, %EAX
	pushq	%RAX
	call	putDigit
printInt_bb6:
	popq	%R14
	popq	%R15
	ret
printInt_bb11:
	movl	$1, %EAX
	cmpl	%EAX, %R14D
	jne	printInt_bb10
printInt_bb12:
	movl	$0, %EAX
	pushq	%RAX
	call	putDigit
printInt_bb13:
	jmp	printInt_bb10
printInt_bb16:
	movl	$1, %EAX
	cmpl	%EAX, %R14D
	jne	printInt_bb15
printInt_bb17:
	movl	$0, %EAX
	pushq	%RAX
	call	putDigit
printInt_bb18:
	jmp	printInt_bb15
printInt_bb5:
	movl	$1000, %EAX
	cmpl	%EAX, %R15D
	jl	printInt_bb8
printInt_bb7:
	movl	$1000, %EDI
	movl	$0, %EDX
	movl	%R15D, %EAX
	idivl	%EDI, %EAX
	movl	%EAX, %R14D
	movl	%R14D, %EDX
	call	putDigit
	movl	$1000, %EDI
	movl	%R14D, %EAX
	imull	%EDI, %EAX
	movl	%EAX, %EDI
	movl	%R15D, %EAX
	subl	%EDI, %EAX
	movl	%EAX, %R15D
	movl	$1, %EAX
	movl	%EAX, %R14D
printInt_bb8:
	movl	$100, %EAX
	cmpl	%EAX, %R15D
	jl	printInt_bb11
printInt_bb9:
	movl	$100, %EDI
	movl	$0, %EDX
	movl	%R15D, %EAX
	idivl	%EDI, %EAX
	movl	%EAX, %R14D
	movl	%R14D, %EDX
	call	putDigit
	movl	$100, %EDI
	movl	%R14D, %EAX
	imull	%EDI, %EAX
	movl	%EAX, %EDI
	movl	%R15D, %EAX
	subl	%EDI, %EAX
	movl	%EAX, %R15D
	movl	$1, %EAX
	movl	%EAX, %R14D
printInt_bb10:
	movl	$10, %EAX
	cmpl	%EAX, %R15D
	jl	printInt_bb16
printInt_bb14:
	movl	$10, %EDI
	movl	$0, %EDX
	movl	%R15D, %EAX
	idivl	%EDI, %EAX
	movl	%EAX, %R14D
	movl	%R14D, %EDX
	call	putDigit
	movl	$10, %EDI
	movl	%R14D, %EAX
	imull	%EDI, %EAX
	movl	%EAX, %EDI
	movl	%R15D, %EAX
	subl	%EDI, %EAX
	movl	%EAX, %R15D
printInt_bb15:
	movl	%R15D, %ESI
	call	putDigit
	jmp	printInt_bb6
.globl  main
main:
main_bb1:
	pushq	%R14
	pushq	%R15
main_bb2:
	movl	$5, %EAX
	movl	%EAX, %EDI
	movl	$5, %EAX
	movl	$5, %EAX
	cmpl	%EAX, %EDI
	jne	main_bb5
main_bb3:
	movl	$3, %EAX
	movl	%EAX, a(%RIP)
main_bb4:
	movl	$0, %EAX
	movl	%EAX, %R14D
	movl	$1, %EAX
	movl	%EAX, %R15D
	movl	$8, %EAX
	cmpl	%EAX, %R15D
	jg	main_bb7
main_bb6:
	movl	%R14D, %EAX
	addl	%R15D, %EAX
	movl	%EAX, %R14D
	movl	$1, %ESI
	movl	%R15D, %EAX
	addl	%ESI, %EAX
	movl	%EAX, %R15D
	movl	$8, %EAX
	cmpl	%EAX, %R15D
	jle	main_bb6
main_bb7:
	movl	$3, %ESI
	movl	$0, %EDX
	movl	%R14D, %EAX
	idivl	%ESI, %EAX
	movl	%EAX, %EDX
	movl	$4, %ESI
	movl	%EDX, %EAX
	imull	%ESI, %EAX
	movl	%EAX, %R14D
	movl	%EDI, %ESI
	movl	a(%RIP), %EAX
	pushq	%RAX
	call	addThem
	movl	%EAX, %R15D
	movl	$56, %EAX
	pushq	%RAX
	call	putchar
	movl	$61, %EAX
	pushq	%RAX
	call	putchar
	movl	%R15D, %EAX
	addl	%R14D, %EAX
	pushq	%RAX
	call	putchar
	movl	$10, %EAX
	pushq	%RAX
	call	putchar
	movl	$0, %EAX
	movl	%EAX, %R15D
	movl	$10, %EAX
	cmpl	%EAX, %R15D
	jge	main_bb9
main_bb8:
	movl	$48, %EAX
	addl	%R15D, %EAX
	pushq	%RAX
	call	putchar
	movl	$1, %EDI
	movl	%R15D, %EAX
	addl	%EDI, %EAX
	movl	%EAX, %R15D
	movl	$10, %EAX
	cmpl	%EAX, %R15D
	jl	main_bb8
main_bb9:
	movl	$10, %EAX
	pushq	%RAX
	call	putchar
	movl	$67, %EAX
	pushq	%RAX
	call	putchar
	movl	$83, %EAX
	pushq	%RAX
	call	putchar
	movl	$3510, %EAX
	pushq	%RAX
	call	printInt
	movl	$10, %EAX
	pushq	%RAX
	call	putchar
	movl	$0, %EAX
	movl	%EAX, %EDI
	movl	$1, %EAX
	movl	%EAX, %R15D
	movl	$1, %EAX
	movl	%EAX, %R14D
	movl	$0, %EAX
	movl	%EAX, %EDX
	movl	$0, %EAX
	movl	$0, %EAX
	cmpl	%EAX, %EDI
	jne	main_bb12
main_bb10:
	movl	$0, %EAX
	cmpl	%EAX, %R15D
	jne	main_bb15
main_bb13:
	movl	$1, %EAX
	movl	%EAX, %R15D
main_bb11:
	movl	$10, %EAX
	cmpl	%EAX, %R15D
	jne	main_bb24
main_bb22:
	movl	$99, %EAX
	pushq	%RAX
	call	putchar
	movl	$0, %EAX
	pushq	%RAX
	call	putDigit
	movl	$0, %EAX
	pushq	%RAX
	call	putDigit
	movl	$108, %EAX
	pushq	%RAX
	call	putchar
main_bb23:
	movl	$10, %EAX
	pushq	%RAX
	call	putchar
	movl	$0, %EAX
main_bb25:
	popq	%R14
	popq	%R15
	ret
main_bb5:
	movl	$4, %EAX
	movl	%EAX, a(%RIP)
	jmp	main_bb4
main_bb21:
	movl	$3, %EAX
	movl	%EAX, %R15D
	jmp	main_bb11
main_bb18:
	movl	$0, %EAX
	cmpl	%EAX, %EDX
	jne	main_bb21
main_bb19:
	movl	$10, %EAX
	movl	%EAX, %R15D
main_bb20:
	jmp	main_bb11
main_bb15:
	movl	$0, %EAX
	cmpl	%EAX, %R14D
	jne	main_bb18
main_bb16:
	movl	$2, %EAX
	movl	%EAX, %R15D
main_bb17:
	jmp	main_bb11
main_bb12:
	movl	$0, %EAX
	movl	%EAX, %R15D
	jmp	main_bb11
main_bb24:
	movl	$98, %EAX
	pushq	%RAX
	call	putchar
	movl	$97, %EAX
	pushq	%RAX
	call	putchar
	movl	$100, %EAX
	pushq	%RAX
	call	putchar
	movl	$61, %EAX
	pushq	%RAX
	call	putchar
	movl	%R15D, %R9D
	call	printInt
	jmp	main_bb23
