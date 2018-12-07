package LinkedList;

public interface IListaLinear {
	
	public int size();
	
	public boolean isEmpty();
	
	public boolean add(Object obj);
	
	public boolean add(int pos, Object obj);
	
	public Object get(int pos);
	
	public Object remove(int pos);
	
	public int indexOf(Object obj);
	
	public boolean contains(Object obj);
	
	public Object[] toArray();
}
