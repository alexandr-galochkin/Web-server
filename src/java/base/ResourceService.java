package base;

import services.accountService.UserProfile;

import java.io.RandomAccessFile;
import java.nio.file.Path;

public interface ResourceService {

    public RandomAccessFile getFile(String stringPath);

    public byte[] readBytes(RandomAccessFile file);

    public String readString(RandomAccessFile file);

    public Object createObjectFromFile(String stringPath);

}
