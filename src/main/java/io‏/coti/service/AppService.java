package io‏.coti.service;

import java.util.List;

import io‏.coti.dto.OutputDto;

public interface AppService {

	OutputDto insertNumber(final int number);
	void deleteNumber(final int index);
	List<Integer> returnIntervalArrayValues(final String indexes);
}
