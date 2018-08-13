package com.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;

/**
 * 监视ZooKeeper中的更新，并将其打印到控制台
 * @author carazheng
 *
 */
public class ConfigWatcher implements Watcher {
	private ActiveKeyValueStore store;
	
	public ConfigWatcher(String hosts) throws IOException, InterruptedException
	{
		store = new ActiveKeyValueStore();
		store.connect(hosts);
	}

	public void displayConfig() throws KeeperException, InterruptedException
	{
		String value = store.read(ConfigUpdater.PATH, this);
		System.out.printf("Read %s as %s\n", ConfigUpdater.PATH, value);
	}
	
	@Override
	public void process(WatchedEvent event) {
		if(event.getType() == EventType.NodeDataChanged)
		{
			try	{
				displayConfig();
			} catch (InterruptedException e) {
				System.err.println("Interrupted. Exiting...");
				Thread.currentThread().interrupt();
			} catch(KeeperException e) {
				System.err.printf("KeeperException: %s. Exiting...", e);
			}
		}
	}
	
	public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
		ConfigWatcher configWatcher = new ConfigWatcher(args[0]);
		configWatcher.displayConfig();
		
		Thread.sleep(Long.MAX_VALUE);
	}

}
