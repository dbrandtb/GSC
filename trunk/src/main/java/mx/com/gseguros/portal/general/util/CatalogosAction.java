package mx.com.gseguros.portal.general.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.kernel.service.KernelManagerSustituto;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal2.web.GenericVO;
import mx.com.gseguros.externo.service.StoredProceduresManager;
import mx.com.gseguros.portal.endosos.service.EndososManager;
import mx.com.gseguros.portal.general.service.CatalogosManager;
import mx.com.gseguros.portal.siniestros.model.CoberturaPolizaVO;
import mx.com.gseguros.portal.siniestros.model.ConsultaProveedorVO;
import mx.com.gseguros.portal.siniestros.service.SiniestrosManager;

import org.apache.log4j.Logger;

/**
 * 
 * @author Ricardo
 *
 */
public class CatalogosAction extends PrincipalCoreAction {
	
	private static final long serialVersionUID = 384960409053296809L;

	private Logger logger = Logger.getLogger(CatalogosAction.class);
	
	private CatalogosManager        catalogosManager;
	private EndososManager          endososManager;
	private KernelManagerSustituto  kernelManager;
	private SiniestrosManager 	    siniestrosManager;
    private StoredProceduresManager storedProceduresManager; 
	
    private boolean success;
    
    /**
     * true si queremos la propiedad "listaGenerica", 
     * false si queremos la propiedad "lista"
     */
    private boolean catalogoGenerico;
    
    /**
     * Nombre del catalogo a obtener
     */
    private String catalogo;
    
    /**
     * Lista con elementos de tipo "key","value" del cat&aacute;logo solicitado
     */
    private List<GenericVO> lista = new ArrayList<GenericVO>(0);
    
    /**
     * Lista personalizada, puede contener cualquier tipo de objeto 
     */
    private List<?> listaGenerica;
    
    /**
     * Parametros enviados a los catalogos
     */
    private Map<String, String> params;

    
    /**
     * Obtiene el catalogo solicitado en forma de una lista de VOs con llave y valor
     * @return
     * @throws Exception
     */
    public String obtieneCatalogo() throws Exception {
    	logger.debug("catalogo=" + catalogo);
        try {
        	Catalogos cat = Catalogos.valueOf(catalogo);
        	switch(cat) {
        	
        		case AGENTES:
        			lista = catalogosManager.obtieneAgentes(params!=null?params.get("agente"):null);
        			break;
        		case COLONIAS:
					lista = catalogosManager.obtieneColonias(params.get("cp"));
					break;
				case MC_SUCURSALES_ADMIN:
				case MC_SUCURSALES_DOCUMENTO:
				case MC_TIPOS_TRAMITE:
				case MOTIVOS_CANCELACION:
				case MOTIVOS_REEXPEDICION:
				case NACIONALIDAD:
				case ROLES_POLIZA:
				case SEXO:
				case STATUS_POLIZA:
				case TIPOS_PAGO_POLIZA:
				case TIPOS_PAGO_POLIZA_SIN_DXN:
				case TIPOS_PERSONA:
				case TIPOS_POLIZA:
				case TIPO_PAGO_SINIESTROS:
				case TIPO_ATENCION_SINIESTROS:
				case TIPO_CONCEPTO_SINIESTROS:
				case CAUSA_SINIESTRO:
				case TRATAMIENTOS:
				case PENALIZACIONES:
				case PLANES:
				case TIPO_MONEDA:
				case TIPO_MENU:
				case DESTINOPAGO:
				case CATCONCEPTO:
				case REFERENCIAS_TRAMITE_NUEVO:
				case TERRORWS:
					lista = catalogosManager.getTmanteni(cat);
	                break;
				case MC_ESTATUS_TRAMITE:
					lista = catalogosManager.obtieneStatusTramite(params);
					break;
				case TATRISIT:
		            lista = catalogosManager.obtieneAtributosSituacion(params.get("cdatribu"), params.get("cdtipsit"), params.get("idPadre"));
					break;
				case TATRISIN:
		            lista = catalogosManager.obtieneAtributosSiniestro(params.get("cdatribu"), params.get("cdtipsit"), params.get("idPadre"));
					break;
				case TATRIPOL:
			        lista = catalogosManager.obtieneAtributosPoliza(params.get("cdatribu"), params.get("cdramo"), params.get("idPadre"));
					break;
				case TATRIGAR:
					lista = catalogosManager.obtieneAtributosGarantia(params.get("cdatribu"), params.get("cdtipsit"), params.get("cdramo"), params.get("idPadre"), params.get("cdgarant"));
					break;
				case TATRIPER:
			        lista = catalogosManager.obtieneAtributosRol(params.get("cdatribu"), params.get("cdtipsit"), params.get("cdramo"), params.get("idPadre"), params.get("cdrol"));
					break;
				case RAMOS:
					List<Map<String,String>>ramos=kernelManager.obtenerRamos(params!=null?params.get("idPadre"):null);
					lista=new ArrayList<GenericVO>(0);
					for(Map<String,String> ramo:ramos) {
						lista.add(new GenericVO(ramo.get("cdramo"), ramo.get("dsramo")));
					}
					break;
				case TIPSIT:
					List<Map<String,String>>tipsits=kernelManager.obtenerTipsit(params!=null?params.get("idPadre"):null);
					lista=new ArrayList<GenericVO>(0);
					for(Map<String,String> tipsit:tipsits) {
						lista.add(new GenericVO(tipsit.get("CDTIPSIT"), tipsit.get("DSTIPSIT")));
					}
					break;
				case ROLES_SISTEMA:
					String filtro = null;
					if(params!=null){
						filtro = params.get("dsRol");
					}
					lista = catalogosManager.obtieneRolesSistema(filtro);
					break;
				case ENDOSOS:
					UserVO usuario = (UserVO)session.get("USUARIO");
					List<Map<String,String>>nombresEndosos=endososManager.obtenerNombreEndosos(usuario.getRolActivo().getObjeto().getValue());
					lista=new ArrayList<GenericVO>(0);
					for(Map<String,String> nombre:nombresEndosos) {
						lista.add(new GenericVO(nombre.get("CDTIPSUP"), nombre.get("DSTIPSUP")));
					}
					break;
				case SINO:
					lista=new ArrayList<GenericVO>(0);
					lista.add(new GenericVO("N", "NO"));
					lista.add(new GenericVO("S", "SI"));
					break;
				case COBERTURAS:
					lista = siniestrosManager.obtieneListadoCobertura(params.get("cdramo"), params.get("cdtipsit"));
					break;
				case COBERTURASXTRAMITE:
					
					try{
						String tipoPago = params.get("tipopago").toString();
						
						if(TipoPago.DIRECTO.getCodigo().equals(tipoPago)){
							lista = siniestrosManager.obtieneListadoCobertura(params.get("cdramo"), params.get("cdtipsit"));
							break;
						}else{
							String ntramite = params.get("ntramite");
							Map<String,String> paramsRes = (HashMap<String, String>) siniestrosManager.obtenerLlaveSiniestroReembolso(ntramite);
							
							for(Entry<String,String>en:paramsRes.entrySet()){
								params.put(en.getKey().toLowerCase(),en.getValue());
							}
							
							HashMap<String, Object> paramCobertura = new HashMap<String, Object>();
							paramCobertura.put("pv_cdunieco_i",params.get("cdunieco"));
							paramCobertura.put("pv_estado_i",params.get("estado"));
							paramCobertura.put("pv_cdramo_i",params.get("cdramo"));
							paramCobertura.put("pv_nmpoliza_i",params.get("nmpoliza"));
							paramCobertura.put("pv_nmsituac_i",params.get("nmsituac"));
							paramCobertura.put("pv_cdgarant_i",params.get("cdgarant"));
							
							List<CoberturaPolizaVO> listaCobertura = siniestrosManager.getConsultaListaCoberturaPoliza(paramCobertura);
							lista=new ArrayList<GenericVO>(0);
							for(CoberturaPolizaVO nombre:listaCobertura) {
								lista.add(new GenericVO(nombre.getCdgarant(), nombre.getDsgarant()));
							}
							break;
						}
					}catch(Exception ex){
						logger.error("error al obtener clave de siniestro para la pantalla del tabed panel",ex);
					}
					
					
				case SUBCOBERTURAS:
					String cdgarant = null;
					String cdsubcob = null;
					if(params!=null)
					{
						cdsubcob = params.get("cdsubcob");
						if(params.get("cdgarant")!=null)
						{
							cdgarant = params.get("cdgarant");
						}
						else if(params.get("idPadre")!=null)
						{
							cdgarant = params.get("idPadre");
						}
					}
					lista = siniestrosManager.getConsultaListaSubcobertura(cdgarant, cdsubcob);
					break;
				case MEDICOS:
					List<ConsultaProveedorVO> medicos = siniestrosManager.getConsultaListaProveedorMedico(
							Rol.MEDICO.getCdrol(), params != null ? params.get("cdpresta") : null);
					if(catalogoGenerico) {
						listaGenerica = medicos;
					} else {
						lista = new ArrayList<GenericVO>();
						for(ConsultaProveedorVO medico : medicos) {
							lista.add(new GenericVO(medico.getCdpresta(),medico.getNombre()));
						}
					}
					break;
				case PROVEEDORES:
					List<ConsultaProveedorVO> provs = siniestrosManager.getConsultaListaProveedorMedico(
							Rol.CLINICA.getCdrol(), params != null ? params.get("cdpresta") : null);
					if(catalogoGenerico) {
						listaGenerica = provs;
					} else {
						lista = new ArrayList<GenericVO>();
						for(ConsultaProveedorVO prov : provs) {
							lista.add(new GenericVO(prov.getCdpresta(),prov.getNombre()));
						}
					}
					break;
				case ICD:
					lista = siniestrosManager.getConsultaListaCPTICD(cat.getCdTabla(),params.get("otclave"));
					break;
				case CODIGOS_MEDICOS:
					String idconcep = null;
					String descripc = null;
					if(params!=null)
					{
						idconcep = params.get("idPadre");
						descripc = params.get("descripc");
					}
					lista = siniestrosManager.obtenerCodigosMedicos(idconcep, descripc);
					break;
				case MOTIVOS_RECHAZO_SINIESTRO:
					lista=new ArrayList<GenericVO>();
					List<Map<String,String>>listaMotivos=siniestrosManager.loadListaRechazos();
					for(Map<String,String>ele : listaMotivos)
					{
						lista.add(new GenericVO(ele.get("CDMOTIVO"),ele.get("DSMOTIVO")));
					}
					break;
				case SUBMOTIVOS_RECHAZO_SINIESTRO:
					if(params==null)
					{
						params=new HashMap<String,String>();
					}
					params.put("pv_cdmotivo_i",params.get("idPadre"));
					lista=new ArrayList<GenericVO>();
					List<Map<String,String>>listaSubMotivos=siniestrosManager.loadListaIncisosRechazos(params);
					for(Map<String,String>ele : listaSubMotivos)
					{
						lista.add(new GenericVO(ele.get("CDCAUMOT"),ele.get("DSCAUMOT")));
					}
					break;
				case ROLES_RAMO:
					LinkedHashMap<String,Object>params2=new LinkedHashMap<String,Object>();
					params2.putAll(params);
					List<Map<String,String>>lista2=storedProceduresManager.procedureListCall("PKG_LISTAS.P_GET_ROLES_X_RAMO", params2, null);
					lista=new ArrayList<GenericVO>();
					for(Map<String,String>iElem : lista2)
					{
						lista.add(new GenericVO(iElem.get("CDROL"),iElem.get("DSROL")));
					}
					break;
				default:
					throw new Exception("Catalogo no existente: " + cat);
					//break;
			}
        	success = true;
        } catch(Exception e) {
        	logger.error("No se pudo obtener el catalogo para " + catalogo, e);
            lista=new ArrayList<GenericVO>(0);
        }
        return SUCCESS;
	}
    
    
    // Getters and setters
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public boolean isCatalogoGenerico() {
		return catalogoGenerico;
	}

	public void setCatalogoGenerico(boolean catalogoGenerico) {
		this.catalogoGenerico = catalogoGenerico;
	}

	public String getCatalogo() {
		return catalogo;
	}

	public void setCatalogo(String catalogo) {
		this.catalogo = catalogo;
	}

	public List<GenericVO> getLista() {
		return lista;
	}

	public void setLista(List<GenericVO> lista) {
		this.lista = lista;
	}

	public List<?> getListaGenerica() {
		return listaGenerica;
	}

	public void setListaGenerica(List<?> listaGenerica) {
		this.listaGenerica = listaGenerica;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public void setCatalogosManager(CatalogosManager catalogosManager) {
		this.catalogosManager = catalogosManager;
	}

	public void setKernelManager(KernelManagerSustituto kernelManager) {
		this.kernelManager = kernelManager;
	}

	public void setEndososManager(EndososManager endososManager) {
		this.endososManager = endososManager;
	}


	public void setSiniestrosManager(SiniestrosManager siniestrosManager) {
		this.siniestrosManager = siniestrosManager;
	}

	public void setStoredProceduresManager(
			StoredProceduresManager storedProceduresManager) {
		this.storedProceduresManager = storedProceduresManager;
	}
	
}