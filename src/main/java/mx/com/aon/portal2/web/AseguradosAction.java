/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.aon.portal2.web;

import java.math.BigDecimal;
import java.util.List;
import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.portal.web.model.IncisoSaludVO;

/**
 *
 * @author Jair
 */
public class AseguradosAction extends PrincipalCoreAction{
    
    public static final String cdatribuSexo                         ="1";
    public static final String cdatribuCiudad                       ="3";
    public static final String cdatribuCopago                       ="5";
    public static final String cdatribuSumaAsegurada                ="7";
    public static final String cdatribuCirculoHospitalario          ="8";
    public static final String cdatribuCoberturaVacunas             ="9";
    public static final String cdatribuCoberturaPrevEnfAdultos      ="10";
    public static final String cdatribuMaternidad                   ="11";
    public static final String cdatribuSumaAseguradaMatenidad       ="12";
    public static final String cdatribuBaseTabuladorRembolso        ="13";
    public static final String cdatribuCostoEmergenciaExtranjero    ="14";
    private String ciudad;
    private BigDecimal deducible;
    private String copago;
    private String sumaSegurada;
    private String circuloHospitalario;
    private String coberturaVacunas;
    private String coberturaPrevencionEnfermedadesAdultos;
    private String maternidad;
    private String sumaAseguradaMaternidad;
    private String baseTabuladorReembolso;
    private String costoEmergenciaExtranjero;
    private List<IncisoSaludVO> incisos;
    
    public String asegurados()
    {
        return SUCCESS;
    }
    
    public String cotizar()
    {
        System.out.println("###: "+ciudad);
        System.out.println("###: "+deducible);
        System.out.println("###: "+copago);
        System.out.println("###: "+sumaSegurada);
        System.out.println("###: "+circuloHospitalario);
        System.out.println("###: "+coberturaVacunas);
        System.out.println("###: "+coberturaPrevencionEnfermedadesAdultos);
        System.out.println("###: "+maternidad);
        System.out.println("###: "+sumaAseguradaMaternidad);
        System.out.println("###: "+baseTabuladorReembolso);
        System.out.println("###: "+costoEmergenciaExtranjero);
        System.out.println("###: "+incisos.size());
        if(incisos!=null)
        {
            for(IncisoSaludVO i:incisos)
            {
                System.out.println("####: "+i.getId());
                System.out.println("####: "+i.getApellidoMaterno());
                System.out.println("####: "+i.getApellidoPaterno());
                System.out.println("####: "+i.getNombre());
                System.out.println("####: "+i.getSegundoNombre());
                System.out.println("####: "+i.getRol());
                System.out.println("####: "+i.getSexo());
                System.out.println("####: "+i.getFechaNacimiento());
            }
        }
        return SUCCESS;
    }

    public String getCdatribuSexo() {
        return cdatribuSexo;
    }

    public String getCdatribuCiudad() {
        return cdatribuCiudad;
    }

    public String getCdatribuCopago() {
        return cdatribuCopago;
    }

    public String getCdatribuSumaAsegurada() {
        return cdatribuSumaAsegurada;
    }

    public String getCdatribuCirculoHospitalario() {
        return cdatribuCirculoHospitalario;
    }

    public String getCdatribuCoberturaVacunas() {
        return cdatribuCoberturaVacunas;
    }

    public String getCdatribuCoberturaPrevEnfAdultos() {
        return cdatribuCoberturaPrevEnfAdultos;
    }

    public String getCdatribuMaternidad() {
        return cdatribuMaternidad;
    }

    public String getCdatribuSumaAseguradaMatenidad() {
        return cdatribuSumaAseguradaMatenidad;
    }

    public String getCdatribuBaseTabuladorRembolso() {
        return cdatribuBaseTabuladorRembolso;
    }

    public String getCdatribuCostoEmergenciaExtranjero() {
        return cdatribuCostoEmergenciaExtranjero;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public BigDecimal getDeducible() {
        return deducible;
    }

    public void setDeducible(BigDecimal deducible) {
        this.deducible = deducible;
    }

    public String getCopago() {
        return copago;
    }

    public void setCopago(String copago) {
        this.copago = copago;
    }

    public String getSumaSegurada() {
        return sumaSegurada;
    }

    public void setSumaSegurada(String sumaSegurada) {
        this.sumaSegurada = sumaSegurada;
    }

    public String getCirculoHospitalario() {
        return circuloHospitalario;
    }

    public void setCirculoHospitalario(String circuloHospitalario) {
        this.circuloHospitalario = circuloHospitalario;
    }

    public String getCoberturaVacunas() {
        return coberturaVacunas;
    }

    public void setCoberturaVacunas(String coberturaVacunas) {
        this.coberturaVacunas = coberturaVacunas;
    }

    public String getCoberturaPrevencionEnfermedadesAdultos() {
        return coberturaPrevencionEnfermedadesAdultos;
    }

    public void setCoberturaPrevencionEnfermedadesAdultos(String coberturaPrevencionEnfermedadesAdultos) {
        this.coberturaPrevencionEnfermedadesAdultos = coberturaPrevencionEnfermedadesAdultos;
    }

    public String getMaternidad() {
        return maternidad;
    }

    public void setMaternidad(String maternidad) {
        this.maternidad = maternidad;
    }

    public String getSumaAseguradaMaternidad() {
        return sumaAseguradaMaternidad;
    }

    public void setSumaAseguradaMaternidad(String sumaAseguradaMaternidad) {
        this.sumaAseguradaMaternidad = sumaAseguradaMaternidad;
    }

    public String getBaseTabuladorReembolso() {
        return baseTabuladorReembolso;
    }

    public void setBaseTabuladorReembolso(String baseTabuladorReembolso) {
        this.baseTabuladorReembolso = baseTabuladorReembolso;
    }

    public String getCostoEmergenciaExtranjero() {
        return costoEmergenciaExtranjero;
    }

    public void setCostoEmergenciaExtranjero(String costoEmergenciaExtranjero) {
        this.costoEmergenciaExtranjero = costoEmergenciaExtranjero;
    }

    public List<IncisoSaludVO> getIncisos() {
        return incisos;
    }

    public void setIncisos(List<IncisoSaludVO> incisos) {
        this.incisos = incisos;
    }
    
}
