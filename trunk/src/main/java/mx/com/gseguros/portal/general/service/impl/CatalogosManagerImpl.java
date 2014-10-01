package mx.com.gseguros.portal.general.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.portal2.web.GenericVO;
import mx.com.gseguros.exception.DaoException;
import mx.com.gseguros.portal.general.dao.CatalogosDAO;
import mx.com.gseguros.portal.general.service.CatalogosManager;
import mx.com.gseguros.portal.general.util.Catalogos;
import mx.com.gseguros.portal.general.util.Rango;
import mx.com.gseguros.portal.general.util.TipoTramite;
import mx.com.gseguros.portal.general.util.Validacion;
import mx.com.gseguros.wizard.dao.WizardDAO;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class CatalogosManagerImpl implements CatalogosManager {
	
	private CatalogosDAO catalogosDAO;
	
	@Autowired
	private WizardDAO wizardDAO;
	
	private static final Logger logger = Logger.getLogger(CatalogosManagerImpl.class);
	
	
	@Override
	public List<GenericVO> getTmanteni(Catalogos catalogo) throws Exception {
		return catalogosDAO.obtieneTmanteni(catalogo.getCdTabla());
	}
	
	
	@Override
	public List<GenericVO> obtieneColonias(String codigoPostal) throws Exception {
		return catalogosDAO.obtieneColonias(codigoPostal);
	}

	@Override
	public List<GenericVO> obtieneMunicipios(String cdEstado) throws Exception {
		return catalogosDAO.obtieneMunicipios(cdEstado);
	}

	@Override
	public List<GenericVO> obtieneZonasPorModalidad(String cdtipsit) throws Exception {
		return catalogosDAO.obtieneZonasPorModalidad(cdtipsit);
	}
	
	@Override
	public List<GenericVO> obtieneAtributosSituacion(String cdAtribu,
			String cdTipSit, String idPadre) throws Exception {
		
		String otValor = StringUtils.isNotBlank(idPadre) ? idPadre : null; 
		return catalogosDAO.obtieneAtributosSituacion(cdAtribu, cdTipSit, otValor);
	}
	
	@Override
	public List<GenericVO> obtieneAtributosSiniestro(String cdAtribu,
			String cdTipSit, String idPadre) throws Exception {
		
		String otValor = StringUtils.isNotBlank(idPadre) ? idPadre : null; 
		return catalogosDAO.obtieneAtributosSiniestro(cdAtribu, cdTipSit, otValor);
	}
	
	@Override
	public List<GenericVO> obtieneAtributosPoliza(String cdAtribu,
			String cdRamo, String idPadre) throws Exception {
		
		String otValor = StringUtils.isNotBlank(idPadre) ? idPadre : null;
		return catalogosDAO.obtieneAtributosPoliza(cdAtribu, cdRamo, otValor);
	}
	
	
	@Override
	public List<GenericVO> obtieneAtributosGarantia(String cdAtribu,
			String cdTipSit, String cdRamo, String idPadre, String cdGarant)
			throws Exception {
		
		String valAnt = StringUtils.isNotBlank(idPadre) ? idPadre : null;
		return catalogosDAO.obtieneAtributosGarantia(cdAtribu, cdTipSit, cdRamo, valAnt, cdGarant);
	}
	
	
	@Override
	public List<GenericVO> obtieneAtributosRol(String cdAtribu,
			String cdTipSit, String cdRamo, String valAnt, String cdRol)
			throws Exception {
		valAnt = StringUtils.isNotBlank(valAnt) ? valAnt : null;
		return catalogosDAO.obtieneAtributosRol(cdAtribu, cdTipSit, cdRamo, valAnt, cdRol);
	}
	
	
	@Override
	public List<GenericVO> obtieneRolesSistema(String dsRol) throws Exception {
		return catalogosDAO.obtieneRolesSistema(dsRol);
	}
	
	@Override
	public List<GenericVO> obtieneSucursales(String cdunieco) throws Exception {
		return catalogosDAO.obtieneSucursales(cdunieco);
	}
	
	@Override
	public List<GenericVO> obtieneAgentes(String claveONombre) throws Exception {
		return catalogosDAO.obtieneAgentes(claveONombre);
	}
	
	public void setCatalogosDAO(CatalogosDAO catalogosDAO) {
		this.catalogosDAO = catalogosDAO;
	}
	
	@Override
	public List<GenericVO> obtieneStatusTramite(Map<String,String> params) throws Exception
	{
		return catalogosDAO.obtieneStatusTramite(params);
	}
	
	@Override
	public String obtieneCantidadMaxima(String cdramo, String cdtipsit, TipoTramite tipoTramite, Rango rango, Validacion validacion) throws Exception {
		return catalogosDAO.obtieneCantidadMaxima(cdramo, cdtipsit, tipoTramite, rango, validacion);
	}
	
	@Override
	public List<GenericVO> cargarAgentesPorPromotor(String cdusuari)throws Exception
	{
		logger.info(""
				+ "\n######################################"
				+ "\n###### cargarAgentesPorPromotor ######"
				+ "\ncdusuari "+cdusuari
				);
		Map<String,String>params=new HashMap<String,String>();
		params.put("cdusuari",cdusuari);
		List<GenericVO>lista=catalogosDAO.cargarAgentesPorPromotor(params);
		if(lista==null)
		{
			lista=new ArrayList<GenericVO>();
		}
		logger.info(""
				+ "\nlista size "+lista.size()
				+ "\n###### cargarAgentesPorPromotor ######"
				+ "\n######################################"
				);
		return lista;
	}
	
	@Override
	public List<GenericVO> cargarServicioPublicoAutos(String substring,String cdramo,String cdtipsit)throws Exception
	{
		logger.info(""
				+ "\n########################################"
				+ "\n###### cargarServicioPublicoAutos ######"
				+ "\nsubstring "+substring
				+ "\ncdramo "+cdramo
				+ "\ncdtipsit "+cdtipsit
				);
		Map<String,String>params=new HashMap<String,String>();
		params.put("substring" , substring);
		params.put("cdtipsit"  , cdtipsit);
		params.put("cdramo"    , cdramo);
		List<GenericVO>lista=catalogosDAO.cargarServicioPublicoAutos(params);
		if(lista==null)
		{
			lista=new ArrayList<GenericVO>();
		}
		logger.info(""
				+ "\nlista size "+lista.size()
				+ "\n###### cargarServicioPublicoAutos ######"
				+ "\n########################################"
				);
		return lista;
	}
	
	@Override
	public String agregaCodigoPostal(Map<String, String> params)throws Exception
	{
		return catalogosDAO.agregaCodigoPostal(params);
	}

	@Override
	public String asociaZonaCodigoPostal(Map<String, String> params)throws Exception
	{
		return catalogosDAO.asociaZonaCodigoPostal(params);
	}
	
	@Override
	public List<Map<String, String>> obtieneTablasApoyo(Map<String,String> params) throws Exception
	{
		return wizardDAO.obtieneTablasApoyo(params);
	}

	@Override
	public String guardaTablaApoyo(Map<String,String> params) throws Exception
	{
		return wizardDAO.guardaTablaApoyo(params);
	}
	
	@Override
	public boolean guardaClavesTablaApoyo(Map<String, String> params, List<Map<String, String>> saveList) throws Exception
	{
		
		boolean add1 = false;
		boolean add2 = false;
		boolean add3 = false;
		boolean add4 = false;
		boolean add5 = false;
		
		HashMap<String, Map<String, String>> existentes =  new HashMap<String, Map<String, String>>();
		List<Map<String, String>> inexistentes = new ArrayList<Map<String,String>>();
		inexistentes.addAll(saveList);
		
		//Identificando que llaves ya existen y quitandolas de la lista de inexistentes
		for(Map<String, String> clave : saveList){
			if(StringUtils.isNotBlank(clave.get("NUMCLAVE"))){
				if("1".equals(clave.get("NUMCLAVE"))){
					existentes.put("1", clave);
					inexistentes.remove(clave);
				}else if("2".equals(clave.get("NUMCLAVE"))){
					existentes.put("2", clave);
					inexistentes.remove(clave);
				}else if("3".equals(clave.get("NUMCLAVE"))){
					existentes.put("3", clave);
					inexistentes.remove(clave);
				}else if("4".equals(clave.get("NUMCLAVE"))){
					existentes.put("4", clave);
					inexistentes.remove(clave);
				}else if("5".equals(clave.get("NUMCLAVE"))){
					existentes.put("5", clave);
					inexistentes.remove(clave);
				}
			}
		}
		
		logger.debug("Claves existentes: "+ existentes);
		logger.debug("Claves nuevas/ a generar: "+ inexistentes);
		
		Map<String, String> claveAgregar = null;
		
		//Agregando Existentes
		if(existentes.containsKey("1")){
			claveAgregar = existentes.get("1");
			params.put("pi_dsclave1", claveAgregar.get("DSCLAVE1"));
			params.put("pi_swforma1", claveAgregar.get("SWFORMA1"));
			params.put("pi_nmlmin1", claveAgregar.get("NMLMIN1"));
			params.put("pi_nmlmax1", claveAgregar.get("NMLMAX1"));
		}else {
			add1 = true;
		}
		if(existentes.containsKey("2")){
			claveAgregar = existentes.get("2");
			params.put("pi_dsclave2", claveAgregar.get("DSCLAVE1"));
			params.put("pi_swforma2", claveAgregar.get("SWFORMA1"));
			params.put("pi_nmlmin2", claveAgregar.get("NMLMIN1"));
			params.put("pi_nmlmax2", claveAgregar.get("NMLMAX1"));
		}else {
			add2 = true;
		}
		if(existentes.containsKey("3")){
			claveAgregar = existentes.get("3");
			params.put("pi_dsclave3", claveAgregar.get("DSCLAVE1"));
			params.put("pi_swforma3", claveAgregar.get("SWFORMA1"));
			params.put("pi_nmlmin3", claveAgregar.get("NMLMIN1"));
			params.put("pi_nmlmax3", claveAgregar.get("NMLMAX1"));
		}else {
			add3 = true;
		}
		if(existentes.containsKey("4")){
			claveAgregar = existentes.get("4");
			params.put("pi_dsclave4", claveAgregar.get("DSCLAVE1"));
			params.put("pi_swforma4", claveAgregar.get("SWFORMA1"));
			params.put("pi_nmlmin4", claveAgregar.get("NMLMIN1"));
			params.put("pi_nmlmax4", claveAgregar.get("NMLMAX1"));
		}else {
			add4 = true;
		}
		if(existentes.containsKey("5")){
			claveAgregar = existentes.get("5");
			params.put("pi_dsclave5", claveAgregar.get("DSCLAVE1"));
			params.put("pi_swforma5", claveAgregar.get("SWFORMA1"));
			params.put("pi_nmlmin5", claveAgregar.get("NMLMIN1"));
			params.put("pi_nmlmax5", claveAgregar.get("NMLMAX1"));
		}else {
			add5 = true;
		}
		
		//Agregando las que aun no existen
		if(add1){
			if(!inexistentes.isEmpty()){
				claveAgregar = new HashMap<String, String>(inexistentes.get(0));
				params.put("pi_dsclave1", claveAgregar.get("DSCLAVE1"));
				params.put("pi_swforma1", claveAgregar.get("SWFORMA1"));
				params.put("pi_nmlmin1", claveAgregar.get("NMLMIN1"));
				params.put("pi_nmlmax1", claveAgregar.get("NMLMAX1"));
				inexistentes.remove(0);
			}else{
				params.put("pi_dsclave1", null);
				params.put("pi_swforma1", null);
				params.put("pi_nmlmin1", null);
				params.put("pi_nmlmax1", null);
			}
		}
		
		if(add2){
			if(!inexistentes.isEmpty()){
				claveAgregar = new HashMap<String, String>(inexistentes.get(0));
				params.put("pi_dsclave2", claveAgregar.get("DSCLAVE1"));
				params.put("pi_swforma2", claveAgregar.get("SWFORMA1"));
				params.put("pi_nmlmin2", claveAgregar.get("NMLMIN1"));
				params.put("pi_nmlmax2", claveAgregar.get("NMLMAX1"));
				inexistentes.remove(0);
			}else{
				params.put("pi_dsclave2", null);
				params.put("pi_swforma2", null);
				params.put("pi_nmlmin2", null);
				params.put("pi_nmlmax2", null);
			}
		}
		
		if(add3){
			if(!inexistentes.isEmpty()){
				claveAgregar = new HashMap<String, String>(inexistentes.get(0));
				params.put("pi_dsclave3", claveAgregar.get("DSCLAVE1"));
				params.put("pi_swforma3", claveAgregar.get("SWFORMA1"));
				params.put("pi_nmlmin3", claveAgregar.get("NMLMIN1"));
				params.put("pi_nmlmax3", claveAgregar.get("NMLMAX1"));
				inexistentes.remove(0);
			}else{
				params.put("pi_dsclave3", null);
				params.put("pi_swforma3", null);
				params.put("pi_nmlmin3", null);
				params.put("pi_nmlmax3", null);
			}
		}
		
		if(add4){
			if(!inexistentes.isEmpty()){
				claveAgregar = new HashMap<String, String>(inexistentes.get(0));
				params.put("pi_dsclave4", claveAgregar.get("DSCLAVE1"));
				params.put("pi_swforma4", claveAgregar.get("SWFORMA1"));
				params.put("pi_nmlmin4", claveAgregar.get("NMLMIN1"));
				params.put("pi_nmlmax4", claveAgregar.get("NMLMAX1"));
				inexistentes.remove(0);
			}else{
				params.put("pi_dsclave4", null);
				params.put("pi_swforma4", null);
				params.put("pi_nmlmin4", null);
				params.put("pi_nmlmax4", null);
			}
		}
		
		if(add5){
			if(!inexistentes.isEmpty()){
				claveAgregar = new HashMap<String, String>(inexistentes.get(0));
				params.put("pi_dsclave5", claveAgregar.get("DSCLAVE1"));
				params.put("pi_swforma5", claveAgregar.get("SWFORMA1"));
				params.put("pi_nmlmin5", claveAgregar.get("NMLMIN1"));
				params.put("pi_nmlmax5", claveAgregar.get("NMLMAX1"));
				inexistentes.remove(0);
			}else{
				params.put("pi_dsclave5", null);
				params.put("pi_swforma5", null);
				params.put("pi_nmlmin5", null);
				params.put("pi_nmlmax5", null);
			}
		}
		
		
		wizardDAO.guardaClavesTablaApoyo(params);
		
		return true;
	}
	
	@Override
	public List<Map<String, String>> obtieneClavesTablaApoyo(Map<String,String> params) throws Exception
	{
		return wizardDAO.obtieneClavesTablaApoyo(params);
	}
	
	@Override
	public List<Map<String, String>> obtieneAtributosTablaApoyo(Map<String,String> params) throws Exception
	{
		return wizardDAO.obtieneAtributosTablaApoyo(params);
	}
	
	@Override
	public List<GenericVO> cargarDescuentosPorAgente(
    		String tipoUnidad
    		,String uso
    		,String zona
    		,String promotoria
    		,String cdagente
    		,String cdtipsit
    		,String cdatribu)throws Exception
    {
		logger.info(
				new StringBuilder()
				.append("\n#######################################")
				.append("\n###### cargarDescuentosPorAgente ######")
				.append("\n###### tipoUnidad").append(tipoUnidad)
				.append("\n###### uso").append(uso)
				.append("\n###### zona").append(zona)
				.append("\n###### promotoria").append(promotoria)
				.append("\n###### cdagente").append(cdagente)
				.append("\n###### cdtipsit").append(cdtipsit)
				.append("\n###### cdatribu").append(cdatribu)
				.toString()
				);
		return catalogosDAO.cargarDescuentosPorAgente(tipoUnidad,uso,zona,promotoria,cdagente,cdtipsit,cdatribu);
    }
}