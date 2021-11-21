package Interpreter;


import static Interpreter.TokenType.ID;
import static Interpreter.TokenType.INT;
import static Interpreter.TokenType.*;
import static Interpreter.TransitionOutput.GOTO_ACCEPT_ID;
import static Interpreter.TransitionOutput.GOTO_ACCEPT_INT;
import static Interpreter.TransitionOutput.GOTO_EOS;
import static Interpreter.TransitionOutput.GOTO_FAILED;
import static Interpreter.TransitionOutput.GOTO_MATCHED;
import static Interpreter.TransitionOutput.GOTO_SIGN;
import static Interpreter.TransitionOutput.GOTO_START; 
import static Interpreter.TransitionOutput.GOTO_BOOL;
import static Interpreter.TransitionOutput.GOTO_SPECIAL;

/*���¸� ��������
 * START -> ó�� ������ �� ��ġ
 * SPECIAL -> Ư�� ���ڰ� ������ ��
 * ACCEPT_ID -> ID�� ���� �� ó�� (Ư���� ������ ����ó���� ���Ͽ� �ذ�)
 * ACCEPT_INT ->DIGIT�� ���� ��, SIGN ���� DIGIT�� �� ��
 * SIGN -> SIGN�� �� ��
 * FAILED -> ���� �� ��
 * MATCHED -> ����
 * ���*/
enum State {  //����
	START {  //���� �� �� ����
		@Override
		public TransitionOutput transit(ScanContext context) { 
			Char ch = context.getCharStream().nextChar();
			char v = ch.value();
			switch ( ch.type() ) {
			case LETTER: //���ڿ��� ���� ��
				context.append(v);
				return GOTO_ACCEPT_ID;
			case SIGN:  // +/-�� ���� ��
				context.append(v); // +,-
				context.getCharStream().pushBack(v);
				/*pushBack�� �ϴ� ������ ���� ���� ���ڷ� ������ ��, GOTO_SIGN�� �ϸ� ���� ��ġ��  �о�ͼ� ������� �������� �ǰ� ���� �߻�
				 * ���� pushBack�� �̿��Ͽ� �����͸� �־���� �ٽ� ����� �� �ְ� �Ѵ�.*/
				return GOTO_SIGN;  //SIGN���� �̵�
			case DIGIT: //0~9�� �����Ͱ� ���� ��
				context.append(v);
				return GOTO_ACCEPT_INT;
			case SPECIAL_CHAR: //���� SIGN�� ���� ����
				context.append(v);
				context.getCharStream().pushBack(v);
				return GOTO_SPECIAL; 
			case SHARP: //TRUE FALSE�� Ȯ���Ͽ��ֱ����Ͽ� #�� ���� ��
				context.append(v);
				return GOTO_BOOL;
			case WS:
				return GOTO_START;
			case END_OF_STREAM:
				return GOTO_EOS;
			default:
				throw new AssertionError();
			}
		}
	},
	/*Ư�����ڰ� ���Ե� ��
	 * ch�� ������ �����ʹ� ������ ���� Ư�������� Stream
	 * v�� ���� �����ʹ� Ư������
	 * nextch�� ���� ��ġ �� ���� �����Ͱ� �ִ��� Ȯ���ϰ� ���� ��ȭ*/
	SPECIAL{
		@Override
		public TransitionOutput transit(ScanContext context) {
			Char ch = context.getCharStream().nextChar(); // pushback�� �ϰ� �����ϹǷ� ������ Ư������ ������.
			char v = ch.value();
			Char nextch = context.getCharStream().nextChar();  //���� ��ġ �б�.
			switch ( nextch.type() ) {
			case LETTER:
			case DIGIT:
				return GOTO_FAILED;
			case SPECIAL_CHAR:
				return GOTO_FAILED;
			case WS:
			case END_OF_STREAM:
				return GOTO_MATCHED(fromSpecialCharactor(v),context.getLexime());  //���� �������̸� �� ���ڿ� ���� ���� ��Ī
				/*fromSpecialCharator�� ���ڰ� ������ ��, �� �����Ͱ� ��� Ư���������� Ȯ��*/
			default:
				throw new AssertionError();
			}
		}
		
	},
	ACCEPT_ID {  //LETTER�� ���� ��.
		@Override
		public TransitionOutput transit(ScanContext context) {
			Char ch = context.getCharStream().nextChar();
			char v = ch.value();
			switch ( ch.type() ) {
			/*LETTER�̵� DIGIT�̵� ID�̹Ƿ� �Ʒ�ó�� ����*/
			case LETTER:
			case DIGIT:
				context.append(v);
				return GOTO_ACCEPT_ID;
			case SPECIAL_CHAR:
				return GOTO_FAILED;
			case WS:
			case END_OF_STREAM:
				String result = context.getLexime();
				return GOTO_MATCHED(Token.ofName(result).type(),result);
				/*���������� ID��°��� Ȯ���Ͽ��� ��, ���������� Ư���� ������� Ȯ���ϱ� ���Ͽ� ofName �޼ҵ� ���.
				 * ofName�� Ư���Ѱ�� Ư���� ���� Ư���� ��찡 �ƴϸ� ID�� ��ȯ��.
				 * result�� �����ϴ� ������ getLexime�� ����ϸ� �����͸� ������ ������ ����� �ϱ� ���� ����*/
			case Q: // ?�� ������ ��
				context.append(v);
				result = context.getLexime(); //���� Ư�� ������ ���� ����.
				return Token.ofName(result).type() == ID ? GOTO_FAILED : GOTO_MATCHED(Token.ofName(result).type(),result); 
				/*���׿����ڸ� �̿��Ͽ� Q�� ���� ����ó��. ?�� ���� ofName���� �о���� ��, �� ���� ID�̸� Ư���� ��찡 �ƴϰ� ?�� �������Ƿ�  FAIL */
			default:
				throw new AssertionError();
			}
		}
	},
	ACCEPT_INT { //DIGIT�� ���� ��
		@Override
		public TransitionOutput transit(ScanContext context) {
			Char ch = context.getCharStream().nextChar();
			switch ( ch.type() ) {
			case LETTER:
				return GOTO_FAILED;
			case DIGIT:
				context.append(ch.value());
				return GOTO_ACCEPT_INT;
			case SPECIAL_CHAR:
				return GOTO_FAILED;
			case WS:
			case END_OF_STREAM:
				return GOTO_MATCHED(INT, context.getLexime());
			default:
				throw new AssertionError();
			}
		}
	},
	SIGN { //SIGN �� Digit�� ���� ��, SPECIAL�� ����. but DIGIT�� �ö��� �ٸ�.
		@Override
		public TransitionOutput transit(ScanContext context) {
			Char ch = context.getCharStream().nextChar(); // pushback�� �ϰ� �����ϹǷ� ������ Ư������ ������.
			char v = ch.value();
			Char nextch = context.getCharStream().nextChar();  //���� ��ġ �б�.
			switch ( nextch.type() ) {
			case LETTER:
				return GOTO_FAILED;
			case DIGIT: //DIGIT�� ������, INT�� �ٲ�Ƿ� ACCEPT_INT ����ؼ� INT�� ����.
				context.append(nextch.value());
				return GOTO_ACCEPT_INT;
			case SPECIAL_CHAR:
				return GOTO_FAILED;
			case WS:
			case END_OF_STREAM:
				return GOTO_MATCHED(fromSpecialCharactor(v),context.getLexime()); 
			default:
				throw new AssertionError();
			}
		}
	},
	MATCHED { // ������ ��
		@Override
		public TransitionOutput transit(ScanContext context) {
			throw new IllegalStateException("at final state");
		}
	},
	FAILED{ // ���� ���� ��
		@Override
		public TransitionOutput transit(ScanContext context) {
			throw new IllegalStateException("at final state");
		}
	},
	EOS {
		@Override
		public TransitionOutput transit(ScanContext context) {
			return GOTO_EOS;
		}
	},
	/*TRUE, FALSE�� �����ϱ� ���� ���� enum
	 * �ѱ��ڸ� ������ �� ���� LETTER�� �ƴϸ� ����.
	 * LETTER�� ��� �����͸� �ϳ� �� �о�´�.
	 * ���� �� �о�� �����Ͱ� ���̶��, �� #T #F���
	 * �׿� �´� TRUE FALSE.*/
	BOOL{
		@Override
		TransitionOutput transit(ScanContext context) {
			Char ch =context.getCharStream().nextChar();
			char v = ch.value();
			switch(ch.type()) {
			case LETTER :
				Char nextch = context.getCharStream().nextChar();
				switch(nextch.type()) {
				case END_OF_STREAM:
				case WS:
					context.append(v);
					if(v == 'T') {
						return GOTO_MATCHED(TRUE,context.getLexime());
					}
					else if(v== 'F') {
						return GOTO_MATCHED(FALSE,context.getLexime());
					}
				}
				default:
					return GOTO_FAILED;
			}
		}
		
	};

	abstract TransitionOutput transit(ScanContext context);
}
