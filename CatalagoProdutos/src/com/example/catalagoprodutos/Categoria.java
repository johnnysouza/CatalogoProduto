package com.example.catalagoprodutos;

public class Categoria {
	
	private int id;
	private String categoria;
	private String cor;
	
	public Categoria(int id, String categoria, String cor) {
		super();
		this.id = id;
		this.categoria = categoria;
		this.cor = cor;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	@Override
	public String toString() {
		return categoria;
	}

}
