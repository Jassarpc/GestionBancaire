package vn.edu.ifi.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import vn.edu.ifi.javabean.Banque;

public class WithdrawTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testWithdraw() {

		Banque banque = new Banque();
		boolean succes = banque.withdraw(11, 50000f);
		assertEquals(true,succes);
	}

}
