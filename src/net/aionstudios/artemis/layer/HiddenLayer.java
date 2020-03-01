package net.aionstudios.artemis.layer;

import net.aionstudios.artemis.network.Network;

public class HiddenLayer extends OperableLayer {

	public HiddenLayer(int size, Layer previous, Network network) {
		super(size, previous, network);
	}

}
