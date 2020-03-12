package fil.eservices.campusincident;

public class ImageApi {

    public static String getUrl(Long id) {
        return "http://54.38.242.184:8080/images/" + id.toString();
    }

}
