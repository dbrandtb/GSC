package mx.com.aon.catbo.service;
import java.util.List;

public class PagedList {

	@SuppressWarnings("unchecked")
	List itemsRangeList;
	int totalItems;
    String messageResult;

    @SuppressWarnings("unchecked")
	public List getItemsRangeList() {
		return itemsRangeList;
	}
	@SuppressWarnings("unchecked")
	public void setItemsRangeList(List itemsRangeList) {
		this.itemsRangeList = itemsRangeList;
	}


    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }


    public String getMessageResult() {
        return messageResult;
    }

    public void setMessageResult(String messageResult) {
        this.messageResult = messageResult;
    }
}
