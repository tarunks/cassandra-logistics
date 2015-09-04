package com.tarun.myapp.services;

import java.util.List;
import java.util.UUID;

import com.tarun.myapp.entity.ShipmentDetail;
import com.tarun.myapp.entity.ShipmentTrack;
import com.tarun.myapp.entity.Track;

public interface LogisticsService {

	List<ShipmentDetail> getLatestShipments();
	ShipmentDetail getShipmentDetails(UUID waybillNo);
	ShipmentDetail addNewConsignment(ShipmentDetail shipmentDetail);
	void updateConsignment(UUID waybillNo,ShipmentDetail shipmentDetail);
	
	ShipmentTrack getShipmentTrackDetails(UUID waybillNo);
	void addNewTrack(UUID waybillNo,Track track);
	
}
