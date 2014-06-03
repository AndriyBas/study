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

	clc         ; ��������� �� CF ������� EFLAGS, ���� ���������� ������������

	mov edx, 0  ; ��������, �� ������� �� ����

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
		ret 12
Add_LONGOP endp


;***************************
;**
;**
;***************************
Sub_LONGOP proc
Sub_LONGOP endp

;***************************
;**
;** ATTENTION !!!! multiplicatioin is not writen to result, but added to it ))) enjoy
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

	clc         ; ��������� �� CF ������� EFLAGS, ���� ���������� ������������

	mov ebp, 0  ; ��������, �� ������� �� ����

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
	ret 12
Mul_N32_LONGOP endp



;***************************
;**
;** ATTENTION !!!! multiplicatioin is writen to result, but added to it ))) enjoy
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

	clc         ; ��������� �� CF ������� EFLAGS, ���� ���������� ������������

	@outer:
		mov eax, counter1
		inc eax
		mov counter1, eax
		cmp eax, maxCounter1
		jg @exitp
		mov counter2, 0h

		@inner:
			mov eax, counter2
			inc eax
			mov counter2, eax
			cmp eax, maxCounter2
			jg @outer

			; get index of counter1 
			mov ebp, counter1
			dec ebp

			mov eax, dword ptr [esi + 4 * ebp]

			; get index of counter2
			mov ebp, counter2
			dec ebp

			mul dword ptr [ebx + 4 * ebp]

			; get index where to put result, ebp = counter1 + counter2 - 1
			add ebp, counter1

			; save result
			add dword ptr [edi + 4 * ebp - 4], eax
			adc dword ptr [edi + 4 * ebp], edx
			
			jmp @inner

	@exitp:
		pop ebp
		ret 12
Mul_NN_LONGOP endp

.data
	counter1 dd 0h
	counter2 dd 0h
	maxCounter1 dd 0h
	maxCounter2 dd 0h
	
end