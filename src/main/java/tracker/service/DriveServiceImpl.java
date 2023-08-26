package tracker.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import tracker.entity.Drive;

@Component
public class DriveServiceImpl implements DriveService {
	private static final Logger LOGGER = LoggerFactory.getLogger(DriveServiceImpl.class);

    @Override
    public List<Drive> findAll() {
        List<Drive> drives = new ArrayList<>();

        for (char c = 'C'; c <= 'Z'; c++) {
            File file = new File(c + ":\\");
            if (file.isDirectory() && file.exists()) {
                Drive drive = new Drive();
                drives.add(drive);

                drive.setName(file.getAbsolutePath());
                drive.setFreeSpaceSize(file.getFreeSpace());
                drive.setTotalSpaceSize(file.getTotalSpace());
            }
        }

        return drives;
    }
}
