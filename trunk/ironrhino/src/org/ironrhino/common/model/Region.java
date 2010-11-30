package org.ironrhino.common.model;

import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableProperty;
import org.ironrhino.common.util.RegionUtils;
import org.ironrhino.core.metadata.AutoConfig;
import org.ironrhino.core.metadata.NotInJson;
import org.ironrhino.core.metadata.PublishAware;
import org.ironrhino.core.model.BaseTreeableEntity;

@PublishAware
@AutoConfig
@Searchable(alias = "region")
public class Region extends BaseTreeableEntity<Region> {

	private static final long serialVersionUID = 8878381261391688086L;

	private Double latitude;

	private Double longitude;

	public Region() {

	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Region(String name) {
		this.name = name;
	}

	public Region(String name, int displayOrder) {
		this.name = name;
		this.displayOrder = displayOrder;
	}

	String fullname = null;

	@Override
	@SearchableProperty(boost = 2)
	public String getFullname() {
		if (fullname == null)
			fullname = super.getFullname();
		return fullname;
	}

	String shortFullname;

	@NotInJson
	public String getShortFullname() {
		if (shortFullname == null)
			shortFullname = RegionUtils.shortenAddress(getFullname());
		return shortFullname;
	}

	@Override
	public String toString() {
		return getFullname();
	}

}
