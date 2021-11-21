package HW_03;
import java.util.HashMap;
import java.util.Map;

public class Token {												//Token Ŭ���� ����
	private final TokenType type;
	private final String lexme;
	
	static Token ofName(String lexme) {		//�Ű������� ���� ��Ʈ���� ���� Ÿ�԰� �� ��Ʈ�� �Ű������� �޴� �����ڸ� �����ؼ� ��ȯ���ִ� �Լ�
		TokenType type = KEYWORDS.get(lexme);
		if( type != null ) {										//Ű���忡 ������
			return new Token(type, lexme);	//KEYWORDS �ؽ����� ������ �ش��ϴ� ���� keywords�� �ƴϸ� identifier�� �����ϰ� ��ȯ
		}
		else if( lexme.endsWith("?") ) {							//Ű���尡 �ƴѵ� ?�� �����ٸ�
			if(lexme.substring(0, lexme.length() -1).contains("?")) {
				throw new ScannerException("invalid ID=" +lexme);	//����ó��
			}
			
			return new Token(TokenType.QUESTION, lexme);		//QUESTION�� �ش��ϴ� ��ū�� ��ȯ
		}
		else if( lexme.contains("?") ) {						//�̻��ѵ��� ? �� �����Ѵٸ�
			throw new ScannerException("invalid ID=" + lexme);	//����ó��
		}
		else {
			return new Token(TokenType.ID, lexme);				//���� ���� �ƴ϶�� �Ϲ� identifier�� �����ϰ� ��ȯ
		}
	}
	
	Token(TokenType type, String lexme) {
		this.type = type;
		this.lexme = lexme;
	}
	
	public TokenType type() {//�ش� ��ū�� Ÿ���� ��ȯ
		return this.type;
	}
	
	public String lexme() {
		return this.lexme;
	}
	
	@Override
	public String toString() {						//TOKEN Ŭ������ toString �޼ҵ�
		return String.format("%s(%s)", type, lexme);//type�� ��ū�� type, lexme�� ���� ��Ʈ��
	}
	//Ű���� ������� ������ KEYWORDS ��� ���� ���� Ű�� String,����� TokenType
	private static final Map<String,TokenType> KEYWORDS = new HashMap<>();
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
