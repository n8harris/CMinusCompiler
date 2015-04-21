.text
	.align 4
.globl  quicksort
quicksort:
quicksort_bb1:
	pushq	%RBP
	pushq	%R12
	pushq	%R13
	pushq	%R14
	pushq	%R15
	movl	%EDI, %R14D
	movl	%ESI, %EAX
	movl	%EDX, %R15D
quicksort_bb2:
	cmpl	%R15D, %EAX
	jge	quicksort_bb5
quicksort_bb3:
	movl	%EAX, %ESI
	movl	%R15D, %EBP
	cmpl	%EBP, %ESI
	jge	quicksort_bb7
quicksort_bb6:
	cmpl	%ESI, %R14D
	jg	quicksort_bb9
quicksort_bb8:
	movl	%ESI, %EDI
	addl	%R10D, %EDI
	movl	%EDI, %ESI
	cmpl	%ESI, %R14D
	jle	quicksort_bb8
quicksort_bb9:
	movl	%EBP, %EDI
	addl	%EBP, %EDI
	cmpl	%EDI, %R14D
	jle	quicksort_bb11
quicksort_bb10:
	movl	%EBP, %EDI
	addl	%R9D, %EDI
	movl	%EDI, %EBP
	movl	%EBP, %EDI
	addl	%EBP, %EDI
	cmpl	%EDI, %R14D
	jg	quicksort_bb10
quicksort_bb11:
	cmpl	%EBP, %ESI
	jge	quicksort_bb13
quicksort_bb12:
	movl	%R14D, %EDI
	movl	%EBP, %ESI
	movl	%EDI, %R14D
quicksort_bb13:
	cmpl	%EBP, %ESI
	jl	quicksort_bb13
quicksort_bb7:
	movl	%R14D, %EDI
	movl	%EBP, %R14D
	movl	%EDI, %EBP
	movl	%EBP, %EDI
	subl	%R8D, %EDI
	pushq	%RDI
	movl	%EAX, %EDX
	movl	%R14D, %ESI
	call	quicksort
	movl	%R15D, %ECX
	movl	%EBP, %EAX
	addl	%R12D, %EAX
	pushq	%RAX
	movl	%R14D, %ESI
	call	quicksort
quicksort_bb4:
	popq	%R13
	popq	%R14
	popq	%R15
	popq	%R12
	popq	%RBP
	ret
quicksort_bb5:
	jmp	quicksort_bb4
