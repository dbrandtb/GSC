package mx.com.aon.portal.service;

import mx.com.aon.portal.model.DatosMovPolizasVO;
import mx.com.aon.portal.model.DatosVarRolVO;
import mx.com.aon.portal.model.DatosVblesObjAsegurablesVO;
import mx.com.aon.portal.model.EncabezadoPolizaVO;
import mx.com.aon.portal.model.MovTValoPolVO;
import mx.com.aon.portal.model.NuevaCoberturaVO;
import mx.com.gseguros.exception.ApplicationException;

/**Interface de servicios para Consulta de Poliza Renovada
 * 
 * @author CIMA
 *
 */
public interface ConsultaPolizaRenovadaManager {

	/**
	 * Metodo que obtiene los datos generales definidos 
	 * a nivel póliza para la pantalla Datos Generales de Consulta de Póliza Renovada.
	 * 
	 * @param cdUniEco: Código de la Aseguradora
	 * @param cdRamo: Código del Producto Asegurado.
	 * @param estado: Estado de la póliza.
	 * @param nmPoliza: Número de la Póliza.
	 * 
	 * @return EncabezadoPolizaVO 
	 */
	public EncabezadoPolizaVO getPolizaEncabezado(String cdUniEco,String cdRamo,String estado,String nmPoliza)throws ApplicationException;
	
	/**
	 * Metodo que obtiene los atributos variables definidos 
	 * a nivel póliza para la pantalla Consulta de Póliza Renovada.
	 * 
	 * @param cdUniEco: Código de la Aseguradora
	 * @param cdRamo: Código del Producto Asegurado.
	 * @param estado: Estado de la póliza.
	 * @param nmPoliza: Número de la Póliza.
	 * 
	 * @return PagedList 
	 */
	public PagedList obtenerValoresPoliza(String cdUniEco,String cdRamo,String estado,String nmPoliza, String cdAtribu, int start, int limit)throws ApplicationException;	
	
	/**
	 * Metodo que guada los atributos variables definidos 
	 * a nivel póliza para la pantalla Consulta de Póliza Renovada.
	 * 
	 * @param datosMovPolizasVO
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio. 
	 */
	public String guardarDatosGenerales(DatosMovPolizasVO datosMovPolizasVO)throws ApplicationException;	
	
	/**
	 * Metodo que guarda los atributos variables definidos 
	 * a nivel póliza para la pantalla Consulta de Póliza Renovada.
	 * 
	 * @param movTValoPolVO
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio. 
	 */
	public String guardarValoresPoliza(MovTValoPolVO movTValoPolVO)throws ApplicationException;
	
	/**
	 * Metodo que obtiene los objetos asegurables, personas, para 
	 * la pantalla Consulta de Póliza Renovada.
	 * 
	 * @param cdUniEco
	 * @param cdRamo
	 * @param estado
	 * @param nmPoliza
	 * @param start
	 * @param limit
	 * 
	 * @return PagedList 
	 */
	public PagedList obtenerObjetosAsegurables(String cdUniEco, String cdRamo,String estado, String nmPoliza, int start, int limit)throws ApplicationException;
	
	/**
	 * Metodo que obtiene los objetos, funciones, que realizan en la poliza para 
	 * la pantalla Consulta de Póliza Renovada.
	 * 
	 * @param cdUniEco
	 * @param cdRamo
	 * @param estado
	 * @param nmPoliza
	 * @param nmSituac
	 * @param cdPerson
	 * @param start
	 * @param limit
	 * 
	 * @return PagedList 
	 */
	public PagedList obtenerFunciones(String cdUniEco, String cdRamo,String estado, String nmPoliza, String nmSituac, String cdPerson, int start, int limit)throws ApplicationException;
	
	/**
	 * Metodo que obtiene datos variables en funcion de la poliza para 
	 * la pantalla Datos Variables de la Funcion en la Poliza de Consulta de Póliza Renovada.
	 * 
	 * @param cdUniEco
	 * @param cdRamo
	 * @param estado
	 * @param nmPoliza
	 * @param nmSituac
	 * @param cdRol
	 * @param cdPerson
	 * @param cdAtribu
	 * @param start
	 * @param limit
	 * 
	 * @return PagedList 
	 */
	public PagedList obtenerDatosVblesDeFuncioEnPoliza(String cdUniEco, String cdRamo,String estado, String nmPoliza, String nmSituac, String cdRol,String cdPerson,String cdAtribu, int start, int limit)throws ApplicationException;
	
	/**
	 * Metodo que realiza la eliminacion de un inciso seleccionado en el grid del bloque objeto asegurable de  
	 * la pantalla Consulta de Póliza Renovada.
	 * 
	 * @param cdUniEco
	 * @param cdRamo
	 * @param estado
	 * @param nmPoliza
	 * @param nmSituac
	 * @param nmSuplem
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */
	public String eliminarIncisoObjetoAsegurable(String cdUniEco,String cdRamo,String estado,String nmPoliza, String nmSituac, String nmSuplem)throws ApplicationException;
	
	/**
	 * Metodo que realiza la eliminacion de una funcion seleccionado en el grid del bloque funcion en la poliza de  
	 * la pantalla Consulta de Póliza Renovada.
	 * 
	 * @param cdUniEco
	 * @param cdRamo
	 * @param estado
	 * @param nmPoliza
	 * @param nmSituac
	 * @param nmSuplem
	 * @param cdRol
	 * @param cdPerson
	 * @param cdAtribu
	 * @param status
	 * @param nmOrdDom
	 * @param swReclam
	 * @param accion
	 * 
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */
	public String eliminarFuncionObjetoAsegurable(String cdUniEco,String cdRamo,String estado,String nmPoliza, String nmSituac, 
			String nmSuplem,String cdRol,String cdPerson,String cdAtribu, String status,String nmOrdDom, String swReclam, String accion)throws ApplicationException;
	
	/**
	 * Metodo que guarda los datos variables de la funcion en
	 * la póliza para la pantalla Consulta de Póliza Renovada.
	 * 
	 * @param DatosVarRolVO
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio. 
	 */
	public String guardarDatosVblesDeFuncionEnPoliza(DatosVarRolVO datosVarRolVO)throws ApplicationException;
	
	/**
	 * Metodo que obtiene los datos variables del objetos para 
	 * la pantalla Consulta de Póliza Renovada - Datos Variables del objeto Asegurable.
	 * 
	 * @param cdUniEco
	 * @param cdRamo
	 * @param estado
	 * @param nmPoliza
	 * @param nmSituac
	 * @param start
	 * @param limit
	 * 
	 * @return PagedList 
	 */
	public PagedList obtenerDatosDelObjeto(String cdUniEco, String cdRamo,String estado, String nmPoliza, String nmSituac, int start, int limit)throws ApplicationException;
	
	/**
	 * Metodo que guarda los incisos de los datos variables de la 
	 * pantalla Datos Objetos Asegurables.
	 * 
	 * @param datosVblesObjAsegurablesVO
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio. 
	 */
	public String guardarDatosVblesObjetoAsegurable(DatosVblesObjAsegurablesVO datosVblesObjAsegurablesVO)throws ApplicationException;
	
	/**Metodo que obtiene las coberturas de la poliza anterior de  
	 * la pantalla Consulta de Póliza Renovada - Coberturas de Polizas a Renovar.
	 * 
	 * @param cdUniEco
	 * @param cdRamo
	 * @param nmpPoliza
	 * @param nmSituac
	 * 
	 * @return objeto PagedList
	 * 
	 */
	public PagedList obtieneCoberturaPolizaAnterior(String cdUniEco, String cdRamo, String nmPoliza, String nmSituac, int start, int limit)throws ApplicationException;
	
	/**Metodo que obtiene las coberturas de la poliza renovada de  
	 * la pantalla Consulta de Póliza Renovada - Coberturas de Polizas a Renovar.
	 * 
	 * @param cdUniEco
	 * @param cdRamo
	 * @param nmpPoliza
	 * @param nmSituac
	 * 
	 * @return objeto PagedList
	 * 
	 */
	public PagedList obtieneCoberturaPolizaRenovada(String cdUniEco, String cdRamo, String nmPoliza, String nmSituac, int start, int limit)throws ApplicationException;
	
	/**
	 * Metodo que realiza la eliminacion de una cobertura seleccionado en el grid del inciso en cuestion de  
	 * la pantalla Consulta de Póliza Renovada - Coberturas de Polizas a Renovar.
	 * 
	 * @param cdUniEco
	 * @param cdRamo
	 * @param nmPoliza
	 * @param nmSituac
	 * @param cdGarant
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */
	public String eliminarIncisoCobertura(String cdUniEco,String cdRamo,String nmPoliza, String nmSituac,String cdGarant)throws ApplicationException;
	
	/**
	 * Metodo que guarda las coberturas agregadas de la pantalla Agregar Cobertura de 
	 * Consulta de Poliza Renovada
	 * 
	 * @param nuevaCoberturaVO
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio. 
	 */
	public String guardarDatosCobertura(NuevaCoberturaVO nuevaCoberturaVO)throws ApplicationException;
	
	/**Metodo que obtiene los datos variables de la cobertura para  
	 * la pantalla Consulta de Póliza Renovada - Datos Cobertura.
	 * 
	 * @param cdUniEco
	 * @param cdRamo
	 * @param estado
	 * @param nmpPoliza
	 * @param nmSituac
	 * @param cdGarant
	 * @param cdAtribu
	 * @param status
	 * 
	 * @return objeto PagedList
	 * 
	 */
	public PagedList obtieneDatosVblesCobertura(String cdUniEco, String cdRamo, String estado, String nmPoliza, String nmSituac,String cdGarant,String cdAtribu, String status, int start, int limit)throws ApplicationException;
	
	/**
	 * Metodo que guarda los datos de la cobertura de la 
	 * pantalla Datos Coberturas de Consulta de Poliza Renovada.
	 * 
	 * @param datosVblesObjAsegurablesVO
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio. 
	 */
	public String guardarDatosDeCobertura(DatosVblesObjAsegurablesVO datosVblesObjAsegurablesVO)throws ApplicationException;
	
	/**
	 * Metodo que obtiene la lista de equipo especial para la pantalla Accesorios de Consulta de 
	 * Poliza Renovada.
	 * 
	 * @param cdUniEco
	 * @param cdRamo
	 * @param estado
	 * @param nmPoliza
	 * @param nmSituac
	 * @param nmObjeto
	 *  
	 * @return PagedList 
    */
	public PagedList obtieneEquipoEspecial(String cdUniEco, String cdRamo, String estado, String nmPoliza, String nmSituac,
			String nmObjeto,int start, int limit)throws ApplicationException;
	
	/**
	 * Metodo que permite eliminar un accesorio seleccionado.
	 * 
	 *@param cdUniEco
     *@param cdRamo
     *@param estado
     *@param nmPoliza
     *@param nmSituac
     *@param cdTipObj
     *@param nmSuplem
     *@param status
     *@param nmObjeto
     *@param dsObjeto
     *@param ptObjeto
     *@param cdAgrupa
     *@param accion
     *
     *@return Mensaje asociado en respuesta a la ejecucion del servicio.
	 * 
	 * @throws ApplicationException
	 */
	public String eliminarAccesorioEquipoEspecial(String cdUniEco,String cdRamo,String estado,String nmPoliza,String nmSituac,String cdTipObj,
			String nmSuplem, String status,String nmObjeto,String dsObjeto,String ptObjeto,String cdAgrupa,String accion)throws ApplicationException;
	
	/**
	 * Metodo que obtiene un detalle segun la seleccion de un accesorio.
	 * 
	 *@param cdUniEco
     *@param cdRamo
     *@param estado
     *@param nmPoliza
     *@param nmSituac
     *@param nmObjeto
     *@param cdatribu
     *
     *@return PagedList.
	 * 
	 * @throws ApplicationException
	 */
	public PagedList obtieneDetalleEquipoEspecial(String cdUniEco, String cdRamo,String estado, String nmPoliza, String nmSituac,
			String nmObjeto, String cdatribu, int start, int limit)throws ApplicationException;
		
	/**
	 * Metodo que permite actualizar o insertar un nuevo accesorio.
	 * 
	 *@param cdUniEco
     *@param cdRamo
     *@param estado
     *@param nmPoliza
     *@param nmSituac
     *@param cdTipObj
     *@param nmSuplem
     *@param status
     *@param nmObjeto
     *@param dsObjeto
     *@param ptObjeto
     *@param cdAgrupa
     *@param accion
     *
     *@return Mensaje asociado en respuesta a la ejecucion del servicio.
	 * 
	 * @throws ApplicationException
	 */
	public String incluirAccesorio(String cdUniEco,String cdRamo,String estado,String nmPoliza,String nmSituac,String cdTipObj,
			String nmSuplem, String status,String nmObjeto,String dsObjeto,String ptObjeto,String cdAgrupa,String accion)throws ApplicationException;
	
	/**Metodo que obtiene un conjunto de recibos para la pantalla Recibos de Consulta
	 * de Poliza Renovada.
	 * 
	 * @param cdUniEco
	 * @param cdRamo
	 * @param nmPoliza
	 * @param 
	 * 
	 * @return PagedList
	 * 
	 * @throws ApplicationException
	 */
	public PagedList obtieneRecibos(String cdUniEco, String cdRamo, String nmPoliza, int start, int limit)throws ApplicationException;
	
	/**
	 * Metodo que realiza la guarda de una funcion en la poliza para la pantalla emergente 
	 * Agregar Funcion en la Poliza de Objetos Asegurables de Consulta de Póliza Renovada.
	 * 
	 * @param cdUniEco
	 * @param cdRamo
	 * @param estado
	 * @param nmPoliza
	 * @param nmSituac
	 * @param nmSuplem
	 * @param cdRol
	 * @param cdPerson
	 * @param cdAtribu
	 * @param status
	 * @param nmOrdDom
	 * @param swReclam
	 * @param accion
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */
	@SuppressWarnings("unchecked")
	public String guardaFuncionEnPoliza(String cdUniEco,
			String cdRamo, String estado, String nmPoliza, String nmSituac,
			String nmSuplem, String cdRol, String cdPerson, String cdAtribu,
			String status, String nmOrdDom, String swReclam, String accion)
			throws ApplicationException;
	
	/**
	 * Metodo que obtiene datos variables en funcion de la informacion seleccionada en el 
	 * combo Tipo de la pantalla Accesorios de Consulta de Póliza Renovada.
	 * 
	 * @param cdTipObj
	 * @param start
	 * @param limit
	 * 
	 * @return PagedList 
	 */
	public PagedList obtenerDatosVblesSegunTipoObjeto(String cdTipObj, int start, int limit)throws ApplicationException;
	
}