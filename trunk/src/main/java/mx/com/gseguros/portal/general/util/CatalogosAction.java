/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.gseguros.portal.general.util;

import java.util.ArrayList;
import java.util.List;
import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.kernel.service.KernelManagerSustituto;
import mx.com.aon.portal.util.WrapperResultados;
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
    
    private String codigoPostal;
    
    public String cargar()
    {
        try
        {
            if(catalogo.equals(ConstantesCatalogos.CON_CAT_POL_ESTADO))
            {
                lista=kernelManager.getTmanteni(catalogo);
                success=true;
            }
            else if(catalogo.equals(ConstantesCatalogos.CON_CAT_POL_TIPO_PAGO))
            {
                lista=kernelManager.getTmanteni(catalogo);
                success=true;
            }
            else if(catalogo.equals(ConstantesCatalogos.CON_CAT_POL_TIPO_POLIZA))
            {
                lista=kernelManager.getTmanteni(catalogo);
                success=true;
            }
            else if(catalogo.equals(ConstantesCatalogos.CON_CAT_POL_ROL))
            {
                lista=kernelManager.getTmanteni(catalogo);
                success=true;
            }
            else if(catalogo.equals(ConstantesCatalogos.CON_CAT_MESACONTROL_SUCUR_ADMIN))
            {
                lista=kernelManager.getTmanteni(catalogo);
                success=true;
            }
            else if(catalogo.equals(ConstantesCatalogos.CON_CAT_MESACONTROL_SUCUR_DOCU))
            {
                lista=kernelManager.getTmanteni(catalogo);
                success=true;
            }
            else if(catalogo.equals(ConstantesCatalogos.CON_CAT_MESACONTROL_TIP_TRAMI))
            {
                lista=kernelManager.getTmanteni(catalogo);
                success=true;
            }
            else if(catalogo.equals(ConstantesCatalogos.CON_CAT_MESACONTROL_ESTAT_TRAMI))
            {
                lista=kernelManager.getTmanteni(catalogo);
                success=true;
            }
            else if(catalogo.equals(ConstantesCatalogos.CON_CAT_TPERSONA))
            {
                lista=kernelManager.getTmanteni(catalogo);
                success=true;
            }
            else if(catalogo.equals(ConstantesCatalogos.CON_CAT_NACIONALIDAD))
            {
                lista=kernelManager.getTmanteni(catalogo);
                success=true;
            }
            else if(catalogo.equals(ConstantesCatalogos.CON_CAT_CANCELA_MOTIVOS))
            {
                lista=kernelManager.getTmanteni(catalogo);
                success=true;
            }
        }
        catch(Exception ex)
        {
            lista=new ArrayList<GenericVO>(0);
            success=false;
        }
        return SUCCESS;
    }
    
    public String cargaColonias()
    {
        try
        {
        	WrapperResultados result = kernelManager.cargaColonias(codigoPostal);
        	lista = (List<GenericVO>) result.getItemList(); 
             
        }
        catch(Exception ex)
        {
            lista=new ArrayList<GenericVO>(0);
            success=false;
            logger.error("Error al obtener el catalogo de colonias",ex);
        }
        success=true;
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

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
    
}
