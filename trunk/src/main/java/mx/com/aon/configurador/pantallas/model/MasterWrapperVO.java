package mx.com.aon.configurador.pantallas.model;

import java.io.Serializable;
import java.util.List;

@Deprecated
public class MasterWrapperVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 784176548204219744L;
	
	private List<SectionVo> sectionList;

	/**
	 * @return the sectionList
	 */
	public List<SectionVo> getSectionList() {
		return sectionList;
	}

	/**
	 * @param sectionList the sectionList to set
	 */
	public void setSectionList(List<SectionVo> sectionList) {
		this.sectionList = sectionList;
	}
	
	
	
	
	
	

}
