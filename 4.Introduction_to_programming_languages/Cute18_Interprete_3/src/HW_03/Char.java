package HW_03;

class Char {
	private final char value;			//character ���� �ϳ��� ���� �� ���� ����
	private final CharacterType type;	//character ���� �ϳ��� ���ؼ��� type�� ����

	enum CharacterType {				//enum ����
		LETTER, DIGIT, SPECIAL_CHAR, WS, END_OF_STREAM, SHARP//����, ����, Ư�� ��ȣ, ����, EOS,
	}
	
	static Char of(char ch) {//���ڷ� ���� ch �� ch�� Ÿ���� ����ִ� �����ڸ� ��ȯ
		return new Char(ch, getType(ch));
	}
	
	static Char end() {
		return new Char(Character.MIN_VALUE, CharacterType.END_OF_STREAM);
	}
	
	private Char(char ch, CharacterType type) {
		this.value = ch;
		this.type = type;
	}
	
	char value() {
		return this.value;
	}
	
	CharacterType type() {
		return this.type;
	}
	//���� �ϳ��� Ÿ���� ��ȯ���ִ� �Լ�
	private static CharacterType getType(char ch) {	//Char Ŭ������ getType �Լ� switch�� ����, �ƽ�Ű�ڵ� ����ϸ� �ɵ�
		int code = (int)ch;
		if ( (code >= (int)'A' && code <= (int)'Z')
			|| (code >= (int)'a' && code <= (int)'z')) {
			return CharacterType.LETTER;
		}
		if ( Character.isDigit(ch) )  return CharacterType.DIGIT;
		
		switch ( ch ) {
			case '(':
				return CharacterType.SPECIAL_CHAR; 
			case ')':
				return CharacterType.SPECIAL_CHAR;
			case '+':
				return CharacterType.SPECIAL_CHAR;
			case '-':
				return CharacterType.SPECIAL_CHAR;
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
			case '?' :
				return CharacterType.LETTER;	// ? �϶� SPECIAL_CHAR�� ó���Ǹ� FAILED�� ���� ������ LETTER�� ��ȯ 
			case '#':
				return CharacterType.SHARP;		// # �ϳ��϶��� ���ؼ��� ���¿� ���ؼ� SHARP��� ���¸� ��ȯ
		}
		if ( Character.isWhitespace(ch) ) 	return CharacterType.WS;  //�����ΰ��
		
		if(ch == '\'') return CharacterType.END_OF_STREAM;//END_OF_STREAM : Stream�� ������ �˸�
		
		throw new IllegalArgumentException("input = " + ch);
	}
}
