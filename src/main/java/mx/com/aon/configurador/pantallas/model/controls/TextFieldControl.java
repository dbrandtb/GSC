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
public class TextFieldControl extends AbstractMasterModelControl {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3516618835068933610L;
	/**
     * 
     */
    

    private boolean allowBlank;   
    private int width;
    private String labelSeparator;
    private boolean hidden;
    private boolean hiddeParent;
    private boolean disabled;
//    private String type;
    
    
    public TextFieldControl() {
		xtype = TEX_FIELD_TYPE;
	}
    
    
    /**
     * @return the allowBlank
     */
    public boolean isAllowBlank() {
        return allowBlank;
    }
    /**
     * @param allowBlank the allowBlank to set
     */
    public void setAllowBlank(boolean allowBlank) {
        this.allowBlank = allowBlank;
    }
    /**
     * @return the disabled
     */
    public boolean isDisabled() {
        return disabled;
    }
    /**
     * @param disabled the disabled to set
     */
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
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
    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }
    /**
     * @param width the width to set
     */
    public void setWidth(int width) {
        this.width = width;
    }
//    /**
//     * @return the type
//     */
//    public String getType() {
//        return type;
//    }
//    /**
//     * @param type the type to set
//     */
//    public void setType(String type) {
//        this.type = type;
//    }
}
