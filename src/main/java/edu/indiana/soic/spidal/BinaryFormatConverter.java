package edu.indiana.soic.spidal;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.nio.ShortBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Created by pulasthi on 5/31/16.
 */
public class BinaryFormatConverter {
    private static ByteOrder endianness = ByteOrder.BIG_ENDIAN;
    private static int dataTypeSize = Short.BYTES;

    public static void main(String[] args) {
        // args[2] takes values big or little for endianness
        // arg[3] takes one of the primitive type names in lower case
        String file = args[0];
        String outputfile = args[1];
        endianness =  args[2].equals("big") ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN;

        switch (args[3]){
            case "short": dataTypeSize = Short.BYTES;
                break;
            case "int": dataTypeSize = Integer.BYTES;
                break;
            case "double": dataTypeSize = Double.BYTES;
                break;
            case "long": dataTypeSize = Long.BYTES;
                break;
            case "float": dataTypeSize = Float.BYTES;
                break;
            case "byte": dataTypeSize = Byte.BYTES;
                break;
            default: dataTypeSize = Short.BYTES;
        }

        ConvertFormat(file,outputfile,endianness,dataTypeSize,args[3]);
    }

    private static void ConvertFormat(String filename, String outputfilename, ByteOrder endianness, int dataTypeSize, String dataType) {
        try(FileChannel fc = (FileChannel) Files
                .newByteChannel(Paths.get(filename), StandardOpenOption.READ)) {
            ByteBuffer byteBuffer = ByteBuffer.allocate((int)fc.size());

            if(endianness.equals(ByteOrder.BIG_ENDIAN)){
                byteBuffer.order(ByteOrder.BIG_ENDIAN);
            }else{
                byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
            }
            fc.read(byteBuffer);
            byteBuffer.flip();
            
            ShortBuffer shortBuffer = byteBuffer.asShortBuffer();
            short[] array = new short[(int)fc.size()/2];
            shortBuffer.get(array);

            byteBuffer.clear();

            if(endianness.equals(ByteOrder.BIG_ENDIAN)){
                byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
            }else{
                byteBuffer.order(ByteOrder.BIG_ENDIAN);
            }

            ShortBuffer myShortBuffer = byteBuffer.asShortBuffer();
            myShortBuffer.put(array);
            FileChannel out = new FileOutputStream(outputfilename).getChannel();
            out.write(byteBuffer);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
