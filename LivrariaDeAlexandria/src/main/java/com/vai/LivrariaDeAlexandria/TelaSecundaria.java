package com.vai.LivrariaDeAlexandria;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.Color;
import javax.swing.JTextPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;
import java.beans.PropertyChangeEvent;
import java.awt.Toolkit;

// TODO: Auto-generated Javadoc
/**
 * The Class TelaSecundaria.
 */
public class TelaSecundaria extends JFrame {

	/** Cria elementos da interface. */
	private JPanel contentPane;
	private JTextField txtDigiteAqui;
	private JButton btnSalvar;
	private JButton btnApagar;
	private JTextArea txtrA;
	private JButton btnSalvarPdf;
	private JButton btnSalvarTxt;

	/** Variável que mantém acesso a tela principal */
	private TelaPrincipal criador;
	
	/** Variável que armazena o texto */
	private Texto texto;
	
	/** Variável que armazena o título dependendo da língua */
	private String mesa;
	
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
					TelaSecundaria frame = new TelaSecundaria(null, null, 0);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	
	public void avisar() {
		texto.setTitulo(txtDigiteAqui.getText());
		texto.setConteudo(txtrA.getText());
		criador.salvar(texto);
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
	
	/**
	 * Atualiza textos do programa pra língua correta.
	 *
	 * @param rb ResourceBundle com a língua que se quer alterar
	 */
	public void updateText(ResourceBundle rb) {
		btnSalvar.setText(rb.getString("salvar"));
		btnApagar.setText(rb.getString("deletar"));
		btnSalvarPdf.setText(rb.getString("pdf"));
		btnSalvarTxt.setText(rb.getString("txt"));
		mesa = rb.getString("mesa");
	}
	
	
	/**
	 * Atualiza e pede para a tela principal salvar eles no banco de dados
	 */
	private void update () {
		texto.setTitulo(txtDigiteAqui.getText());
		texto.setConteudo(txtrA.getText());
		criador.salvar(texto);
		JOptionPane.showMessageDialog(this,"Salvo com sucesso!");  
	}
	
	/**
	 * Pede para o criador deletar esse texto do banco de dados, e fecha a janela.
	 */
	private void deleteThis() {
		int resultado =JOptionPane.showConfirmDialog(this,"Você tem certeza?","Você tem certeza MESMO?",JOptionPane.YES_NO_OPTION);  
		if (resultado == JOptionPane.YES_OPTION) {
			criador.deletar(texto);
			this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}
		
	}

	/**
	 * Cria nova tela secundária
	 *
	 * @param criador Telaprincipal que criou isso
	 * @param t Texto com os dados
	 * @param idioma idioma que atualmente sendo utilizado (0 para português, 1 para inglês)
	 */
	public TelaSecundaria(TelaPrincipal criador, Texto t, int idioma) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(TelaSecundaria.class.getResource("/com/vai/LivrariaDeAlexandria/ALEXANDRIA2.png")));
		facade = Facade.getInstance(); //pega Facade pra utilizar
		if (idioma==0) {mesa="Mesa:";} 
		else if(idioma==1) {mesa="Table:";} //dependendo do idioma, inicializa o título
		texto = t; //inicializa o texto com os valores
		this.criador=criador; //inicializa variável que armazena a tela principal
		
		//inicialização de coisas da interface
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 754, 709);
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);


		
		txtDigiteAqui = new JTextField();
		/**
		 * Cria um listener que fica de olho para atualizar o título com base no que escrito
		 */
		txtDigiteAqui.getDocument().addDocumentListener(new DocumentListener() {
			
			public void changedUpdate(DocumentEvent e) {
				mudatitulo();
			  }
			  public void removeUpdate(DocumentEvent e) {
				  mudatitulo();
			  }
			  public void insertUpdate(DocumentEvent e) {
				  mudatitulo();
			  }
			  public void mudatitulo() {setTitle(mesa+" "+txtDigiteAqui.getText());}
		});
		
		txtDigiteAqui.setFont(new Font("Arial Black", Font.PLAIN, 13));
		txtDigiteAqui.setForeground(Color.WHITE);
		txtDigiteAqui.setBackground(Color.DARK_GRAY);
		txtDigiteAqui.setText(texto.getTitulo());
		txtDigiteAqui.setCaretColor(Color.WHITE);
		txtDigiteAqui.setColumns(10);
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.setFont(new Font("Arial Black", Font.PLAIN, 13));
		btnSalvar.setForeground(Color.WHITE);
		btnSalvar.setBackground(Color.DARK_GRAY);
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				update();
			}
		});
		
		btnApagar = new JButton("Deletar");
		btnApagar.setFont(new Font("Arial Black", Font.PLAIN, 13));
		btnApagar.setForeground(Color.WHITE);
		btnApagar.setBackground(Color.DARK_GRAY);
		btnApagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteThis();
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		
		btnSalvarPdf = new JButton("Salvar Pdf");
		/**
		 * Salva documento pdf do texto atual.
		 */
		btnSalvarPdf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				update(); //atualiza e salva valores
				String[] lines = texto.getConteudo().split(System.lineSeparator()+"|\\n|\n|\r|\\r"); //separa cada linha em um array 
				ArrayList<String> array = new ArrayList<String>(); //converte pra arraylist
				for (int i=0;i<lines.length;i++) {
					array.add(lines[i]);
				}
				try {
					facade.makePdf(array,texto.getTitulo()); //manda pra gerar pdf
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnSalvarPdf.setForeground(Color.WHITE);
		btnSalvarPdf.setFont(new Font("Arial Black", Font.PLAIN, 13));
		btnSalvarPdf.setBackground(Color.DARK_GRAY);
		
		btnSalvarTxt = new JButton("Salvar Txt");
		/**
		 * Salva documento txt do texto atual.
		 */
		btnSalvarTxt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				update(); //atualiza e salva valores
				String[] lines = texto.getConteudo().split(System.lineSeparator()+"|\\n|\n|\r|\\r"); //separa cada linha em um array
				ArrayList<String> array = new ArrayList<String>(); //converte pra arraylist
				for (int i=0;i<lines.length;i++) {
					array.add(lines[i]);
				}
				try {
					facade.makeTxt(array,texto.getTitulo()); //manda pra gerar txt
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnSalvarTxt.setForeground(Color.WHITE);
		btnSalvarTxt.setFont(new Font("Arial Black", Font.PLAIN, 13));
		btnSalvarTxt.setBackground(Color.DARK_GRAY);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(34)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 665, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(btnSalvarPdf, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnSalvarTxt, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtDigiteAqui, GroupLayout.PREFERRED_SIZE, 422, GroupLayout.PREFERRED_SIZE))
							.addGap(33)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(btnApagar, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnSalvar, GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE))))
					.addGap(27))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(26)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(txtDigiteAqui, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnSalvarPdf)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnSalvarTxt, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnSalvar, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnApagar, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 510, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		
		txtrA = new JTextArea();
		scrollPane.setViewportView(txtrA);
		txtrA.setFont(new Font("Arial Black", Font.PLAIN, 13));
		txtrA.setForeground(Color.WHITE);
		txtrA.setBackground(Color.DARK_GRAY);
		txtrA.setLineWrap(true);
		txtrA.setText(texto.getConteudo());
		txtrA.setCaretColor(Color.WHITE);
		contentPane.setLayout(gl_contentPane);
		
		/**
		 * Atualiza textos com base na linguagem salva em idioma
		 */
		if(idioma==0) {
			Locale.setDefault(new Locale("pt_Br","pt_BR"));
			ResourceBundle rb = ResourceBundle.getBundle("escritos");
			updateText(rb);
		}
		else if(idioma==1) {
			Locale.setDefault(new Locale("en_US","en_US"));
			ResourceBundle rb = ResourceBundle.getBundle("escritos");
			updateText(rb);
		}
	}
}
