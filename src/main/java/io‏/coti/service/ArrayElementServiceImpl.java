package io‏.coti.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io‏.coti.entity.ArrayElement;
import io‏.coti.repository.ArrayElementRepository;

@Transactional
@Service
public class ArrayElementServiceImpl implements ArrayElementService {

	private final ArrayElementRepository arrayElementRepository;

	public ArrayElementServiceImpl(@Autowired final ArrayElementRepository arrayElementRepository) {

		this.arrayElementRepository = arrayElementRepository;
	}

	@Override
	public int saveAndGetIndexId(ArrayElement arrayElement) {

		return arrayElementRepository.saveAndFlush(arrayElement).getIndexId();
	}

	@Override
	public void swapLastValueWithIndexValue(int lastIndexId, int indexId, int lastIndexValue, int indexValue) {

		arrayElementRepository.setNumberValueByIndexId(indexValue, lastIndexId);
		arrayElementRepository.setNumberValueByIndexId(lastIndexValue, indexId);
	}
}
