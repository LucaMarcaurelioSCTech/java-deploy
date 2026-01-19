package com.deployrest.rest_prova.model;

public class CloudVendor {
	
	private String nome;
	private String cognome;
	private String email;
	private String password;
	
	
	public CloudVendor() {
		
	}
	
	
	public CloudVendor(String nome, String cognome, String email, String password) {
		this.setNome(nome);
		this.setCognome(cognome);
		this.setEmail(email);
		this.setPassword(password);
		
	}
	
	public CloudVendor(String email, String password) {
		
		this.setEmail(email);
		this.setPassword(password);
		
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getCognome() {
		return cognome;
	}


	public void setCognome(String cognome) {
		this.cognome = cognome;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	
	
	
	
}
