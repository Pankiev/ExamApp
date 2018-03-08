package pl.exam.app.examapp.configuration;

import java.util.List;

import org.atmosphere.cache.BroadcastMessage;
import org.atmosphere.cache.BroadcasterCacheInspector;
import org.atmosphere.cache.CacheMessage;
import org.atmosphere.config.service.BroadcasterCacheService;
import org.atmosphere.cpr.AtmosphereConfig;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.BroadcasterCache;
import org.atmosphere.cpr.BroadcasterCacheListener;

@BroadcasterCacheService
public class BroadcastCacheConfiguration implements BroadcasterCache
{

	@Override
	public void configure(AtmosphereConfig arg0)
	{
		BroadcasterCache.DEFAULT.configure(arg0);		
	}

	@Override
	public BroadcasterCache addBroadcasterCacheListener(BroadcasterCacheListener arg0)
	{
		return BroadcasterCache.DEFAULT.addBroadcasterCacheListener(arg0);		
	}

	@Override
	public CacheMessage addToCache(String arg0, String arg1, BroadcastMessage arg2)
	{
		return BroadcasterCache.DEFAULT.addToCache(arg0, arg1, arg2);
	}

	@Override
	public BroadcasterCache cacheCandidate(String arg0, String arg1)
	{
		return BroadcasterCache.DEFAULT.cacheCandidate(arg0, arg1);
	}

	@Override
	public void cleanup()
	{
		BroadcasterCache.DEFAULT.cleanup();
	}

	@Override
	public BroadcasterCache clearCache(String arg0, String arg1, CacheMessage arg2)
	{
		return BroadcasterCache.DEFAULT.clearCache(arg0, arg1, arg2);
	}

	@Override
	public BroadcasterCache excludeFromCache(String arg0, AtmosphereResource arg1)
	{
		return BroadcasterCache.DEFAULT.excludeFromCache(arg0, arg1);
	}

	@Override
	public BroadcasterCache inspector(BroadcasterCacheInspector arg0)
	{
		return BroadcasterCache.DEFAULT.inspector(arg0);
	}

	@Override
	public BroadcasterCache removeBroadcasterCacheListener(BroadcasterCacheListener arg0)
	{
		return BroadcasterCache.DEFAULT.removeBroadcasterCacheListener(arg0);
	}

	@Override
	public List<Object> retrieveFromCache(String arg0, String arg1)
	{
		return BroadcasterCache.DEFAULT.retrieveFromCache(arg0, arg1);
	}

	@Override
	public void start()
	{
		BroadcasterCache.DEFAULT.start();
	}

	@Override
	public void stop()
	{
		BroadcasterCache.DEFAULT.stop();
	}

}
