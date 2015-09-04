package com.tarun.myapp.entity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.datastax.driver.mapping.annotations.Field;
import com.datastax.driver.mapping.annotations.Transient;
import com.datastax.driver.mapping.annotations.UDT;

@UDT(name = "TRACK")
public class Track {
	@Transient
	private DateFormat dateFormat=new SimpleDateFormat("MM/dd/yyyy");
	
	private String location;
	private String zone;
	@Field(name = "update_date")
	private Date updateDate;
	private String remarks;
	
	public Track(){}
	public Track(String location,String zone,Date updateDate,String remarks){
		this.location=location;
		this.zone= zone;
		this.updateDate=updateDate;
		this.remarks=remarks;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getFormatedUpdateDate() {
		return dateFormat.format(updateDate);
	}
	@Override
	public String toString(){
		return String.format("Hub Location:%s \n"
				+ "Zone:%s \n"
				+ "Date:%s \n"
				+ "Remarks:%s", location,zone,updateDate,remarks);
	}
}
