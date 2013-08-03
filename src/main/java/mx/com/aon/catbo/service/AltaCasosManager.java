package mx.com.aon.catbo.service;

import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.catbo.model.CasoDetalleVO;
import mx.com.aon.catbo.model.CasoVO;
import mx.com.aon.catbo.model.FormatoOrdenVO;
import mx.com.aon.catbo.model.PersonaVO;
import mx.com.aon.catbo.model.ReasignacionCasoVO;
import mx.com.aon.catbo.model.ResultadoGeneraCasoVO;

/**
 * User: gabrielforradellas
 * Date: Sep 2, 2008
 * Time: 10:42:12 PM
 */
public interface AltaCasosManager {
	
	public PersonaVO obtenerPersonCaso(String pv_cdperson_i) throws ApplicationException;

}

