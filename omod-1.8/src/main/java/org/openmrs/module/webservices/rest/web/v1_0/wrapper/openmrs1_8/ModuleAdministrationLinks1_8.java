/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.webservices.rest.web.v1_0.wrapper.openmrs1_8;

import java.io.Serializable;
import java.util.List;

/**
 * This class represents list of administration links for a module.
 * These links are provided by AdministrationSectionExt class, extended by appropriate classes in installed modules.
 * Exposed by AdministrationLinksResource1_8 class.
 *
 * @see org.openmrs.module.web.extension.AdministrationSectionExt
 * @see org.openmrs.module.webservices.rest.web.v1_0.resource.openmrs1_8.AdministrationLinksResource1_8
 */
public class ModuleAdministrationLinks1_8 implements Serializable {

	public static final long serialVersionUID = 1L;

	private final String moduleId;

	private final String moduleTitle;

	private final List<AdministrationLink> administrationLinks;

	public ModuleAdministrationLinks1_8(String moduleId, String moduleTitle, List<AdministrationLink> administrationLinks) {
		this.moduleId = moduleId;
		this.moduleTitle = moduleTitle;
		this.administrationLinks = administrationLinks;
	}

	public String getModuleId() {
		return moduleId;
	}

	public String getModuleTitle() {
		return moduleTitle;
	}

	public List<AdministrationLink> getAdministrationLinks() {
		return administrationLinks;
	}

	public static class AdministrationLink implements Serializable {

		public static final long serialVersionUID = 1L;

		private final String url;

		private final String title;

		public AdministrationLink(String url, String title) {
			this.url = url;
			this.title = title;
		}

		public String getUrl() {
			return url;
		}

		public String getTitle() {
			return title;
		}
	}
}


