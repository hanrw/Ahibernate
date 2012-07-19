package com.hrw.framework.ahibernate.test.domain;

import java.util.List;

import com.hrw.framework.ahibernate.annotation.Column;
import com.hrw.framework.ahibernate.annotation.Id;
import com.hrw.framework.ahibernate.annotation.OneToMany;
import com.hrw.framework.ahibernate.annotation.Table;

@Table(name = "book")
public class Book extends BaseDomain {
	@Id
	private Long id;
	@Column(name = "book_name")
	private String bookName;
	@OneToMany
	private List<Chapter> chapter;

	public Book() {

	}

	public List<Chapter> getChapter() {
		return chapter;
	}

	public void setChapter(List<Chapter> chapter) {
		this.chapter = chapter;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

}
