package stack;

public class PilhaVetor implements IStack {

	private Object[] pilha;
	
	private static final int MAX_ELEMENTS = 100;
	
	private int topo;

	public PilhaVetor() {
		this.pilha = new Object[MAX_ELEMENTS];
		this.topo = -1;
	}
	
	public PilhaVetor(int tamanho) {
		this.pilha = new Object[tamanho];
		this.topo = -1;
	}
	
	@Override
	public boolean isEmpty() {
		return (this.topo == -1);
	}

	@Override
	public int size() {
		return this.topo + 1;
	}

	@Override
	public Object pop() {
		if (this.isEmpty())
			return null;
		
		return this.pilha[this.topo--];
	}

	@Override
	public void push(Object item) {
		if (this.topo < this.pilha.length-1) {
			this.pilha[++this.topo] = item;
		}
	}

	@Override
	public Object[] toArray() {
		Object[] elementos = new Object[this.topo+1];
		
		for (int i=0 ; i <= this.topo ; i++ ) {
			elementos[i] = this.pilha[i];
		}
		
		return elementos;
	}
}




