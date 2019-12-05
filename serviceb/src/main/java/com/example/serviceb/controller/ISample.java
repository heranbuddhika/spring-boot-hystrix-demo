package com.example.serviceb.controller;

@FunctionalInterface
public interface ISample {

	public void mm();
	default void m1() {}
	default void m2() {}
	public static void m3() {}
	public static void m4() {}
}
