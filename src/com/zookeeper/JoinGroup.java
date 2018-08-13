package com.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;

/**
 * 加入组的程序
 * @author carazheng
 *
 */
public class JoinGroup extends ConnectionWatcher{
	
	public void join(String groupName, String memberName) throws KeeperException, InterruptedException
	{
		String path = "/" + groupName + "/" +memberName;
		String createdPath = zk.create(path, null, Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		System.out.println("Created " + createdPath);
	}

	public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
		// TODO Auto-generated method stub
		JoinGroup joinGroup = new JoinGroup();
		joinGroup.connect(args[0]);
		joinGroup.join(args[1], args[2]);
		//模拟工作直到进程被强制终止
		Thread.sleep(Long.MAX_VALUE);
//		joinGroup.close();
	}

}
