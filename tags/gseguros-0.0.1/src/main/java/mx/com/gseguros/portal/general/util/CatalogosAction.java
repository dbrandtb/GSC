package mx.com.gseguros.portal.general.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.kernel.service.KernelManagerSustituto;
import mx.com.aon.portal2.web.GenericVO;
import mx.com.gseguros.portal.endosos.service.EndososManager;
import mx.com.gseguros.portal.general.service.CatalogosManager;

import org.apache.log4j.Logger;

/**
 * 
 * @author Ricardo
 *
 */
public class CatalogosAction extends PrincipalCoreAction {
	
	private static final long serialVersionUID = 384960409053296809L;

	private Logger logger = Logger.getLogger(CatalogosAction.class);
	
	private CatalogosManager       catalogosManager;
	private KernelManagerSustituto kernelManager;
	private EndososManager         endososManager;
    
    private boolean success;
    
    /**
     * Nombre del catalogo a obtener
     */
    private String catalogo;
    
    /**
     * Lista con los objetos a devolver
     */
    private List<GenericVO> lista = new ArrayList<GenericVO>(0);
    
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
        	Catalogos nombreCatalogo = Catalogos.valueOf(catalogo);
        	switch(nombreCatalogo) {
        	
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
				case TCAUSASSV:
				case TTRATAMIENTO:
				case TPENALIZACIONES:
				case PLANES:
					lista = catalogosManager.getTmanteni(nombreCatalogo);
	                break;
				case MC_ESTATUS_TRAMITE:
					lista = catalogosManager.obtieneStatusTramite(params);
					break;
				case TATRISIT:
		            lista = catalogosManager.obtieneAtributosSituacion(params.get("cdatribu"), params.get("cdtipsit"), params.get("idPadre"));
					break;
				case TATRIPOL:
			        lista = catalogosManager.obtieneAtributosPoliza(params.get("cdatribu"), "2", params.get("idPadre"));
					break;
				case TATRIGAR:
					lista = catalogosManager.obtieneAtributosGarantia(params.get("cdatribu"), "SL", "2", params.get("idPadre"), params.get("cdgarant"));
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
					lista = catalogosManager.obtieneRolesSistema();
					break;
				case ENDOSOS:
					List<Map<String,String>>nombresEndosos=endososManager.obtenerNombreEndosos();
					lista=new ArrayList<GenericVO>(0);
					for(Map<String,String> nombre:nombresEndosos) {
						lista.add(new GenericVO(nombre.get("CDTIPSUP"), nombre.get("DSTIPSUP")));
					}
					break;
				default:
					throw new Exception("Catalogo no existente: " + nombreCatalogo);
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
	
}