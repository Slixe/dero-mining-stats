package fr.slixe.mining.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.google.inject.Singleton;

import fr.slixe.mining.structures.Miner;
import fr.slixe.mining.structures.SubmitBlock;

@Singleton
public class StatsService {

	private final Set<SubmitBlock> blocks = new LinkedHashSet<>();
	private final Map<String, Miner> miners = new HashMap<>();

	public void addSubmitBlock(SubmitBlock block)
	{
		this.blocks.add(block);
	}

	public Miner createMiner(String ip)
	{
		Miner miner = new Miner(ip);
		miners.put(ip, miner);

		return miner;
	}

	public Optional<Miner> findMiner(String ip)
	{
		return Optional.ofNullable(miners.get(ip));
	}

	public Collection<SubmitBlock> getBlocks()
	{
		return blocks;
	}

	public Collection<Miner> getMiners()
	{
		return miners.values();
	}
}
