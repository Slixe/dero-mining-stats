package fr.slixe.mining.structures;

public class Miner {

	private String ip;
	private String workerName;
	private long lastJobRequest;
	private long lastBlockSent;
	private int blocksFound;
	private long lastUpdate;

	public Miner() {}

	public Miner(String ip)
	{
		this.ip = ip;
		this.lastJobRequest = System.currentTimeMillis();
		this.lastBlockSent = -1;
		this.blocksFound = 0;
	}

	public String getIp()
	{
		return ip;
	}

	public String getWorkerName()
	{
		return workerName;
	}

	public void setWorkerName(String workerName)
	{
		this.workerName = workerName;
	}

	public long getLastJobRequest()
	{
		return lastJobRequest;
	}

	public long getLastBlockSent()
	{
		return lastBlockSent;
	}

	public int getBlocksFound()
	{
		return blocksFound;
	}

	public void setLastJobRequest(long lastJobRequest)
	{
		this.lastJobRequest = lastJobRequest;
	}

	public void setLastBlockSent(long lastBlockSent)
	{
		this.lastBlockSent = lastBlockSent;
	}

	public void setBlockFounds(int blocksFound)
	{
		this.blocksFound = blocksFound;
	}

	public void addBlocksFound(int blocks)
	{
		this.blocksFound += blocks;
	}

	public void markAlive()
	{
		this.lastUpdate = System.currentTimeMillis();
	}

	public boolean isAlive()
	{
		return this.lastUpdate + 30 * 1000 > System.currentTimeMillis();
	}
}
