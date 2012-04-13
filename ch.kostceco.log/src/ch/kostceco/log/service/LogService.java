package ch.kostceco.log.service;

public interface LogService
{
	void log(Status status, String message);

	public enum Status
	{
		LOG_DEBUG, LOG_INFO, LOG_WARNING, LOG_ERROR;
	}
}
