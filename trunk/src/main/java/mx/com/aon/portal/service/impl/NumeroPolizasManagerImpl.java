package mx.com.aon.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.NumeroPolizaVO;
import mx.com.aon.portal.service.NumeroPolizasManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;


/**
 * Implementa la interface de servicios para numeros de polizas.
 *
 */
public class NumeroPolizasManagerImpl extends AbstractManager implements NumeroPolizasManager  {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(AgrupacionGrupoPersonasManagerImpl.class);

	/**
	  * Obtiene un conjunto de numeros de polizas de la pantalla y exporta el resultado en Formato PDF, Excel, CSV, etc.
	  * Usa el Store Procedure PKG_EMISION.P_OBTIENE_TNUMPOEX.
	  * 
	  * @return success
	  * 
	  * @throws Exception
	  */
	@SuppressWarnings("unchecked")
	public TableModelExport getModel(String dsUniEco, String dsRamo,String dsElemen) throws ApplicationException {
		// Se crea el objeto de respuesta
		TableModelExport model = new TableModelExport();
		
		List lista = null;
		HashMap map = new HashMap();
		map.put("pv_dsunieco_i",dsUniEco);
		map.put("pv_dsramo_i",dsRamo);
		map.put("pv_dselemen_i",dsElemen);
				
		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "OBTIENE_NUMEROS_POLIZA_EXPORT");

		model.setInformation(lista);
		// Se agregan los nombre de las columnas del modelo de datos
		model.setColumnName(new String[]{"Aseguradora","Producto","Cliente"});
		return model;
	}

	
	/**
	 *  Obtiene un numero de poliza.
	 *  Usa el Store Procedure PKG_EMISION.P_DETALLE_TNUMEPOEX.
	 * 
	 *  @param cdUniEco
	 *  @param cdElemento
	 *  @param cdRamo
	 *  
	 *  @return NumeroPolizaVO 
	 */		
	@SuppressWarnings("unchecked")
	public NumeroPolizaVO getNumeroPoliza(String cdUniEco, String cdElemento,String cdRamo) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("pv_cdunieco_i", cdUniEco);
		map.put("pv_cdelemento_i", cdElemento);
		map.put("pv_cdramo_i", cdRamo);
		
		return (NumeroPolizaVO)getBackBoneInvoke(map, "OBTIENE_NUMERO_POLIZA_REG");
	}

	
	/**
	 *  Salva la informacion de numeros de polizas.
	 *  Usa el Store Procedure PKG_EMISION.P_OBTIENE_TNUMPOEX.
	 * 
	 *  @param numeroPolizaVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio. 
	 */	
	@SuppressWarnings("unchecked")
	public String guardarNumerosPoliza(NumeroPolizaVO numeroPolizaVO)throws ApplicationException {
		HashMap map = new HashMap();
		map.put("pv_cdunieco_i", numeroPolizaVO.getCdUniEco());
		map.put("pv_cdelemento_i", numeroPolizaVO.getCdElemento());
		map.put("pv_cdperson_i", numeroPolizaVO.getCdPerson());
		map.put("pv_cdramo_i", numeroPolizaVO.getCdRamo());
		map.put("pv_INDSUFPRE_i", numeroPolizaVO.getIndSufPre());
        map.put("pv_DSSUFPRE_i", numeroPolizaVO.getDsSufPre());
		map.put("pv_INDCALC_i", numeroPolizaVO.getIndCalc());
		map.put("pv_DSCALCULO_i", numeroPolizaVO.getDsCalculo());
		map.put("pv_NMFOLIOINI_i", numeroPolizaVO.getNmFolioIni());
		map.put("pv_NMFOLIOFIN_i", numeroPolizaVO.getNmFolioFin());
		map.put("pv_NMFOLIOACT_i", numeroPolizaVO.getNmFolioAct());
		map.put("pv_cdtippol_i", numeroPolizaVO.getCdTipPol());
		map.put("pv_swagrupa_i", numeroPolizaVO.getSwAgrupa());
		map.put("pv_cdexpres_i", numeroPolizaVO.getCdExpres());
		map.put("pv_cdproceso_i", numeroPolizaVO.getCdProceso());
		map.put("pv_cdnumpol_i", numeroPolizaVO.getCdNumPol());
		
        WrapperResultados res =  returnBackBoneInvoke(map,"GUARDA_NUMERO_POLIZA");
        return res.getMsgText();
	}

	
	/**
	 *  Inserta un nuevo de numeros de polizas.
	 *  Usa el Store Procedure PKG_EMISION.P_INSERTA_TNUMPOEX.
	 * 
	 *  @param numeroPolizaVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio. 
	 */	
	@SuppressWarnings("unchecked")
	public String insertarNumerosPoliza(NumeroPolizaVO numeroPolizaVO)throws ApplicationException {
		HashMap map = new HashMap();
		map.put("pv_cdunieco_i", numeroPolizaVO.getCdUniEco());
		map.put("pv_cdelemento_i", numeroPolizaVO.getCdElemento());
		map.put("pv_cdperson_i", numeroPolizaVO.getCdPerson());
		map.put("pv_cdramo_i", numeroPolizaVO.getCdRamo());
		map.put("pv_INDSUFPRE_i", numeroPolizaVO.getIndSufPre());
        map.put("pv_DSSUFPRE_i", numeroPolizaVO.getDsSufPre());
		map.put("pv_INDCALC_i", numeroPolizaVO.getIndCalc());
		map.put("pv_DSCALCULO_i", numeroPolizaVO.getDsCalculo());
		map.put("pv_NMFOLIOINI_i", numeroPolizaVO.getNmFolioIni());
		map.put("pv_NMFOLIOFIN_i", numeroPolizaVO.getNmFolioFin());
		map.put("pv_NMFOLIOACT_i", numeroPolizaVO.getNmFolioAct());
		map.put("pv_cdtippol_i", numeroPolizaVO.getCdTipPol());
		map.put("pv_swagrupa_i", numeroPolizaVO.getSwAgrupa());
		map.put("pv_cdexpres_i", numeroPolizaVO.getCdExpres());
		map.put("pv_cdproceso_i", numeroPolizaVO.getCdProceso());
		map.put("pv_cdnumpol_i", numeroPolizaVO.getCdNumPol());
		
        WrapperResultados res =  returnBackBoneInvoke(map,"INSERTA_NUMERO_POLIZA");
        return res.getMsgText();
	}

	
	/**
	 *  Relaiza la baja de numeros de polizas.
	 *  Usa el Store Procedure PKG_EMISION.P_BORRA_TNUMPOEX.
	 * 
	 *  @param cdUniEco
	 *  @param cdElemento
	 *  @param cdRamo
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio. 
	 */	
	@SuppressWarnings("unchecked")
	public String borrarNumeroPoliza(String cdUniEco, String cdElemento,String cdRamo) throws ApplicationException {
		HashMap map = new HashMap ();
		map.put("pv_cdunieco_i", cdUniEco);
		map.put("pv_cdelemento_i", cdElemento);
		map.put("pv_cdramo_i", cdRamo);
		
        WrapperResultados res =  returnBackBoneInvoke(map,"BORRAR_NUMERO_POLIZA");
        return res.getMsgText();
	}

	@SuppressWarnings("unchecked")
	public String borrarNumPolEmision(String cdNumPol) throws ApplicationException {
		HashMap map = new HashMap ();
		map.put("pv_cdnumpol_i", cdNumPol);
		
        WrapperResultados res =  returnBackBoneInvoke(map,"BORRAR_NUMERO_POLIZA_EMISION");
        return res.getMsgText();
	}
	
	/**
	 *  Obtiene un conjunto de numeros de polizas.
	 *  Usa el Store Procedure PKG_EMISION.P_OBTIENE_TNUMPOEX. 
	 * 
	 *  @param dsUniEco
	 *  @param dsRamo
	 *  @param dsElemen
	 *  @param start
	 *  @param limit
	 *  
	 *  @return Objeto PagedList 
	 */		
	@SuppressWarnings("unchecked")
	public PagedList buscarNumerosPoliza(String dsUniEco, String dsRamo,String dsElemen, int start, int limit)throws ApplicationException {
		// Se crea un mapa para pasar los parametros de ejecucion al endpoint
		HashMap map = new HashMap();
        map.put("pv_dsunieco_i",dsUniEco);
		map.put("pv_dsramo_i",dsRamo);
		map.put("pv_dselemen_i",dsElemen);
		                            
		return pagedBackBoneInvoke(map, "OBTIENE_NUMEROS_POLIZA", start, limit);
	}

}
