/**
 * 
 */
package vn.edu.ifi.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import vn.edu.ifi.javabean.Banque;

/**
 * @author yasse
 *
 */
public class DepositTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link vn.edu.ifi.javabean.Banque#deposit(int, float)}.
	 */
	@Test
	public void testDeposit() {
		Banque banque = new Banque();
		boolean succes = banque.deposit(-1, 500000f);
		assertEquals(false,succes);
	}

}
