package com.doComplaint.demand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class DemandService {

    @Autowired
    DemandRepository demandRepository;

    private Path fileStorageLocation = Paths.get("/zprojectimages/request/");

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

    public boolean addDemand(Demand demand){
        demandRepository.save(demand);
        return true;
    }

    public List<Demand> findAll() {return (List<Demand>)demandRepository.findAll(); }

    public Demand findById(Long id) {return demandRepository.findById(id).orElse(null); }
}
