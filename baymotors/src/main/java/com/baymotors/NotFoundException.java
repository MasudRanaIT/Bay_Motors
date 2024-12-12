package com.baymotors;

public class NotFoundException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private String type;
	
	public NotFoundException(String type) {
		super();
		this.type = type;
	}
	
	@Override
	public String getMessage() {
		return  this.type + " not found";
	}

}
