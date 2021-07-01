package io‏.coti.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io‏.coti.entity.ArrayElement;

@Repository
public interface ArrayElementRepository extends JpaRepository<ArrayElement, Integer> {

	@Modifying
	@Query("UPDATE ArrayElement ae SET ae.numberValue = :numberValue WHERE ae.indexId = :indexId")
	void setNumberValueByIndexId(@Param("numberValue") int numberValue, @Param("indexId") int indexId);
}
