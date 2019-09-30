package nz.mashupapp.Controllers;

import nz.mashupapp.Consumed.*;
import nz.mashupapp.Services.AlbumAndImageList;
import nz.mashupapp.Services.WikiExtractService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
public class MashupController {
    private AlbumAndImageList albumAndImageList;
    private WikiExtractService wikiExtractService;

    MashupController(AlbumAndImageList albumAndImageList, WikiExtractService wikiExtractService) {
        this.albumAndImageList = albumAndImageList;
        this.wikiExtractService = wikiExtractService;
    }

    @RequestMapping("/mashup/{mbid}")
    //http://localhost:8081/mashup/5b11f4ce-a62d-471e-81fc-a69a8278c7da
    public TheResponse theResponse(@PathVariable("mbid") String mbid) {
        //  public String theResponse(@PathVariable("mbid") String mbid) {
        TheResponse theResponse = new TheResponse();
        String test = "testing" + mbid;

        //get list of albums
        ArrayList<AlbumAndImage> albumAndImageArrayList = albumAndImageList.getList(mbid);

        //get info from Wikipedia
        String wikiExtract = wikiExtractService.getwikiExtract(mbid);

        theResponse.setMbid(mbid);
        theResponse.setAlbums(albumAndImageArrayList);
        theResponse.setDescription(wikiExtract);

        return theResponse;
        // return test;
    }


    @RequestMapping("/test")

    public String theTest() {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        RestTemplate restTemplate = new RestTemplate();
        // String url = "http://104.248.251.193:8000/";
        String url = "http://testcallbackb.serveo.net";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("account", "myaccount")
                .queryParam("name", "myname");
        HttpEntity<?> entity = new HttpEntity<>(headers);

        HttpEntity<String> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                String.class);
        //    System.out.println(account);
        return "nothing";
        //return response;
    }

    @RequestMapping("/test2")
    public String theTest2() {

        //   String url = "http://104.248.251.193:8000/?account=test2&name=name2";
        String url = "http://testcallbackb.serveo.net/?account=test2&name=name2";

        RestTemplate restTemplate = new RestTemplate();
        String testingString = restTemplate.getForObject(
                url, String.class);
        System.out.println("test 2 " + testingString);
        return "nothing";

    }


    //for testing previous /test
    @RequestMapping("/")

    public String theResponse2(@RequestParam(name = "account", required = false) String account,
                               @RequestParam(name = "name", required = false) String name) {
        String resp = "";
        resp += "default ";
        if (account != null) {
            System.out.println("success! " + account);
        }
        ;
        resp += account + " ";
        resp += name + " ";
        return resp;
    }

}
