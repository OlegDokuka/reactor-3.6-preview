package io.projectreactor.samples;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import io.micrometer.context.ContextRegistry;
import org.reactivestreams.FlowAdapters;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;
import reactor.util.Logger;
import reactor.util.Loggers;

public class StrongContextPropagationSample {

	static final Logger logger = Loggers.getLogger(StrongContextPropagationSample.class);
	static final ThreadLocal<String> TRACE_ID = ThreadLocal.withInitial(() -> "");

	static HttpClient jdkHttpClient = HttpClient.newHttpClient();

	static {
		ContextRegistry.getInstance()
		               .registerThreadLocalAccessor("TRACE_ID", TRACE_ID);
	}

	public static void main(String[] args) {
		logger.info("Setting Trace ID");
		TRACE_ID.set("test-123-567-890");

		Hooks.enableAutomaticContextPropagation();

		Mono.fromFuture(() -> {
			    logger.info("[" + TRACE_ID.get() + "] Preparing request");
			    return jdkHttpClient.sendAsync(HttpRequest.newBuilder()
			                                       .uri(URI.create("https://httpbin.org/drip"))
			                                       .GET()
			                                       .build(),
					    HttpResponse.BodyHandlers.ofPublisher());
		    })
		    .flatMapMany(r -> {
			    logger.info("[" + TRACE_ID.get() + "] " + "Handling response[" + r.statusCode() + "] and reading body");
			    return FlowAdapters.toPublisher(r.body());
		    })
		    .collect(new ByteBufferToStringCollector())
		    .doOnNext(v -> logger.info("[" + TRACE_ID.get() + "] " + "Response body is " + v))
		    .block();
	}
}