package com.qait.tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessBufferedFileInputStream;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;



public class PDFPasrse {
	public static void main(String args[]) 
	{


		PDFTextStripper pdfStripper = null;
		PDDocument pdDoc = null;
		COSDocument cosDoc = null;
		PDFParser parser=null;
		File file = new File("C:/Users/rahulyadav/Desktop/netforumqatests/src/test/resources/UploadFiles/Test Support Form 2.pdf");
		try {

			parser = new PDFParser( new RandomAccessBufferedFileInputStream(file));



			parser.parse();

			cosDoc = parser.getDocument();

			pdfStripper = new PDFTextStripper();

			pdDoc = new PDDocument(cosDoc);

			pdfStripper.setStartPage(1);

			pdfStripper.setEndPage(2);
			String parsedText = pdfStripper.getText(pdDoc);

			System.out.println(parsedText);


		}
		catch (FileNotFoundException e) {
			System.out.println("File not found");
		} 
		catch (IOException e) {
			System.out.println("Io");
			e.printStackTrace();
		}
		 finally {
				try {
					if (cosDoc != null)
						cosDoc.close();
					if (pdDoc != null)
						pdDoc.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}


	}
}




