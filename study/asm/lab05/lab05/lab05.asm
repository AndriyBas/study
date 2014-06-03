.586
.model flat, stdcall
option casemap : none			;розрізнювати великі та маленькі букви
include \masm32\include\windows.inc

include \masm32\include\user32.inc
include \masm32\include\kernel32.inc

; а також include для інших заголовочних файлів
includelib \masm32\lib\user32.lib
includelib \masm32\lib\kernel32.lib

include module.inc
include longop.inc


.data
	captionHeaderFactorial db "Lab 5, factorial", 0
	captionHeaderSquare db "Lab 5, square", 0	
		
	textBufFactorial dd 10 dup(0ffffffffh)
	textBufSquare dd 20 dup(0ffffffffh)
	
	;A dd  0ffffabcfh, 01000000h, 0ffffabcfh,  0ffffabcfh,  0ffffabcfh,  0ffffabcfh,  0ffffabcfh,  0ffffabcfh,  0ffffabcfh,  0ffffabcfh,  0f123abcfh,  01111ab4fh,  0bd01abcfh,  077776655h
	
	R dd 00000001h, 00000000h, 00000000h, 00000000h, 00000000h, 00000000h, 00000000h, 00000000h  ; 8
	
	A dd 00000000h, 00000000h, 00000000h, 00000000h, 00000000h, 00000000h, 00000000h, 00000000h  ; 8 
	
	RF dd 20 dup(00000000h) ; 16
	
	; count factorial correctly only for N <= 51, for bigger numbers needs bigger arrays (more than 8) and also counters updated)
	N dd 51
.code

 main:

	@factorial:
		; line below can be put before @factorial, left here for simplicity
		mov eax, N ; copy current multilier to eax

		; give Mul_N32_LONGOP all params
		push 7
		push offset A
		push eax
		push offset R
		call Mul_N32_LONGOP

		; copy content of R to A
		mov ecx, 8
		@copy_R_to_A:
			mov eax, [R + 4 * ecx - 4]
			mov [A + 4 * ecx - 4], eax
			dec ecx
			jnz @copy_R_to_A

		mov eax, N ; decrement counter
		dec eax
		mov N, eax ; save updated value
		jnz @factorial

	; output
	push offset dword ptr [textBufFactorial]
	push offset dword ptr [R]
	push 8 * 32
	call StrHex_MY
	invoke MessageBoxA, 0, ADDR textBufFactorial, ADDR captionHeaderFactorial, MB_ICONINFORMATION	


	push 8
	push offset R
	push 8
	push offset R
	push offset RF
	call Mul_NN_LONGOP

	; output
	push offset dword ptr [textBufSquare]
	push offset dword ptr [RF]
	push 16 * 32
	call StrHex_MY
	invoke MessageBoxA, 0, ADDR textBufSquare, ADDR captionHeaderSquare, MB_ICONINFORMATION	


	invoke ExitProcess, 0
end main

