package com.tarun.myapp.repository;

import java.util.List;
import java.util.UUID;

import com.tarun.myapp.entity.ShipmentDetail;

public interface ShipmentRepository {

	List<ShipmentDetail> getLatestShipments();
	ShipmentDetail getShipmentDetails(UUID waybillNo);

	ShipmentDetail addNewConsignment(ShipmentDetail shipmentDetail) ;

	ShipmentDetail updateConsignment(ShipmentDetail oldShipmentDetail, ShipmentDetail newShipmentDetail) ;

}
