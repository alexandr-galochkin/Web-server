package resourceService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ResourceFactory {
    private static ResourceFactory resourceFactory;

    private ResourceFactory(){
    };

    public static ResourceFactory instance(){
        if (resourceFactory == null){
            resourceFactory = new ResourceFactory();
        }
        return resourceFactory;
    }

    public boolean isFileReadable(Path path){
        return Files.isReadable(path);
    }

    public boolean isFileWritable(Path path){
        return Files.isWritable(path);
    }

    public RandomAccessFile getFile(Path path){
        try {
            return new RandomAccessFile(path.toString(), "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] readBytes(RandomAccessFile file) {
        FileChannel inChannel = file.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(256);
        List<Byte> resultList = new ArrayList<>();
        try {
            while (inChannel.read(buffer) != -1) {
                buffer.flip();
                while(buffer.hasRemaining()){
                    resultList.add(buffer.get());
                }
                buffer.clear();
            }
            file.close();
            byte[] resultArray = new byte[resultList.size()];
            int i = 0;
            for (Object currentByte: resultList) {
                resultArray[i] = (byte)currentByte;
                i++;
            }
            return resultArray;
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    public String readString(RandomAccessFile file){
        byte[] arrayBytes = readBytes(file);
        StringBuilder byteToString = new StringBuilder(arrayBytes.length);
        for(int i = 0; i < arrayBytes.length; i++){
            byteToString.append((char)arrayBytes[i]);
        }
        return byteToString.toString();
    }
}
