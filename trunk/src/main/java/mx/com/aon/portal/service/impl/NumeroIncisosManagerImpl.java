package mx.com.aon.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.NumeroIncisoVO;
import mx.com.aon.portal.service.NumeroIncisosManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;


/**
 * Implementa la interface de servicios para numeros de incisos.
 *
 */
public class NumeroIncisosManagerImpl extends AbstractManager implements
NumeroIncisosManager {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(AgrupacionGrupoPersonasManagerImpl.class);


	/**
	  * Obtiene un conjunto de numeros de incisos y exporta el resultado en Formato PDF, Excel, CSV, etc.
	  * Usa el store procedure PKG_EMISION.P_OBTIENE_TNUMINEX.
	  * 
	  * param dsUniEco
	  * param cdRamo
	  * param dsElemen
	  * param dsIndSitSubSit
	  * 
	  * @return TableModelExport
	  */
	@SuppressWarnings("unchecked")
	public TableModelExport getModel(String dsUniEco, String dsRamo,String dsElemen, String dsIndSitSubSit) throws ApplicationException {
		// Se crea el objeto de respuesta
		TableModelExport model = new TableModelExport();
		
		List lista = null;
		HashMap map = new HashMap();
		map.put("cdMetodo",dsUniEco);
		map.put("dsMetodo",dsRamo);
		map.put("cdMetodo",dsElemen);
		map.put("dsMetodo",dsIndSitSubSit);
				
		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "OBTIENE_NUMEROS_INCISO_EXPORT");

		model.setInformation(lista);
		// Se agregan los nombre de las columnas del modelo de datos
		model.setColumnName(new String[]{"Aseguradora","Producto","Cliente","Numeracion Inciso/Subinciso"});
		return model;
	}


	/**
	 *  Obtiene la informacion de numeros de incisos.
	 *  Usa el store procedure PKG_EMISION.P_DETALLE_TNUMINEX.
	 *  @param cdUniEco
	 *  @param cdElemento
	 *  @param cdRamo
	 *  @param indSituac
	 *  
	 *  @return NumeroIncisoVO
	 */		
	@SuppressWarnings("unchecked")
	public NumeroIncisoVO getNumeroInciso(String cdUniEco, String cdElemento,String cdRamo, String indSituac) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdUniEco", cdUniEco);
		map.put("cdElemento", cdElemento);
		map.put("cdRamo", cdRamo);
		map.put("indSituac", indSituac);
		return (NumeroIncisoVO)getBackBoneInvoke(map, "OBTIENE_NUMERO_INCISO_REG");
	}


	/**
	 *  Salva la informacion de numero de inciso.
	 *  Usa el store preocedure PKG_EMISION.P_GUARDA_TNUMINEX.
	 * 
	 *  @param numeroIncisoVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */			
	@SuppressWarnings("unchecked")
	public String guardarNumerosInciso(NumeroIncisoVO numeroIncisoVO)throws ApplicationException {
		HashMap map = new HashMap();
		map.put("pv_cdunieco_i", numeroIncisoVO.getCdUniEco());
		map.put("pv_cdelemento_i", numeroIncisoVO.getCdElemento());
		map.put("pv_cdperson_i", numeroIncisoVO.getCdPerson());
		map.put("pv_cdramo_i", numeroIncisoVO.getCdRamo());
		map.put("pv_INDSITUAC_i", numeroIncisoVO.getIndSituac());
		map.put("pv_INDSUFPRE_i", numeroIncisoVO.getIndSufPre());
        map.put("pv_DSSUFPRE_i", numeroIncisoVO.getDsSufPre());
		map.put("pv_INDCALC_i", numeroIncisoVO.getIndCalc());
		map.put("pv_DSCALCULO_i", numeroIncisoVO.getDsCalculo());
		map.put("pv_NMFOLIOINI_i", numeroIncisoVO.getNmFolioIni());
		map.put("pv_NMFOLIOFIN_i", numeroIncisoVO.getNmFolioFin());
		
        
        
        WrapperResultados res =  returnBackBoneInvoke(map,"GUARDA_NUMERO_INCISO");
        return res.getMsgText();
	}


	/**
	 *  Agrega un nuevo numero de inciso.
	 *  Usa el store procedure PKG_EMISION.P_INSERTA_TNUMINEX.
	 * 
	 *  @param numeroIncisoVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */			
	@SuppressWarnings("unchecked")
	public String insertarNumerosInciso(NumeroIncisoVO numeroIncisoVO)throws ApplicationException {
		HashMap map = new HashMap();
		map.put("pv_cdunieco_i", numeroIncisoVO.getCdUniEco());
		map.put("pv_cdelemento_i", numeroIncisoVO.getCdElemento());
		map.put("pv_cdperson_i", numeroIncisoVO.getCdPerson());
		map.put("pv_cdramo_i", numeroIncisoVO.getCdRamo());
		map.put("pv_INDSITUAC_i", numeroIncisoVO.getIndSituac());
		map.put("pv_INDSUFPRE_i", numeroIncisoVO.getIndSufPre());
        map.put("pv_DSSUFPRE_i", numeroIncisoVO.getDsSufPre());
		map.put("pv_INDCALC_i", numeroIncisoVO.getIndCalc());
		map.put("pv_DSCALCULO_i", numeroIncisoVO.getDsCalculo());
		map.put("pv_NMFOLIOINI_i", numeroIncisoVO.getNmFolioIni());
		map.put("pv_NMFOLIOFIN_i", numeroIncisoVO.getNmFolioFin());
		
        WrapperResultados res =  returnBackBoneInvoke(map,"INSERTA_NUMERO_INCISO");
        return res.getMsgText();
	}


	/**
	 *  Relaiza la baja de numero de inciso.
	 *  Usa el store procedure PKG_EMISION.P_BORRA_TNUMINEX.
	 * 
	 *  @param cdUniEco
	 *  @param cdElemento
	 *  @param cdRamo
	 *  @param indSituac
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */			
	@SuppressWarnings("unchecked")
	public String borrarNumeroInciso(String cdUniEco, String cdElemento,String cdRamo, String indSituac) throws ApplicationException {
		HashMap map = new HashMap ();
		map.put("pv_cdunieco_i", cdUniEco);
		map.put("pv_cdelemento_i", cdElemento);
		map.put("pv_cdramo_i", cdRamo);
		map.put("pv_INDSITUAC_i", indSituac);
		
        WrapperResultados res =  returnBackBoneInvoke(map,"BORRAR_NUMERO_INCISO");
        return res.getMsgText();
	}

	
	/**
	 *  Obtiene un conjunto de numeros de incisos.
	 *  Usa el store procedure PKG_EMISION.P_OBTIENE_TNUMINEX. 
	 * 
	 *  @param dsUniEco
	 *  @param dsRamo
	 *  @param dsElemen
	 *  @param dsIndSitSubSit
	 *  @param start
	 *  @param limit
	 *  
	 *  @return Objeto PagedList 
	 */		
	@SuppressWarnings("unchecked")
	public PagedList buscarNumerosInciso(String dsUniEco, String dsRamo,String dsElemen, String dsIndSitSubSit, int start, int limit)throws ApplicationException {
		// Se crea un mapa para pasar los parametros de ejecucion al endpoint
		HashMap map = new HashMap();
        map.put("pv_dsunieco_i",dsUniEco);
		map.put("pv_dsramo_i",dsRamo);
		map.put("pv_dselemento_i",dsElemen);
		map.put("pv_dsindsitsubsit_i",dsIndSitSubSit);
		
		return pagedBackBoneInvoke(map, "OBTIENE_NUMEROS_INCISO", start, limit);
	}


	/**
	 *  Obtiene informacion sobre tramos.
	 *  Usa el store procedure Pkg_Wizard.P_LOADTRAMOS.
	 *   
	 *  @param cdRamo
	 *  
	 *  @return NumeroIncisoVO
	 */		
	@SuppressWarnings("unchecked")
	public NumeroIncisoVO getTramos(String cdRamo) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("pv_cdramo_i", cdRamo);
	    return (NumeroIncisoVO)getBackBoneInvoke(map, "OBTIENE_TRAMOS");
	}

}
