//CS2210a 
//Brendan Bain
//251086487

public class DataItem {
	private Key theKey;
	private String content;
	
	public DataItem(Key k, String data) {
		theKey = k;
		content = data;
	}
	
	//getKey: returns the key of data item
	public Key getKey() {
		return theKey;
	}
	
	//getContent: returns the content of data item
	public String getContent() {
		return content;
	}
	
}
