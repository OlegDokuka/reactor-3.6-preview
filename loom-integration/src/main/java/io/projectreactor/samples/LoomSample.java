package io.projectreactor.samples;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class LoomSample {
	public static void main(String[] args) {
		Flux.using(() -> Files.lines(Paths.get(ClassLoader.getSystemResource("testfile.txt").toURI())),
				    Flux::fromStream,
				    Stream::close)
		    .subscribeOn(Schedulers.boundedElastic())
			.map(v -> Thread.currentThread() + " " + v)
		    .log()
		    .blockLast();
	}
}