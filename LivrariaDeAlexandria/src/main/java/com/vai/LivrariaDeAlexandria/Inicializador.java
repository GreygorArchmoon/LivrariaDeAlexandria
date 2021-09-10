package com.vai.LivrariaDeAlexandria;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;



// TODO: Auto-generated Javadoc
/**
 * The Class Inicializador.
 */
public class Inicializador {
	


	/** The Constant INSTANCE. */
	private static final Inicializador INSTANCE = new Inicializador();

	/**
	 * Gets the single instance of Inicializador.
	 *
	 * @return single instance of Inicializador
	 */
	public static Inicializador getInstance() {
	return INSTANCE;
	}
	
	/**
	 * Inicializa o inicializador e a conexão temporária
	 */
	private Inicializador () {
		url = "jdbc:postgresql://localhost:5432/"; 
		usuario = "postgres";
		senha = "senha";
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection(url,usuario,senha);
			System.out.println("Conexão funcionou!");
		}
		catch (Exception e) {e.printStackTrace();}
	}
	
	
	/** The con. */
	private Connection con;
	
	/** The usuario. */
	private String usuario;
	
	/** The senha. */
	private String senha;
	
	/** The url. */
	private String url;
	

	
	/**
	 * Checagem inicial. Checa se tem banco de dados necessário. Caso não, busca criar ele.
	 */
	public void checagemInicial () {
		
		String sql = "SELECT 1 from pg_database WHERE datname='livraria';";
		try {
			Statement statement = con.createStatement();
			ResultSet x = statement.executeQuery(sql);
			if(x.next()==true) {
				System.out.println("Banco de dados já presente!");
			}
			else {
				System.out.println("Banco de dados ainda não presente. Adicionando...");
				criarBanco();
			}
			
		}
		catch (Exception e) {
			e.printStackTrace(); 
			System.out.println("Falha ao inicalizar!");}
	}
	
	/**
	 * Criar banco de dados necessário, assim como tabela.
	 */
	public void criarBanco() {

		String sql = "CREATE DATABASE livraria;";
		String sql2 = "CREATE TABLE texto (id_texto SERIAL, titulo VARCHAR(60000), conteudo VARCHAR(60000), PRIMARY KEY(id_texto))";
		
		try {

			PreparedStatement statement = con.prepareStatement(sql);
			int x = statement.executeUpdate();
			System.out.println("Banco de dados criado com sucesso!");
			Class.forName("org.postgresql.Driver");
			Connection con2 = DriverManager.getConnection("jdbc:postgresql://localhost:5432/livraria","postgres","senha");
			System.out.println("Conexão temporária funcionou!");
			PreparedStatement statement2 = con2.prepareStatement(sql2);
			x = statement2.executeUpdate();
			System.out.println("Tabela criada com sucesso!");
			
		}
		catch (Exception e) {
			e.printStackTrace(); 
			System.out.println("Erro ao criar banco de dados!");
			}
	}
		
	

}
