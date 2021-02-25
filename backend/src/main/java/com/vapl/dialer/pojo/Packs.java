package com.vapl.dialer.pojo;

public class Packs {

	private int id;
	private int pack_id;
	private String pack_name;
	private int pack_price;
	private int credits;
	private int agents;
	private int domains;
	private String validity;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPackId() {
		return pack_id;
	}

	public void setPackId(int pack_id) {
		this.pack_id = pack_id;
	}

	public String getPackName() {
		return pack_name;
	}

	public void setPackName(String pack_name) {
		this.pack_name = pack_name;
	}

	public int getPackPrice() {
		return pack_price;
	}

	public void setPackPrice(int pack_price) {
		this.pack_price = pack_price;
	}

	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}

	public int getAgents() {
		return agents;
	}

	public void setAgents(int agents) {
		this.agents = agents;
	}

	public int getDomains() {
		return domains;
	}

	public void setDomains(int domains) {
		this.domains = domains;
	}

	public String getValidity() {
		return validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}
}
