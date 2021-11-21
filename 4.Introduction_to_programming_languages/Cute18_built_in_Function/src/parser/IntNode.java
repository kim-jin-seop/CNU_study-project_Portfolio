package parser;

// IntNode�� ����
public class IntNode implements ValueNode { // ���� ������ IntNode
	/*IntNode�� value������ Integer�� ���´�.
	 * �����ڷ�, text�� �޾Ƽ� Integer�� �����Ѵ�.*/
	public Integer value; // value ��
	@Override
	public String toString(){
		return "INT: " + value;
	}
	public IntNode(String text) {
		this.value = new Integer(text);
	}
}