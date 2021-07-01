package io‏.coti.dto;

import io‏.coti.exception.InvalidInputException;

public class NumberDto {

	private Integer number;
	
	public NumberDto() {
		
	}
	
	public void validate() {
		
		if (number == null) {
			
			throw new InvalidInputException("field number can't be null");
		}
		
		if (number < 1) {
			
			String message = String.format("field number can't be less than 1. number: %d", number);
			
			throw new InvalidInputException(message);
		}
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NumberDto [number=");
		builder.append(number);
		builder.append("]");
		return builder.toString();
	}
}
