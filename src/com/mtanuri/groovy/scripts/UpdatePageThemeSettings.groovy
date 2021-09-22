package com.mtanuri.groovy.scripts

import com.liferay.portal.kernel.model.Layout
import com.liferay.portal.kernel.model.LayoutRevision
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil
import com.liferay.portal.kernel.service.LayoutRevisionLocalServiceUtil
import com.liferay.portal.kernel.util.UnicodeProperties
import com.liferay.portal.kernel.workflow.WorkflowConstants

import groovy.transform.Field

/**
 * @author marcel.tanuri
 *
 * Reveal portlets name and preferences in the given page and column
 *
 * @param groupId Group where script will run
 * @param privateLayout Type of page (private when true, public when false) that will processed
 * @param parentFriendlyUrl Friendly URL of the parent page. All children pages will be processed
 * @param column Column where the portlet will be inspect  
 * 
 */

@Field groupId = 35020
@Field debug = true
@Field includeParent = false
@Field includeChild = true
def privateLayout = false
def parentFriendlyURL = "/servicos"
def newThemeId = "eportugal_WAR_eportugaltheme"

Layout layout = LayoutLocalServiceUtil.fetchLayoutByFriendlyURL(groupId, privateLayout, parentFriendlyURL)

if(includeParent) {
	updateThemeId(layout, newThemeId);
}

if (includeChild) {
	def childrenPages = layout.getChildren();
	childrenPages.each { child ->
		updateThemeId(child, newThemeId);
	}
}

def updateThemeId(Layout layout, String newThemeId) {
	try {
		UnicodeProperties up = layout.getTypeSettingsProperties()

		println("Page URL: " + layout.getFriendlyURL())
		println("Plid: " + layout.getPlid())

		println("actual themeID: " + layout.getThemeId())

		layout.setThemeId(newThemeId)
		LayoutLocalServiceUtil.updateLookAndFeel(layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(), newThemeId, layout.getColorSchemeId(), layout.getCss())
		persistLayoutChanges(layout)

		Layout updatedLayout = LayoutLocalServiceUtil.fetchLayout(layout.getPlid())
		println("new themeID: " + updatedLayout.getThemeId())

		println("===========================================")
	} catch (Exception e) {
		println(e)
	}
}

def persistLayoutChanges(Layout layout) {
	LayoutRevision lastLayoutRevision = LayoutRevisionLocalServiceUtil.fetchLastLayoutRevision(layout.getPlid(),
			false)

	//update layout as ready to publish
	LayoutRevisionLocalServiceUtil.updateStatus(layout.getUserId(), lastLayoutRevision.getLayoutRevisionId(),
			WorkflowConstants.STATUS_APPROVED, null)
}
