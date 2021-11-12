package com.uniba.di.dfmdevelop.labservice.service;

import com.uniba.di.dfmdevelop.labservice.LabServiceApplication;
import com.uniba.di.dfmdevelop.labservice.model.FileDB;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LabServiceApplication.class)
public class FileStorageServiceTest {

    @Autowired
    FileStorageService fileStorageService;

    @Test
    public void savingFile() throws IOException {

        MultipartFile file = new MockMultipartFile("file.txt","file.txt","text/plain", (byte[]) null);

        FileDB newFile = fileStorageService.store(file);

        assertTrue(newFile != null);
    }

    /*@Test
    public void getRequestedFile() {

        FileDB fileDB = new FileDB();

        fileDB.setName("file.txt");
        fileDB.setType("text/plain");
        fileDB.setData((byte[]) null);

        FileDB gotFile = fileStorageService.getFile(fileDB.getId());

        assertTrue(gotFile != null);
    }*/

    @Test
    public void returningAllFilesPresentsInTheDatabase(){

        Stream<FileDB> listFile = fileStorageService.getAllFiles();

        assertTrue(listFile != null);
    }
}
