package mx.com.aon.portal.model;

public class AtributosVariablesPersonaVO {
	
    private String cdAtribu;
    private String dsAtribu;
    private String swFormat;
    private String nmlmin;
    private String nmlmax;
    private String otTabVal;
    private String gbSwFormat;
    private String cdFisJur;
    private String cdCatego;
    private String swObliga;
    
    
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
	public String getSwFormat() {
		return swFormat;
	}
	public void setSwFormat(String swFormat) {
		this.swFormat = swFormat;
	}

	public String getOtTabVal() {
		return otTabVal;
	}
	public void setOtTabVal(String otTabVal) {
		this.otTabVal = otTabVal;
	}
	public String getGbSwFormat() {
		return gbSwFormat;
	}
	public void setGbSwFormat(String gbSwFormat) {
		this.gbSwFormat = gbSwFormat;
	}
	public String getCdFisJur() {
		return cdFisJur;
	}
	public void setCdFisJur(String cdFisJur) {
		this.cdFisJur = cdFisJur;
	}
	public String getCdCatego() {
		return cdCatego;
	}
	public void setSwObliga(String swObliga) {
		if (swObliga.equals("S")) {
			this.swObliga = "true";
			return;
		};
		if (swObliga.equals("N")) {
			this.swObliga = "false";
			return;
		};
		this.swObliga = swObliga;
	}

	/*public void setSwObliga(String swObliga) {
		this.swObliga = swObliga;
	}*/
	public void _setSwObliga(String swObliga) {
		this.swObliga = swObliga;
	}
	public void setCdCatego(String cdCatego) {
		this.cdCatego = cdCatego;
	}
	public String getSwObliga() {
		return swObliga;
	}
	public String getNmlmin() {
		return nmlmin;
	}
	public void setNmlmin(String nmlmin) {
		this.nmlmin = nmlmin;
	}
	public String getNmlmax() {
		return nmlmax;
	}
	public void setNmlmax(String nmlmax) {
		this.nmlmax = nmlmax;
	}

}
