/**
 * 
 */
package mx.com.gseguros.portal.general.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author eflores
 * @date 26/05/2008
 *
 */
public class UsuarioVO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String cdUsuario;
    private String dsUsuario;
    private String dsNombre;
    private String dsNombre1;
    private String dsApellido;
    private String dsApellido1;
    private String otSexo;
    private String dSexo;
    private String feNacimi;
    private String cdrfc;
    private String curp;
    private String dsEmail;
    private String cdrol;
    private String esAgente;

    /**
     * @return the cdUsuario
     */
    public String getCdUsuario() {
        return cdUsuario;
    }

    /**
     * @param cdUsuario the cdUsuario to set
     */
    public void setCdUsuario(String cdUsuario) {
        this.cdUsuario = cdUsuario;
    }

    /**
     * @return the dsUsuario
     */
    public String getDsUsuario() {
        return dsUsuario;
    }

    /**
     * @param dsUsuario the dsUsuario to set
     */
    public void setDsUsuario(String dsUsuario) {
        this.dsUsuario = dsUsuario;
    }
    
    /**
     * @return String
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

	public String getDsNombre() {
		return dsNombre;
	}

	public void setDsNombre(String dsNombre) {
		this.dsNombre = dsNombre;
	}

	public String getDsNombre1() {
		return dsNombre1;
	}

	public void setDsNombre1(String dsNombre1) {
		this.dsNombre1 = dsNombre1;
	}

	public String getDsApellido() {
		return dsApellido;
	}

	public void setDsApellido(String dsApellido) {
		this.dsApellido = dsApellido;
	}

	public String getDsApellido1() {
		return dsApellido1;
	}

	public void setDsApellido1(String dsApellido1) {
		this.dsApellido1 = dsApellido1;
	}

	public String getOtSexo() {
		return otSexo;
	}

	public void setOtSexo(String otSexo) {
		this.otSexo = otSexo;
	}

	public String getdSexo() {
		return dSexo;
	}

	public void setdSexo(String dSexo) {
		this.dSexo = dSexo;
	}

	public String getFeNacimi() {
		return feNacimi;
	}

	public void setFeNacimi(String feNacimi) {
		this.feNacimi = feNacimi;
	}

	public String getCdrfc() {
		return cdrfc;
	}

	public void setCdrfc(String cdrfc) {
		this.cdrfc = cdrfc;
	}

	public String getDsEmail() {
		return dsEmail;
	}

	public void setDsEmail(String dsEmail) {
		this.dsEmail = dsEmail;
	}

	public String getCdrol() {
		return cdrol;
	}

	public void setCdrol(String cdrol) {
		this.cdrol = cdrol;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public String getEsAgente() {
		return esAgente;
	}

	public void setEsAgente(String esAgente) {
		this.esAgente = esAgente;
	}
}
