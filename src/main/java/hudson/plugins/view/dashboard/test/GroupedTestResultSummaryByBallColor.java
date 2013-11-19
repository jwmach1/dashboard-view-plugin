package hudson.plugins.view.dashboard.test;

import hudson.model.BallColor;

public class GroupedTestResultSummaryByBallColor extends GroupedTestResultSummary<BallColor>{

	@Override
	protected BallColor buildKey(TestResult testResult) {
		return testResult.getJob().getIconColor();
	}

}
