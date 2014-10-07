package mx.com.gseguros.wizard.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.gseguros.wizard.dao.TablasApoyoDAO;
import mx.com.gseguros.wizard.service.TablasApoyoManager;

@Service
public class TablasApoyoManagerImpl implements TablasApoyoManager {
	
	@Autowired
	private TablasApoyoDAO tablasApoyoDAO;

}