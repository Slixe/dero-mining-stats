package fr.slixe.mining.structures;

import java.math.BigInteger;

public class SubmitBlock {

	private String blid;
	private String status;
	private String minerIp;
	private BigInteger height;
	private long timestamp;

	public String getBlid()
	{
		return blid;
	}

	public String getStatus()
	{
		return status;
	}

	public String getMinerIp()
	{
		return minerIp;
	}

	public void setMinerIp(String ip)
	{
		this.minerIp = ip;
	}

	public BigInteger getHeight()
	{
		return height;
	}

	public void setHeight(BigInteger height)
	{
		this.height = height;
	}

	public long getTimestamp()
	{
		return timestamp;
	}

	public void setTimestamp(long timestamp)
	{
		this.timestamp =  timestamp;
	}
}
