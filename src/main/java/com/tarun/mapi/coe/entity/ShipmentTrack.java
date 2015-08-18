package com.tarun.mapi.coe.entity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.Frozen;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import com.datastax.driver.mapping.annotations.Transient;
import com.datastax.driver.mapping.annotations.UDT;
import com.google.common.base.Objects;


@Table(name = "shipment_track")
public class ShipmentTrack {

	
	
	@PartitionKey
	@Column(name = "waybill_no")
	private UUID waybillNo;
	@Frozen
	@Column(name = "source_address")
	private Address sourceAddress;
	@Frozen
	@Column(name = "destination_address")
	private Address destinationAddress;	
	@Column(name = "route_path")
	private Map<String, Track> routPath;
	

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

	
	public Map<String, Track> getRoutPath() {
		return routPath;
	}

	public void setRoutPath(Map<String, Track> routPath) {
		this.routPath = routPath;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof ShipmentTrack) {
			ShipmentTrack that = (ShipmentTrack) other;
			return Objects.equal(this.waybillNo, that.waybillNo);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(waybillNo);
	}
}
