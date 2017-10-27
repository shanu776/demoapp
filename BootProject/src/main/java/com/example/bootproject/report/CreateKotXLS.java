package com.example.bootproject.report;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class CreateKotXLS {


	/* public void printPage(List<OrderItems> model,HttpServletRequest request,HttpServletResponse resp){		
				JasperReportBuilder customerReport = DynamicReports.report();	
		try {
			customerReport.columns(
					Columns.column("Product","productName",DataTypes.stringType()),					
					Columns.column("Quantity","quantity",DataTypes.integerType()).setHorizontalAlignment(HorizontalAlignment.LEFT),
					Columns.column("Price","price",DataTypes.integerType()).setHorizontalAlignment(HorizontalAlignment.LEFT)
					).title(Components.text("Measurement Report")
							.setHorizontalAlignment(HorizontalAlignment.CENTER))
							.pageFooter(Components.pageXofY())
							.setDataSource(model);		
			
				customerReport.show(false);		
			}catch (DRException e) {
				e.printStackTrace();
			}						
							
		}
	 */
	
	private void addLabel(WritableSheet sheet, int column, int row, String s)
		      throws WriteException, RowsExceededException {
		    Label label;
		    label = new Label(column, row, s);
		    sheet.addCell(label);
		  }
	
	 private static final String EXCEL_FILE_LOCATION = "C:\\MyFirstExcel.xls";
	 public void print(HttpServletRequest request){
		        //1. Create an Excel file
		 	String path =request.getServletContext().getRealPath("report");
		        WritableWorkbook myFirstWbook = null;
		        try {
		            myFirstWbook = Workbook.createWorkbook(new File(path+"/MyFirstExcel.xls"));
		            // create an Excel sheet
		            WritableSheet excelSheet = myFirstWbook.createSheet("Sheet 1", 0);
		            // add something into the Excel sheet
		            Label label = new Label(0, 0, "Test Count");
		            excelSheet.addCell(label);

		            Number number = new Number(0, 1, 1);
		            excelSheet.addCell(number);

		            label = new Label(1, 0, "Result");
		            excelSheet.addCell(label);

		            label = new Label(1, 1, "Passed");
		            excelSheet.addCell(label);

		            number = new Number(0, 2, 2);
		            excelSheet.addCell(number);

		            label = new Label(1, 2, "Passed 2");
		            excelSheet.addCell(label);

		            myFirstWbook.write();            


		        } catch (IOException e) {
		            e.printStackTrace();
		        } catch (WriteException e) {
		            e.printStackTrace();
		        } finally {

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
