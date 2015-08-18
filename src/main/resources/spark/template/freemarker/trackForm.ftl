

 <div class="starter-template">

<form class="form-horizontal" role="form" id='track-create-form' method='POST' action='/track/update/'>
	<div class="form-group">
		<label class="col-sm-3 control-label" for="currentLocation">Current Location: </label>
		<div class="col-sm-5">
			<input class="form-control" type='text' id="currentLocation" name='currentLocation' placeholder="Enter current location" />
		</div>
	</div>

	<div class="form-group">
		<label class="col-sm-3 control-label" for="currentZone">Current Zone: </label>
		<div class="col-sm-5">
			
			<select class="form-control"  id="currentZone" name="currentZone" >
				<option value="IN">India</option>
				<option value="UK">United Kingdom</option>
				<option value="US">United States</option>
			</select>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-3 control-label" for="remarks">Remarks:</label>
		<div class="col-sm-5">
			<textarea class="form-control" name="remarks" id="remarks" rows='4' cols='5' form='consignment-create-form' placeholder="Enter any remarks"></textarea>
		</div>
	</div>
 <input type="hidden" id="waybillno" name="waybillno" value="${waybillno}" />
</form>

  <input type='submit' value='Update' class="btn btn-primary" form='track-create-form' />
   
</div>