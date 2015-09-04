package com.tarun.myapp.repository;

import java.util.List;
import java.util.UUID;

import com.tarun.myapp.entity.ShipmentTrack;

public interface ShipmentTrackRepository {

	ShipmentTrack getShipmentTrack(UUID waybillNo);
	ShipmentTrack getOneShipmentTrack(UUID waybillNo);
	void addNewTrack(ShipmentTrack shipmentTrack);
	
}
