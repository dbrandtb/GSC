/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.aon.portal2.web;

import java.util.ArrayList;
import java.util.List;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.portal.service.CombosManager2;

import org.apache.log4j.Logger;

/**
 *
 * @author Jair
 */
public class JsonGenericList extends PrincipalCoreAction
/////////////////////////////////////////////////////////////////////////////////
// ESTA CLASE UTILIZA UNA LISTA GENERICA PARA REGRESAR LISTAS DE COMBO Y OTROS //
/////////////////////////////////////////////////////////////////////////////////
{
    private List<GenericVO> lista;
    private CombosManager2 combosManager2;
    private String cdatribu;
    private String codigoTabla;
    private String idPadre="";
    private static Logger log = Logger.getLogger(JsonGenericList.class);
    private String cdgarant;
    private String cdramo;
    private String cdrol;
    private String cdtipsit;
    
    public String obtenCatalogo()
    {
        try
        {
            lista=combosManager2.obtenCatalogoSaludVital("", "SL", cdatribu, idPadre!=null&&idPadre.length()>0?idPadre:null);
        }
        catch(Exception ex)
        {
            lista=new ArrayList<GenericVO>(0);
            log.error("No se pudo obtener el catalogo "+cdatribu, ex);
        }
        return SUCCESS;
    }
    
    public String obtenCatalogoPol()
    {
        try
        {
            lista=combosManager2.obtenCatalogoPol("", "2", cdatribu, idPadre!=null&&idPadre.length()>0?idPadre:null);
        }
        catch(Exception ex)
        {
            lista=new ArrayList<GenericVO>(0);
            log.error("No se pudo obtener el catalogo "+cdatribu, ex);
        }
        return SUCCESS;
    }
    
    public String obtenCatalogoGar()
    {
        try
        {
            lista=combosManager2.obtenCatalogoGar("2", "SL", cdgarant, cdatribu, idPadre!=null&&idPadre.length()>0?idPadre:null);
        }
        catch(Exception ex)
        {
            lista=new ArrayList<GenericVO>(0);
            log.error("No se pudo obtener el catalogo "+cdatribu, ex);
        }
        return SUCCESS;
    }
    
    public String obtenCatalogoPer()
    {
        try
        {
            lista=combosManager2.obtenCatalogoPer(cdramo,cdrol,cdatribu,cdtipsit,idPadre);
        }
        catch(Exception ex)
        {
            lista=new ArrayList<GenericVO>(0);
            log.error("No se pudo obtener el catalogo "+cdatribu, ex);
        }
        return SUCCESS;
    }

    public List<GenericVO> getLista() {
        return lista;
    }

    public void setLista(List<GenericVO> lista) {
        this.lista = lista;
    }
    
    public void setCombosManager2(CombosManager2 combosManager2) {
        this.combosManager2 = combosManager2;
    }

    public String getCdatribu() {
        return cdatribu;
    }

    public void setCdatribu(String cdatribu) {
        this.cdatribu = cdatribu;
    }

    public String getCodigoTabla() {
        return codigoTabla;
    }

    public void setCodigoTabla(String codigoTabla) {
        this.codigoTabla = codigoTabla;
    }

    public String getIdPadre() {
        return idPadre;
    }

    public void setIdPadre(String idPadre) {
        this.idPadre = idPadre;
    }

	public String getCdgarant() {
		return cdgarant;
	}

	public void setCdgarant(String cdgarant) {
		this.cdgarant = cdgarant;
	}

	public String getCdramo() {
		return cdramo;
	}

	public void setCdramo(String cdramo) {
		this.cdramo = cdramo;
	}

	public String getCdrol() {
		return cdrol;
	}

	public void setCdrol(String cdrol) {
		this.cdrol = cdrol;
	}

	public String getCdtipsit() {
		return cdtipsit;
	}

	public void setCdtipsit(String cdtipsit) {
		this.cdtipsit = cdtipsit;
	}
}
/////////////////////////////////////////////////////////////////////////////////