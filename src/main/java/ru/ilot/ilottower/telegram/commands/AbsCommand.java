package ru.ilot.ilottower.telegram.commands;


import ru.ilot.ilottower.telegram.response.Response;

public abstract class AbsCommand {
	
	public abstract Response<?> execute();
}
