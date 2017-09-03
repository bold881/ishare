package com.neo.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.neo.entity.PostText;
import com.neo.entity.UserInfo;

@Repository
public class PostTextRepository {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private final int POSTTEXT_PAGESIZE = 3;
	
	protected Session currentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public List<PostText> getPostTexts(int page, UserInfo useInfo) {
		Query query = currentSession().createQuery("From PostText");
		query.setFirstResult((page-1)*this.POSTTEXT_PAGESIZE);
		query.setMaxResults(page*this.POSTTEXT_PAGESIZE-1);
		@SuppressWarnings("unchecked")
		List<PostText> postTexts = query.list();
		return postTexts;
	}

}
