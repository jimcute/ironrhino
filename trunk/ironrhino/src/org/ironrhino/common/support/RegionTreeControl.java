package org.ironrhino.common.support;

import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.commons.lang.StringUtils;
import org.ironrhino.common.model.Region;
import org.ironrhino.common.util.RegionUtils;
import org.ironrhino.core.event.EntityOperationEvent;
import org.ironrhino.core.event.EntityOperationType;
import org.ironrhino.core.service.BaseManager;
import org.ironrhino.core.util.BeanUtils;
import org.springframework.context.ApplicationListener;

@Singleton
@Named("regionTreeControl")
public class RegionTreeControl implements
		ApplicationListener<EntityOperationEvent> {

	private Region regionTree;

	@Inject
	private BaseManager<Region> baseManager;

	public void buildRegionTree() {
		baseManager.setEntityClass(Region.class);
		regionTree = baseManager.loadTree();
	}

	@PostConstruct
	public void afterPropertiesSet() throws Exception {
		buildRegionTree();
	}

	public Region getRegionTree() {
		return regionTree;
	}

	public Region parseByHost(String host) {
		return RegionUtils.parseByHost(host, regionTree);
	}

	public Region parseByAddress(String address) {
		if (StringUtils.isBlank(address))
			return null;
		return RegionUtils.parseByAddress(address, regionTree);
	}

	private void create(Region region) {
		Region parent;
		if (region.getParent() == null)
			parent = regionTree;
		else
			parent = regionTree.getDescendantOrSelfById(region.getParent()
					.getId());
		Region r = new Region();
		BeanUtils.copyProperties(region, r,
				new String[] { "parent", "children" });
		r.setParent(parent);
		parent.getChildren().add(r);
		if (parent.getChildren() instanceof List)
			Collections.sort((List<Region>) parent.getChildren());
	}

	private void update(Region region) {
		Region r = regionTree.getDescendantOrSelfById(region.getId());
		if (!r.getFullId().equals(region.getFullId())) {
			r.getParent().getChildren().remove(r);
			Region newParent;
			if (region.getParent() == null)
				newParent = regionTree;
			else
				newParent = regionTree.getDescendantOrSelfById(region
						.getParent().getId());
			r.setParent(newParent);
			newParent.getChildren().add(r);
		}
		BeanUtils.copyProperties(region, r,
				new String[] { "parent", "children" });
		if (r.getParent().getChildren() instanceof List)
			Collections.sort((List<Region>) r.getParent().getChildren());
	}

	private void delete(Region region) {
		Region r = regionTree.getDescendantOrSelfById(region.getId());
		r.getParent().getChildren().remove(r);
	}

	public void onApplicationEvent(EntityOperationEvent event) {
		if (regionTree == null)
			return;
		if (event.getEntity() instanceof Region) {
			Region region = (Region) event.getEntity();
			if (event.getType() == EntityOperationType.CREATE)
				create(region);
			else if (event.getType() == EntityOperationType.UPDATE)
				update(region);
			else if (event.getType() == EntityOperationType.DELETE)
				delete(region);
		}
	}
}
