package hudson.plugins.view.dashboard.test;

import static org.junit.Assert.*;
import hudson.model.Job;
import hudson.plugins.view.dashboard.test.CategorizationType.TestStatisticCategorization;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GroupedTestResultSummaryByJobNameTest {
	@Mock
	private Job<?,?> mockJobOne;
	@Mock
	private Job<?,?> mockJobTwo;
	@Mock
	private Job<?,?> mockJobThree;

	@Test
	public void grouping_happypath() throws Exception {
		JobNameCategorizationType testCategorization = new JobNameCategorizationType(TestStatisticCategorization.JOB_NAME.name(), "^.*-(.*)$", "$1");
		GroupedTestResultSummaryByJobName testObject = new GroupedTestResultSummaryByJobName(testCategorization);

		Mockito.when(mockJobOne.getName()).thenReturn("foo-AA");
		Mockito.when(mockJobTwo.getName()).thenReturn("bar-BB");
		Mockito.when(mockJobThree.getName()).thenReturn("baz-AA");
		TestResult testResultOne = new TestResult(mockJobOne, 5, 6, 7);
		TestResult testResultTwo = new TestResult(mockJobTwo, 5, 6, 7);
		TestResult testResultThree = new TestResult(mockJobThree, 5, 6, 7);
		testObject.addTestResult(testResultOne);
		testObject.addTestResult(testResultTwo);
		testObject.addTestResult(testResultThree);

		Map<String, TestResultSubtotal<String>> actual = testObject.getGrouping();
		Map<String, TestResultSubtotal<String>> expected = new HashMap<String, TestResultSubtotal<String>>();
		
		expected.put("AA", new TestResultSubtotal<String>("AA").add(testResultOne).add(testResultThree));
		expected.put("BB", new TestResultSubtotal<String>("BB").add(testResultTwo));
		assertEquals(expected, actual);
	}
	
	@Test
	public void grouping_DoesNotMatch() throws Exception {
		JobNameCategorizationType testCategorization = new JobNameCategorizationType(TestStatisticCategorization.JOB_NAME.name(), "^.*-(.*)$", "$1");
		GroupedTestResultSummaryByJobName testObject = new GroupedTestResultSummaryByJobName(testCategorization);

		Mockito.when(mockJobOne.getName()).thenReturn("fooAA");
		TestResult testResultOne = new TestResult(mockJobOne, 5, 6, 7);
		testObject.addTestResult(testResultOne);

		Map<String, TestResultSubtotal<String>> actual = testObject.getGrouping();
		Map<String, TestResultSubtotal<String>> expected = new HashMap<String, TestResultSubtotal<String>>();
		expected.put("fooAA", new TestResultSubtotal<String>("fooAA").add(testResultOne));
		assertEquals(expected, actual);		
	}
	@Test
	public void grouping_NoGroupInRegex() throws Exception {
		JobNameCategorizationType testCategorization = new JobNameCategorizationType(TestStatisticCategorization.JOB_NAME.name(), "regex", "$1");
		GroupedTestResultSummaryByJobName testObject = new GroupedTestResultSummaryByJobName(testCategorization);
		
		Mockito.when(mockJobOne.getName()).thenReturn("fooAA");
		TestResult testResultOne = new TestResult(mockJobOne, 5, 6, 7);
		testObject.addTestResult(testResultOne);
		
		Map<String, TestResultSubtotal<String>> actual = testObject.getGrouping();
		Map<String, TestResultSubtotal<String>> expected = new HashMap<String, TestResultSubtotal<String>>();
		expected.put("fooAA", new TestResultSubtotal<String>("fooAA").add(testResultOne));
		assertEquals(expected, actual);		
	}

}
