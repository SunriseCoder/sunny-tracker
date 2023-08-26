package tracker.service;

import java.util.List;

import org.springframework.stereotype.Service;

import tracker.entity.Drive;

@Service
public interface DriveService {
    public List<Drive> findAll();
}
