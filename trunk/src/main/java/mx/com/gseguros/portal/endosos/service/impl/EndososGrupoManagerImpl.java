package mx.com.gseguros.portal.endosos.service.impl;

import mx.com.gseguros.portal.endosos.dao.EndososGrupoDAO;
import mx.com.gseguros.portal.endosos.service.EndososGrupoManager;
import mx.com.gseguros.portal.general.dao.PantallasDAO;

public class EndososGrupoManagerImpl implements EndososGrupoManager
{
	private EndososGrupoDAO endososGrupoDAO;
	private PantallasDAO    pantallasDAO;
	
	@Override
	public void test()
	{
		endososGrupoDAO.test();
	}
	
	//getters y setters
	public void setEndososGrupoDAO(EndososGrupoDAO endososGrupoDAO) {
		this.endososGrupoDAO = endososGrupoDAO;
	}
	public void setPantallasDAO(PantallasDAO pantallasDAO) {
		this.pantallasDAO = pantallasDAO;
	}
}