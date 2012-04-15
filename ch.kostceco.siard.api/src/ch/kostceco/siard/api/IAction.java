package ch.kostceco.siard.api;

import java.util.Collection;

public interface IAction<M extends IMessage>
{
	boolean isOK();
	
	void addMessage(M message, boolean valid);

	Collection<M> getMessages();

}
