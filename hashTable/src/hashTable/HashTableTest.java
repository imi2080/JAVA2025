package hashTable;

import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

class HashTable {
    private static final Logger logger = Logger.getLogger(HashTable.class.getName());
	private static class Node {
		String key;
		String value;

		public Node(String key, String value) {
			this.key = key;
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}

	@SuppressWarnings("unchecked")
	LinkedList<Node>[] data;

	public HashTable(int size) {
		data = (LinkedList<Node>[]) new LinkedList[size];
		logger.setLevel(Level.SEVERE);
	}

	int getHashCode(String key) {
		// return key.hashCode();  // 실전에서는 이 방식이 더 나음
		int hashcode = 0;
		for (char c : key.toCharArray())
			hashcode += c;
		return hashcode;
	}

	int convertToIndex(int hashcode) {
		return Math.abs(hashcode) % data.length;
	}

	Node searchNodeByKey(LinkedList<Node> list, String key) {
		if (list == null) return null;

		for (Node node : list) {
			if (node.key.equals(key)) return node;
		}
		return null;
	}

	public void put(String key, String value) {
		int hashcode = getHashCode(key);
		int index = convertToIndex(hashcode);
		// System.out.println(key + " hashcode(" + hashcode + ") index(" + index + ")");
		logger.info(() -> key + " hashcode(" + hashcode + ") index(" + index + ")");
		
		LinkedList<Node> list = data[index];
		if (list == null) {
			list = new LinkedList<>();
			data[index] = list;
		}

		Node node = searchNodeByKey(list, key);
		if (node == null) {
			list.addLast(new Node(key, value));
		} else {
			node.setValue(value);
		}
	}

	public String get(String key) {
		int hashcode = getHashCode(key);
		int index = convertToIndex(hashcode);
		Node node = searchNodeByKey(data[index], key);
		return node == null ? null : node.getValue();  // "Not Found" 대신 null 반환
	}
}


public class HashTableTest {
    public static void main(String[] args) {
		HashTable h = new HashTable(5);
		h.put("Kim", "Lotte First");
		h.put("Kwak", "LG First");
		h.put("Seung", "Doosan First");

		System.out.println("Kim: " + h.get("Kim"));     // 출력: Babo
		System.out.println("Kwak: " + h.get("Kwak"));   // 출력: LG First
		System.out.println("Seung: " + h.get("Seung")); // 출력: Doosan First
		System.out.println("Park: " + h.get("Park"));   // 출력: null
	}

}
