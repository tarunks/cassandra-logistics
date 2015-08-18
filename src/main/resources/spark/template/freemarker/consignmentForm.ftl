<div class="starter-template">
<form class="form-horizontal" role="form" id='consignment-create-form' method='POST' action='/shipment/create'>


<!-- From Address -->
 <div class="panel-group">
    <div class="panel panel-default">
      <div class="panel-heading">Sender Address </div>
      <div class="panel-body">
      
      <!-- Customer Name -->
<div class="form-group">
<label class="col-sm-3 control-label" for="txtCustName">Sender Name: </label>
<div class="col-sm-5">
<input class="form-control" type='text' id="txtSenderName" name='txtSenderName' placeholder="Enter sender name" />
</div>
</div>
      
      <div class="form-group">
<label class="col-sm-3 control-label" for="txtStreet">Street: </label>
<div class="col-sm-5">
<input class="form-control" type='text' id="txtStreet" name='txtStreet' placeholder="Enter street address" />
</div>
</div>
      
      <div class="form-group">
<label class="col-sm-3 control-label" for="txtCity">City: </label>
<div class="col-sm-5">
<input class="form-control" type='text' id="txtCity" name='txtCity' placeholder="Enter city" />
</div>
</div>

    <div class="form-group">
<label class="col-sm-3 control-label" for="txtState">State: </label>
<div class="col-sm-5">
<input class="form-control" type='text' id="txtState" name='txtState' placeholder="Enter state" />
</div>
</div>
      
          <div class="form-group">
<label class="col-sm-3 control-label" for="txtZip">Zip Code: </label>
<div class="col-sm-5">
<input class="form-control" type='text' id="txtZip" name="txtZip" placeholder="Enter zip code" />
</div>
</div>

 <div class="form-group">
<label class="col-sm-3 control-label" for="txtCountry">Country: </label>
<div class="col-sm-5">
<!-- input class="form-control" type='text' id="txtCountry" name="txtCountry" placeholder="Enter country name" /> -->
<select class="form-control"  id="txtCountry" name="txtCountry" >
<option value="IN">India</option>
<option value="UK">United Kingdom</option>
<option value="US">United States</option>
</select>
</div>
</div>

 <div class="form-group">
<label class="col-sm-3 control-label" for="txtPhone">Phone: </label>
<div class="col-sm-5">
<input class="form-control" type='text' id="txtPhone" name="txtPhone" placeholder="Enter phone number" />
</div>
</div>


 <div class="form-group">
<label class="col-sm-3 control-label" for="txtEmail">Email: </label>
<div class="col-sm-5">
<input class="form-control" type='text' id="txtEmail" name="txtEmail" placeholder="Enter email" />
</div>
</div>


      </div>
    </div>
</div>


<!-- TO Address -->
 <div class="panel-group">
    <div class="panel panel-default">
      <div class="panel-heading">Receiver Address </div>
      <div class="panel-body">
      
      
      <!-- Customer Name -->
<div class="form-group">
<label class="col-sm-3 control-label" for="txtCustName">Receiver Name: </label>
<div class="col-sm-5">
<input class="form-control" type='text' id="txtReceiverName" name='txtReceiverName' placeholder="Enter customer name" />
</div>
</div>
      <div class="form-group">
<label class="col-sm-3 control-label" for="txtRStreet">Street: </label>
<div class="col-sm-5">
<input class="form-control" type='text' id="txtRStreet" name='txtRStreet' placeholder="Enter street address" />
</div>
</div>
      
      <div class="form-group">
<label class="col-sm-3 control-label" for="txtRCity">City: </label>
<div class="col-sm-5">
<input class="form-control" type='text' id="txtRCity" name='txtRCity' placeholder="Enter city" />
</div>
</div>

    <div class="form-group">
<label class="col-sm-3 control-label" for="txtRState">State: </label>
<div class="col-sm-5">
<input class="form-control" type='text' id="txtRState" name='txtRState' placeholder="Enter State" />
</div>
</div>
      
          <div class="form-group">
<label class="col-sm-3 control-label" for="city">Zip Code: </label>
<div class="col-sm-5">
<input class="form-control" type='text' id="txtRZip" name="txtRZip" placeholder="Enter Zip code" />
</div>
</div>

 <div class="form-group">
<label class="col-sm-3 control-label" for="txtRCountry">Country: </label>
<div class="col-sm-5">
<!-- input class="form-control" type='text' id="txtRCountry" name="txtRCountry" placeholder="Enter Country name" /> -->
<select class="form-control"  id="txtRCountry" name="txtRCountry" >
<option value="IN">India</option>
<option value="UK">United Kingdom</option>
<option value="US">United States</option>
</select>
</div>
</div>

 <div class="form-group">
<label class="col-sm-3 control-label" for="txtRPhone">Phone: </label>
<div class="col-sm-5">
<input class="form-control" type='text' id="txtRPhone" name="txtRPhone" placeholder="Enter Phone Number" />
</div>
</div>


 <div class="form-group">
<label class="col-sm-3 control-label" for="txtREmail">Email: </label>
<div class="col-sm-5">
<input class="form-control" type='text' id="txtREmail" name="txtREmail" placeholder="Enter email" />
</div>
</div>


      </div>
    </div>
</div>

 <div class="form-group">
<label class="col-sm-3 control-label" for="priority">Priority: </label>
<div class="col-sm-5">
<label class="radio-inline"><input type="radio" name="priority">Superfast</label>
<label class="radio-inline"><input type="radio" name="priority">Fast</label>
<label class="radio-inline"><input type="radio" name="priority" checked>General</label> 
</div>
</div>

<div class="form-group">
<label class="col-sm-3 control-label" for="itemDetail">Item Details: </label>
<div class="col-sm-5">
<input class="form-control" type='text' id="itemDetail" name='itemDetail' placeholder="Enter item details" />
</div>
</div>
</form>
<label for="remarks">Remarks:</label>
<textarea class="form-control" name='remarks' id="remarks" rows='4' cols='5' form='consignment-create-form' placeholder="Enter any remarks"></textarea>
<input type='submit' value='Submit' class="btn btn-primary" form='consignment-create-form' />
</div>