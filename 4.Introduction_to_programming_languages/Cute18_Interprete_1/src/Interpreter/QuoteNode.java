package Interpreter;

public class QuoteNode implements Node { // ���� �߰��� QuoteNode Class
	/*QuoteNode�� ���� �߰��� ��������̴�.
	 * QuoteNode�� �����ڷ� Node�� ������ �ִµ�, �̴� CuteParser Ŭ�������� ����ϴ°��� ����, parseExpr()�޼ҵ带 ������ ���������, '�ڿ� ������ ������� �� �� �ִ�.
	 * nodeInside�� ���� ��带 ����Ų��. */
	Node quoted;
	public QuoteNode(Node quoted) {
		this.quoted = quoted;
	}
	@Override
	public String toString(){
		return quoted.toString();
	}
	public Node nodeInside() {
		// TODO Auto-generated method stub
		return quoted;
	}
}
