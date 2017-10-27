shortcut.add("F2", function() {
	printBill();	
	});
shortcut.add("F6", function() {
	printKot();
	});
/*shortcut.add("F1", function() {
	printKotAndBill();
	});*/


var selected_product_id;
$(document).ready(function(){
				
	getMobileAddr();
	searchMobile();
	menuDetailTabs();
	getAddress();
	finalamount();

	$(".create-bill-area").scrollTop($(".create-bill-area")[0].scrollHeight);
/*================================================Decide Order Type========================================================================*/
	
	$('input[name=order_type]').on('click',function(){
		if($('input[name=order_type]:checked').val()=='dinein'){
			$('#table-no').show();
			$('.table-no').attr("required","true");
			$('#mo-n-addr').hide();
		}
		else{
			if($('input[name=order_type]:checked').val()=='delivery'){
				$('#mo-n-addr').show();
				$('.table-no').removeAttr("required");
				$('#table-no').hide();
			}else{
			$('#table-no').hide();
			$('.table-no').removeAttr("required");
			$('#mo-n-addr').show();
			}
		}
	});
	
/*========================================Search Product Items==============================================================================*/
	
	$( "#search-box" ).autocomplete({
	      source: function(request, response) {
	            $.ajax({
	                url: "getallproduct",
	                dataType: "json",
	                data: 'keyword='+$("#search-box").val(),
	                success: function( data, textStatus, jqXHR) {
	                    var items={};	                    
	                    $.each(data,function(e){	
	                    	items[data[e].short_name]=[data[e].name,data[e].id];
	                    });
	                    response($.map(items, function (value, key) {
	                        return {
	                            label: key+" :-"+value[0],
	                            value: function(event,ui){
	                            	selected_product_id = value[1];
	                            		return value[0];	
	                            	}       
	                        }
	                    }));
	                },	                
	                error: function(jqXHR, textStatus, errorThrown){
	                     console.log( textStatus);
	                }
	            });
	        }		

	    });
	
/*===================================================calculate price according to quantity=================================================*/
	
	$( "#quantity" ).on('keyup',function(){	
		
		if(selected_product_id === undefined){
			var prod_id = $("#productid").val();
			console.log(prod_id);
		}
		else
			var prod_id = selected_product_id;
		  $.ajax({
              url: "getoneproduct",
              dataType: "json",
              data: 'id='+prod_id,
              success: function(data) {
                 console.log($('#quantity').val());
                 var quantity = $('#quantity').val();
                 var price = (parseInt(data.price))*parseInt(quantity);
                 console.log(quantity)
                  $('#price').val(price);
                 $('#productid').val(data.id);
              }
          });
	});

/*====================================== Check table is available or not =================================================================*/
	
	/*$( ".table-no" ).on('keyup',function(){
		var tno = $(".table-no").val()
		  $.ajax({
              url: "istableavailable",
              dataType: "json",
              data: 'keyword='+tno,
              success: function(data) {
                 console.log(data);  
                 if(data==false){
                	 $(".table-no").val('');
                	 alert('table not available')
                 }
              }
          });
	});*/
});

/*====================================================Index===============================================================================*/

function getMobileAddr(){
	$.ajax({
        url: "getmobileaddress",
        dataType: "json",
        success: function(data) {
           console.log(table);  
           if(data.address!="" && table == null){
           $('#search-mobile').val(data.mobile);
           $('#address').val(data.address);
           }
        }
    });
}


function searchMobile(){
	$( "#search-mobile" ).autocomplete({
	      source: function(request, response) {
	            $.ajax({
	                url: "searchmobile",
	                dataType: "json",
	                data: 'keyword='+$("#search-mobile").val(),
	                success: function( data, textStatus, jqXHR) {
	                    var items=[];
	                    $.each(data,function(e){
	                    	items.push(data[e].mobileno);	                    	
	                    });
	                    response(items);
	                },
	                error: function(jqXHR, textStatus, errorThrown){
	                     console.log( textStatus);
	                }
	            });
	        }

	    });
}

function getAddress(){
	var mobaddr;
	  $.ajax({
          url: "searchaddress",
          dataType: "json",
          success: function( data) {
        	  console.log(data);
        	  mobaddr = data;
          }
	  });
	$( "#search-mobile" ).on('keyup change',function(){
		for(var i=0;i<mobaddr.length;i++){
			if($( "#search-mobile" ).val() == mobaddr[i].mobileno){
				$("#address").val(mobaddr[i].address);
				break;
			}
			else
				$("#address").val("");
		}
      });
}


/*=========================================================billing details==========================================================*/

function printKotAndBill(){
	var key = $(".table-no").val();
	var total = $('#discounted-amount').text();
	var gst = $('#gst').text();
	var gtotal = $('#gtotal').text();
	if(key==""){
		key=0;
		location.href="generatebill?tableno="+key+"&total="+total+"&gst="+gst+"&gtotal="+gtotal;	
	}
	
}

function printKot(){
	var key = $(".table-no").val();
	console.log(key);
	if(key!="" && key!='0')
		location.href="generatekot?tableno="+key;
	else
		location.href="generatekot?tableno="+0;
}

function printBill(){
	var table = $(".table-no").val();
	console.log(table);	
	var total = $('#discounted-amount').text();
	var gst = $('#gst').text();
	var gtotal = $('#gtotal').text();
	console.log('total='+total+' gst='+gst+' gtotal='+gtotal);
	if(table!="" && table!='0'){
		location.href="generatebill?tableno="+table+"&total="+total+"&gst="+gst+"&gtotal="+gtotal;
	}else{
		location.href="generatebill?tableno="+0+"&total="+total+"&gst="+gst+"&gtotal="+gtotal;	
	}
}


var discount=0.0;
function finalamount(){	
	$('#totalamount').text(totalAmount);	
	$('#discount-percentage,#discount-value,#ccharge,#dcharge').on('keyup',function(){
		billCalculations();						
	});	
	billCalculations();
}

function billCalculations(){
	var discountedamount = totalAmount;			
	if(($('#discount-percentage').val()=="")){
		$('#discount-percentage').val(0);
	}
	if(($('#discount-value').val()=="")){
		$('#discount-value').val(0);
	}
	if(($('#ccharge').val()=="")){
		$('#ccharge').val(0);
	}
	if(($('#dcharge').val()=="")){
		$('#dcharge').val(0);
	}
	var desc_val = parseFloat($('#discount-value').val());
	var desc = parseFloat($('#discount-percentage').val());
	var ccharge = parseFloat($('#ccharge').val());
	var dcharge = parseFloat($('#dcharge').val());
	console.log(ccharge+"  "+dcharge);
	discount = (discountedamount*desc)/100;
	discounted_amount = discountedamount-discount;
	discounted_amount = discounted_amount-desc_val;
	/*
	ccharge_amount = discountedamount+ccharge;
	*/
	var gst=(discountedamount*total_gst)/100;
	var gtotal = discounted_amount+gst
	$('#discounted-amount').text(discountedamount);		
	$('#gst').text(gst);
	$('#gtotal').text((gtotal).toFixed());
	$('#amount-with-extras').text((dcharge+ccharge+gtotal).toFixed());
}

/*========================================================on history page============================================================*/
	
function showRecord(val){
	$.ajax({
        url: "getoneorderhistory?id="+val,
        dataType: "json",
        success: function(data) {
           console.log(data); 
           $('#tbody').empty();
           var i=0;
           $.each(data,function(result){
        	   console.log(data[i].table_no);
        	   $('#tbody').append("<tr><td class='billint-td'>"+(i+1)+"</td><td class='billint-td'>"+data[i].prodoct+"</td>" +
        	   		"<td class='billint-td'>"+data[i].quantity+"</td><td class='billint-td'>"+data[i].price+"</td></tr>");
        	   $('.complete-record').slideDown();
        	   $('.history_container').css("z-index","-1");
        	   $('.history_container').css("display","none");
        	   i++;
           });
        }
    });
}
/*======================================================================menuPage===================================================================*/
function menuDetailTabs(){
	$('.table').on('click',function(){
		$('#dine-in').show();
		$('#take-away').hide();
		$('#delivery').hide();
		$('.button').css("background-color", "white");
		$(this).css("background-color", "green");
	});		
	
	$('.delivery').on('click',function(){
		$('#dine-in').hide();
		$('#take-away').hide();
		$('#delivery').show();
		$('.button').css("background-color", "white");
		$(this).css("background-color", "green");
	});		
		
	$('.take-away').on('click',function(){
		$('#dine-in').hide();
		$('#take-away').show();
		$('#delivery').hide();
		$('.button').css("background-color", "white");
		$(this).css("background-color", "green");
	});	
		
	
}

