package mx.com.aon.portal.service;

import java.util.List;

public interface GridPagerManager {

	public int getStart();

	public void setStart(int start);

	public int getLimit();

	public void setLimit(int limit);

	public int getTotalCount();

	public void setTotalCount(int totalCount);

	@SuppressWarnings("unchecked")
	public List getPagedDataList();

	@SuppressWarnings("unchecked")
	public void setPagedDataList(List pagedDataList);
}
