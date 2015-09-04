package com.tarun.myapp.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import com.tarun.myapp.CassandraClient;
import com.tarun.myapp.entity.Address;
import com.tarun.myapp.entity.ShipmentDetail;
import com.tarun.myapp.entity.ShipmentTrack;
import com.tarun.myapp.entity.Track;

public class ShipmentTrackRepositoryImpl implements ShipmentTrackRepository {

	static Logger logger = LoggerFactory
			.getLogger(ShipmentTrackRepositoryImpl.class);
	private CassandraClient client;

	// private UserType addressUDT;
	private UDTMapper<Address> addressMapper;
	private UDTMapper<Track> trackMapper;
	private Mapper<ShipmentTrack> shipmentTrackMapper;

	public ShipmentTrackRepositoryImpl(CassandraClient client) {
		this.client = client;

		/*
		 * addressUDT = client.getSession().getCluster().getMetadata()
		 * .getKeyspace(this.keyspace).getUserType("address");
		 */
		addressMapper = new MappingManager(
				client.getGlobalShipmetTrackSession()).udtMapper(Address.class);
		trackMapper = new MappingManager(client.getGlobalShipmetTrackSession())
				.udtMapper(Track.class);
	}

	private List<ShipmentTrack> mapShipment(ResultSet result) {

		List<ShipmentTrack> lstDetails = new ArrayList<ShipmentTrack>();
		Iterator<Row> iterator = result.iterator();
		while (iterator.hasNext()) {
			lstDetails.add(mapShipment(iterator.next()));
		}
		return lstDetails;
	}

	private ShipmentTrack mapShipment(Row row) {
		if (row == null)
			return null;
		ShipmentTrack shipmentTrack = new ShipmentTrack();

		Address sourceAddress = addressMapper.fromUDT(row
				.getUDTValue("source_address"));
		shipmentTrack.setSourceAddress(sourceAddress);
		Address destinationAddress = addressMapper.fromUDT(row
				.getUDTValue("destination_address"));
		shipmentTrack.setDestinationAddress(destinationAddress);

		shipmentTrack.setWaybillNo(row.getUUID("Waybill_no"));

		List<UDTValue> udtRoutPath = row.getList("route_path", UDTValue.class);
		List<Track> routPath = new ArrayList<Track>();
		for (UDTValue val : udtRoutPath) {
			Track t = trackMapper.fromUDT(val);
			routPath.add(t);
		}
		shipmentTrack.setRoutPath(routPath);
		
		return shipmentTrack;
	}

	public ShipmentTrack getOneShipmentTrack(UUID waybillNo) {
		Session session = client.getGlobalShipmetTrackSession();
		PreparedStatement statement = session.prepare(
				"SELECT * FROM shipment_track " + "WHERE waybill_no=?;")
				.setConsistencyLevel(ConsistencyLevel.ONE);
		Row result = session.execute(statement.bind(waybillNo)).one();
		return mapShipment(result);
	}

	public ShipmentTrack getShipmentTrack(UUID waybillNo) {

		Session session = client.getGlobalShipmetTrackSession();
		PreparedStatement statement = session.prepare(
				"SELECT * FROM shipment_track " + "WHERE waybill_no=?;")
				.setConsistencyLevel(ConsistencyLevel.ONE);

		Row result = session.execute(statement.bind(waybillNo)).one();

		return mapShipment(result);

	}

	public void addNewTrack(ShipmentTrack shipmentTrack) {

		shipmentTrackMapper = new MappingManager(
				client.getGlobalShipmetTrackSession())
				.mapper(ShipmentTrack.class);

		UDTValue sourceAddresse = addressMapper.toUDT(shipmentTrack
				.getSourceAddress());
		UDTValue destinationAddresse = addressMapper.toUDT(shipmentTrack
				.getDestinationAddress());

		List<UDTValue> routePath = new ArrayList<UDTValue>();
		List<Track> tracks = shipmentTrack.getRoutPath();

		for (Track t : tracks) {
			logger.info("TRACK:" + t.toString());
			UDTValue udtTrack = trackMapper.toUDT(t);
			routePath.add(udtTrack);
		}

		Session session = client.getGlobalShipmetTrackSession();
		PreparedStatement statement = session.prepare(
				"INSERT INTO shipment_track (" + "waybill_no,"
						+ "destination_address," + "route_path,"
						+ "source_address) VALUES (" + "?,?,?,?) ;")
				.setConsistencyLevel(ConsistencyLevel.ONE);

		session.execute(statement.bind(shipmentTrack.getWaybillNo(),
				destinationAddresse, routePath, sourceAddresse));

	}

}
