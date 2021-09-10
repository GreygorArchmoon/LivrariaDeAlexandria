package com.vai.LivrariaDeAlexandria;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Font;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import java.awt.Window.Type;
import javax.swing.JComboBox;
import java.awt.GridLayout;
import java.awt.FlowLayout;

import javax.swing.ComboBoxEditor;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Toolkit;

// 
/**
 * Classe da tela principal!
 */
public class TelaPrincipal extends JFrame {

	/** The content pane. */
	private JPanel contentPane;
	
	/** Arraylist de observadores.
	 * Telas criadas pela principal, onde ela pode notificar.
	 *  */
	private ArrayList<TelaSecundaria> observadores;
	
	/** Ponteiro para este próprio objeto pra poder referenciar em uma função */
	private TelaPrincipal self = this;
	
	/** Os dados armazenados no banco de dados */
	private ArrayList<Texto> dados;
	
	/** Componentes da interface */
	private JComboBox combo;
	private JLabel titleT;
	private JLabel bemvindoT;
	private JButton btnAbrirSelecionado;
	private JButton btnBoto;
	private JButton btnFecharSalvar;
	private JButton btnIngles;
	private JButton btnPortugus;
	
	/** O título padrão, colocado ao gerar novo texto */
	private String tituloPadrao;
	
	/** O conteúdo padrão, colocado ao gerar novo texto*/
	private String conteudoPadrao;
	
	/** Variável com qual idioma atualmente selecionado (0 para português, 1 para português) */
	private int idioma;
	
	/**Facade para facilitar acesso */
	private Facade facade;

	/**
	 * Launch the application.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaPrincipal frame = new TelaPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Salvar alterações de um texto no banco de dados.
	 *
	 * @param t Qual texto deseja-se salvar no banco de dados
	 */
	public void salvar (Texto t) {
		facade.alterar(t);
	}
	
	/**
	 * Deletar um texto do banco de dados
	 *
	 * @param t texto que quer deletar
	 */
	public void deletar(Texto t) {
		facade.remover(t);
		combo.removeItem(t);
	}
	
	/**
	 * Atualiza textos do programa pra língua correta, e chama observadores para fazer o mesmo
	 *
	 * @param rb ResourceBundle com a língua que se quer alterar
	 */
	public void updateText(ResourceBundle rb) {
		titleT.setText(rb.getString("ola"));
		bemvindoT.setText(rb.getString("bemvindo"));
		btnAbrirSelecionado.setText(rb.getString("abrir"));
		btnBoto.setText(rb.getString("criar"));
		btnFecharSalvar.setText(rb.getString("fechar"));
		btnIngles.setText(rb.getString("ingles"));
		btnPortugus.setText(rb.getString("portugues"));
		tituloPadrao = rb.getString("digite1");
		conteudoPadrao = rb.getString("digite2");
		this.setTitle(rb.getString("titulo"));
		for(int i=0;i<observadores.size();i++) {
			observadores.get(i).updateText(rb);
		}
	}
	

	/**
	 * Cria a tela principal.
	 */
	
	public TelaPrincipal() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(TelaPrincipal.class.getResource("/com/vai/LivrariaDeAlexandria/ALEXANDRIA2.png")));
		facade = Facade.getInstance(); //inicializa o facade utilizado
		idioma = 0; //inicializa idioma como zero, português
		facade.checagemInicial(); //checa se tem o banco de dados. Se não, ele cria ele.
		dados = facade.buscarTodos(); //busca dados do banco de dados e armazena
		observadores = new ArrayList<TelaSecundaria>(); //inicializa observadores
		
		//criação da interface
		setFont(new Font("Calisto MT", Font.PLAIN, 12));
		setTitle("Livraria de Alexandria");
		setBackground(Color.BLACK);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 441);
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setContentPane(contentPane);
		
		combo = new JComboBox(dados.toArray(new Texto[dados.size()])); //cria a combo box com dados do banco de dados
		combo.setForeground(Color.WHITE);
		combo.setBackground(Color.DARK_GRAY);
		btnBoto = new JButton("Criar Novo Texto");
		btnBoto.setFont(new Font("Arial Black", Font.PLAIN, 13));
		
		
		/**
		 * Cria novo texto no banco de dados e abre janela pra ele.
		 */
		btnBoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Texto t = new Texto(0,tituloPadrao,conteudoPadrao); //cria um novo objeto texto
				int id_recebido = facade.adicionar(t); //coloca ele no banco de dados e pega o id adicionado
				t.setId_texto(id_recebido); //muda o id do objeto pra ser igual ao adicionado por default
				TelaSecundaria tela = new TelaSecundaria(self, t, idioma); //cria uma tela secundária
				observadores.add(tela); //adiciona ela aos observadores
				tela.setVisible(true); //deixa ela vísvel
				combo.addItem(t); //adiciona o texto a combobox

			}
		});
		btnBoto.setForeground(Color.WHITE);
		btnBoto.setBackground(Color.DARK_GRAY);
		
		
		/**
		 * Abre o texto atualmente selecionado na combo box.
		 */
		btnAbrirSelecionado = new JButton("Abrir Selecionado");
		btnAbrirSelecionado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(dados.size()!=0) { //checa se tem algum elemento
					Texto t = (Texto) combo.getSelectedItem(); //pega o item da combo box
					TelaSecundaria tela = new TelaSecundaria(self, t, idioma); //cria tela com ele
					tela.setVisible(true); //deixa ela visível
					observadores.add(tela); //adiciona aos observadores
					}
			}
		});
		btnAbrirSelecionado.setFont(new Font("Arial Black", Font.PLAIN, 13));
		btnAbrirSelecionado.setForeground(Color.WHITE);
		btnAbrirSelecionado.setBackground(Color.DARK_GRAY);
		
		btnFecharSalvar = new JButton("Fechar & Salvar Todos");
		
		/**
		 * Avisa todos os observadores pra salvar e fechar.
		 */
		btnFecharSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i=0;i<observadores.size();i++) {
					observadores.get(i).avisar();
				}
			}
		});
		btnFecharSalvar.setForeground(Color.WHITE);
		btnFecharSalvar.setFont(new Font("Arial Black", Font.PLAIN, 13));
		btnFecharSalvar.setBackground(Color.DARK_GRAY);
		
		bemvindoT = new JLabel("Bem vindo a Livraria de Alexandria.");
		bemvindoT.setForeground(Color.BLACK);
		bemvindoT.setFont(new Font("Arial Black", Font.PLAIN, 16));
		
		titleT = new JLabel("Olá!");
		titleT.setForeground(Color.BLACK);
		titleT.setFont(new Font("Arial Black", Font.PLAIN, 16));
		
		/**
		 * Muda a linguagem para inglês.
		 */
		btnIngles = new JButton("Inglês");
		btnIngles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        Locale.setDefault(new Locale("en_US", "en_US")); //cria resource bundle e coloca em inglês
		        ResourceBundle rb = ResourceBundle.getBundle("escritos");
		        updateText(rb); //chama update text pra realizar o processo
		        idioma=1; //atualiza variável de idioma
			}
		});
		btnIngles.setFont(new Font("Arial Black", Font.PLAIN, 13));
		btnIngles.setForeground(Color.WHITE);
		btnIngles.setBackground(Color.DARK_GRAY);
		
		/**
		 * Muda linguagem para português.
		 */
		btnPortugus = new JButton("Português");
		btnPortugus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        Locale.setDefault(new Locale("pt_BR", "pt_BR")); //cria resource bundle e coloca em portguês
		        ResourceBundle rb = ResourceBundle.getBundle("escritos"); 
		        updateText(rb); //chama update text pra realizar o processo
		        idioma=0; //atualiza variável de idioma
			}
		});
		btnPortugus.setForeground(Color.WHITE);
		btnPortugus.setFont(new Font("Arial Black", Font.PLAIN, 13));
		btnPortugus.setBackground(Color.DARK_GRAY);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(56)
					.addComponent(bemvindoT)
					.addContainerGap(273, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(26)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnPortugus, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
									.addComponent(combo, 0, 369, Short.MAX_VALUE)
									.addGroup(gl_contentPane.createSequentialGroup()
										.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
											.addComponent(btnFecharSalvar, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE)
											.addGroup(gl_contentPane.createSequentialGroup()
												.addComponent(btnAbrirSelecionado, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(btnBoto, GroupLayout.PREFERRED_SIZE, 177, GroupLayout.PREFERRED_SIZE)))
										.addPreferredGap(ComponentPlacement.RELATED)))
								.addGap(31))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(btnIngles, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
								.addContainerGap()))))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(193, Short.MAX_VALUE)
					.addComponent(titleT, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addGap(148))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(25)
					.addComponent(titleT, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(bemvindoT)
					.addGap(18)
					.addComponent(combo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnAbrirSelecionado, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnBoto, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(btnFecharSalvar, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnIngles)
					.addGap(18)
					.addComponent(btnPortugus, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
					.addGap(30))
		);
		contentPane.setLayout(gl_contentPane);
		/**
		 * Deixa tudo no padrão, português.
		 */
		Locale.setDefault(new Locale("pt_Br","pt_BR"));
		ResourceBundle rb = ResourceBundle.getBundle("escritos");
		updateText(rb);
	}
}
