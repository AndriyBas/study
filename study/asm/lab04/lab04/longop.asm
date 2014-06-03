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
		ret 12
Add_LONGOP endp


;***************************
;**
;**
;***************************
Sub_LONGOP proc
	

	
Sub_LONGOP endp

end