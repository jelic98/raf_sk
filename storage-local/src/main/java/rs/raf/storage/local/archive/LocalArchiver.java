package rs.raf.storage.local.archive;

import rs.raf.storage.local.core.LocalDirectory;
import rs.raf.storage.spec.archive.Archiver;
import rs.raf.storage.spec.core.Directory;
import rs.raf.storage.spec.core.File;
import rs.raf.storage.spec.core.Path;
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
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class LocalArchiver extends Archiver {

    @Override
    public void archive(List<File> list) {
    	String tmpStr = "tmpDir";
    	String parent = list.get(0).getParent().getAbsolutePath(list.get(0).getParent().getPath());
    	java.io.File folder = new java.io.File(new Path(parent + Res.Wildcard.SEPARATOR + tmpStr, null).build());
    	folder.mkdirs();
    	LocalDirectory absFolder = new LocalDirectory(folder.toString());
    	absFolder.extract(list);
    	
    	try {
    		
    		for(File f : list) {
    			Files.copy(Paths.get(f.getAbsolutePath(f.getPath())), Paths.get(folder.getPath()), StandardCopyOption.REPLACE_EXISTING);
    		}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	zipFile(folder, parent);
    	folder.delete();
    }

    @Override
    public void unarchive(File file) {
    	String fileZip = file.getPath();
        java.io.File destDir = new java.io.File(file.getParent().getPath());
        byte[] buffer = new byte[1024];
        try {
	        ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip));
	        ZipEntry zipEntry = zis.getNextEntry();
	        while (zipEntry != null) {
	            java.io.File newFile = new java.io.File(destDir, fileZip);
	            FileOutputStream fos = new FileOutputStream(newFile);
	            int len;
	            while ((len = zis.read(buffer)) > 0) {
	                fos.write(buffer, 0, len);
	            }
	            fos.close();
	            zipEntry = zis.getNextEntry();
	        }
	        zis.closeEntry();
	        zis.close();
        }catch (IOException e) {
		   e.printStackTrace();
        }
        
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
