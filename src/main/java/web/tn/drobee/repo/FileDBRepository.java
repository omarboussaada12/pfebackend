package web.tn.drobee.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import web.tn.drobee.entity.FileDB;



@Repository
public interface FileDBRepository extends JpaRepository<FileDB, String> {

}
