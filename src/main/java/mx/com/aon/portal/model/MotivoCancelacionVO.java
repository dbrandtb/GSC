/**
 * 
 */
package mx.com.aon.portal.model;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 * Clase VO usada para obtener una seccion.
 * 
 * @param cdRazon 
 * @param dsRazon 
 * @param swReInst 
 * @param swVerFec 
 * @param swVerPag
 */
public class MotivoCancelacionVO {

    private String cdRazon;
    private String dsRazon;
    private String swReInst;
    private String swVerFec;
    private String swVerPag;

    private List<RequisitoCancelacionVO> requisitoCancelacionVOs;

    /**
     * @return String
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    public String getCdRazon() {
        return cdRazon;
    }

    public void setCdRazon(String cdRazon) {
        this.cdRazon = cdRazon;
    }

    public String getDsRazon() {
        return dsRazon;
    }

    public void setDsRazon(String dsRazon) {
        this.dsRazon = dsRazon;
    }

    public String getSwReInst() {
        return swReInst;
    }

    public void setSwReInst(String swReInst) {
        this.swReInst = swReInst;
    }

    public String getSwVerFec() {
        return swVerFec;
    }

    public void setSwVerFec(String swVerFec) {
        this.swVerFec = swVerFec;
    }

    public String getSwVerPag() {
        return swVerPag;
    }

    public void setSwVerPag(String swVerPag) {
        this.swVerPag = swVerPag;
    }

    public List<RequisitoCancelacionVO> getRequisitoCancelacionVOs() {
        return requisitoCancelacionVOs;
    }

    public void setRequisitoCancelacionVOs(List<RequisitoCancelacionVO> requisitoCancelacionVOs) {
        this.requisitoCancelacionVOs = requisitoCancelacionVOs;
    }
}



