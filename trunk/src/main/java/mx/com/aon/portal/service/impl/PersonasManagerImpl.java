package mx.com.aon.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.AyudaCoberturasVO;
import mx.com.aon.portal.model.BackBoneResultVO;
import mx.com.aon.portal.model.DomicilioVO;
import mx.com.aon.portal.model.PersonaCorporativoVO;
import mx.com.aon.portal.model.PersonaDatosAdicionalesVO;
import mx.com.aon.portal.model.PersonaDatosGeneralesVO;
import mx.com.aon.portal.model.PersonaVO;
import mx.com.aon.portal.model.RelacionesPersonaVO;
import mx.com.aon.portal.model.TelefonoVO;
import mx.com.aon.portal.model.UsuarioClaveVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.PersonasManager;
import mx.com.aon.portal.util.WrapperResultados;

/**
 * Clase que sirve para dar servicios al action que lo invoca.
 * 
 * @implements PersonasManager
 * 
 * @extends AbstractManager
 */
public class PersonasManagerImpl extends AbstractManager implements PersonasManager {

	/**
	 *  Obtiene un conjunto de usos del carrito de compras
	 *  Hace uso del Store Procedure  PKG_PERSONA.P_BUSCA_PERSONAS
	 * 
	 *  @param codTipoPersona
	 *  @param codCorporativo
	 *  @param nombre
	 *    
	 *  @return Objeto PagedList
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public PagedList buscarPersonas(String codTipoPersona, String codCorporativo, String nombre, String rfc, int start, int limit) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("codTipoPersona", codTipoPersona);
		map.put("codCorporativo", codCorporativo);
		map.put("nombre", nombre);
		map.put("rfc", rfc);
		
		return pagedBackBoneInvoke(map, "PERSONAS_BUSCAR", start, limit);
	}

	/**
	 *  Inserta o actualiza datos generales de persona
	 *  Hace uso de los Store Procedure para insertar PKG_PERSONA.P_SALVA_PERSONA
	 *  y para guardar PKG_PERSONA.P_SALVA_DATOS_ADIC
	 * 
	 *  @param personaDatosGeneralesVO
	 *  
	 *  @return Objeto BackBoneResultVO
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public BackBoneResultVO guardarDatosGenerales (PersonaDatosGeneralesVO personaDatosGeneralesVO) throws ApplicationException {

		BackBoneResultVO backBoneResultVO = new BackBoneResultVO();

		HashMap map = new HashMap();
		map.put("codigoTipoPersonaJ", personaDatosGeneralesVO.getCodigoTipoPersonaJ());
		map.put("codigoPersona", personaDatosGeneralesVO.getCodigoPersona());
		map.put("Nombre", personaDatosGeneralesVO.getNombre());
		map.put("ApellidoPaterno", personaDatosGeneralesVO.getApellidoPaterno());
		map.put("ApellidoMaterno", personaDatosGeneralesVO.getApellidoMaterno());
		map.put("Sexo", personaDatosGeneralesVO.getSexo());
		map.put("EstadoCivil", personaDatosGeneralesVO.getEstadoCivil());
		map.put("FechaNacimiento", personaDatosGeneralesVO.getFechaNacimiento());
		map.put("Nacionalidad", personaDatosGeneralesVO.getNacionalidad());
		map.put("TipoIdentificador", personaDatosGeneralesVO.getTipoIdentificador());
		map.put("NroIdentificador", personaDatosGeneralesVO.getNroIdentificador());
		map.put("TipoEntidad", personaDatosGeneralesVO.getTipoEntidad());
		//map.put("FechaIngreso", personaDatosGeneralesVO.getFechaIngreso());
		map.put("email", personaDatosGeneralesVO.getEmail());
		map.put("curp", personaDatosGeneralesVO.getCurp());
		map.put("rfc", personaDatosGeneralesVO.getRfc());

		if (personaDatosGeneralesVO.getCodigoPersona() == null || personaDatosGeneralesVO.getCodigoPersona().equals("")) {
			WrapperResultados res = returnBackBoneInvoke(map, "PERSONAS_INSERTAR");
			backBoneResultVO.setOutParam(res.getResultado());
			backBoneResultVO.setMsgText(res.getMsgText());
			return backBoneResultVO;
		} else {
			WrapperResultados res = returnBackBoneInvoke(map, "PERSONAS_GUARDAR");
			backBoneResultVO.setOutParam("");
			backBoneResultVO.setMsgText(res.getMsgText());
			return backBoneResultVO;
		}
	}

	/**
	 *  Inserta datos de una persona corporativa
	 *  Hace uso del Store Procedure PKG_PERSONA.P_GUARDA_CORPORA
	 * 
	 *  @param personaCorporativoVO
	 *  
	 *  @return Objeto WrapperResultados
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public String guardarCorporativo(PersonaCorporativoVO personaCorporativoVO) throws ApplicationException {

		HashMap map = new HashMap();
		map.put("codigoPersona", personaCorporativoVO.getCodigoPersona());
		map.put("cdElemento", personaCorporativoVO.getCdElemen());
		map.put("codigoGrupo", personaCorporativoVO.getCdGrupoPer());
		map.put("FechaAlta", personaCorporativoVO.getFeInicio());
		map.put("FechaBaja", personaCorporativoVO.getFeFin());
		map.put("Estado", personaCorporativoVO.getCdStatus());
		map.put("Nomina", personaCorporativoVO.getNmNomina());

		
		WrapperResultados res = returnBackBoneInvoke(map, "PERSONAS_GUARDAR_CORPORATIVO");
		return res.getMsgText();
	}

	/**
	 *  Inserta o actualiza datos adicionales de una persona
	 *  Hace uso del Store Procedure PKG_PERSONA.P_SALVA_DATOS_ADIC
	 * 
	 *  @param personaDatosAdicionalesVO
	 *  
	 *  @return Objeto WrapperResultados
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public String guardarDatosAdicionales(PersonaDatosAdicionalesVO personaDatosAdicionalesVO) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdUniEco", personaDatosAdicionalesVO.getPi_cdunieco());
		map.put("cdRamo", personaDatosAdicionalesVO.getPi_cdramo());
		map.put("estado", personaDatosAdicionalesVO.getPi_estado());
		map.put("nmPoliza", personaDatosAdicionalesVO.getPi_nmpoliza());
		map.put("nmSituac", personaDatosAdicionalesVO.getPi_nmsituac());
		map.put("cdRol", personaDatosAdicionalesVO.getPi_cdrol());
		map.put("cdPerson", personaDatosAdicionalesVO.getPi_cdperson());
		map.put("cdTipSit", personaDatosAdicionalesVO.getPi_cdtipsit());
		map.put("nmSuplem", personaDatosAdicionalesVO.getPi_nmsuplem());
		map.put("otValor01", personaDatosAdicionalesVO.getPi_otvalor01());
		map.put("otValor02", personaDatosAdicionalesVO.getPi_otvalor02());
		map.put("otValor03", personaDatosAdicionalesVO.getPi_otvalor03());
		map.put("otValor04", personaDatosAdicionalesVO.getPi_otvalor04());
		map.put("otValor05", personaDatosAdicionalesVO.getPi_otvalor05());
		map.put("otValor06", personaDatosAdicionalesVO.getPi_otvalor06());
		map.put("otValor07", personaDatosAdicionalesVO.getPi_otvalor07());
		map.put("otValor08", personaDatosAdicionalesVO.getPi_otvalor08());
		map.put("otValor09", personaDatosAdicionalesVO.getPi_otvalor09());
		map.put("otValor10", personaDatosAdicionalesVO.getPi_otvalor10());
		map.put("otValor11", personaDatosAdicionalesVO.getPi_otvalor11());
		map.put("otValor12", personaDatosAdicionalesVO.getPi_otvalor12());
		map.put("otValor13", personaDatosAdicionalesVO.getPi_otvalor13());
		map.put("otValor14", personaDatosAdicionalesVO.getPi_otvalor14());
		map.put("otValor15", personaDatosAdicionalesVO.getPi_otvalor15());
		map.put("otValor16", personaDatosAdicionalesVO.getPi_otvalor16());
		map.put("otValor17", personaDatosAdicionalesVO.getPi_otvalor17());
		map.put("otValor18", personaDatosAdicionalesVO.getPi_otvalor18());
		map.put("otValor19", personaDatosAdicionalesVO.getPi_otvalor19());
		map.put("otValor20", personaDatosAdicionalesVO.getPi_otvalor20());
		map.put("otValor21", personaDatosAdicionalesVO.getPi_otvalor21());
		map.put("otValor22", personaDatosAdicionalesVO.getPi_otvalor22());
		map.put("otValor23", personaDatosAdicionalesVO.getPi_otvalor23());
		map.put("otValor24", personaDatosAdicionalesVO.getPi_otvalor24());
		map.put("otValor25", personaDatosAdicionalesVO.getPi_otvalor25());
		map.put("otValor26", personaDatosAdicionalesVO.getPi_otvalor26());
		map.put("otValor27", personaDatosAdicionalesVO.getPi_otvalor27());
		map.put("otValor28", personaDatosAdicionalesVO.getPi_otvalor28());
		map.put("otValor29", personaDatosAdicionalesVO.getPi_otvalor29());
		map.put("otValor30", personaDatosAdicionalesVO.getPi_otvalor30());
		map.put("otValor31", personaDatosAdicionalesVO.getPi_otvalor31());
		map.put("otValor32", personaDatosAdicionalesVO.getPi_otvalor32());
		map.put("otValor33", personaDatosAdicionalesVO.getPi_otvalor33());
		map.put("otValor34", personaDatosAdicionalesVO.getPi_otvalor34());
		map.put("otValor35", personaDatosAdicionalesVO.getPi_otvalor35());
		map.put("otValor36", personaDatosAdicionalesVO.getPi_otvalor36());
		map.put("otValor37", personaDatosAdicionalesVO.getPi_otvalor37());
		map.put("otValor38", personaDatosAdicionalesVO.getPi_otvalor38());
		map.put("otValor39", personaDatosAdicionalesVO.getPi_otvalor39());
		map.put("otValor40", personaDatosAdicionalesVO.getPi_otvalor40());
		map.put("otValor41", personaDatosAdicionalesVO.getPi_otvalor41());
		map.put("otValor42", personaDatosAdicionalesVO.getPi_otvalor42());
		map.put("otValor43", personaDatosAdicionalesVO.getPi_otvalor43());
		map.put("otValor44", personaDatosAdicionalesVO.getPi_otvalor44());
		map.put("otValor45", personaDatosAdicionalesVO.getPi_otvalor45());
		map.put("otValor46", personaDatosAdicionalesVO.getPi_otvalor46());
		map.put("otValor47", personaDatosAdicionalesVO.getPi_otvalor47());
		map.put("otValor48", personaDatosAdicionalesVO.getPi_otvalor48());
		map.put("otValor49", personaDatosAdicionalesVO.getPi_otvalor49());
		map.put("otValor50", personaDatosAdicionalesVO.getPi_otvalor50());

		WrapperResultados res = returnBackBoneInvoke(map, "PERSONAS_GUARDAR_DATOS_ADICIONALES");
		return res.getMsgText();
	}

	/**
	 *  Inserta o actualiza datos referidos al domicilio de la persona
	 *  Hace uso del Store Procedure PKG_PERSONA.P_GUARDA_DOMICILIO
	 * 
	 *  @param domicilioVO
	 *  
	 *  @return Objeto WrapperResultados
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public String guardarDomicilios(DomicilioVO domicilioVO) throws ApplicationException {

		HashMap map = new HashMap();
		map.put("codigoPersona", domicilioVO.getCodigoPersona());
		map.put("codigoTipoDom", domicilioVO.getTipoDomicilio());
		map.put("ordenDom", domicilioVO.getNumOrdenDomicilio());
		map.put("Calle", domicilioVO.getDsDomicilio());
		map.put("CodigoPostal", domicilioVO.getCdPostal());
		map.put("NumeroExterior", domicilioVO.getNumero());
		map.put("NumeroInterior", domicilioVO.getNumeroInterno());
		map.put("Pais", domicilioVO.getCodigoPais());
		map.put("Estado", domicilioVO.getCodigoEstado());
		map.put("Municipio", domicilioVO.getCodigoMunicipio());
		map.put("Colonia", domicilioVO.getCodigoColonia());

		WrapperResultados res =  returnBackBoneInvoke(map, "PERSONAS_GUARDAR_DOMICILIO");
		return res.getMsgText();
	}

	/**
	 *  Inserta o actualiza un telefono de persona
	 *  Hace uso del Store Procedure PKG_PERSONA.P_GUARDA_TELEFONO
	 * 
	 *  @param telefonoVO
	 *  
	 *  @return Objeto WrapperResultados
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public String guardarTelefonos(TelefonoVO telefonoVO) throws ApplicationException {

		HashMap map = new HashMap ();
		map.put("codigoPersona", telefonoVO.getCodigoPersona());
		map.put("codigoTipoTelefono", telefonoVO.getCodTipTel());
		map.put("numOrden", telefonoVO.getNumeroOrden());
		map.put("codigoArea", telefonoVO.getCodArea());
		map.put("Numero", telefonoVO.getNumTelef());
		map.put("Extension", telefonoVO.getNumExtens());

		WrapperResultados res = returnBackBoneInvoke(map, "PERSONAS_GUARDAR_TELEFONO");
		return res.getMsgText();
	}

	/**
	 *  Obtiene datos de personas
	 *  Hace uso del Store Procedure PKG_COTIZA.P_OBTIENE_DIRECCION_ORDEN
	 * 
	 *  @param tipoPersona
	 *  @param codPersona
	 *  
	 *  @return Objeto PersonaVO
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public PersonaVO obtenerPersona (String tipoPersona, String codPersona) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("codPersona", codPersona);
		map.put("TipoPersona", tipoPersona);

		return (PersonaVO)getBackBoneInvoke(map, "OBTENER_PERSONA");
	}

	/**
	 *  Obtiene un conjunto de domicilios de personas
	 *  Hace uso del Store Procedure PKG_PERSONA.P_OBTIENE_DOMICILIO
	 * 
	 *  @param codigoPersona
	 *  
	 *  @return Objeto PagedList
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public PagedList getDomicilios(String codigoPersona, int start, int limit)throws ApplicationException {
		HashMap map = new HashMap();
		map.put("codigoPersona", codigoPersona);

		return pagedBackBoneInvoke(map, "OBTENER_DOMICILIOS_PERSONA", start, limit);
	}

	/**
	 *  Obtiene un conjunto de telefonos de personas
	 *  Hace uso del Store Procedure  PKG_PERSONA.P_OBTIENE_TELEFONO
	 * 
	 *  @param codigoPersona
	 *  
	 *  @return Objeto PagedList
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public PagedList getTelefono (String codigoPersona, int start, int limit) throws ApplicationException {
		HashMap map = new HashMap ();
		map.put("codigoPersona", codigoPersona);

		return pagedBackBoneInvoke(map, "OBTENER_TELEFONOS_PERSONA", start, limit);
	}

	/**
	 *  Obtiene un conjunto de datos sobre personas corporativas
	 *  Hace uso del Store Procedure PKG_PERSONA.P_PERSONA_CORPORATIVO
	 * 
	 *  @param codigoPersona
	 *  
	 *  @return Objeto PagedList
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public PagedList getCorporativo (String codigoPersona, int start, int limit) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("codigoPersona", codigoPersona);

		return pagedBackBoneInvoke(map, "OBTENER_CORPORATIVO_PERSONA", start, limit);
	}

	/**
	 *  Elimina un domicilio de persona
	 *  Hace uso del Store Procedure PKG_PERSONA.P_BORRA_DOMICILIO
	 * 
	 *  @param codigoPersona
	 *  @param numOrden
	 *  
	 *  @return Objeto WrapperResultados
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public String borraDomicilio(String codigoPersona, String numOrden)throws ApplicationException {

		HashMap map = new HashMap();
		map.put("codigoPersona", codigoPersona);
		map.put("numOrden", numOrden);

		WrapperResultados res = returnBackBoneInvoke(map, "BORRAR_DOMICILIO_PERSONA");
		return res.getMsgText();
	}

	/**
	 *  Elimina un telefono de persona
	 *  Hace uso del Store Procedure PKG_PERSONA.P_BORRA_TELEFONO
	 * 
	 *  @param codigoPersona
	 *  @param numOrden
	 *  
	 *  @return Objeto WrapperResultados
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public String borraTelefono(String codigoPersona, String numOrden) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("codigoPersona", codigoPersona);
		map.put("numOrden", numOrden);

		WrapperResultados res = returnBackBoneInvoke(map, "BORRAR_TELEFONO_PERSONA");
		return res.getMsgText();
	}

	/**
	 *  Obtiene un conjunto de datos adicionales correpondientes a personas
	 *  Hace uso del Store Procedure  PKG_PERSONA.P_OBTIENE_DATOS_ADICIONALES
	 * 
	 *  @param codigoTipoPersona
	 *  @param codigoAtributo
	 *  @param codigoPersona
	 *  
	 *  @return Objeto PagedList
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public PagedList getDatosAdicionales(String codigoTipoPersona, String codigoAtributo, String codigoPersona, int start, int limit)throws ApplicationException {
		HashMap map = new HashMap();
		map.put("codigoTipoPersona", codigoTipoPersona);
		map.put("codigoAtributo", codigoAtributo);
		map.put("codigoPersona", codigoPersona);

		return pagedBackBoneInvoke(map, "PERSONAS_OBTENER_DATOS_ADICIONALES", start, limit);
	}

	/**
	 *  Obtiene un conjunto de personas para la exportacion a un formato predeterminado
	 *  Hace uso del Store Procedure  PKG_PERSONA.P_BUSCA_PERSONAS
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
	@SuppressWarnings("unchecked")
	public TableModelExport getModel(String codTipoPersona, String codCorporativo, String nombre, String rfc) throws ApplicationException {
		TableModelExport model = new TableModelExport();
		List lista = null;
		
		HashMap map = new HashMap();
		map.put("codTipoPersona", codTipoPersona);
		map.put("codCorporativo", codCorporativo);
		map.put("nombre", nombre);
		map.put("rfc", rfc);

		lista = (ArrayList)getExporterAllBackBoneInvoke(map, "PERSONAS_EXPORTAR");
		model.setInformation(lista);
		model.setColumnName(new String[] {"Cod. Persona", "Nombre", "Tipo de Persona", "Cod. Estructura", "Estructura", "Rfc", "Nro. Nomina"});

		return model;
	}

	/**
	 * Obtiene una lista de relaciones en la póliza
	 * 
	 * @param codigoPersona
	 * @return
	 * @throws ApplicationException
	 */
	public PagedList getRelacionesPersona(String codigoPersona, int start, int limit)
			throws ApplicationException {
		HashMap map = new HashMap();
		map.put("codigoPersona", codigoPersona);

		return pagedBackBoneInvoke(map, "PERSONAS_OBTENER_RELACIONES", start, limit);
	}

	/**
	 * Guarda informacion de relaciones
	 */
	public String guardarRelaciones(List<RelacionesPersonaVO> datosRelaciones)
			throws ApplicationException {
		WrapperResultados res = new WrapperResultados();
		for (int i=0; i<datosRelaciones.size(); i++) {
			HashMap map = new HashMap ();
			RelacionesPersonaVO relacionesPersonaVO = (RelacionesPersonaVO)datosRelaciones.get(i);
			map.put("cdPerson", relacionesPersonaVO.getCdPerson());
			map.put("cdPerRel", relacionesPersonaVO.getCdPerRel());
			map.put("cdRol", relacionesPersonaVO.getCdRol());
			map.put("cdRelaci", relacionesPersonaVO.getCdRelaci());
			map.put("cdAccion", relacionesPersonaVO.getCdAccion());
			res = returnBackBoneInvoke(map, "PERSONAS_GUARDAR_RELACIONES");
		}
		return res.getMsgText();
	}

	public String borrarPersona(String cdPerson) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 *  Obtiene un conjunto de claves de personas
	 *  Hace uso del Store Procedure  PKG_PERSONA.P_BUSCA_PERSONAS
	 * 
	 *  @param cdPerson
	 *  
	 *  @return Objeto PagedList
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public UsuarioClaveVO getUsuarioClave(String cdperson) throws ApplicationException
	{
		HashMap map = new HashMap();
		map.put("cdperson", cdperson);
		
        return (UsuarioClaveVO)getBackBoneInvoke(map,"OBTENER_USUARIO_CLAVE_PERSONA");
	}
	
	
	/**
	 *  Inserta una clave de un usuario de persona
	 *  Hace uso del Store Procedure PKG_PERSONA.P_GUARDA_REGISTRO
	 * 
	 *  @param telefonoVO
	 *  
	 *  @return Objeto WrapperResultados
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public String guardaUsuarioClave(UsuarioClaveVO usuarioClaveVO) throws ApplicationException {

		HashMap map = new HashMap ();

		map.put("cdUsuari", usuarioClaveVO.getCdUsuari());
		map.put("registGrid", usuarioClaveVO.getRegistGrid());

		WrapperResultados res = returnBackBoneInvoke(map, "PERSONAS_GUARDAR_CLAVE_USUARIO");
		return res.getMsgText();
	}
	
	/**
	 *  Obtiene un conjunto de claves de usuarios sin personas
	 *  Hace uso del Store Procedure  PKG_PERSONA.P_BUSCA_PERSONAS
	 * 
	 *  @param nombre
	 *  
	 *  @return Objeto PagedList
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public PagedList buscarUsuarioSinPersona(String nombre, int start, int limit) throws ApplicationException {
		HashMap map = new HashMap ();
		map.put("nombre", nombre);
		

		return pagedBackBoneInvoke(map, "BUSCAR_USUARIO_SIN_PERSONA", start, limit);
	}
	
	/**
	 *  Inserta una clave de un usuario de persona
	 *  Hace uso del Store Procedure PKG_PERSONA.P_GUARDA_REGISTRO
	 * 
	 *  @param telefonoVO
	 *  
	 *  @return Objeto WrapperResultados
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public String guardaAsociaUsuarioClave(UsuarioClaveVO usuarioClaveVO) throws ApplicationException {

		HashMap map = new HashMap ();
		map.put("cdUsuari", usuarioClaveVO.getCdUsuari());
		map.put("cdperson", usuarioClaveVO.getCdPerson());

		WrapperResultados res = returnBackBoneInvoke(map, "PERSONAS_GUARDAR_ASOCIAR_CLAVE_USUARIO");
		return res.getMsgText();
	}
	
	/**
	 *  Inserta una clave creada de un usuario persona 
	 *  Hace uso del Store Procedure Pkg_Sec_Users.add_user
	 * 
	 *  @param telefonoVO
	 *  
	 *  @return Objeto WrapperResultados
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public String guardaCrearUsuarioClave(UsuarioClaveVO usuarioClaveVO) throws ApplicationException {

		HashMap map = new HashMap ();
		map.put("cdUsuari", usuarioClaveVO.getCdUsuari());
		map.put("contrasenha", usuarioClaveVO.getContrasenha());
		map.put("dsUsuari", usuarioClaveVO.getDsUsuari());
		map.put("cdPerson", usuarioClaveVO.getCdPerson());
		
		WrapperResultados res = returnBackBoneInvoke(map, "PERSONAS_GUARDAR_CREAR_CLAVE_USUARIO");
		return res.getMsgText();
	}
	
}
