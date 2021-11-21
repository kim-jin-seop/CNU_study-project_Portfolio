package Interpreter;

public class BooleanNode implements ValueNode { // ���� ������ BooleanNode Class
	/*BooleanNode�� value������ Boolean�� ���´�.
	 * Value���� True�� ���°�, False�� ���°��� ���� ����� �ٲ��.
	 * toString�� �� ���������ν�, class�� ����ϸ� #T or #F�� ��µǰ� �����Ͽ���.*/
	Boolean value;

	@Override
	public String toString(){
		return value ? "BOOLEAN: #T" : "BOOLEAN: #F";
	}
	public static BooleanNode FALSE_NODE = new BooleanNode(false);
	public static BooleanNode TRUE_NODE = new BooleanNode(true);
	public BooleanNode(Boolean b){
		value = b;
	}
}