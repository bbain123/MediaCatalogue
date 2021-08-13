//CS2210a 
//Brendan Bain
//251086487

public class Key {
	private String name;
	private String kind;
	
	public Key(String word, String type) {
		name = word.toLowerCase();
		kind = type;
	}
	
	//getName: returns the name of key
	public String getName() {
		return name;
	}
	
	//getKind: returns the kind of key
	public String getKind() {
		return kind;
	}
	
	//compareTo: compares two keys. 0 = they are equal. -1 = first key is smaller. 1 = first key is larger
	public int compareTo(Key k) {
		if (name.equals(k.name)) {
			if (kind.equals(k.kind))
				return 0;
			else if (kind.compareTo(k.kind) > 0)
				return 1;
			else
				return -1;
		}
		else if (name.compareTo(k.name) > 0)
			return 1;
		else
			return -1;
	}
}
