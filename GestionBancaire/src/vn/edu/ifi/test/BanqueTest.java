/**
 * 
 */
package vn.edu.ifi.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

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
public class BanqueTest {

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
		boolean success = banque.deposit(11, 500000);
		assertEquals(true, success);
	}

	/**
	 * Test method for {@link vn.edu.ifi.javabean.Banque#withdraw(int, float)}.
	 */
	@Test
	public void testWithdraw() {
		Banque banque = new Banque();
		boolean success = banque.withdraw(11, 50000);
		assertEquals(true, success);
	}

	/**
	 * Test method for {@link vn.edu.ifi.javabean.Banque#signUp(java.lang.String, float)}.
	 */
	@Test
	public void testSignUpStringFloat() {
		Banque banque = new Banque();
		int value = banque.signUp("Yasser", 5);
		assertNotEquals(0,value);
	}

}
