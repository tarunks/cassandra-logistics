package com.tarun.mapi.coe;

import com.tarun.mapi.coe.controller.*;
import com.tarun.mapi.coe.repository.ShipmentRepository;
import com.tarun.mapi.coe.repository.ShipmentRepositoryImpl;
import com.tarun.mapi.coe.repository.ShipmentTrackRepository;
import com.tarun.mapi.coe.repository.ShipmentTrackRepositoryImpl;
import com.tarun.mapi.coe.services.LogisticsService;
import com.tarun.mapi.coe.services.LogisticsServiceImpl;

public class App {
	public static void main(String[] args) {
		CassandraClient client = new CassandraClient("127.0.0.1");

		ShipmentRepository shipmentRepository = new ShipmentRepositoryImpl(
				client);
		ShipmentTrackRepository shipmentTrackRepository = new ShipmentTrackRepositoryImpl(
				client);
		LogisticsService shipmentService = new LogisticsServiceImpl(
				shipmentRepository, shipmentTrackRepository);
		new LogisticsController(shipmentService);
	}
}
