package com.kince.andevui.entity;

import java.util.ArrayList;
import java.util.List;

public class CommentFactory {
	private static List<CommentModel> list;

	public static List<CommentModel> getComment() {
		list = new ArrayList<CommentModel>();
		list.add(new CommentModel("园长一号", "36",
				"    幸福，不在别的地方而就在这里.。.不在别的时候而就在此时。", "2012-03-17 20:18-403", "one"));
		list.add(new CommentModel("园长三号", "17",
				"    如果你愿意去发现和创造，在世界的任何一个可以想象的地方，确实都存在着美好的事物。",
				"2012-07-21 16:28-403", "three"));
		list.add(new CommentModel("园长四号", "26", "    你若安好，便是晴天！",
				"2012-09-27 02:18-403", "four"));
		list.add(new CommentModel("园长一号", "36",
				"    真正的缘分，并非是冥冥注定的安排，而是两个人彼此相信的对方。", "2013-01-15 22:48-403", "one"));
		list.add(new CommentModel("园长二号", "15", "在一起比什么都是重要，快乐就好。",
				"2013-03-17 20:18-403", "two"));
		list.add(new CommentModel("园长六号", "29",
				"    就算无趣，就算平淡，也要在黄昏的街道上坚定地握着彼此的手。我们约定，一辈子就已足够。",
				"2013-07-05 14:27-403", "yuxin"));

		return list;

	}
}
