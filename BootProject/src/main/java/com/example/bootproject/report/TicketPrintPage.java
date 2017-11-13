/*package com.qosko.utility;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.JobSheets;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;


public class TicketPrintPage implements Printable {

	private File ticket;
    public TicketPrintPage(File f) {
        ticket = f;
    }
	@Override
	public int print(Graphics g, PageFormat pf, int pageIndex)
			throws PrinterException {
		
		
		 int interline = 12;
		 int interline_x=75;
	        Graphics2D g2 = (Graphics2D) g;
	        g2.setFont(new Font("CourierThai", Font.PLAIN, 10));
	        int x =  (int) pf.getImageableX();
	        int y = (int) pf.getImageableY();

	        try {
	            //FileReader fr = new FileReader(ticket);
	            //BufferedReader br = new BufferedReader(fr);
	            
	            ///
	            Workbook w;
	            try {
	              w = Workbook.getWorkbook(ticket);
	              // Get the first sheet
	              Sheet sheet = w.getSheet(0);
	              // Loop over first 10 column and lines
	             
	              for (int i = 0; i < sheet.getRows(); i++) {
	            	  x =  (int) pf.getImageableX();
	            	  y += interline;
	            	  for (int j = 0; j < sheet.getColumns(); j++) {
	             
	                  Cell cell = sheet.getCell(j, i);
	                  CellType type = cell.getType();
	                  
	                  x += interline_x;
		                g2.drawString(cell.getContents(), x, y);
	                  
//	                  if (cell.getType() == CellType.LABEL) {
//	                    System.out.println("I got a label "
//	                        + cell.getContents());
//	                  }
//
//	                  if (cell.getType() == CellType.NUMBER) {
//	                    System.out.println("I got a number "
//	                        + cell.getContents());
//	                  }

	                }
	              }
	            } catch (BiffException e) {
	              e.printStackTrace();
	            }
	            ///

//	            String s;
//	            while ((s = br.readLine()) != null) {
//	                y += interline;
//	                g2.drawString(s, x, y);
//	            }
	        } catch (IOException e) {
	            throw new PrinterException("File to print does not exist (" + ticket.getAbsolutePath() +") !");
	        }
	        return Printable.PAGE_EXISTS;
	}
	
	
	public void printTicketFile(File ticket, int orientation) throws PrinterException {
	    if (!ticket.exists()) {
	        throw new PrinterException("Ticket to print does not exist (" + ticket.getAbsolutePath() + ") !");
	    }
	    PrinterJob pjob = PrinterJob.getPrinterJob();
	    // get printer using PrintServiceLookup.lookupPrintServices(null, null) and looking at the name
	    pjob.setPrintService(getPrintService());
	    pjob.setPrintService(PrintServiceLookup.lookupDefaultPrintService());
	    // job title
	    pjob.setJobName(ticket.getName());

	    // page fomat
	    PageFormat pf = pjob.defaultPage();
	    // landscape or portrait
	    pf.setOrientation(orientation);
	    // Paper properties
	    Paper a4Paper = new Paper();
	    double paperWidth  =  8.26;
	    double paperHeight = 11.69;
	    double margin = 40;
	    a4Paper.setSize(paperWidth * 72.0, paperHeight * 72.0);
	    a4Paper.setImageableArea(
	                margin,
	                //0,
	                margin,
	                //0,
	                a4Paper.getWidth()- 2 * margin,
	                //a4Paper.getWidth(),
	                a4Paper.getHeight()- 2 * margin
	                //a4Paper.getHeight()
	                ); // no margin = no scaling
	    pf.setPaper(a4Paper);
	    // Custom class that defines how to layout file text
	    TicketPrintPage pages = new TicketPrintPage(ticket);
	    // adding the page to a book
	    Book book = new Book();
	    book.append(pages, pf);
	    // Adding the book to a printjob
	    pjob.setPageable(book);
	    PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
	        // No jobsheet (banner page, the page with user name, job name, date and whatnot)
	    pras.add(JobSheets.NONE);
	    // Printing
	    pjob.print(pras);
	    
	}
	
	public void printBill() throws IOException, PrinterException
	{
		File f=new File("E:/write/bill.xls");
		f.createNewFile();
		try{
		FileWriter fstream = new FileWriter("D://File1.txt");
        BufferedWriter out = new BufferedWriter(fstream);
        out.write("watzzzzzzzzz uppp");
        out.close();
		}catch (Exception e){
			System.err.println("Error: " + e.getMessage());
		}
		TicketPrintPage ticketPrintPage=new TicketPrintPage(f);
		ticketPrintPage.printTicketFile(f,1);
		System.out.println("print Complete");
	}
}
*/



package com.example.bootproject.report;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;

import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.JobSheets;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;


public class TicketPrintPage implements Printable {

	private File ticket;
    public TicketPrintPage(File f) {
        ticket = f;
    }
	@Override
	public synchronized int print(Graphics g, PageFormat pf, int pageIndex)
			throws PrinterException {
		
		
		 int interline = 15;
		 int interline_x=10;
	        Graphics2D g2 = (Graphics2D) g;
	        g2.setFont(new Font("CourierThai", Font.PLAIN, 9));
	        int x =  (int) pf.getImageableX();
	        int y = (int) pf.getImageableY();

	        try {
	            //FileReader fr = new FileReader(ticket);
	            //BufferedReader br = new BufferedReader(fr);
	            
	            ///
	            Workbook w;
	            try {
	              w = Workbook.getWorkbook(ticket);
	              // Get the first sheet
	              Sheet sheet = w.getSheet(0);
	              // Loop over first 10 column and lines
	             
	              for (int i = 0; i < sheet.getRows(); i++) {
	            	  x =  (int) pf.getImageableX();
	            	  y += interline;
	            	  for (int j = 0; j < sheet.getColumns(); j++) {
	             
	                  Cell cell = sheet.getCell(j, i);
	                  CellType type = cell.getType();
	                  
	                  
		                g2.drawString(cell.getContents(), x, y);
		                if(j>0)
		                {
		                	interline_x=150;
		                }
		                else
		                {
		                	interline_x=50;
		                }
		                x += interline_x;
//	                  if (cell.getType() == CellType.LABEL) {
//	                    System.out.println("I got a label "
//	                        + cell.getContents());
//	                  }
//
//	                  if (cell.getType() == CellType.NUMBER) {
//	                    System.out.println("I got a number "
//	                        + cell.getContents());
//	                  }

	                }
	              }
	            } catch (BiffException e) {
	              e.printStackTrace();
	            }
	            ///

//	            String s;
//	            while ((s = br.readLine()) != null) {
//	                y += interline;
//	                g2.drawString(s, x, y);
//	            }
	        } catch (IOException e) {
	            throw new PrinterException("File to print does not exist (" + ticket.getAbsolutePath() +") !");
	        }
	        return Printable.PAGE_EXISTS;
	}
	
	
	public synchronized void printTicketFile(File ticket, int orientation) throws PrinterException {
	    if (!ticket.exists()) {
	        throw new PrinterException("Ticket to print does not exist (" + ticket.getAbsolutePath() + ") !");
	    }
	    PrinterJob pjob = PrinterJob.getPrinterJob();
	    // get printer using PrintServiceLookup.lookupPrintServices(null, null) and looking at the name
	    /*pjob.setPrintService(getPrintService());*/
	    pjob.setPrintService(PrintServiceLookup.lookupDefaultPrintService());
	    // job title
	    pjob.setJobName(ticket.getName());
      //  pjob.setCopies(1);
        System.out.println("No of prints "+pjob.getCopies());
	    // page fomat
	    PageFormat pf = pjob.defaultPage();
	    // landscape or portrait
	    pf.setOrientation(orientation);
	    System.err.println(pf.getHeight());
	    // Paper properties
	    Paper a4Paper = new Paper();
	    /*double paperWidth  =  8.26;
	    double paperHeight = 11.69;*/
	    double paperWidth  =  3.13;
	    double paperHeight = 6.69;
	    double margin = 0;
	    a4Paper.setSize(paperWidth * 203.0, paperHeight * 203.0);
	    a4Paper.setImageableArea(
	                margin,
	                //0,
	                margin,
	                //0,
	                a4Paper.getWidth()- 2 * margin,
	                //a4Paper.getWidth(),
	                a4Paper.getHeight()- 2 * margin
	                //a4Paper.getHeight()
	                ); // no margin = no scaling
	    pf.setPaper(a4Paper);
	    // Custom class that defines how to layout file text
	    TicketPrintPage pages = new TicketPrintPage(ticket);
	    // adding the page to a book
	    Book book = new Book();
	    book.append(pages, pf);
	    // Adding the book to a printjob
	    pjob.setPageable(book);
	    PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
	        // No jobsheet (banner page, the page with user name, job name, date and whatnot)
	    pras.add(JobSheets.STANDARD);
	    // Printing
	    pjob.print(pras);
	    
	}

}
