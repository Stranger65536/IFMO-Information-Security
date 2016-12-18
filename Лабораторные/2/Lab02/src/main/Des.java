package main;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author vladislav.trofimov@emc.com
 */
public class Des {

    public static final String DECODE_ARG = "-d";
    public static final String ENCODE_ARG = "-e";

    public static void main(String[] args) throws IOException {

        if ((args.length != 5) || (!args[0].equals(DECODE_ARG) && !args[0].equals(ENCODE_ARG))) {
            showHelp();
        } else {

            String option = args[0];
            String inputFilePath = args[1];
            String outputFilePath = args[2];
            String keyString = args[3];
            String lengthValue = args[4];

            int length;
            long key;

            try {
                length = Integer.parseInt(lengthValue);
                if (length <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid length parameter");
                showHelp();
                return;
            }

            try {
                key = Long.parseLong(keyString);
            } catch (NumberFormatException e) {
                System.err.println("Invalid key parameter");
                showHelp();
                return;
            }

            FileInputStream in;
            FileOutputStream out;

            try {

                in = new FileInputStream(new File(inputFilePath));
                out = new FileOutputStream(new File(outputFilePath));

                if (in.available() < length) {
                    throw new IllegalArgumentException();
                }

                DesKey desKey = new DesKey(key);

                for (int i = 0; i < length / 8; i++) {

                    byte[] block = new byte[8];

                    IOUtils.read(in, block);

                    byte encoded[];

                    if (option.equals(ENCODE_ARG)) {
                        encoded = DesEncoder.encode(block, desKey);
                    } else {
                        encoded = DesEncoder.decode(block, desKey);
                    }

                    IOUtils.write(encoded, out);

                }

                if (length % 8 != 0) {

                    byte[] block = new byte[8];
                    byte encoded[];

                    if (option.equals(ENCODE_ARG)) {
                        IOUtils.read(in, block, 0, length % 8);
                        encoded = DesEncoder.encode(block, desKey);
                        IOUtils.write(encoded, out);
                    } else {
                        IOUtils.read(in, block, 0, 8);
                        encoded = DesEncoder.decode(block, desKey);
                        encoded = Arrays.copyOfRange(encoded, 0, length % 8);
                        IOUtils.write(encoded, out);
                    }

                }

                in.close();
                out.close();

            } catch (IOException e) {
                System.err.println("IO error");
            } catch (IllegalArgumentException e) {
                System.err.println("Specified length parameter invalid");
            }

        }

    }

    private static void showHelp() {
        System.err.println("Usage: java Des [option] [input file] [output file] [key] [length]");
    }

}
