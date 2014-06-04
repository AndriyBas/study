.586
.model flat, c
.code
;***************************
;**
;**
;***************************

Add_LONGOP proc
	
	push ebp
	mov ebp, esp
	
	mov edi, [ebp + 8]   ; address of RESULT
	mov ebx, [ebp + 12]  ; address of operand B
	mov esi, [ebp + 16]  ; address of operand A
	mov ecx, [ebp + 20]  ; counter, required number of repetitions

	clc         ; обнулюємо біт CF регістру EFLAGS, куди записується переповнення

	mov edx, 0  ; лічильник, що відповідає за зсув

	@cycle:

		mov eax, dword ptr [esi + edx] ; take next 32 bits of A
		adc eax, dword ptr [ebx + edx] ; add them with nex 32 bits of B
		mov dword ptr [edi + edx], eax ; write result on appropriate position
		
		jnc @laa
		mov ebp, 1
		clc 
		 
		@laa:
			add edx, 4 
		
		dec ebp
		jnz @lab
		stc

		@lab:

		dec ecx
		jnz @cycle
	@exitp:
		pop ebp
		ret 16
Add_LONGOP endp


;***************************
;**
;**
;***************************
Sub_LONGOP proc
Sub_LONGOP endp

;***************************
;**
;** 1 param : len of A
;** 2 param : pointer to A
;** 3 param : 32-bit multiplier
;** 4 param : pointer to RESULT
;**
;***************************
Mul_N32_LONGOP proc
	
	push ebp      ; magic operation, not sure why it is needed
	mov ebp, esp
	
	mov edi, [ebp + 8]   ; address of RESULT
	mov ebx, [ebp + 12]  ; address of N (32-bit multiplier)
	mov esi, [ebp + 16]  ; address of operand A
	mov ecx, [ebp + 20]  ; counter, how many dd's to multiply
						 ; simply, just lenght of the array (operand A) 

	clc         ; обнулюємо біт CF регістру EFLAGS, куди записується переповнення

	; put all zero's to RESULT
	mov ebp, ecx
	dec ebp
	@putZero:
		mov dword ptr [edi + ebp * 4], 0
		dec ebp
		jge @putZero

	mov ebp, 0  ; лічильник, що відповідає за зсув

	@cycle:

		add ebp, 4	; increment ebp
					; ATTENTION : DO NOT PUT THIS OPERATION AFTER next 4 commands, because small children will cry

		mov eax, dword ptr [esi + ebp - 4]			 ; take next 32 bits of A
		mul ebx									     ; multiply them with N (stored in ebx)
		add dword ptr [edi + ebp - 4], eax           ; add lower  32-bits from eax (without carry)
		adc dword ptr [edi + ebp], edx  ; add higher 32-bits from edx (with carry)
		
		dec ecx
		jnz @cycle

	pop ebp
	ret 16
Mul_N32_LONGOP endp



;***************************
;**
;** ATTENTION !!!! multiplicatioin is writen to result, but added to it ))) enjoy
;**
;** 1 param : len of A
;** 2 param : pointer to A
;** 3 param : len of B
;** 4 param : pointer to B
;** 5 param : pointer to RESULT
;**
;***************************
Mul_NN_LONGOP proc
	
	push ebp      ; magic operation, not sure why it is needed
	mov ebp, esp
	
	mov edi, [ebp + 8]   ; address of RESULT
	
	mov ebx, [ebp + 12]  ; address of operand B
	mov ecx, [ebp + 16]  ; length of B
	mov maxCounter2, ecx ; save length of B to maxCounter2
	
	mov esi, [ebp + 20]  ; address of operand A
	mov ecx, [ebp + 24]  ; length of A
	mov maxCounter1, ecx ; save length of A to maxCounter2

	mov counter1, 0h ; put zero to counter, just in case
	@outer:
		; check if need to exit (on condition counter1 >= maxCounter1)
		mov eax, counter1
		cmp eax, maxCounter1
		jge @exitp
		mov counter2, 0h

		@inner:
			; get index of counter1 
			mov ecx, counter1
			mov eax, dword ptr [esi + 4 * ecx]

			; get index of counter2
			mov ecx, counter2
			mul dword ptr [ebx + 4 * ecx]

			; get index where to put result, ecx = counter1 + counter2
			add ecx, counter1
			; save result
			add dword ptr [edi + 4 * ecx], eax
			adc dword ptr [edi + 4 * ecx + 4], edx

			; increment counter2 and continue inner loop if counter2 < maxCounter2
			inc counter2
			mov eax, counter2
			cmp eax, maxCounter2
			jl @inner

			; increment counter1 and jump to @outer
			inc counter1
			jmp @outer

	@exitp:
		pop ebp
		ret 20   ; because 5 operands
Mul_NN_LONGOP endp


;***************************
;**
;** procedure divides array A by 32-bit value N, fraction written to edx
;**
;** 1 param : length of A
;** 2 param : pointer to A
;** 3 param : divider N
;** 4 param : pointer to RESULT
;**
;***************************
Div_N32_LONGOP proc
	push ebp
	mov ebp,esp
	
	mov esi, [ebp + 8]  ; address of RESULT

	mov ecx, [ebp + 12]  ; divider (32-bit value)
	mov divider, ecx     ; save divider

	mov ebx, [ebp + 16]  ; address of dividend A (array of 32-bits)
	mov ecx, [ebp + 20]  ; length of A
	
	xor edx, edx
	dec ecx
	@cycle:
		
		mov eax, dword ptr [ebx + 4 * ecx]   ; get next 32-bits to divide
		div divider						     ; divide them
		mov dword ptr [esi + 4 * ecx], eax   ; save result

		dec ecx
		jge @cycle

	@exitp:
		pop ebp
		ret 16   ; because 4 operands	
Div_N32_LONGOP endp

Copy_LONGOP proc
	push ebp
	mov ebp, esp

	mov edi, [ebp + 8]   ; address of DEST
	mov ebx, [ebp + 12]  ; address of SRC
	mov ecx, [ebp + 16]  ; length of arrays

	dec ecx
	@copy_my:
		mov eax, [ebx + 4 * ecx]
		mov [edi + 4 * ecx], eax 
		dec ecx
		jge @copy_my

	pop ebp
	ret 12
Copy_LONGOP endp

.data
	; vars for Mul_NN
	counter1 dd 0h
	counter2 dd 0h
	maxCounter1 dd 0h
	maxCounter2 dd 0h

	; vars for Div_N32
	divider dd 1
end