package ch.kostceco.siard.impl.internal;

import java.util.ArrayList;
import java.util.Collection;

import ch.kostceco.siard.api.IAction;

public class AbstractAction implements IAction<ValidateDirectoryStructureMessage>
{
	private final Collection<ValidateDirectoryStructureMessage> messages = new ArrayList<ValidateDirectoryStructureMessage>();

	@Override
	public Collection<ValidateDirectoryStructureMessage> getMessages()
	{
		return messages;
	}

	@Override
	public void addMessage(ValidateDirectoryStructureMessage message, boolean valid)
	{
		message.setValid(valid);
		messages.add(message);
	}

	@Override
	public boolean isOK()
	{
		for (ValidateDirectoryStructureMessage message : messages)
		{
			if (!message.isValid())
			{
				return false;
			}
		}
		return true;
	}
}
