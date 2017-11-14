package com.example.bootproject.controller;

import java.awt.print.PrinterJob;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.print.PrintService;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.bootproject.dao.ConfigurationDaoImpl;
import com.example.bootproject.dao.MobileAndAddrDaoImpl;
import com.example.bootproject.dao.OrderDaoImpl;
import com.example.bootproject.dao.ProductDaoImpl;
import com.example.bootproject.model.Configuration;
import com.example.bootproject.model.DailySoldItems;
import com.example.bootproject.model.Items;
import com.example.bootproject.model.MobileAndAddress;
import com.example.bootproject.model.OrderHistory;
import com.example.bootproject.model.OrderItems;
import com.example.bootproject.model.Product;
import com.example.bootproject.report.CreateBillXLS;
import com.example.bootproject.report.UploadFile;

@org.springframework.stereotype.Controller
public class Controller {
	
	@Autowired
	ProductDaoImpl productDao;
	@Autowired
	OrderDaoImpl orderDao;
	@Autowired
	MobileAndAddrDaoImpl moAddrDao;
	@Autowired
	ConfigurationDaoImpl configurationDao;
	
	String table;							//determine which table is selected        (not a good idea )
	Configuration getGonfig;
	
/******************************************
******INDEX PAGE OPERATION START HERE******
*******************************************/

	public float findTotalAmount(List<OrderItems> orderList){
		float totalamount=0;
		for(OrderItems o:orderList)
			totalamount = totalamount+o.getPrice();
		return totalamount;
	}
	
	public Map<Integer, Float> getAllTableWithTotal(Set<Integer> tables){
		Map<Integer, Float> model = new HashMap<>();		
		tables.forEach(table->{
			Float totalamount=0.0f;			
			totalamount = findTotalAmount(orderDao.findallAccTable(table));			
			model.put(table, totalamount);
		});
		
		return model;
	}
	
/*====================================================MainHomePage=====================================================================*/
	@RequestMapping(value="/")
	public String index(Model model,OrderItems order,HttpServletRequest request){
		table = request.getParameter("table");		
		List<OrderItems> items =  orderDao.findall();
		List<OrderItems> orderList;
		LocalDate localDate = LocalDate.now();
	    String date = DateTimeFormatter.ofPattern("dd/MM/yyy").format(localDate);
		float totalamount = 0;
		Set<Integer> tables = new HashSet<>();		
		items.forEach((item)->{
			if(item.getTableno()!=0)
				tables.add(item.getTableno());
		});
		
		if(!(table==null) && !(table.equals("null"))){
			orderList = orderDao.findallAccTable(Integer.parseInt(table));
			model.addAttribute("table", table);
		}
		else {
			orderList = orderDao.findallAccTable(0);
			model.addAttribute("table", null);
			if(!orderList.isEmpty())
				model.addAttribute("order_type", orderList.get(0).getOrder_type());
		}
		
		totalamount = findTotalAmount(orderList);
		/*System.out.println("delivery"+orderDao.findDiliveryAccCDate(date).toString());*/
		model.addAttribute("gst", configurationDao.getOne(1));
		model.addAttribute("takeaway", orderDao.findTakeayayAccCDate(date));
		model.addAttribute("delivery", orderDao.findDiliveryAccCDate(date));
		model.addAttribute("tabledata", orderList);
		model.addAttribute("totalamount", totalamount);
		model.addAttribute("product", productDao.findall());
		model.addAttribute("tables",getAllTableWithTotal(tables));
		model.addAttribute("order", new OrderItems());
		return "index";
	}
	
	/*===============================================Add Current or table order=======================================================================*/
	
	@RequestMapping(value="addorder",method=RequestMethod.POST)
	public String addOrder(OrderItems order){		
		if(order.getTableno()==null)
			order.setTableno(0);
		Configuration config = configurationDao.getOne(1);
		if(config.getTax_type().equals("inclusive")){
			Float gst = (order.getPrice()*(config.getCgst()+config.getSgst()))/100;
			order.setPrice(order.getPrice()-gst);
		}
		LocalDate localDate = LocalDate.now();
	    order.setDate(DateTimeFormatter.ofPattern("dd/MM/yyy").format(localDate));
	    //if product is already exist then run this 
	   for(OrderItems item:orderDao.findallAccTable(order.getTableno())){
	    	if(item.getProduct_id().equals(order.getProduct_id())){
	    		item.setQuantity(item.getQuantity()+order.getQuantity());
	    		item.setPrice(item.getPrice()+order.getPrice());
	    		orderDao.add(item);	
	    		if(order.getTableno()!=0)													//here code not good
	    			return "redirect:/.html?table="+order.getTableno();
	    		else		
	    			return "redirect:/.html";
	    	}
	    }
		orderDao.add(order);
		if(order.getTableno()!=0)													//here code not good
			return "redirect:/.html?table="+order.getTableno();
		else		
			return "redirect:/.html";
	}
	
	/*===============================================delete Current or table order=======================================================================*/
	
	@RequestMapping(value="deletetableorder")
	public String deleteTableOrder(HttpServletRequest request){
		String tableno = request.getParameter("tableno");
		if(tableno != null){
			orderDao.deleteOrderAccTable(Integer.parseInt(tableno));
		}else{
			orderDao.deleteOrderAccTable(0);
		}
		if(!tableno.equals("0"))													//here code not good
			return "redirect:/.html?table="+tableno;
		else		
			return "redirect:/.html";
	}
	
	@RequestMapping(value="deletecurrentorder")
	public String deleteCurrentOrder(HttpServletRequest request){
		String id = request.getParameter("id");
		orderDao.delete(Integer.parseInt(id));							//here code not good
		if(table!=null)
			return "redirect:/.html?table="+table;
		else		
			return "redirect:/.html";
	}
	
/*===============================================Update Current or table order=======================================================================*/
	
	@RequestMapping(value="updateorder",method=RequestMethod.GET)
	public String updateOrder(HttpServletRequest request,Model model){
		Integer orderId = Integer.parseInt(request.getParameter("id"));
		OrderItems order = orderDao.findone(orderId);
		String table = this.table;		
		List<OrderItems> items =  orderDao.findall();
		List<OrderItems> orderList;
		float totalamount = 0;
		Set<Integer> tables = new HashSet<>();
		items.forEach((item)->{
			tables.add(item.getTableno());
		});
		if(!(table==null) && !(table.equals("null"))){
			orderList = orderDao.findallAccTable(Integer.parseInt(table));
			model.addAttribute("table", table);
		}
		else {
			orderList = orderDao.findallAccTable(0);
			if(!orderList.isEmpty())
				model.addAttribute("order_type", orderList.get(0).getOrder_type());
		}
		for(OrderItems o:orderList)
			totalamount = totalamount+o.getPrice();
		model.addAttribute("gst", configurationDao.getOne(1));
		model.addAttribute("tabledata", orderList);
		model.addAttribute("totalamount", totalamount);
		model.addAttribute("product", productDao.findall());
		model.addAttribute("tables",getAllTableWithTotal(tables));
		model.addAttribute("order", order);
		return "index";
		
	}
	
/*===================================================Get all product for automatic populated product list======================================*/
	
	@ResponseBody
	@RequestMapping(value="getallproduct")
	public List<Product> getAllProduct(HttpServletRequest request){
		String keyword = request.getParameter("keyword");
		List<Product> list = productDao.searchProduct(keyword);
		return list;
	}
	
	@ResponseBody
	@RequestMapping(value="getoneproduct")
	public Product getOneProduct(HttpServletRequest request){
		String id = request.getParameter("id");		
		return productDao.findone(Integer.parseInt(id));
	}
	
	@ResponseBody
	@RequestMapping(value="getproductaccshortname")
	public List<Product> getProductAccShortName(HttpServletRequest request){
		String shortname = request.getParameter("short");
		List<Product> list = productDao.searchProduct(shortname);
		return list;
	}
	
/*=============================================Check table is available or not========================================================*/	

	@ResponseBody
	@RequestMapping(value="istableavailable")
	public boolean isTableAvailable(HttpServletRequest request){
		Integer table = Integer.parseInt(request.getParameter("keyword"));		
		return orderDao.isTableAvailable(table);
	}
	
/*=======================================get mobile and address for current delivery order============================================*/
	
	@ResponseBody
	@RequestMapping(value="getmobileaddress")
	public Map<String,Object> getMobileAddress(OrderItems order){
		List<OrderItems> orderlist=orderDao.findallAccTable(0);
		Map<String, Object> model =new HashMap<>();
		if(!orderlist.isEmpty())
			order = orderlist.get(0);		
		model.put("mobile", order.getMobile());
		model.put("address", order.getAddress());
		return model;
	}
	
/*=======================================get all saved mobile no history for automatic drop down list===================================*/
	
	@ResponseBody
	@RequestMapping(value="searchmobile")
	public List<MobileAndAddress> searchMobile(HttpServletRequest request){
		String keyword = request.getParameter("keyword");
		return moAddrDao.searchMobile(keyword);
	}
	
	@ResponseBody
	@RequestMapping(value="searchaddress")
	public List<MobileAndAddress> searchAddress(HttpServletRequest request){
		return moAddrDao.findall();
	}
	
/*================================================== print bill related program ========================================================*/	
	@RequestMapping(value="generatekot")
	public String generateKot(HttpServletRequest request,Model model){
		Integer tableNo = Integer.parseInt(request.getParameter("tableno"));
		System.err.println(tableNo);
		List<OrderItems> kot = new ArrayList<>();
		String date = "";
		String o_type = "";
		Integer table_no = null;
		List<OrderItems> orders = orderDao.findallAccTable(tableNo);
		for(OrderItems item :orders){
			OrderItems kotItem = null;
			System.err.println(item.getQuantity());
			if(item.getKot()<item.getQuantity()){
				kotItem = new OrderItems(item.getProduct_name(),item.getComment(),item.getQuantity());
				kotItem.setQuantity(item.getQuantity()-item.getKot());
				kot.add(kotItem);
				date = item.getDate();
				o_type = item.getOrder_type();
				table_no = item.getTableno();
				System.err.println(item.getQuantity()+"  "+kotItem.getQuantity()+" "+item.getKot());
				item.setKot(item.getQuantity());
				orderDao.add(item);
			}
		}
		if(!(kot.size()>0)){
			return "redirect:/.html?table="+table;
		}
		model.addAttribute("kotitem",kot);
		String printer = configurationDao.findall().get(0).getPrinter_for_kot();
		new CreateBillXLS().generateKot(request, kot,date,o_type,table_no,printer);
		return "redirect:/.html";
	}
	
	
	public void generateKotWithBill(Integer tableNo,HttpServletRequest request){	
		List<OrderItems> kot = new ArrayList<>();
		String date = "";
		String o_type = "";
		Integer table_no = null;
		List<OrderItems> orders = orderDao.findallAccTable(tableNo);
		for(OrderItems item :orders){
			OrderItems kotItem = null;
			System.err.println(item.getQuantity());
			if(item.getKot()<item.getQuantity()){
				kotItem = new OrderItems(item.getProduct_name(),item.getComment(),item.getQuantity());
				kotItem.setQuantity(item.getQuantity()-item.getKot());
				kot.add(kotItem);
				date = item.getDate();
				o_type = item.getOrder_type();
				table_no = item.getTableno();
				System.err.println(item.getQuantity()+"  "+kotItem.getQuantity()+" "+item.getKot());
				item.setKot(item.getQuantity());
				orderDao.add(item);
			}
		}
		if(!(kot.size()>0)){
			return ;
		}
		String printer = configurationDao.findall().get(0).getPrinter_for_kot();
		new CreateBillXLS().generateKot(request, kot,date,o_type,table_no,printer);
	}
	
	Integer total_quantity=0;
	@RequestMapping(value="generatebill")
	public String generateBill(HttpServletRequest request,Model model){
		String tableno = request.getParameter("tableno");		
		String totalprice = request.getParameter("total");
		String gst = request.getParameter("gst");
		String gtotal = request.getParameter("gtotal");
		String discount_per = request.getParameter("dp");
		String discount_rs = request.getParameter("dr");
		String container = request.getParameter("co");
		String delivery = request.getParameter("de");
		
		total_quantity=0;
		System.err.println("table="+tableno+" total="+totalprice+" gst="+gst+" gtotal="+gtotal);
		
		LocalDate localDate = LocalDate.now();
	    String date = DateTimeFormatter.ofPattern("dd/MM/yyy").format(localDate);
	    
		List<Items> items = new ArrayList<>();
		List<OrderItems> orderList= orderDao.findallAccTable(Integer.parseInt(tableno));
		if(orderList.isEmpty()){
			if(!tableno.equals("0"))													//here code not good
				return "redirect:/.html?table="+tableno;
			else		
				return "redirect:/.html";
		}
		OrderHistory orderHistory = new OrderHistory(Integer.parseInt(tableno),orderList.get(0).getOrder_type(), date,discount_per,discount_rs,container,delivery, totalprice,total_quantity, gst, gtotal, items);
		orderList.forEach(item->{
			items.add(new Items(Integer.parseInt(tableno),item.getProduct_id(),item.getProduct_name(), item.getQuantity(), item.getPrice(),item.getComment(),orderHistory));	
			total_quantity = total_quantity+item.getQuantity();
			if(item.getMobile()!=null && !item.getMobile().equals("")){
				orderHistory.setMobileno(item.getMobile());
				orderHistory.setAddress(item.getAddress());
			}
			});	
		orderHistory.setTotal_quantity(total_quantity);
		if(orderList.isEmpty()){
			return "redirect:/.html?table="+table;
		}
		
		OrderItems order = orderList.get(0);
		
		OrderHistory billdetail = orderDao.saveOrderHistory(orderHistory);
		generateKotWithBill(billdetail.getTable_no(),request);
		new CreateBillXLS().generateBill(request,billdetail,configurationDao.getOne(1));
		orderDao.deleteOrderAccTable(Integer.parseInt(tableno));		
		model.addAttribute("order",orderList);
		model.addAttribute("total", totalprice);
		model.addAttribute("gst", gst);
		model.addAttribute("gtotal", gtotal);
		model.addAttribute("monaddr",order);
		
		try {
			if(order.getMobile()!=null && !order.getMobile().equals("")){
				moAddrDao.add(new MobileAndAddress(order.getMobile(), order.getAddress()));
			}
		} catch (Exception e) {
			return "printBill";
		}
		return "printBill";
	}
	
/******************************************
******INDEX PAGE OPERATION END HERE********
*******************************************/


	

/*******************************************
******ADD PRODUCT OPERATION Start Here******
******************************************/


/*===============================================go to Add product Page=======================================================================*/

@RequestMapping(value="addproductform")
public String addProductForm(Product product){
	return "addProduct";
}

/*=============================================== Add product =======================================================================*/
@RequestMapping(value="addproduct",method=RequestMethod.POST)
public String addProduct(Product product){
	productDao.add(product);
	return "addProduct";
}

/*****************************************
******ADD PRODUCT OPERATION End Here******
******************************************/



/*****************************************
******ORDER HISTORY End Here**************
******************************************/

/*===============================================get all order history============================================================*/

@RequestMapping(value="getorderhistory")
public String getOrderHistory(Model model){
	Float total_delivery=0.0f;
	Float total_takeaway=0.0f;
	Float total_table=0.0f;
	LocalDate localDate = LocalDate.now();
    String date = DateTimeFormatter.ofPattern("dd/MM/yyy").format(localDate);
    List<OrderHistory> everydayhistory = orderDao.getCurrentDateHistory(date);
    for(OrderHistory oh:everydayhistory){
    	if(oh.getOrder_type().equals("delivery")){
    		total_delivery = total_delivery+Float.parseFloat(oh.getTotal_Amount());
    	}else{
    		if(oh.getOrder_type().equals("takeaway")){
    			total_takeaway = total_takeaway+Float.parseFloat(oh.getTotal_Amount());
    		}else{
    			total_table = total_table+Float.parseFloat(oh.getTotal_Amount());
    		}
    	}
    }
	model.addAttribute("orderHistory", everydayhistory);
	model.addAttribute("total_delivery", total_delivery);
	model.addAttribute("total_takeaway", total_takeaway);
	model.addAttribute("total_table", total_table);
	model.addAttribute("total_collection", total_delivery+total_table+total_takeaway);
	return "orderHistory";
}

@ResponseBody
@RequestMapping(value="getoneorderhistory")
public List<Items> getOneOrderHistory(HttpServletRequest request){	
	String id = request.getParameter("id");	
	System.out.println(id);
	List<Items> itemList = new ArrayList<>();
	OrderHistory order = orderDao.getOneOrderHistory(Integer.parseInt(id));
	order.getItems().forEach(item->{
		itemList.add(new Items(item.getTable_no(), item.getProdoct(), item.getQuantity(), item.getPrice()));
	});
	System.err.println(itemList.toString());
	return  itemList;
}

@RequestMapping(value="getitemfromhistorytocurrent")
public String getItemFromHistoryToCurrent(HttpServletRequest request){
	String id = request.getParameter("id");
	OrderHistory orderHistory = orderDao.getOneOrderHistory(Integer.parseInt(id));
	orderDao.deleteOrderAccTable(0);
	orderHistory.getItems().forEach(item->{
		orderDao.add(new OrderItems(item.getProduct_id(), item.getProdoct(),item.getComment(), item.getQuantity(), 0, orderHistory.getOrder_type(), item.getPrice(), orderHistory.getDate(), item.getQuantity(), orderHistory.getMobileno(), orderHistory.getAddress()));		
	});
	orderDao.deleteOrderHistory(orderHistory);
	return "redirect:/.html";
}

@RequestMapping(value="solditemreport")
public String soldItemReport(Model model){
	LocalDate localDate = LocalDate.now();
    String date = DateTimeFormatter.ofPattern("dd/MM/yyy").format(localDate);
	List<DailySoldItems> soldItem = orderDao.dailysoldItems(date);
	model.addAttribute("saleProduct", soldItem);
	System.out.println(soldItem.toString());
	return "soldItemsReport";
}

/*****************************************
******ORDER HISTORY End Here**************
******************************************/




/*****************************************
******CONFIGURATION End Here**************
******************************************/

/*===============================================Configuration Page============================================================*/

@RequestMapping(value="configurationpage")
public String configPage(Configuration configuration,Model model){
	Configuration config = configurationDao.getOne(1);
	PrintService printers[] = PrinterJob.lookupPrintServices();
	model.addAttribute("config",config);
	model.addAttribute("printers", printers);
	return "configuration";
}

@RequestMapping(value="saveconfiguration",method=RequestMethod.POST)
public String saveConfiguration(@RequestParam("logo") MultipartFile file,Configuration configuration,HttpServletRequest request) throws IOException {
	System.err.println(configuration.toString());
	configuration.setFile_name(file.getOriginalFilename());
	new UploadFile().uploadFile(file, request, "logo", "logo.jpg");
	configurationDao.save(configuration);
	return "redirect:configurationpage.html";
}

@ResponseBody
@RequestMapping(value="getconfigdata")
public Configuration getconfgData(){
	return configurationDao.getOne(1);
}


/*****************************************
******CONFIGURATION End Here**************
******************************************/




}