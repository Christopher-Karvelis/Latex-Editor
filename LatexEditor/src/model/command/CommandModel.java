package model.command;

public class CommandModel {
	
	private String name;
	private String text;
	
	public CommandModel(String name, String text) {
		setName(name);
		setText(text);
	}
	
	public CommandModel clone() {
		return new CommandModel(this.name, this.text);
	}
	
	public String getName() {
		return name;
	}
	
	public String getText() {
		return text;
	}
	
	public void setName(String name) {
		this.name = new String(name);
	}
	
	public void setText(String text) {
		this.text = new String(text);
	}
}
