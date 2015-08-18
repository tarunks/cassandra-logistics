<div class="starter-template table-responsive">

<table class="table">
   
    <tbody>
	<tr>
	<td>Waybill Number: </td>
	<td>${shipment.getWaybillNo()}</td>	
	</tr>
	<tr>
	<td>Receiver Details:</td>
	<td>
	<p><b>${shipment.getDestinationAddress().getName()} </b></p>
 <p>${shipment.getDestinationAddress().getStreet()};
 ${shipment.getDestinationAddress().getCity()};
 ${shipment.getDestinationAddress().getState()};
 ${shipment.getDestinationAddress().getCountry()};
 ${shipment.getDestinationAddress().getZip()};</p>
 <div class="glyphicon glyphicon-phone">
 <#list shipment.getDestinationAddress().getPhone() as phone >
  <p>${phone} </p>
  
</#list>  
  
  </div>
 <div class="glyphicon glyphicon-envelope"> ${shipment.getDestinationAddress().getEmail()};</div>
 
	
	</td>	
	</tr>
 	<tr>
	<td>Sender Details:</td>
	<td>
	<p><b>${shipment.getSourceAddress().getName()} </b></p>
 <p>${shipment.getSourceAddress().getStreet()};
 ${shipment.getSourceAddress().getCity()};
 ${shipment.getSourceAddress().getState()};
 ${shipment.getSourceAddress().getCountry()};
 ${shipment.getSourceAddress().getZip()};</p>
 <div class="glyphicon glyphicon-phone">
 <#list shipment.getSourceAddress().getPhone() as phone >
  <p>${phone} </p>
  
</#list>  
  
  </div>
 <div class="glyphicon glyphicon-envelope"> ${shipment.getSourceAddress().getEmail()};</div>
 
	</td>	
	</tr>
 	<tr>
	<td>Dispatch Date:</td>
	<td>${shipment.getFormatedDispatchDate()}</td>	
	</tr>
 	<tr>
	<td>Item Detail:</td>
	<td>${shipment.getItemName()}</td>	
	</tr>
 	<tr>
	<td>Remarks:</td>
	<td>${shipment.getRemarks()}</td>	
	</tr>	
 
 
    </tbody>
  </table>

</div>