<#if hasNoShipment??>
<div class="starter-template">
<h1>${hasNoShipment}</h1>
</div>
<#else>
<div class="starter-template table-responsive">

<table class="table table-bordered table-condensed">
    <thead>
      <tr>
        <th class="success" width="20%">Waybill</th>
        <th class="success" width="20%">Receiver Details</th>
        <th  class="success" width="20%">Sender Details</th>
        <th class="success" width="10%">Dispatch Date</th>
        <th class="success" width="10%">Item Details</th>
        <th class="success" width="20%">Remarks</th>
      </tr>
    </thead>
    <tbody>
<#list shipments as shipment>
 <tr>
 <td><a href="/shiment/detail/${shipment.getWaybillNo()}">${shipment.getWaybillNo()}</a></td>
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
 <td>${shipment.getFormatedDispatchDate()}</td>
 <td>${shipment.getItemName()}</td>
 <td>${shipment.getRemarks()}</td>
 </tr>
</#list>    
     
    </tbody>
  </table>

</div>
</#if>