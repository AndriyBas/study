.586
.model flat, c

option casemap : none			;����������� ����� �� ������� �����
include \masm32\include\windows.inc

include \masm32\include\user32.inc
include \masm32\include\kernel32.inc

; � ����� include ��� ����� ������������ �����
includelib \masm32\lib\user32.lib
includelib \masm32\lib\kernel32.lib

include \masm32\include\comdlg32.inc
includelib \masm32\lib\comdlg32.lib

include longop.inc

.data
	maxCounter1 dd 0
	counter1 dd 0
	counter2 dd 0
	tempVal1 dd 0
	tempVal2 dd 0
	pTemp dd ?	    ; temporary pointer
	pRes dd ?       ; temporary pointer 

.code

;��������� StrHex_MY ������ ����� ����������������� ����
;������ �������� - ������ ������ ���������� (����� �������)
;������ �������� - ������ �����
;����� �������� - ���������� ����� � ���� (�� ���� ������ 8)

StrHex_MY proc bits:DWORD, src:DWORD, dest:DWORD
	
	mov ecx, bits ;������� ��� �����
	cmp ecx, 0
	jle @exitp
	shr ecx, 3 ;������� ����� �����
	mov esi, src  ;������ �����
	mov ebx, dest ;������ ������ ����������

	@cycle:
		mov dl, byte ptr[esi+ecx-1] ;���� ����� - �� �� hex-�����
		mov al, dl
		shr al, 4 ;������ �����
		call HexSymbol_MY
		mov byte ptr[ebx], al
		mov al, dl ;������� �����
		call HexSymbol_MY
		mov byte ptr[ebx+1], al
		mov eax, ecx
		cmp eax, 4
		jle @next
		dec eax
		and eax, 3 ;������� ������� ����� �� ��� ����
		cmp al, 0
		jne @next
		mov byte ptr[ebx+2], 32 ;��� ������� �������
		inc ebx
	
	@next:
		add ebx, 2
		dec ecx
		jnz @cycle
	mov byte ptr[ebx], 0 ;����� ���������� �����
	
	@exitp:
		ret
StrHex_MY endp


;�� ��������� �������� ��� hex-�����
;�������� - �������� AL
;��������� -> AL

HexSymbol_MY proc
	and al, 0Fh
	add al, 48 ;��� ����� ����� ��� ���� 0-9
	cmp al, 58
	jl @exitp
	add al, 7 ;��� ���� A,B,C,D,E,F

	@exitp:
		ret
HexSymbol_MY endp


StrDec_MY proc bits:DWORD, src:DWORD, dest:DWORD
	
	mov esi, src  ;������ �����
	mov ebx, dest ;������ ������ ����������
	
	; ATTENTION, @MagicNumber, should be enough for most operations
	mov maxCounter1, 75
	mov ecx, maxCounter1
	mov counter1, ecx
	
	mov byte ptr[ebx + ecx], 0 ; end line with 0

	invoke GlobalAlloc, GPTR, 32 * 8
	mov pTemp, eax
	
	@cycle:
		
		mov tempVal1, esi		; save register esi, because Div_N32_LONGOP also uses it
		mov tempVal2, ebx		; save register ebx, because Div_N32_LONGOP also uses it

		; divide array by 10
		push pTemp
		push 10
		push esi
		push 8
		call Div_N32_LONGOP
		add esp, 16				; clear stack

		; copy content of pTemp to esi
		push 8
		push pTemp
		push tempVal1
		call Copy_LONGOP
		add esp, 12				;clear stack
		
		mov esi, tempVal1		; restore value of esi	
		mov ebx, tempVal2       ; restore value of ebx

		add edx, 48
		mov ecx, counter1
		mov byte ptr[ebx + ecx - 1], dl

		dec counter1
		jnz @cycle

	@exitp:
		invoke GlobalFree, pTemp  ; free memory 
		ret
StrDec_MY endp

end