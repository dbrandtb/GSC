package mx.com.aon.portal.service;

import mx.com.aon.portal.model.ElementoComboBoxVO;
import mx.com.aon.portal2.web.GenericVO;
import mx.com.aon.core.ApplicationException;

import java.util.ArrayList;
import java.util.List;

/**
 * Interface con servicios para todos los combos del proyecto.
 *
 */
public interface CombosManager2 {


	public List comboRolFuncionalidades (String pv_cdelemento_i) throws ApplicationException;

	public List comboUsuarioFuncionalidades (String pv_cdelemento_i, String pv_cdsisrol_i) throws ApplicationException;

    public List comboCargaMasiva () throws ApplicationException;

    public List comboTiposActividad () throws ApplicationException;
    
    public List comboObtieneAlgoritmos () throws ApplicationException;
    
    
    public List obtienePais() throws ApplicationException;
	
	public List obtenerCatalogoAon(String pv_country_code_i,String pv_cdsistema_i) throws ApplicationException;
	
	public List obtenerCatalogoExterno(String pv_country_code_i, String pv_cdsistema_i, String pv_otclave01_i, String pv_otvalor_i, String pv_cdtablaext_i) throws ApplicationException;
	
	public List obtenerCodigo(String pv_country_code_i) throws ApplicationException;
    
	public List comboObtienePaises()throws ApplicationException;
    
	public List comboObtieneUso()throws ApplicationException;
	
	public List comboObtieneSistemaExterno()throws ApplicationException;
	
	public List comboCodigoPostal(String pv_cdpais_i)throws ApplicationException;
	
	public List comboObtienePoliza(String pv_cdunieco_i, String pv_cdramo_i, String pv_cdperson_i )throws ApplicationException;

	public List comboCatalogosCompuestos (String cdColumna, String cdClave1, String cdClave2) throws ApplicationException;
	
	public List comboListaValores (String ottipotb) throws ApplicationException;
	
	public List comboCondicionInstPago() throws ApplicationException;
	
	public List obtenCatalogoSaludVital(String producto, String cdtipsit, String cdatribu, String otval) throws ApplicationException;
	
	public List obtenCatalogoPol(String producto, String cdramo, String cdatribu, String otval) throws ApplicationException;
        
    public List obtenCatalogoRoles(String pv_cdramo_i) throws ApplicationException;
        
    public List obtenComboDependienteOverride(String codigoTablaDependiente,String valorTablaPadre,String valantant) throws ApplicationException;

    public List obtenCatalogoGar(String cdramo, String cdtipsit, String cdgarant, String cdatribu, String valant) throws ApplicationException;
    
    public List obtenCatalogoPer(String cdramo, String cdrol, String cdatribu, String cdtipsit, String valant) throws ApplicationException;
	
}
