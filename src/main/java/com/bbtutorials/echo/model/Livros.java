package com.bbtutorials.echo.model;

import java.util.ArrayList;
import java.util.List;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity(value="Livros",noClassnameStored=true)
public class Livros {
	
	@Id	
	private Integer id;
	private String titulo;	
	private String autor;	
	private String editora;	
	private String num_paginas;	
	private String idiomas;	
	private String isbn;	
	private String ilustracao;	
	private String indice;	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getEditora() {
		return editora;
	}

	public void setEditora(String editora) {
		this.editora = editora;
	}

	public String getNum_paginas() {
		return num_paginas;
	}

	public void setNum_paginas(String num_paginas) {
		this.num_paginas = num_paginas;
	}

	public String getIdiomas() {
		return idiomas;
	}

	public void setIdiomas(String idiomas) {
		this.idiomas = idiomas;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getIlustracao() {
		return ilustracao;
	}

	public void setIlustracao(String ilustracao) {
		this.ilustracao = ilustracao;
	}

	public String getIndice() {
		return indice;
	}

	public void setIndice(String indice) {
		this.indice = indice;
	}
	
	public Livros(Integer id, String titulo) {
		this.id = id;
		this.titulo = titulo;
	}

	public List<Livros> listLivros() {
		List<Livros> listLivros = new ArrayList<Livros>();
		return listLivros;
	}	

	public Livros() {

	}
}
