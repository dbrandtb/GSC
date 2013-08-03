package mx.com.aon.catbo.model;

import java.util.List;

public class TreeViewVO {
	
	private String parent;
	private String type;
	private boolean singleClickExpanded;
	private String text;
	private boolean collapsible;
	private boolean leaf;
	private String id;
	private String cls;
	
	private String cdUniEco;
	private String cdProceso;
	private String cdRamo;
	private String cdGuion;
	private String cdElemento;
	
	//Lista que contiene todos los nodos hijos para este nodo
	private List<TreeViewVO> children;
	
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

	public void setId(String id) {
		this.id = id;
	}
	public String getCls() {
		return cls;
	}
	public void setCls(String cls) {
		this.cls = cls;
	}
	public List<TreeViewVO> getChildren() {
		return children;
	}
	public void setChildren(List<TreeViewVO> children) {
		this.children = children;
	}
	public String getId() {
		return id;
	}
	public String getCdUniEco() {
		return cdUniEco;
	}
	public void setCdUniEco(String cdUniEco) {
		this.cdUniEco = cdUniEco;
	}
	public String getCdProceso() {
		return cdProceso;
	}
	public void setCdProceso(String cdProceso) {
		this.cdProceso = cdProceso;
	}
	public String getCdRamo() {
		return cdRamo;
	}
	public void setCdRamo(String cdRamo) {
		this.cdRamo = cdRamo;
	}
	public String getCdGuion() {
		return cdGuion;
	}
	public void setCdGuion(String cdGuion) {
		this.cdGuion = cdGuion;
	}
	public String getCdElemento() {
		return cdElemento;
	}
	public void setCdElemento(String cdElemento) {
		this.cdElemento = cdElemento;
	}
}
