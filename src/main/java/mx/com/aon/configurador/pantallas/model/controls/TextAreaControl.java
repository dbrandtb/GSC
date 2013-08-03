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
public class TextAreaControl extends AbstractMasterModelControl {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private boolean allowBlank;
    private int width;
    private int height;
    private boolean grow;
    private boolean preventScrollbars;
    private boolean disabled;
    
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
     * @return the grow
     */
    public boolean isGrow() {
        return grow;
    }
    /**
     * @param grow the grow to set
     */
    public void setGrow(boolean grow) {
        this.grow = grow;
    }
    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }
    /**
     * @param height the height to set
     */
    public void setHeight(int height) {
        this.height = height;
    }
    /**
     * @return the preventScrollbars
     */
    public boolean isPreventScrollbars() {
        return preventScrollbars;
    }
    /**
     * @param preventScrollbars the preventScrollbars to set
     */
    public void setPreventScrollbars(boolean preventScrollbars) {
        this.preventScrollbars = preventScrollbars;
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
}
