package mx.com.aon.portal.model;

/**
 * Clase VO usada para guardar el carrito de compras
 * 
 * @param cdCarro             codigo del carrito de compras
 * @param cdUsuari            codigo del usuario  
 * @param feInicio            fecha de inicio
 * @param cdContra            codigo  contrato
 * @param cdAsegur            codigo asegurado  
 * @param nmSubtot            monto subtotal del carrito
 * @param nmDsc               monto de descuento del carrito
 * @param mnTotal             monto total del carrito
 * @param cdEstado            codigo estado
 * @param feEstado            fecha estado
 * @param cdTipDom            codigo tipo domicilio          
 * @param nmOrdDom            numeracion del domicilio       
 * @param cdClient            codigo cliente
 * @param cdForPag            codigo forma de pago
 * @param cdUniEco            codigo aseguradora
 * @param cdRamo              codigo producto
 * @param nmPoliza            numero poliza
 * @param nmSuplem            numero suplemento
 * @param nmTotalP            monto total a pagar
 * @param cdTipSit            codigo tipos situacion
 * @param cdPlan              codigo plan
 * @param fgDscapli           figura descuento aplicable
 * @param feIngres            fecha ingreso 
 * @param cdEstadoD           codigo estado
 * @param nmTarj              numero tarjeta de credito
 * @param cdTiTarj            codigo tarjeta de credito 
 * @param cdPerson            codigo persona
 * @param feVence             fecha vencimiento tarjeta
 * @param cdBanco             codigo banco emisor tarjeta
 * @param debCred             indicador debito o credito
 *
 */

public class CarritoComprasGuardarVO {

	private String cdCarro;
    private String cdUsuari;
    private String feInicio;
  
    private String cdContra;
    private String cdAsegur;
	private String nmSubtot;
    private String nmDsc;
    private String nmTotal;
    private String cdEstado;
    private String feEstado;
    private String cdTipDom;
    private String nmOrdDom;
    private String cdClient;
    private String cdForPag;
    private String cdUniEco;
    private String cdRamo;
    private String nmPoliza;
    private String nmSuplem;
    private String mnTotalP;
    private String cdTipSit;
    private String cdPlan;
    private String fgDscapli;
    private String feIngres;
    private String cdEstadoD;
    
    private String nmTarj;
    private String cdTiTarj;
    private String cdPerson;
    private String feVence;
    private String cdBanco;
    private String debCred;
    
    
	public String getCdUsuari() {
		return cdUsuari;
	}
	public void setCdUsuari(String cdUsuari) {
		this.cdUsuari = cdUsuari;
	}
	public String getFeInicio() {
		return feInicio;
	}
	public void setFeInicio(String feInicio) {
		this.feInicio = feInicio;
	}
	public String getNmTarj() {
		return nmTarj;
	}
	public void setNmTarj(String nmTarj) {
		this.nmTarj = nmTarj;
	}
	public String getCdContra() {
		return cdContra;
	}
	public void setCdContra(String cdContra) {
		this.cdContra = cdContra;
	}
	public String getCdAsegur() {
		return cdAsegur;
	}
	public void setCdAsegur(String cdAsegur) {
		this.cdAsegur = cdAsegur;
	}
	public String getNmSubtot() {
		return nmSubtot;
	}
	public void setNmSubtot(String nmSubtot) {
		this.nmSubtot = nmSubtot;
	}
	public String getNmDsc() {
		return nmDsc;
	}
	public void setNmDsc(String nmDsc) {
		this.nmDsc = nmDsc;
	}
	public String getNmTotal() {
		return nmTotal;
	}
	public void setNmTotal(String nmTotal) {
		this.nmTotal = nmTotal;
	}
	public String getCdEstado() {
		return cdEstado;
	}
	public void setCdEstado(String cdEstado) {
		this.cdEstado = cdEstado;
	}
	public String getFeEstado() {
		return feEstado;
	}
	public void setFeEstado(String feEstado) {
		this.feEstado = feEstado;
	}
	public String getCdTipDom() {
		return cdTipDom;
	}
	public void setCdTipDom(String cdTipDom) {
		this.cdTipDom = cdTipDom;
	}
	public String getNmOrdDom() {
		return nmOrdDom;
	}
	public void setNmOrdDom(String nmOrdDom) {
		this.nmOrdDom = nmOrdDom;
	}
	public String getCdClient() {
		return cdClient;
	}
	public void setCdClient(String cdClient) {
		this.cdClient = cdClient;
	}
	public String getCdUniEco() {
		return cdUniEco;
	}
	public void setCdUniEco(String cdUniEco) {
		this.cdUniEco = cdUniEco;
	}
	public String getCdRamo() {
		return cdRamo;
	}
	public void setCdRamo(String cdRamo) {
		this.cdRamo = cdRamo;
	}
	public String getNmPoliza() {
		return nmPoliza;
	}
	public void setNmPoliza(String nmPoliza) {
		this.nmPoliza = nmPoliza;
	}
	public String getNmSuplem() {
		return nmSuplem;
	}
	public void setNmSuplem(String nmSuplem) {
		this.nmSuplem = nmSuplem;
	}
	public String getMnTotalP() {
		return mnTotalP;
	}
	public void setMnTotalP(String mnTotalP) {
		this.mnTotalP = mnTotalP;
	}
	public String getCdTipSit() {
		return cdTipSit;
	}
	public void setCdTipSit(String cdTipSit) {
		this.cdTipSit = cdTipSit;
	}
	public String getCdPlan() {
		return cdPlan;
	}
	public void setCdPlan(String cdPlan) {
		this.cdPlan = cdPlan;
	}
	public String getFgDscapli() {
		return fgDscapli;
	}
	public void setFgDscapli(String fgDscapli) {
		this.fgDscapli = fgDscapli;
	}
	public String getFeIngres() {
		return feIngres;
	}
	public void setFeIngres(String feIngres) {
		this.feIngres = feIngres;
	}
	public String getCdEstadoD() {
		return cdEstadoD;
	}
	public void setCdEstadoD(String cdEstadoD) {
		this.cdEstadoD = cdEstadoD;
	}

	public String getCdForPag() {
		return cdForPag;
	}

	public void setCdForPag(String cdForPag) {
		this.cdForPag = cdForPag;
	}
	public String getCdTiTarj() {
		return cdTiTarj;
	}
	public void setCdTiTarj(String cdTiTarj) {
		this.cdTiTarj = cdTiTarj;
	}
	public String getCdPerson() {
		return cdPerson;
	}
	public void setCdPerson(String cdPerson) {
		this.cdPerson = cdPerson;
	}
	public String getFeVence() {
		return feVence;
	}
	public void setFeVence(String feVence) {
		this.feVence = feVence;
	}
	public String getCdBanco() {
		return cdBanco;
	}
	public void setCdBanco(String cdBanco) {
		this.cdBanco = cdBanco;
	}
	public String getDebCred() {
		return debCred;
	}
	public void setDebCred(String debCred) {
		this.debCred = debCred;
	}
	public String getCdCarro() {
		return cdCarro;
	}
	public void setCdCarro(String cdCarro) {
		this.cdCarro = cdCarro;
	}


}
