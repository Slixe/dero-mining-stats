package fr.slixe.mining.structures;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public class Info {

	@SerializedName("alt_blocks_count")
	@JsonProperty("alt_blocks_count")
	private BigInteger altBlocksCount;
	private BigInteger difficulty;
	private BigInteger height;
	@SerializedName("stableheight")
	@JsonProperty("stableheight")
	private BigInteger stableHeight;
	@SerializedName("topoheight")
	@JsonProperty("topoheight")
	private BigInteger topoHeight;
	@SerializedName("averageblocktime50")
	@JsonProperty("averageblocktime50")
	private double averageBlockTime50;
	private BigInteger target;
	@SerializedName("target_height")
	@JsonProperty("target_height")
	private BigInteger targetHeight;
	private boolean testnet;
	@SerializedName("top_block_hash")
	@JsonProperty("top_block_hash")
	private String topBlockHash;
	@SerializedName("tx_count")
	@JsonProperty("tx_count")
	private BigInteger txCount;
	@SerializedName("tx_pool_size")
	@JsonProperty("tx_pool_size")
	private BigInteger txPoolSize;
	@SerializedName("dynamic_fee_per_kb")
	@JsonProperty("dynamic_fee_per_kb")
	private BigInteger dynamicFeePerKb;
	@SerializedName("total_supply")
	@JsonProperty("total_supply")
	private BigInteger totalSupply;
	@SerializedName("median_block_size")
	@JsonProperty("median_block_size")
	private BigInteger medianBlockSize;
	private String version;

	public BigInteger getAltBlocksCount()
	{
		return altBlocksCount;
	}

	public BigInteger getDifficulty()
	{
		return difficulty;
	}

	public BigInteger getHeight()
	{
		return height;
	}

	public BigInteger getStableHeight()
	{
		return stableHeight;
	}

	public BigInteger getTopoHeight()
	{
		return topoHeight;
	}

	public double getAverageBlockTime50()
	{
		return averageBlockTime50;
	}

	public BigInteger getTarget()
	{
		return target;
	}

	public BigInteger getTargetHeight()
	{
		return targetHeight;
	}

	public boolean isTestnet()
	{
		return testnet;
	}

	public String getTopBlockHash()
	{
		return topBlockHash;
	}

	public BigInteger getTxCount()
	{
		return txCount;
	}

	public BigInteger getTxPoolSize()
	{
		return txPoolSize;
	}

	public BigInteger getDynamicFeePerKb()
	{
		return dynamicFeePerKb;
	}

	public BigInteger getTotalSupply()
	{
		return totalSupply;
	}

	public BigInteger getMedianBlockSize()
	{
		return medianBlockSize;
	}

	public String getVersion()
	{
		return version;
	}

	private String walletAddress;

	public String getWalletAddress()
	{
		return walletAddress;
	}

	public void setWalletAddress(String walletAddress)
	{
		this.walletAddress = walletAddress;
	}
}
