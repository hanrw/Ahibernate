package com.hrw.framework.ahibernate.test.domain;

import com.hrw.framework.ahibernate.annotation.Column;

public class History extends BaseDomain {
	private Long id;
	@Column(name = "audio_name")
	private String name;

	private Long chapterId;

	private Long bookId;

}
