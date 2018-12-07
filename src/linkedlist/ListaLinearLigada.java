package linkedlist;

import java.util.concurrent.ThreadLocalRandom;

import linkedlist.IListaLinear;

public class ListaLinearLigada implements IListaLinear {
	
	private Celula inicio;
	private Celula fim;
	private int qtd;
	
	public ListaLinearLigada() {
		this.qtd = 0;
	}
	
	@Override
	public int size() {
		return this.qtd;
	}

	@Override
	public boolean isEmpty() {
		return (this.qtd == 0);
	}

	@Override
	public boolean add(Object obj) {
		Celula novo = new Celula(obj);
		
		if (this.isEmpty()) {
			this.inicio = novo;			
		}else {
			this.fim.setProximo(novo);
		}
		this.fim = novo;
		this.qtd++;
		return true;
	}

	@Override
	public boolean add(int pos, Object obj) {
		Celula novo = new Celula(obj);
		
		if(pos == 0) {
			novo.setProximo(this.inicio);
			this.inicio = novo;
			
			if(this.isEmpty())
				this.fim = novo;
		}else if (pos == this.qtd-1 || pos >= this.qtd) {
			this.add(obj);
		}else {
			Celula atual = this.inicio;
			
			for(int i=0; i<pos-1; i++) {
				atual = atual.getProximo();
			}
			
			novo.setProximo(atual.getProximo());
			atual.setProximo(novo);
		}
		
		this.qtd++;
		return true;
	}

	@Override
	public Object get(int pos) {
		if (pos>this.qtd)
			return null;
		
		Celula atual = this.inicio;
		
		for(int i=0; i<pos; i++) {
			atual = atual.getProximo();
		}
		
		return atual.getElemento();
	}

	@Override
	public Object remove(int pos) {
		Celula removido;
		
		if (pos == 0) {
			removido = this.inicio;
			this.inicio = this.inicio.getProximo();
			
			if(this.qtd == 1)
				this.fim = null;
			
		}else if(pos <= this.qtd-1) {
			Celula atual = this.inicio;
			for(int i=0; i<pos-1; i++) {
				atual = atual.getProximo();
			}
			
			removido = atual.getProximo();
			atual.setProximo(removido.getProximo());
			
			if(pos == this.qtd-1)
				this.fim = atual;
			
		}else {
			return null;
		}
		
		this.qtd--;
		return removido.getElemento();
	}

	@Override
	public int indexOf(Object obj) {
		Celula atual = this.inicio;
		
		for(int i=0; i<this.qtd; i++) {
			if(atual.equals(obj))
				return i;
			atual = atual.getProximo();
		}
		return -1;
	}

	@Override
	public boolean contains(Object obj) {
		return this.indexOf(obj) >= 0;
	}

	@Override
	public Object[] toArray() {
		Object[] arr = new Object[this.qtd];
		
		Celula atual = this.inicio;
		
		for(int i=0; i<this.qtd; i++) {
			arr[i] = atual;
			atual = atual.getProximo();
		}
		return arr;
	}
	
	public ListaLinearLigada shuffle() {
		ListaLinearLigada newList = new ListaLinearLigada();	
		int randomIndex = 0;
		
		do {
			randomIndex = ThreadLocalRandom.current().nextInt(0, this.size());
			Object obj = this.get(randomIndex);
			newList.add(obj);			
			this.remove(randomIndex);
		}while(newList.size() <= this.size()+2);

		return newList;
	}
}
