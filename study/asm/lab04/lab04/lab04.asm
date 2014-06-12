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
	captionHeader db "Lab 4, true Bas", 0	
	textBuf dd 64 dup(0ffffffffh)
	
	A dd  0ffffabcfh, 01000000h
	B dd  0fffffffah, 0a000000h 
	R dd 2 dup(?)

	N dd 2

	;A dd 20 dup (0abcd1234h)
	;B dd 20 dup (0ffffff00h)
	;R dd 20 dup (?)

.code
 main:

    ; write length of array to add
	mov ebx, N
	push ebx
	push offset A
	push offset B
	push offset R
	call Add_LONGOP

	; count length of string
	mov eax, N
	mov ebx, 32
	mul ebx

	push offset dword ptr [textBuf]
	push offset dword ptr [R]
	push eax
	call StrHex_MY
	invoke MessageBoxA, 0, ADDR textBuf, ADDR captionHeader, MB_ICONINFORMATION


	invoke ExitProcess, 0
end main

