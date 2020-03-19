package fil.eservices.campusincident;

public class ImageApi {

    private ImageApi() {
    }

    public static String getUrl(Long id) {
        String ip = "http://54.38.242.184:8080/images/";
        return ip + id.toString();
    }

}
