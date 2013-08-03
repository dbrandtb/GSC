package mx.com.aon.portal.service;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.FormulaMetodoCancelacionVO;

/**
 * Interface para servicios para Formula del Metodo de Cancelacion
 *
 */
public interface FormulaMetodoCancelacionManager {

	/**
	 * Obtiene informacion de encabezado del Metodo de Cancelacion
	 * 
	 * @param cdMetodo
	 * @return
	 * @throws ApplicationException
	 */
	public FormulaMetodoCancelacionVO getEncabezado (String cdMetodo) throws ApplicationException;

}
