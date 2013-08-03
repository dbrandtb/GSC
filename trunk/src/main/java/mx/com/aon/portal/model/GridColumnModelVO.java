package mx.com.aon.portal.model;

import net.sf.json.JSONObject;

public class GridColumnModelVO {
	private String header;
	private String tooltip;
	private String dataIndex;
	private boolean hidden;
	private boolean sortable;
	private String nomColumn;

	public String toString () {
		String jsonObject = JSONObject.fromObject(this).toString();
		
		return jsonObject;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getTooltip() {
		return tooltip;
	}
	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}
	public String getDataIndex() {
		return dataIndex;
	}
	public void setDataIndex(String dataIndex) {
		this.dataIndex = dataIndex;
	}
	public boolean isHidden() {
		return hidden;
	}
	public void setHidden(String hidden) {
		if (hidden.equals("S")) this.hidden = false;
		else this.hidden = true;
	}
	public boolean isSortable() {
		return sortable;
	}
	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}
	public String getNomColumn() {
		return nomColumn;
	}
	public void setNomColumn(String nomColumn) {
		this.nomColumn = nomColumn;
	}
}
