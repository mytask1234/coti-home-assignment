package io‏.coti.service;

import io‏.coti.entity.ArrayElement;

public interface ArrayElementService {

	int saveAndGetIndexId(ArrayElement arrayElement);
	void swapLastValueWithIndexValue(int lastIndexId, int indexId, int lastIndexValue, int indexValue);
}
