extern "C"
{
	void Add_LONGOP(long bits, long *pA, long *pB, long *dest);
	void Sub_LONGOP(long bits, long *pA, long *pB, long *dest);
	void Mul_N32_LONGOP(long counter, long N, long *pA, long *res);
	void Mul_NN_LONGOP(long aLen, long *pA, long bLen, long *pB, long *res);
	void Div_N32_LONGOP(long aLen, long *pA, long nDivider, long *res);
}