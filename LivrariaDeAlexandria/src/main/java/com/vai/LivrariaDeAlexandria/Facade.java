package com.vai.LivrariaDeAlexandria;

import java.io.IOException;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class Facade.
 */
public class Facade {
	
	/** The Constant INSTANCE. */
	private static final Facade INSTANCE = new Facade();

	/**
	 * Gets the single instance of Facade.
	 *
	 * @return single instance of Facade
	 */
	public static Facade getInstance() {
	return INSTANCE;
	}
	
	/** Acesso ao banco de dados */
	private Acesso a;
	
	/** Inicializador do banco de dados */
	private Inicializador b;
	
	/** Impressor de relatórios */
	private Impressor c;
	
	/**
	 * Instantiates a new facade.
	 */
	private Facade() {
		a = Acesso.getInstance();
		b = Inicializador.getInstance();
		c = Impressor.getInstance();
	}
	
	/**
	 * Adiciona novo texto ao banco de dados
	 *
	 * @param t texto que quer adicionar
	 * @return id do local colocaodo por default
	 */
	public int adicionar(Texto t) {
		return a.adicionar(t);
	}
	
	/**
	 * Remover do banco de dados
	 *
	 * @param t texto que quer remover
	 * @return valor 2
	 */
	public int remover(Texto t) {
		return a.remover(t);
	}

	/**
	 * Alterar/Atualiza valor no banco de dados
	 *
	 * @param t texto que quer atualizar
	 * @return valor 1
	 */
	public int alterar(Texto t) {
		return a.alterar(t);
	}
	
	/**
	 * Busca todos os dados no banco de dados
	 *
	 * @return arraylist de textos encontrados
	 */
	public ArrayList<Texto> buscarTodos() {
		return a.buscarTodos();
	}
	
	/**
	 * Checagem inicial. Checa se tem banco de dados necessário. Caso não, busca criar ele.
	 */
	public void checagemInicial () {
		b.checagemInicial();
	}
	
	/**
	 * Gera um arquivo txt.
	 *
	 * @param text texto que quer fazer em txt.
	 * @param nome o título do texto
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void makeTxt (ArrayList<String> text, String nome) throws IOException {
		c.makeTxt(text, nome);
	}
	
	/**
	 * Gera um arquivo pdf.
	 *
	 * @param text o texto que quer fazer em pdf.
	 * @param nome o título do arquivo em pdf
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void makePdf (ArrayList<String> text, String nome) throws IOException {
		c.makePdf(text, nome);
	}
	

}
