package fr.slixe.mining.structures;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public class BlockTemplate {

	@SerializedName("blocktemplate_blob") //used by Gson
	@JsonProperty("blocktemplate_blob") //used by Jackson
	private String templateBlob;

	@SerializedName("blockhashing_blob")
	@JsonProperty("blockhashing_blob")
	private String hashingBlob;

	@SerializedName("prev_hash")
	@JsonProperty("prev_hash")
	private String prevHash;

	@SerializedName("expected_reward")
	@JsonProperty("expected_reward")
	private BigInteger expectedReward;

	@SerializedName("reserved_offset")
	@JsonProperty("reserved_offset")
	private BigInteger reservedOffset;

	private BigInteger difficulty;
	private BigInteger height;
	private BigInteger epoch;
	private String status;

	public String getTemplateBlob()
	{
		return templateBlob;
	}

	public String getHashingBlob()
	{
		return hashingBlob;
	}

	public String getPrevHash()
	{
		return prevHash;
	}

	public BigInteger getExpectedReward()
	{
		return expectedReward;
	}

	public BigInteger getReservedOffset()
	{
		return reservedOffset;
	}

	public BigInteger getDifficulty()
	{
		return difficulty;
	}

	public BigInteger getHeight()
	{
		return height;
	}

	public BigInteger getEpoch()
	{
		return epoch;
	}

	public String getStatus()
	{
		return status;
	}
}
