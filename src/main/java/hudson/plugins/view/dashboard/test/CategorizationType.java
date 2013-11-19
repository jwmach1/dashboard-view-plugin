package hudson.plugins.view.dashboard.test;

import org.kohsuke.stapler.DataBoundConstructor;

import jenkins.model.Jenkins;
import hudson.model.Describable;
import hudson.model.Descriptor;

public class CategorizationType implements Describable<CategorizationType>{

	private TestStatisticCategorization testStatisticCategorization;
	static enum TestStatisticCategorization {
		BALL_COLOR(CategorizationType.class), JOB_NAME(JobNameCategorizationType.class);
		
		private Class<? extends CategorizationType> typeClass;

		private TestStatisticCategorization(Class<? extends CategorizationType> typeClass) {
			this.typeClass = typeClass;
		}
		public Class<? extends CategorizationType> getTypeClass() {
			return typeClass;
		}
	}
	
	@DataBoundConstructor
	public CategorizationType(String value) {
		this.testStatisticCategorization = TestStatisticCategorization.valueOf(value);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Descriptor<CategorizationType> getDescriptor() {
		return Jenkins.getInstance().getDescriptorOrDie(getClass());
	}
	
	public TestStatisticCategorization getTestStatisticCategorization() {
		return testStatisticCategorization;
	}
}
