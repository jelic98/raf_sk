package rs.raf.storage.local.archive;

import rs.raf.storage.spec.archive.Archiver;
import rs.raf.storage.spec.core.File;
import rs.raf.storage.spec.res.Res;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class LocalArchiver extends Archiver {

    @Override
    public void archive(List<File> list) {
    	String tmpStr = "tmpDir";
    	String parent = list.get(0).getParent().getPath();
    	java.io.File folder = new java.io.File(parent + tmpStr);
    	
    	try {
    		
    		for(File f : list) {
    			Files.copy(Paths.get(f.getPath()), Paths.get(folder.getPath()), StandardCopyOption.REPLACE_EXISTING);
    		}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	zipFile(folder, parent);
    }

    @Override
    public void unarchive(File file) {
    	
    }
    
    void zipFile(java.io.File zipFile, String folder) {
    	byte[] buffer = new byte[1024];
    	List <String> fileList = generateFileList(zipFile, folder);
        String source = new java.io.File(folder).getName();
        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        try {
            fos = new FileOutputStream(zipFile);
            zos = new ZipOutputStream(fos);

            FileInputStream in = null;

            for (String file: fileList) {
                ZipEntry ze = new ZipEntry(source + Res.Wildcard.SEPARATOR + file);
                zos.putNextEntry(ze);
                try {
                    in = new FileInputStream(folder + Res.Wildcard.SEPARATOR + file);
                    int len;
                    while ((len = in .read(buffer)) > 0) {
                        zos.write(buffer, 0, len);
                    }
                } finally {
                    in.close();
                }
            }

            zos.closeEntry();
            System.out.println("Folder successfully compressed");

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                zos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    List <String> generateFileList(java.io.File node, String folder) {
    	List <String> fileList = new LinkedList<String>();
        if (node.isFile()) {
            fileList.add(folder);
        }

        if (node.isDirectory()) {
            String[] subNote = node.list();
            for (String filename: subNote) {
                generateFileList(new java.io.File(node, filename), node.toString());
            }
        }
        return fileList;
    }
}
