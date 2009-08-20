package org.ironrhino.common.action;

import java.util.Collection;

import org.apache.struts2.ServletActionContext;
import org.ironrhino.common.model.Region;
import org.ironrhino.common.support.RegionTreeControl;
import org.ironrhino.common.util.HtmlUtils;
import org.ironrhino.core.ext.struts.BaseAction;
import org.ironrhino.core.metadata.AutoConfig;
import org.ironrhino.core.metadata.JsonConfig;

@AutoConfig(namespace = "/", actionName = "region")
public class RegionTreeAction extends BaseAction {

	private static final long serialVersionUID = -1333891551369466096L;

	private Collection<Region> children;

	private transient RegionTreeControl regionTreeControl;

	private boolean async = true;

	private int root;

	public int getRoot() {
		return root;
	}

	public void setRoot(int root) {
		this.root = root;
	}

	public boolean isAsync() {
		return async;
	}

	public void setAsync(boolean async) {
		this.async = async;
	}

	public Collection<Region> getChildren() {
		return children;
	}

	public void setRegionTreeControl(RegionTreeControl regionTreeControl) {
		this.regionTreeControl = regionTreeControl;
	}

	@JsonConfig(root = "children")
	public String children() {
		Region region;
		if (root < 1)
			region = regionTreeControl.getRegionTree();
		else
			region = regionTreeControl.getRegionTree().getDescendantOrSelfById(
					root);
		children = region.getChildren();
		ServletActionContext.getResponse().setHeader("Cache-Control",
				"max-age=86400");
		return JSON;
	}

	@Override
	public String execute() {
		if (!async) {
			Region region;
			if (root < 1)
				region = regionTreeControl.getRegionTree();
			else
				region = regionTreeControl.getRegionTree()
						.getDescendantOrSelfById(root);
			children = region.getChildren();
		}
		return SUCCESS;
	}

	public String table() {
		return "table";
	}

	public Region getRegionTree() {
		return regionTreeControl.getRegionTree();
	}

	public String getTreeViewHtml() {
		return HtmlUtils.getTreeViewHtml(children, async);
	}

}
