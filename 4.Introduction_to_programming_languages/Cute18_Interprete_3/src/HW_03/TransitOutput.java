package HW_03;
import java.util.Optional;

class TransitionOutput {					//TransitionOutput Ŭ���� ���� �߰� (#T/F)
	private final State nextState;
	private final Optional<Token> token;
	static TransitionOutput GOTO_START = new TransitionOutput(State.START);
	static TransitionOutput GOTO_ACCEPT_ID = new TransitionOutput(State.ACCEPT_ID);
	static TransitionOutput GOTO_ACCEPT_INT = new TransitionOutput(State.ACCEPT_INT);
	static TransitionOutput GOTO_SIGN = new TransitionOutput(State.SIGN);
	static TransitionOutput GOTO_FAILED = new TransitionOutput(State.FAILED);
	static TransitionOutput GOTO_EOS = new TransitionOutput(State.EOS);
	static TransitionOutput GOTO_ACCEPT_TRUE = new TransitionOutput(State.ACCEPT_TRUE);//�߰��� TRUE ����
	static TransitionOutput GOTO_ACCEPT_FALSE = new TransitionOutput(State.ACCEPT_FALSE);//�߰��� FALSE ����
	static TransitionOutput GOTO_ACCEPT_SHARP = new TransitionOutput(State.ACCEPT_SHARP);//�߰��� SHARP ����
	
	static TransitionOutput GOTO_MATCHED(TokenType type, String lexime) {	//Ÿ�԰� ������ ���ڷ� �޴� ������
		return new TransitionOutput(State.MATCHED, new Token(type, lexime));//��ū�� Ÿ���� �ٲ� Ÿ���� ����� ��ū�� ��ȯ��
	}
	static TransitionOutput GOTO_MATCHED(Token token) {	//TOKEN �� ���ڷ� �޴� ������
		return new TransitionOutput(State.MATCHED, token);//��ū�� Ÿ���� �ٲ��� �ʾ��� �� �ش� ��ū�� �״�� ��ȯ
	}
	
	TransitionOutput(State nextState, Token token) {	//���� ���¿� ��ū��ü�� ���ڷ� �޴� ������
		this.nextState = nextState;
		this.token = Optional.of(token);
	}
	
	TransitionOutput(State nextState) {		//���� ���¸� ���ڷ� �޴� ������
		this.nextState = nextState;
		this.token = Optional.empty();
	}
	
	State nextState() {					//���� ���¸� ��ȯ�ϴ� �Լ�
		return this.nextState;
	}
	
	Optional<Token> token() {
		return this.token;
	}
}