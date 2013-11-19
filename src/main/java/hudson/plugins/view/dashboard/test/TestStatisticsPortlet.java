package hudson.plugins.view.dashboard.test;

import hudson.Extension;
import hudson.maven.reporters.SurefireAggregatedReport;
import hudson.model.Descriptor;
import hudson.model.Job;
import hudson.model.TopLevelItem;
import hudson.plugins.view.dashboard.DashboardPortlet;
import hudson.plugins.view.dashboard.test.CategorizationType.TestStatisticCategorization;

import java.util.Collection;
import java.util.List;

import net.sf.json.JSONObject;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;

import hudson.plugins.view.dashboard.Messages;
import hudson.tasks.test.AbstractTestResultAction;
import hudson.tasks.test.TestResultProjectAction;

import java.text.DecimalFormat;

/**
 * Portlet that presents a grid of test result data with summation
 * 
 * @author Peter Hayes
 */
public class TestStatisticsPortlet extends DashboardPortlet {
	private boolean useBackgroundColors;
	private String skippedColor;
	private String successColor;
	private String failureColor;
	private CategorizationType categorizationType;

	@DataBoundConstructor
	public TestStatisticsPortlet(String name, String successColor, String failureColor, String skippedColor, boolean useBackgroundColors) {
		super(name);
		this.successColor = successColor;
		this.failureColor = failureColor;
		this.skippedColor = skippedColor;
		this.useBackgroundColors = useBackgroundColors;
	}

	public GroupedTestResultSummary<?> getTestResultSummary(Collection<TopLevelItem> jobs) {
		GroupedTestResultSummary<?> summary = null;
		switch(categorizationType.getTestStatisticCategorization()) {
		case BALL_COLOR:
			summary = new GroupedTestResultSummaryByBallColor();
			break;
		case JOB_NAME:
			summary = new GroupedTestResultSummaryByJobName((JobNameCategorizationType) categorizationType);
			break;
		}
		for (TopLevelItem item : jobs) {
	           if (item instanceof Job) {
	                Job job = (Job) item;
	                boolean addBlank = true;
	                TestResultProjectAction testResults = job.getAction(TestResultProjectAction.class);

	                if (testResults != null) {
	                    AbstractTestResultAction tra = testResults.getLastTestResultAction();

	                    if (tra != null) {
	                       addBlank = false;
	                       summary.addTestResult(new TestResult(job, tra.getTotalCount(), tra.getFailCount(), tra.getSkipCount()));
	                    }
	                } else {
	                    SurefireAggregatedReport surefireTestResults = job.getAction(SurefireAggregatedReport.class);
	                    if (surefireTestResults != null) {
	                       addBlank = false;
	                       summary.addTestResult(new TestResult(job, surefireTestResults.getTotalCount(), surefireTestResults.getFailCount(), surefireTestResults.getSkipCount()));
	                    }
	                }

	                if (addBlank) {
	                    summary.addTestResult(new TestResult(job, 0, 0, 0));
	                }
	           }
	      }

		TestUtil.getTestResultSummary(jobs);
		return summary;
	}

	public String format(DecimalFormat df, double val) {
		if (val < 1d && val > .99d) {
			return "<100%";
		}
		if (val > 0d && val < .01d) {
			return ">0%";
		}
		return df.format(val);
	}
	
	public boolean isUseBackgroundColors() {
		return useBackgroundColors;
	}

	public String getSuccessColor() {
		return successColor;
	}

	public String getFailureColor() {
		return failureColor;
	}

	public String getSkippedColor() {
		return skippedColor;
	}
	
	public String getRowColor(TestResult testResult) {
		return testResult.success == testResult.tests ? successColor : failureColor;
	}
	
	public String getTotalRowColor(List<TestResult> testResults) {
		for(TestResult testResult : testResults) {
			if(testResult.success != testResult.tests) {
				return failureColor;
			}
		}
		return successColor;
	}
		
	public CategorizationType getCategorizationType() {
		return categorizationType;
	}
	
	public void setCategorizationType(CategorizationType categorizationType) {
		this.categorizationType = categorizationType;
	}
	
	public boolean isCategorizationTypeJobName() {
		return TestStatisticCategorization.JOB_NAME.equals(categorizationType.getTestStatisticCategorization());
	}
	
	public boolean isCategorizationTypeBallColor() {
		return TestStatisticCategorization.BALL_COLOR.equals(categorizationType.getTestStatisticCategorization());
	}

	@Extension
	public static class DescriptorImpl extends Descriptor<DashboardPortlet> {

		@Override
		public String getDisplayName() {
			return Messages.Dashboard_TestStatisticsGrid();
		}
		@Override
		public DashboardPortlet newInstance(StaplerRequest req,
				JSONObject formData)
				throws hudson.model.Descriptor.FormException {
			TestStatisticsPortlet newInstance = (TestStatisticsPortlet) super.newInstance(req, formData);
			newInstance.setCategorizationType(new CategorizationTypeBuilder().build(req, formData.getJSONObject("categorizationType")));
			return newInstance;
		}
	}
}
