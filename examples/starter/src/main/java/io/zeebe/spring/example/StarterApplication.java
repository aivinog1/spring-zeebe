package io.zeebe.spring.example;

import io.zeebe.client.api.response.Topology;
import io.zeebe.client.api.response.WorkflowInstanceEvent;
import io.zeebe.reactor.client.ZeebeReactorClient;
import io.zeebe.reactor.client.ZeebeReactorClientImpl;
import io.zeebe.spring.client.EnableZeebeClient;
import io.zeebe.spring.client.ZeebeClientLifecycle;
import io.zeebe.spring.client.annotation.ZeebeDeployment;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import reactor.core.publisher.Mono;

@SpringBootApplication
@EnableZeebeClient
@EnableScheduling
//@ZeebeDeployment(classPathResources = "demoProcess.bpmn")
@Slf4j
public class StarterApplication {

  private final ZeebeClientLifecycle client;
  private final ZeebeReactorClient zeebeReactorClient;

  public StarterApplication(final ZeebeClientLifecycle client) {
    this.client = client;
    this.zeebeReactorClient = new ZeebeReactorClientImpl(client);
  }

  public static void main(final String... args) {
    SpringApplication.run(StarterApplication.class, args);
  }

  @Scheduled(fixedRate = 5000L)
  public void startProcesses() {
    if (!client.isRunning()) {
      return;
    }
    final Mono<Topology> topologyMono = zeebeReactorClient.newTopologyRequest().asMono();
    topologyMono.subscribe(topology -> log.info("Topology brokers: {}", topology.getBrokers()));

//    final WorkflowInstanceEvent event =
//      client
//        .newCreateInstanceCommand()
//        .bpmnProcessId("demoProcess")
//        .latestVersion()
//        .variables("{\"a\": \"" + UUID.randomUUID().toString() + "\"}")
//        .send()
//        .join();
//
//    log.info("started instance for workflowKey='{}', bpmnProcessId='{}', version='{}' with workflowInstanceKey='{}'",
//      event.getWorkflowKey(), event.getBpmnProcessId(), event.getVersion(), event.getWorkflowInstanceKey());
  }
}
