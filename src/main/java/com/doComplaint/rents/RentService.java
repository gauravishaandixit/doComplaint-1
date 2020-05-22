package com.doComplaint.rents;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class RentService {

    @Autowired
    RentRepository rentRepository;

    private Path fileStorageLocation = Paths.get("/zprojectimages/rent/");

    public String storeFile(MultipartFile file) throws IOException {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;

    }

    public Resource loadFileAsResource(String fileName) throws MalformedURLException {

            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                System.out.println("File not found " + fileName);
                return null;
            }
    }

    /*public boolean addRent(Rent rent){
        rentRepository.save(rent);
        return true;
    }*/
    public Long addRent(Rent rent){
        rentRepository.save(rent);
        return rent.getId();
    }

    public List<Rent> findAll() { return (List<Rent>) rentRepository.findAll(); }

    public Rent findById(Long id)
    {
        return rentRepository.findById(id).orElse(null);
    }
}
