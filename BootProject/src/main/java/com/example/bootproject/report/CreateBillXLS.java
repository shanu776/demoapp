package com.example.bootproject.report;

import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.example.bootproject.model.Configuration;
import com.example.bootproject.model.Items;
import com.example.bootproject.model.OrderHistory;
import com.example.bootproject.model.OrderItems;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class CreateBillXLS {
	
	public void addLabel(WritableSheet sheet, int column, int row, String s)throws WriteException, RowsExceededException {
		WritableCellFormat cellFormat = new WritableCellFormat();
	      cellFormat.setAlignment(Alignment.CENTRE);
		    Label label= new Label(column, row, s,cellFormat);		    
		    sheet.addCell(label);
		  }
	public void addLabelwithH(WritableSheet sheet, int column, int row, String s)throws WriteException, RowsExceededException {
		WritableCellFormat cellFormat = new WritableCellFormat();
	      cellFormat.setAlignment(Alignment.CENTRE);
		    Label label= new Label(column, row, s,createFormatCellStatus(true));
		    sheet.addCell(label);
		  }
	public WritableCellFormat createFormatCellStatus(boolean b) throws WriteException{
	    WritableFont wfontStatus = new WritableFont(WritableFont.createFont("Arial"), WritableFont.DEFAULT_POINT_SIZE, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
	    WritableCellFormat fCellstatus = new WritableCellFormat(wfontStatus);

	    fCellstatus.setWrap(true);
	    fCellstatus.setAlignment(jxl.format.Alignment.CENTRE);
	    fCellstatus.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
	    return fCellstatus;
	}
	
	public void generateKot(HttpServletRequest request,List<OrderItems> model,String date,String type,Integer tno,String printer){
		 WritableWorkbook myFirstWbook = null;
		 String path =request.getServletContext().getRealPath("report");
		 File file = new File(path+"/kot.xls");
		 try {			
			 myFirstWbook = Workbook.createWorkbook(file);
			 WritableSheet excelSheet = myFirstWbook.createSheet("Sheet 1", 0);
			 excelSheet.mergeCells(0, 0, 2, 0);
			 excelSheet.mergeCells(0, 1, 2, 1);
			 excelSheet.mergeCells(0, 2, 2, 2);
			 excelSheet.mergeCells(0, 3, 2, 3);
			 excelSheet.mergeCells(0, 4, 2, 4);
			 addLabel(excelSheet, 0, 0, "                                    KOT  ");
			 addLabel(excelSheet, 0, 1, "                                   "+date+"  ");
			 addLabel(excelSheet, 0, 2, "                                   "+type+"  ");
			 if(tno!=0)
			 addLabelwithH(excelSheet, 0, 3, "                                   TableNo:"+tno+"  ");
			 addLabel(excelSheet, 0, 4, "     -----------------------------------------------------------------------------");
			 addLabel(excelSheet, 0, 5, "     Item");addLabel(excelSheet, 1, 5, "                                  SpecialNote");
			 addLabel(excelSheet, 2, 5, "qty");
			 int i=6;
			 for(OrderItems oi:model){
				 addLabel(excelSheet, 0, i, "     "+oi.getProduct_name());
				 addLabel(excelSheet, 1, i,"                                      "+oi.getComment());
				 addLabel(excelSheet, 2, i,oi.getQuantity()+"     "); 
				 i++;
			 }			
			 excelSheet.mergeCells(0, i, 2, i);
			 addLabel(excelSheet, 0, i, "     -----------------------------------------------------------------------------");
			 myFirstWbook.write();
			 
		} catch (IOException | WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {

            if (myFirstWbook != null) {
                try {
                    myFirstWbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
			TicketPrintPage ticketPrintPage = new TicketPrintPage(file);
			ticketPrintPage.printTicketFile(file, 1,printer);
		} catch (PrinterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void generateBill(HttpServletRequest request,OrderHistory billdetail,Configuration config){
		 WritableWorkbook myFirstWbook = null;
		 String path =request.getServletContext().getRealPath("report");
		 File file = new File(path+"/bill.xls");
		 try {			
			 myFirstWbook = Workbook.createWorkbook(file);
			 WritableSheet excelSheet = myFirstWbook.createSheet("Sheet 1", 0);
			 excelSheet.mergeCells(0, 0, 3, 0);
			 excelSheet.mergeCells(0, 1, 3, 1);
			 excelSheet.mergeCells(0, 2, 3, 2);
			 excelSheet.mergeCells(0, 3, 3, 3);
			 excelSheet.mergeCells(0, 4, 3, 4);			 
			
			 addLabel(excelSheet, 0, 0, "                               DesiChulhaa(Baner)");
			 addLabel(excelSheet, 0, 1, "      baner road, Balewadt Phata,Shrinath Complex");
			 addLabel(excelSheet, 0, 2, "                       M:8087164638/808716");
			 addLabel(excelSheet, 0, 3, "                  GSTIN No:27BAVPK2884A1ZF");
			 addLabel(excelSheet, 0, 4, "-------------------------------------------------------------------------------------------");
			
			 addLabel(excelSheet, 0, 6, "     Date:"+billdetail.getDate());addLabel(excelSheet, 1, 6, "                                       "+billdetail.getOrder_type());
			 addLabel(excelSheet, 0, 7, "     BillNo:"+billdetail.getId());if(billdetail.getTable_no()!=0){addLabel(excelSheet, 1, 7, "                              Table No."+billdetail.getTable_no());}
			 excelSheet.mergeCells(0, 8, 3, 8);
			 addLabel(excelSheet, 0, 8, "-------------------------------------------------------------------------------------------");
		
			 addLabel(excelSheet, 0, 9, "     No.   Item");
			 addLabel(excelSheet, 1, 9, "                                 Qty   Price   Amount");
			 excelSheet.mergeCells(0, 10, 3, 10);
			 addLabel(excelSheet, 0, 10, "-------------------------------------------------------------------------------------------");
			
			 int i=11;		
			 for(Items oi : billdetail.getItems()){
				 addLabel(excelSheet, 0, i, "     "+(i-10)+" "+oi.getProdoct());
				 addLabel(excelSheet, 1, i,"                                    "+oi.getQuantity()+"   "+(oi.getPrice()/oi.getQuantity())+"   "+oi.getPrice()+"");
				 i++;
			 }
			 excelSheet.mergeCells(0, i, 3, i);
			 addLabel(excelSheet, 0, i, "-------------------------------------------------------------------------------------------");
			 
			 addLabel(excelSheet, 0, i+1, "                         TotalQuantity:"+billdetail.getTotal_quantity());
			 addLabel(excelSheet, 1, i+1, "                                   Sub Total    "+billdetail.getTotal_Amount());
			 
			 addLabel(excelSheet, 0, i+2, "      CGST : "+config.getCgst()+" % + SGST : "+config.getSgst()+" %");
			 addLabel(excelSheet, 1, i+2, "                                     TotalGst    "+billdetail.getGst());
			 
			 addLabel(excelSheet, 0, i+3, "-------------------------------------------------------------------------------------------");
			 
			 addLabel(excelSheet, 0, i+4, "                   Grand Total ");
			 addLabel(excelSheet, 1, i+4, "                                                   "+billdetail.getGtotal());
			 
			 excelSheet.mergeCells(0, i+5, 3, i+5);
			 addLabel(excelSheet, 0, i+5, "------------------------ThankYou (Visit Again)------------------------------------");
			 myFirstWbook.write();			 
		} catch (IOException | WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {

            if (myFirstWbook != null) {
                try {
                    myFirstWbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }
            }
        }
		 try {
				new TicketPrintPage(file).printTicketFile(file, 1,config.getPrinter_for_bill());
			} catch (PrinterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    
	}

}
