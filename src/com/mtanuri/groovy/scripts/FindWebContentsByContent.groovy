package com.mtanuri.groovy.scripts

import com.liferay.journal.service.JournalArticleLocalServiceUtil

def groupId = 20142
def ocurrency = "@gmail.com"

def getArticles = JournalArticleLocalServiceUtil.getArticles(groupId, -1, -1)

getArticles.each { val ->
	def indexOf = val.content.indexOf(ocurrency)
	if (indexOf>0) {
		println( val.articleId + ", " + val.version + ", " + val.content.substring(indexOf-10, indexOf+10))
	}
}
''