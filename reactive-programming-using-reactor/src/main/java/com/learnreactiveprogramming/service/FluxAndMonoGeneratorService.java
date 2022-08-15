package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

public class FluxAndMonoGeneratorService
{
	public static void main(String[] args)
	{
		/*namesFlux().subscribe(print());
		System.out.println("-----------------------");
		nameMono().subscribe(print());
		System.out.println("-----------------------");
		namesFlux_map().subscribe(print());
		System.out.println("-----------------------");
		namesFlux_flatmap_async().subscribe(print());*/

		System.out.println("-----------------------");
		nameMono_flatMap().subscribe(s-> System.out.println(s.toString()));
	}

	private static Consumer<String> print()
	{
		return x -> System.out.println(x);
	}

	public static Flux<String> namesFlux()
	{
		return Flux.fromIterable(List.of("a", "b", "c", "d", "e", "f"))// come from db or remote service call
				   .log(); // log each event between publisher and subscriber.

	}

	public static Flux<String> namesFlux_map()
	{
		return Flux.fromIterable(List.of("A", "B", "C", "D", "E", "F")).map(x -> x.toUpperCase()).log();

	}

	public static Flux<String> namesFlux_immutability()
	{
		Flux<String> stringFlux = Flux.fromIterable(List.of("A", "B", "C", "D", "E", "F"));
		// it is immutable
		stringFlux.map(String::toLowerCase);
		return stringFlux.log();

	}

	public static Flux<String> namesFlux_filter(int length)
	{
		return Flux.fromIterable(List.of("Mostafa", "Wael", "Malek", "Hasan"))
				   .map(String::toLowerCase)
				   .filter(s -> s.length() >= length)
				   .map(s -> s.length() + "-" + s)
				   .log();
	}

	public static Flux<String> namesFlux_flatmap()
	{
		return Flux.fromIterable(List.of("Mostafa", "Wael", "Malek", "Hasan")).flatMap(s -> splitArray(s)).log();
	}

	public static Flux<String> namesFlux_flatmap_async()
	{
		return Flux.fromIterable(List.of("Mostafa", "Wael")).flatMap(s -> splitArray_withDelay(s)).log();
	}

	//maintain the order of elements after flatting it in asynchronous requests
	public static Flux<String> namesFlux_concat_map()
	{
		return Flux.fromIterable(List.of("Mostafa", "Wael")).concatMap(s -> splitArray_withDelay(s)).log();
	}

	// ALEX => FLUX(A,L,E,X)
	public static Flux<String> splitArray(String name)
	{
		var array = name.split("");
		return Flux.fromArray(array);
	}

	public static Flux<String> splitArray_withDelay(String name)
	{
		var array = name.split("");
		var delay = new Random().nextInt(1000);
		return Flux.fromArray(array).delayElements(Duration.ofMillis(delay));
	}

	/////////////////////////////

	public static Mono<String> nameMono()
	{
		return Mono.just("Mostafa Srour").log();
	}

	public static Mono<List<String>> nameMono_flatMap()
	{
		return Mono.just("Mostafa")
				   .map(String::toUpperCase)
				   .flatMap(s -> splitMonoString(s)).log();
	}

	public static Mono<List<String>> splitMonoString(String s)
	{
		return Mono.just(List.of(s.split("")));
	}

}
