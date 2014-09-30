package mx.com.gseguros.portal.endosos.dao.impl;

import mx.com.gseguros.portal.dao.AbstractManagerDAO;
import mx.com.gseguros.portal.endosos.dao.EndososGrupoDAO;

import org.apache.log4j.Logger;

public class EndososGrupoDAOImpl extends AbstractManagerDAO implements EndososGrupoDAO
{
	private static final Logger logger = Logger.getLogger(EndososGrupoDAOImpl.class);
	
	@Override
	public void test()
	{
		logger.debug("test");
	}
}