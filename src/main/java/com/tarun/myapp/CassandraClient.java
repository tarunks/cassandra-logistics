package com.tarun.myapp;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;

public class CassandraClient {

	private Cluster cluster;
	private Session globalShipmetTrackSession;
	private Session indiaUkUsSession;
	private Session ukUsSession;
	private Session indiaUkSession;
	public CassandraClient(String... clusterNode){
		setupConnection(clusterNode);
	}
	private void setupConnection(String... node) {
		if (cluster == null) {
			cluster = Cluster.builder().addContactPoints(node).build();
		
			Metadata metadata = cluster.getMetadata();
			System.out.printf("Connected to cluster: %s\n",
					metadata.getClusterName());
			for (Host host : metadata.getAllHosts()) {
				System.out
						.printf("Datacenter: %s; Host: %s; Rack: %s\n",
								host.getDatacenter(), host.getAddress(),
								host.getRack());
			}
		}
	}
	public Session getGlobalShipmetTrackSession() {
		if (globalShipmetTrackSession == null) {
		
			globalShipmetTrackSession = cluster.connect("global_shipment_track");
		
		}
		return globalShipmetTrackSession;
	}
	public Session getIndiaUkUsSession() {
		if (indiaUkUsSession == null) {
		
			indiaUkUsSession = cluster.connect("shipment_india_uk_us");
		
		}
		return indiaUkUsSession;
	}
	public Session getUkUsSession() {
		if (ukUsSession == null) {
		
			ukUsSession = cluster.connect("shipment_uk_us");
		
		}
		return ukUsSession;
	}
	public Session getIndiaUkSession() {
		if (indiaUkSession == null) {
		
			indiaUkSession = cluster.connect("shipment_india_uk");
		
		}
		return indiaUkSession;
	}
}
