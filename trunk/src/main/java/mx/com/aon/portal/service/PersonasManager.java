package mx.com.aon.portal.service;

import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.BackBoneResultVO;
import mx.com.aon.portal.model.DomicilioVO;
import mx.com.aon.portal.model.PersonaCorporativoVO;
import mx.com.aon.portal.model.PersonaDatosAdicionalesVO;
import mx.com.aon.portal.model.PersonaDatosGeneralesVO;
import mx.com.aon.portal.model.PersonaVO;
import mx.com.aon.portal.model.RelacionesPersonaVO;
import mx.com.aon.portal.model.TelefonoVO;
import mx.com.aon.portal.model.UsuarioClaveVO;

/**
 * Interface de servicios para datos de la persona
 *
 */
public interface PersonasManager {

	/**
	 *  Obtiene un conjunto de datos de persona
	 * 
	 *  @param codTipoPersona
	 *  @param codCorporativo
	 *  @param nombre
	 *  @param rfc
	 *  @param start comienzo o limite inferior de la paginacion de la grilla
	 *  @param limit final o limite superior de la paginacion la grilla
	 *  
	 *  @return Conjunto de objetos PersonasVO
	 *  
	 *  @throws ApplicationException
	 */
	public PagedList buscarPersonas (String codTipoPersona, String codCorporativo, String nombre, String rfc, int start, int limit) throws ApplicationException;
	
	/**
	 *  Inserta datos generales de persona 
	 * 
	 *  @param personaDatosGeneralesVO
	 *  
	 *  @return Objetos tipo BackBoneResultVO
	 *  
	 *  @throws ApplicationException
	 */
	public BackBoneResultVO guardarDatosGenerales (PersonaDatosGeneralesVO personaDatosGeneralesVO) throws ApplicationException;
	
	/**
	 *  Inserta datos adicionales de persona
	 * 
	 *  @param personaDatosAdicionalesVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio
	 *  
	 *  @throws ApplicationException
	 */
	public String guardarDatosAdicionales (PersonaDatosAdicionalesVO personaDatosAdicionalesVO) throws ApplicationException;
	
	/**
	 *  Obtiene el domicilio de persona en base a un parametro de entrada
	 * 
	 *  @param codigoPersona
	 *  @param start comienzo o limite inferior de la paginacion de la grilla
	 *  @param limit final o limite superior de la paginacion la grilla
	 *  
	 *  @return Conjunto de objetos DomicilioVO
	 *  
	 *  @throws ApplicationException
	 */
	public PagedList getDomicilios(String codigoPersona, int start, int limit) throws ApplicationException;
	
	/**
	 *  Inserta datos correspondientes a domicilio de persona
	 * 
	 *  @param domicilioVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio
	 *  
	 *  @throws ApplicationException
	 */
	public String guardarDomicilios(DomicilioVO domicilioVO) throws ApplicationException;

	/**
	 *  Obtiene configuracion del carrido de compras en base a un parametro de entrada
	 * 
	 *  @param codigoPersona
	 *  
	 *  @return Conjunto de objetos TelefonoVO
	 *  
	 *  @throws ApplicationException
	 */
	public PagedList getTelefono (String codigoPersona, int start, int limit) throws ApplicationException;

	/**
	 *  Inserta datos correspondites al telefono de persona
	 * 
	 *  @param telefonoVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio
	 *  
	 *  @throws ApplicationException
	 */
	public String guardarTelefonos (TelefonoVO telefonoVO) throws ApplicationException;

	/**
	 *  Inserta datos corporativos de personas
	 * 
	 *  @param personaCorporativoVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio
	 *  
	 *  @throws ApplicationException
	 */
	public String guardarCorporativo (PersonaCorporativoVO personaCorporativoVO) throws ApplicationException;

	/**
	 *  Obtiene datos de persona corporativo en base a un parametro de entrada
	 * 
	 *  @param codigoPersona
	 *  
	 *  @return Conjunto de objetos PersonaCorporativoVO
	 *  
	 *  @throws ApplicationException
	 */
	public PagedList getCorporativo (String codigoPersona, int start, int limit) throws ApplicationException;

	/**
	 *  Obtiene datos de persona en base a parametros de entrada
	 * 
	 *  @param tipoPersona
	 *  @param codPersona
	 *  
	 *  @return Conjunto de objetos PersonaVO
	 *  
	 *  @throws ApplicationException
	 */
	public PersonaVO obtenerPersona (String tipoPersona, String codPersona) throws ApplicationException;

	/**
	 *  Elimina un domicilio de persona
	 * 
	 *  @param codigoPersona
	 *  @param numOrden
	 *  @param start comienzo o limite inferior de la paginacion de la grilla
	 *  @param limit final o limite superior de la paginacion la grilla
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio
	 *  
	 *  @throws ApplicationException
	 */
	public String borraDomicilio (String codigoPersona, String numOrden) throws ApplicationException;

	/**
	 *  Elimina telefono de persona
	 * 
	 *  @param codigoPersona
	 *  @param numOrden
	 *  @param start comienzo o limite inferior de la paginacion de la grilla
	 *  @param limit final o limite superior de la paginacion la grilla
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio
	 *  
	 *  @throws ApplicationException
	 */
	public String borraTelefono (String codigoPersona, String numOrden) throws ApplicationException;

	/**
	 *  Obtiene datos adicionales de la persona en base a parametros de entrada
	 * 
	 *  @param codigoTipoPersona
	 *  @param codigoAtributo
	 *  @param codigoPersona
	 *  
	 *  @return Conjunto de objetos PersonaDatosAdicionalesVO
	 *  
	 *  @throws ApplicationException
	 */
	public PagedList getDatosAdicionales(String codigoTipoPersona, String codigoAtributo, String codigoPersona, int start, int limit) throws ApplicationException;

	/**
	 *  Obtiene una lista de personas para la exportacion a un formato predeterminado.
	 * 
	 *  @param codTipoPersona
	 *  @param codCorporativo
	 *  @param nombre
	 *  @param rfc
	 *    
	 *  @return Objeto modelo para exportar.
	 *  
	 *  @throws ApplicationException
	 */
	public TableModelExport getModel(String codTipoPersona, String codCorporativo, String nombre, String rfc) throws ApplicationException;

	/**
	 * Obtiene una lista de relaciones en la póliza
	 * 
	 * @param codigoPersona
	 * @param start
	 * @param limit
	 * @return
	 * @throws ApplicationException
	 */
	public PagedList getRelacionesPersona (String codigoPersona, int start, int limit) throws ApplicationException;

	/**
	 * Guardar datos de Relaciones
	 * 
	 * @param codigoPersona
	 * @param datosRelaciones
	 * @return
	 * @throws ApplicationException
	 */
	public String guardarRelaciones (List<RelacionesPersonaVO> datosRelaciones) throws ApplicationException;

	/**
	 * Elimina la persona seleccionada
	 * @param cdPerson
	 * @return
	 * @throws ApplicationException
	 */
	public String borrarPersona (String cdPerson) throws ApplicationException;
	
	/**
	 *  Obtiene configuracion de usuario en base a un parametro de entrada
	 * 
	 *  @param cdPerson
	 *  
	 *  @return Conjunto de objetos UsuarioClaveVO
	 *  
	 *  @throws ApplicationException
	 */
	public UsuarioClaveVO getUsuarioClave (String cdPerson) throws ApplicationException;
	
	/**
	 *  Inserta datos correspondites a la clave del usuario de persona
	 * 
	 *  @param usuarioClaveVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio
	 *  
	 *  @throws ApplicationException
	 */
	public String guardaUsuarioClave (UsuarioClaveVO usuarioClaveVO) throws ApplicationException;
	
	/**
	 *  Obtiene un conjunto de datos de usuario sin personas
	 * 
	 *  @param nombre
	 *  
	 *  @param start comienzo o limite inferior de la paginacion de la grilla
	 *  @param limit final o limite superior de la paginacion la grilla
	 *  
	 *  @return Conjunto de objetos UsuarioClaveVO
	 *  
	 *  @throws ApplicationException
	 */
	public PagedList buscarUsuarioSinPersona(String nombre, int start, int limit) throws ApplicationException;

	/**
	 *  Inserta datos correspondites a la clave del usuario de persona
	 * 
	 *  @param usuarioClaveVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio
	 *  
	 *  @throws ApplicationException
	 */
	public String guardaAsociaUsuarioClave (UsuarioClaveVO usuarioClaveVO) throws ApplicationException;
	
	/**
	 *  Inserta datos correspondites a la clave del usuario de persona
	 * 
	 *  @param usuarioClaveVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio
	 *  
	 *  @throws ApplicationException
	 */
	public String guardaCrearUsuarioClave (UsuarioClaveVO usuarioClaveVO) throws ApplicationException;
}