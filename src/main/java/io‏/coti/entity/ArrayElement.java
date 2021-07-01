package io‏.coti.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "array_elements")
public class ArrayElement {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "index_id")
	private Integer indexId;

	@Column(name = "number_value")
	private int numberValue;

	public ArrayElement() {

	}

	public ArrayElement(int numberValue) {
		super();
		this.numberValue = numberValue;
	}

	public Integer getIndexId() {
		return indexId;
	}

	public void setIndexId(Integer indexId) {
		this.indexId = indexId;
	}

	public int getNumberValue() {
		return numberValue;
	}

	public void setNumberValue(int numberValue) {
		this.numberValue = numberValue;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ArrayElement [indexId=");
		builder.append(indexId);
		builder.append(", numberValue=");
		builder.append(numberValue);
		builder.append("]");
		return builder.toString();
	}
}
