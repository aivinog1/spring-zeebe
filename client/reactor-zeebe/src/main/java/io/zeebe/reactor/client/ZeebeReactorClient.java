package io.zeebe.reactor.client;

import io.zeebe.client.api.response.Topology;
import reactor.core.publisher.Mono;

public interface ZeebeReactorClient {

  TopologyRequestStep1 newTopologyRequest();


  interface TopologyRequestStep1 extends FinalCommandStep<Topology> {

  }

  interface FinalCommandStep<T> {
    Mono<T> asMono();
  }
}
