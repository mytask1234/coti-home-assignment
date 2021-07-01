package io‏.coti.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io‏.coti.dto.NumberDto;
import io‏.coti.dto.OutputDto;
import io‏.coti.service.AppService;

@RestController
@RequestMapping("coti/api/v1")
public class AppController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AppController.class);

	@Autowired
	private AppService appService;

	@PostMapping("/insert")
	@ResponseStatus(HttpStatus.CREATED)
	public OutputDto insertNumber(@RequestBody final NumberDto numberDto) {

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("numberDto={}", numberDto);
		}

		numberDto.validate();

		OutputDto outputDto = appService.insertNumber(numberDto.getNumber());

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("outputDto={}", outputDto);
		}

		return outputDto;
	}

	@DeleteMapping(value = "/delete/{index}")
	public ResponseEntity<Integer> deleteNumber(@PathVariable("index") final int index) {

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("index={}", index);
		}
		
		appService.deleteNumber(index);

		return new ResponseEntity<>(index, HttpStatus.OK);
	}

	@GetMapping("/return")
	public ResponseEntity<List<Integer>> returnIntervalArrayValues(@RequestParam("indexes") final String indexes) {

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("indexes={}", indexes);
		}

		List<Integer> intervalArrayValues = appService.returnIntervalArrayValues(indexes);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("intervalArrayValues={}", intervalArrayValues);
		}

		return new ResponseEntity<>(intervalArrayValues, HttpStatus.OK);
	}
}
