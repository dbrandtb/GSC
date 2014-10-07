package mx.com.gseguros.wizard.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.gseguros.wizard.dao.TablasApoyoDAO;
import mx.com.gseguros.wizard.service.TablasApoyoManager;

@Service
public class TablasApoyoManagerImpl implements TablasApoyoManager {
	
	@Autowired
	private TablasApoyoDAO tablasApoyoDAO;

	@Override
	public List<Map<String, String>> obtieneValoresTablaApoyo5claves(Map<String,String> params) throws Exception
	{
		return tablasApoyoDAO.obtieneValoresTablaApoyo5claves(params);
	}
}