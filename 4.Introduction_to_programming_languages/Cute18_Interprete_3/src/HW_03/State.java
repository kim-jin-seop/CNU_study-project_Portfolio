package HW_03;
import static HW_03.TokenType.FALSE;
import static HW_03.TokenType.INT;
import static HW_03.TokenType.SHARP;
import static HW_03.TokenType.TRUE;
import static HW_03.TransitionOutput.GOTO_ACCEPT_FALSE;
import static HW_03.TransitionOutput.GOTO_ACCEPT_ID;
import static HW_03.TransitionOutput.GOTO_ACCEPT_INT;
import static HW_03.TransitionOutput.GOTO_ACCEPT_SHARP;
import static HW_03.TransitionOutput.GOTO_ACCEPT_TRUE;
import static HW_03.TransitionOutput.GOTO_EOS;
import static HW_03.TransitionOutput.GOTO_FAILED;
import static HW_03.TransitionOutput.GOTO_MATCHED;
import static HW_03.TransitionOutput.GOTO_SIGN;
import static HW_03.TransitionOutput.GOTO_START;

import HW_03.Token;


enum State {//State Ŭ���� ���� �߰� (#T/F)	���� Ŭ����
	START {//START ����� ���¸� ��Ÿ���� enum
		@Override
		public TransitionOutput transit(ScanContext context) {
			Char ch = context.getCharStream().nextChar();//���� �ϳ��� �о��
			char v = ch.value();	//ch�� ������ �̾����� character ���� �ϳ�.
			switch ( ch.type() ) { 
				case LETTER:
					context.append(v);
					return GOTO_ACCEPT_ID;//������ ���ڷ� �Ұ�� �ĺ��ڷ� �Ǵ��ϰ� �����ϰ� ��
				case DIGIT:
					context.append(v);
					return GOTO_ACCEPT_INT;//���ڰ� ���۵� ��� INT�� �����ϰ� accept ������ ��ħ
				case SPECIAL_CHAR:		//+ or - �׿ܿ� ��ȣ�� 
					context.append(v);//��ȣ�� append()
					context.getCharStream().pushBack(v);//�ش� ��ȣ�� pushback()�� ����ؼ� ����
					return GOTO_SIGN;
				case WS:				//���Ͷ� �����̽��� ws�� ó���ȴ�.
					return GOTO_START;	//���� ���� �����̸� GOTO_START�� �ٽ� ���ư���.
				case END_OF_STREAM:
					return GOTO_EOS;//������ ���̸� EOS�� ����
				case SHARP://# �� ����� ����
					context.append(v);
					return GOTO_ACCEPT_SHARP;
				default:
					throw new AssertionError();
			}
		}
	},
	ACCEPT_ID { //ID �� identifier�� �ǹ�
		@Override
		public TransitionOutput transit(ScanContext context) {
			Char ch = context.getCharStream().nextChar();
			char v = ch.value();
			switch ( ch.type() ) {
				case LETTER://���⼭ keywords���� �ƴ��� Ȯ��
				case DIGIT:
					context.append(v);
					return GOTO_ACCEPT_ID;		//id ���̿� ���ڰ� ���´ٸ� identifier�� �����ϰ� ����
				case SPECIAL_CHAR:
					return GOTO_FAILED;
				case WS:
				case END_OF_STREAM:
					return GOTO_MATCHED(Token.ofName(context.getLexime()));
				default:
					throw new AssertionError();
			}
		}
	},
	ACCEPT_INT {// ------>integer �� accept
		@Override
		public TransitionOutput transit(ScanContext context) {
			Char ch = context.getCharStream().nextChar();
			switch ( ch.type() ) {
				case LETTER:
					return GOTO_FAILED;	//d �ڿ� i�� �´ٸ� fail
				case DIGIT:
					context.append(ch.value());//d�ڿ� d�� �´ٸ� go
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
	SIGN {//��ȣ �� ��ȣ �� �ٸ� ��ȣ�� ����
		@Override
		public TransitionOutput transit(ScanContext context) {
			Char ch = context.getCharStream().nextChar();
			char v = ch.value();	//v�� ���� ��ȣ .
			Char ch2 = context.getCharStream().nextChar();
			char v2 = ch2.value();	//v2�� DIGIT�̶�� ����
			switch ( ch2.type() ) {
				case LETTER:	//��ȣ �ڿ� ���ڰ� ������ fail
					return GOTO_FAILED;
				case DIGIT:
					context.append(v2);//������ �ִ� input�� ���ٰ� ����
					return GOTO_ACCEPT_INT;//SIGN ��ȣ �ڿ� INT �� ������ APPEND�� �ٿ��ְ� INT�� ACCEPT ������ ����.
				case WS:
				case END_OF_STREAM://��ȣ �� �ϳ��� ���������.
					return GOTO_MATCHED(TokenType.fromSpecialCharcter(v) ,context.getLexime());
				default:
					throw new AssertionError();
			}
		}
	},
	MATCHED {
		@Override
		public TransitionOutput transit(ScanContext context) {
			throw new IllegalStateException("at final state");
		}
	},
	FAILED{
		@Override
		public TransitionOutput transit(ScanContext context) {
			throw new IllegalStateException("at final state");
		}
	},
	EOS {//End of Stream
		@Override
		public TransitionOutput transit(ScanContext context) {
			return GOTO_EOS;
		}
	},

	ACCEPT_TRUE {//#T �� ��ū�� accept �ϴ� enum
		@Override
		public TransitionOutput transit(ScanContext context) {
			Char ch = context.getCharStream().nextChar();
			switch ( ch.type() ) {
				case LETTER:
				case DIGIT:
					return GOTO_FAILED;//#T ������ ���ڳ� ���ڰ� ���Եȴٸ� FAILED!
				case WS:
				case END_OF_STREAM:
					return GOTO_MATCHED(TRUE, context.getLexime());//���� ���̻� ���ڰ� ���� �ʴ´ٸ� TRUE��� ��ūŸ���� ������ ��ȯ
				default:
					throw new AssertionError();
			}
		}
	},
	ACCEPT_FALSE {//TRUE�� ���������� ����
		@Override
		public TransitionOutput transit(ScanContext context) {
			Char ch = context.getCharStream().nextChar();
			switch ( ch.type() ) {
				case LETTER:
				case DIGIT:
					return GOTO_FAILED;
				case WS:
				case END_OF_STREAM:
					return GOTO_MATCHED(FALSE, context.getLexime());
				default:
					throw new AssertionError();
			}
		}
	},
	ACCEPT_SHARP {//# �ϳ��� ��쿡 ���ؼ��� enum
		@Override
		public TransitionOutput transit(ScanContext context) {
			Char ch = context.getCharStream().nextChar();
			char v = ch.value();
			switch ( ch.type() ) {
				case LETTER:
					if(v == 'T') {					//#�ڿ� ���� T�� �´ٸ� append�� ���̰� goto_accept_true
						context.append(v);
						return GOTO_ACCEPT_TRUE;
					} else if(v == 'F') {			//#�ڿ� ���� F�� �´ٸ� append�� ���̰� goto_accept_true
						context.append(v);
						return GOTO_ACCEPT_FALSE;
					} else
						return GOTO_FAILED;
				case DIGIT:
					return GOTO_FAILED;
				case WS:
				case END_OF_STREAM:
					return GOTO_MATCHED(SHARP, context.getLexime());
				default:
					throw new AssertionError();
			}
		}
	};

	abstract TransitionOutput transit(ScanContext context);
}
