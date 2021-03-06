package com.tarun.myapp.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tarun.myapp.controller.LogisticsController;
import com.tarun.myapp.entity.Address;
import com.tarun.myapp.entity.ShipmentDetail;
import com.tarun.myapp.entity.ShipmentTrack;
import com.tarun.myapp.entity.Track;
import com.tarun.myapp.repository.ShipmentRepository;
import com.tarun.myapp.repository.ShipmentTrackRepository;

enum ShipingStatus {
	DEP,
}

public class LogisticsServiceImpl implements LogisticsService {
	Calendar cal = Calendar.getInstance();

	static Logger logger = LoggerFactory.getLogger(LogisticsServiceImpl.class);
	private ShipmentRepository shipmentRepository;
	private ShipmentTrackRepository shipmentTrackRepository;

	public LogisticsServiceImpl(ShipmentRepository shipmentRepository,
			ShipmentTrackRepository shipmentTrackRepository) {
		this.shipmentRepository = shipmentRepository;
		this.shipmentTrackRepository = shipmentTrackRepository;

	}

	public List<ShipmentDetail> getLatestShipments() {
		return shipmentRepository.getLatestShipments();
	}

	public ShipmentDetail getShipmentDetails(UUID waybillNo) {
		return shipmentRepository.getShipmentDetails(waybillNo);

	}

	public ShipmentDetail addNewConsignment(ShipmentDetail shipmentDetail) {
		UUID waybillNo = UUID.randomUUID();
		shipmentDetail.setWaybillNo(waybillNo);

		shipmentDetail.setDispatchDate(cal.getTime());
		shipmentRepository.addNewConsignment(shipmentDetail);
		Track track = new Track();
		ShipmentTrack shipmentTrack = new ShipmentTrack();
		shipmentTrack.setWaybillNo(waybillNo);
		track.setLocation(shipmentDetail.getSourceAddress().getCity());
		track.setZone(shipmentDetail.getSourceZoneId());
		shipmentTrack.setDestinationAddress(shipmentDetail
				.getDestinationAddress());
		track.setRemarks(shipmentDetail.getRemarks());
		shipmentTrack.setSourceAddress(shipmentDetail.getSourceAddress());
		track.setUpdateDate(shipmentDetail.getDispatchDate());
		List<Track> routPath = new ArrayList<Track>();
		routPath.add(track);
		shipmentTrack.setRoutPath(routPath);
		shipmentTrackRepository.addNewTrack(shipmentTrack);
		return shipmentDetail;

	}

	private Map<String, Track> getRoutPath(String sourceZone,
			String destinationZone) {
		Map<String, Track> routPath = new HashMap<String, Track>();

		ShipingZone sZone = Enum.valueOf(ShipingZone.class, sourceZone);
		ShipingZone dZone = Enum.valueOf(ShipingZone.class, destinationZone);
		if (ShipingZone.IN == sZone && ShipingZone.US == dZone) {
			routPath.put(ShipingZone.IN.name(), null);
			routPath.put(ShipingZone.UK.name(), null);
			routPath.put(ShipingZone.US.name(), null);
		} else if (ShipingZone.US == sZone && ShipingZone.IN == dZone) {

			routPath.put(ShipingZone.US.name(), null);
			routPath.put(ShipingZone.UK.name(), null);
			routPath.put(ShipingZone.IN.name(), null);
		} else if (ShipingZone.IN == sZone && ShipingZone.UK == dZone) {
			routPath.put(ShipingZone.IN.name(), null);
			routPath.put(ShipingZone.UK.name(), null);

		} else if (ShipingZone.UK == sZone && ShipingZone.IN == dZone) {
			routPath.put(ShipingZone.UK.name(), null);
			routPath.put(ShipingZone.IN.name(), null);
		} else if (ShipingZone.US == sZone && ShipingZone.UK == dZone) {
			routPath.put(ShipingZone.US.name(), null);
			routPath.put(ShipingZone.UK.name(), null);

		} else if (ShipingZone.UK == sZone && ShipingZone.US == dZone) {
			routPath.put(ShipingZone.UK.name(), null);
			routPath.put(ShipingZone.US.name(), null);
		}
		return routPath;
	}

	public void updateConsignment(UUID waybillNo, ShipmentDetail shipmentDetail) {
		ShipmentDetail oldShipmentDetail = shipmentRepository
				.getShipmentDetails(waybillNo);
		shipmentRepository.updateConsignment(oldShipmentDetail, shipmentDetail);

	}

	public ShipmentTrack getShipmentTrackDetails(UUID waybillNo) {
		return shipmentTrackRepository.getShipmentTrack(waybillNo);

	}

	public void addNewTrack(UUID waybillNo,Track track) {
		ShipmentTrack shimentTrack = shipmentTrackRepository
				.getOneShipmentTrack(waybillNo);
		if (shimentTrack != null) {
			List<Track> trackList=shimentTrack.getRoutPath();
			track.setUpdateDate(cal.getTime());
			logger.info("TRACK:" + track.toString());
			trackList.add(track);

			shipmentTrackRepository.addNewTrack(shimentTrack);
		}
		
	}

}
