package com.tarun.mapi.coe.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.UDTValue;
import com.datastax.driver.core.UserType;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.UDTMapper;
import com.tarun.mapi.coe.CassandraClient;
import com.tarun.mapi.coe.entity.Address;
import com.tarun.mapi.coe.entity.ShipmentDetail;
import com.tarun.mapi.coe.entity.ShipmentTrack;

public class ShipmentTrackRepositoryImpl implements ShipmentTrackRepository {

	private CassandraClient client;

	//private UserType addressUDT;
	private UDTMapper<Address> addressMapper;
	private Mapper<ShipmentTrack> shipmentTrackMapper;
	public ShipmentTrackRepositoryImpl(CassandraClient client){
		this.client=client;
		
		/*addressUDT = client.getSession().getCluster().getMetadata()
				.getKeyspace(this.keyspace).getUserType("address");*/
		addressMapper = new MappingManager(client.getGlobalShipmetTrackSession())
		   .udtMapper(Address.class);
	}
	
private List<ShipmentTrack> mapShipment(ResultSet result) {
		
		List<ShipmentTrack> lstDetails = new ArrayList<ShipmentTrack>();
		Iterator<Row> iterator=result.iterator();
		while(iterator.hasNext()){
			lstDetails.add(mapShipment(iterator.next()));
		}
		return lstDetails;
	}

	private ShipmentTrack mapShipment(Row row) {
		if (row == null)
			return null;
		ShipmentTrack shipmentTrack = new ShipmentTrack();
		
		
		Address sourceAddress = addressMapper.fromUDT(row.getUDTValue("source_address"));
		shipmentTrack.setSourceAddress(sourceAddress);
		Address destinationAddress = addressMapper.fromUDT(row.getUDTValue("destination_address"));
		shipmentTrack.setDestinationAddress(destinationAddress);
	

		shipmentTrack.setWaybillNo(row.getUUID("Waybill_no"));
		shipmentTrack.setUpdateDate(row.getTimestamp("update_date"));
		shipmentTrack.setCurrentLocationId(row.getString("current_location_id"));
		shipmentTrack.setCurrentZoneId(row.getString("current_zone_id"));
		Map<String,String> routPath=row.getMap("route_path", String.class,String.class);
		shipmentTrack.setRoutPath(routPath);;
		shipmentTrack.setRemarks(row.getString("remarks"));

		return shipmentTrack;
	}
	public ShipmentTrack getOneShipmentTrack(UUID waybillNo){
		Session session = client.getGlobalShipmetTrackSession();
		PreparedStatement statement = session.prepare(
				"SELECT * FROM shipment_track " + "WHERE waybill_no=?;")
				.setConsistencyLevel(ConsistencyLevel.ONE);
		Row result = session.execute(statement.bind(waybillNo)).one();
		return mapShipment(result);
	}
	public List<ShipmentTrack> getShipmentTrack(UUID waybillNo) {
		
		Session session = client.getGlobalShipmetTrackSession();
		PreparedStatement statement = session.prepare(
				"SELECT * FROM shipment_track " + "WHERE waybill_no=?;")
				.setConsistencyLevel(ConsistencyLevel.ONE);

		ResultSet result = session.execute(statement.bind(waybillNo));

		return mapShipment(result);
		
	}

	public void addNewTrack(ShipmentTrack shipmentTrack) {		
	
		shipmentTrackMapper = new MappingManager(client.getGlobalShipmetTrackSession())
		   .mapper(ShipmentTrack.class);

		UDTValue sourceAddresse =addressMapper.toUDT(shipmentTrack.getSourceAddress());
		UDTValue destinationAddresse = addressMapper.toUDT(shipmentTrack.getDestinationAddress());
		
		Session session = client.getGlobalShipmetTrackSession();
		PreparedStatement statement = session.prepare(
				"INSERT INTO shipment_track ("
				+ "waybill_no,"
				+ "current_location_id,"
				+ "current_zone_id,"
				+ "destination_address,"
				+ "remarks,"
				+ "route_path,"
				+ "source_address,"
				+ "update_date) VALUES ("
				+ "?,?,?,?,?,?,?,?) ;")
				.setConsistencyLevel(ConsistencyLevel.ONE);
	
		session.execute(statement.bind(shipmentTrack.getWaybillNo(),
				shipmentTrack.getCurrentLocationId(),
				shipmentTrack.getCurrentZoneId(),
				destinationAddresse,
				shipmentTrack.getRemarks(),
				shipmentTrack.getRoutPath(),
				sourceAddresse,
				shipmentTrack.getUpdateDate()
				));
		
		
	}

	
}
