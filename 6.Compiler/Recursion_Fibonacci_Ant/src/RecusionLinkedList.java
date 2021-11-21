import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class RecusionLinkedList {
	private Node head;
	private static char UNDEF = Character.MIN_VALUE;

	/**
	 * ���Ӱ� ������ ��带 ����Ʈ�� ó������ ����
	 */
	private void linkFirst(char element) {
		head = new Node(element, head);
	}
	/**
	 * ���� (1) �־��� Node x �� ���������� ����� Node �� �������� ���Ӱ� ������ ��带 ����
	 *
	 * @param element
	 * ������
	 * @param x
	 * ���
	 */

	private void linkLast(char element, Node x) {
		if (x.next == null) //���� ���Ұ� ��������� 
			x.next = new Node(element, null); //�ٷ� ����
		else //���� ��� �湮 recursion
			linkLast(element,x.next); // ���� ��ġ ���� �ٽ� Ȯ��
	}

	/**
	 * ���� Node �� ���� Node �� ���Ӱ� ������ ��带 ����
	 *
	 * @param element
	 * ����
	 * @param pred
	 * �������
	 */
	private void linkNext(char element, Node pred) {
		Node next = pred.next;
		pred.next = new Node(element, next);
	}
	/**
	 * ����Ʈ�� ù��° ���� ����(����)
	 *
	 * @return ù��° ������ ������
	 */
	private char unlinkFirst() {
		Node x = head;
		char element = x.item;
		head = head.next;
		x.item = UNDEF;
		x.next = null;
		return element;
	}
	/**
	 * ���� Node �� ���� Node ���� ����(����)
	 *
	 * @param pred
	 * �������
	 * @return ��������� ������
	 */
	private char unlinkNext(Node pred) {
		Node x = pred.next;
		Node next = x.next;
		char element = x.item;
		x.item = UNDEF;
		x.next = null;
		pred.next = next;
		return element;
	}
	/**
	 * ���� (2) x ��忡�� index ��ŭ ������ Node ��ȯ
	 */
	private Node node(int index, Node x) {
		Node findNode = null; //ã�� ��ġ�� ���
		if(index != 0) { // index�� 0�� �ƴϸ� ��� �湮
			findNode = node(index-1,x.next); //index �ٿ����� ���� ��Ʈ �湮
			return findNode;
		}
		return x;
		// ä���� ���, index �� �ٿ����鼭 ���� ��� �湮
	}

	/**
	 * ���� (3) ���κ��� �������� ����Ʈ�� ��� ���� ��ȯ
	 */
	private int length(Node x) {
		int size = 1;   // �ڱ��ڽ� ���� 1
		if(x.next != null) {  //������ ������ ��ͷ� ���� ����
			size += length(x.next);
		}
		else {
			return 1; // ������ ã���� 1 �����ϸ� ��� ��� ������
		}
		return size; // ���������� �߰��Ͽ��ֱ�.
	}
	/**
	 * ���� (4) ���κ��� �����ϴ� ����Ʈ�� ���� ��ȯ
	 */
	private String toString(Node x) {
		String result = Character.toString(x.item) + " ";
		if(x.next != null) {
			result = result + toString(x.next);
		}
		return result;
	}
	/**
	 * ���� ����� ���� ������ ����Ʈ�� �������� �Ųٷ� ����
	 * ex)��尡 [s]->[t]->[r]�� ��, reverse ���� �� [r]->[t]->[s]
	 * @param x
	 * ���� ���
	 * @param pred
	 * �������� ���� ���
	 */
	private void reverse(Node x, Node pred) {
		if(x.next != null) {
			reverse(x.next, x);
		}
		else {
			head = x;
		}
		x.next = pred;
	}

	/**
	 * ���Ҹ� ����Ʈ�� �������� �߰�
	 */
	public boolean add(char element) {
		if (head == null) {
			linkFirst(element);
		} else {
			linkLast(element, head);
		}
		return true;
	}
	/**
	 * ���Ҹ� �־��� index ��ġ�� �߰�
	 *
	 * @param index
	 * ����Ʈ���� �߰��� ��ġ
	 * @param element
	 * �߰��� ������
	 */
	public void add(int index, char element) {
		if (!(index >= 0 && index <= size()))
			throw new IndexOutOfBoundsException("" + index);
		if (index == 0)
			linkFirst(element);
		else
			linkNext(element, node(index - 1, head));
	}
	/**
	 * ����Ʈ���� index ��ġ�� ���� ��ȯ
	 */
	public char get(int index) {
		if (!(index >= 0 && index < size()))
			throw new IndexOutOfBoundsException("" + index);
		return node(index, head).item;
	}
	/**
	 * ����Ʈ���� index ��ġ�� ���� ����
	 */
	public char remove(int index) {
		if (!(index >= 0 && index < size()))
			throw new IndexOutOfBoundsException("" + index);
		if (index == 0) {
			return unlinkFirst();
		}
		return unlinkNext(node(index - 1, head));
	}
	/**
	 * ����Ʈ�� �Ųٷ� ����
	 */
	public void reverse() {
		reverse(head, null);
	}
	/**
	 * ����Ʈ�� ���� ���� ��ȯ
	 */
	public int size() {
		return length(head); 
	}

	@Override
	public String toString() {
		if (head == null) //�ƹ��͵� ���� ��
			return "[]";
		return "[ " + toString(head) + "]";  
	}

	/*
	 * ����Ʈ�� ���� �ڷᱸ��
	 */
	private static class Node {
		char item; // Node�� ���ԵǾ��ִ� ������
		Node next; // Node�� ������ġ
		Node(char element, Node next) { // ������
			this.item = element;
			this.next = next;
		}
	}
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		RecusionLinkedList list = new RecusionLinkedList();
		FileReader fr;
		try {
			fr = new FileReader("hw01.txt");
			BufferedReader br = new BufferedReader(fr);
			String inputString = br.readLine();
			for(int i = 0; i < inputString.length(); i++)
				list.add(inputString.charAt(i));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(list.toString());
		list.add(3, 'b'); System.out.println(list.toString());
		list.reverse(); System.out.println(list.toString());
	}
}

