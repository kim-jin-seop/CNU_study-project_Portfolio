package Interpreter;

// IdNode�� ����
public class IdNode implements ValueNode{
	/*IdNode�� Ư���� ��츦 ������ String ���� �ǹ��Ѵ�.*/
	// ���� ������ IdNode Class
	String idString;
	public IdNode(String text) {
		idString = text;
	}
	@Override
	public String toString(){
		return "ID: " + idString;
	}
}
