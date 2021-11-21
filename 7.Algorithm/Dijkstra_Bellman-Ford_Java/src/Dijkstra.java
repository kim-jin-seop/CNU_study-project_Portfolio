import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Dijkstra {
	private int[][] g;
	private vertex[] result;
	boolean fail = false;
	
	public Dijkstra(int[][] g) {
		this.g = g;
		result = new vertex[g.length];
		for(int i = 0; i < g.length; i++) {
			result[i] = new vertex(i);
		}
	}

	public void doPrint(BufferedWriter fw1File) throws IOException {
		if(!fail) {
			for(int i = 0 ; i < result.length; i++) {
				vertex x = result[i];
				while(x.pi.index != x.index) {
					fw1File.write(x.index+" <- ");
					x = x.pi;
				}
				fw1File.write(x.index+" "+result[i].key);
				fw1File.newLine();
			}
		}else {
			fw1File.write("ERROR : ���� ����ġ�� ���� ����");
		}
	}

	public void DoDijkstra(int start) {
		result[start].key = 0;
		ArrayList<vertex> Q = new ArrayList<vertex>();
		for(int i = 0; i < result.length; i++) {
			Q.add(result[i]);
		}
		while(!Q.isEmpty()) {
			vertex u = ExtractMin(Q);
			u.visit = true;
			for(int i = 0; i < g[u.index].length; i ++) {
				if(g[u.index][i] != Integer.MAX_VALUE && g[u.index][i] >= 0) {
					RELAX(u,result[i],g[u.index][i]);
				}else if(g[u.index][i] < 0) {
					fail = true;
					return;
				}
			}
		}
	}
	
	private void RELAX(vertex u, vertex v, int weight ) {
		if(v.key > u.key+weight) {
			v.key = u.key+weight;
			v.pi = u;
		}
	}

	private vertex ExtractMin(ArrayList<vertex> vertex) {
		for(int i = vertex.size()/2-1; i >= 0 ; i --) {
			heapify(vertex,i);
		}
		return vertex.remove(0);
	}

	private void heapify(ArrayList<vertex> vertex, int rootIndex) {
		int left = leftChiledIndex(rootIndex); //���� �ڽ� 
		int right = rightChiledIndex(rootIndex); //������ �ڽ� 
		if(left >= vertex.size()) // �ڽ��� ���°��
			return;
		if(right < vertex.size()) { //�ڽ��� �Ѵ� �ִ� ���
			if(vertex.get(left).key > vertex.get(rootIndex).key && vertex.get(right).key > vertex.get(rootIndex).key) { // �θ� ���� �������
				return;
			}else if(vertex.get(left).key < vertex.get(rootIndex).key && vertex.get(right).key > vertex.get(rootIndex).key) { // ���� �ڽ��� �� �������
				swap(vertex ,left,rootIndex);
				heapify(vertex,left);
			}else if(vertex.get(right).key < vertex.get(rootIndex).key && vertex.get(left).key > vertex.get(rootIndex).key) { // ������ �ڽ��� �� ���� ���
				swap(vertex,right,rootIndex);
				heapify(vertex,right);
			}else { // �� �ڽ� ��� ���� ���
				if(vertex.get(right).key < vertex.get(left).key) { // �ڽĳ��� �� �� ���� �ڽ� ã��
					swap(vertex,right,rootIndex);
					heapify(vertex,right);
				}
				else { // ���� �ڽ��� �� ���� ���
					swap(vertex,left,rootIndex);
					heapify(vertex,left);
				}
			}
		}else { // �ڽ��� ���ʸ� �ִ� ���
			if(vertex.get(left).key < vertex.get(rootIndex).key) {
				swap(vertex,left,rootIndex);
			}
		}
	}
	
	/*���� �ڽ��� index*/
	private int leftChiledIndex(int index) {
		return 2*index+1;
	}
	
	/*������ �ڽ��� index*/
	private int rightChiledIndex(int index) {
		return 2*index+2;
	}
	
	/*�����͸� ��ȯ�ϴ� �޼ҵ�*/
	private void swap(ArrayList<vertex> vertex ,int a, int b) {
		Collections.swap(vertex, a, b);
	}
	
	private class vertex{
		public int key;
		public vertex pi;
		public int index;
		boolean visit;
		
		public vertex(int index) {
			key = Integer.MAX_VALUE;
			pi = this;
			this.index = index;
			visit = false;
		}
	}
}
