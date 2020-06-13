package fr.slixe.mining.structures;

public class FakeRPCResult<T> {

	private T result;

	public FakeRPCResult(T data)
	{
		this.result = data;
	}

	public T getResult()
	{
		return result;
	}
}
