import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class hw02{
	public enum TokenType{  //Token�� ���� ����
		ID(3), INT(2);   // state�� 3�� ��� id, 2�� ��� int

		private final int finalState; 

		TokenType(int finalState) {
			this.finalState = finalState;
		}
	}


	public static class Token { //Token �Ұ�
		public final TokenType type;
		public final String lexme;

		Token(TokenType type, String lexme) {
			this.type = type;
			this.lexme = lexme;
		}

		@Override
		public String toString() { //Token ��� �� ��� ���
			return String.format("[%s: %s]", type.toString(), lexme);
		}
	}

	public static class Scanner{ //Scanner class
		private StringTokenizer st; 
		private int transM[][]; //transition Matrix
		private String source;  //�޾ƿ� ������

		public Scanner(String source) {
			this.transM = new int[4][128];
			this.source = source == null ? "" : source;
			st = new StringTokenizer(this.source);  // ������ �޾Ƽ� �ɰ���.
			initTM();
		}

		private void initTM() { //transition Matrix ����
			
			//���� -1�� �켱 �ʱ�ȭ
			for(int i = 0 ; i < 4; i ++) {
				for(int j = 0; j < 128; j++) {
					transM[i][j] = -1;
				}
			}

			//state = 0�� ��
			for(int i = 0; i < 10; i ++) {             //Digit�� ���� ��
				transM[0]['0'+i] = 2;
			}
			transM[0]['-'] = 1;      // -�� ���� ��
			for(int i = 0; i < 26; i++) {              // ���ĺ��� ���� ��
				transM[0]['a' + i] = 3;
				transM[0]['A' + i] = 3;
			}

			//state = 1�� ��
			for(int i = 0; i < 10; i++) {               //Digit�� ���� ��
				transM[1]['0'+i] = 2;
			}

			//state = 2�� ��
			for(int i = 0; i < 10; i++) {               //Digit�� ���� ��
				transM[2]['0'+i] = 2;
			}

			//state = 3�� ��
			for(int i = 0; i < 26; i++) {                //���ĺ��� ���� ��
				transM[3]['a' + i] = 3;
				transM[3]['A' + i] = 3;
			}
			for(int i = 0; i < 10; i++) {               //Digit�� ���� ��
				transM[3]['0'+i] = 3;
			}
		}

		
		private Token nextToken(){	
			int stateOld = 0, stateNew;

			//��ū�� �� �ִ��� �˻�
			if(!st.hasMoreTokens()) return null;

			//�� ���� ��ū�� ����
			String temp = st.nextToken();

			Token result = null;	
			for(int i = 0; i<temp.length();i++){
				//���ڿ��� ���ڸ� �ϳ��� ������ ���� �Ǻ�
				stateNew = transM[stateOld][temp.charAt(i)];

				if(stateNew == -1){
					//�Էµ� ������ ���°� reject �̹Ƿ� �����޼��� ����� return��
					System.out.println(String.format("acceptState error %s\n", temp)); return null;
				}
				stateOld = stateNew;
			}
			for (TokenType t : TokenType.values()){
				if(t.finalState == stateOld){
					result = new Token(t, temp);
					break;
				}
			}			
			return result;	
		}

		//tokenize
		public List<Token> tokenize() {
			List<Token> list = new ArrayList<Token>(); //List ����
			list.add(nextToken()); //��ū �߰�
			return list; // ��ū ���� ����Ʈ return
		}
	}

	public static void main(String[] args){
		FileReader fr; //���� �б�
		try {
			fr = new FileReader("as02.txt");
			BufferedReader br = new BufferedReader(fr);
			String split[] = br.readLine().split(" "); //������ �ɰ��� �ֱ� �ɰ��� ������ = source
			for(int i = 0; i < split.length; i++) { //for���� �̿��� �ɰ����� ������ ��� �ֱ�
				String source = split[i];
				Scanner s = new Scanner(source);
				List<Token> tokens = s.tokenize();
				System.out.println(tokens.get(0)); //get�Ͽ� token�����ͼ�  ���
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}