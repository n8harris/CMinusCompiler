.text
	.align 4
.globl  quicksort
quicksort:
quicksort_bb2:
	pushq	%R13
	pushq	%R14
	pushq	%R15
	movl	%EDI, %R13D
	movl	%ESI, %ECX
	movl	%EDX, %R14D
quicksort_bb3:
	cmpl	%R14D, %ECX
	jge	quicksort_bb6
quicksort_bb4:
	movl	%ECX, %ESI
	movl	%R14D, %R15D
	cmpl	%R15D, %ESI
	jge	quicksort_bb8
quicksort_bb7:
	cmpl	%ESI, %R13D
	jg	quicksort_bb10
quicksort_bb9:
	movl	$1, %EDI
	movl	%ESI, %EAX
	addl	%EDI, %EAX
	movl	%EAX, %ESI
	cmpl	%ESI, %R13D
	jle	quicksort_bb9
quicksort_bb10:
	movl	%R15D, %EAX
	addl	%R15D, %EAX
	cmpl	%EAX, %R13D
	jle	quicksort_bb12
quicksort_bb11:
	movl	$1, %EDI
	movl	%R15D, %EAX
	addl	%EDI, %EAX
	movl	%EAX, %R15D
	movl	%R15D, %EAX
	addl	%R15D, %EAX
	cmpl	%EAX, %R13D
	jg	quicksort_bb11
quicksort_bb12:
	cmpl	%R15D, %ESI
	jge	quicksort_bb14
quicksort_bb13:
	movl	%R13D, %EAX
	movl	%R15D, %ESI
	movl	%EAX, %R13D
quicksort_bb14:
	cmpl	%R15D, %ESI
	jl	quicksort_bb14
quicksort_bb8:
	movl	%R13D, %EAX
	movl	%R15D, %R13D
	movl	%EAX, %R15D
	movl	%R13D, %EDI
	movl	%ECX, %ESI
	movl	$1, %EDI
	movl	%R15D, %EAX
	subl	%EDI, %EAX
	movl	%EAX, %EDX
	call	quicksort
	movl	%R13D, %EDI
	movl	$1, %EDI
	movl	%R15D, %EAX
	addl	%EDI, %EAX
	movl	%EAX, %ESI
	movl	%R14D, %EDX
	call	quicksort
quicksort_bb1:
	popq	%R13
	popq	%R14
	popq	%R15
	ret
quicksort_bb6:
	movl	$0, %EAX
	jmp	quicksort_bb1
