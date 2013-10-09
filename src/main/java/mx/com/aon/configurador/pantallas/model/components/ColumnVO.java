package mx.com.aon.configurador.pantallas.model.components;

import java.io.Serializable;

public class ColumnVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5313129052591923828L;

	/**
	 * 
	 */
	private String align;
	
	/**
	 * 
	 */
	private String css;
	
	/**
	 * 
	 */
	private String dataIndex;
	
	/**
	 * 
	 */
	private boolean fixed;
	
	/**
	 * 
	 */
	private String header;

	/**
	 * 
	 */
	private boolean hidden;
	
	/**
	 * 
	 */
	private boolean hideable;
	
	/**
	 * 
	 */
	private String id;
	
	/**
	 * 
	 */
	private boolean menuDisabled;
	
	/**
	 * 
	 */
	private boolean resizable;
	
	/**
	 * 
	 */
	private Boolean sortable;
	
	/**
	 * 
	 */
	private String tooltip;
	
	/**
	 * 
	 */
	private int width;
	
	
	//Constructors
	
	public ColumnVO(){
		
	}
	
	public ColumnVO(String align, String css, String dataIndex, boolean fixed, 
			String header, boolean hidden, boolean hideable,  String id, 
			boolean menuDisabled, boolean resizable, 
			Boolean sortable, String tooltip, int width){
		this.align = align;
		this.css = css;
		this.dataIndex = dataIndex;
		this.fixed = fixed;
		this.header = header;
		this.hidden = hidden;
		this.hideable = hideable;
		this.id = id;
		this.menuDisabled = menuDisabled;
		this.resizable = resizable;
		this.sortable = sortable;
		this.tooltip = tooltip;
		this.width = width;
	}
	
	
	//Getters and setters

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}

	public String getDataIndex() {
		return dataIndex;
	}

	public void setDataIndex(String dataIndex) {
		this.dataIndex = dataIndex;
	}

	public boolean isFixed() {
		return fixed;
	}

	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public boolean isHideable() {
		return hideable;
	}

	public void setHideable(boolean hideable) {
		this.hideable = hideable;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isMenuDisabled() {
		return menuDisabled;
	}

	public void setMenuDisabled(boolean menuDisabled) {
		this.menuDisabled = menuDisabled;
	}

	public boolean isResizable() {
		return resizable;
	}

	public void setResizable(boolean resizable) {
		this.resizable = resizable;
	}

	

	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public Boolean getSortable() {
		return sortable;
	}

	public void setSortable(Boolean sortable) {
		this.sortable = sortable;
	}

}