/**
 * 
 */
package com.bj.oa.util.pager;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author 罗老师
 * @from 柠檬学院：http://www.bjlemon.com/
 * Version:1.0 
 * 2018年12月29日
 * 自定义标签处理类  ，通过该标签处理类  实现分页标签的业务逻辑处理
 */
public class MyPageTag extends TagSupport {

	//当前页码
	private int pageIndex;
	//每页显示的记录数
	private int pageSize;
	//总记录数
	private int totalNum;
	private String pageStyle = "digg";
	//提交地址
	private String submitUrl;//${pageContext.request.contextPath}/index.action?pageIndex={0}
	
	
	
	@Override
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		System.out.println("------doEndTag-----");
		return super.doEndTag();
	}

	/*
	 * 
	 */
	@Override
	public int doStartTag() throws JspException {
		// TODO Auto-generated method stub
		System.out.println("------doStartTag------");
		try {
			
			//创建 StringBuffer用于拼装页码相关信息
			StringBuffer pageDetail = new StringBuffer();
			
			//总记录数为0
			if(this.totalNum == 0) {
				
				pageDetail.append("<table class='"+pageStyle+"'><tr><td>总共<font color='red'>0</font>条记录,当前显示0-0条记录</td></tr></table>");
				
			}else {

				StringBuffer pager = new StringBuffer();
				
				//定义跳转地址
				String jumpUrl = "";
				//计算总页码数
				int totalPageNum = this.totalNum % this.pageSize == 0 ? this.totalNum / this.pageSize :  (this.totalNum / this.pageSize) + 1;
				
				//如果当前页码是第一页 
				if(this.pageIndex==1) {
					pager.append("<span class='disabled'>上一页</span>");
					
					
					//计算中间页码 
					calcMiddlePage(pager,totalPageNum);
					
					//拼装下一页   如果总共只有一页   那么下一页也不能点击  否则下一页可以点击
					if(totalPageNum == this.pageIndex) {
						//总共就一页  下一页 也不能点击
						pager.append("<span class='disabled'>下一页</span>");
					}else {
						//${pageContext.request.contextPath}/index.action?pageIndex={0}  == >${pageContext.request.contextPath}/index.action?pageIndex=3
						jumpUrl = this.submitUrl.replace("{0}", String.valueOf(this.pageIndex + 1));
						pager.append("<a href='"+jumpUrl+"'>下一页</a>");
					}
				//当前页码是尾页	
				}else if(this.pageIndex == totalPageNum) {
					
					jumpUrl = this.submitUrl.replace("{0}", String.valueOf(this.pageIndex - 1));
					pager.append("<a href='"+jumpUrl+"'>上一页</a>");	
					
					
					//计算中间页码 
					calcMiddlePage(pager,totalPageNum);
					
					//拼装尾页
					pager.append("<span class='disabled'>下一页</span>");
				
					
					//当前页码既不是首页也是不尾页
				}else {
					jumpUrl = this.submitUrl.replace("{0}", String.valueOf(this.pageIndex - 1));
					pager.append("<a href='"+jumpUrl+"'>上一页</a>");	
					
					
					//计算中间页码 
					calcMiddlePage(pager,totalPageNum);
					
					
					jumpUrl = this.submitUrl.replace("{0}", String.valueOf(this.pageIndex + 1));
					pager.append("<a href='"+jumpUrl+"'>下一页</a>");
				}
				
				//计算开始行号  以及  结束行号
				int startSize = (this.pageIndex - 1) * this.pageSize + 1;
				
				int endSize = this.pageIndex == totalPageNum ? this.totalNum : this.pageIndex * this.pageSize;
				
				pageDetail.append("<table class='"+pageStyle+"' style='width:100%;align:center;'><tr><td>"+pager.toString()+"</td></tr><tr><td>总共<font color='red'>"+this.totalNum+"</font>条记录,当前显示"+startSize+"-"+endSize+"条记录</td></tr></table>");

				
			}

			JspWriter writer = this.pageContext.getOut();
			
			writer.write(pageDetail.toString());
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return super.doStartTag();
	}

	/**
	 * 计算中间页码
	 */
	private void calcMiddlePage(StringBuffer pager, int totalPageNum) {
		// TODO Auto-generated method stub
		
		//定义跳转地址
		String jumpUrl = "";
		//如果总页码数小于等于10则全部显示   1 2 3 4 5 6 7 8 9 10
		if(totalPageNum <= 10) {
			
			for(int i = 1;i<= totalPageNum;i++) {
				if(i == this.pageIndex) {
					pager.append("<span class='current'>"+i+"</span>");
				}else {
					jumpUrl = this.submitUrl.replace("{0}", String.valueOf(i));
					pager.append("<a href='"+jumpUrl+"'>"+i+"</a>");
				}
			}
		}else {
			
			//如果当前页码靠近首页的时候  1 2 3 4 5 6 7 8 9  ...  100
			if(this.pageIndex <=8 ) {
				for(int i=1;i<=9;i++) {
					if(i == this.pageIndex) {
						pager.append("<span class='current'>"+i+"</span>");
					}else {
						jumpUrl = this.submitUrl.replace("{0}", String.valueOf(i));
						pager.append("<a href='"+jumpUrl+"'>"+i+"</a>");
					}
				}
				
				//拼装 ... 
				pager.append("...");
				
				//拼装尾页
				jumpUrl = this.submitUrl.replace("{0}", String.valueOf(totalPageNum));
				pager.append("<a href='"+jumpUrl+"'>"+totalPageNum+"</a>");
			}else if(this.pageIndex+8 >= totalPageNum) {
				//如果当前页码靠近尾页的时候  1 ... 91 92 93 94 95 96 97 98 99 100
				jumpUrl = this.submitUrl.replace("{0}", String.valueOf(1));
				pager.append("<a href='"+jumpUrl+"'>"+1+"</a>");
				
				//拼装 ... 
				pager.append("...");
				
				
				for(int i=totalPageNum - 9;i<=totalPageNum;i++) {
					if(i == this.pageIndex) {
						pager.append("<span class='current'>"+i+"</span>");
					}else {
						jumpUrl = this.submitUrl.replace("{0}", String.valueOf(i));
						pager.append("<a href='"+jumpUrl+"'>"+i+"</a>");
					}
				}
			}else {
				//当前页码靠近中间   1 ... 41 42 43 44 45 46 47 48 49 ... 100
				//如果当前页码靠近尾页的时候  1 ... 91 92 93 94 95 96 97 98 99 100
				jumpUrl = this.submitUrl.replace("{0}", String.valueOf(1));
				pager.append("<a href='"+jumpUrl+"'>"+1+"</a>");
				
				//拼装 ... 
				pager.append("...");
				
				
				for(int i=this.pageIndex - 4;i<= this.pageIndex+ 4;i++) {
					if(i == this.pageIndex) {
						pager.append("<span class='current'>"+i+"</span>");
					}else {
						jumpUrl = this.submitUrl.replace("{0}", String.valueOf(i));
						pager.append("<a href='"+jumpUrl+"'>"+i+"</a>");
					}
				}
				
				//拼装 ... 
				pager.append("...");
				
				//拼装尾页
				jumpUrl = this.submitUrl.replace("{0}", String.valueOf(totalPageNum));
				pager.append("<a href='"+jumpUrl+"'>"+totalPageNum+"</a>");
				
			}
			
			
			
		}
		
		
	}

	/**
	 * @return the pageIndex
	 */
	public int getPageIndex() {
		return pageIndex;
	}

	/**
	 * @param pageIndex the pageIndex to set
	 */
	public void setPageIndex(int pageIndex) {
		System.out.println("pageIndex:"+pageIndex);
		if(pageIndex == 0) {
			pageIndex = 1;
		}
		this.pageIndex = pageIndex;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		System.out.println("pageSize:"+pageSize);

		this.pageSize = pageSize;
	}

	/**
	 * @return the totalNum
	 */
	public int getTotalNum() {
		return totalNum;
	}

	/**
	 * @param totalNum the totalNum to set
	 */
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	/**
	 * @return the submitUrl
	 */
	public String getSubmitUrl() {
		return submitUrl;
	}

	/**
	 * @param submitUrl the submitUrl to set
	 */
	public void setSubmitUrl(String submitUrl) {
		this.submitUrl = submitUrl;
	}

	/**
	 * @return the pageStyle
	 */
	public String getPageStyle() {
		return pageStyle;
	}

	/**
	 * @param pageStyle the pageStyle to set
	 */
	public void setPageStyle(String pageStyle) {
		this.pageStyle = pageStyle;
	}

	
	    
}
