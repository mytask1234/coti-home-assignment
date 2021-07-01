package io‏.coti.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io‏.coti.dto.OutputDto;
import io‏.coti.entity.ArrayElement;
import io‏.coti.exception.IndexNotFoundException;
import io‏.coti.exception.InvalidInputException;

@Service
public class AppServiceImpl implements AppService {

	private final ArrayElementService arrayElementService;
	private final AtomicLong currSumOfArrayElements = new AtomicLong(0);
	private final List<Integer> intervalList = new ArrayList<Integer>();
	private final List<Integer> dbNumberValuelList = new ArrayList<Integer>();
	private final Lock writeLock;
	private final Lock readLock;

	private volatile int lastIndexId = 0;

	public AppServiceImpl(@Autowired final ArrayElementService arrayElementService) {
		super();
		this.arrayElementService = arrayElementService;

		ReadWriteLock lock = new ReentrantReadWriteLock();

		writeLock = lock.writeLock();
		readLock = lock.readLock();
	}

	@Override
	public OutputDto insertNumber(int number) {

		long currSum;

		try {
			writeLock.lock();

			currSum = currSumOfArrayElements.addAndGet(number);

			ArrayElement arrayElement = new ArrayElement(number);

			lastIndexId = arrayElementService.saveAndGetIndexId(arrayElement);
			
			dbNumberValuelList.add(number);

			intervalList.add(number);

		} finally {

			writeLock.unlock();
		}

		return new OutputDto(currSum, currSum / number);
	}

	@Override
	public void deleteNumber(int index) {
		
		if (index < 0) {
			
			String message = String.format("invalid input. index %d is negative", index);
			
			throw new InvalidInputException(message);
		}

		if (lastIndexId == 0) {

			String message = String.format("index %d not found (array is empty)", index);
			
			throw new IndexNotFoundException(message);
		}

		if (index > lastIndexId-1) {
			
			String message = String.format("index %d not found (array length is %d)", index, lastIndexId);

			throw new IndexNotFoundException(message);
		}

		try {
			writeLock.lock();
			
			if (index > lastIndexId-1) {

				String message = String.format("index %d not found (array length is %d)", index, lastIndexId);
				
				throw new IndexNotFoundException(message);
			}
			
			if (index != lastIndexId-1) { // swap only if index is not the same as last element index

				int lastIndexValue = dbNumberValuelList.get(lastIndexId-1);
				int indexValue = dbNumberValuelList.get(index);
				
				arrayElementService.swapLastValueWithIndexValue(lastIndexId, index+1, lastIndexValue, indexValue); // index+1 because indexId in db starts from 1 (not 0)
				
				dbNumberValuelList.set(lastIndexId-1, indexValue);
				dbNumberValuelList.set(index, lastIndexValue);
			}

			intervalList.set(index, -1);

		} finally {

			writeLock.unlock();
		}
	}

	@Override
	public List<Integer> returnIntervalArrayValues(String indexes) {

		if (indexes.trim().isEmpty()) {
			
			throw new InvalidInputException("indexes request param is empty");
		}

		List<Integer> resultList = new ArrayList<Integer>();

		IntStream intStreamIndexes = Stream.of(indexes.split(",")).mapToInt(indexStr -> toInt(indexStr, indexes));

		try {
			readLock.lock();

			intStreamIndexes.forEach(index -> {

				int value;

				try {

					value = intervalList.get(index);

				} catch (IndexOutOfBoundsException e) {
					
					String message = String.format("index %d not found (array length is %d). indexes request param: %s", index, lastIndexId, indexes);

					throw new IndexNotFoundException(message);
				}

				resultList.add(value);
			});

		} finally {

			readLock.unlock();
		}

		return resultList;
	}
	
	private int toInt(String indexStr, String indexes) {
		
		int index;
		
		try {
			
			index = Integer.parseInt(indexStr.trim());
			
		} catch (NumberFormatException e) {
			
			String message = String.format("index %s is not an integer. indexes request param: %s", indexStr, indexes);
			
			throw new InvalidInputException(message);
		}
		
		if (index < 0) {
			
			String message = String.format("invalid input. index %d is negative. indexes request param: %s", index, indexes);
			
			throw new InvalidInputException(message);
		}
		
		return index;
	}
}
