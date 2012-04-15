package ch.kostceco.siard.api;

import java.util.ArrayList;
import java.util.Collection;

public class Action<M extends IMessage> implements IAction<M>
{
	private final Collection<M> messages = new ArrayList<M>();

	public Action()
	{
	}

	@Override
	public Collection<M> getMessages()
	{
		return messages;
	}

	@Override
	public void addMessage(M message, boolean valid)
	{
		message.setValid(valid);
		messages.add(message);
	}

	@Override
	public boolean isOK()
	{
		for (M message : messages)
		{
			if (!message.isValid())
			{
				return false;
			}
		}
		return true;
	}
}
