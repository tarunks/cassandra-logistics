package com.tarun.mapi.coe.entity;

import java.util.List;

import com.datastax.driver.mapping.annotations.UDT;

@UDT(name = "address")
public class Address {
	private String name;
	private String street;
	private String city;
	private String state;
	private String country;
	private String zip;
	private List<String> phone;
	private String email;

	public Address(){}
	public Address(String name, String street, String city, String state,
			String country, String zip, List<String> phone, String email) {
		this.name = name;
		this.street = street;
		this.city = city;
		this.state = state;
		this.country = country;
		this.zip = zip;
		this.phone = phone;
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public List<String> getPhone() {
		return phone;
	}

	public void setPhone(List<String> phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return String
				.format("Name: %s \n Street: %s \n City: %s \n State: %s \n Country: %s \n Zip: %s \n Phone: %s \n Email: %s \n",
						name,street, city, state, country, zip, phone, email);
	}
}
