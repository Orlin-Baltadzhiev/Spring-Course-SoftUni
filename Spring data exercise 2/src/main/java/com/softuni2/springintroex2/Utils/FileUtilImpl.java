package com.softuni2.springintroex2.Utils;

import org.springframework.stereotype.Component;

import java.io.*;
import java.lang.reflect.Field;
import java.util.LinkedHashSet;
import java.util.Set;

@Component
public class FileUtilImpl implements FileUtil {
    @Override
    public String[] readFileContent(String filePath) throws IOException {
        File file = new File(filePath);

        BufferedReader bufferedReader = new BufferedReader
                (new FileReader(file));

        String line;
        Set<String> result = new LinkedHashSet<>();

        while ((line= bufferedReader.readLine()) != null)

            if(!"".equals(line)){
                result.add(line);
            }



        return result.toArray(String[]::new);
    }
}
