/**
 * 
 */
package mx.com.aon.flujos.endoso.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author Alejandro Garcia
 * @date 09/12/2008
 *
 */
public class TarificarVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String msgId;
    private String title;
    
    private String impAnt;
    private String impAct;
    private String impDif;
    
    /**
     * @return String
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

	public String getMsgID() {
		return msgId;
	}

	public void setMsgID(String msgID) {
		this.msgId = msgID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImpAnt() {
		return impAnt;
	}

	public void setImpAnt(String impAnt) {
		this.impAnt = impAnt;
	}

	public String getImpAct() {
		return impAct;
	}

	public void setImpAct(String impAct) {
		this.impAct = impAct;
	}

	public String getImpDif() {
		return impDif;
	}

	public void setImpDif(String impDif) {
		this.impDif = impDif;
	}

}
