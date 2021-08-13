
public interface OrderedDictionaryADT {
	public DataItem get (Key k);
	public void put (DataItem d) throws DictionaryException;
	public void remove (Key k) throws DictionaryException;
	public DataItem successor (Key k);
	public DataItem smallest();
	public DataItem largest();
}