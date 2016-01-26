package mx.com.gseguros.mesacontrol.model;

import mx.com.gseguros.utils.Utils;

public class FlujoVO
{
	private String ntramite
	               ,status
	               ,cdtipflu
	               ,cdflujomc
	               ,webid
	               ,tipoent
	               ,claveent
	               ,cdunieco
	               ,cdramo
	               ,estado
	               ,nmpoliza
	               ,nmsituac
	               ,nmsuplem
	               ,aux;

	public String getNtramite() {
		return ntramite;
	}

	public void setNtramite(String ntramite) {
		this.ntramite = ntramite;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCdtipflu() {
		return cdtipflu;
	}

	public void setCdtipflu(String cdtipflu) {
		this.cdtipflu = cdtipflu;
	}

	public String getCdflujomc() {
		return cdflujomc;
	}

	public void setCdflujomc(String cdflujomc) {
		this.cdflujomc = cdflujomc;
	}

	public String getWebid() {
		return webid;
	}

	public void setWebid(String webid) {
		this.webid = webid;
	}

	public String getTipoent() {
		return tipoent;
	}

	public void setTipoent(String tipoent) {
		this.tipoent = tipoent;
	}

	public String getCdunieco() {
		return cdunieco;
	}

	public void setCdunieco(String cdunieco) {
		this.cdunieco = cdunieco;
	}

	public String getCdramo() {
		return cdramo;
	}

	public void setCdramo(String cdramo) {
		this.cdramo = cdramo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getNmpoliza() {
		return nmpoliza;
	}

	public void setNmpoliza(String nmpoliza) {
		this.nmpoliza = nmpoliza;
	}

	public String getNmsituac() {
		return nmsituac;
	}

	public void setNmsituac(String nmsituac) {
		this.nmsituac = nmsituac;
	}

	public String getNmsuplem() {
		return nmsuplem;
	}

	public void setNmsuplem(String nmsuplem) {
		this.nmsuplem = nmsuplem;
	}

	public String getClaveent() {
		return claveent;
	}

	public void setClaveent(String claveent) {
		this.claveent = claveent;
	}
	
	public String getAux() {
		return aux;
	}

	public void setAux(String aux) {
		this.aux = aux;
	}

	@Override
	public String toString()
	{
		return Utils.log(
				"\nntramite="   , this.ntramite
				,"\nstatus="    , this.status
				,"\ncdtipflu="  , this.cdtipflu
				,"\ncdflujomc=" , this.cdflujomc
				,"\nwebid="     , this.webid
				,"\ntipoent="   , this.tipoent
				,"\nclaveent="  , this.claveent
				,"\ncdunieco="  , this.cdunieco
				,"\ncdramo="    , this.cdramo
				,"\nestado="    , this.estado
				,"\nnmpoliza="  , this.nmpoliza
				,"\nnmsituac="  , this.nmsituac
				,"\nnmsuplem="  , this.nmsuplem
				,"\naux="       , this.aux
				);
	}
}