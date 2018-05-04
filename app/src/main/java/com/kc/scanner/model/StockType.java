package com.kc.scanner.model;

public enum StockType {
	In(0),
	Out(1);
	private final int value;

	StockType(int value) {
		this.value = value;
	}

	public int value() {
		return value;
	}
}
