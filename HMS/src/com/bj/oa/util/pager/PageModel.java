package com.bj.oa.util.pager;


/**
 * @Version 1.0
 *  分页实体 
 */
public class PageModel {
	
	
	/** 分页总数据条数  */
	private int recordCount;
	/** 当前页面 */
	private int pageIndex=1;
	/** 每页分多少条数据   */
	private int pageSize = 4;
	
	/** 总页数  */
	private int totalSize;

	public int getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}
	
	public int getPageIndex() {
		
		
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getPageSize() {

		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public int getTotalSize() {
		
		return totalSize;
	}
	
	public int getFirstLimitParam(){
		return (this.getPageIndex()-1)*this.getPageSize() ;
	}
	
}
