package com.crawler.webapp.util.tree;

import java.util.List;

/**
 * 生成树数据的接收类
 *
 */
public class EntityTree implements ITree<EntityTree> {

	private String id;
	
	private String parentId;
	
	private String label;
	
	private List<EntityTree> children;

	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public List<EntityTree> getChildren() {
		return children;
	}

	@Override
	public void setChildren(List<EntityTree> childList) {
		this.children = childList;
	}
}
