package com.mtanuri.groovy.scripts

import org.apache.commons.lang.StringEscapeUtils;

out.println(StringEscapeUtils.escapeXml("cat /opt/liferay-portal-tomcat-6.2/config/tcp_ping_transport.xml".execute().text))

