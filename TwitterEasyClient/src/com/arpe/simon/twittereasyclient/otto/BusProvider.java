package com.arpe.simon.twittereasyclient.otto;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Maintains a singleton instance for obtaining the bus.
 */
public final class BusProvider {
	private static final Bus BUS = new Bus(ThreadEnforcer.ANY);

	public static Bus getInstance() {
		return BUS;
	}

	private BusProvider() {
		// No instances.
	}
}