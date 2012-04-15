package ch.kostceco.siard.api;


public class Status<A extends IAction<M>, M extends IMessage> implements IStatus<A, M>
{
	private final A action;
	
	public Status(A action)
	{
		this.action = action;
	}
	
	@Override
	public boolean isOK()
	{
		return action.isOK();
	}

	@Override
	public A getAction()
	{
		return action;
	}

	@Override
	public void update(M message, boolean valid)
	{
		action.addMessage(message, valid);
	}
}
