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

.data
	captionHeader db "CPUID 0 Vendor string", 0	
	
	Value dq 3.14159265358979323846264338327
	textBuf dd 64 dup(0ffffffffh)

    N dd 1
	X dd ?
	Y dd ?

	a dd ?
	one_f dd 2.0

.code
 main:

	mov eax, N
	add eax, 10
	mov X, eax
	add eax, eax
	mov Y, eax

	mov edx, X
	mov a, edx
	push offset dword ptr [textBuf]
	push offset a
	push 8
	call StrHex_MY
	invoke MessageBoxA, 0, ADDR textBuf, ADDR captionHeader, MB_ICONINFORMATION

	mov edx, X
	or edx, 80h
	mov a, edx
	push offset dword ptr [textBuf + 2]
	push offset a
	push 8
	call StrHex_MY
	invoke MessageBoxA, 0, ADDR [textBuf + 2], ADDR captionHeader, MB_ICONINFORMATION

	mov edx, X
	mov a, edx
	push offset dword ptr [textBuf + 4]
	push offset a
	push 16
	call StrHex_MY
	invoke MessageBoxA, 0, ADDR [textBuf + 4], ADDR captionHeader, MB_ICONINFORMATION


	mov edx, X
	or edx, 8000h
	mov a, edx
	push offset dword ptr [textBuf + 8]
	push offset a
	push 16
	call StrHex_MY
	invoke MessageBoxA, 0, ADDR [textBuf + 8], ADDR captionHeader, MB_ICONINFORMATION



	mov edx, X
	mov a, edx
	push offset dword ptr [textBuf + 12]
	push offset a
	push 32
	call StrHex_MY
	invoke MessageBoxA, 0, ADDR [textBuf + 12], ADDR captionHeader, MB_ICONINFORMATION


	mov edx, X
	or edx, 80000000h
	mov a, edx
	push offset dword ptr [textBuf + 20]
	push offset a
	push 32
	call StrHex_MY
	invoke MessageBoxA, 0, ADDR [textBuf + 20], ADDR captionHeader, MB_ICONINFORMATION

	mov edx, X
	mov eax, one_f
	mul edx
	mov a, eax
	push offset dword ptr [textBuf + 28]
	push offset a
	push 32
	call StrHex_MY
	invoke MessageBoxA, 0, ADDR [textBuf + 28], ADDR captionHeader, MB_ICONINFORMATION


	push offset dword ptr [textBuf]
	push offset Value
	push 32
	call StrHex_MY
	invoke MessageBoxA, 0, ADDR textBuf, ADDR captionHeader, MB_ICONINFORMATION


	invoke ExitProcess, 0
end main

