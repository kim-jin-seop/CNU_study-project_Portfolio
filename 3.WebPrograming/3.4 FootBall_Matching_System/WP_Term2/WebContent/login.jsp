<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<link href="login.css" rel="stylesheet" type="text/css">
<title>login</title>
</head>
<body>

<h1>CNU-WithFoot</h1>
<div>
<h2>�ڽ��� ������ Ȯ���ϰ� �����ø� �α����� �� �ּ���.</h2>
<form action = "mydata.jsp">
	���̵� : <input name = "id" type = "text" required> <br>
	�н����� : <input name = "password" type = "password" required>
	<input type = "submit" value = "login">
</form>
<h3>���̵� : ��Ī ��û ��ȭ��ȣ �ݵ�� -�� �ٿ��ּ���</h3>
<h4>������ �Է����� �����̳���? <a href = "Match.jsp">�����Է�</a>�� �����ø� ��Ī �� �����Է� �ܰ�� �̵��մϴ�.</h4>
<h4>���ư��� �����ø� <a href = "Main.html">���ư���</a>�� �������� </h4>
</div>

<!-- �α��� ȭ��, ���� �α����� �� ��, �Է��� ������ pw�� �´ٸ�, �ڽ��� �����͸� �������� ȭ���� �����ְԵȴ�.
���� �α����� �ؼ� ������ �ϸ� -> �ڽ��� data�� ����,������ �� �ְ�, ���Ŀ� �� ��Ī�� �����ϰ� �ȴ�.
�α����� �ϴ� ����� ������ �켱 ������ id�� ��й�ȣ�� ġ�� login���ϸ�, mydata.jsp���� �޾ƿ� ������ ������
�α����� �ϰ����� �� �ƴϸ� �����ʰ� �������� �����ش�. ���� id�� pw�� �ٸ��ٸ�, ������ ���� �ٽ� �α��� ȭ������ �̵��ϰ�
���̵�� �н����尡 ��ġ�ϴٸ� �������� ȯ���մϴ�! ��� �������Բ� �����͸� �����ְԵȴ�. �α��� ������ mydata.jsp���� �����ϵ����ϰڴ�.
�߰������� a tag�� ����Ͽ� �����Է��� ������ ��Ī�� �ϴ� ������ ���ư��⸦ ������ Main.html�� �̵��ϵ��� �����Ͽ���.
-->
</body>
</html>