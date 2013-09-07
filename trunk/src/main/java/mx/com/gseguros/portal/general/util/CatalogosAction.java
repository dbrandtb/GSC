/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.gseguros.portal.general.util;

import java.util.ArrayList;
import java.util.List;
import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.kernel.service.KernelManagerSustituto;
import mx.com.aon.portal2.web.GenericVO;

/**
 *
 * @author jair
 */
public class CatalogosAction extends PrincipalCoreAction
{
    //input
    private String catalogo;
    //output
    private List<GenericVO> lista=new ArrayList<GenericVO>(0);
    private boolean success=false;
    //private
    private org.apache.log4j.Logger log=org.apache.log4j.Logger.getLogger(CatalogosAction.class);
    private KernelManagerSustituto kernelManager;
    
    public String cargar()
    {
        if(catalogo.equals(ConstantesCatalogos.CON_CAT_POL_ESTADO))
        {
            lista.add(new GenericVO("#P","#Poliza"));
            lista.add(new GenericVO("#W","#Working"));
            success=true;
        }
        else if(catalogo.equals(ConstantesCatalogos.CON_CAT_POL_TIPO_PAGO))
        {
            lista.add(new GenericVO("#1",    "#Mensual"));
            lista.add(new GenericVO("#12",   "#Anual"));
            success=true;
        }
        else if(catalogo.equals(ConstantesCatalogos.CON_CAT_POL_TIPO_POLIZA))
        {
            lista.add(new GenericVO("#N","#Nueva"));
            lista.add(new GenericVO("#R","#Renovación"));
            success=true;
        }
        return SUCCESS;
    }
    
    /////////////////////////////////
    ////// getters and setters //////
    /*/////////////////////////////*/
    public String getCatalogo() {
        return catalogo;
    }

    public void setCatalogo(String catalogo) {
        this.catalogo = catalogo;
    }

    public List<GenericVO> getLista() {
        return lista;
    }

    /*public void setLista(List<GenericVO> lista) {
        this.lista = lista;
    }*/

    public boolean isSuccess() {
        return success;
    }

    /*public void setSuccess(boolean success) {
        this.success = success;
    }*/

    /*public KernelManagerSustituto getKernelManager() {
        return kernelManager;
    }*/

    public void setKernelManager(KernelManagerSustituto kernelManager) {
        this.kernelManager = kernelManager;
    }
    
}
