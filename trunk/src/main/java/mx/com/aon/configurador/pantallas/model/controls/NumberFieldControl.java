/**
 * 
 */
package mx.com.aon.configurador.pantallas.model.controls;

/**
 * @author eflores
 * @date 18/06/2008
 *
 */
@Deprecated
public class NumberFieldControl extends AbstractMasterModelControl {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String anchor;
    private String labelSeparator;
    private boolean hidden;
    private boolean hiddeParent;
    
    /**
     * @return the anchor
     */
    public String getAnchor() {
        return anchor;
    }
    /**
     * @param anchor the anchor to set
     */
    public void setAnchor(String anchor) {
        this.anchor = anchor;
    }
    /**
     * @return the hidden
     */
    public boolean isHidden() {
        return hidden;
    }
    /**
     * @param hidden the hidden to set
     */
    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
    /**
     * @return the hiddeParent
     */
    public boolean isHiddeParent() {
        return hiddeParent;
    }
    /**
     * @param hiddeParent the hiddeParent to set
     */
    public void setHiddeParent(boolean hiddeParent) {
        this.hiddeParent = hiddeParent;
    }
    /**
     * @return the labelSeparator
     */
    public String getLabelSeparator() {
        return labelSeparator;
    }
    /**
     * @param labelSeparator the labelSeparator to set
     */
    public void setLabelSeparator(String labelSeparator) {
        this.labelSeparator = labelSeparator;
    }
}
