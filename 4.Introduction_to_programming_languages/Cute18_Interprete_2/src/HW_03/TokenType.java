package HW_03;

public enum TokenType {	//enum ����
	ID, ALPHA, DIGIT,
	INT, QUESTION,
	TRUE, FALSE, NOT,
	PLUS, MINUS, TIMES, DIV,
	LT, GT, EQ, APOSTROPHE,
	L_PAREN, R_PAREN, SHARP,//SHARP �߰�
	DEFINE, LAMBDA, COND, QUOTE,
	CAR, CDR, CONS,
	ATOM_Q, NULL_Q, EQ_Q;

	static TokenType fromSpecialCharcter(char ch) { //character ��ū �ϳ��� ���� Ÿ���� ��ȯ���ִ� �Լ�
		int code = (int)ch;
		if ( (code >= (int)'A' && code <= (int)'Z')
			|| (code >= (int)'a' && code <= (int)'z')) {
			return ID;	//���� �ϳ� ¥���� ���ؼ��� �ĺ��ڷ� �Ǵ��ϰ� identifier�� ��ȯ
		}
		
		if ( Character.isDigit(ch) ) {
			return INT;	//���� �ϳ� ¥���� ���ؼ��� integer�� �Ǵ��ϰ� INT�� ��ȯ
		}
		
		switch( ch ) {	//����ǥ������ �����Ͽ� ch�� ��Ī�Ǵ� keyword�� ��ȯ�ϴ� case�� �ۼ�
			case '(' :
				return L_PAREN;
			case ')' :
				return R_PAREN;
			case '+' :
				return PLUS;
			case '-' :
				return MINUS;
			case '*' :
				return TIMES;
			case '/' :
				return DIV;
			case '<' :
				return LT;
			case '=' :
				return EQ;
			case '>' :
				return GT;
			case '\'' :
				return APOSTROPHE;
			case '#' :
				return SHARP;
			default:
				throw new IllegalArgumentException("unregistered char : " + ch);
		}
	}
}
