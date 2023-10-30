package io.projectreactor.samples;

import java.util.UUID;

import io.micrometer.context.ContextRegistry;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.Logger;
import reactor.util.Loggers;

public class SimpleContextPropagationSample {

	static final Logger logger = Loggers.getLogger(SimpleContextPropagationSample.class);
	static final ThreadLocal<String> TRACE_ID = ThreadLocal.withInitial(() -> "");

	static {
		ContextRegistry.getInstance()
		               .registerThreadLocalAccessor("TRACE_ID", TRACE_ID);
	}

	public static void main(String[] args) {
		logger.info("Setting Trace ID");
		TRACE_ID.set("test-123-567-890");

		Hooks.enableAutomaticContextPropagation();

		Mono.fromCallable(() -> {
			    logger.info("[" + TRACE_ID.get() + "] Generating UUID");
			    return UUID.randomUUID();
		    })
		    .subscribeOn(Schedulers.boundedElastic())
		    .doOnNext(v -> logger.info("[" + TRACE_ID.get() + "] " + "Generated UUID " + v))
		    .contextCapture()
		    .block();
	}
}