package com.tarun.mapi.coe.controller;

import static spark.Spark.get;
import static spark.Spark.post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.tarun.mapi.coe.entity.Address;
import com.tarun.mapi.coe.entity.ShipmentDetail;
import com.tarun.mapi.coe.entity.ShipmentTrack;
import com.tarun.mapi.coe.services.LogisticsService;

import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogisticsController {

	static Logger logger = LoggerFactory.getLogger(LogisticsController.class);
	LogisticsService logisticsService;

	public LogisticsController(LogisticsService logisticsService) {
		// public LogisticsController() {

		this.logisticsService = logisticsService;

		get("/",
				(req, res) -> {

					Map<String, Object> attributes = new HashMap<String, Object>();

					List<ShipmentDetail> shipments = logisticsService
							.getLatestShipments();
					if (shipments == null || shipments.size() <= 0) {
						attributes
								.put("hasNoShipment",
										"Welcome, please click \"New Shipment\" to create new shipment.");
					} else {
						attributes.put("shipments", shipments);
					}
					attributes.put("templateName", "shipment.ftl");
					return new ModelAndView(attributes, "home.ftl");
				}, new FreeMarkerEngine());
		
		
		get("/shiment/detail/:waybillno", (req, res) -> {

			Map<String, Object> attributes = new HashMap<String, Object>();
			
			String strWaybill=req.params(":waybillno");
			logger.info("============Waybill no==" + strWaybill);
			ShipmentDetail shipment= logisticsService.getShipmentDetails(UUID.fromString(strWaybill));
			attributes.put("shipment", shipment);
			attributes.put("templateName", "shipment-detail.ftl");
			return new ModelAndView(attributes, "home.ftl");
		}, new FreeMarkerEngine());
		
		

		get("/shipment/create", (req, res) -> {

			Map<String, Object> attributes = new HashMap<String, Object>();
			attributes.put("templateName", "consignmentForm.ftl");

			return new ModelAndView(attributes, "home.ftl");
		}, new FreeMarkerEngine());

		post("/shipment/create", (req, res) -> {
			ShipmentDetail shipmentDetail = new ShipmentDetail();
			
			String senderName = req.queryParams("txtSenderName");
			String street = req.queryParams("txtStreet");
			String city = req.queryParams("txtCity");
			String state = req.queryParams("txtState");
			String zip = req.queryParams("txtZip");
			String country = req.queryParams("txtCountry");
			String phone = req.queryParams("txtPhone");
			String email = req.queryParams("txtEmail");
			List<String> phones = new ArrayList<String>();
			phones.add(phone);
			Address address = new Address(senderName, street, city, state, zip,
					country, phones, email);
			logger.info("================= Source Address: " + address);
			shipmentDetail.setSourceAddress(address);
			shipmentDetail.setSourceZoneId(country);
			String receiverName = req.queryParams("txtReceiverName");
			street = req.queryParams("txtRStreet");
			city = req.queryParams("txtRCity");
			state = req.queryParams("txtRState");
			zip = req.queryParams("txtRZip");
			country = req.queryParams("txtRCountry");
			phone = req.queryParams("txtRPhone");
			email = req.queryParams("txtREmail");
			phones = new ArrayList<String>();
			phones.add(phone);
			address = new Address(receiverName, street, city, state, zip,
					country, phones, email);
			logger.info("================= Destination Address: " + address);
			shipmentDetail.setDestinationAddress(address);
			shipmentDetail.setDestinationZoneId(country);
			String priority = req.queryParams("priority");
			String itemDetail = req.queryParams("itemDetail");
			String remarks = req.queryParams("remarks");

			shipmentDetail.setPriority(priority);
			shipmentDetail.setItemName(itemDetail);
			shipmentDetail.setRemarks(remarks);

			logger.info(shipmentDetail.toString());
			logisticsService.addNewConsignment(shipmentDetail);
			
			res.status(201);
			res.redirect("/");
			return "";
		});
		get("/track/update/:waybillno", (req,res) -> {
			Map<String, Object> attributes = new HashMap<String, Object>();
			
			String strWaybill=req.params(":waybillno");
			logger.info("============Waybill no==" + strWaybill);
			attributes.put("waybillno", strWaybill);
			attributes.put("templateName", "trackForm.ftl");
			return new ModelAndView(attributes, "home.ftl");
		}, new FreeMarkerEngine());
		
		post("/track/update/", (req, res) -> {
		
			String waybillno = req.queryParams("waybillno");
			String currentZone = req.queryParams("currentZone");
			String currentLocation = req.queryParams("currentLocation");
			String remarks = req.queryParams("remarks");
			ShipmentTrack shipmentTrack=new ShipmentTrack();
			logger.info("*********===Waybill number:===*********" + waybillno);
			shipmentTrack.setWaybillNo(UUID.fromString(waybillno));
			shipmentTrack.setCurrentLocationId(currentLocation);
			shipmentTrack.setCurrentZoneId(currentZone);
			shipmentTrack.setRemarks(remarks);
			
			logisticsService.addNewTrack(shipmentTrack);
			res.status(201);
			res.redirect("/shipment/track/"+waybillno);
			return "";
		});
		
		
		get("shipment/track/:waybillno", (req, res) -> {

			Map<String, Object> attributes = new HashMap<String, Object>();
			String waybillNo=req.params(":waybillno");
			logger.info("============Waybill number:===" + waybillNo);
			List<ShipmentTrack> tracklist=logisticsService.getShipmentTrackDetails(UUID.fromString(waybillNo));
			if(tracklist!=null || tracklist.size()>0){
				ShipmentTrack shipment=tracklist.get(0);
				attributes.put("shipment", shipment);
				attributes.put("tracklist", tracklist);
			}
			
			attributes.put("templateName", "shipment-track.ftl");
			return new ModelAndView(attributes, "home.ftl");
		}, new FreeMarkerEngine());	
	}

}
