package com.vai.LivrariaDeAlexandria;

// TODO: Auto-generated Javadoc
/**
 * The Class Texto.
 */
public class Texto {
	
	/** The id texto. */
	private int id_texto;
	
	/** The titulo. */
	private String titulo;
	
	/** The conteudo. */
	private String conteudo;
	
	/**
	 * Instantiates a new texto.
	 *
	 * @param id_texto the id texto
	 * @param titulo the titulo
	 * @param conteudo the conteudo
	 */
	public Texto(int id_texto, String titulo, String conteudo) {
		super();
		this.id_texto = id_texto;
		this.titulo = titulo;
		this.conteudo = conteudo;
	}
	
	/**
	 * Gets the id texto.
	 *
	 * @return the id texto
	 */
	public int getId_texto() {
		return id_texto;
	}
	
	/**
	 * Sets the id texto.
	 *
	 * @param id_texto the new id texto
	 */
	public void setId_texto(int id_texto) {
		this.id_texto = id_texto;
	}
	
	/**
	 * Gets the titulo.
	 *
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}
	
	/**
	 * Sets the titulo.
	 *
	 * @param titulo the new titulo
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	/**
	 * Gets the conteudo.
	 *
	 * @return the conteudo
	 */
	public String getConteudo() {
		return conteudo;
	}
	
	/**
	 * Sets the conteudo.
	 *
	 * @param conteudo the new conteudo
	 */
	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
	
	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return titulo;
	}
	
	

}
