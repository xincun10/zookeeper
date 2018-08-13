package com.zookeeper;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.KeeperException;

/**
 *  以随机次数更新ZooKeeper的特性
 * @author carazheng
 *
 */
public class ConfigUpdater {

	public static final String PATH = "/config";
	private ActiveKeyValueStore store;
	private Random random = new Random();
	
	public ConfigUpdater(String hosts) throws IOException, InterruptedException
	{
		store = new ActiveKeyValueStore();
		store.connect(hosts);
	}
	
	public void run() throws KeeperException, InterruptedException
	{
		while(true)
		{
			String value = random.nextInt(100) + "";
			store.write(PATH, value);
			System.out.printf("Set %s to %s\n", PATH, value);
			TimeUnit.SECONDS.sleep(random.nextInt(10));
		}
	}
	/**
	 *  用随机值以随机次数更新/config znode
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws KeeperException
	 */
	public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
		ConfigUpdater configUpdater = new ConfigUpdater(args[0]);
		configUpdater.run();
	}
}
