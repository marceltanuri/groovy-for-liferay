package com.mtanuri.groovy.scripts

import com.liferay.portal.kernel.backgroundtask.BackgroundTask

Calendar pastDate = Calendar.getInstance();
pastDate.setTime(new Date());
pastDate.add(Calendar.MONTH, -10);
dateFilter = pastDate.getTime();

className = "com.liferay.portal.lar.backgroundtask.StagingIndexingBackgroundTaskExecutor";

try {
	bgTasks = com.liferay.portal.service.BackgroundTaskLocalServiceUtil.getBackgroundTasks(-1,-1);
	for(BackgroundTask bgTask : bgTasks) {
		if(bgTask.getCompletionDate().before(dateFilter) && bgTask.getTaskExecutorClassName().equals(className)){
			com.liferay.portal.service.BackgroundTaskLocalServiceUtil.deleteBackgroundTask(bgTask.getBackgroundTaskId());
			println bgTask.getBackgroundTaskId() + " has been deleted from your database.";
		}
	}
} catch(Exception e) {
	println e;
}
