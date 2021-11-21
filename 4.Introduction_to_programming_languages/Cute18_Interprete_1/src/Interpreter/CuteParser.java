package Interpreter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;

public class CuteParser {
	private Iterator<Token> tokens; 
	private static Node END_OF_LIST = new Node(){}; // ���� �߰��� �κ�

	//������ -> ������ �о�´�.
	public CuteParser(File file) {
		try {
			tokens = Scanner.scan(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//���� ��ū�� ��������.
	private Token getNextToken() {
		if (!tokens.hasNext()) //��ū�� ���� ��
			return null;
		return tokens.next(); // ��ū�� ���� ��
	}

	//parse ����
	public Node parseExpr() {
		Token t = getNextToken();  // ��ū ��� �б�
		if (t == null) {  // token��  null�� ��� �� ��ū�� ���� ��
			System.out.println("No more token");
			return null;
		}
		TokenType tType = t.type(); // token�� type
		String tLexeme = t.lexme(); 

		switch (tType) {//TokenType�� ���� �ٸ��� ����
		case ID: // ID�� ��� ����
			IdNode idNode = new IdNode(tLexeme);
			return idNode;
		case INT: //Int�� ��� ����
			if(tLexeme == null) {
				System.out.println("???");
			}
			return new IntNode(tLexeme);
			
			// BinaryOpNode�� ��� ����
		case DIV:
		case EQ:
		case MINUS:
		case GT:
		case PLUS:
		case TIMES:
		case LT:
			BinaryOpNode binaryNode = new BinaryOpNode(tType);
			return binaryNode;
			// FunctionNode�� ��� ����
		case ATOM_Q:
		case CAR:
		case CDR:
		case COND:
		case CONS:
		case DEFINE:
		case EQ_Q:
		case LAMBDA:
		case NOT:
		case NULL_Q:
			FunctionNode functionNode = new FunctionNode(tType);
			return functionNode;
			// BooleanNode
		case FALSE:
			return BooleanNode.FALSE_NODE;
		case TRUE:
			return BooleanNode.TRUE_NODE; 
			//���� ������ L_PAREN, R_PAREN Case
		case L_PAREN: // ���� ��ȣ�϶���, List�� ���ۤӹǷ� pareExperList�� ���� ListNode�� ��������.
			return parseExprList(); 
		case R_PAREN:
			return END_OF_LIST ; // ������ ��ȣ�� List�� ���� �ǹ��Ѵ�.
			//���� �߰��� APOSTROPHE, QUOTE
		case APOSTROPHE: //APOSTROPHE�� QUOTE�� ��� ���� ��ġ�� Node���� ������ ���Ӱ� ������� ���� return�Ͽ��ش�.
		case QUOTE:
			return new QuoteNode(parseExpr());
		default:
			System.out.println("Parsing Error!");
			return null;
		}
	}
	// List�� value�� �����ϴ� �޼ҵ�
	private ListNode parseExprList() {
		Node head = parseExpr(); //parseExpr�� ���Ͽ� �� �� Node�ϳ� �����´�.
		if (head == null)
			return null;
		if (head == END_OF_LIST) // if next token is RPAREN
			return ListNode.ENDLIST;
		
		ListNode tail = parseExprList();  // tail�� parseExprList�� ������ش�.
		if (tail == null) return null;
		return ListNode.cons(head, tail); //cons�� List�� node�� ä���.
	}
}