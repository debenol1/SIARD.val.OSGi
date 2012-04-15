package ch.kostceco.siard.api;

public interface IStatus<A extends IAction<M>, M extends IMessage>
{
	boolean isOK();
	
	void update(M message, boolean valid);
	
	A getAction();
	
}
