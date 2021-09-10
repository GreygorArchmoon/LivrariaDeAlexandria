package com.vai.LivrariaDeAlexandria;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

// TODO: Auto-generated Javadoc
/**
 * The Class Impressor.
 */
public class Impressor {
	
	/**
	 * Instantiates a new impressor.
	 */
	private Impressor () {}
	
	/**  cria instância única de impressor. */
	private static final Impressor INSTANCE = new Impressor();
	
	/**
	 * Gets the single instance of Impressor.
	 *
	 * @return single instance of Impressor
	 */
	public static Impressor getInstance() {
		return INSTANCE;
		}
	
	/**
	 * escreve em um loop cada string do array como uma linha no txt.
	 *
	 * @param text the text
	 * @param nome the nome
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void makeTxt (ArrayList<String> text, String nome) throws IOException{
		File file = new File("relatório.txt");
		FileWriter escritor = new FileWriter(nome+".txt");
		for (int i = 0; i<text.size();i++) {
			escritor.write(text.get(i));
			escritor.write("\n");
		}
		
		escritor.close();
		
	}
	
	
	/**
	 * escreve em um loop cada string do array como uma linha no pdf.
	 *
	 * @param text the text
	 * @param nome the nome
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void makePdf (ArrayList<String> text, String nome) throws IOException {
		
		  try (PDDocument doc = new PDDocument()) {

	            PDPage myPage = new PDPage();
	            doc.addPage(myPage);

	            try (PDPageContentStream cont = new PDPageContentStream(doc, myPage)) {

	                cont.beginText();

	                cont.setFont(PDType1Font.TIMES_ROMAN, 12);
	                cont.setLeading(14.5f);

	                cont.newLineAtOffset(25, 700);
	                for (int i= 0; i<text.size();i++) {
	                	text.set(i,text.get(i).replace("\n", "").replace("\r", ""));
	                	String line = text.get(i);
	                	cont.showText(line);
	                	cont.newLine();
	                }
	                
	                cont.endText();
	            }
	            catch (IOException error) {}

	            doc.save(nome+".pdf");
	        }
		
		//
		
		
	}

}