	package parser;
import java.io.PrintStream;

public class NodePrinter {
	/*NodePrinter class�� ����� ���� ������� Ŭ����
	 * printNode�� ListNode, Node, QuoteNode 3���� Node�� �Ķ���͸� ���� ������ Overload�Ͽ� �����.
	 **/
	PrintStream ps;
	public static NodePrinter getPrinter(PrintStream ps) {     ////////////static ����
		return new NodePrinter(ps);
	}
	private NodePrinter(PrintStream ps) {
		this.ps = ps;
	}

	// ListNode, QuoteNode, Node�� ���� printNode �Լ��� ���� overload �������� �ۼ�
	/*ListNode�� printNode
	 * ListNode�� ���� printNode�� �����Ͽ��ش�.
	 * ����, listNode�� ����ִٸ�, ( )�� ����Ͽ��ְ�
	 * ����, ListNode�� �������� ����Ű�� �ٷ� �����Ѵ�.
	 * �ƴ� ��쿡�� (�� ���� car()�� ���Ͽ� ���� �� �� List�� �ٽ� ListNode�� �ҷ� ����Ͽ� �� �� List ��� ó���ϵ��� �����Ѵ�.
	 * ���������� )�� ����Ͽ��ش�.
	 **/
	private void printNode(ListNode listNode) {
		if (listNode == ListNode.EMPTYLIST) {
			ps.print("( ) ");
			return;
		}
		if (listNode == ListNode.ENDLIST) {
			return;
		}
		ps.print("( ");
		printNode(listNode.car());
		printNode(listNode.cdr());
		ps.print(") "); 
	}
	
	/*QuoteNode�� printNode
	 * quoteNode�� printNode�� �켱 quoteNode�� quoted�� Ȯ���Ѵ�.
	 * null�� ��� �ڿ� Node�� �����Ƿ� return�Ͽ��ش�.
	 * �ƴҰ�쿡�� '�� ����� ��, quoted�� �ҷ� Node�� printNode�� ����Ѵ�.
	 */
	private void printNode(QuoteNode quoteNode) {
		if (quoteNode.nodeInside() == null)
			return; 
		ps.print("'");
		printNode(quoteNode.quoted);
	}

	/* Node�� printNode
	 * Node�� null�̸� �ٷ� return
	 * ���� ListNode���, ListNode ����
	 * QuoteNode��� QuoteNode ����
	 * �� ���� ��쿡�� node�� ���
	 */
	private void printNode(Node node) {
		if (node == null)
			return; 
		if(node instanceof ListNode)
			printNode((ListNode)node);
		else if(node instanceof QuoteNode) {
			printNode((QuoteNode)node);
		}
		else {
			ps.print("[" + node + "] ");
		}
	}

	public void prettyPrint(Node root){ // Node Print
		printNode(root);
	}
}