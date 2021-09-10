package com.vai.LivrariaDeAlexandria;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;



// TODO: Auto-generated Javadoc
/**
 * Classe pra acessar banco de dados
 */
public class Acesso {
	


	/** The Constant INSTANCE. */
	private static final Acesso INSTANCE = new Acesso();

	/**
	 * Gets the single instance of Acesso.
	 *
	 * @return single instance of Acesso
	 */
	public static Acesso getInstance() {
	return INSTANCE;
	}
	
	/**
	 * Inicializa acesso e a conexão.
	 * O banco de dados apresenta uma extrutura extremamente simples:
	 * 
	 * 			texto(id_texto, titulo, conteudo) 
	 * 
	 * sendo id_texto a chava primária.
	 * 
	 */
	private Acesso () {
		url = "jdbc:postgresql://localhost:5432/livraria"; 
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
	 * Adiciona novo texto ao banco de dados
	 *
	 * @param t texto que quer adicionar
	 * @return id do local colocaodo por default
	 */
	public int adicionar (Texto t) {
		int id = t.getId_texto();
		String titulo = t.getTitulo();
		String conteudo = t.getConteudo();
		String sql = "INSERT INTO texto(id_texto, titulo, conteudo) VALUES (" + "DEFAULT" + ", " + "?" +", "+ "?" + ");";
		
		try {
			PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, titulo);
			statement.setString(2, conteudo);
			int x = statement.executeUpdate();
			System.out.println("Adicionado com sucesso!");
			ResultSet y= statement.getGeneratedKeys();
			y.next();
			int id_return = (int) (y.getLong(1));
			return id_return;
			
		}
		catch (Exception e) {
			e.printStackTrace(); 
			System.out.println("Erro ao adicionar!");
			return 0;
			}
		
	} 
	
	/**
	 * Remover do banco de dados
	 *
	 * @param t texto que quer remover
	 * @return valor 2
	 */
	public int remover (Texto t) {
		int id = t.getId_texto();
		String sql = "DELETE FROM texto WHERE id_texto = " + id + ";";
		
		try {
			Statement statement = con.createStatement();
			int r = statement.executeUpdate(sql);
			System.out.println("Deletado com sucesso!");
			return r;
		}
		catch (Exception e) {
			e.printStackTrace(); 
			System.out.println("Erro ao deletar!");
			return 0;
			}
		
	} 
	
	/**
	 * Alterar/Atualiza valor no banco de dados
	 *
	 * @param t texto que quer atualizar
	 * @return valor 1
	 */
	public int alterar (Texto t) {
		int id = t.getId_texto();
		String titulo = t.getTitulo();
		String conteudo = t.getConteudo();
		String sql = "UPDATE texto SET titulo = '" + titulo +"', conteudo = '"+ conteudo +  "' WHERE id_texto = " + id + ";";
		
		try {
			Statement statement = con.createStatement();
			int r = statement.executeUpdate(sql);
			System.out.println("Alterado com sucesso!");
			return r;
		}
		catch (Exception e) {
			e.printStackTrace(); 
			System.out.println("Erro ao alterar!");
			return 0;
			}
		
	} 

/**
 * Busca todos os dados no banco de dados
 *
 * @return arraylist de textos encontrados
 */
public ArrayList<Texto> buscarTodos () {
		
		String sql = "SELECT * FROM texto";
		try {
			PreparedStatement statement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet x = statement.executeQuery();
			ArrayList<Texto> result = new ArrayList<Texto>();
			//x.first();
			while (x.next()==true) {
				result.add(new Texto(x.getInt("id_texto"),x.getString("titulo"),x.getString("conteudo")));
			}
			System.out.println("Sucesso ao buscar!");
			return result;
		}
		catch (Exception e) {
			e.printStackTrace(); 
			System.out.println("Falha ao buscar!");
			return null;}
	}
	
	
	

}
