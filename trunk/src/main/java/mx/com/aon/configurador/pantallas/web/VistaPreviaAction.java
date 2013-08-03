/**
 * 
 */
package mx.com.aon.configurador.pantallas.web;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;

import mx.com.aon.configurador.pantallas.model.PantallaVO;
import mx.com.aon.configurador.pantallas.service.ConfiguradorPantallaService;
import mx.com.aon.core.VariableKernel;

/**
 * Clase Action para el control y visualizacion de datos en vista previa
 * 
 * @author aurora.lozada
 * 
 */

public class VistaPreviaAction  extends PrincipalConfPantallaAction {

    /**
     * 
     */
    private static final long serialVersionUID = -3813629711662156807L;
    
  
//    private ConfiguradorPantallaService configuradorManager;
    
    private String componente;
    private String cdElemento;
    private String cdRamo;
    private String cdTitulo;
    private String cdTipsit;
    private String cdSisrol;

    private boolean success;
    
    
    /**
     * @return the success
     */
    public boolean getSuccess() {
        return success;
    }


    /**
     * @param success the success to set
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * @param configuradorManager the configuradorManager to set
     */
    public void setConfiguradorManager( ConfiguradorPantallaService configuradorManager ) {
        this.configuradorManager = configuradorManager;
    }
    
    
    @Override
    public void prepare() throws Exception {
        super.prepare();

        boolean isDebug = logger.isDebugEnabled();

        if (isDebug) {
            logger.debug("### Enterintg to prepare in VistaPreviaAction ...");
        }

    }
    
    /**
     * Metodo dummy para la obtencion de parametros del area de trabajo
     * 
     * @return Cadena SUCCESS
     * @throws Exception
     */
    public String getParametro() throws Exception {
        boolean isDebugueable = logger.isDebugEnabled();
        
        
        if(isDebugueable){
            logger.debug("######################################################");
            logger.debug("### Llegando a metodo getParametro en VistaPreviaAction...");
            logger.debug("### componenete-------" + componente);
            logger.debug("######################################################");
        }
        
         componente = "items:[{xtype:\"textfield\", fieldLabel: \"Ejemplo action\", name:\"ejemplo1\"  }]";
        
        
      
        success = true;
        return SUCCESS;
    }
    
    public String obtienePantallaVistaPrevia() throws Exception{
    	
    	
    	Map<String, String> parametersPantallaFinal = new HashMap<String, String>();
        parametersPantallaFinal.put("CD_ELEMENT", cdElemento);
        parametersPantallaFinal.put("CD_RAMO", 	  cdRamo);
        parametersPantallaFinal.put("CD_TIPSIT",  cdTipsit);
        parametersPantallaFinal.put("CD_TITULO", cdTitulo);
        parametersPantallaFinal.put("CD_SISROL",  cdSisrol);
        PantallaVO pantalla = new PantallaVO ();
        pantalla = configuradorManager.getPantallaFinal(parametersPantallaFinal);
    	
        
    	logger.debug("EN EL METODO obtienePantallaVistaPrevia DEL VISTA PREVIA ACTION: "+ pantalla.getDsArchivoSec() );
        session.put("PANTALLA_VISTA_PREVIA", StringEscapeUtils.unescapeHtml(pantalla.getDsArchivoSec()));
        
        success = true;
    	return SUCCESS;
    }
    
    /**
     * @return the componente
     */
    public String getComponente() {
        return componente;
    }

    /**
     * @param componente the componente to set
     */
    public void setComponente(String componente) {
        this.componente = componente;
    }


	public String getCdElemento() {
		return cdElemento;
	}


	public void setCdElemento(String cdElemento) {
		this.cdElemento = cdElemento;
	}


	public String getCdRamo() {
		return cdRamo;
	}


	public void setCdRamo(String cdRamo) {
		this.cdRamo = cdRamo;
	}


	public String getCdTitulo() {
		return cdTitulo;
	}


	public void setCdTitulo(String cdTitulo) {
		this.cdTitulo = cdTitulo;
	}


	public String getCdTipsit() {
		return cdTipsit;
	}


	public void setCdTipsit(String cdTipsit) {
		this.cdTipsit = cdTipsit;
	}


	public String getCdSisrol() {
		return cdSisrol;
	}


	public void setCdSisrol(String cdSisrol) {
		this.cdSisrol = cdSisrol;
	}

}