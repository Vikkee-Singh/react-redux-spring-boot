package com.vapl.dialer.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vapl.dialer.pojo.Tasks;

@Service
public class TaskImpService implements TaskService {

	@Override
	public Tasks getJson(String task) {
		Tasks taskJson = new Tasks();
		
		try {
			ObjectMapper objMapper = new ObjectMapper();
			taskJson = objMapper.readValue(task, Tasks.class);
		} catch (IOException err) {
			// TODO: handle exception
			System.out.println("ERROR @@@ "+ err.toString());
		}
		return taskJson;
	}
}
