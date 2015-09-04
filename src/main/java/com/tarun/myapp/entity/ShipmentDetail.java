package com.tarun.myapp.entity;

import java.util.Date;
import java.util.UUID;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
public class ShipmentDetail {

	private DateFormat dateFormat=new SimpleDateFormat("MM/dd/yyyy");
 private String	sourceZoneId;
 private String	destinationZoneId;
 private UUID waybillNo;
 private Address sourceAddress; 
 private Address destinationAddress;
 private String packageType;
 private String priority;
 private Date dispatchDate;
 private Date expDeliveryDate;
 private Date actualDeliveryDate;
 private String currentStatus;
 private String itemName ;
 private boolean isReturned;
 private boolean isDamaged;
 private String remarks;
 
public String getSourceZoneId() {
	return sourceZoneId;
}
public void setSourceZoneId(String sourceZoneId) {
	this.sourceZoneId = sourceZoneId;
}
public String getDestinationZoneId() {
	return destinationZoneId;
}
public void setDestinationZoneId(String destinationZoneId) {
	this.destinationZoneId = destinationZoneId;
}
public UUID getWaybillNo() {
	return waybillNo;
}
public void setWaybillNo(UUID waybillNo) {
	this.waybillNo = waybillNo;
}

public Address getSourceAddress() {
	return sourceAddress;
}
public void setSourceAddress(Address sourceAddress) {
	this.sourceAddress = sourceAddress;
}
public Address getDestinationAddress() {
	return destinationAddress;
}
public void setDestinationAddress(Address destinationAddress) {
	this.destinationAddress = destinationAddress;
}
public String getPackageType() {
	return packageType;
}
public void setPackageType(String packageType) {
	this.packageType = packageType;
}
public String getPriority() {
	return priority;
}
public void setPriority(String priority) {
	this.priority = priority;
}
public Date getDispatchDate() {
	return dispatchDate;
}
public void setDispatchDate(Date dispatchDate) {
	this.dispatchDate = dispatchDate;
}
public Date getExpDeliveryDate() {
	return expDeliveryDate;
}
public void setExpDeliveryDate(Date expDeliveryDate) {
	this.expDeliveryDate = expDeliveryDate;
}
public Date getActualDeliveryDate() {
	return actualDeliveryDate;
}
public void setActualDeliveryDate(Date actualDeliveryDate) {
	this.actualDeliveryDate = actualDeliveryDate;
}
public String getCurrentStatus() {
	return currentStatus;
}
public void setCurrentStatus(String currentStatus) {
	this.currentStatus = currentStatus;
}
public String getItemName() {
	return itemName;
}
public void setItemName(String itemName) {
	this.itemName = itemName;
}
public boolean isReturned() {
	return isReturned;
}
public void setReturned(boolean isReturned) {
	this.isReturned = isReturned;
}
public boolean isDamaged() {
	return isDamaged;
}
public void setDamaged(boolean isDamaged) {
	this.isDamaged = isDamaged;
}
public String getRemarks() {
	return remarks;
}
public void setRemarks(String remarks) {
	this.remarks = remarks;
}
public String getFormatedDispatchDate() {
	return dateFormat.format(dispatchDate);
}
@Override
public String toString() {
	return String
			.format("Source Zone: %s \n Destination Zone: %s \n Source Address: %s \n Destination Address: %s \n waybillNo: %s \n ",
					sourceZoneId,destinationZoneId, sourceAddress, destinationAddress,waybillNo);
}
 
}
