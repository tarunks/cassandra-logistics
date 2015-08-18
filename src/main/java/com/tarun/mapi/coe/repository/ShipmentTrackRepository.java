package com.tarun.mapi.coe.repository;

import java.util.List;
import java.util.UUID;

import com.tarun.mapi.coe.entity.ShipmentTrack;

public interface ShipmentTrackRepository {

	List<ShipmentTrack> getShipmentTrack(UUID waybillNo);
	ShipmentTrack getOneShipmentTrack(UUID waybillNo);
	void addNewTrack(ShipmentTrack shipmentTrack);
	
}
