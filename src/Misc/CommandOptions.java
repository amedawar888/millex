package Misc;

public class CommandOptions {
	public String method;
	public boolean requiresCommand;
	
	public CommandOptions(String method, boolean requires) {
		this.method = method;
		this.requiresCommand = requires;
	}
}
