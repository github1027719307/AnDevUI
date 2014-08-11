package com.kince.andevui.entity;

public class CommentModel {
	public String name;
	public String likes;
	public String comment;
	public String createDate;
	public String player;

	public CommentModel(String name, String likes, String comment,
			String createDate, String player) {
		super();
		this.name = name;
		this.likes = likes;
		this.comment = comment;
		this.createDate = createDate;
		this.player = player;
	}

}
