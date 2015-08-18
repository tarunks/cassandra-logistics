package com.tarun.mapi.coe.services;

import java.util.List;
import java.util.UUID;

import com.tarun.mapi.coe.entity.ShipmentDetail;
import com.tarun.mapi.coe.entity.ShipmentTrack;

public interface LogisticsService {

	List<ShipmentDetail> getLatestShipments();
	ShipmentDetail getShipmentDetails(UUID waybillNo);
	ShipmentDetail addNewConsignment(ShipmentDetail shipmentDetail);
	void updateConsignment(UUID waybillNo,ShipmentDetail shipmentDetail);
	
	List<ShipmentTrack> getShipmentTrackDetails(UUID waybillNo);
	void addNewTrack(ShipmentTrack shipmentTrack);
	
}
