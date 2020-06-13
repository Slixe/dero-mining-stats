package fr.slixe.mining.service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.body.RequestBodyEntity;

import fr.litarvan.paladin.PaladinConfig;
import fr.slixe.mining.structures.BlockTemplate;
import fr.slixe.mining.structures.Info;
import fr.slixe.mining.structures.Miner;
import fr.slixe.mining.structures.SubmitBlock;

@Singleton
public class DaemonService {

	private static final Logger log = LoggerFactory.getLogger("Daemon Service");

	private final String daemonURL;
	private final RequestBodyEntity requestJob;
	private final RequestBodyEntity requestInfo;
	private final String walletAddress;

	private BlockTemplate blockTemplate;
	private Info info;

	@Inject
	private Gson gson;

	@Inject
	private StatsService stats;

	@Inject
	public DaemonService(PaladinConfig config)
	{
		this.daemonURL = config.get("daemonURL") + "/json_rpc";

		this.walletAddress = config.get("walletAddress");
		int reserveSize = config.get("reserveSize", int.class);

		final Map<String, Object> params = new HashMap<>();
		params.put("wallet_address", walletAddress);
		params.put("reserve_size", reserveSize);

		JSONObject body = body("getblocktemplate", params);
		this.requestJob = Unirest.post(daemonURL).header("Content-Type", "application/json").body(body);

		body = body("get_info");
		this.requestInfo = Unirest.post(daemonURL).header("Content-Type", "application/json").body(body);
	}

	public void updateInfo()
	{
		Info info = call(requestInfo, Info.class);
		if (info != null) {
			info.setWalletAddress(walletAddress);
			this.info = info;
		}
	}

	public void updateJob()
	{
		BlockTemplate blockTemplate = call(requestJob, BlockTemplate.class);
		if (blockTemplate != null) {
			this.blockTemplate = blockTemplate;
		}
	}

	public SubmitBlock submitBlock(Miner miner, String[] x)
	{
		final BigInteger height = getCurrentJob().getHeight();
		JSONObject body = body("submitblock", x);
		RequestBodyEntity request = Unirest.post(this.daemonURL).header("Content-Type", "application/json").body(body);

		SubmitBlock block = call(request, SubmitBlock.class);

		if (block != null) {
			block.setMinerIp(miner.getIp());
			block.setHeight(height);
			block.setTimestamp(System.currentTimeMillis());
			stats.addSubmitBlock(block);
			miner.addBlocksFound(1);
			updateJob();
		}

		return block;
	}

	private JSONObject body(String method)
	{
		return body(method, null);
	}

	private JSONObject body(String method, Object params)
	{
		JSONObject json = new JSONObject().put("jsonrpc", "2.0").put("id", 1).put("method", method);
		if (params != null) {
			json.put("params", params);
		}

		return json;
	}

	private <T> T call(RequestBodyEntity body, Class<T> clazz)
	{
		HttpResponse<String> response;
		try {
			response = body.asString();
		} catch (UnirestException e) {
			log.error("An error has occured while retrieving data from daemon!");
			e.printStackTrace();
			return null;
		}

		T data = null;
		String result = response.getBody();
		if (result != null && !result.isEmpty())
		{
			JsonObject jsonResponse = new JsonParser().parse(result).getAsJsonObject();

			if (jsonResponse.has("result"))
			{
				data = gson.fromJson(jsonResponse.get("result").getAsJsonObject(), clazz);
			} else {
				log.warn("Error while retrieving data: {}", jsonResponse);
			}
		}

		return data;
	}

	public BlockTemplate getCurrentJob()
	{
		return blockTemplate;
	}

	public Info getInfo()
	{
		return info;
	}
}
