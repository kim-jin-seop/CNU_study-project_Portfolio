package Interpreter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CuteInterpreter {

	private void errorLog(String err) {
		System.out.println(err);
	}

	//runExpr�� ��忡 ���� ������ ó���Ͽ��ִµ�, ListNode�� ��� runList�� ��������ش�.
	public Node runExpr(Node rootExpr) {
		if (rootExpr == null)
			return null;
		if (rootExpr instanceof IdNode)
			return rootExpr;
		else if (rootExpr instanceof IntNode)
			return rootExpr;
		else if (rootExpr instanceof BooleanNode)
			return rootExpr;
		else if (rootExpr instanceof ListNode)
			return runList((ListNode) rootExpr);
		else
			errorLog("run Expr error");
		return null;
	}

	/*runList�� ��� ListNode�� ���� ��� ó�����ش�.
	 * ����, list�� ����ִٸ� list�� �׳� return�Ͽ��ش�. 
	 * ���� list�� �����ڰ� functionNode��� runFunction��
	 * BinaryOpNode��� runBinary�� �����Ͽ��ش�.*/
	private Node runList(ListNode list) {
		if(list.equals(ListNode.EMPTYLIST))
			return list;
		if(list.car() instanceof FunctionNode){
			return runFunction((FunctionNode)list.car(), list.cdr());
		}
		if(list.car() instanceof BinaryOpNode){
			return runBinary(list);
		}
		return list;
	}

	/*CAR����, CDR����, CONS���� �� Ư���� ������ �����Ѵ�.*/
	private Node runFunction(FunctionNode operator, ListNode operand) {
		switch (operator.value){ 
		/*CAR������ �����Ѵ�. �׻� QuoteNode�� ���ԵǹǷ�  runQuote�� ���� ��Ų �� car()�� �����´�.*/
		case CAR :
			return ((ListNode)runQuote(operand)).car();
			/*CDR������ �����Ѵ�.
			 * �׻� QuoteNode�� ���Ƿ� runQuote�Ͽ� �� �� cdr()�� �����´�.*/
		case CDR :
			return new QuoteNode(((ListNode)runQuote(operand)).cdr());
			/*CONS������ �����Ѵ�.
			 * CONS������ ���� �켱 car�κ��� ������ش�. �׸���, ���� car�� QuoteNode��� runQuote�� �̿��� car�� �־��ش�.
			 * �׸��� cdr�� runQuote�� ���� ����� �ش�. �� �� new������ �̿��� cons�� ����� return�Ѵ�.*/
		case CONS :
			Node car = operand.car();
			if(car instanceof QuoteNode) {
				car = ((ListNode)runQuote(operand));
			}
			ListNode cdr = (ListNode)runQuote(operand.cdr());
			return new QuoteNode(ListNode.cons(car,cdr));
			/*����ִ��� Ȯ���ϴ� NULL_Q�̴�.
			 * runQuote�� �̿��Ͽ� ���� ���� �� �պκ��� EMPTYLIST�� ����, cdr()�κе� ������ NULL�� ����Ͽ� true return
			 * �ƴҰ�� false return*/
		case NULL_Q :
			if(((ListNode)runQuote(operand)).car() == (ListNode.EMPTYLIST).car() && ((ListNode)runQuote(operand)).cdr() == (ListNode.EMPTYLIST).cdr()) {
				return BooleanNode.TRUE_NODE;
			}
			return BooleanNode.FALSE_NODE;
			/*ATOM_Q�� ����Ʈ�� �ƴѰ� Ȯ���ϴ°��̴�.
			 * ���� ����Ʈ��� false �ƴ϶�� true�� return �Ѵ�.
			 * runQuote�� �̿��� QuoteNode�� ���� Ȯ���Ͽ�
			 * ���� ListNode��� false��
			 * �ƴ϶�� True�� return�Ѵ�.*/
		case ATOM_Q :
			Node oper_atom = runQuote(operand);
			if(oper_atom instanceof ListNode) {
				return BooleanNode.FALSE_NODE;
			}
			return BooleanNode.TRUE_NODE;
			/*EQ_Q�� ������ Ȯ���ϴ� ���̴�.
			 * ���� �� ����� �켱 ������ �Ѵ�.
			 * �׸��� �� Node�� ListNode���� IdNode���� IntNode���� Ȯ���� �ϰ� ó���Ѵ�.
			 * ���� ListNode��� ���ѷ����� �̿��Ͽ� �ΰ��� List�� ���� �ϳ��ϳ��� ���� ������ Ȯ���Ѵ�.
			 * ����, �ٸ� ���Ұ� ���Դٸ�, break�� �ϰ� false�� return�ϰ�, ������ �� ã���� true�� �����Ѵ�.
			 * ���� IdNode�� IntNode��� �׿� �´� value���� Ȯ���� �� ������ true �ٸ��� false�� return�Ѵ�. */
		case EQ_Q :
			Node oper_eq1 = ((QuoteNode)operand.car()).quoted;
			Node oper_eq2 = runQuote(operand.cdr());
			Boolean check = false;

			if(oper_eq1 instanceof ListNode && oper_eq2 instanceof ListNode) {
				ListNode checkList1 = (ListNode)oper_eq1;
				ListNode checkList2 = (ListNode)oper_eq2;
				Node check_listNode1 = checkList1.car();
				Node check_listNode2 = checkList2.car();
				boolean breakcheck = false;
				while(true) {
					if(check_listNode1 == null && check_listNode2 == null) {
						break;
					}
					if(check_listNode1 instanceof IdNode && check_listNode2 instanceof IdNode) {
						String string_check1 = ((IdNode)check_listNode1).idString;
						String string_check2 = ((IdNode)check_listNode2).idString;
						if(!string_check1.equals(string_check2)) {
							breakcheck = true;
							break;
						}
					}
					else if(check_listNode1 instanceof IntNode && check_listNode1 instanceof IntNode) {
						int int_check1 = ((IntNode)check_listNode1).value;
						int int_check2 = ((IntNode)check_listNode2).value;
						if(int_check1 != int_check2) {
							breakcheck = true;
							break;
						}
					}
					else {
						breakcheck = true;
						break;
					}
					checkList1 = checkList1.cdr();
					checkList2 = checkList2.cdr();
					check_listNode1 = checkList1.car();
					check_listNode2 = checkList2.car();
				}
				if(!breakcheck)
					check = true;
			}
			else if(oper_eq1 instanceof IdNode && oper_eq2 instanceof IdNode) {
				String string_check1 = ((IdNode)oper_eq1).idString;
				String string_check2 = ((IdNode)oper_eq2).idString;
				if(string_check1.equals(string_check2))
					check = true;
			}
			else if(oper_eq1 instanceof IntNode && oper_eq2 instanceof IntNode) {
				int int_check1 = ((IntNode)oper_eq1).value;
				int int_check2 = ((IntNode)oper_eq1).value;
				if(int_check1 == int_check2)
					check = true;
			}

			if(check){
				return BooleanNode.TRUE_NODE;
			}
			return BooleanNode.FALSE_NODE;

			/*not�� true�� ���� false�� false�� ���� true�� ��ȯ�Ͽ��ش�.
			 * oper_not�� ����� �ű⿡ operand�� �־��ش�.
			 * �� �� operand�� car��ġ�� ���� ListNode���, runExpr�� ���Ͽ� ListNode�� ó���Ͽ��� �� �� ���(�׻� �������� ��Ȳ�� �ȵ����Ƿ� �̰��� ������ Boolean)�� 
			 * Boolean���� ����ȯ �� �� �����Ѵ�.
			 * ����, BooleanNode�� �ٷγ��Դٸ�, �� ���� �־��ְ�, true�� false false�� true�� ��ȯ�Ͽ� return�Ѵ�*/
		case NOT :
			Node oper_not = runExpr(operand);
			if(operand.car() instanceof ListNode) {
				oper_not  = (BooleanNode)runExpr((ListNode)operand.car());
			}
			else if(operand.car() instanceof BooleanNode){
				oper_not = (BooleanNode)operand.car();
			}

			if(((BooleanNode)oper_not).value) {
				return BooleanNode.FALSE_NODE;
			}
			return BooleanNode.TRUE_NODE;
			/*COND�� ���ǹ����� �Ǿտ� true�� ������ �ٷ� �� ������� return�ϴ� ��ɾ��̴�.
			 * �켱 ���������� List�ȿ� List�� �ִ� �����̰�, List���� List���� ó������ Boolean �� �ڿ��� � ���� �־���Ѵ�.
			 * ����, true���� �ֳ�, List�� List�� car()�κ��� Ȯ���Ͽ� ã�´�.
			 * ���� true�� ���Ҵٸ�, ��� �� ��ġ�� cdr�κ��� runExpr�Ͽ� return�Ͽ��ش�.*/
		case COND : 
			ListNode checkList = (ListNode)operand.car();
			ListNode nextList = operand.cdr();
			BooleanNode find = null;
			while(true) {
				if(checkList.car() instanceof ListNode) {
					find  = ((BooleanNode)runExpr((ListNode)checkList.car()));
				}
				else if(checkList.car() instanceof BooleanNode){
					find = ((BooleanNode)checkList.car());
				}
				if(find.value)
					return runExpr(checkList.cdr());

				if(nextList == ListNode.EMPTYLIST || nextList == ListNode.EMPTYLIST) {
					return null;
				}
				checkList = (ListNode)nextList.car();
				nextList = nextList.cdr();
			}
		default:
			break;
		}
		return null;
	}

	/*������� ó���ϱ�, < > = ó���ϱ�
	 * �켱, BinaryOpNode�� �����ڸ� list.car()�� �̿��Ͽ� �����ɴϴ�.
	 * �׸���, �� ���� �����ʹ� �ǿ�����(list.cdr())�� �켱 operands�� �־�Ӵϴ�.
	 * runBinary�� IntNode�� ������ �����ϱ� ���� �޼ҵ��̹Ƿ� operand1,2�� IntNode�� �־�Ӵϴ�.
	 * �� ��, runExpr�� ������ ����� �����ɴϴ�.
	 * �� �� ������ �����մϴ�. */
	private Node runBinary(ListNode list) {
		BinaryOpNode operator = (BinaryOpNode) list.car(); // ������������ �ʿ��� ���� �� �Լ� �۾� ����
		ListNode operands = (ListNode) list.cdr();
		IntNode operand1 = (IntNode) runExpr(operands.car());
		IntNode operand2 = (IntNode) runExpr(operands.cdr().car());
		switch (operator.value){
		/*PLUS ���꿡 ��쿡��, operand1�� operand2�� ������ IntNode�� return �Ͽ��ݴϴ�.*/
		case PLUS :
			return  new IntNode(String.valueOf(operand1.value + operand2.value));
			/*MINUS ���꿡 ��쿡�� operand1���� operand2�� ���� ����� IntNode�� �ְ� return�Ͽ��ݴϴ�.*/
		case MINUS :
			return new IntNode(String.valueOf(operand1.value - operand2.value));
			/*DIV ���꿡 ��쿡�� operand1���� operand2�� ���� ����� IntNode�� �ְ� return�Ͽ��ش�.*/
		case DIV :
			return new IntNode(String.valueOf(operand1.value / operand2.value));
			/*TIMES ���꿡 ��쿡�� operand1���� operand2�� ���� ����� IntNode�� �ְ� return�Ͽ��ش�.*/
		case TIMES :
			return  new IntNode(String.valueOf(operand1.value * operand2.value));
			/*LT ���꿡 ��쿡�� ũ�� �� ��������
			 * operand1���� operand2�� �� ����� 0���� �۴ٸ� true�� �ƴϸ� false�� return�Ѵ�.*/	
		case LT :
			return new BooleanNode((operand1.value-operand2.value) < 0);
			/*GT ���꿡 ��쿡�� ũ�� �� ��������
			 * operand1���� operand2�� �� ����� 0���� ũ�� true�� �ƴϸ� false�� return�Ѵ�.*/	
		case GT :
			return new BooleanNode((operand1.value-operand2.value) > 0);
			/*EQ���꿡 ��쿡�� ������ Ȯ���ϴ� ��������
			 * operand1�� operand2�� ������ true �ٸ��� false�� return�Ѵ�.*/
		case EQ :
			return new BooleanNode(operand1.value.equals(operand2.value));
		}
		return null;
	}

	/*QuoteNode ó��*/
	private Node runQuote(ListNode node) {
		return ((QuoteNode)node.car()).nodeInside();
	}

	public static void main(String[] args) {
		try {
			while(true) {
				System.out.print("$ ");
				java.util.Scanner sc = new java.util.Scanner(System.in);
				BufferedWriter bw = new BufferedWriter(new FileWriter(new File("C:/Users/������/eclipse-workspace/Interpreter/data.txt")));
				bw.write(sc.nextLine());
				bw.flush();
				File file = new File("C:/Users/������/eclipse-workspace/Interpreter/data.txt");
				CuteParser cuteParser = new CuteParser(file);
				Node parseTree = cuteParser.parseExpr();
				CuteInterpreter i = new CuteInterpreter();
				Node resultNode = i.runExpr(parseTree);
				System.out.print("...");
				NodePrinter.getPrinter(System.out).prettyPrint(resultNode);
				System.out.println("");
			}
		}catch (FileNotFoundException e) {
			e.printStackTrace();} 
		catch (IOException e) {
			e.printStackTrace();
		}

	}
}