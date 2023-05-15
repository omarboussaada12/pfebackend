package web.tn.drobee;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import web.tn.drobee.entity.ERole;
import web.tn.drobee.entity.FileDB;
import web.tn.drobee.entity.Role;
import web.tn.drobee.repo.FileDBRepository;
import web.tn.drobee.repo.RoleRepository;
import web.tn.drobee.repo.UserRepository;

import org.springframework.web.multipart.MultipartFile;


@Configuration
@ConditionalOnProperty(name  ="app.dbinit" , havingValue = "true")
public class DbInitializer implements CommandLineRunner {
     
	private RoleRepository rolerepo ;
	private FileDBRepository filedbrepo ;
	private UserRepository userrepo ;
	
	
	public DbInitializer(RoleRepository rolerepo, FileDBRepository filedbrepo, UserRepository userrepo) {
		this.rolerepo = rolerepo;
		this.filedbrepo = filedbrepo;
		this.userrepo = userrepo;
	}


	public void run(String... args) throws Exception {
		Role ROLE_USER = new Role(ERole.ROLE_USER);
		Role ROLE_ADMIN = new Role(ERole.ROLE_ADMIN);
		Role ROLE_CLIENT = new Role(ERole.ROLE_CLIENT);
		
		 String filePath = "C:\\Users\\ASUS\\Desktop\\omarpfe\\avatar7.png";
	     File file = new File(filePath);
		 MultipartFile multipartFile = new CustomMultipartFile(file);
		FileDB FileDB = new FileDB(multipartFile.getName(), multipartFile.getContentType(), multipartFile.getBytes());
		FileDB imagepardefaut = new FileDB();
		this.rolerepo.save(ROLE_USER);
		this.rolerepo.save(ROLE_ADMIN);
		this.rolerepo.save(ROLE_CLIENT);
		this.filedbrepo.save(FileDB);
	} 
	
}
 class CustomMultipartFile implements MultipartFile {
     private final File file;

     public CustomMultipartFile(File file) {
         this.file = file;
     }

     @Override
     public String getName() {
         return file.getName();
     }

     @Override
     public String getOriginalFilename() {
         return file.getName();
     }

     @Override
     public String getContentType() {
         return "image/png";
     }

     @Override
     public boolean isEmpty() {
         return file.length() == 0;
     }

     @Override
     public long getSize() {
         return file.length();
     }

     @Override
     public byte[] getBytes() throws IOException {
         try (InputStream inputStream = new FileInputStream(file)) {
             byte[] bytes = new byte[(int) file.length()];
             inputStream.read(bytes);
             return bytes;
         }
     }

     @Override
     public InputStream getInputStream() throws IOException {
         return new FileInputStream(file);
     }

     @Override
     public void transferTo(File dest) throws IOException, IllegalStateException {
         try (InputStream inputStream = new FileInputStream(file);
              FileOutputStream outputStream = new FileOutputStream(dest)) {
             byte[] buffer = new byte[5024];
             int bytesRead;
             while ((bytesRead = inputStream.read(buffer)) != -1) {
                 outputStream.write(buffer, 0, bytesRead);
             }
         }
     }
 }



