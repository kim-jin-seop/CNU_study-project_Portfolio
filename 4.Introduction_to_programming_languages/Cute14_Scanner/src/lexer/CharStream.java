package lexer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

class CharStream {
	private final Reader reader;
	private Character cache;
	
	static CharStream from(File file) throws FileNotFoundException { //CharStream�� reader
		return new CharStream(new FileReader(file));
	}
	
	/*CharStream�� ������*/
	CharStream(Reader reader) {  // CharStream ������
		this.reader = reader;
		this.cache = null;
	}
	
	/*Char�� ���� ��ġ ��������
	 *cache�� Ȯ���ϴ� ���� = pushBack
	 *nextChar()�ϳ� ���� �� ���� �ѱ��ھ� �̵�.
	*/
	Char nextChar() { 
		if ( cache != null ) {  //cache�� null�� �ƴϸ�
			char ch = cache;    // ch�� cache�� ����.
			cache = null;       // ch�� �־����� cache null
			
			return Char.of(ch);   //Char of -> Char�� �����´�. Char�� ���¿� �Բ�.
		}
		else { // cache�� null�̸�
			try {
				int ch = reader.read(); // reader�� �ϳ� �о����.
				if ( ch == -1 ) {  //ch�� -1�̸�
					return Char.end(); // end return
				}
				else {
					return Char.of((char)ch); //Char �����´�.
				}
			}
			catch ( IOException e ) {
				throw new ScannerException("" + e);
			}
		}
	}
	
	/*pushBack-> cache�� ch�� �����Ѵ�.
	 * �ϴ� ������ nextChar�� �Ͽ��� �� cache�� �����Ͱ� ��������Ƿ� �� �����͸� ����ϰ� �ȴ�.
	 * �� �� �����͸� ������ ����ؾ��� �� ����Ѵ�. -> ���� Ư������, sign ó���� �� ���
	 * */
	void pushBack(char ch) { //cache�� ch �ֱ�.
		cache = ch;
	}
}
