package mx.com.aon.configurador.pantallas.model;


import java.io.Serializable;


/**
 * 
 *  Clase Value Object utilizada para la generacion del tree de pantallas
 * 
 * @author  aurora.lozada
 * 
 */
public class RamaPantallaVO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1122609732921431841L;

    
    private String id;
    private String text;
    private boolean leaf = false;
    private String cls;
    private String icon;
    private String iconCls;
    private String href;
   
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * @return the text
     */
    public String getText() {
        return text;
    }
    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }
    /**
     * @return the leaf
     */
    public boolean getLeaf() {
        return leaf;
    }
    /**
     * @param leaf the leaf to set
     */
    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }
    /**
     * @return the cls
     */
    public String getCls() {
        return cls;
    }
    /**
     * @param cls the cls to set
     */
    public void setCls(String cls) {
        this.cls = cls;
    }
   
    /**
     * @return the href
     */
    public String getHref() {
        return href;
    }
    /**
     * @param href the href to set
     */
    public void setHref(String href) {
        this.href = href;
    }
    /**
     * @return the iconCls
     */
    public String getIconCls() {
        return iconCls;
    }
    /**
     * @param iconCls the iconCls to set
     */
    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }
    /**
     * @return the icon
     */
    public String getIcon() {
        return icon;
    }
    /**
     * @param icon the icon to set
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }
    
   
    
    

}