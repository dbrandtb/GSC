package mx.com.gseguros.portal.general.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mx.com.aon.portal2.web.GenericVO;
import mx.com.gseguros.portal.general.service.CatalogosManager;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author Ricardo
 *
 */
public class CatalogosAction extends ActionSupport {
	
	private static final long serialVersionUID = 384960409053296809L;

	private Logger logger = Logger.getLogger(CatalogosAction.class);
	
	private CatalogosManager catalogosManager;
    
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
        	
				case MC_ESTATUS_TRAMITE:
				case MC_SUCURSALES_ADMIN:
				case MC_SUCURSALES_DOCUMENTO:
				case MC_TIPOS_TRAMITE:
				case MOTIVOS_CANCELACION:
				case NACIONALIDAD:
				case ROLES_POLIZA:
				case STATUS_POLIZA:
				case TIPOS_PAGO_POLIZA:
				case TIPOS_PERSONA:
				case TIPOS_POLIZA:
					lista = catalogosManager.getTmanteni(nombreCatalogo);
	                break;
				case CODIGOS_POSTALES:
					lista = catalogosManager.obtieneColonias(params.get("cp"));
					break;
					
				default:
					break;
			}
        	success = true;
        } catch(Exception ex) {
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
	
}