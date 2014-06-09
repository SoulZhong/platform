/**
 * 
 */
package com.itag.water.platform.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.itag.water.platform.domain.DataFrame;

/**
 * @author Soul
 */

@Repository
public class DataFrameDao {

	@Autowired
	private SessionFactory sessionFactory;

	public DataFrameDao() {
	}

	public DataFrameDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void save(DataFrame dataFrame) {

		Session session = sessionFactory.getCurrentSession();

		session.save(dataFrame);
	}

	public void delete(DataFrame dataFrame) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(dataFrame);

	}

}
