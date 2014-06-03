.586
.model flat, stdcall
option casemap : none			;����������� ����� �� ������� �����
include \masm32\include\windows.inc

include \masm32\include\user32.inc
include \masm32\include\kernel32.inc

; � ����� include ��� ����� ������������ �����
includelib \masm32\lib\user32.lib
includelib \masm32\lib\kernel32.lib

include \masm32\include\comdlg32.inc
includelib \masm32\lib\comdlg32.lib

include module.inc
include longop.inc

.const
	Len equ 8
	FVal equ 51

.data
	captionHeaderFactorial db "Lab 5, factorial", 0
	captionHeaderSquare db "Lab 5, square", 0	
		
	textBufFactorial dd Len dup(0)
	textBufSquare dd Len * 2 dup(0)
	
	R dd Len dup(0)
	pTemp dd ?				;Len dup(0)   ; temporary pointer 
	RF dd Len * 2 dup(0)
	
	; count factorial correctly only for N <= 51, for bigger numbers needs bigger arrays (more than 8) and also counters updated)
	N dd ?

	szFileName db 256 dup(0)

	hFile dd 0
	pResFile dd 0
	;szFileName db "ololo.txt", 0
	szTextBuf db "����� ������, ��������� � ����", 10, 13, 10, 13, 0
	newLine db 0ah, 0dh, 0ah, 0dh, 0

.code

MySaveFileName proc
	LOCAL ofn : OPENFILENAME
	invoke RtlZeroMemory, ADDR ofn, SIZEOF ofn  ; �������� �� ���� ���������
	mov ofn.lStructSize, SIZEOF ofn
	mov ofn.lpstrFile, OFFSET szFileName
	mov ofn.nMaxFile, SIZEOF szFileName
	invoke GetSaveFileName, ADDR ofn			; ������ ���� File Save As
	ret
MySaveFileName endp

 main:

	call MySaveFileName
	cmp eax, 0 ; ��������: ���� � ��� ���� ��������� ������ Cancel, �� EAX=0
	je @exit
	
	invoke GlobalAlloc, GPTR, 32 * Len
	mov pTemp, eax
	mov dword ptr [eax], 1

	invoke CreateFile,  ADDR szFileName,
						GENERIC_WRITE,
						FILE_SHARE_WRITE,
						0, CREATE_ALWAYS,
						FILE_ATTRIBUTE_NORMAL,
						0
	cmp eax, INVALID_HANDLE_VALUE
	je @exit						;������ �� ����� ����������
	mov hFile, eax
	

	;mov dword ptr [pTemp], 1
	mov N, 1
	@factorial:

		; give Mul_N32_LONGOP all params
		push Len
		push pTemp
		push N
		push offset R
		call Mul_N32_LONGOP

		; copy content of R to pTemp
		mov ecx, Len
		dec ecx
		mov edx, pTemp
		@copy_R_to_pTemp:
			mov eax, [R + 4 * ecx]
			mov [edx + 4 * ecx], eax  ; ����� � ����� �� ��������� �������� ����� ������
			dec ecx
			jge @copy_R_to_pTemp

		push offset dword ptr [textBufFactorial]
		push offset dword ptr [R]
		push Len * 32
		call StrHex_MY
		
		invoke lstrlen, ADDR textBufFactorial
		invoke WriteFile, hFile, ADDR textBufFactorial, eax, ADDR pResFile, 0

		invoke lstrlen, ADDR newLine
		invoke WriteFile, hFile, ADDR newLine, eax, ADDR pResFile, 0

		inc N
		cmp N, FVal
		jle @factorial
	
	invoke CloseHandle, hFile

	invoke GlobalFree, pTemp  ; free memory 

	; output
	push offset dword ptr [textBufFactorial]
	push offset dword ptr [R]
	push Len * 32
	call StrHex_MY
	invoke MessageBoxA, 0, ADDR textBufFactorial, ADDR captionHeaderFactorial, MB_ICONINFORMATION	

	; call NxN procedure
	;push Len
	;push offset R
	;push Len
	;push offset R
	;push offset RF
	;call Mul_NN_LONGOP

	; output
	;push offset dword ptr [textBufSquare]
	;push offset dword ptr [RF]
	;push Len * 64 - 64
	;call StrHex_MY
	;invoke MessageBoxA, 0, ADDR textBufSquare, ADDR captionHeaderSquare, MB_ICONINFORMATION	

	@exit:
		invoke ExitProcess, 0
end main

