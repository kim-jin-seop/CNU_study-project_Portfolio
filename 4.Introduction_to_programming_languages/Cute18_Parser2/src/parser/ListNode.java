package parser;
//ListNode�� ���� 
public interface ListNode extends Node { // ���� ������ ListNode
	
	/*car() �޼ҵ�� �� ó�� ��ġ�ϴ� List�� �����̴�. (Node)
	 *cdr() �޼ҵ�� List�����߿� �� ���������̴�. (�� �� Node ������ �κ�)
	 *���� NodePrinter�� �Ͽ��� ��, car()�� �̿��� �� �� List�� ���Ҹ� �������� �� �� cdr()�� List�� �̿��� ��� List�� ������ش�.(���Ŀ� NodePrinter �κп��� �߰������� ����)
	 *cons�� Node���� 1���� �Ѱ��� List�� �ٿ��� ���ο� List�� ����� ���̴�.
	 *List ���ҿ� ��� ���� �ϳ��� �ٿ��� ���ο� List�� ����� ����̴�. 
	 *EMPTYLIST�� ������� ��� �̰�, ENDLIST�� ���� ����̴�.
	 *EMPTYLIST�� ��쿡�� ()�� ����ϵ��� �ϱ� �����̰�, ENDLIST�� LIST�� ���̹Ƿ� return���ֱ� �����̴�.
	 **/
	
	
	Node car(); // List�� �� ó�� ����
	ListNode cdr(); //List�� ������
	static ListNode cons(Node head, ListNode tail){
		return new ListNode(){
			@Override
			public Node car() {
				return head;
			}
			@Override
			public ListNode cdr() {
				return tail;
			}
		};
	}
	static ListNode EMPTYLIST = new ListNode(){
		@Override
		public Node car() {
			return null;
		}
		@Override
		public ListNode cdr() {
			return null;
		}
	};
	
	static ListNode ENDLIST = new ListNode(){
		@Override
		public Node car() {
			return null;
		}
		@Override
		public ListNode cdr() {
			return null;
		}
	};
}