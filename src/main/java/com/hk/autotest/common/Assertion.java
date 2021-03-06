package com.hk.autotest.common;

//import static com.ctrip.cap.CapLogger.pass;
import static org.testng.internal.EclipseInterface.ASSERT_LEFT;
import static org.testng.internal.EclipseInterface.ASSERT_LEFT2;
import static org.testng.internal.EclipseInterface.ASSERT_MIDDLE;
import static org.testng.internal.EclipseInterface.ASSERT_RIGHT;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.collections.Lists;

import com.google.common.base.Predicate;


public class Assertion {
	private static final Logger log = LoggerFactory.getLogger(Assertion.class);

	/**
	 * 判断通过此Locator的Webelement 是否存在于dom或View中
	 * 
	 * @param locator
	 * @param driver
	 */
	public static void assertElementPresent(By locator, WebDriver driver) {
		assertElementPresent(locator, driver, 0);
	}

	/**
	 * 
	 * @param locator
	 * @param driver
	 * @param timeOutInSeconds
	 */
	public static void assertElementPresent(final By locator, WebDriver driver,
			int timeOutInSeconds) {

		try {
			new WebDriverWait(driver, timeOutInSeconds)
					.until(new Predicate<WebDriver>() {
						@Override
						public boolean apply(WebDriver input) {
							WebElement element = null;
							try {
								element = ExpectedConditions
										.presenceOfElementLocated(locator)
										.apply(input);
							} catch (Exception e) {
								log.debug("assertElementPresent", e);
							}
							return element != null;
						}
					});
		} catch (Exception e) {
			fail(String
					.format("Expected element by locator (%s) is not present within %d seconds",
							locator, timeOutInSeconds));
		}

		//pass(String.format("Expected element by locator (%s) is present",
		//		locator));

	}

	/**
	 * 定位器查询到的元素size>0
	 * 
	 * @param locator
	 * @param driver
	 */
	public static void assertElementsPresent(By locator, WebDriver driver) {
		assertElementsPresent(locator, driver, 0);
	}

	/**
	 * 
	 * @param locator
	 * @param driver
	 * @param timeOutInSeconds
	 */
	public static void assertElementsPresent(final By locator,
			WebDriver driver, int timeOutInSeconds) {

		try {
			new WebDriverWait(driver, timeOutInSeconds)
					.until(new Predicate<WebDriver>() {
						@Override
						public boolean apply(WebDriver input) {
							List<WebElement> elements = null;
							;
							try {
								elements = ExpectedConditions
										.presenceOfAllElementsLocatedBy(locator)
										.apply(input);
							} catch (Exception e) {
								log.debug("assertElementsPresent", e);
							}
							return !CollectionUtils.isEmpty(elements);
						}
					});
		} catch (Exception e) {
			fail(String
					.format("Expected element by locator (%s) is not present within %d seconds",
							locator, timeOutInSeconds));
		}
		//pass(String.format("Expected elements by locator (%s) is present",
		//		locator));
	}

	/**
	 * Asserts that a condition is true. If it isn't, an AssertionError, with
	 * the given message, is thrown.
	 * 
	 * @param condition
	 *            the condition to evaluate
	 * @param message
	 *            the assertion error message
	 */
	public static void assertTrue(boolean condition, String message) {
		if (!condition) {
			failNotEquals(Boolean.valueOf(condition), Boolean.TRUE, message);
		}

		if (StringUtils.isNotBlank(message)) {
			//pass(message);
		}

	}

	/**
	 * Asserts that a condition is true. If it isn't, an AssertionError is
	 * thrown.
	 * 
	 * @param condition
	 *            the condition to evaluate
	 */
	public static void assertTrue(boolean condition) {
		assertTrue(condition, null);
	}

	/**
	 * Asserts that a condition is false. If it isn't, an AssertionError, with
	 * the given message, is thrown.
	 * 
	 * @param condition
	 *            the condition to evaluate
	 * @param message
	 *            the assertion error message
	 */
	public static void assertFalse(boolean condition, String message) {
		if (condition) {
			failNotEquals(Boolean.valueOf(condition), Boolean.FALSE, message); // TESTNG-81
		}
		if (StringUtils.isNotBlank(message)) {
			//pass(message);
		}
	}

	/**
	 * Asserts that a condition is false. If it isn't, an AssertionError is
	 * thrown.
	 * 
	 * @param condition
	 *            the condition to evaluate
	 */
	public static void assertFalse(boolean condition) {
		assertFalse(condition, null);
	}

	/**
	 * Fails a test with the given message and wrapping the original exception.
	 *
	 * @param message
	 *            the assertion error message
	 * @param realCause
	 *            the original exception
	 */
	public static void fail(String message, Throwable realCause) {
		AssertionError ae = new AssertionError(message);
		ae.initCause(realCause);

		throw ae;
	}

	/**
	 * Fails a test with the given message.
	 * 
	 * @param message
	 *            the assertion error message
	 */
	public static void fail(String message) {
		throw new AssertionError(message);
	}

	/**
	 * Fails a test with no message.
	 */
	public static void fail() {
		fail(null);
	}

	/*
	 * public static void assertJsonNodeEquals(Object expected, String json,
	 * String jsonPath) { assertEquals(expected,
	 * JsonPath.parse(json).read(jsonPath)); }
	 */
	/**
	 * Asserts that two objects are equal. If they are not, an AssertionError,
	 * with the given message, is thrown.
	 * 
	 * @param actual
	 *            the actual value
	 * @param expected
	 *            the expected value
	 * @param message
	 *            the assertion error message
	 */
	public static void assertEquals(Object actual, Object expected,
			String message) {
		if ((expected == null) && (actual == null)) {
			//pass(String.valueOf(actual), String.valueOf(expected), message);
			return;
		}
		if (expected != null) {
			if (expected.getClass().isArray()) {
				assertArrayEquals(actual, expected, message);
				//pass(String.valueOf(actual), String.valueOf(expected), message);
				return;
			} else if (expected.equals(actual)) {
				//pass(String.valueOf(actual), String.valueOf(expected), message);
				return;
			}
		}
		failNotEquals(actual, expected, message);
	}

	/**
	 * Asserts that two objects are equal. If they are not, an AssertionError is
	 * thrown.
	 * 
	 * @param actual
	 *            the actual value
	 * @param expected
	 *            the expected value
	 */
	public static void assertEquals(Object actual, Object expected) {
		assertEquals(actual, expected, null);
	}

	/**
	 * Asserts that two Strings are equal. If they are not, an AssertionError,
	 * with the given message, is thrown.
	 * 
	 * @param actual
	 *            the actual value
	 * @param expected
	 *            the expected value
	 * @param message
	 *            the assertion error message
	 */
	public static void assertEquals(String actual, String expected,
			String message) {
		assertEquals((Object) actual, (Object) expected, message);
	}

	/**
	 * Asserts that two Strings are equal. If they are not, an AssertionError is
	 * thrown.
	 * 
	 * @param actual
	 *            the actual value
	 * @param expected
	 *            the expected value
	 */
	public static void assertEquals(String actual, String expected) {
		assertEquals(actual, expected, null);
	}

	/**
	 * Asserts that two doubles are equal concerning a delta. If they are not,
	 * an AssertionError, with the given message, is thrown. If the expected
	 * value is infinity then the delta value is ignored.
	 * 
	 * @param actual
	 *            the actual value
	 * @param expected
	 *            the expected value
	 * @param delta
	 *            the absolute tolerable difference between the actual and
	 *            expected values
	 * @param message
	 *            the assertion error message
	 */
	public static void assertEquals(double actual, double expected,
			double delta, String message) {
		if (Double.compare(actual, expected) == 0) {
		} else if (!(Math.abs(expected - actual) <= delta)) {
			failNotEquals(new Double(actual), new Double(expected), message);
		}
		//pass(message);
	}

	/**
	 * Asserts that two doubles are equal concerning a delta. If they are not,
	 * an AssertionError is thrown. If the expected value is infinity then the
	 * delta value is ignored.
	 * 
	 * @param actual
	 *            the actual value
	 * @param expected
	 *            the expected value
	 * @param delta
	 *            the absolute tolerable difference between the actual and
	 *            expected values
	 */
	public static void assertEquals(double actual, double expected, double delta) {
		assertEquals(actual, expected, delta, null);
	}

	/**
	 * Asserts that two floats are equal concerning a delta. If they are not, an
	 * AssertionError, with the given message, is thrown. If the expected value
	 * is infinity then the delta value is ignored.
	 * 
	 * @param actual
	 *            the actual value
	 * @param expected
	 *            the expected value
	 * @param delta
	 *            the absolute tolerable difference between the actual and
	 *            expected values
	 * @param message
	 *            the assertion error message
	 */
	public static void assertEquals(float actual, float expected, float delta,
			String message) {
		// handle infinity specially since subtracting to infinite values gives
		// NaN and the
		// the following test fails
		if (Float.isInfinite(expected)) {
			if (!(Float.compare(expected, actual) == 0)) {
				failNotEquals(new Float(actual), new Float(expected), message);
			}
		} else if (!(Math.abs(expected - actual) <= delta)) {
			failNotEquals(new Float(actual), new Float(expected), message);
		}

		//pass(message);
	}

	/**
	 * Asserts that two floats are equal concerning a delta. If they are not, an
	 * AssertionError is thrown. If the expected value is infinity then the
	 * delta value is ignored.
	 * 
	 * @param actual
	 *            the actual value
	 * @param expected
	 *            the expected value
	 * @param delta
	 *            the absolute tolerable difference between the actual and
	 *            expected values
	 */
	public static void assertEquals(float actual, float expected, float delta) {
		assertEquals(actual, expected, delta, null);
	}

	/**
	 * Asserts that two longs are equal. If they are not, an AssertionError,
	 * with the given message, is thrown.
	 * 
	 * @param actual
	 *            the actual value
	 * @param expected
	 *            the expected value
	 * @param message
	 *            the assertion error message
	 */
	public static void assertEquals(long actual, long expected, String message) {
		assertEquals(Long.valueOf(actual), Long.valueOf(expected), message);
	}

	/**
	 * Asserts that two longs are equal. If they are not, an AssertionError is
	 * thrown.
	 * 
	 * @param actual
	 *            the actual value
	 * @param expected
	 *            the expected value
	 */
	public static void assertEquals(long actual, long expected) {
		assertEquals(actual, expected, null);
	}

	/**
	 * Asserts that two booleans are equal. If they are not, an AssertionError,
	 * with the given message, is thrown.
	 * 
	 * @param actual
	 *            the actual value
	 * @param expected
	 *            the expected value
	 * @param message
	 *            the assertion error message
	 */
	public static void assertEquals(boolean actual, boolean expected,
			String message) {
		assertEquals(Boolean.valueOf(actual), Boolean.valueOf(expected),
				message);
	}

	/**
	 * Asserts that two booleans are equal. If they are not, an AssertionError
	 * is thrown.
	 * 
	 * @param actual
	 *            the actual value
	 * @param expected
	 *            the expected value
	 */
	public static void assertEquals(boolean actual, boolean expected) {
		assertEquals(actual, expected, null);
	}

	/**
	 * Asserts that two bytes are equal. If they are not, an AssertionError,
	 * with the given message, is thrown.
	 * 
	 * @param actual
	 *            the actual value
	 * @param expected
	 *            the expected value
	 * @param message
	 *            the assertion error message
	 */
	public static void assertEquals(byte actual, byte expected, String message) {
		assertEquals(Byte.valueOf(actual), Byte.valueOf(expected), message);
	}

	/**
	 * Asserts that two bytes are equal. If they are not, an AssertionError is
	 * thrown.
	 * 
	 * @param actual
	 *            the actual value
	 * @param expected
	 *            the expected value
	 */
	public static void assertEquals(byte actual, byte expected) {
		assertEquals(actual, expected, null);
	}

	/**
	 * Asserts that two chars are equal. If they are not, an
	 * AssertionFailedError, with the given message, is thrown.
	 * 
	 * @param actual
	 *            the actual value
	 * @param expected
	 *            the expected value
	 * @param message
	 *            the assertion error message
	 */
	public static void assertEquals(char actual, char expected, String message) {
		assertEquals(Character.valueOf(actual), Character.valueOf(expected),
				message);
	}

	/**
	 * Asserts that two chars are equal. If they are not, an AssertionError is
	 * thrown.
	 * 
	 * @param actual
	 *            the actual value
	 * @param expected
	 *            the expected value
	 */
	public static void assertEquals(char actual, char expected) {
		assertEquals(actual, expected, null);
	}

	/**
	 * Asserts that two shorts are equal. If they are not, an
	 * AssertionFailedError, with the given message, is thrown.
	 * 
	 * @param actual
	 *            the actual value
	 * @param expected
	 *            the expected value
	 * @param message
	 *            the assertion error message
	 */
	public static void assertEquals(short actual, short expected, String message) {
		assertEquals(Short.valueOf(actual), Short.valueOf(expected), message);
	}

	/**
	 * Asserts that two shorts are equal. If they are not, an AssertionError is
	 * thrown.
	 * 
	 * @param actual
	 *            the actual value
	 * @param expected
	 *            the expected value
	 */
	public static void assertEquals(short actual, short expected) {
		assertEquals(actual, expected, null);
	}

	/**
	 * Asserts that two ints are equal. If they are not, an
	 * AssertionFailedError, with the given message, is thrown.
	 * 
	 * @param actual
	 *            the actual value
	 * @param expected
	 *            the expected value
	 * @param message
	 *            the assertion error message
	 */
	public static void assertEquals(int actual, int expected, String message) {
		assertEquals(Integer.valueOf(actual), Integer.valueOf(expected),
				message);
	}

	/**
	 * Asserts that two ints are equal. If they are not, an AssertionError is
	 * thrown.
	 * 
	 * @param actual
	 *            the actual value
	 * @param expected
	 *            the expected value
	 */
	public static void assertEquals(int actual, int expected) {
		assertEquals(actual, expected, null);
	}

	/**
	 * Asserts that an object isn't null. If it is, an AssertionError is thrown.
	 * 
	 * @param object
	 *            the assertion object
	 */
	public static void assertNotNull(Object object) {
		assertNotNull(object, null);
	}

	/**
	 * Asserts that an object isn't null. If it is, an AssertionFailedError,
	 * with the given message, is thrown.
	 * 
	 * @param object
	 *            the assertion object
	 * @param message
	 *            the assertion error message
	 */
	public static void assertNotNull(Object object, String message) {
		if (object == null) {
			String formatted = "";
			if (message != null) {
				formatted = message + " ";
			}
			fail(formatted + "expected object to not be null");
		}
		assertTrue(object != null, message);
		//pass(message);
	}

	/**
	 * Asserts that an object is null. If it is not, an AssertionError, with the
	 * given message, is thrown.
	 * 
	 * @param object
	 *            the assertion object
	 */
	public static void assertNull(Object object) {
		assertNull(object, null);
	}

	/**
	 * Asserts that an object is null. If it is not, an AssertionFailedError,
	 * with the given message, is thrown.
	 * 
	 * @param object
	 *            the assertion object
	 * @param message
	 *            the assertion error message
	 */
	public static void assertNull(Object object, String message) {
		if (object != null) {
			failNotSame(object, null, message);
		}
		//pass(message);
	}

	/**
	 * Asserts that two objects refer to the same object. If they do not, an
	 * AssertionFailedError, with the given message, is thrown.
	 * 
	 * @param actual
	 *            the actual value
	 * @param expected
	 *            the expected value
	 * @param message
	 *            the assertion error message
	 */
	public static void assertSame(Object actual, Object expected, String message) {
		if (expected == actual) {
			//pass(message);
			return;
		}
		failNotSame(actual, expected, message);
	}

	/**
	 * Asserts that two objects refer to the same object. If they do not, an
	 * AssertionError is thrown.
	 * 
	 * @param actual
	 *            the actual value
	 * @param expected
	 *            the expected value
	 */
	public static void assertSame(Object actual, Object expected) {
		assertSame(actual, expected, null);
	}

	/**
	 * Asserts that two objects do not refer to the same objects. If they do, an
	 * AssertionError, with the given message, is thrown.
	 * 
	 * @param actual
	 *            the actual value
	 * @param expected
	 *            the expected value
	 * @param message
	 *            the assertion error message
	 */
	public static void assertNotSame(Object actual, Object expected,
			String message) {
		if (expected == actual) {
			failSame(actual, expected, message);
		}
		//pass(message);
	}

	/**
	 * Asserts that two objects do not refer to the same object. If they do, an
	 * AssertionError is thrown.
	 * 
	 * @param actual
	 *            the actual value
	 * @param expected
	 *            the expected value
	 */
	public static void assertNotSame(Object actual, Object expected) {
		assertNotSame(actual, expected, null);
	}

	/**
	 * Asserts that two collections contain the same elements in the same order.
	 * If they do not, an AssertionError is thrown.
	 *
	 * @param actual
	 *            the actual value
	 * @param expected
	 *            the expected value
	 */
	public static void assertEquals(Collection<?> actual, Collection<?> expected) {
		assertEquals(actual, expected, null);
	}

	/**
	 * Asserts that two collections contain the same elements in the same order.
	 * If they do not, an AssertionError, with the given message, is thrown.
	 * 
	 * @param actual
	 *            the actual value
	 * @param expected
	 *            the expected value
	 * @param message
	 *            the assertion error message
	 */
	public static void assertEquals(Collection<?> actual,
			Collection<?> expected, String message) {
		if (actual == expected) {
			//pass(message);
			return;
		}

		if (actual == null || expected == null) {
			if (message != null) {
				fail(message);
			} else {
				fail("Collections not equal: expected: " + expected
						+ " and actual: " + actual);
			}
		}

		assertEquals(actual.size(), expected.size(), message
				+ ": lists don't have the same size");

		Iterator<?> actIt = actual.iterator();
		Iterator<?> expIt = expected.iterator();
		int i = -1;
		while (actIt.hasNext() && expIt.hasNext()) {
			i++;
			Object e = expIt.next();
			Object a = actIt.next();
			String explanation = "Lists differ at element [" + i + "]: " + e
					+ " != " + a;
			String errorMessage = message == null ? explanation : message
					+ ": " + explanation;

			assertEquals(a, e, errorMessage);
		}
	}

	/**
	 * Asserts that two iterators return the same elements in the same order. If
	 * they do not, an AssertionError is thrown. Please note that this assert
	 * iterates over the elements and modifies the state of the iterators.
	 * 
	 * @param actual
	 *            the actual value
	 * @param expected
	 *            the expected value
	 */
	public static void assertEquals(Iterator<?> actual, Iterator<?> expected) {
		assertEquals(actual, expected, null);
	}

	/**
	 * Asserts that two iterators return the same elements in the same order. If
	 * they do not, an AssertionError, with the given message, is thrown. Please
	 * note that this assert iterates over the elements and modifies the state
	 * of the iterators.
	 * 
	 * @param actual
	 *            the actual value
	 * @param expected
	 *            the expected value
	 * @param message
	 *            the assertion error message
	 */
	public static void assertEquals(Iterator<?> actual, Iterator<?> expected,
			String message) {
		if (actual == expected) {
			//pass(message);
			return;
		}

		if (actual == null || expected == null) {
			if (message != null) {
				fail(message);
			} else {
				fail("Iterators not equal: expected: " + expected
						+ " and actual: " + actual);
			}
		}

		int i = -1;
		while (actual.hasNext() && expected.hasNext()) {

			i++;
			Object e = expected.next();
			Object a = actual.next();
			String explanation = "Iterators differ at element [" + i + "]: "
					+ e + " != " + a;
			String errorMessage = message == null ? explanation : message
					+ ": " + explanation;

			assertEquals(a, e, errorMessage);

		}

		if (actual.hasNext()) {

			String explanation = "Actual iterator returned more elements than the expected iterator.";
			String errorMessage = message == null ? explanation : message
					+ ": " + explanation;
			fail(errorMessage);

		} else if (expected.hasNext()) {

			String explanation = "Expected iterator returned more elements than the actual iterator.";
			String errorMessage = message == null ? explanation : message
					+ ": " + explanation;
			fail(errorMessage);

		}

	}

	/**
	 * Asserts that two iterables return iterators with the same elements in the
	 * same order. If they do not, an AssertionError is thrown.
	 * 
	 * @param actual
	 *            the actual value
	 * @param expected
	 *            the expected value
	 */
	public static void assertEquals(Iterable<?> actual, Iterable<?> expected) {
		assertEquals(actual, expected, null);
	}

	/**
	 * Asserts that two iterables return iterators with the same elements in the
	 * same order. If they do not, an AssertionError, with the given message, is
	 * thrown.
	 * 
	 * @param actual
	 *            the actual value
	 * @param expected
	 *            the expected value
	 * @param message
	 *            the assertion error message
	 */
	public static void assertEquals(Iterable<?> actual, Iterable<?> expected,
			String message) {
		if (actual == expected) {
			//pass(message);
			return;
		}

		if (actual == null || expected == null) {
			if (message != null) {
				fail(message);
			} else {
				fail("Iterables not equal: expected: " + expected
						+ " and actual: " + actual);
			}
		}

		Iterator<?> actIt = actual.iterator();
		Iterator<?> expIt = expected.iterator();

		assertEquals(actIt, expIt, message);
	}

	/**
	 * Asserts that two arrays contain the same elements in the same order. If
	 * they do not, an AssertionError, with the given message, is thrown.
	 * 
	 * @param actual
	 *            the actual value
	 * @param expected
	 *            the expected value
	 * @param message
	 *            the assertion error message
	 */
	public static void assertEquals(Object[] actual, Object[] expected,
			String message) {
		if (actual == expected) {
			return;
		}

		if ((actual == null && expected != null)
				|| (actual != null && expected == null)) {
			if (message != null) {
				fail(message);
			} else {
				fail("Arrays not equal: " + Arrays.toString(expected) + " and "
						+ Arrays.toString(actual));
			}
		}
		assertEquals(Arrays.asList(actual), Arrays.asList(expected), message);
	}

	/**
	 * Asserts that two arrays contain the same elements in no particular order.
	 * If they do not, an AssertionError, with the given message, is thrown.
	 * 
	 * @param actual
	 *            the actual value
	 * @param expected
	 *            the expected value
	 * @param message
	 *            the assertion error message
	 */
	public static void assertEqualsNoOrder(Object[] actual, Object[] expected,
			String message) {
		if (actual == expected) {
			return;
		}

		if ((actual == null && expected != null)
				|| (actual != null && expected == null)) {
			failAssertNoEqual("Arrays not equal: " + Arrays.toString(expected)
					+ " and " + Arrays.toString(actual), message);
		}

		if (actual.length != expected.length) {
			failAssertNoEqual("Arrays do not have the same size:"
					+ actual.length + " != " + expected.length, message);
		}

		List<Object> actualCollection = Lists.newArrayList();
		for (Object a : actual) {
			actualCollection.add(a);
		}
		for (Object o : expected) {
			actualCollection.remove(o);
		}
		if (actualCollection.size() != 0) {
			failAssertNoEqual("Arrays not equal: " + Arrays.toString(expected)
					+ " and " + Arrays.toString(actual), message);
		}
	}

	/**
	 * Asserts that two arrays contain the same elements in the same order. If
	 * they do not, an AssertionError is thrown.
	 *
	 * @param actual
	 *            the actual value
	 * @param expected
	 *            the expected value
	 */
	public static void assertEquals(Object[] actual, Object[] expected) {
		assertEquals(actual, expected, null);
	}

	/**
	 * Asserts that two arrays contain the same elements in no particular order.
	 * If they do not, an AssertionError is thrown.
	 * 
	 * @param actual
	 *            the actual value
	 * @param expected
	 *            the expected value
	 */
	public static void assertEqualsNoOrder(Object[] actual, Object[] expected) {
		assertEqualsNoOrder(actual, expected, null);
	}

	/**
	 * Asserts that two arrays contain the same elements in the same order. If
	 * they do not, an AssertionError is thrown.
	 *
	 * @param actual
	 *            the actual value
	 * @param expected
	 *            the expected value
	 */
	public static void assertEquals(final byte[] actual, final byte[] expected) {
		assertEquals(actual, expected, "");
	}

	/**
	 * Asserts that two arrays contain the same elements in the same order. If
	 * they do not, an AssertionError, with the given message, is thrown.
	 *
	 * @param actual
	 *            the actual value
	 * @param expected
	 *            the expected value
	 * @param message
	 *            the assertion error message
	 */
	public static void assertEquals(final byte[] actual, final byte[] expected,
			final String message) {
		if (expected == actual) {
			return;
		}
		if (null == expected) {
			fail("expected a null array, but not null found. " + message);
		}
		if (null == actual) {
			fail("expected not null array, but null found. " + message);
		}

		assertEquals(actual.length, expected.length,
				"arrays don't have the same size. " + message);

		for (int i = 0; i < expected.length; i++) {
			if (expected[i] != actual[i]) {
				fail("arrays differ firstly at element [" + i + "]; "
						+ "expected value is <" + expected[i] + "> but was <"
						+ actual[i] + ">. " + message);
			}
		}
	}

	/**
	 * Asserts that two sets are equal.
	 */
	public static void assertEquals(Set<?> actual, Set<?> expected) {
		assertEquals(actual, expected, null);
	}

	/**
	 * Assert set equals
	 */
	public static void assertEquals(Set<?> actual, Set<?> expected,
			String message) {
		if (actual == expected) {
			return;
		}

		if (actual == null || expected == null) {
			// Keep the back compatible
			if (message == null) {
				fail("Sets not equal: expected: " + expected + " and actual: "
						+ actual);
			} else {
				failNotEquals(actual, expected, message);
			}
		}

		if (!actual.equals(expected)) {
			if (message == null) {
				fail("Sets differ: expected " + expected + " but got " + actual);
			} else {
				failNotEquals(actual, expected, message);
			}
		}
	}

	/**
	 * Asserts that two maps are equal.
	 */
	public static void assertEquals(Map<?, ?> actual, Map<?, ?> expected) {
		if (actual == expected) {
			return;
		}

		if (actual == null || expected == null) {
			fail("Maps not equal: expected: " + expected + " and actual: "
					+ actual);
		}

		if (actual.size() != expected.size()) {
			fail("Maps do not have the same size:" + actual.size() + " != "
					+ expected.size());
		}

		Set<?> entrySet = actual.entrySet();
		for (Iterator<?> iterator = entrySet.iterator(); iterator.hasNext();) {
			Map.Entry<?, ?> entry = (Map.Entry<?, ?>) iterator.next();
			Object key = entry.getKey();
			Object value = entry.getValue();
			Object expectedValue = expected.get(key);
			assertEquals(value, expectedValue, "Maps do not match for key:"
					+ key + " actual:" + value + " expected:" + expectedValue);
		}

	}

	// ///
	// assertNotEquals
	//

	public static void assertNotEquals(Object actual1, Object actual2,
			String message) {
		boolean fail = false;
		try {
			Assert.assertEquals(actual1, actual2);
			fail = true;
		} catch (AssertionError e) {
		}

		if (fail) {
			Assert.fail(message);
		}
	}

	public static void assertNotEquals(Object actual1, Object actual2) {
		assertNotEquals(actual1, actual2, null);
	}

	public static void assertNotEquals(float actual1, float actual2,
			float delta, String message) {
		boolean fail = false;
		try {
			Assert.assertEquals(actual1, actual2, delta, message);
			fail = true;
		} catch (AssertionError e) {

		}

		if (fail) {
			Assert.fail(message);
		}
	}

	public static void assertNotEquals(float actual1, float actual2, float delta) {
		assertNotEquals(actual1, actual2, delta, null);
	}

	public static void assertNotEquals(double actual1, double actual2,
			double delta, String message) {
		boolean fail = false;
		try {
			Assert.assertEquals(actual1, actual2, delta, message);
			fail = true;
		} catch (AssertionError e) {

		}

		if (fail) {
			Assert.fail(message);
		}
	}

	public static void assertNotEquals(double actual1, double actual2,
			double delta) {
		assertNotEquals(actual1, actual2, delta, null);
	}

	private static String format(Object actual, Object expected, String message) {
		String formatted = "";
		if (null != message) {
			formatted = message + " ";
		}

		return formatted + ASSERT_LEFT + expected + ASSERT_MIDDLE + actual
				+ ASSERT_RIGHT;
	}

	static void assertNotEquals(String actual1, String actual2, String message) {
		assertNotEquals((Object) actual1, (Object) actual2, message);
	}

	static void assertNotEquals(String actual1, String actual2) {
		assertNotEquals(actual1, actual2, null);
	}

	static void assertNotEquals(long actual1, long actual2, String message) {
		assertNotEquals(Long.valueOf(actual1), Long.valueOf(actual2), message);
	}

	static void assertNotEquals(long actual1, long actual2) {
		assertNotEquals(actual1, actual2, null);
	}

	static void assertNotEquals(boolean actual1, boolean actual2, String message) {
		assertNotEquals(Boolean.valueOf(actual1), Boolean.valueOf(actual2),
				message);
	}

	static void assertNotEquals(boolean actual1, boolean actual2) {
		assertNotEquals(actual1, actual2, null);
	}

	static void assertNotEquals(byte actual1, byte actual2, String message) {
		assertNotEquals(Byte.valueOf(actual1), Byte.valueOf(actual2), message);
	}

	static void assertNotEquals(byte actual1, byte actual2) {
		assertNotEquals(actual1, actual2, null);
	}

	static void assertNotEquals(char actual1, char actual2, String message) {
		assertNotEquals(Character.valueOf(actual1), Character.valueOf(actual2),
				message);
	}

	static void assertNotEquals(char actual1, char actual2) {
		assertNotEquals(actual1, actual2, null);
	}

	static void assertNotEquals(short actual1, short actual2, String message) {
		assertNotEquals(Short.valueOf(actual1), Short.valueOf(actual2), message);
	}

	static void assertNotEquals(short actual1, short actual2) {
		assertNotEquals(actual1, actual2, null);
	}

	static void assertNotEquals(int actual1, int actual2, String message) {
		assertNotEquals(Integer.valueOf(actual1), Integer.valueOf(actual2),
				message);
	}

	static void assertNotEquals(int actual1, int actual2) {
		assertNotEquals(actual1, actual2, null);
	}

	/**
	 * Asserts that two objects are equal. It they are not, an AssertionError,
	 * with given message, is thrown.
	 * 
	 * @param actual
	 *            the actual value
	 * @param expected
	 *            the expected value (should be an non-null array value)
	 * @param message
	 *            the assertion error message
	 */
	private static void assertArrayEquals(Object actual, Object expected,
			String message) {
		// is called only when expected is an array
		if (actual.getClass().isArray()) {
			int expectedLength = Array.getLength(expected);
			if (expectedLength == Array.getLength(actual)) {
				for (int i = 0; i < expectedLength; i++) {
					Object _actual = Array.get(actual, i);
					Object _expected = Array.get(expected, i);
					try {
						assertEquals(_actual, _expected);
					} catch (AssertionError ae) {
						failNotEquals(actual, expected, message == null ? ""
								: message + " (values at index " + i
										+ " are not the same)");
					}
				}
				// array values matched
				return;
			} else {
				failNotEquals(Array.getLength(actual), expectedLength,
						message == null ? "" : message
								+ " (Array lengths are not the same)");
			}
		}
		failNotEquals(actual, expected, message);
	}

	private static void failSame(Object actual, Object expected, String message) {
		String formatted = "";
		if (message != null) {
			formatted = message + " ";
		}
		fail(formatted + ASSERT_LEFT2 + expected + ASSERT_MIDDLE + actual
				+ ASSERT_RIGHT);
	}

	private static void failNotSame(Object actual, Object expected,
			String message) {
		String formatted = "";
		if (message != null) {
			formatted = message + " ";
		}
		fail(formatted + ASSERT_LEFT + expected + ASSERT_MIDDLE + actual
				+ ASSERT_RIGHT);
	}

	private static void failNotEquals(Object actual, Object expected,
			String message) {
		fail(format(actual, expected, message));
	}

	private static void failAssertNoEqual(String defaultMessage, String message) {
		if (message != null) {
			fail(message);
		} else {
			fail(defaultMessage);
		}
	}
}
