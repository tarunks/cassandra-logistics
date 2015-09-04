package com.tarun.myapp.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.UDTValue;
import com.datastax.driver.core.UserType;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.UDTMapper;
import com.tarun.myapp.CassandraClient;
import com.tarun.myapp.entity.Address;
import com.tarun.myapp.entity.ShipmentDetail;
import com.tarun.myapp.services.LogisticsServiceImpl;

import static com.datastax.driver.core.querybuilder.QueryBuilder.*;

public class ShipmentRepositoryImpl implements ShipmentRepository {

	static Logger logger = LoggerFactory.getLogger(ShipmentRepositoryImpl.class);
	private CassandraClient cassandraClient;	
	
	private UDTMapper<Address> mapper;
	
	public ShipmentRepositoryImpl(CassandraClient cassandraClient
			) {
		this.cassandraClient = cassandraClient;
				
		mapper = new MappingManager(cassandraClient.getIndiaUkUsSession())
		   .udtMapper(Address.class);
		
	}

	private List<ShipmentDetail> mapShipment(ResultSet result) {
		
		List<ShipmentDetail> lstDetails = new ArrayList<ShipmentDetail>();
		Iterator<Row> iterator=result.iterator();
		while(iterator.hasNext()){
			lstDetails.add(mapShipment(iterator.next()));
		}
		return lstDetails;
	}

	private ShipmentDetail mapShipment(Row row) {
		if (row == null)
			return null;
		ShipmentDetail shipmentDetail = new ShipmentDetail();
		shipmentDetail.setSourceZoneId(row.getString("source_zone_id"));
		shipmentDetail.setDestinationZoneId(row
				.getString("destination_zone_id"));
		shipmentDetail.setWaybillNo(row.getUUID("waybill_no"));
		
		UDTValue udtSourceAddress=row.getUDTValue("source_address");
		
		logger.info(udtSourceAddress.toString());
		Address sourceAddress = mapper.fromUDT(udtSourceAddress);
		
		/*for (UDTValue addr : row.getMap("source_address", String.class,
				UDTValue.class).values()) {
			address.setStreet(addr.getString("street"));
			address.setCity(addr.getString("city"));
			address.setState(addr.getString("state"));
			address.setCountry(addr.getString("country"));
			address.setZip(addr.getString("zip"));
			address.setEmail(addr.getString("email"));
			address.setPhone(addr.getList("phone", String.class));
		}*/
		shipmentDetail.setSourceAddress(sourceAddress);
		UDTValue udtDestAddress=row.getUDTValue("destination_address");
		logger.info(udtDestAddress.toString());
		Address destinationAddress = mapper.fromUDT(udtDestAddress);
		
/*
		for (UDTValue addr : row.getMap("destination_address", String.class,
				UDTValue.class).values()) {
			address.setStreet(addr.getString("street"));
			address.setCity(addr.getString("city"));
			address.setState(addr.getString("state"));
			address.setCountry(addr.getString("country"));
			address.setZip(addr.getString("zip"));
			address.setEmail(addr.getString("email"));
			address.setPhone(addr.getList("phone", String.class));
		}
*/	
		shipmentDetail.setDestinationAddress(destinationAddress);
		shipmentDetail.setPriority(row.getString("priority"));
		shipmentDetail.setDispatchDate(row.getTimestamp("dispatch_date"));
		shipmentDetail
				.setExpDeliveryDate(row.getTimestamp("exp_delivery_date"));
		shipmentDetail.setActualDeliveryDate(row
				.getTimestamp("actual_delivery_date"));
		shipmentDetail.setCurrentStatus(row.getString("current_status"));
		shipmentDetail.setItemName(row.getString("item_name"));
		shipmentDetail.setReturned(row.getBool("is_returned"));
		shipmentDetail.setDamaged(row.getBool("is_damaged"));
		shipmentDetail.setRemarks(row.getString("remarks"));

		return shipmentDetail;
	}

	public List<ShipmentDetail> getLatestShipments(){
		Session session = cassandraClient.getIndiaUkUsSession();
		PreparedStatement statement = session.prepare(
				"SELECT * FROM shipment_detail limit 40")
				.setConsistencyLevel(ConsistencyLevel.ONE);

		ResultSet result = session.execute(statement.bind());

		return mapShipment(result);
	}
	public ShipmentDetail getShipmentDetails(UUID waybillNo) {
		Session session = cassandraClient.getIndiaUkUsSession();
		PreparedStatement statement = session.prepare(
				"SELECT * FROM shipment_detail_by_waybillno " + " WHERE waybill_no=?;")
				.setConsistencyLevel(ConsistencyLevel.ONE);

		Row result = session.execute(statement.bind(waybillNo)).one();

		return mapShipment(result);
	}

	public ShipmentDetail addNewConsignment(ShipmentDetail shipmentDetail) {
		Session session = cassandraClient.getIndiaUkUsSession();
		BatchStatement batch = new BatchStatement();
		batch.enableTracing();
		PreparedStatement shipmentDetailStatement = session.prepare(
				"INSERT into shipment_detail (" + "source_zone_id,"
						+ "destination_zone_id," + "waybill_no," 
						+ "source_address, " + "destination_address,"
						+ "package_type," + "priority," + "dispatch_date,"
						+ "exp_delivery_date," + "actual_delivery_date,"
						+ "current_status," + "item_name," + "is_returned,"
						+ "is_damaged," + "remarks"
						+ ") values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);")
				.setConsistencyLevel(ConsistencyLevel.ONE);

		
		
		UDTValue sourceAddresse =mapper.toUDT(shipmentDetail.getSourceAddress());
		UDTValue destinationAddresse =mapper.toUDT(shipmentDetail.getDestinationAddress());
		
		batch.add(shipmentDetailStatement.bind(
				shipmentDetail.getSourceZoneId(),
				shipmentDetail.getDestinationZoneId(),
				shipmentDetail.getWaybillNo(), 
				sourceAddresse,
				destinationAddresse,
				shipmentDetail.getPackageType(), shipmentDetail.getPriority(),
				shipmentDetail.getDispatchDate(),
				shipmentDetail.getExpDeliveryDate(),
				shipmentDetail.getActualDeliveryDate(),
				shipmentDetail.getCurrentStatus(),
				shipmentDetail.getItemName(), shipmentDetail.isReturned(),
				shipmentDetail.isDamaged(), shipmentDetail.getRemarks()));

		logger.info(shipmentDetailStatement.getQueryString());
		PreparedStatement shipmentDetailSourceLocationStatement = session
				.prepare(
						"INSERT into shipment_detail_source_location ("
								+ "source_zone_id," + "destination_zone_id,"
								+ "waybill_no," 
								+ "source_address, " + "destination_address,"
								+ "package_type," + "priority,"
								+ "dispatch_date," + "exp_delivery_date,"
								+ "actual_delivery_date," + "current_status,"
								+ "item_name," + "is_returned," + "is_damaged,"
								+ "remarks"
								+ ") values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);")
				.setConsistencyLevel(ConsistencyLevel.ONE);

		batch.add(shipmentDetailSourceLocationStatement.bind(
				shipmentDetail.getSourceZoneId(),
				shipmentDetail.getDestinationZoneId(),
				shipmentDetail.getWaybillNo(),
				sourceAddresse,
				destinationAddresse,
				shipmentDetail.getPackageType(), shipmentDetail.getPriority(),
				shipmentDetail.getDispatchDate(),
				shipmentDetail.getExpDeliveryDate(),
				shipmentDetail.getActualDeliveryDate(),
				shipmentDetail.getCurrentStatus(),
				shipmentDetail.getItemName(), shipmentDetail.isReturned(),
				shipmentDetail.isDamaged(), shipmentDetail.getRemarks()));

		logger.info(shipmentDetailSourceLocationStatement.getQueryString());
		PreparedStatement shipmentDetailDestinationLocationStatement = session
				.prepare(
						"INSERT into shipment_detail_destination_location ("
								+ "source_zone_id," + "destination_zone_id,"
								+ "waybill_no," 
								+ "source_address, " + "destination_address,"
								+ "package_type," + "priority,"
								+ "dispatch_date," + "exp_delivery_date,"
								+ "actual_delivery_date," + "current_status,"
								+ "item_name," + "is_returned," + "is_damaged,"
								+ "remarks"
								+ ") values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);")
				.setConsistencyLevel(ConsistencyLevel.ONE);

		batch.add(shipmentDetailDestinationLocationStatement.bind(
				shipmentDetail.getSourceZoneId(),
				shipmentDetail.getDestinationZoneId(),
				shipmentDetail.getWaybillNo(), 
				sourceAddresse,
				destinationAddresse,
				shipmentDetail.getPackageType(), shipmentDetail.getPriority(),
				shipmentDetail.getDispatchDate(),
				shipmentDetail.getExpDeliveryDate(),
				shipmentDetail.getActualDeliveryDate(),
				shipmentDetail.getCurrentStatus(),
				shipmentDetail.getItemName(), shipmentDetail.isReturned(),
				shipmentDetail.isDamaged(), shipmentDetail.getRemarks()));
		
		PreparedStatement shipmentDetailByWaybillStatement = session.prepare(
				"INSERT into shipment_detail_by_waybillno (" + "source_zone_id,"
						+ "destination_zone_id," + "waybill_no," 
						+ "source_address, " + "destination_address,"
						+ "package_type," + "priority," + "dispatch_date,"
						+ "exp_delivery_date," + "actual_delivery_date,"
						+ "current_status," + "item_name," + "is_returned,"
						+ "is_damaged," + "remarks"
						+ ") values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);")
				.setConsistencyLevel(ConsistencyLevel.ONE);
		
		batch.add(shipmentDetailByWaybillStatement.bind(
				shipmentDetail.getSourceZoneId(),
				shipmentDetail.getDestinationZoneId(),
				shipmentDetail.getWaybillNo(), 
				sourceAddresse,
				destinationAddresse,
				shipmentDetail.getPackageType(), shipmentDetail.getPriority(),
				shipmentDetail.getDispatchDate(),
				shipmentDetail.getExpDeliveryDate(),
				shipmentDetail.getActualDeliveryDate(),
				shipmentDetail.getCurrentStatus(),
				shipmentDetail.getItemName(), shipmentDetail.isReturned(),
				shipmentDetail.isDamaged(), shipmentDetail.getRemarks()));
		
		logger.info(shipmentDetailDestinationLocationStatement.getQueryString());
		Collection<Statement> statements=batch.getStatements();
		Iterator<Statement> iterator=statements.iterator();
		while(iterator.hasNext()){
			logger.info(iterator.next().toString());
		}
		session.execute(batch);

	
		
		
		return shipmentDetail;
	}

	public ShipmentDetail updateConsignment(ShipmentDetail shipmentDetail,
			ShipmentDetail newShipmentDetail) {
		Session session = cassandraClient.getIndiaUkUsSession();
		BatchStatement batch = new BatchStatement();
		PreparedStatement shipmentDetailStatement = session.prepare(
				"UPDATE shipment_detail SET "

				+ "exp_delivery_date=?," + "actual_delivery_date=?,"
						+ "current_status=?," + "item_name=?,"
						+ "is_returned=?," + "is_damaged=?," + "remarks=?"
						+ " WHERE source_zone_id=? "
						+ "AND destination_zone_id=? " + "AND waybill_no=?;")
				.setConsistencyLevel(ConsistencyLevel.ONE);
		
		batch.add(shipmentDetailStatement.bind(

		shipmentDetail.getExpDeliveryDate(),
				shipmentDetail.getActualDeliveryDate(),
				shipmentDetail.getCurrentStatus(),
				shipmentDetail.getItemName(), shipmentDetail.isReturned(),
				shipmentDetail.isDamaged(), shipmentDetail.getRemarks(),
				shipmentDetail.getSourceZoneId(),
				shipmentDetail.getDestinationZoneId(),
				shipmentDetail.getWaybillNo()));

		PreparedStatement shipmentDetailSourceLocationStatement = session
				.prepare(
						"UPDATE shipment_detail_source_location SET "

						+ "exp_delivery_date=?," + "actual_delivery_date=?,"
								+ "current_status=?," + "item_name=?,"
								+ "is_returned=?," + "is_damaged=?,"
								+ "remarks=?" + " WHERE source_zone_id=? "
								+ "AND waybill_no=?;").setConsistencyLevel(
						ConsistencyLevel.ONE);

		batch.add(shipmentDetailSourceLocationStatement.bind(
				shipmentDetail.getExpDeliveryDate(),
				shipmentDetail.getActualDeliveryDate(),
				shipmentDetail.getCurrentStatus(),
				shipmentDetail.getItemName(), shipmentDetail.isReturned(),
				shipmentDetail.isDamaged(), shipmentDetail.getRemarks(),
				shipmentDetail.getSourceZoneId(), shipmentDetail.getWaybillNo()));

		PreparedStatement shipmentDetailDestinationLocationStatement = session
				.prepare(
						"UPDATE shipment_detail_destination_location SET "

						+ "exp_delivery_date=?," + "actual_delivery_date=?,"
								+ "current_status=?," + "item_name=?,"
								+ "is_returned=?," + "is_damaged=?,"
								+ "remarks=?" + "AND destination_zone_id=? "
								+ "AND waybill_no=?;").setConsistencyLevel(
						ConsistencyLevel.ONE);

		batch.add(shipmentDetailDestinationLocationStatement.bind(
				shipmentDetail.getExpDeliveryDate(),
				shipmentDetail.getActualDeliveryDate(),
				shipmentDetail.getCurrentStatus(),
				shipmentDetail.getItemName(), shipmentDetail.isReturned(),
				shipmentDetail.isDamaged(), shipmentDetail.getRemarks(),
				shipmentDetail.getDestinationZoneId(),
				shipmentDetail.getWaybillNo()));
		session.execute(batch);

		return newShipmentDetail;
	}

}
