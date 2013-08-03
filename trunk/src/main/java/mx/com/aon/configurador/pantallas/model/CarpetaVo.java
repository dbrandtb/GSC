package mx.com.aon.configurador.pantallas.model;

import java.io.Serializable;

/**
 * 
 *  Clase Value Object utilizada para la generacion las ramas de un Tree
 * 
 * @author  aurora.lozada
 * 
 */

public class CarpetaVo implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6442799702733781547L;

    private String id;
    private String text;
    private boolean leaf = false;
    private String cls = "file";
    private String href;
    private Object[] children;
    private boolean expanded;
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
     * @return the children
     */
    public Object[] getChildren() {
        return children;
    }
    /**
     * @param children the children to set
     */
    public void setChildren(Object[] children) {
        this.children = children;
    }
    /**
     * @return the expanded
     */
    public boolean getExpanded() {
        return expanded;
    }
    /**
     * @param expanded the expanded to set
     */
    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
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
    
}
