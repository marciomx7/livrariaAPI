package com.bbtutorials.echo.model;

import java.util.ArrayList;
import java.util.List;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity(value="Livros",noClassnameStored=true)
public class Livros {
	
	@Id	
	private Integer id;
	private String Nome;	
	private String Valor;	
	private String Categoria;	
	private String idAutor;	
	private String capa;	
	
	public Integer getIdLivro() {
		return id;
	}

	public void setIdLivro(Integer idLivro) {
		this.id = idLivro;
	}

	public String getNome() {
		return Nome;
	}

	public void setNome(String nome) {
		Nome = nome;
	}

	public String getValor() {
		return Valor;
	}

	public void setValor(String valor) {
		Valor = valor;
	}

	public String getCategoria() {
		return Categoria;
	}

	public void setCategoria(String categoria) {
		Categoria = categoria;
	}

	public String getIdAutor() {
		return idAutor;
	}

	public void setIdAutor(String idAutor) {
		this.idAutor = idAutor;
	}

	public String getCapa() {
		return capa;
	}

	public void setCapa(String capa) {
		this.capa = capa;
	}


	
	public Livros(Integer id, String Nome) {
		this.id = id;
		this.Nome = Nome;
	}

	public List<Livros> listLivros() {
		List<Livros> listLivros = new ArrayList<Livros>();
		return listLivros;
	}	

	public Livros() {

	}
}
