 <div class="starter-template">
<div class="row">

	 <div class="col-sm-6">
	   <h3 style="background-color:lavender;">Destination Address</h3>
	   <div class="col-sm-5" >
	      <h4>${shipment.getDestinationAddress().getName()}</h4>
	      <p>${shipment.getDestinationAddress().getStreet()}</p>
	      <p>${shipment.getDestinationAddress().getCity()}</p>
	      <p>${shipment.getDestinationAddress().getState()}</p>
	      <p>${shipment.getDestinationAddress().getCountry()}</p>
	      <p>${shipment.getDestinationAddress().getZip()}</p>
	      <div class="glyphicon glyphicon-phone">
			  <#list shipment.getDestinationAddress().getPhone() as phone >
				 ${phone}
				  
			 </#list>  
		  </div>
		 <div class="glyphicon glyphicon-envelope"> ${shipment.getDestinationAddress().getEmail()};</div>
			 
	   </div>
	 </div> 
	 
	<div class="col-sm-6">
	      <h3 style="background-color:lavender;">Source Address</h3>
	      <div class="col-sm-5" >
	      <h4>${shipment.getSourceAddress().getName()}</h4>
	      <p>${shipment.getSourceAddress().getStreet()}</p>
	      <p>${shipment.getSourceAddress().getCity()}</p>
	      <p>${shipment.getSourceAddress().getState()}</p>
	      <p>${shipment.getSourceAddress().getCountry()}</p>
	      <p>${shipment.getSourceAddress().getZip()}</p>
	      <div class="glyphicon glyphicon-phone">
			 <#list shipment.getSourceAddress().getPhone() as phone >
			  ${phone} 
			  
			</#list>  
			  
		 </div>
		 <div class="glyphicon glyphicon-envelope"> ${shipment.getSourceAddress().getEmail()};</div>
			 
	      </div>
	 </div> 
 </div>
 </br>
 <table class="table">
 <thead>
 <tr>
  <td>Zones</td>
  <td>Location</td>
  <td>Last Update</td>
 <tr>
 </thead>   
 <tbody>
 <#list tracklist as track >
  <tr>  
    <td>${track.getCurrentZoneId()}</td>
    <td>${track.getCurrentLocationId()}</td>
    <td>${track.getFormatedUpdateDate()}</td>
  </tr>  
 </#list>
</tbody>
</table>

<div>
<a href="/track/update/${shipment.getWaybillNo()}" >Update Status</a>
</div>
</div>
