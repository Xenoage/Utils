package com.xenoage.utils.promise;

import com.xenoage.utils.async.Promise;

/**
 * A helper class for {@link PromiseTest}, performing simple calculations.
 */
public class Calculator {

	public enum Op {
		Plus,
		Div;
	}

	/**
	 * Returns the result asynchronously.
	 */
	public static Promise<Integer> calc(int value1, Op op, int value2) {
		/*Promise<Integer> ret = new Promise<Integer>() {
			@Override public void produce() {
				//Plus returns immediately
				if (op == Plus) {
					success(value1 + value2);
				}
				//Simulate more time with Div
				else if (op == Div) {
					new Timer().schedule(new TimerTask() {
						@Override public void run() {
							if (value2 == 0)
								failure(new ArithmeticException());
							else
								success(value1 / value2);
						}
					},500);
				}
				else {
					failure(new UnsupportedOperationException());
				}
			}
		};
		return ret;*/
		return null;
	}

}
