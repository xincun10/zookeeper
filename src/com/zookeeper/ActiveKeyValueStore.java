package com.zookeeper;

import java.nio.charset.Charset;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;
/**
 *  将一个给定的值写到ZooKeeper的键上
 * @author carazheng
 *
 */
public class ActiveKeyValueStore extends ConnectionWatcher{

	private static final Charset CHARSET = Charset.forName("UTF-8");
	/**
	 * 将节点值value写入节点path当中
	 * @param path
	 * @param value
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public void write(String path, String value) throws KeeperException, InterruptedException
	{
		Stat stat = zk.exists(path, false);
		if (stat == null)
		{
			zk.create(path, value.getBytes(CHARSET), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		} else {
			zk.setData(path, value.getBytes(CHARSET), -1);
		}
	}
	/**
	 * 读取节点path的值
	 * @param path
	 * @param watcher
	 * @return
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public String read(String path, Watcher watcher) throws KeeperException, InterruptedException
	{
		byte[] data = zk.getData(path, watcher, null);
		return new String(data, CHARSET);
	}
}
