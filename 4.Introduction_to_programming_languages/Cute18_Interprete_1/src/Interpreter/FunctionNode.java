package Interpreter;
import java.util.HashMap;
import java.util.Map;

public class FunctionNode implements ValueNode{ //binaryOpNodeŬ������ ���� �����ؼ� �ۼ� 
	/*���� �� ������ ũ�� �ٲ����� �����Ƿ� ������ �����մϴ�.*/
		enum FunctionType{ //FunctionType enum
			ATOM_Q{TokenType tokenType(){return TokenType.ATOM_Q;}},
			CAR{TokenType tokenType(){return TokenType.CAR;}},
			CDR{TokenType tokenType(){return TokenType.CDR;}},
			COND{TokenType tokenType(){return TokenType.COND;}},
			CONS{TokenType tokenType(){return TokenType.CONS;}},
			DEFINE{TokenType tokenType(){return TokenType.DEFINE;}},
			EQ_Q{TokenType tokenType(){return TokenType.EQ_Q;}},
			LAMBDA{TokenType tokenType(){return TokenType.LAMBDA;}},
			NOT{TokenType tokenType(){return TokenType.NOT;}},
			NULL_Q{TokenType tokenType(){return TokenType.NULL_Q;}};
			private static Map<TokenType, FunctionType> fromTokenType = new HashMap<TokenType, FunctionType>();
			
			//map�� �����Ͱ� �ֱ�. key ��  value ( TokenType�� FunctionType)
			static {
				for(FunctionType fType : FunctionType.values()) {
					fromTokenType.put(fType.tokenType(), fType);  
				}
			}
			
			//map���� �ش� FunctionType ������ ��������.
			static FunctionType getFunctionType(TokenType fType) {
				return fromTokenType.get(fType);
			}
			abstract TokenType tokenType();
		}
		public FunctionType value;
		
		/*setValue�� �����, �����ڸ� �߰������� ���*/
		public FunctionNode(TokenType tType) {
			FunctionType fType = FunctionType.getFunctionType(tType);
			value = fType;
		}
		@Override
		public String toString(){ //���� ä���
			return value.name();
		}
	}
