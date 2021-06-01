/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.webservices.rest.web.v1_0.resource.openmrs1_8;

import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.resource.impl.BaseDelegatingResourceTest;
import org.openmrs.module.webservices.rest.web.v1_0.wrapper.openmrs1_8.ModuleAdministrationLinks1_8;

import java.util.ArrayList;
import java.util.List;

public class AdministrationLinksResource1_8Test
		extends BaseDelegatingResourceTest<AdministrationLinksResource1_8, ModuleAdministrationLinks1_8> {

	@Override
	public ModuleAdministrationLinks1_8 newObject() {
		List<ModuleAdministrationLinks1_8.AdministrationLink> linksForModule = new ArrayList<ModuleAdministrationLinks1_8.AdministrationLink>();
		linksForModule.add(new ModuleAdministrationLinks1_8.AdministrationLink("module/webservices/rest/settings.form",
				RestConstants.MODULE_ID + ".manage.settings"));

		return new ModuleAdministrationLinks1_8(RestConstants.MODULE_ID, RestConstants.MODULE_ID + ".title",
				linksForModule);
	}

	@Override
	public String getDisplayProperty() {
		return RestConstants.MODULE_ID + ".title";
	}

	@Override
	public String getUuidProperty() {
		return RestConstants.MODULE_ID;
	}
}
