package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.util.List;

class FluxAndMonoGeneratorServiceTest
{
	@Test
	public void namesFlux()
	{
		var namesFlux = FluxAndMonoGeneratorService.namesFlux();
		StepVerifier.create(namesFlux).expectNext("a", "b", "c", "d", "e", "f").verifyComplete();

		StepVerifier.create(namesFlux).expectNextCount(6).verifyComplete();

		StepVerifier.create(namesFlux).expectNext("a").expectNext("b").expectNextCount(4).verifyComplete();
	}

	@Test
	public void namesFlux_map()
	{
		var namesFlux = FluxAndMonoGeneratorService.namesFlux_map();
		StepVerifier.create(namesFlux).expectNext("A", "B", "C", "D", "E", "F").verifyComplete();

	}

	@Test
	public void namesFlux_filter()
	{
		var namesFlux = FluxAndMonoGeneratorService.namesFlux_filter(5);
		StepVerifier.create(namesFlux).expectNextCount(3).verifyComplete();
	}

	@Test
	public void namesFlux_flatmap_aync()
	{
		var namesFlux = FluxAndMonoGeneratorService.namesFlux_flatmap_async();
		StepVerifier.create(namesFlux).expectNextCount(11).verifyComplete();
	}

	@Test
	public void namesFlux_concat_map()
	{
		var namesFlux = FluxAndMonoGeneratorService.namesFlux_concat_map();
		StepVerifier.create(namesFlux).expectNext("M", "o", "s", "t", "a", "f", "a", "W", "a", "e", "l").verifyComplete();
	}

	@Test
	public void namesMono_flatMap()
	{
		var namesFlux = FluxAndMonoGeneratorService.nameMono_flatMap();
		StepVerifier.create(namesFlux)
					.expectNext(List.of("M", "O", "S", "T", "A", "F", "A"))
					.verifyComplete();
	}
}