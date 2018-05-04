package com.kc.scanner.model;

import java.io.Serializable;

public class Record implements Serializable {
	private StockType stockType; //入库出库
	private Long id;// 货物ID
	private String name;// 货物名
	private String type;// 货物类型
	private String size;// 货物规格
	private Double value;// 货物价值
	private Long number; //数量
	private Long repositoryID;//仓库ID
	private Long supplierID;//供应商ID
	private Long customerID; //客户ID

	public StockType getStockType() {
		return stockType;
	}

	public void setStockType(StockType stockType) {
		this.stockType = stockType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public Long getRepositoryID() {
		return repositoryID;
	}

	public void setRepositoryID(Long repositoryID) {
		this.repositoryID = repositoryID;
	}

	public Long getSupplierID() {
		return supplierID;
	}

	public void setSupplierID(Long supplierID) {
		this.supplierID = supplierID;
	}

	public Long getCustomerID() {
		return customerID;
	}

	public void setCustomerID(Long customerID) {
		this.customerID = customerID;
	}

	@Override
	public String toString() {
		return "Record{" +
				"stockType=" + stockType +
				", id=" + id +
				", name='" + name + '\'' +
				", type='" + type + '\'' +
				", size='" + size + '\'' +
				", value=" + value +
				", number=" + number +
				", repositoryID=" + repositoryID +
				", supplierID=" + supplierID +
				", customerID=" + customerID +
				'}';
	}
}
