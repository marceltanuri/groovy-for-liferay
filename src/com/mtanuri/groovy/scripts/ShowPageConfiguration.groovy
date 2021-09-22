package com.mtanuri.groovy.scripts

import com.liferay.portal.kernel.model.Layout
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil
import com.liferay.portal.kernel.util.UnicodeProperties

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

Layout layout = LayoutLocalServiceUtil.fetchLayoutByFriendlyURL(groupId, privateLayout, parentFriendlyURL)

if(includeParent) {
	getAllPreferences(layout);
}

if (includeChild) {
	def childrenPages = layout.getChildren();
	childrenPages.each { child ->
		getAllPreferences(child);
	}
}

def getAllPreferences(Layout layout) {
	try {
		UnicodeProperties up = layout.getTypeSettingsProperties()

		println("Page URL: " + layout.getFriendlyURL())
		println("Plid: " + layout.getPlid())

		println("Page Settings:")
		println(up)

		println("Theme ID: " + layout.getThemeId())

		println("===========================================")
	} catch (Exception e) {
		println(e)
	}
}
