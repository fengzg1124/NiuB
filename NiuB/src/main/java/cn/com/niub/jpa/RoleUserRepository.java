package cn.com.niub.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.com.niub.domain.RoleUser;
public interface RoleUserRepository extends JpaRepository<RoleUser, String>, JpaSpecificationExecutor<RoleUser> {

	@Modifying
	@Query(value = "delete from RoleUser b where b.userid = :userid ")
	int deleteByUserId(@Param("userid") String userid);
	
	@Query(value = "select b from RoleUser b where b.userid = :userid ")
	List<RoleUser> findRoleUserByUserId(@Param("userid") String userid);
}
