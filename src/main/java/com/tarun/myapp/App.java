package com.tarun.myapp;

import com.tarun.myapp.controller.*;
import com.tarun.myapp.repository.ShipmentRepository;
import com.tarun.myapp.repository.ShipmentRepositoryImpl;
import com.tarun.myapp.repository.ShipmentTrackRepository;
import com.tarun.myapp.repository.ShipmentTrackRepositoryImpl;
import com.tarun.myapp.services.LogisticsService;
import com.tarun.myapp.services.LogisticsServiceImpl;

public class App {
	public static void main(String[] args) {
		CassandraClient client = new CassandraClient("127.0.0.1");
		ShipmentRepository shipmentRepository = new ShipmentRepositoryImpl(
				client);
		ShipmentTrackRepository shipmentTrackRepository = new ShipmentTrackRepositoryImpl(
				client);
		LogisticsService shipmentService = new LogisticsServiceImpl(shipmentRepository, shipmentTrackRepository);
		new LogisticsController(shipmentService);
	}
}
