package lexer;

class Char { 
	private final char value;   // ��
	private final CharacterType type;  //type - ����

	/*LETTER -> A~Z, a~z
	 *DIGIT -> 0~9
	 *SPECIAL_CHAR -> +,-������ Ư������
	 *WS -> ����
	 *END_OF_STREAM -> STREAM�� ������ Ȯ��
	 *SIGN -> +,-�ǹ� SPECIAL_CHAR�� SIGN�� ���� ������, SIGN�� �ڿ� DIGIT�� ���� INT�� �ٲ�� ����
	 *SHARP-> #�� �� ��, boolean�� ���� ����
	 *Q -> ?  null? �� ���� �����͸� ó���ϱ� ���Ͽ� ����
	 */
	enum CharacterType {
		LETTER, DIGIT, SPECIAL_CHAR, WS, END_OF_STREAM,SHARP,SIGN,Q  
	}
	
	
	/*Char of�� getType�� ���Ͽ� ���� �ϳ��� Type�� �����´�.*/
	static Char of(char ch) {   // Char�� �����´�.
		return new Char(ch, getType(ch));
	}
	
	/*end�� STREAM�� ���Ḧ �ǹ��Ѵ�.*/
	static Char end() {  //value = null �� CharacterType = END_OF_STREAM
		return new Char(Character.MIN_VALUE, CharacterType.END_OF_STREAM);
	}
	
	/*Char�� ������ -> value�� ch�� �ְ�, CharacterType�� type����*/
	private Char(char ch, CharacterType type) { //Char getter
		this.value = ch;
		this.type = type;
	}
	
	char value() {        //value �� getter
		return this.value;
	}
	
	CharacterType type() {  //Ÿ�� getter
		return this.type;
	}
	
	/*������ Ÿ�� ���ϱ� ���� CharacterType ���� ����� ��� ������ Ÿ�� ����*/
	private static CharacterType getType(char ch) {        //������ Ÿ�� ���ϱ�
		int code = (int)ch;
		if ( (code >= (int)'A' && code <= (int)'Z')        // A~Z, a~z -> letter
			|| (code >= (int)'a' && code <= (int)'z')) {
			return CharacterType.LETTER;
		}
		
		if ( Character.isDigit(ch) ) {                     // 0~9 -> Digit
			return CharacterType.DIGIT;
		}
		switch ( ch ) {                                 // -> SPECIAL_CHAR, SIGN, Q, SHARP;
			case '-': 
				return CharacterType.SIGN;  //���� ����
			case '(':
				return CharacterType.SPECIAL_CHAR;
			case ')':
				return CharacterType.SPECIAL_CHAR;
			case '+':
				return CharacterType.SIGN;
			case '*':
				return CharacterType.SPECIAL_CHAR;
			case '/':
				return CharacterType.SPECIAL_CHAR;
			case '<':
				return CharacterType.SPECIAL_CHAR;
			case '=':
				return CharacterType.SPECIAL_CHAR;
			case '>':
				return CharacterType.SPECIAL_CHAR;
			case '\'':
				return CharacterType.SPECIAL_CHAR;
			case '#' :
				return CharacterType.SHARP; //�߰� SHARP�� ��.
			case '?' :
				return CharacterType.Q;
		}
		
		if ( Character.isWhitespace(ch) ) {             // Chhar�� �������� Ȯ��
			return CharacterType.WS;
		}
		
		//���� ���ڵ��� �ƴ� ���.
		throw new IllegalArgumentException("input=" + ch); 
	}
}
