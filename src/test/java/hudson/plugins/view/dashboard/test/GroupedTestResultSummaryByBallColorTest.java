package hudson.plugins.view.dashboard.test;

import static org.junit.Assert.assertEquals;
import hudson.model.BallColor;
import hudson.model.Job;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GroupedTestResultSummaryByBallColorTest {

	@Mock
	private Job<?, ?> mockJobOne;
	@Mock
	private Job<?, ?> mockJobTwo;
	@Mock
 	private Job<?, ?> mockJobThree;

	@Test
	public void test() {
		GroupedTestResultSummaryByBallColor testObject = new GroupedTestResultSummaryByBallColor();
		TestResult testResultOne = new TestResult(mockJobOne, 1, 2, 3);
		TestResult testResultTwo = new TestResult(mockJobTwo, 1, 2, 3);
		TestResult testResultThree = new TestResult(mockJobThree, 1, 2, 3);
		testObject.addTestResult(testResultOne);
		testObject.addTestResult(testResultTwo);
		testObject.addTestResult(testResultThree);
		Mockito.when(mockJobOne.getIconColor()).thenReturn(BallColor.ABORTED);
		Mockito.when(mockJobTwo.getIconColor()).thenReturn(BallColor.ABORTED);
		Mockito.when(mockJobThree.getIconColor()).thenReturn(BallColor.BLUE);

		Map<BallColor, TestResultSubtotal<BallColor>> actual = testObject.getGrouping();
		Map<BallColor, TestResultSubtotal<BallColor>> expected = new HashMap<BallColor, TestResultSubtotal<BallColor>>();
		expected.put(BallColor.ABORTED, new TestResultSubtotal<BallColor>(BallColor.ABORTED).add(testResultOne).add(testResultTwo));
		expected.put(BallColor.BLUE, new TestResultSubtotal<BallColor>(BallColor.BLUE).add(testResultThree));
		assertEquals(expected, actual);
	}

}
