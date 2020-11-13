package io.zeebe.reactor.client;

import io.zeebe.client.ZeebeClient;
import io.zeebe.client.api.ZeebeFuture;
import io.zeebe.client.api.response.Topology;
import reactor.core.publisher.Mono;

public class ZeebeReactorClientImpl implements ZeebeReactorClient {

  private final ZeebeClient zeebeClient;

  public ZeebeReactorClientImpl(final ZeebeClient zeebeClient) {
    this.zeebeClient = zeebeClient;
  }

  @Override
  public TopologyRequestStep1 newTopologyRequest() {
    return new TopologyRequsetStep1Impl(zeebeClient.newTopologyRequest().send());
  }

  private static class TopologyRequsetStep1Impl implements TopologyRequestStep1 {

    private final ZeebeFuture<Topology> topologyZeebeFuture;

    private TopologyRequsetStep1Impl(final ZeebeFuture<Topology> topologyZeebeFuture) {
      this.topologyZeebeFuture = topologyZeebeFuture;
    }

    @Override
    public Mono<Topology> asMono() {
      return Mono.fromFuture(topologyZeebeFuture.toCompletableFuture());
    }
  }
}
