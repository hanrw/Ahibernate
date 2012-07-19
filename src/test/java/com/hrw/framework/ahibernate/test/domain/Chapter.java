package com.hrw.framework.ahibernate.test.domain;

import com.hrw.framework.ahibernate.annotation.Column;
import com.hrw.framework.ahibernate.annotation.Id;
import com.hrw.framework.ahibernate.annotation.Table;

@Table(name = "chapter")
public class Chapter extends BaseDomain {
	@Id
	private Long id;
	@Column(name = "chapter_name")
	private String chapterName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getChapterName() {
		return chapterName;
	}

	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}

}
