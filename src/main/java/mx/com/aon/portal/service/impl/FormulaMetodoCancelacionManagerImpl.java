package mx.com.aon.portal.service.impl;

import java.util.HashMap;

import org.apache.log4j.Logger;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.FormulaMetodoCancelacionVO;
import mx.com.aon.portal.service.FormulaMetodoCancelacionManager;

/**
 * Implementa la interfaz de servicios para Formula del Metodo de Cancelacion
 *
 */
public class FormulaMetodoCancelacionManagerImpl extends AbstractManager implements FormulaMetodoCancelacionManager {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(FormulaMetodoCancelacionManagerImpl.class);
	/**
	 * Obtiene la informacion de encabezado de la Formula del Metodo de Cancelacion.
	 * Utiliza sp PKG_CANCELA.P_OBTIENE_METODO
	 * 
	 * @param cdMetodo
	 */
	@SuppressWarnings("unchecked")
	public FormulaMetodoCancelacionVO getEncabezado(String cdMetodo) throws ApplicationException {

		HashMap map = new HashMap ();
		map.put("cdMetodo", cdMetodo);
		
		return (FormulaMetodoCancelacionVO)getBackBoneInvoke(map, "FORMULA_METODO_CANCELACION_ENCABEZADO");
	}

}
