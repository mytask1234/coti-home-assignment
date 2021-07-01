package io‏.coti.dto;

public class OutputDto {

	private Long output1;
	private Long output2;
	
	public OutputDto() {
		
	}
	
	public OutputDto(Long output1, Long output2) {
		super();
		this.output1 = output1;
		this.output2 = output2;
	}

	public Long getOutput1() {
		return output1;
	}

	public void setOutput1(Long output1) {
		this.output1 = output1;
	}

	public Long getOutput2() {
		return output2;
	}

	public void setOutput2(Long output2) {
		this.output2 = output2;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OutputDto [output1=");
		builder.append(output1);
		builder.append(", output2=");
		builder.append(output2);
		builder.append("]");
		return builder.toString();
	}
}
