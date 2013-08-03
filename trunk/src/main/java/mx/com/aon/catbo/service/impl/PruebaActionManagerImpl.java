package mx.com.aon.catbo.service.impl;

import org.springframework.context.ApplicationContextException;

import mx.com.aon.catbo.service.PruebaActionManager;
import mx.com.aon.portal.service.impl.AbstractManager;

public class PruebaActionManagerImpl extends AbstractManager implements PruebaActionManager {

	public String metodoPrueba() throws ApplicationContextException {
		return "Cadenita de prueba";
	}

}
