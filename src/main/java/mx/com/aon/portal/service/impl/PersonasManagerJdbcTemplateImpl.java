package mx.com.aon.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.beanutils.Converter;

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
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.PersonasManager;
import mx.com.aon.portal.util.UserSQLDateConverter;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.portal.util.ConvertUtil;
import mx.com.gseguros.exception.ApplicationException;

/**
 * Clase que sirve para dar servicios al action que lo invoca.
 *
 * @implements PersonasManager
 *
 * @extends AbstractManager
 */
public class PersonasManagerJdbcTemplateImpl extends AbstractManagerJdbcTemplateInvoke implements PersonasManager {

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
        //todo implementar bajo el nuevo esquema
        return null;
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
		map.put("pi_otfisjur", personaDatosGeneralesVO.getCodigoTipoPersonaJ());
		map.put("pi_cdperson", ConvertUtil.nvl(personaDatosGeneralesVO.getCodigoPersona()));
		map.put("pi_dsnombre", personaDatosGeneralesVO.getNombre());
		map.put("pi_dsapellido", personaDatosGeneralesVO.getApellidoPaterno());
		map.put("pi_dsapellido1", personaDatosGeneralesVO.getApellidoMaterno());
		map.put("pi_otsexo", personaDatosGeneralesVO.getSexo());
		map.put("pi_cdestciv", personaDatosGeneralesVO.getEstadoCivil());		
		Converter converter = new UserSQLDateConverter("");
		map.put("pi_fenacimi", converter.convert(java.util.Date.class, personaDatosGeneralesVO.getFechaNacimiento()));

		map.put("pi_cdnacion", personaDatosGeneralesVO.getNacionalidad());
		map.put("pi_cdtipide", personaDatosGeneralesVO.getTipoIdentificador());
		map.put("pi_cdideper", personaDatosGeneralesVO.getNroIdentificador());
		map.put("pi_cdtipper", personaDatosGeneralesVO.getTipoEntidad());
		map.put("dsemail", personaDatosGeneralesVO.getEmail());
		map.put("curp", personaDatosGeneralesVO.getCurp());
		map.put("cdrfc", personaDatosGeneralesVO.getRfc());

/*
	<statement type="stored" name="PKG_PERSONA.P_SALVA_PERSONA" id="actualizaPersona">
        <param eval="true" name="pi_otfisjur" type="VARCHAR">$!params.get("codigoTipoPersonaJ")</param>
        <param eval="true" name="pi_cdperson" type="NUMERIC">$!params.get("codigoPersona")</param>

        <param eval="true" name="pi_cdtipide" type="VARCHAR">$!params.get("TipoIdentificador")</param>
        <param eval="true" name="pi_cdideper" type="VARCHAR">$!params.get("NroIdentificador")</param>

        <param eval="true" name="pi_dsnombre" type="VARCHAR"><![CDATA[$!params.get("Nombre")]]></param>
        <param eval="true" name="pi_dsapellido" type="VARCHAR"><![CDATA[$!params.get("ApellidoPaterno")]]></param>
        <param eval="true" name="pi_dsapellido1" type="VARCHAR"><![CDATA[$!params.get("ApellidoMaterno")]]></param>
        <param eval="true" name="pi_otsexo" type="VARCHAR">$!params.get("Sexo")</param>

        <param eval="true" name="pi_cdestciv" type="VARCHAR">$!params.get("EstadoCivil")</param>

        <param eval="true" name="pi_fenacimi" type="VARCHAR">$!params.get("FechaNacimiento")</param>
        <param eval="true" name="pi_cdnacion" type="VARCHAR">$!params.get("Nacionalidad")</param>
        <param eval="true" name="pi_cdtipper" type="VARCHAR">$!params.get("TipoEntidad")</param>
        <param eval="true" name="curp" type="VARCHAR">$!params.get("curp")</param>
        <param eval="true" name="cdrfc" type="VARCHAR">$!params.get("rfc")</param>
        <param eval="true" name="dsemail" type="VARCHAR">$!params.get("email")</param>
        <!--param eval="true" name="pv_feingreso_i" type="VARCHAR">$!params.get("FechaIngreso")</param-->

        <outparam id="po_msg_id" type="NUMERIC"></outparam>
        <outparam id="po_title" type="VARCHAR"></outparam>
	</statement>
 */
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
		map.put("pi_cdperson", ConvertUtil.nvl(personaCorporativoVO.getCodigoPersona()));
		map.put("pi_cdelemen", ConvertUtil.nvl(personaCorporativoVO.getCdElemen()));
		map.put("pi_cdgrupoper", ConvertUtil.nvl(personaCorporativoVO.getCdGrupoPer()));
		map.put("pi_feinicio", ConvertUtil.convertToDate(personaCorporativoVO.getFeInicio()));

		/*Converter converter = new UserSQLDateConverter("");
		map.put("pi_feinicio", converter.convert(java.util.Date.class, personaCorporativoVO.getFeInicio()));*/
		
		map.put("pi_fefin", ConvertUtil.convertToDate(personaCorporativoVO.getFeFin()));
		
		/*Converter converter = new UserSQLDateConverter("");
		map.put("pi_fefin", converter.convert(java.util.Date.class, personaCorporativoVO.getFeFin()));*/

		map.put("pi_status", ConvertUtil.nvl(personaCorporativoVO.getCdStatus()));
		map.put("pi_nmnomina", personaCorporativoVO.getNmNomina());

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
        //todo implementar bajo el nuevo esquema
        return null;

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

        //todo implementar bajo el nuevo esquema
        return null;

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

        //todo implementar bajo el nuevo esquema
        return null;

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
		map.put("pi_cdperson", codPersona);
		map.put("pi_otfisjur", tipoPersona);

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
        //todo implementar bajo el nuevo esquema
        return null;
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
        //todo implementar bajo el nuevo esquema
        return null;
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
        //todo implementar bajo el nuevo esquema
        HashMap map = new HashMap();
        map.put("pi_cdperson", codigoPersona);

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

        //todo implementar bajo el nuevo esquema
        return null;
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
        //todo implementar bajo el nuevo esquema
        return null;
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
        //todo implementar bajo el nuevo esquema
        return null;
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
        //todo implementar bajo el nuevo esquema
        return null;
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
        //todo implementar bajo el nuevo esquema
        return null;
	}

	/**
	 * Guarda informacion de relaciones
	 */
	public String guardarRelaciones(List<RelacionesPersonaVO> datosRelaciones)
			throws ApplicationException {
        //todo implementar bajo el nuevo esquema
        return null;
	}

	public String borrarPersona(String cdPerson) throws ApplicationException {
		HashMap map = new HashMap ();
		map.put("pv_cdperson_i", cdPerson);

		WrapperResultados res = returnBackBoneInvoke(map, "PERSONAS_BORRAR");
		return res.getMsgText();
	}

	/**
	 *  Obtiene un conjunto de Clave de Usuario de personas
	 *  Hace uso del Store Procedure  PKG_PERSONA.P_BUSCA_PERSONAS
	 *
	 *  @param cdPerson
	 *
	 *  @return Objeto PagedList
	 *
	 *  @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public UsuarioClaveVO getUsuarioClave(String cdperson) throws ApplicationException {
        //todo implementar bajo el nuevo esquema
        return null;
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
        //todo implementar bajo el nuevo esquema
        return null;

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
        //todo implementar bajo el nuevo esquema
        return null;
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
        //todo implementar bajo el nuevo esquema
        return null;

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

        //todo implementar bajo el nuevo esquema
        return null;
	}
	
}
