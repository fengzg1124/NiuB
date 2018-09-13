package cn.com.niub.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.com.niub.domain.RoleMenu;

public interface RoleMenuRepository extends JpaRepository<RoleMenu, String>, JpaSpecificationExecutor<RoleMenu> {

	@Modifying
	@Query(value = "delete from RoleMenu b where b.roleId = :roleId ")
	int deleteByRoleId(@Param("roleId") String roleId);
	
	@Query(value = "select b from RoleMenu b where b.roleId = :roleId ")
	List<RoleMenu> findRoleMenuByRoleId(@Param("roleId") String roleId);
}
