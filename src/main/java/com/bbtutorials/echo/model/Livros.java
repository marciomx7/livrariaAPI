package com.bbtutorials.echo.model;

import java.util.ArrayList;
import java.util.List;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity(value="Livros",noClassnameStored=true)
public class Livros {
	
	@Id	
	private Integer idLivro;
	private String Nome;	
	private String Valor;	
	private String Categoria;	
	private String idAutor;	
	private String capa;	
	
	public Integer getIdLivro() {
		return idLivro;
	}

	public void setIdLivro(Integer idLivro) {
		this.idLivro = idLivro;
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


	
	public Livros(Integer idLivro, String Nome) {
		this.idLivro = idLivro;
		this.Nome = Nome;
	}

	public List<Livros> listLivros() {
		List<Livros> listLivros = new ArrayList<Livros>();
		return listLivros;
	}	

	public Livros() {

	}
}
