package fr.slixe.mining.http.controller;

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.google.gson.Gson;
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.inject.Inject;

import fr.litarvan.paladin.http.Controller;
import fr.litarvan.paladin.http.Request
import fr.litarvan.paladin.http.routing.JsonBody
import fr.slixe.mining.http.InvalidParameterException
import fr.slixe.mining.service.DaemonService
import fr.slixe.mining.service.StatsService
import fr.slixe.mining.structures.FakeRPCResult
import fr.slixe.mining.structures.Miner

public class MainController extends Controller {

	private static final Logger log = LoggerFactory.getLogger("Controller");

	@Inject
	private Gson gson

	@Inject
	private DaemonService daemon

	@Inject
	private StatsService stats


	@JsonBody(parse = false)
	def jsonRpc(Request request)
	{
		String content = request.getParam("__jBdy");
		JsonElement jsonElement = new JsonParser().parse(content)

		if (jsonElement == null || !jsonElement.isJsonObject()) {
			throw new InvalidParameterException("Invalid body")
		}

		JsonObject json = jsonElement.getAsJsonObject()
		String method = json.get("method").getAsString()
		
		Optional<Miner> optMiner = stats.findMiner(request.ip)
		Miner miner = optMiner.isPresent() ? optMiner.get() : stats.createMiner(request.ip)
		miner.markAlive()

		switch (method) {
			case "get_info":
				return new FakeRPCResult(daemon.info)
			case "getblocktemplate":
				miner.lastJobRequest = System.currentTimeMillis()
				return new FakeRPCResult(daemon.blockTemplate)
			case "submitblock":
				miner.lastBlockSent = System.currentTimeMillis()
				def jsonArray = json.get("params").getAsJsonArray()
				def x = new String[2];
				x[0] = jsonArray.get(0).getAsString();
				x[1] = jsonArray.get(1).getAsString();
				return new FakeRPCResult(daemon.submitBlock(miner, x))
		}

		[
			message: "Invalid method"
		]
	}

	def job()
	{
		daemon.blockTemplate
	}

	def info()
	{
		daemon.info
	}

	def miners()
	{
		stats.miners
	}

	def blocks()
	{
		stats.blocks
	}
}
