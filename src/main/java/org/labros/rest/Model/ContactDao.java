package org.labros.rest.Model;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;

@Transactional
public interface ContactDao extends CrudRepository<Contact, Long> {

	/**
	 * Return all contacts
	 */
	public List<Contact> findAll();

}