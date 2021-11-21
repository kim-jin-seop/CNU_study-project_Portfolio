import java.util.Stack;

import ast.*;
import compile.TreeFactory;

public class PL_4 {
	public static int max(Node node) {
		Stack<ListNode> stack = new Stack();      //�ٸ� List�� ������ �� Stack�� �ְ�, ���߿� ��� ������ Ȯ���ϱ�.
		Node findNode = ((ListNode) node).value;  //��� ��ġ �Űܰ��鼭 �� ã��
		int max = Integer.MIN_VALUE;              //�ִ밪 ã�� (�켱 MIN������ �ʱ�ȭ)

		while(true) {                             //��� ������ Ȯ��
			if(findNode instanceof ListNode) {    //A instanceof B -> A�� B Ŭ�����κ��� ���Դ��� Ȯ�� 
				stack.push((ListNode) findNode);  //stack�� �̿��Ͽ� ���� ListNode��� �켱 �־�д�.(�ٸ� ����Ʈ)
			}
			else if(findNode instanceof IntNode) {   //���� �����Ͷ��, ���Ͽ��ش�.
				if(((IntNode)findNode).value > max) {//findNode�� ���� max������ ū�� Ȯ���ϰ�,
					max = ((IntNode)findNode).value; //ũ�� max�� ��ü
				}
			}
			if(findNode.getNext() == null && stack.isEmpty()) { //����, ������ġ�� ���� null�̶��, stack�� ����ִ��� Ȯ��
				break; //stack���� ����ִٸ�, break�Ͽ� max�� ã�� ����
			}
			else if(findNode.getNext() == null) { //stack�� �Ⱥ���ִٸ�
				findNode = stack.pop().value;     //stack���� �ϳ� ������ ������ �ֱ�
				while(findNode == null)           //�װ��� �����Ͱ� ���� ���
					if(!stack.isEmpty()) {        //stack�� ����ִٸ�
						findNode = stack.pop().value;  //������ ��� ��������
					}else {                            //Stack�� ������� �ʴٸ�
						return max;                    //max�� return �ϱ�
					}
				continue;
			}
			findNode = findNode.getNext();
		}
		return max;
	}

	public static int sum(Node node) {
		Stack<ListNode> stack = new Stack();      //�ٸ� List�� ������ �� Stack�� �ְ�, ���߿� ��� ������ Ȯ���ϱ�.
		Node findNode = ((ListNode) node).value;  //��� ��ġ �Űܰ��鼭 �� ã��
		int sum = 0;                              //�� �� 

		while(true) {                             //��� ������ Ȯ��
			if(findNode instanceof ListNode) {    //A instanceof B -> A�� B Ŭ�����κ��� ���Դ��� Ȯ�� 
				stack.push((ListNode) findNode);  //stack�� �̿��Ͽ� ���� ListNode��� �켱 �־�д�.(�ٸ� ����Ʈ)
			}
			else if(findNode instanceof IntNode) {   //���� �����Ͷ��, ���Ͽ��ش�.
				sum += ((IntNode)findNode).value;    //��� ���ϱ�(sum)
			}
			if(findNode.getNext() == null && stack.isEmpty()) { //����, ������ġ�� ���� null�̶��, stack�� ����ִ��� Ȯ��
				break; //stack���� ����ִٸ�, break�Ͽ� �� �� return
			}
			else if(findNode.getNext() == null) { //stack�� �Ⱥ���ִٸ�
				findNode = stack.pop().value;     //stack���� �ϳ� ������ ������ �ֱ�
				while(findNode == null)           //�װ��� �����Ͱ� ���� ���
					if(!stack.isEmpty()) {        //stack�� ����ִٸ�
						findNode = stack.pop().value;  //������ ��� ��������
					}else {                            //Stack�� ������� �ʴٸ�
						return sum;                    //sum�� return �ϱ�
					}
				continue;
			}
			findNode = findNode.getNext();
		}
		return sum;
		
	}

	public static void main(String... args) {
		Node node = TreeFactory.createtTree("( ( 3 ( ( 10 ) ) 6 ) 4 1 ( ) -2 ( ) )");
		System.out.println("�ִ� �� : " + max(node));
		System.out.println("���� : " + sum(node));
		//���� ����� ����ϵ��� �ۼ�
	}
}
