package com.doComplaint.sells;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Max;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class SellService {

    @Autowired
    SellRepository sellRepository;

    private Path fileStorageLocation = Paths.get("/zprojectimages/sell/");

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

    public boolean addSell(Sell sell){
        sellRepository.save(sell);
        return true;
    }

    public List<Sell> findAll() {return (List<Sell>)sellRepository.findAll(); }

    public Sell findById(Long id) {return sellRepository.findById(id).orElse(null); }
}
