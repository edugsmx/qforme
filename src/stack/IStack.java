package stack;

public interface IStack {

	public boolean isEmpty();
	
	public int size();
	
	public Object pop();
	
	public void push(Object item);
	
	public Object[] toArray();
}