package parser;


import java.io.File;
import java.io.FileNotFoundException;

class ScanContext {
	private final CharStream input;
	private StringBuilder builder;
	
	ScanContext(File file) throws FileNotFoundException {
		this.input = CharStream.from(file);
		this.builder = new StringBuilder();
	}
	
	CharStream getCharStream() {
		return input;
	}
	
	//���ڿ��� �о���� �׵��� append�ߴ� ���ڿ��� ���󰣴�.
	String getLexime() {
		String str = builder.toString(); 
		builder.setLength(0); //������ ������ �ִ� ���ڿ� ���� ������
		return str;
	}
	
	//���� �߰�
	void append(char ch) {
		builder.append(ch); // builder�� ������ �� �ֱ�
	}
}
