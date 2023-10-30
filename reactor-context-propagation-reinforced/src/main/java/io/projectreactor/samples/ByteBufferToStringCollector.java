package io.projectreactor.samples;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

final class ByteBufferToStringCollector
		implements Collector<List<ByteBuffer>, List<ByteBuffer>, String> {

	@Override
	public Supplier<List<ByteBuffer>> supplier() {
		return () -> new ArrayList<>();
	}

	@Override
	public BiConsumer<List<ByteBuffer>, List<ByteBuffer>> accumulator() {
		return (holder, next) -> holder.addAll(next);
	}

	@Override
	public BinaryOperator<List<ByteBuffer>> combiner() {
		return (s1, s2) -> {
			s1.addAll(s2);
			return s1;
		};
	}

	@Override
	public Function<List<ByteBuffer>, String> finisher() {
		return list -> {
			StringBuilder builder = new StringBuilder();
			for (ByteBuffer byteBuffer : list) {
				builder.append(StandardCharsets.UTF_8.decode(byteBuffer));
			}
			return builder.toString();
		};
	}

	@Override
	public Set<Characteristics> characteristics() {
		return Collections.emptySet();
	}
}
