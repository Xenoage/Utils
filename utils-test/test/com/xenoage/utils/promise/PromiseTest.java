package com.xenoage.utils.promise;

import com.xenoage.utils.async.Consumer;
import com.xenoage.utils.async.Function;
import com.xenoage.utils.async.Promise;
import com.xenoage.utils.async.PromiseFn;
import org.junit.Test;

/**
 * Tests for {@link Promise}.
 *
 * @author Andreas Wenger
 */
public class PromiseTest {

	int result;

	@Test public void testSimple()
			throws Exception {

		//old Java style
		new Promise<Integer>(new PromiseFn<Integer>() {
			@Override public void run(Consumer<Integer> resolve) {
				int value = 42;
				resolve.run(value);
			}
		}).thenAsync(new Function<Integer, Promise<Integer>>() {
			@Override public Promise<Integer> run(Integer value) {
				return new Promise<Integer>(new PromiseFn<Integer>() {
					@Override public void run(Consumer<Integer> resolve) {
						resolve.run(value * 2);
					}
				});
			}
		}).thenSync(new Function<Integer, Object>() {
			@Override public Object run(Integer value) {
				System.out.println(value);
				return null;
			}
		});

		//lambda style
		new Promise<Integer>(resolve -> resolve.run(42))
				.thenSync(v -> v * 2)
				.thenAsync(v -> new Promise<String>(resolve -> resolve.run("The number is " + v)))
				.thenSync(v -> v.toUpperCase())
				.thenSync(v -> { System.out.println(v); return null; });
	}

	/*
	@Test public void testCalc()
			throws Exception {
		sync(calc(1, Plus, 3)
				.then(v -> calc(v, Div, 2))
				.then(v -> calc(v, Plus, 4))
				.then(v -> {
					this.result = v;
					return null;
				}));
		assertEquals(6, result);
	} */


}
