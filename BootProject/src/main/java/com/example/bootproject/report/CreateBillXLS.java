package com.example.bootproject.report;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.example.bootproject.model.OrderItems;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class CreateBillXLS {
	
	public void addLabel(WritableSheet sheet, int column, int row, String s)throws WriteException, RowsExceededException {
		    Label label;
		    label = new Label(column, row, s);
		    sheet.addCell(label);
		  }
	
	public void generateKot(HttpServletRequest request,List<OrderItems> model){
		 WritableWorkbook myFirstWbook = null;
		 String path =request.getServletContext().getRealPath("report");
		 File file = new File(path+"/kot.xls");
		 try {			
			 myFirstWbook = Workbook.createWorkbook(file);
			 WritableSheet excelSheet = myFirstWbook.createSheet("Sheet 1", 0);
			/* excelSheet.mergeCells(0, 0, 2, 0);
			 excelSheet.mergeCells(0, 1, 2, 1);
			 excelSheet.mergeCells(0, 2, 2, 2);
			 excelSheet.mergeCells(0, 3, 2, 3);*/
			 addLabel(excelSheet, 0, 0, "                DesiChulhaa(Baner)");
			 addLabel(excelSheet, 0, 1, " baner road, Balewadt Phata,Shrinath Complex");
			 addLabel(excelSheet, 0, 2, "           M:8087164638/8087164738");
			 addLabel(excelSheet, 0, 3, "         GSTIN No:27BAVPK2884A1zF");
			 addLabel(excelSheet, 0, 4, "                                 ");
			 addLabel(excelSheet, 0, 5, "-------------------------------------------------------------------");
			 addLabel(excelSheet, 0, 6, "                                 ");
			 int i=7;
			 for(OrderItems oi:model){
				 addLabel(excelSheet, 0, i, oi.getProduct_name()+"                :                          "+oi.getQuantity());  
				 i++;
			 }			
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
       /* try {
			new TicketPrintPage(file).printTicketFile(file, 0);
		} catch (PrinterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	public void generateBill(HttpServletRequest request){
		 WritableWorkbook myFirstWbook = null;
		 String path =request.getServletContext().getRealPath("report");
		 File file = new File(path+"/bill.xls");
		 try {			
			 myFirstWbook = Workbook.createWorkbook(file);
			 WritableSheet excelSheet = myFirstWbook.createSheet("Sheet 1", 0);
			/* excelSheet.mergeCells(0, 0, 1, 0);
			 excelSheet.mergeCells(0, 1, 1, 1);
			 excelSheet.mergeCells(0, 2, 1, 2);
			 excelSheet.mergeCells(0, 3, 1, 3);
			 excelSheet.mergeCells(0, 5, 1, 5);*/
			 addLabel(excelSheet, 0, 0, "                DesiChulhaa(Baner)");
			 addLabel(excelSheet, 0, 1, " baner road, Balewadt Phata,Shrinath Complex");
			 addLabel(excelSheet, 0, 2, "           M:8087164638/8087164738");
			 addLabel(excelSheet, 0, 3, "         GSTIN No:27BAVPK2884A1zF");
			 addLabel(excelSheet, 0, 4, "                                 ");
			 addLabel(excelSheet, 0, 5, "-------------------------------------------------------------------");
			 addLabel(excelSheet, 0, 6, "                                 ");
			 int i=7;			
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
    
	}

}
