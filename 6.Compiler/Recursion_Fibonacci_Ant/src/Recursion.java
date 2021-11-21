
public class Recursion {

	// main
	public static void main(String[] args) {
		System.out.println(fibonacci(10));
		System.out.println(recursiveAnt(10));
		System.out.println(recursiveAnt(11));
	}

	//�Ǻ���ġ
	public static int fibonacci(int n) {
		if(n == 1 || n == 2) {  //ù° ��, ��° ���� ��� return 1
			return 1;
		}
		else {    // �� �� ��� ���� �� �ΰ��� ���Ͽ�, n��ġ�� �� return
			return fibonacci(n-1) + fibonacci(n-2);
		}
	}

	//���� ����
	public static String recursiveAnt(int n) {
		if(n == 1) {    // ù°���� ��� "1"
			return "1";
		}
		else if(n == 2) {
			return "11";
		}
		else {   //ù°���� �ƴ� ���
			return makeResult(recursiveAnt(n-1));
		}
	}

	//���� ���� n��° �� ����
	public static String makeResult(String previous){
		String[] sp = previous.split("");   
		String result = "";
		if(previous.length() != 0) { //�켱 ��� �����͸� �б� ���� ��� ����
			result += makeResult(previous.substring(1,sp.length -1));
		}
		else { // ��� ������ �б� �غ� �Ϸ�
			return "";
		}
		String[] resultsp = result.split("");

		if(result.length() == 0) { // ���� �����͸� �дµ�, �����̶��
			if(sp[0].equals(sp[1])) { // �д� �� ������ ���� ������
				return sp[0] + "2";
			}
			else { // �ٸ����
				return sp[0] + "1" + sp[1] + "1";
			}
		}
		else { // �����͸� �дµ� ������ �ƴϸ�
			//���� ������ ����
			if(sp[0].equals(resultsp[0])) { // ���� �����ʹ� ���� ���� ��. ������ ������ �÷��ش�.
				result = sp[0]+ Integer.toString(Integer.parseInt(resultsp[1]) + 1) + result.substring(2, result.length());
			}
			else { // �հ� �ٸ��� ���ο� �� �о��ֱ�.
				result = sp[0] + "1" + result;
			}
			// ���� ������ ����
			if(sp[sp.length-1].equals(resultsp[resultsp.length-2])) {  // ���� ������ �б� �� ������ �б�� ���� ���� ����
				result = result.substring(0, result.length() - 1) + Integer.toString(Integer.parseInt(resultsp[resultsp.length-1]) + 1);
			}
			else {
				result = result + sp[sp.length-1] + "1";
			}
			return result;
		}
	}
}
