package com.zookeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

/**
 * 创建一个znode，代表Zookeeper中的一个组
 * @author carazheng
 *
 */
public class CreateGroup implements Watcher {
	
	private static final int SESSION_TIMEOUT = 5000;
	
	private ZooKeeper zk;
	private CountDownLatch connectedSignal = new CountDownLatch(1);
	
	public void connect(String hosts) throws IOException, InterruptedException
	{
		zk = new ZooKeeper(hosts, SESSION_TIMEOUT, this);
		connectedSignal.await();
	}
	
	@Override
	public void process(WatchedEvent event) {
		// TODO Auto-generated method stub
		if(event.getState() == Watcher.Event.KeeperState.SyncConnected)
		{
			connectedSignal.countDown();
		}
	}
	
	public void create(String groupName) throws KeeperException, InterruptedException
	{
		String path = "/" + groupName;
		String createdPath = zk.create(path, null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		System.out.println("Created " + createdPath);
	}
	
	public void close() throws InterruptedException
	{
		zk.close();
	}
	
	public static void main(String[] args) throws KeeperException, InterruptedException, IOException {
		// TODO Auto-generated method stub
		CreateGroup createGroup = new CreateGroup();
		createGroup.connect(args[0]);
		createGroup.create(args[1]);
		createGroup.close();
	}

}
