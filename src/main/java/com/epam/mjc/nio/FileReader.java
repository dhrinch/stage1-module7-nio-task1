package com.epam.mjc.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


public class FileReader {

    public Profile getDataFromFile(File file) {

        Profile profile = new Profile();

        try (RandomAccessFile incFile = new RandomAccessFile(file, "r");
             FileChannel channel = incFile.getChannel();) {
            StringBuilder content = new StringBuilder();
            ByteBuffer buffer = ByteBuffer.allocate(48);
            int bytesRead = channel.read(buffer);
            while (bytesRead != -1) {
                buffer.flip();
                while (buffer.hasRemaining()) {
                    content.append((char) buffer.get());
                }
                buffer.clear();
                bytesRead = channel.read(buffer);

                String result = content.toString();
                String[] data = result.split("\n");

                profile.setName(data[0].split(":")[1].trim());
                profile.setAge(Integer.parseInt(data[1].split(":")[1].trim()));
                profile.setEmail(data[2].split(":")[1].trim());
                profile.setPhone(Long.parseLong(data[3].split(":")[1].trim()));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return profile;
    }
}
