package io.keiji.weatherforecasts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class PreferenceUtil {

    public static List<String> load(File from) throws IOException {
        List<String> cityList = null;
        if (from.exists()) {
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(new FileInputStream(from));
                cityList = (List<String>) ois.readObject();
            } catch (ClassNotFoundException e) {
                // ignore
            } finally {
                if (ois != null) {
                    try {
                        ois.close();
                    } catch (IOException e) {
                    }
                }
            }
        }
        return cityList;
    }

    public static void save(List<String> cityList, File to) throws IOException {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(to));
            oos.writeObject(cityList);
        } finally {
            if (oos != null) {
                try {
                    oos.flush();
                    oos.close();
                } catch (IOException e) {
                }
            }
        }
    }

}
