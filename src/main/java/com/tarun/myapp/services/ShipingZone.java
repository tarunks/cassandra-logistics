package com.tarun.myapp.services;

import java.io.Serializable;

public enum ShipingZone implements Serializable {

	US, IN, UK;

	public String toString() {
		switch (this) {

		case US:
			return "United States";
		case UK:
			return "United Kingdom";
		case IN:
			return "India";
		}
		return null;
	}

	
}
