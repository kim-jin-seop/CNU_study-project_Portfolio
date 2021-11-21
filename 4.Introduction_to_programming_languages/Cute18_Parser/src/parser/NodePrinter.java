package parser;
import java.io.PrintStream;

public class NodePrinter {
		PrintStream ps;
		public static NodePrinter getPrinter(PrintStream ps) {     ////////////static ����
			return new NodePrinter(ps);
		}
		private NodePrinter(PrintStream ps) {
			this.ps = ps;
		}
		
		//ListNode�� ��� ���
		private void printList(Node head) {
			if (head == null) { // head�� �����Ͱ� ���� ��
				ps.print("( ) ");
				return;
			}
			//head�� �����Ͱ� ������
			ps.print("( "); // ( ����
			printNode(head); // head ������ ����
			ps.print(") "); // ) ����
		}
		
		
		private void printNode(Node head) { 
			if (head == null) return; //�� ã���� ���� 
			if (head instanceof ListNode) { // List Node�� ���
				ListNode ln = (ListNode) head; 
				printList(ln.value); // printList -> ListNode�� ��� ���� ����
			} else {
				ps.print("[" + head + "] ");
			}
			printNode(head.getNext()); //������ ��������
		}
		
		public void prettyPrint(Node root){ // Node Print
			printNode(root);
		}
	}