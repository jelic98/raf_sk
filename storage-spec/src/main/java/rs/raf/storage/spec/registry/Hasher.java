package rs.raf.storage.spec.registry;

import rs.raf.storage.spec.auth.User;
import rs.raf.storage.spec.core.File;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Hasher {

    public String hashUsername(User user) {
        return hash(user.getName());
    }

    public String hashPassword(User user) {
        if(user.isSaved()) {
            return user.getPassword();
        }

        return hash(user.getPassword());
    }

    String hashFilepath(File file) {
        return hash(file.getPath());
    }

    Map<String, File> hashFiles(List<File> fileList) {
        Map<String, File> fileMap = new HashMap<>();

        for(File file : fileList) {
            fileMap.put(hashFilepath(file), file);
        }

        return fileMap;
    }

    String hash(String value) {
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(value.getBytes());

            StringBuilder builder = new StringBuilder();

            for(byte b : md.digest()) {
                builder.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }

            return builder.toString();
        }catch(Exception e) {
            return value;
        }
    }
}