package com.xenoage.utils.promise;

import com.xenoage.utils.async.Promise;
import org.junit.Test;

import static com.xenoage.utils.jse.promise.Sync.sync;
import static com.xenoage.utils.promise.Calculator.Op.Div;
import static com.xenoage.utils.promise.Calculator.Op.Plus;
import static com.xenoage.utils.promise.Calculator.calc;
import static org.junit.Assert.*;

/**
 * Tests for {@link Promise}.
 *
 * @author Andreas Wenger
 */
public class PromiseTest {

	int result;

	@Test public void testChaining()
			throws Exception {
		sync(calc(1, Plus, 3).produce()
				.then(v -> calc(v, Div, 2))
				.then(v -> calc(v, Plus, 4))
				.thenDo(v -> this.result = v));
		assertEquals(6, result);
	}


}
