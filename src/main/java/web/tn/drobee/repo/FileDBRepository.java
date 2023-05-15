package web.tn.drobee.repo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import web.tn.drobee.entity.FileDB;



@Repository
public interface FileDBRepository extends JpaRepository<FileDB, String> {
	@Query(value = "SELECT id FROM `files` WHERE name = :defaultPictureName LIMIT 1", nativeQuery = true)
	String getidofimagepardefaut(@Param("defaultPictureName") String defaultPictureName);
	
	
	
}
