package mx.com.aon.portal.model;

/**
 * 
 * Clase VO usada para obtener un ejecutivo por cuenta.
 * 
 * @param cdPerson 
 * @param cdElemento 
 * @param dsNombre 
 * @param cdTipRam 
 * @param dsTipRam
 * @param cdRamo
 * @param dsRamo
 * @param cdGrupo
 * @param desGrupo
 * @param cdAgente
 * @param nomAgente
 * @param dsEstado
 * @param cdEstado
 * @param feInicio
 * @param feFin
 * @param cdTipage
 * @param dsTipage
 * @param cdLinCta
 * @param dsLinCta
 * @param swNivelPosterior
 */
public class EjecutivoCuentaVO {

    private String cdPerson;
    private String dsPerson;
    private String cdElemento;
    private String dsNombre;
    private String cdTipRam;
    private String dsTipRam;
    private String cdRamo;
    private String dsRamo;  
    private String cdGrupo;
    private String desGrupo;  
    private String cdAgente;
    private String nomAgente;
    private String dsEstado;
    private String cdEstado;
    private String feInicio;
    private String feFin;
    private String cdTipage;
    private String dsTipage;
    private String cdLinCta;
    private String dsLinCta;
    private String swNivelPosterior;

    /**
     * Agregados para manejo de Atributos de Ejecutivos de Cuenta
     */
    private String cdAtribu;
    private String dsAtribu;
    private String otValor;
    private String otTabval;
    private String swObliga;
    private String nmLmax;
    private String nmLmin;
    private String swFormat;
    private String gbSwformat;
    private String dsvalor;
    
   
    private String accion;
    
	public String getCdPerson() {
		return cdPerson;
	}
	public void setCdPerson(String cdPerson) {
		this.cdPerson = cdPerson;
	}
	public String getDsNombre() {
		return dsNombre;
	}
	public void setDsNombre(String dsNombre) {
		this.dsNombre = dsNombre;
	}
	public String getDsvalor() {
		return dsvalor;
	}
	public void setDsvalor(String dsvalor) {
		this.dsvalor = dsvalor;
	}
	public String getCdTipRam() {
		return cdTipRam;
	}
	public void setCdTipRam(String cdTipRam) {
		this.cdTipRam = cdTipRam;
	}
	public String getDsTipRam() {
		return dsTipRam;
	}
	public void setDsTipRam(String dsTipRam) {
		this.dsTipRam = dsTipRam;
	}
	public String getCdRamo() {
		return cdRamo;
	}
	public void setCdRamo(String cdRamo) {
		this.cdRamo = cdRamo;
	}
	public String getDsRamo() {
		return dsRamo;
	}
	public void setDsRamo(String dsRamo) {
		this.dsRamo = dsRamo;
	}
	public String getCdGrupo() {
		return cdGrupo;
	}
	public void setCdGrupo(String cdGrupo) {
		this.cdGrupo = cdGrupo;
	}
	public String getDesGrupo() {
		return desGrupo;
	}
	public void setDesGrupo(String desGrupo) {
		this.desGrupo = desGrupo;
	}
	public String getCdAgente() {
		return cdAgente;
	}
	public void setCdAgente(String cdAgente) {
		this.cdAgente = cdAgente;
	}
	public String getNomAgente() {
		return nomAgente;
	}
	public void setNomAgente(String nomAgente) {
		this.nomAgente = nomAgente;
	}
	public String getDsEstado() {
		return dsEstado;
	}
	public void setDsEstado(String dsEstado) {
		this.dsEstado = dsEstado;
	}
	public String getCdEstado() {
		return cdEstado;
	}
	public void setCdEstado(String cdEstado) {
		this.cdEstado = cdEstado;
	}
	public String getFeInicio() {
		return feInicio;
	}
	public void setFeInicio(String feInicio) {
		this.feInicio = feInicio;
	}
	public String getFeFin() {
		return feFin;
	}
	public void setFeFin(String feFin) {
		this.feFin = feFin;
	}
	public String getCdLinCta() {
		return cdLinCta;
	}
	public void setCdLinCta(String cdLinCta) {
		this.cdLinCta = cdLinCta;
	}
	public String getSwNivelPosterior() {
		return swNivelPosterior;
	}
	public void setSwNivelPosterior(String swNivelPosterior) {
		this.swNivelPosterior = swNivelPosterior;
	}
	public String getDsLinCta() {
		return dsLinCta;
	}
	public void setDsLinCta(String dsLinCta) {
		this.dsLinCta = dsLinCta;
	}
	public String getCdElemento() {
		return cdElemento;
	}
	public void setCdElemento(String cdElemento) {
		this.cdElemento = cdElemento;
	}
	public String getDsPerson() {
		return dsPerson;
	}
	public void setDsPerson(String dsPerson) {
		this.dsPerson = dsPerson;
	}
	public String getCdAtribu() {
		return cdAtribu;
	}
	public void setCdAtribu(String cdAtribu) {
		this.cdAtribu = cdAtribu;
	}
	public String getDsAtribu() {
		return dsAtribu;
	}
	public void setDsAtribu(String dsAtribu) {
		this.dsAtribu = dsAtribu;
	}
	public String getOtValor() {
		return otValor;
	}
	public void setOtValor(String otValor) {
		this.otValor = otValor;
	}
	public String getAccion() {
		return accion;
	}
	public void setAccion(String accion) {
		this.accion = accion;
	}
	public String getSwObliga() {
		return swObliga;
	}
	public void setSwObliga(String swObliga) {
		this.swObliga = swObliga;
	}
	public String getNmLmax() {
		return nmLmax;
	}
	public void setNmLmax(String nmLmax) {
		this.nmLmax = nmLmax;
	}
	public String getNmLmin() {
		return nmLmin;
	}
	public void setNmLmin(String nmLmin) {
		this.nmLmin = nmLmin;
	}
	public String getSwFormat() {
		return swFormat;
	}
	public void setSwFormat(String swFormat) {
		this.swFormat = swFormat;
	}
	public String getGbSwformat() {
		return gbSwformat;
	}
	public void setGbSwformat(String gbSwformat) {
		this.gbSwformat = gbSwformat;
	}
	public String getOtTabval() {
		return otTabval;
	}
	public void setOtTabval(String otTabval) {
		this.otTabval = otTabval;
	}
	public String getCdTipage() {
		return cdTipage;
	}
	public void setCdTipage(String cdTipage) {
		this.cdTipage = cdTipage;
	}
	public String getDsTipage() {
		return dsTipage;
	}
	public void setDsTipage(String dsTipage) {
		this.dsTipage = dsTipage;
	}

  
}
