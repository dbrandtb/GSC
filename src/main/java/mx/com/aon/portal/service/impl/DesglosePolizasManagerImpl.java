
package mx.com.aon.portal.service.impl;

import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.DesglosePolizasVO;
import mx.com.aon.portal.service.DesglosePolizasManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.exception.ApplicationException;

import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Clase que sirve para dar servicios al action que lo invoca.
 * 
 * @implements DesglosePolizasManager
 * 
 * @extends AbstractManager
 */
public class DesglosePolizasManagerImpl extends AbstractManager implements DesglosePolizasManager {
	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(EstructuraManagerImpl.class);
	

	/**
	 * Metodo que obtiene un unico registro de un desglose de poliza.
	 * Invoca al Store Procedure PKG_COTIZA.P_OBTIENE_DESGLOSE_CONCEPTO.
	 * 
	 * @param cdPerson
	 * @param cdTipCon
	 * @param cdRamo
	 * 
	 * @return DesglosePolizasVO
	 * 
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public DesglosePolizasVO getDesglosePolizas(String cdPerson,String cdTipCon, String cdRamo) throws ApplicationException
	{
			HashMap map = new HashMap();
			map.put("cdPerson",cdPerson);
			map.put("cdTipCon",cdTipCon);
			map.put("cdRamo",cdRamo);
			return (DesglosePolizasVO)getBackBoneInvoke(map,"OBTIENE_DESGLOSE_POLIZAS_REG");
	}

	/**
	 * Metodo que realiza la insercion de un nuevo registro de desglose de poliza.
	 * Invoca al Store Procedure PKG_COTIZA.P_SALVA_CONCEPTO_DESGLOSE.
	 * 
	 * @param desglosePolizasVO
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio
	 * 
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked") // Manejo de ArrayList Controlado
	public String agregarDesglosePolizas(DesglosePolizasVO desglosePolizasVO) throws ApplicationException{
        	// Se crea un mapa para pasar los parametros de ejecucion al endpoint
			HashMap map = new HashMap();
			
			map.put("cliente",desglosePolizasVO.getCdPerson());
			map.put("elemento",desglosePolizasVO.getCdElemento());
			map.put("concepto",desglosePolizasVO.getCdTipCon());
			map.put("producto",desglosePolizasVO.getCdRamo());
            WrapperResultados res =  returnBackBoneInvoke(map,"INSERTA_DESGLOSE_POLIZAS");
            return res.getMsgText();

	}
	
	/**
	 * Metodo que realiza la copia de un registro de desglose de poliza seleccionado.
	 * Invoca al Store Procedure PKG_COTIZA.P_COPIA_DESGLOSE.
	 * 
	 * @param cdPerson
	 * @param cdRamo
	 * @param dsPerson
	 * @param dsRamo
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio
	 * 
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public String copiarDesglosePolizas(String cdPerson, String cdRamo, String dsPerson, String dsRamo)throws ApplicationException{
        HashMap map = new HashMap();

        map.put("cdPerson",cdPerson);
        map.put("cdRamo",cdRamo);
        map.put("dsPerson",dsPerson);
        map.put("dsRamo",dsRamo);
        WrapperResultados res =  returnBackBoneInvoke(map,"COPIA_DESGLOSE_POLIZAS");
        return res.getMsgText();
	}

	// Se elimino el metodo guardarConfiguracionAlertasAutomatico(...)
	
	/**
     * Metodo que realiza la eliminacion de un registro de desglose de poliza seleccionado.
     * Invoca al Store Procedure PKG_COTIZA.P_BORRA_CONCEPTO_DESGLOSE.
     * 
     * @param cdPerson
     * @param cdTipCon
     * @param cdRamo
     * 
     * @return Mensaje asociado en respuesta a la ejecucion del servicio
     * 
     * @throws ApplicationException
     */
    @SuppressWarnings("unchecked")
	public String borrarDesglosePolizas(String cdPerson,String cdTipCon, String cdRamo) throws ApplicationException{
    		// Se crea un mapa para pasar los parametros de ejecucion al endpoint
			HashMap map = new HashMap();
			map.put("cdPerson",cdPerson);
			map.put("cdTipCon",cdTipCon);
			map.put("cdRamo",cdRamo);
            WrapperResultados res =  returnBackBoneInvoke(map,"BORRA_DESGLOSE_POLIZAS");
            return res.getMsgText();
    }
    
    /**
	 * Metodo que busca y obtiene un conjunto de registros para realizar
	 *  la exportacion del resultado a un formato PDF, XSL, TXT, etc.
	 *  Invoca al Store Procedure PKG_COTIZA.P_OBTIENE_DESGLOSES.
	 *  
	 * @param dsCliente
	 * @param dsConcepto
	 * @param dsProducto
	 * 
	 * @return TableModelExport
	 * 
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public PagedList buscarDesglosePolizas(String dsCliente, String dsConcepto, String dsProducto, String cdEstruct, int start, int limit)throws ApplicationException
	{
			HashMap map = new HashMap();
	
			map.put("dsCliente",dsCliente);
			map.put("dsConcepto",dsConcepto);
			map.put("dsProducto",dsProducto);
			map.put("cdEstruct",cdEstruct);
			
			return pagedBackBoneInvoke(map, "OBTIENE_DESGLOSE_POLIZAS", start, limit);

	}
	
	
	/**
	 * Metodo que busca y obtiene un conjunto de registros para realizar
	 *  la exportacion del resultado a un formato PDF, XSL, TXT, etc.
	 *  
	 * @param dsCliente
	 * @param dsConcepto
	 * @param dsProducto
	 * 
	 * @return TableModelExport
	 * 
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public TableModelExport getModel(String dsCliente, String dsConcepto, String dsProducto, String cdEstruct) throws ApplicationException {
		TableModelExport model = new TableModelExport();
		
		List lista = null;
		HashMap map = new HashMap();
		map.put("dsCliente",dsCliente);
		map.put("dsConcepto",dsConcepto);
		map.put("dsProducto",dsProducto);
		map.put("cdEstruct",cdEstruct);
		
		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "OBTIENE_DESGLOSE_POLIZAS_EXPORT");

		model.setInformation(lista);
		model.setColumnName(new String[]{"Cliente","Concepto","Producto"});
		return model;
	}


	
}
