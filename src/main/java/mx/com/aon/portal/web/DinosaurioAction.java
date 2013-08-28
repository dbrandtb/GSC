/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.aon.portal.web;

import java.math.BigDecimal;
import java.util.Map;
import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.portal.web.model.CotizacionSaludVO;
import mx.com.aon.portal2.web.GenericVO;
import org.apache.struts2.interceptor.CookiesAware;

/**
 *
 * @author Jair
 */
public class DinosaurioAction extends PrincipalCoreAction implements CookiesAware{

    private Map<String, String> cookiesMap;
    private Dinosaurio dino;
    private String llave;
    private CotizacionSaludVO cotizacion;
    
    public String jsoo()
    {
        cotizacion=new CotizacionSaludVO();
        cotizacion.setId(                                           6969l);                         //0
        //sexo (inciso)                                                                               1
        //fecha nacimiento (inciso)                                                                   2
        cotizacion.setEstado(                                       new GenericVO("02","lol"));     //3
        cotizacion.setCiudad(                                       new GenericVO("06001","lol"));  //4
        cotizacion.setDeducible(                                    new BigDecimal("690000"));      //5
        cotizacion.setCopago(                                       new GenericVO("10000","lol"));  //6
        cotizacion.setSumaSegurada(                                 new GenericVO("14000","lol"));  //7
        cotizacion.setCirculoHospitalario(                          new GenericVO("3","lol"));      //8
        cotizacion.setCoberturaVacunas(                             new GenericVO("S","lol"));      //9
        cotizacion.setCoberturaPrevencionEnfermedadesAdultos(       new GenericVO("S","lol"));      //10
        cotizacion.setMaternidad(                                   new GenericVO("S","lol"));      //11
        cotizacion.setSumaAseguradaMaternidad(                      new GenericVO("100000","lol")); //12
        cotizacion.setBaseTabuladorReembolso(                       new GenericVO("35000","lol"));  //13
        cotizacion.setCostoEmergenciaExtranjero(                    new GenericVO("S","lol"));      //14
        cotizacion.setCoberturaEliminacionPenalizacionCambioZona(   new GenericVO("N","lol"));      //15
        //rol (inciso)                                                                                16
        return SUCCESS;
    }
    
    public Map<String, String> getCookiesMap() {
        return cookiesMap;
    }
    
    public void setCookiesMap(Map<String, String> map) {
        this.cookiesMap=map;
    }

    public Dinosaurio getDino() {
        return dino;
    }

    public void setDino(Dinosaurio dino) {
        this.dino = dino;
    }

    public String getLlave() {
        return llave;
    }

    public void setLlave(String llave) {
        this.llave = llave;
    }

    public CotizacionSaludVO getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(CotizacionSaludVO cotizacion) {
        this.cotizacion = cotizacion;
    }
    
}
