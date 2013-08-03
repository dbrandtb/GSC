package mx.com.aon.portal.model;

/**
 * VO que representa cada rama en un control TreeView de ExtJS
 * 
 * @author Cima
 *
 */
public class TreeViewVO {
	private String parent;
	private String type;
	private boolean singleClickExpanded;
	private String text;
	private boolean collapsible;
	private boolean leaf;
	private String id;
	private String cls;
	private String qtip;
	
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isSingleClickExpanded() {
		return singleClickExpanded;
	}
	public void setSingleClickExpanded(boolean singleClickExpanded) {
		this.singleClickExpanded = singleClickExpanded;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public boolean isCollapsible() {
		return collapsible;
	}
	public void setCollapsible(boolean collapsible) {
		this.collapsible = collapsible;
	}
	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCls() {
		return cls;
	}
	public void setCls(String cls) {
		this.cls = cls;
	}
	public String getQtip() {
		return qtip;
	}
	public void setQtip(String qtip) {
		this.qtip = qtip;
	}
}