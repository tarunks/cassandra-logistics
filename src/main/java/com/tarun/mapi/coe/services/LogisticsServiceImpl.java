package com.tarun.mapi.coe.services;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tarun.mapi.coe.controller.LogisticsController;
import com.tarun.mapi.coe.entity.Address;
import com.tarun.mapi.coe.entity.ShipmentDetail;
import com.tarun.mapi.coe.entity.ShipmentTrack;
import com.tarun.mapi.coe.entity.Track;
import com.tarun.mapi.coe.repository.ShipmentRepository;
import com.tarun.mapi.coe.repository.ShipmentTrackRepository;
enum ShipingStatus{
	DEP,
}
public class LogisticsServiceImpl implements LogisticsService {
	Calendar cal = Calendar.getInstance();
	
	static Logger logger = LoggerFactory.getLogger(LogisticsServiceImpl.class);
	private ShipmentRepository shipmentRepository;
	private ShipmentTrackRepository shipmentTrackRepository;
	public LogisticsServiceImpl(ShipmentRepository shipmentRepository,ShipmentTrackRepository shipmentTrackRepository){
		this.shipmentRepository=shipmentRepository;
		this.shipmentTrackRepository= shipmentTrackRepository;
		
	}

	public List<ShipmentDetail> getLatestShipments(){
		return shipmentRepository.getLatestShipments();
	}
	public ShipmentDetail getShipmentDetails(UUID waybillNo) {
		return shipmentRepository.getShipmentDetails(waybillNo);
		
	}

	public ShipmentDetail addNewConsignment(ShipmentDetail shipmentDetail) {
		UUID waybillNo=UUID.randomUUID();
		shipmentDetail.setWaybillNo(waybillNo);
			
		shipmentDetail.setDispatchDate(cal.getTime());
		shipmentRepository.addNewConsignment(shipmentDetail);
		Track track=new Track();
		ShipmentTrack shipmentTrack=new ShipmentTrack();
		shipmentTrack.setWaybillNo(waybillNo);
		track.setLocation(shipmentDetail.getSourceZoneId());
		track.setZone(shipmentDetail.getSourceZoneId());
		shipmentTrack.setDestinationAddress(shipmentDetail.getDestinationAddress());
		track.setRemarks(shipmentDetail.getRemarks());
		shipmentTrack.setSourceAddress(shipmentDetail.getSourceAddress());
		track.setUpdateDate(shipmentDetail.getDispatchDate());
		Map<String,Track> routPath=getRoutPath(shipmentDetail.getSourceZoneId(),
				shipmentDetail.getDestinationZoneId());
		shipmentTrack.setRoutPath(routPath);
		addNewTrack(shipmentTrack);
		return shipmentDetail;
		
	}
	private Map<String,Track> getRoutPath(String sourceZone,String destinationZone){
		Map<String,Track> routPath=new HashMap<String,Track>();
		
		ShipingZone sZone=Enum.valueOf(ShipingZone.class, sourceZone);
		ShipingZone dZone=Enum.valueOf(ShipingZone.class, destinationZone);
		if(ShipingZone.IN == sZone && ShipingZone.US==dZone){
			routPath.put(ShipingZone.IN.name(), "true");
			routPath.put(ShipingZone.UK.name(), "false");
			routPath.put(ShipingZone.US.name(), "false");
		}
		else if(ShipingZone.US == sZone && ShipingZone.IN==dZone){
			
			routPath.put(ShipingZone.US.name(), "true");
			routPath.put(ShipingZone.UK.name(), "false");
			routPath.put(ShipingZone.IN.name(), "true");
		}
		else if(ShipingZone.IN == sZone && ShipingZone.UK==dZone){
			routPath.put(ShipingZone.IN.name(), "true");
			routPath.put(ShipingZone.UK.name(), "false");
			
		}
		else if(ShipingZone.UK == sZone && ShipingZone.IN==dZone){
			routPath.put(ShipingZone.UK.name(), "true");
			routPath.put(ShipingZone.IN.name(), "false");
		}
		else if(ShipingZone.US == sZone && ShipingZone.UK==dZone){
			routPath.put(ShipingZone.US.name(), "true");
			routPath.put(ShipingZone.UK.name(), "false");
			
		}
		else if(ShipingZone.UK == sZone && ShipingZone.US==dZone){
			routPath.put(ShipingZone.UK.name(), "true");
			routPath.put(ShipingZone.US.name(), "false");
		}
		return routPath;
	}

	public void updateConsignment(UUID waybillNo, ShipmentDetail shipmentDetail) {
		ShipmentDetail oldShipmentDetail=shipmentRepository.getShipmentDetails(waybillNo);
		shipmentRepository.updateConsignment(oldShipmentDetail, shipmentDetail);
		
	}

	public List<ShipmentTrack> getShipmentTrackDetails(UUID waybillNo) {
		return shipmentTrackRepository.getShipmentTrack(waybillNo);
		
	}

	public void addNewTrack(ShipmentTrack shipmentTrack) {
		ShipmentTrack oldTrack=shipmentTrackRepository.getOneShipmentTrack(shipmentTrack.getWaybillNo());
	//	shipmentTrack.setUpdateDate(cal.getTime());
		shipmentTrack.setSourceAddress(oldTrack.getSourceAddress());
		shipmentTrack.setDestinationAddress(oldTrack.getDestinationAddress());
		shipmentTrack.setRoutPath(oldTrack.getRoutPath());
		shipmentTrackRepository.addNewTrack(shipmentTrack);
	}

}
