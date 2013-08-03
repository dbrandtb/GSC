/**
 * 
 */
package mx.com.aon.portal.model;

/**
 * @author CIMA_USR
 *
 */
public class AtributoVaribleAgenteVO {

  private String cdAtribu;
  private String dsAtribu;
  private String swFormat;
  private String nmlMax;
  private String nmlMin;
  private String swObliga;
  private String otTabVal;  
  private String gbSwFormat;
  
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
public String getNmlMax() {
	return nmlMax;
}
public void setNmlMax(String nmlMax) {
	this.nmlMax = nmlMax;
}
public String getNmlMin() {
	return nmlMin;
}
public void setNmlMin(String nmlMin) {
	this.nmlMin = nmlMin;
}
public String getSwObliga() {
	return swObliga;
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

public void _setSwObliga(String swObliga) {
	this.swObliga = swObliga;
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
                         



}
