package io.keiji.weatherforecasts;

import android.content.Context;
import android.net.http.AndroidHttpClient;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class GetAreaApi {

    private static final String USER_AGENT = "WeatherForecasts Sample";

    private static final String URL = "http://weather.livedoor.com/forecast/rss/primary_area.xml";

    public static ArrayList<Prefecture> getPrefectureList(Context context)
            throws IOException, ParserConfigurationException, SAXException {
        ArrayList<Prefecture> prefectureList = new ArrayList<Prefecture>();

        AndroidHttpClient client = AndroidHttpClient.newInstance(USER_AGENT, context);

        HttpGet get = new HttpGet(URL);

        try {
            HttpResponse response = client.execute(get);

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(response.getEntity().getContent());
            doc.getDocumentElement().normalize();

            NodeList prefList = doc.getElementsByTagName("pref");

            for (int i = 0; i < prefList.getLength(); i++) {
                Node node = prefList.item(i);
                Prefecture pref = new Prefecture(node);
                prefectureList.add(pref);
            }

        } finally {
            client.close();
        }

        return prefectureList;
    }

    public static class Prefecture implements Serializable {
        public final String title;

        public final ArrayList<City> cityList = new ArrayList<City>();

        public Prefecture(Node node) {
            NamedNodeMap attributes = node.getAttributes();
            title = attributes.getNamedItem("title").getNodeValue();

            NodeList cityNodes = node.getChildNodes();
            for (int i = 0; i < cityNodes.getLength(); i++) {
                if (cityNodes.item(i).getNodeName().equals("city")) {
                    City city = new City(cityNodes.item(i));
                    cityList.add(city);
                }
            }

        }

        public class City implements Serializable {
            public final String id;
            public final String title;
            public final String source;

            public City(Node node) {
                NamedNodeMap attributes = node.getAttributes();
                id = attributes.getNamedItem("id").getNodeValue();
                title = attributes.getNamedItem("title").getNodeValue();
                source = attributes.getNamedItem("source").getNodeValue();

            }

        }

    }
}
