package parser;


import java.util.HashMap;
import java.util.Map;

public class Token {
	private final TokenType type;
	private final String lexme;
	
	static Token ofName(String lexme) {        // Token Type �Ǻ�
		TokenType type = KEYWORDS.get(lexme);  // Token�� KEYWORDS���� Ȯ��- map ��� ( �ƴ� ��� NULL ��ȯ )
		if ( type != null ) {  //type�� null�� �ƴϸ� = KEYWORDS �̹Ƿ� Token �ٷ� ����
			return new Token(type, lexme);
		}
		else if ( lexme.endsWith("?") ) { //�������� ? �̸�
			if ( lexme.substring(0, lexme.length()-1).contains("?") ) { // ?�� �ϳ� �� �����ϰ� ������
				throw new ScannerException("invalid ID=" + lexme);  
			}
			return new Token(TokenType.QUESTION, lexme); // QUESTION���� Type ���� 
		}
		else if ( lexme.contains("?") ) { // ?�� �����ϰ� ������
			throw new ScannerException("invalid ID=" + lexme);
		}
		else {                             // ��� �ƴϸ� ID
			return new Token(TokenType.ID, lexme);
		}
	}

	Token(TokenType type, String lexme) {  // Token�� ������
		this.type = type;
		this.lexme = lexme;
	}

	public TokenType type() { // Type ��ȯ
		return this.type;
	}

	public String lexme() {  //lexme ��ȯ
		return this.lexme;
	}

	@Override
	public String toString() {  //Token ���
		return String.format("%s(%s)", type, lexme);
	}
	
	private static final Map<String,TokenType> KEYWORDS = new HashMap<>();  //Token�� KEYWORDS��.
	static {
	KEYWORDS.put("define", TokenType.DEFINE);
	KEYWORDS.put("lambda", TokenType.LAMBDA);
	KEYWORDS.put("cond", TokenType.COND);
	KEYWORDS.put("quote", TokenType.QUOTE);
	KEYWORDS.put("not", TokenType.NOT);
	KEYWORDS.put("cdr", TokenType.CDR);
	KEYWORDS.put("car", TokenType.CAR);
	KEYWORDS.put("cons", TokenType.CONS);
	KEYWORDS.put("eq?", TokenType.EQ_Q);
	KEYWORDS.put("null?", TokenType.NULL_Q);
	KEYWORDS.put("atom?", TokenType.ATOM_Q);
	}
}
