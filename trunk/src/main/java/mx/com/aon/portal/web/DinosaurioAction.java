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
        cotizacion.setId(                                       69l);
        cotizacion.setCiudad(                                   new GenericVO("04","lol"));
        cotizacion.setDeducible(                                new BigDecimal("69"));
        cotizacion.setCopago(                                   new GenericVO("10000","lol"));
        cotizacion.setSumaSegurada(                             new GenericVO("14000","lol"));
        cotizacion.setCirculoHospitalario(                      new GenericVO("3","lol"));
        cotizacion.setCoberturaVacunas(                         new GenericVO("S","lol"));
        cotizacion.setCoberturaPrevencionEnfermedadesAdultos(   new GenericVO("S","lol"));
        cotizacion.setMaternidad(                               new GenericVO("S","lol"));
        cotizacion.setSumaAseguradaMaternidad(                  new GenericVO("100000","lol"));
        cotizacion.setBaseTabuladorReembolso(                   new GenericVO("35000","lol"));
        cotizacion.setCostoEmergenciaExtranjero(                new GenericVO("S","lol"));
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
