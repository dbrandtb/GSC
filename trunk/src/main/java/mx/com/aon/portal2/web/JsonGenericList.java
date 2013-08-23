/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.aon.portal2.web;

import java.util.ArrayList;
import java.util.List;
import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.portal.service.CombosManager2;

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
    private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(JsonGenericList.class);
    
    public String obtenCatalogo()
    {
        try
        {
            if(cdatribu!=null&&cdatribu.equals("roles"))//solo para el caso en que obtenga roles (Titular, cónyugue, hijo...)
            {
                lista=combosManager2.obtenCatalogoRoles("2");
            }
            else//para los demas catalogos
            {
                lista=combosManager2.obtenCatalogoSaludVital("", "SL", cdatribu);
            }
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
}
/////////////////////////////////////////////////////////////////////////////////