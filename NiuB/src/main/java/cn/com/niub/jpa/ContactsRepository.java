package cn.com.niub.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.com.niub.domain.Contacts;

public interface ContactsRepository extends JpaRepository<Contacts, String>, JpaSpecificationExecutor<Contacts> {

}
