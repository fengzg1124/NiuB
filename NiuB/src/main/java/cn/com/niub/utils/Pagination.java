package cn.com.niub.utils;

import java.util.List;

/**
 * Mybatis - 分页对象
 *
 * @author liuzh/abel533/isea533
 * @version 3.6.0
 *          项目地址 : http://git.oschina.net/free/Mybatis_PageHelper
 */
public class Pagination<E> {

	/**
	 * 页码，从1开始
	 */
	private int pageNum;
	/**
	 * 页面大小
	 */
	private int pageSize;
	/**
	 * 起始行
	 */
	private int startRow;
	/**
	 * 末行
	 */
	private int endRow;
	/**
	 * 总数
	 */
	private long total;
	/**
	 * 总页数
	 */
	private int pages;
	//结果集
	private List<E> result;

	public Pagination() {
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getStartRow() {
		return startRow;
	}
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}


	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public int getEndRow() {
		return endRow;
	}
	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public List<E> getResult() {
		return result;
	}

	public void setResult(List<E> result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "Page{" +
				", pageNum=" + pageNum +
				", pageSize=" + pageSize +
				", startRow=" + startRow +
				", endRow=" + endRow +
				", total=" + total +
				", pages=" + pages +
				'}';
	}
}
