package roz.power.market.crud.repository.services.postgres;

import java.io.File;

import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

@Service
public class ImageService {
	
	public static final String ROOT_PATH = System.getProperty("java.io.tmpdir");
	
	public void saveImageProject(int idProject) {
		String pathImagesProject = ROOT_PATH + "/" + idProject + "/";
		File image = new File(pathImagesProject + "name");

	}
	
}
