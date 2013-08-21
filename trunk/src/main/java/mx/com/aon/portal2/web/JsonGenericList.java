/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.aon.portal2.web;

import java.util.ArrayList;
import java.util.List;
import mx.com.aon.core.web.PrincipalCoreAction;

/**
 *
 * @author Jair
 */
public class JsonGenericList extends PrincipalCoreAction
/////////////////////////////////////////////////////////////////////////////////
// ESTA CLASE UTILIZA UNA LISTA GENERICA PARA REGRESAR LISTAS DE COMBO Y OTROS //
/////////////////////////////////////////////////////////////////////////////////
{
    
    private List<GenericVO> lista=new ArrayList<GenericVO>(0);
    
    public String obtenCiudades()
    {
        lista.add(new GenericVO("1","Guadalajara, Jalisco"));
        lista.add(new GenericVO("2","Hermosillo, Sonora"));
        lista.add(new GenericVO("3","Ciudad Juárez, Chihuahua"));
        lista.add(new GenericVO("4","Matamoros, Tamaulipas"));
        lista.add(new GenericVO("5","Torreón, Choahuila"));
        return SUCCESS;
    }
    
    public String obtenCopagos()
    {
        lista.add(new GenericVO("1","10%"));
        lista.add(new GenericVO("2","15%"));
        lista.add(new GenericVO("3","20%"));
        lista.add(new GenericVO("4","25%"));
        return SUCCESS;
    }
    
    public String obtenSumasAseguradas()
    {
        lista.add(new GenericVO("1","$1,500,000"));
        lista.add(new GenericVO("2","$2,000,000"));
        lista.add(new GenericVO("3","$3,000,000"));
        lista.add(new GenericVO("4","$5,000,000"));
        return SUCCESS;
    }
    
    public String obtenCirculosHospitalarios()
    {
        lista.add(new GenericVO("1","Alto"));
        lista.add(new GenericVO("2","Medio"));
        lista.add(new GenericVO("3","Bajo"));
        return SUCCESS;
    }
    
    public String obtenCoberturasVacunas()
    {
        lista.add(new GenericVO("1","Si"));
        lista.add(new GenericVO("2","No"));
        return SUCCESS;
    }
    
    public String obtenCoberturasPrevencionEnfermedadesAdultos()
    {
        lista.add(new GenericVO("1","Si"));
        lista.add(new GenericVO("2","No"));
        return SUCCESS;
    }
    
    public String obtenMaternidad()
    {
        lista.add(new GenericVO("1","Obligatoria"));
        lista.add(new GenericVO("2","Ayuda Suavizada"));
        lista.add(new GenericVO("3","Ambos"));
        return SUCCESS;
    }
    
    public String obtenSumaAseguradaMaternidad()
    {
        lista.add(new GenericVO("1","7.5 SMMG"));
        lista.add(new GenericVO("2","10 SMMG"));
        lista.add(new GenericVO("3","15 SMMG"));
        return SUCCESS;
    }
    
    public String obtenBaseTabuladorReembolso()
    {
        lista.add(new GenericVO("1","21,000"));
        lista.add(new GenericVO("2","25,000"));
        lista.add(new GenericVO("3","30,000"));
        lista.add(new GenericVO("4","35,000"));
        lista.add(new GenericVO("5","40,000"));
        lista.add(new GenericVO("6","45,000"));
        lista.add(new GenericVO("7","50,000"));
        return SUCCESS;
    }
    
    public String obtenCostoEmergenciaExtranjero()
    {
        lista.add(new GenericVO("1","ZI Individual"));
        lista.add(new GenericVO("2","ZF Individual"));
        lista.add(new GenericVO("3","Excluida"));
        return SUCCESS;
    }
    
    public String obtenGeneros()
    {
        lista.add(new GenericVO("1","Hombre"));
        lista.add(new GenericVO("2","Mujer"));
        return SUCCESS;
    }
    
    public String obtenRoles()
    {
        lista.add(new GenericVO("1","Titular"));
        lista.add(new GenericVO("2","Hermano(a)"));
        lista.add(new GenericVO("3","Hijo(a)"));
        lista.add(new GenericVO("4","Cónyugue"));
        lista.add(new GenericVO("5","Padre o madre"));
        return SUCCESS;
    }

    public List<GenericVO> getLista() {
        return lista;
    }

    public void setLista(List<GenericVO> lista) {
        this.lista = lista;
    }
}
/////////////////////////////////////////////////////////////////////////////////